package com.example.bygra.conversordedivisa;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EditorDivisa extends AppCompatActivity {

    Button btGuardar;
    Button btVolver;
    ListView lvConversion;
    ArrayList<Double> valoresConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_divisa);


        btVolver = findViewById(R.id.btVolver);
        lvConversion = findViewById(R.id.lvConversion);


        valoresConversion = (ArrayList<Double>) getIntent().getSerializableExtra("tasas");

        lvConversion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToSaver(view, position);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK){
                valoresConversion = (ArrayList<Double>) data.getSerializableExtra("arrayEditada");
            }
        }
    }

    public void goToSaver(View view, int divisa){
        Intent saver = new Intent(this, EditorSaver.class);
        saver.putExtra("divisa",divisa);
        saver.putExtra("tasas", valoresConversion);
        startActivityForResult(saver,1);
        Toast.makeText(this,"Valor editado con exito",Toast.LENGTH_SHORT).show();
    }

    public void volverAMain(View view){
        Intent resultado = new Intent();
        resultado.putExtra("valoresEditados",valoresConversion);
        setResult(Activity.RESULT_OK,resultado);
        finish();
    }
}
