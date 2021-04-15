package com.example.descubremadrid;


import android.widget.ImageView;

public class ListaElementos {
    public String idLugar;
    public String nombreLugar;
    public String tipoLugar;
    public String direccionLugar;

    public ListaElementos(String id, String nombre, String tipo, String direccion) {

        this.idLugar = id;
        this.nombreLugar = nombre;
        this.tipoLugar = tipo;
        this.direccionLugar = direccion;
    }

    public String getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(String idLugar) {
        this.idLugar = idLugar;
    }

    public String getNombre() {
        return nombreLugar;
    }

    public void setNombre(String nombre) {
        this.nombreLugar = nombre;
    }

    public String getTipo() {
        return tipoLugar;
    }

    public void setTipo(String tipo) {
        this.tipoLugar = tipo;
    }

    public String getDireccion() {
        return direccionLugar;
    }

    public void setDireccion(String direccion) {
        this.direccionLugar = direccion;
    }


}