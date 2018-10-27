package com.example.bygra.conversordedivisa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    }

    protected void guardar(View view){

    }

    protected void volver(View view){
        finish();
    }
}
