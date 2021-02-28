package com.company.dialogos;

import javax.swing.*;
import java.awt.*;
/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */
public class PantallaCarga extends JDialog{
    JProgressBar progressBar1;
    private JPanel panelCarga;
    /**
     * Pantalla de Carga
     */
    public PantallaCarga() {
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        setSize(width/2, height/2);
        setContentPane(panelCarga);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        progressBar1.setValue(0);
        rellenar();
        dispose();
    }
    /**
     * No devuelve nada
     * Este metodo rellena la barra de carga y cierra la ventana cuando la termine de llenar.
     */
    private void rellenar(){
        int contador = 0;

        while(contador<=100){
            progressBar1.setValue(contador);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            contador +=1;
        }
    }
}
