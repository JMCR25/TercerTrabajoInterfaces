package com.company.mvc.gui;

import com.company.base.Autor;
import com.company.base.Manga;
import com.company.base.Revista;
import com.company.dialogos.PantallaCarga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */
public class Vista {
    JFrame frame;
    JPanel panelPrincipal;
    JTabbedPane tabbedPane1;
    JPanel paneManga;
    JPanel paneEscritor;
    JPanel paneRevista;
    JButton botAutorMod;
    JTextField textAutorNom;
    JButton botMangaAdd;
    JButton botMangaMod;
    JTextField textMangaNombre;
    JComboBox<Autor> cbMangaAutor;
    JCheckBox chckbxManga;
    JLabel lblMangaNombre;
    JLabel lblMangaCaps;
    JLabel lblMangaAutor;
    JLabel lblMangaEstado;
    JLabel lblAutorNacimiento;
    JLabel lblAutorApellido;
    JLabel lblAutorNombre;
    JButton botAutorAddManga;
    JButton botAutorDelManga;
    JButton botAutorAdd;
    JButton botAutorDel;
    JButton botMangaDel;
    JLabel imgPortadaManga;
    JLabel imgAutorFoto;
    JList<Manga> listAutorMangas;
    JList<Autor> listAutores;
    JList<Manga> listMangas;
    JTextField textRevistaNombre;
    JComboBox<String> cbRevistaDiaVenta;
    JList<Revista> listRevista;
    JLabel lblRevistaNombre;
    JLabel lblRevistaDiaVenta;
    JLabel lblRevistaPrecio;
    JLabel lblRevistaFecha;
    JList<Manga> listRevistaPublicaiones;
    com.github.lgooddatepicker.components.DatePicker dateRevistaFecha;
    com.github.lgooddatepicker.components.DatePicker dateAutorFecha;
    JButton botCargar;
    JButton botGuardar;
    JButton botConfiguracion;
    JButton botRevistaAdd;
    JButton botRevistaDel;
    JButton botRevistaMod;
    JButton botRevistaAddPublicaiones;
    JButton botRevistaDelPublicaicion;
    JButton botUsuarios;
    JButton botGraficos;
    JTextField textMangaCaps;
    JTextField textAutorId;
    JTextField txtRevistaEuros;
    JTextField txtRevistaCentimos;
    JTextField txtApellidos;
    JButton botAceptarModAutor;
    JButton botImageManga;
    JButton botAceptarModManga;
    JLabel cts;
    JLabel eu;
    JButton botAceptarModRevista;
    JLabel lblAutorNum;
    JButton botGraficas;
    JButton btnInforme;
    JList listMangaAutores;
    JButton botAddAutorManga;
    JButton botDeleteAutorManga;
    JButton btnInformeMangas;
    JButton botPdf;
    ResourceBundle resourceBundle;
    DefaultListModel<Autor> autoresDlm;
    DefaultListModel<Manga>mangasAutoresDlm;
    DefaultListModel<Manga>mangasDlm;
    DefaultListModel<Manga>mangasRevistasDlm;
    DefaultListModel<Revista>revistaDlm;
    DefaultListModel<Autor>mangaAutorDlm;
    DefaultComboBoxModel<Autor>autoresMangaCb;
    DefaultComboBoxModel<String>diaVentaRevistas;
    boolean modificarAutor = false;
    boolean modificarManga =false;
    boolean modificarRevista= false;



    public Vista(){
        new PantallaCarga();
        resourceBundle = ResourceBundle.getBundle("language", Locale.getDefault());
        frame  = new JFrame(resourceBundle.getString("titulo.pricipal"));
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iniciarModelos();
        frame.pack();
        frame.setIconImage( new ImageIcon(getClass().getResource("/portada.png")).getImage());
        addMnemonicos();
        visibilidadCambios();
        frame.setVisible(true);
    }
    /**
     * Este metodo devuelve un booleano con la intencion de decirnos si se puede modificar o no el autor.
     * @return boolean para saber si se puede modificar
     */
    public boolean isModificarAutor() {
        return modificarAutor;
    }

    /**
     * Este metodo devuelve un booleano con la intencion de decirnos si se puede modificar o no el manga.
     * @return boolean para saber si se puede modificar
     */
    public boolean isModificarManga() {
        return modificarManga;
    }

    /**
     * Este metodo devuelve un booleano con la intencion de decirnos si se puede modificar o no la revista.
     * @return boolean para saber si se puede modificar
     */
    public boolean isModificarRevista() {
        return modificarRevista;
    }
    /**
     * Este metodo cambia la visibilidad de los campos de modificacion de autor a true si es false y viceversa
     */
    public void modificarVisibilidadAutor(){
        if (modificarAutor)
            modificarAutor =false;
        else
            modificarAutor =true;
    }

    /**
     * Este metodo cambia la visibilidad de los campos de modificacion de manga a true si es false y viceversa
     */
    public void modificarVisibilidadManga(){
        if (modificarManga)
            modificarManga =false;
        else
            modificarManga =true;
    }

    /**
     * Este metodo cambia la visibilidad de los campos de modificacion de revista a true si es false y viceversa
     */
    public void modificarVisibilidadRevista(){
        if (modificarRevista)
            modificarRevista =false;
        else
            modificarRevista =true;
    }
    /**
     * No devuelve nada
     * Metodo utilizado para determinar la visibilidad de los modificadores por medio de booleanos
     */
    public void visibilidadCambios(){
        txtApellidos.setVisible(modificarAutor);
        textAutorNom.setVisible(modificarAutor);
        textAutorId.setVisible(modificarAutor);
        dateAutorFecha.setVisible(modificarAutor);
        botAutorAddManga.setVisible(modificarAutor);
        botAutorDelManga.setVisible(modificarAutor);
        botAceptarModAutor.setVisible(modificarAutor);
        botImageManga.setVisible(modificarManga);
        textMangaNombre.setVisible(modificarManga);
        textMangaCaps.setVisible(modificarManga);
        chckbxManga.setVisible(modificarManga);
        botAceptarModManga.setVisible(modificarManga);
        textRevistaNombre.setVisible(modificarRevista);
        cbRevistaDiaVenta.setVisible(modificarRevista);
        txtRevistaCentimos.setVisible(modificarRevista);
        txtRevistaEuros.setVisible(modificarRevista);
        dateRevistaFecha.setVisible(modificarRevista);
        botRevistaAddPublicaiones.setVisible(modificarRevista);
        botRevistaDelPublicaicion.setVisible(modificarRevista);
        botAceptarModRevista.setVisible(modificarRevista);
        cts.setVisible(modificarRevista);
        eu.setVisible(modificarRevista);
        botAddAutorManga.setVisible(modificarManga);
        botDeleteAutorManga.setVisible(modificarManga);
    }

    /**
     * No devuelve nada
     * Este metodo añade atajos mnemonicos a todos los botones de la vista principal.
     */
    private void addMnemonicos() {
        //Atajos Mnemonicos del panel general
        botGuardar.getRootPane().setDefaultButton(botGuardar);
        botCargar.setMnemonic(KeyEvent.VK_C);
        botConfiguracion.setMnemonic(KeyEvent.VK_S);
        botGraficos.setMnemonic(KeyEvent.VK_B);
        botGraficas.setMnemonic(KeyEvent.VK_0);
        //Atajos Mnemonicos del panel de autores
        botAutorDel.setMnemonic(KeyEvent.VK_E);
        botAutorAdd.setMnemonic(KeyEvent.VK_A);
        botAutorMod.setMnemonic(KeyEvent.VK_G);
        botAutorDelManga.setMnemonic(KeyEvent.VK_D);
        botAutorAddManga.setMnemonic(KeyEvent.VK_H);
        //Atajos Mnemonicos del panel de Mangas
        botMangaAdd.setMnemonic(KeyEvent.VK_F);
        botMangaDel.setMnemonic(KeyEvent.VK_L);
        botMangaMod.setMnemonic(KeyEvent.VK_V);
        //Atajos Mnemonicos del panel de revistas
        botRevistaAdd.setMnemonic(KeyEvent.VK_X);
        botRevistaDel.setMnemonic(KeyEvent.VK_Z);
        botRevistaMod.setMnemonic(KeyEvent.VK_Q);
        botRevistaDelPublicaicion.setMnemonic(KeyEvent.VK_R);
        botRevistaAddPublicaiones.setMnemonic(KeyEvent.VK_T);
        btnInforme.setMnemonic(KeyEvent.VK_1);
        btnInformeMangas.setMnemonic(KeyEvent.VK_2);
        botPdf.setMnemonic(KeyEvent.VK_8);
    }

    /**
     * No devuelve nada
     * Metodo que inicializa los modelos para las listas y combobox
     */
    private void iniciarModelos() {
        autoresDlm = new DefaultListModel<>();
        mangasAutoresDlm = new DefaultListModel<>();
        mangasDlm = new DefaultListModel<>();
        mangasRevistasDlm = new DefaultListModel<>();
        revistaDlm = new DefaultListModel<>();
        mangaAutorDlm = new DefaultListModel<>();
        autoresMangaCb = new DefaultComboBoxModel<>();
        diaVentaRevistas = new DefaultComboBoxModel<>();
        //Introducimos los dias de la semana internacionalizados
        diaVentaRevistas.addElement(resourceBundle.getString("objeto.revista.diasdelasemana.lunes"));
        diaVentaRevistas.addElement(resourceBundle.getString("objeto.revista.diasdelasemana.martes"));
        diaVentaRevistas.addElement(resourceBundle.getString("objeto.revista.diasdelasemana.miercoles"));
        diaVentaRevistas.addElement(resourceBundle.getString("objeto.revista.diasdelasemana.jueves"));
        diaVentaRevistas.addElement(resourceBundle.getString("objeto.revista.diasdelasemana.viernes"));
        diaVentaRevistas.addElement(resourceBundle.getString("objeto.revista.diasdelasemana.sabado"));
        diaVentaRevistas.addElement(resourceBundle.getString("objeto.revista.diasdelasemana.domingo"));
        listAutores.setModel(autoresDlm);
        listAutorMangas.setModel(mangasAutoresDlm);
        listMangas.setModel(mangasDlm);
        listRevista.setModel(revistaDlm);
        listRevistaPublicaiones.setModel(mangasRevistasDlm);
        listMangaAutores.setModel(mangaAutorDlm);
        cbRevistaDiaVenta.setModel(diaVentaRevistas);
    }
}
