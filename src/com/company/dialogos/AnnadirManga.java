package com.company.dialogos;

import com.company.base.Autor;
import com.company.base.Manga;
import com.company.mvc.gui.Controlador;
import com.company.mvc.modelo.Modelo;
import com.company.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.jar.Manifest;
/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */
public class AnnadirManga extends JDialog{
    private JTextField textNombreManga;
    private JRadioButton publicadoseRadioButton;
    private JRadioButton finalizadoRadioButton;
    private JButton botImageManga;
    private JButton botCancel;
    private JPanel panelAnnadirManga;
    private JButton botAddManga;
    private JLabel lblPortada;
    private JTextField textMangaCap;
    private Image image;
    private DefaultComboBoxModel<Autor>autoresMangaCb;
    private Image imagen;

    public AnnadirManga(Modelo modelo, Controlador controlador) {
        setContentPane(panelAnnadirManga);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        getRootPane().setDefaultButton(botAddManga);
        botCancel.setMnemonic(KeyEvent.VK_ESCAPE);
        botImageManga.setMnemonic(KeyEvent.VK_Q);
        addKeyListener();
        int height = pantalla.height;
        int width = pantalla.width;
        //he tenido que ajustar la posicion de la ventana ya que por defecto se colocaba en la esquina superior izquierda
        setSize(width/4, height/4);
        setLocationRelativeTo(null);
        setModal(true);
        setModels(modelo, controlador);
        imagen =null;
        publicadoseRadioButton.setSelected(true);
        pack();
        setVisible(true);

    }
    /**
     * No devuelve nada
     * Añade un listener de las teclas pulsadas al cuadro de texto de los capitulos.
     */
    private void addKeyListener() {
        textMangaCap.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try {
                    int num=Integer.parseInt(textMangaCap.getText());
                    if (num<=1)
                        Integer.parseInt("error");
                } catch (Exception ex) {
                    textMangaCap.setText("1");
                }
            }
        });
    }

    /**
     * Ejecuta el metodo addActionListener
     * @param modelo recibido para ejecutar la clase addActionListeners
     * @param controlador
     */
    private void setModels(Modelo modelo, Controlador controlador) {
        addActionListeners(modelo,controlador);
    }


    /**
     * No devuelve nada
     * Añade funcionalidad y listeners a los botones y lista de la ventana.
     * @param modelo objeto de tipo {@code Modelo} utilizado para realizar operaciones de añadido a la lista de autores.
     * @param controlador objeto de tipo {@code Controlador} utilizado para listar.
     *
     */
    private void addActionListeners(Modelo modelo, Controlador controlador) {
        botCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        botAddManga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textNombreManga.getText();
                int caps = Integer.parseInt(textMangaCap.getText());
                boolean estado = publicadoseRadioButton.isSelected();
                int numAutor = 0;
                if(imagen==null){
                    URL enlace =getClass().getResource("/portada_defecto.jpg");
                    ImageIcon imageIcon = new ImageIcon(enlace);
                    imagen = imageIcon.getImage();
                }
                if(!nombre.replace(" ","").equals("")){
                Manga manga = new Manga(nombre,caps,estado,imagen);
                manga.addAutor(0);
                modelo.addManga(manga);
                controlador.listarMangas();
                controlador.listarAutores();
                dispose();}
                else
                    Util.mostrarDialogoError(ResourceBundle.getBundle("language").getString("mensaje.error.nombremanga"));
            }
        });
        botImageManga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                File foto=null;
                String enlace = null;
                try {
                     foto = chooser.getSelectedFile();
                     enlace =foto.getPath();
                } catch (NullPointerException ne){
                }
                int ancho = 70;
                int alto = 110;
                if (enlace==null)
                    enlace ="images/defecto/portada_defecto.jpg";
                ImageIcon imageIcon = new ImageIcon(enlace);
                imagen = imageIcon.getImage();
                Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(imagenEscalada);
                lblPortada.setIcon(imageIcon);
                }
        });
    }
}
