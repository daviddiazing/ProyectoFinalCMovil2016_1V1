package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import java.io.Serializable;

/**
 * Created by IvanYaotzin on 25/11/2015.
 */
//Serializable para que se pueda pasar como objeto entre activitys
public class Lista_entrada implements Serializable {
    private int idImagen;
    private String textoEncima;
    private String textoDebajo;
	
	//para la ubicacion de la tienda
	private String nombre;
    private String latitud;
    private String longitud;


    public Lista_entrada (int idImagen, String textoEncima, String textoDebajo, String nombre, String latitud, String longitud) {
        this.idImagen = idImagen;
        this.textoEncima = textoEncima;
        this.textoDebajo = textoDebajo;
		
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
    }

    public String get_textoEncima() {
        return textoEncima;
    }

    public String get_textoDebajo() {
        return textoDebajo;
    }

    public int get_idImagen() {
        return idImagen;
    }
	
	
	public String get_nombre() {
        return nombre;
    }
	public String get_latitud() {
        return latitud;
    }
	public String get_longitud() {
        return longitud;
    }
}
