package com.company.base;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */
public class Revista implements Serializable {
    private float precio;
    private String nombre;
    private int diaDeVenta;
    private ArrayList<Manga> publicaciones;
    private LocalDate primeraEdicion;

    public Revista(float precio, String nombre, int diaDeVenta, ArrayList<Manga> publicaciones, LocalDate primeraEdicion) {
        this.precio = precio;
        this.nombre = nombre;
        this.diaDeVenta = diaDeVenta;
        this.publicaciones = publicaciones;
        this.primeraEdicion = primeraEdicion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDiaDeVenta() {
        return diaDeVenta;
    }
    public void addPublicacion(Manga manga){
        publicaciones.add(manga);
    }

    public void setDiaDeVenta(int diaDeVenta) {
        this.diaDeVenta = diaDeVenta;
    }

    public ArrayList<Manga> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(ArrayList<Manga> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public LocalDate getPrimeraEdicion() {
        return primeraEdicion;
    }

    public void setPrimeraEdicion(LocalDate primeraEdicion) {
        this.primeraEdicion = primeraEdicion;
    }

    @Override
    public String toString() {
        return  nombre + " "+precio +" €";
    }
}
