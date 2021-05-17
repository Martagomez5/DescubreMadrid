package com.example.descubremadrid;


import java.util.ArrayList;

public class spinnerClase {
    ArrayList<Integer> tiempo;
    ArrayList<Double> precio;
    ArrayList<String> latitud;
    ArrayList<String> longitud;

    public ArrayList<Integer> getTiempo() {
        return tiempo;
    }

    public void setTiempo(ArrayList<Integer> tiempo) {
        this.tiempo = tiempo;
    }

    public ArrayList<Double> getPrecio() {
        return precio;
    }

    public void setPrecio(ArrayList<Double> precio) {
        this.precio = precio;
    }

    public ArrayList<String> getLatitud() {
        return latitud;
    }

    public void setLatitud(ArrayList<String> latitud) {
        this.latitud = latitud;
    }

    public ArrayList<String> getLongitud() {
        return longitud;
    }

    public void setLongitud(ArrayList<String> longitud) {
        this.longitud = longitud;
    }
}
