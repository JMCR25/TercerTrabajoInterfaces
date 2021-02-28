package com.company.base;

import com.company.mvc.modelo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */
public class Manga implements Serializable {
    private String nombre;
    private int capitulos;
    private boolean finalizado;
    private ImageIcon portada;
    private ArrayList<Integer> autores;

    public Manga(String nombre, int capitulos, boolean finalizado, Image portada) {
        this.nombre = nombre;
        this.capitulos = capitulos;
        this.finalizado = finalizado;
        Image image;
        this.portada = new ImageIcon(portada);
        autores = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapitulos() {
        return capitulos;
    }

    public boolean isFinalizado() {
        return finalizado;
    }
    public String finalizado() {
        String estado = "";
        if (isFinalizado())
            estado="Publicandose";
        else
            estado="Finalizado";
        return estado;
    }
    public ImageIcon getPortada() {
        return portada;
    }

    public ArrayList<Integer> getAutor() {
        return autores;
    }
    public String getAutores() {
        String vuelta = "";
        for (int id : autores){
            vuelta += " "+id;
        }
        vuelta = vuelta.trim();
        return vuelta;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapitulos(int capitulos) {
        this.capitulos = capitulos;
    }

    public void removeAutor(int autor) {
        int conta = 0;
        int c = 0;
        for (int aut : autores){
            if(aut == autor)
                c=conta;
            conta++;
        }
        autores.remove(c);
    }
    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public void setPortada(Image portada) {
        this.portada = new ImageIcon(portada);
    }

    public void setAutor(ArrayList<Integer> autor) {
        this.autores = autores;
    }
    public void addAutor(int autor) {
        autores.add(autor);
    }

    @Override
    public String toString() {
        return nombre +": "+ capitulos +" caps";
    }
}
