package com.example.bygra.conversordedivisa;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditorSaver extends AppCompatActivity {

    EditText etValor;
    Button btGuardar;
    Button btVolver;

    ArrayList<Double> valoresConversion;
    int divisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editior_saver);

        etValor = findViewById(R.id.etValor);
        btGuardar = findViewById(R.id.btGuardar);
        btVolver = findViewById(R.id.btVolver);

        valoresConversion = (ArrayList<Double>) getIntent().getSerializableExtra("tasas");
        divisa = getIntent().getExtras().getInt("divisa");

        etValor.setText(valoresConversion.get(divisa).toString());
    }

    //Guarda el valor escrito en el EditText en la posicion adecuada del ArrayList
    protected void guardar(View view){
        if(etValor.getText()==null){
            Toast.makeText(this,"Introduce un valor para poder guardar",Toast.LENGTH_SHORT).show();
        } else {
            Log.d("pene","1");
            String nuevoValorST = etValor.getText().toString();
            double nuevoValor = Double.parseDouble(nuevoValorST);

            valoresConversion.add(divisa,nuevoValor);

            Intent resultado = new Intent();
            resultado.putExtra("arrayEditada",valoresConversion);
            setResult(Activity.RESULT_OK,resultado);

            finish();
        }
    }

    protected void volver(View view){
        finish();
    }
}
