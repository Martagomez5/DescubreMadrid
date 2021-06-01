package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class listaQV {
    public String idLugar;
    public String nombreLugar;
    public String tipoLugar;
    public double precioLugar;
    public double tiempoLugar;
    public double longitud;
    public double latitud;
    public boolean seleccionado;

    public listaQV(String idLugar, String nombreLugar, String tipoLugar, double precioLugar, double tiempoLugar,double longitud,double latitud,boolean seleccionado) {
        this.idLugar = idLugar;
        this.nombreLugar = nombreLugar;
        this.tipoLugar = tipoLugar;
        this.precioLugar = precioLugar;
        this.tiempoLugar = tiempoLugar;
        this.longitud = longitud;
        this.latitud = latitud;
        this.seleccionado=seleccionado;
    }

    public String getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(String idLugar) {
        this.idLugar = idLugar;
    }

    public String getNombreLugar() {
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public String getTipoLugar() {
        return tipoLugar;
    }

    public void setTipoLugar(String tipoLugar) {
        this.tipoLugar = tipoLugar;
    }

    public double getPrecioLugar() {
        return precioLugar;
    }

    public void setPrecioLugar(double precioLugar) {
        this.precioLugar = precioLugar;
    }

    public double getTiempoLugar() {
        return tiempoLugar;
    }

    public void setTiempoLugar(double tiempoLugar) {
        this.tiempoLugar = tiempoLugar;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
