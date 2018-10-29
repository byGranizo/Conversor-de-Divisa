package com.example.bygra.conversordedivisa;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class BCEExchangeSync extends AsyncTask<String, Void, ArrayList<Double>> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Double> doInBackground(String... strings) {

        ArrayList<Double> vc = new ArrayList<>();

        NodeList nl = getDocument(strings[0]).getElementsByTagName("Cube");

        for (int i = 0; i < nl.getLength(); i++){
            Node node = nl.item(i);
            Element element = (Element)node;

           if(!"".equals(element.getAttribute("currency")) && !"".equals(element.getAttribute("rate"))){
                vc.add(Double.parseDouble(element.getAttribute("rate")));
           }
        }

        return vc;
    }

    @Override
    protected void onPostExecute(ArrayList<Double> doubles) {
        super.onPostExecute(doubles);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private Document getDocument(String urlxml){
        URL url = null;
        InputStream is = null;
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;
        try {
            url = new URL(urlxml);
            is = url.openStream();

            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.parse(is);
            doc.getDocumentElement().normalize();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        if(is != null){
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return doc;
    }
}
