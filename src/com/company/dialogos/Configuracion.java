package com.company.dialogos;

import com.company.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */
public class Configuracion extends JDialog{
    private JRadioButton radioButtonEs;
    private JPanel panel1;
    private JRadioButton radioButtonGb;
    private JButton button1;
    private JButton button2;
    /**
     * Este dialogo nos permite modificar las preferencias de idioma
     */
    public Configuracion() {
        setContentPane(panel1);
        setModal(true);
        getRootPane().setDefaultButton(button1);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        setSize(width/4, height/4);
        setLocationRelativeTo(null);
        setUndecorated(true);
        /**
         * No devuelve nada
         * Este metodo cambia la configuración de idioma y nos pide reiniciar la aplicacion si no no se aplicaran los cambios.
         */
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarConfiguracion();
                ResourceBundle resourceBundle = ResourceBundle.getBundle("language");
                int reinicio = Util.mostrarDialogoConfirmacion(resourceBundle.getString("mensaje.seleccion.reinicio"));
                if (reinicio == Util.ACEPTAR)
                    System.exit(0);
                else
                    dispose();
            }
        });
        /**
         * No devuelve nada
         * Este metodo añade el listener al boton para cerrar la ventana de configuracion.
         */
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pack();
        setVisible(true);
    }
    /**
     * No devuelve nada
     * Este metodo guarda la configuracion el el fichero de configuracion por medio de un objeto de tipo {@code Poperties}.
     */
    private void guardarConfiguracion(){
        Properties propiedades = new Properties();
        String idioma;
        String pais;
        if(radioButtonEs.isSelected()){
            idioma = "es";
            pais = "ES";
        } else {
            idioma = "en";
            pais = "UK";
        }
        propiedades.setProperty("idioma", idioma);
        propiedades.setProperty("pais", pais);

        try {
            propiedades.store(new FileWriter("datos/preferencias.conf"),"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
