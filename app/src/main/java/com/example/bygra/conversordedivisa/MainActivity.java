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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etDivisa1;
    private TextView tvDivisa2;
    private Spinner spEntrada, spSalida;
    private double[]valoresConversion = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

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
            tasa = valoresConversion[destino-1];
        } else if(destino == 0){
            tasa = 1/valoresConversion[origen-1];
        } else {
            tasa = (1/valoresConversion[origen-1])*valoresConversion[destino-1];
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

        Toast.makeText(this,"Sincronizando",Toast.LENGTH_SHORT).show();

        //Parseo XML
        try {
            //Conexion app-web
            URL url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            //Esta mierda da error
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();


            //Interpretacion del XML
            NodeList nodeList = doc.getElementsByTagName("Cube");
            for(int i = 0; i < nodeList.getLength(); i++){

                Node node = nodeList.item(i);

                Element element = (Element)node;

                valoresConversion[i] = Double.parseDouble(element.getAttribute("rate"));
            }

            Toast.makeText(this,"Sincronizacion finalizada",Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            Toast.makeText(this,"Error al sincronizar",Toast.LENGTH_SHORT).show();



        }
    }
}