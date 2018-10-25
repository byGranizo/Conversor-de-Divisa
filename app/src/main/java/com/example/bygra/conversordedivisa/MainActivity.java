package com.example.bygra.conversordedivisa;

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

        String[] stSeleccionDivisa = {"EUR","USD","JPY","BGN","CZK","DKK","GBP","HUL","PLN","RON","SEK",
                "CHF","ISK","NOK","HRK","RUB","TRY","AUD","BRL","CAD","CNY","HKD",
                "IDR","ILS","INR","KRW","MXN","MYR","NZD","PHP","SGD","THB","ZAR"};
        ArrayAdapter <String> seleccionDivisa = new ArrayAdapter <String>(this, R.layout.spinner_item_divisas, stSeleccionDivisa);

        spEntrada.setAdapter(seleccionDivisa);
        spSalida.setAdapter(seleccionDivisa);

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
        startActivity(editorTasas);
    }

    public void actualizarTasas (View view){



    }
}