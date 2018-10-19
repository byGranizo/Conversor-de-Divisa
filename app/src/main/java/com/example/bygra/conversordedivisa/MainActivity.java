package com.example.bygra.conversordedivisa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etDivisa1;
    private TextView tvDivisa2;
    private Spinner spEntrada, spSalida;
    private double[][]valoresConversion =    {{1,1.16,0.87,76.45,21.82,8.00,85.28,129.75,7.46,1.14},
            {0.86,1,0.76,66.06,18.85,6.91,73.68,112.10,6.45,0.99},
            {1.13,1.31,1,96.94,24.80,9.09,86.91,147.50,8.48,1.30},
            {0.01,0.01,0.01,1,0.28,0.10,1.11,1.69,0.09,0.01},
            {0.04,0.05,0.04,3.50,1,0.36,3.90,5.94,0.34,0.05},
            {0.12,0.14,0.10,9.55,2.72,1,10.65,16.21,0.93,0.14},
            {0.01,0.01,0.01,0.89,0.25,0.09,1,1.52,0.08,0.01},
            {0.007,0.008,0.006,0.58,0.16,0.06,0.65,1,0.05,0.008},
            {0.13,0.15,0.11,10.23,2.92,1.07,11.42,17.37,1,0.15},
            {0.87,1.00,0.76,66.62,19.01,6.97,74.31,113.06,6.50,1}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDivisa1 = findViewById(R.id.txtDivisa1);
        tvDivisa2 = findViewById(R.id.txtDivisa2);
        spEntrada = findViewById(R.id.spDivisa1);
        spSalida = findViewById(R.id.spDivisa2);

        String[] stSeleccionDivisa = {"Euro","Dolar","Libra","Rublo","Peso","Yuan","Won","Yen","Corona","Franco"};
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

            double divisa2 = divisa1*valoresConversion[divisaOrigen][divisaDestino];

            String stDivisa2 = String.valueOf(divisa2);
            tvDivisa2.setText(stDivisa2);
        } catch (Exception e){
            Toast.makeText(this,"Introduce un valor",Toast.LENGTH_SHORT).show();
        }
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