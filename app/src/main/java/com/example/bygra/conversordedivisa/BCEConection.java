package com.example.bygra.conversordedivisa;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class BCEConection extends AsyncTask<String, String, double[]> {
    private double[]valoresConversion = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected double[] doInBackground(String... strings) {
        publishProgress();
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



        } catch (Exception e){

        }
        return valoresConversion;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        for(String i : values){
            //Toast.makeText(this,values,Toast.LENGTH_SHORT).show();
        }
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(double[] doubles) {
        super.onPostExecute(doubles);
    }
}

