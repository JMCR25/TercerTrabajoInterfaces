package com.company.dialogos;

import com.company.base.Autor;
import com.company.base.Manga;
import com.company.mvc.gui.Controlador;
import com.company.mvc.modelo.Modelo;
import com.company.util.Util;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */
public class AnnadirAutor extends JDialog{
    JTextField textNombreAutor;
    JTextField textApellidoAutor;
    JButton botCancel;
    JButton botAddAutores;
    DatePicker dateFechaNacAutor;
    JPanel panelAutor;
    private JTextField txtAutorId;
    JSpinner spinner1;



    public AnnadirAutor(Modelo modelo, Controlador controlador) {
        setContentPane(panelAutor);
        setResizable(false);
        setModal(true);
        addKeyListener();
        getRootPane().setDefaultButton(botAddAutores);
        botCancel.setMnemonic(KeyEvent.VK_ESCAPE);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        //he tenido que ajustar la posicion de la ventana ya que por defecto se colocaba en la esquina superior izquierda
        setSize(width/4, height/4);
        setLocationRelativeTo(null);
        addActionListeners(modelo, controlador);
        pack();
        setVisible(true);

    }

    /**
     * Añade un {@code KeyListener} al cuadro de texto del id del autor para impedir la entrada por teclado de otro caracter que no sean numeros
     */
    private void addKeyListener() {
        txtAutorId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try {
                    int num=Integer.parseInt(txtAutorId.getText());
                    if(num<=0)
                        Integer.parseInt("error");
                } catch (Exception ex) {
                    txtAutorId.setText("1");
                }
            }
        });
    }

    /**
     * No devuelve nada
     * Añade funcionalidad y listeners a los botones y lista de la ventana.
     * @param modelo objeto de tipo {@code Modelo} utilizado para realizar operaciones de añadido a la lista de autores.
     * @param controlador objeto de tipo {@code Controlador} utilizado para listar en la ventana de {@code Vista}.
     *
     */
    private void addActionListeners(Modelo modelo, Controlador controlador) {
        botAddAutores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textNombreAutor.getText();
                String apellido = textApellidoAutor.getText();
                int id = Integer.valueOf(txtAutorId.getText());
                LocalDate fecha = dateFechaNacAutor.getDate();
                if(!nombre.replace(" ","").equals("")&&!apellido.replace(" ","").equals("")&&fecha!=null){
                Autor autor = new Autor(nombre,id, fecha,new ArrayList<Manga>(),apellido);
                modelo.addAutores(autor);
                controlador.listarAutores();
                dispose();}
                else
                    Util.mostrarDialogoError(ResourceBundle.getBundle("language").getString("mensaje.error.rellenar.campos"));
                }

        });
        botCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               dispose();
            }
        });
    }
}
