package com.imunoz.evaluacion1;

import android.widget.ImageView;

import java.io.Serializable;

public class Noticia implements Serializable {
    private String titulo;
    private String texto;
    private String coordenada;
    private ImageView foto;

    public Noticia() {
        this.titulo = "";
        this.texto =  "";
        this.coordenada =  "";
        this.foto =  null;
    }

    public Noticia(String titulo, String texto, String coordenada, ImageView foto) {
        this.titulo = titulo;
        this.texto = texto;
        this.coordenada = coordenada;
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(String coordenada) {
        this.coordenada = coordenada;
    }

    public ImageView getFoto() {
        return foto;
    }

    public void setFoto(ImageView foto) {
        this.foto = foto;
    }
}
