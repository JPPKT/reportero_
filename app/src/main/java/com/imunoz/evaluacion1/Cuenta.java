package com.imunoz.evaluacion1;

import java.io.Serializable;
import java.util.ArrayList;

public class Cuenta implements Serializable {

    private String nombre;
    private String apellido;
    private ArrayList<Noticia> listaNoticias = new ArrayList<Noticia>();
    //private ArrayList<>

    public Cuenta(){
        this.nombre = "";
        this.apellido = "";
    }

    public Cuenta(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    public ArrayList<Noticia> getListaReservas() {
        return listaNoticias;
    }

    public void setListaReservas(ArrayList<Noticia> listaNoticias) {
        this.listaNoticias = listaNoticias;
    }
}