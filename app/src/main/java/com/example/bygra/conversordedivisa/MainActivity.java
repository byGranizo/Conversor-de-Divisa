package com.example.bygra.conversordedivisa;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private EditText etDivisa1;
    private TextView tvDivisa2;
    private Spinner spEntrada, spSalida;
    private ArrayList<Double> valoresConversion = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializacion controladores de texto
        etDivisa1 = findViewById(R.id.txtDivisa1);
        tvDivisa2 = findViewById(R.id.txtDivisa2);

        //Inicializacion spinners
        spEntrada = findViewById(R.id.spDivisa1);
        spSalida = findViewById(R.id.spDivisa2);

        valoresConversion = rellena(valoresConversion);

    }

    //Convierte la divisa cuando se pulse el boton "Convertir"
    public void conversion(View view){

        try{
            String stDivisa1 = etDivisa1.getText().toString();
            double divisa1 = Double.parseDouble(stDivisa1);

            int divisaOrigen = spEntrada.getSelectedItemPosition();
            int divisaDestino = spSalida.getSelectedItemPosition();

            double divisa2 = divisa1 * getTasa(divisaOrigen,divisaDestino);

            String stDivisa2 = String.valueOf(divisa2);
            tvDivisa2.setText(stDivisa2);
        } catch (Exception e){
            Toast.makeText(this,"Introduce un valor",Toast.LENGTH_SHORT).show();
        }
    }

    //Devuelve la tasa de cambio
    public double getTasa (int origen, int destino){
        double tasa;

        if(origen == destino){
            tasa = 1;
        } else if(origen == 0){
            tasa = valoresConversion.get(destino-1);
        } else if(destino == 0){
            tasa = 1/valoresConversion.get(origen-1);
        } else {
            tasa = (1/valoresConversion.get(origen-1))*valoresConversion.get(destino-1);
        }

        return tasa;
    }


    //Eliminara los valores que tenga el formulario al pulsar el boton "Limpiar"
    public void limpiarFormulario(View view){
        etDivisa1.setText("");
        tvDivisa2.setText("");
    }

    //Cambiara al activity de la edicion de las tasas y pasara como parametro el array predeterminado de tasas
    public void goToEdTasas(View view){
        Intent editorTasas = new Intent(this, EditorDivisa.class);
        editorTasas.putExtra("tasas", valoresConversion);
        startActivityForResult(editorTasas,0);
    }

    //Al volver del activity EditorDivisa se comprueba si el intent ha sido correcto actualiza el ArrayList
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                valoresConversion = (ArrayList<Double>) data.getSerializableExtra("valoresEditados");
            }
        }
    }

    //Se accede a un segundo hilo, se conectara a la web del BCE y parseara el xml añadiendolo al ArrayList, de momento no funciona
    public void actualizarTasas (View view){
        String url = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
        BCEExchangeSync conexion = new BCEExchangeSync();
        conexion.execute(url);

        try {
            valoresConversion = conexion.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Como el parseo da error, esto rellenara el ArrayList con los valores que tiene el xml de forma manual
    public ArrayList<Double> rellena (ArrayList<Double> a){
        a.add(1.1345);
        a.add(127.13);
        a.add(1.9558);
        a.add(25.860);
        a.add(7.4607);
        a.add(0.88680);
        a.add(324.41);
        a.add(4.3177);
        a.add(4.6606);
        a.add(10.4025);
        a.add(1.1367);
        a.add(137.10);
        a.add(9.5160);
        a.add(7.4325);
        a.add(74.7073);
        a.add(6.3949);
        a.add(1.6142);
        a.add(4.2125);
        a.add(1.4916);
        a.add(7.8801);
        a.add(8.8950);
        a.add(17252.40);
        a.add(4.2028);
        a.add(83.3400);
        a.add(1298.16);
        a.add(22.1824);
        a.add(4.7498);
        a.add(1.7535);
        a.add(60.877);
        a.add(1.5705);
        a.add(37.569);
        a.add(16.6992);

        return a;
    }
}