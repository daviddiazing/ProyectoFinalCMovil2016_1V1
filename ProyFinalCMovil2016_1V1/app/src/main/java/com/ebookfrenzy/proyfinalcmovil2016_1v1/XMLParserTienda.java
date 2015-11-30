package com.ebookfrenzy.proyfinalcmovil2016_1v1;

/**
 * Created by ZOROASTRO on 29/11/2015.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParserTienda {
    private URL strURL;
    private Context context;
    private String inputStream;

    public XMLParserTienda(String strURL, Context context) {
        this.context = context;
        try {
            this.strURL = new URL(strURL);
        }catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<DatosTienda> parse() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        ArrayList<DatosTienda> ArrayTiendas = new ArrayList<DatosTienda>();
        try
        {
            //Creamos un nuevo parser DOM
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Realizamos lalectura completa del XML
            Document dom = builder.parse(this.getInputStream());

            //Nos posicionamos en el nodo principal del árbol (<rss>)
            Element root = dom.getDocumentElement();

            //Localizamos todos los elementos <item>
            NodeList items = root.getElementsByTagName("array");

            //Recorremos la lista de juegos
            for (int i=0; i<items.getLength(); i++)
            {
                DatosTienda tienda = new DatosTienda();

                //Obtenemos el tienda
                Node item = items.item(i);

                //Obtenemos la lista de datos de la noticia actual
                NodeList datosNoticia = item.getChildNodes();

                //Procesamos cada dato de la noticia
                for (int j=0; j<datosNoticia.getLength(); j++)
                {
                    Node dato = datosNoticia.item(j);
                    String etiqueta = dato.getNodeName();
                    switch (j){
                        case 1:
                            if (etiqueta.equals("string"))
                            {
                                String texto = obtenerTexto(dato);
                                // tienda.setID(texto);
                                tienda.setNombre(texto);
                            }
                            break;
                        case 3:
                            if (etiqueta.equals("string"))
                            {
                                String texto = obtenerTexto(dato);
                                tienda.setLatitud(texto);
                            }
                            break;
                        case 5:
                            if (etiqueta.equals("string"))
                            {
                                String texto = obtenerTexto(dato);
                                // tienda.setDescripcion(texto);
                                tienda.setLongitud(texto);
                            }
                            break;
                        case 7:
                            if (etiqueta.equals("string"))
                            {
                                String texto = obtenerTexto(dato);
                                // tienda.setImagen(texto);
                                // tienda.setImgJuego(resizeImage(descargarImagen(texto),180,180));
                            }
                            break;
                    }
                }
                ArrayTiendas.add(tienda);
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }

        return ArrayTiendas;

    }

    private String obtenerTexto(Node dato)
    {
        StringBuilder texto = new StringBuilder();
        NodeList fragmentos = dato.getChildNodes();

        for (int k=0;k<fragmentos.getLength();k++)
        {
            texto.append(fragmentos.item(k).getNodeValue());
        }

        return texto.toString();
    }
    private InputStream getInputStream()
    {
        try
        {
            return strURL.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Bitmap descargarImagen (String urlImagen){
        URL imageUrl = null;
        Bitmap imagen = null;
        try{
            imageUrl = new URL(urlImagen);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return imagen;
    }

    public static Bitmap resizeImage(Bitmap imgn, int w, int h) {

        // cargamos la imagen de origen
        Bitmap BitmapOrg = imgn;

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        // calculamos el escalado de la imagen destino
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // para poder manipular la imagen
        // debemos crear una matriz

        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // volvemos a crear la imagen con los nuevos valores
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);

        // si queremos poder mostrar nuestra imagen tenemos que crear un
        // objeto drawable y así asignarlo a un botón, imageview...
        return resizedBitmap;

    }
}
