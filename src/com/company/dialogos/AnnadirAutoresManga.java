package com.company.dialogos;

import com.company.base.Autor;
import com.company.base.Manga;
import com.company.mvc.modelo.Modelo;
import com.company.util.Util;
import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */
public class AnnadirAutoresManga extends JDialog{
    private JList listMangas;
    private JButton botAddManga;
    private JButton buttonOK;
    private JPanel contentPane;
    private DefaultListModel<Autor> dlmAutores;
    private ResourceBundle resourceBundle;
    private ArrayList<Autor> autors;


    public AnnadirAutoresManga(Manga manga, Modelo modelo) {
        resourceBundle= ResourceBundle.getBundle("language", Locale.getDefault());
        getRootPane().setDefaultButton(botAddManga);
        setContentPane(contentPane);
        autors = (ArrayList<Autor>) modelo.getAutores().clone();
        dlmAutores = new DefaultListModel<>();
        listMangas.setModel(dlmAutores);
        listarAutores(manga);
        getRootPane().setDefaultButton(buttonOK);
        setModal(true);
        pack();
        botAddManga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(listMangas.getSelectedValue()!=null){
                    ArrayList<Autor> auto = (ArrayList<Autor>)listMangas.getSelectedValuesList();
                    for (Autor aut : auto){
                        modelo.addAutorManga(aut.getIdAutor(), manga);
                    }
                    listarAutores(manga);
                }
                else
                   Util.mostrarDialogoError(resourceBundle.getString("mensaje.error.seleccion.autor"));
            }
        });
        /**
         * No devuelve nada
         * Añade funcionalidad y listeners al boton de ok.
         * Cierra la ventana.
         */
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
    /**
     * No devuelve nada
     * Añade funcionalidad y listeners a los botones y lista de la ventana.
     * @param manga objeto de tipo {@code Manga} lista los autores que todavia no tiene manga y los muestra el la lista.
     *
     */

    private void listarAutores(Manga manga) {
        dlmAutores.clear();
        ArrayList<Integer> i = new ArrayList<>();
            for(int auto : manga.getAutor()) {
                int cont = 0;
                for (Autor aut : autors) {
                    if (auto == aut.getIdAutor())
                        i.add(cont);
                    cont++;
                }
            }
        int resta = 0;
        for (int j : i){
            autors.remove(j-resta);
            resta++;
        }
        for (Autor aut : autors) {
            dlmAutores.addElement(aut);
        }
}}
