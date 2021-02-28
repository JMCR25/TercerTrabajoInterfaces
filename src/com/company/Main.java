package com.company;


import com.company.mvc.gui.Controlador;
import com.company.mvc.gui.Vista;
import com.company.mvc.modelo.Modelo;
import com.company.util.Util;

import javax.swing.*;
import java.util.Locale;
/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */
public class Main {
    /**
     * Metodo main inicia la aplicaion
     * @param args recibe argumentos en esta aplicacion no se usa
     */
    public static void main(String[] args) {
        Util.crearSiNoExisteDirectorioDatos();
        Locale.setDefault(Util.obtenerLocale());
        Vista vista = new Vista();
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(vista, modelo);

    }
}
