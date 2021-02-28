package com.company.dialogos;

import com.company.base.Autor;
import com.company.base.Manga;
import com.company.base.Revista;
import com.company.mvc.gui.Controlador;
import com.company.mvc.modelo.Modelo;
import com.company.util.Util;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */
public class AnnadirRevista extends JDialog {
    private JTextField textNombreRevista;
    private JPanel panelAnnadirRevista;
    private JButton botAddRev;
    private JComboBox<String> cbDiaVenta;
    private DefaultComboBoxModel<String> dcbmDia;
    private DatePicker dateLanzamiento;
    private JButton botCancel;
    private JTextField textEuros;
    private JTextField textCts;
    private ArrayList<String> dias;

    /**
     * Este dialogo permite añadir revistas a la lista de revistas de la clase {@code Modelo}
     * @param modelo Necesitamos la clase {@code Modelo} para invocar el metodo para añadir
     * @param controlador Recibimos el objeto de clase {@code Controlador} con la unica intencion de listar
     */
    public AnnadirRevista(Modelo modelo, Controlador controlador) {
        dias =new ArrayList<>();
        dcbmDia = new DefaultComboBoxModel<>();
        cbDiaVenta.setModel(dcbmDia);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("language");
        setContentPane(panelAnnadirRevista);
        addListeners(modelo,controlador);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        getRootPane().setDefaultButton(botAddRev);
        int height = pantalla.height;
        int width = pantalla.width;
        setModal(true);
        iniciarComboBox(resourceBundle);
        //he tenido que ajustar la posicion de la ventana ya que por defecto se colocaba en la esquina superior izquierda
        setSize(width/4, height/4);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);

    }

    /**
     * No devuelve nada
     * Este metodo añade al combobox los dias de la semana en el idioma de la aplicacion.
     * @param resourceBundle
     */
    private void iniciarComboBox(ResourceBundle resourceBundle) {
        dias.add(resourceBundle.getString("objeto.revista.diasdelasemana.lunes"));
        dias.add(resourceBundle.getString("objeto.revista.diasdelasemana.martes"));
        dias.add(resourceBundle.getString("objeto.revista.diasdelasemana.miercoles"));
        dias.add(resourceBundle.getString("objeto.revista.diasdelasemana.jueves"));
        dias.add(resourceBundle.getString("objeto.revista.diasdelasemana.viernes"));
        dias.add(resourceBundle.getString("objeto.revista.diasdelasemana.sabado"));
        dias.add(resourceBundle.getString("objeto.revista.diasdelasemana.domingo"));
        for (String dia : dias){
            dcbmDia.addElement(dia);
        }
    }
    /**
     * No devuelve nada
     * Metodo que añade los listeners necesarios a cada boton y lista.
     * @param modelo objeto de tipo {@code Modelo} utilizado para realizar operaciones de añadido a la lista de revistas
     * @param controlador objeto de tipo {@code Controlador} utilizado listar las revistas en la ventana de la {@code Vista}.
     */
    private void addListeners(Modelo modelo, Controlador controlador) {
        botAddRev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float cts = Float.parseFloat(textCts.getText())/100;
                    float precio = Integer.parseInt(textEuros.getText())+cts;
                    ResourceBundle resourceBundle = ResourceBundle.getBundle("language");
                    LocalDate fecha = dateLanzamiento.getDate();
                    int dia = cbDiaVenta.getSelectedIndex()+1;
                    if(!textNombreRevista.getText().replace(" ","").equals("")&&precio>1.00&&fecha!=null){
                        Revista revista =new Revista(precio,textNombreRevista.getText(),dia,new ArrayList<Manga>(),fecha);
                        modelo.addRevista(revista);
                        controlador.listarRevistas();
                        dispose();
                }
                else
                    Util.mostrarDialogoError(resourceBundle.getString("mensaje.error.rellenar.campos"));
            }
        });
        botCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        textEuros.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    int num =Integer.parseInt(textEuros.getText());
                    if(num <=0)
                        Integer.parseInt("error");
                } catch (Exception ex) {
                    textEuros.setText("1");
                }
            }
        });
        textCts.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    int num=Integer.parseInt(textCts.getText());
                    if(num>=100){
                        textCts.setText("0");
                        textEuros.setText(String.valueOf(Integer.parseInt(textEuros.getText())+1));
                    }
                } catch (Exception ex) {
                    textCts.setText("0");
                }
            }
        });
    }


}
