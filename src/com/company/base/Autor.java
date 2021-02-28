package com.company.base;

import java.awt.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */
public class Autor implements Serializable {
    private String nombre;
    private int idAutor;
    private LocalDate fechaNac;
    private ArrayList<Manga> publicaciones;
    private String apellido;

    public Autor(String nombre, int idAutor, LocalDate fechaNac, ArrayList<Manga> publicacion, String apellido) {
        this.nombre = nombre;
        this.idAutor = idAutor;
        this.fechaNac = fechaNac;
        publicaciones = publicacion;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void addPublicacion(Manga manga){
        publicaciones.add(manga);
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public ArrayList<Manga> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(ArrayList<Manga> publicaciones) {
        this.publicaciones = publicaciones;
    }
    public String publicaciones(){
        String devol = "";
        for (Manga manga:getPublicaciones()){
            devol+=manga.getNombre()+"\n";
        }
        return devol;
    }
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return idAutor +". " + nombre + ' '+ apellido;
    }
}
