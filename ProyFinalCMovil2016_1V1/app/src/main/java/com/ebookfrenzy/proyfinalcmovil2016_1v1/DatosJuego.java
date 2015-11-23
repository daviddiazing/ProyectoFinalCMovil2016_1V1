package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.graphics.Bitmap;

/**
 * Created by JUANITO on 22/11/2015.
 */
public class DatosJuego {
    private String strNombre;
    private String strID;
    private String strDescripcion;
    private String strURL;
    private String strImagen;
    private Bitmap imgJuego;

    public void setNombre (String strNombre) {
        this.strNombre = strNombre;
    }

    public void setDescripcion (String strDescripcion) {
        this.strDescripcion= strDescripcion;
    }

    public void setID(String strID) {
        this.strID = strID;
    }

    public void setStrURL(String strURL) {
        this.strURL= strURL;
    }

    public void setImagen(String strImagen) {
        this.strImagen = strImagen;
    }

    public void setImgJuego( Bitmap imgJuego){
        this.imgJuego = imgJuego;
    }

    public String getNombre() {
        return strNombre;
    }

    public String getID() {
        return strID;
    }

    public String getDescripcion() {
        return strDescripcion;
    }

    public String getURL() {
        return strURL;
    }

    public String getImagen() {
        return strImagen;
    }

    public Bitmap getImgJuego(){
        return imgJuego;
    }

}
