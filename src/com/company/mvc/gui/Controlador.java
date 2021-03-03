package com.company.mvc.gui;

import com.company.base.Autor;
import com.company.base.Manga;
import com.company.base.Revista;
import com.company.dialogos.*;
import com.company.mvc.modelo.Modelo;
import com.company.util.Util;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */

public class Controlador implements ActionListener, KeyListener, ListSelectionListener {
    private Vista vista;
    private Modelo modelo;
    private ResourceBundle resourceBundle;
    private Autor autorSeleccionado;
    private ArrayList<Autor>mangaAutorSeleccionado;
    private ArrayList<Manga> autorMangaSeleccionado;
    private Manga mangaSeleccionado;
    private Revista revistaSeleccionada;
    private ArrayList<Manga> revistaMangaSeleccionado;
    private Image imagen;

    /**
     * Constructor de la clase {@code Controlador}
     * Clase que controla las acciones en la ventana de {@code Vista}
     * @param vista recibe el objeto de tipo {@code Vista} sobre el cual se va trabajar
     * @param modelo recibe el objeto de tipo {@code Modelo} que sirve para manipular los datos de los autores, mangas y revistas
     */
    public Controlador(Vista vista, Modelo modelo) {
        autorSeleccionado = null;
        autorMangaSeleccionado = new ArrayList<>();
        mangaSeleccionado = null;
        revistaSeleccionada = null;
        mangaAutorSeleccionado=null;
        imagen = null;
        resourceBundle = ResourceBundle.getBundle("language", Locale.getDefault());
        this.modelo = modelo;
        this.vista = vista;
        addActionListener(this);
        addListSelectionListener(this);
        addKeyListener(this);
        listarAutores();
        refrescarComboBoxMangaAutores();
    }
    /**
     * Añade los listeners a los botones de la clase vista
     * @param listener Recibimos el listener de tipo {@code ActionListener} que le vamos a dar a los botones
     */
    private void addActionListener(ActionListener listener) {
        vista.botAutorAdd.addActionListener(listener);
        vista.botAutorDel.addActionListener(listener);
        vista.botAutorMod.addActionListener(listener);
        vista.botRevistaAdd.addActionListener(listener);
        vista.botRevistaDel.addActionListener(listener);
        vista.botRevistaMod.addActionListener(listener);
        vista.botMangaAdd.addActionListener(listener);
        vista.botMangaMod.addActionListener(listener);
        vista.botMangaDel.addActionListener(listener);
        vista.botAutorAddManga.addActionListener(listener);
        vista.botAutorDelManga.addActionListener(listener);
        vista.botRevistaAddPublicaiones.addActionListener(listener);
        vista.botRevistaDelPublicaicion.addActionListener(listener);
        vista.botConfiguracion.addActionListener(listener);
        vista.botGuardar.addActionListener(listener);
        vista.botCargar.addActionListener(listener);
        vista.botAceptarModAutor.addActionListener(listener);
        vista.botImageManga.addActionListener(listener);
        vista.botAceptarModManga.addActionListener(listener);
        vista.chckbxManga.addActionListener(listener);
        vista.botGraficos.addActionListener(listener);
        vista.botGraficas.addActionListener(listener);
        vista.btnInforme.addActionListener(listener);
        vista.botAddAutorManga.addActionListener(listener);
        vista.botDeleteAutorManga.addActionListener(listener);
        vista.btnInformeMangas.addActionListener(listener);
        vista.botAceptarModRevista.addActionListener(listener);
        vista.botPdf.addActionListener(listener);
    }
    /**
     * Añade los listeners a las listas de la clase vista
     * @param listener Recibimos el listener de tipo {@code ListSelectionListener} que le vamos a dar a las listas
     */
    private void addListSelectionListener(ListSelectionListener listener) {
        vista.listAutores.addListSelectionListener(listener);
        vista.listRevista.addListSelectionListener(listener);
        vista.listMangas.addListSelectionListener(listener);
        vista.listRevistaPublicaiones.addListSelectionListener(listener);
        vista.listAutorMangas.addListSelectionListener(listener);
        vista.listMangaAutores.addListSelectionListener(listener);
    }
    /**
     * Añade los listeners a campos de texto de la clase vista
     * @param listener Recibimos el listener de tipo {@code KeyListener} que le vamos a dar a los campos de texto
     */
    private void addKeyListener(KeyListener listener) {
        vista.textMangaCaps.addKeyListener(listener);
        vista.txtRevistaEuros.addKeyListener(listener);
        vista.txtRevistaCentimos.addKeyListener(listener);
        vista.textAutorId.addKeyListener(listener);
    }
    /**
     * Determina las acciones a llevar a cabo cuando recibe la clave que le proporciona el {@code ActionListener}
     * @param e es un objeto de la clase {@code ActionEvent} que recibe el listener de los botones
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String accion = e.getActionCommand();
        try {
            switch (accion) {
                case "guardar": {
                    guardarDatos();
                    break;
                }
                case "cargar": {
                    cargarDatos();
                    break;
                }
                case "settings": {
                    new Configuracion();
                    break;
                }
                case "graficas": {
                    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                    ArrayList<Autor> autores = modelo.getAutores();
                    for (Autor autor : autores) {
                        dataset.setValue(autor.getPublicaciones().size(), "Mangas", autor.getNombre());
                    }
                    JFreeChart diagrama = ChartFactory.createBarChart(resourceBundle.getString("grafica.mangas.autor"), resourceBundle.getString("objeto.principal.autor"), "", dataset, PlotOrientation.VERTICAL, true, true, false);
                    ChartFrame ventana = new ChartFrame("", diagrama);
                    ventana.pack();
                    ventana.setVisible(true);
                    break;
                }
                case "graficos": {
                    if (modelo.getMangas().size() > 0) {
                        DefaultPieDataset dataset = new DefaultPieDataset();
                        for (Manga manga : modelo.getMangas()) {
                            dataset.setValue(manga.getNombre(), manga.getCapitulos());
                        }
                        JFreeChart diagrama = ChartFactory.createPieChart(resourceBundle.getString("graficas.mangas.caps"), dataset, false, true, false);
                        ChartFrame ventana = new ChartFrame("", diagrama);
                        ventana.pack();
                        ventana.setVisible(true);
                    } else
                        Util.mostrarDialogoError(resourceBundle.getString("error.mangas"));
                    break;
                }
                case "annadirAutor": {
                    new AnnadirAutor(modelo, this).setModal(true);
                    break;
                }
                case "eliminarAutor": {
                    eliminarAutor();
                    autorSeleccionado = null;
                    break;
                }
                case "modAutor": {
                    if (autorSeleccionado != null) {
                        visibilidadAutor();
                    } else
                        Util.mostrarDialogoError(resourceBundle.getString("mensaje.error.seleccion.autor"));
                    break;
                }
                case "annadirAutorManga": {
                    AnnadirMangasAutores an = new AnnadirMangasAutores(autorSeleccionado, modelo);
                    break;
                }
                case "eliminarAutorManga": {
                    if (autorSeleccionado.getIdAutor() != 0)
                        for(Manga autorMangaSeleccionad:autorMangaSeleccionado)
                            modelo.eliminarMangaAutor(autorSeleccionado, autorMangaSeleccionad);
                    break;
                }
                case "annadirManga": {
                    new AnnadirManga(modelo, this);
                    break;
                }
                case "eliminarManga": {
                    Manga manga = mangaSeleccionado;
                    if (manga != null) {
                        modelo.eliminarManga(manga);
                        mangaSeleccionado = null;
                        vista.mangaAutorDlm.clear();
                    }
                    listarMangas();
                    break;
                }
                case "modManga": {
                    if (mangaSeleccionado != null) {
                        visibilidadManga();
                    } else
                        Util.mostrarDialogoError(resourceBundle.getString("mensaje.error.seleccion.manga"));
                    break;
                }
                case "annadirRevista": {
                    new AnnadirRevista(modelo, this);
                    break;
                }
                case "modRevista": {
                    if (revistaSeleccionada != null) {
                        visibilidadRevista();
                    } else
                        Util.mostrarDialogoError(resourceBundle.getString("mensaje.error.seleccion.revista"));
                    break;
                }
                case "delRevista": {
                    eliminarRevista();
                    break;
                }
                case "addPublicacionesRevista": {
                    if (revistaSeleccionada != null)
                        new AnnadirMangasRevista(revistaSeleccionada, modelo);
                    break;
                }
                case "delPublicacionesRevista": {
                    if (revistaMangaSeleccionado.size()!=0)
                        for(Manga manga : revistaMangaSeleccionado)
                            modelo.eliminarRevistaManga(revistaSeleccionada, manga);
                    else
                        Util.mostrarDialogoError("error");
                    break;
                }
                case "aceptarCambiosAutor": {
                    modAutor();
                    visibilidadAutor();
                    break;
                }

                case "aceptarCambiosManga": {
                    modManga();
                    visibilidadManga();
                    break;
                }

                case "aceptarCambiosRevista": {
                    modRevista();
                    visibilidadRevista();
                    break;
                }
                case "img": {
                    addImageManga();
                    break;
                }
                case "informes": {
                    mostrarInforme();
                    break;
                }
                case "informesManga": {
                    mostrarInformeManga();
                    break;
                }
                case "delAutorManga": {
                    for (Autor mangaAutorSelect: mangaAutorSeleccionado)
                        modelo.eliminarMangaAutor(mangaAutorSelect,mangaSeleccionado);
                    break;
                }
                case "addAutorManga": {
                    new AnnadirAutoresManga(mangaSeleccionado, modelo);
                    break;
                }
                case "pdf": {
                    SwingController controller = new SwingController();
                    SwingViewBuilder factory = new SwingViewBuilder(controller);
                    JPanel viewerPanel = factory.buildViewerPanel();
                    JFrame frame = new JFrame("Visor Pdf");
                    frame.setContentPane(viewerPanel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    controller.openDocument(getClass().getResource("/manual_de_uso.pdf"));
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                    break;
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        listarAutores();
        listarRevistas();
        listarMangas();

        limpiarCampos();
        visibilidad();

        refrescarComboBoxMangaAutores();
    }
    /**
     * No devuelve nada
     * Este metodo nos muestra un informe de los mangas que estan dentro de alguna revista mostrando la portada, el nombre, los capitulos y si esta finalizado,
     * esto del manga, de la revista asociada nos muestra el nombre y el precio
     * @throws JRException si hay algun problema interno del jasper report lanzara la excepcion
     */
    private void mostrarInformeManga() throws JRException {
        JasperReport informe = (JasperReport) JRLoader.loadObject(new File("datos/mangas.jasper"));
        JRBeanCollectionDataSource coleccion = new JRBeanCollectionDataSource(modelo.obtenerEntradasInforme());

        JasperPrint printer = JasperFillManager.fillReport(informe, null, coleccion);
        JasperViewer.viewReport(printer, false);
    }


    /**
     * No devuelve nada
     * Este metodo nos muestra un informe de autores jumto con el numero de mangas que tiene asociados y una grafica de lo mismo
     * @throws JRException si hay algun problema interno del jasper report lanzara la excepcion
     */
    private void mostrarInforme() throws JRException {
        JasperReport informe = (JasperReport) JRLoader.loadObject(getClass().getResource("/autores.jasper"));
        JRBeanCollectionDataSource coleccion = new JRBeanCollectionDataSource(modelo.getAutores());

        JasperPrint printer = JasperFillManager.fillReport(informe, null, coleccion);
        JasperViewer.viewReport(printer, false);

    }
    /**
     * No devuelve ningun valor
     * Metodo que recoge la modificacion de la revista seleccionada e invoca el metodo
     * de la clase {@code Modelo} para la realizacion de la modificacion pasandole los
     * parametros de tipo {@code String} para recoger el nombre de la revista antigua
     * y el parametro de tipo {@code Revista} para reemplazarlo por la perteneciente al nombre
     */
    private void modRevista() {
        String nombre = revistaSeleccionada.getNombre();
        float precio = revistaSeleccionada.getPrecio();
        int diaDeVenta = revistaSeleccionada.getDiaDeVenta();
        ArrayList<Manga> publicaciones = revistaSeleccionada.getPublicaciones();
        LocalDate dia = revistaSeleccionada.getPrimeraEdicion();
        if (!vista.textRevistaNombre.getText().replace(" ", "").equals(""))
            nombre = vista.textRevistaNombre.getText();
        diaDeVenta = vista.cbRevistaDiaVenta.getSelectedIndex();
        precio = Float.parseFloat(vista.txtRevistaEuros.getText()) + Float.parseFloat(vista.txtRevistaCentimos.getText()) / 100;
        Revista revista = new Revista(precio, nombre, diaDeVenta, publicaciones, dia);
        modelo.modRevista(revistaSeleccionada.getNombre(), revista);
        //este bucle esta creado para evitar mla repeticion en los nombres a la hora de modificar
        for (Revista revistas : modelo.getRevistas()) {
            if (revistas.getNombre().equals(revistaSeleccionada.getNombre())) ;
            else if (revistas.getNombre().equals(nombre)) {
                nombre = revistaSeleccionada.getNombre();
                Util.mostrarDialogoError(resourceBundle.getString("mensaje.error.modnom.revista"));
            }
        }
    }

    /**
     * No devuelve ningun valor
     * Metodo que tiene la utilidad de cambiar la visibilidad de los elementos de modificacion
     * de la clase {@code Vista}. Solo se pueden volver visible si anteriormente hemos seleccionado un
     * objeto de la lista correspondiente.
     */
    private void visibilidad() {
        if (revistaSeleccionada == null && vista.isModificarRevista())
            visibilidadRevista();
        if (autorSeleccionado == null && vista.isModificarAutor())
            visibilidadAutor();
        if (mangaSeleccionado == null && vista.isModificarManga())
            visibilidadManga();
    }
    /**
     * No devuelve ningun valor
     * Metodo que tiene la utilidad de cambiar la visibilidad de los elementos de modificacion
     * de la clase {@code Vista} en concreto de la seccion de autores.
     */
    private void visibilidadAutor() {
        vista.modificarVisibilidadAutor();
        vista.visibilidadCambios();
    }

    /**
     * No devuelve ningun valor
     * Metodo que tiene la utilidad de cambiar la visibilidad de los elementos de modificacion
     * de la clase {@code Vista} en concreto de la seccion de revistas.
     */
    private void visibilidadRevista() {
        vista.modificarVisibilidadRevista();
        vista.visibilidadCambios();
    }

    /**
     * No devuelve ningun valor
     * Metodo que tiene la utilidad de cambiar la visibilidad de los elementos de modificacion
     * de la clase {@code Vista} en concreto de la seccion de mangas.
     */
    private void visibilidadManga() {
        vista.modificarVisibilidadManga();
        vista.visibilidadCambios();
    }

    /**
     * No devuelve ningun valor
     * Metodo que recoge la modificacion del manga seleccionado e invoca el metodo
     * de la clase {@code Modelo} para la realizacion de la modificacion pasandole dos
     * parametros de tipo {@code Manga} para recoger el manga a modificar y el manga modificado
     * que va a reemplazarlo.
     */
    private void modManga() {
        String nombre = mangaSeleccionado.getNombre();
        int caps = mangaSeleccionado.getCapitulos();
        Image img = mangaSeleccionado.getPortada().getImage();
        boolean estado = mangaSeleccionado.isFinalizado();
        if (vista.chckbxManga.isSelected())
            estado = false;
        else
            estado = true;
        if (!vista.textMangaNombre.getText().replace(" ", "").equals(""))
            nombre = vista.textMangaNombre.getText();
        if (Integer.parseInt(vista.textMangaCaps.getText()) != 0)
            caps = Integer.parseInt(vista.textMangaCaps.getText());
        if (imagen != null)
            img = imagen;
        for (Manga mangas : modelo.getMangas()) {
            if (mangas.getNombre().equals(mangaSeleccionado.getNombre())) ;
            else if (mangas.getNombre().equals(nombre)) {
                nombre = mangaSeleccionado.getNombre();
                Util.mostrarDialogoError(resourceBundle.getString("mensaje.error.modnom"));
            }
        }
        Manga manga = new Manga(nombre, caps, estado, img);
        manga.getAutor().addAll(mangaSeleccionado.getAutor());
        Manga anterior = mangaSeleccionado;
        modelo.modManga(anterior, manga);
        mangaSeleccionado = null;
        imagen=null;
    }
    /**
     * No devuelve ningun valor
     * Metodo que abre un buscador de archivos el cual tiene la funcion de buscar una imagen
     * para modificar la del manga seleccionado
     */
    private void addImageManga() {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File foto = null;
        String enlace = null;
        try {
            foto = chooser.getSelectedFile();
            enlace = foto.getPath();
        } catch (NullPointerException ne) {
        }
        int ancho = 140;
        int alto = 220;
        ImageIcon imageIcon=null;
        if (enlace == null){
            imageIcon = new ImageIcon(getClass().getResource("/portada_defecto"));}
        else
            imageIcon = new ImageIcon(enlace);
        imagen=null;
        imagen = imageIcon.getImage();
        Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(imagenEscalada);
        vista.imgPortadaManga.setIcon(imageIcon);
    }

    /**
     * No devuelve ningun valor
     * Metodo para vaciar los campos de las ventanas cuando se borre el elemento seleccionado
     */
    private void limpiarCampos() {
        if (autorSeleccionado == null) {
            vista.lblAutorNacimiento.setText("");
            vista.lblAutorNombre.setText("");
            vista.lblAutorNum.setText("");
            vista.lblAutorApellido.setText("");
            vista.mangasAutoresDlm.removeAllElements();
        }
        if (mangaSeleccionado == null) {
            vista.lblMangaEstado.setText("");
            vista.lblMangaNombre.setText("");
            vista.lblMangaAutor.setText("");
            vista.lblMangaCaps.setText("");
            vista.imgPortadaManga.setIcon(null);
        }
        if (revistaSeleccionada == null) {
            vista.lblRevistaDiaVenta.setText("");
            vista.lblRevistaPrecio.setText("");
            vista.lblRevistaNombre.setText("");
            vista.lblRevistaFecha.setText("");
            vista.mangasAutoresDlm.removeAllElements();
        }
    }

    /**
     * No devuelve ningun valor
     * Metodo para eliminar revistas, si no hay revista seleccionada mostrara mensaje de error
     */
    private void eliminarRevista() {
        if (revistaSeleccionada != null) {
            modelo.eliminarRevista(revistaSeleccionada);
            revistaSeleccionada = null;
        } else {
            Util.mostrarDialogoError(resourceBundle.getString("mensaje.error.seleccion.revista"));
        }
    }

    /**
     * No devuelve ningun valor
     * Metodo para modificar autor, los campos vacios no se modificaran, modificara los mangas relacionados a su par
     */
    private void modAutor() {
        String nombre = autorSeleccionado.getNombre();
        int id = autorSeleccionado.getIdAutor();
        String apellido = autorSeleccionado.getApellido();
        LocalDate nac = autorSeleccionado.getFechaNac();
        ArrayList<Manga> mangas = autorSeleccionado.getPublicaciones();
        if (!vista.textAutorNom.getText().replace(" ", "").equals(""))
            nombre = vista.textAutorNom.getText();
        if (!vista.txtApellidos.getText().replace(" ", "").equals(""))
            apellido = vista.txtApellidos.getText();
        if (vista.dateAutorFecha.getDate() != null)
            nac = vista.dateAutorFecha.getDate();
        if (!vista.textAutorId.equals(""))
            id = Integer.parseInt(vista.textAutorId.getText());
        for (Autor autores : modelo.getAutores()) {
            if (autores.getIdAutor() == autorSeleccionado.getIdAutor()) ;
            else if (autores.getIdAutor() == id) {
                id = autorSeleccionado.getIdAutor();
                Util.mostrarDialogoError(resourceBundle.getString("mensaje.error.modid"));
            }
        }
        Autor autor = new Autor(nombre, id, nac, mangas, apellido);
        if (autorSeleccionado.getIdAutor() == 0)
            Util.mostrarDialogoError(resourceBundle.getString("error.mensaje.mod"));
        else
            modelo.modAutor(autorSeleccionado.getIdAutor(), autor);
        vista.textAutorId.setText("1");
        vista.textAutorNom.setText("");
        vista.dateAutorFecha.setDate(null);
        vista.txtApellidos.setText("");
    }
    /**
     * No devuelve ningun valor
     * Metodo que abre un dialogo para seleccionar el archivo de datos que deseamos cargar en nuestra app
     * y le pasa el fichero al metodo cargarDatos de la clase {@Modelo}.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void cargarDatos() throws IOException, ClassNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        int opt = fileChooser.showOpenDialog(vista.frame);
        if (opt == JFileChooser.APPROVE_OPTION) {
            modelo.cargarDatos(fileChooser.getSelectedFile());
        }
    }
    /**
     * No devuelve ningun valor
     * Metodo que tiene la utilidad
     * @deprecated
     */
    private void refrescarComboBoxMangaAutores() {
        vista.autoresMangaCb.removeAllElements();
        for (Autor autor : modelo.getAutores()) {
            vista.autoresMangaCb.addElement(autor);
        }
    }
    /**
     * No devuelve ningun valor
     * Metodo que obtiene el autor a eliminar y por medio de del metodo de mismo nombre de la clase {@Modelo}
     * se elimina
     */
    private void eliminarAutor() {
        Autor autor = autorSeleccionado;
        if (autor != null && !autor.getNombre().toLowerCase().equals("anonimo")) {
            int option = Util.mostrarDialogoConfirmacion(resourceBundle.getString("confirmacion.mensaje.eliminarautor") + autor.getNombre() + " " + autor.getApellido() + "?");
            if (option == Util.ACEPTAR) {
                modelo.eliminarAutor(autor);
            }
        } else
            Util.mostrarDialogoError(resourceBundle.getString("error.mensaje.intentoeliminar"));
    }
    /**
     * No devuelve ningun valor
     * Metodo que se usa para actualizar la lista de autores al invocarse
     */
    public void listarAutores() {
        vista.autoresDlm.clear();
        ArrayList<Autor> autores = modelo.getAutores();
        for (Autor autor : autores) {
            vista.autoresDlm.addElement(autor);
        }
    }

    /**
     * No devuelve ningun valor
     * Metodo que se usa para actualizar la lista de mangas al invocarse
     */
    public void listarMangas() {
        vista.mangasDlm.clear();
        ArrayList<Manga> mangas = modelo.getMangas();
        for (Manga manga : mangas) {
            vista.mangasDlm.addElement(manga);
        }
    }

    /**
     * No devuelve ningun valor
     * Metodo que se usa para actualizar la lista de revistas al invocarse
     */
    public void listarRevistas() {
        vista.revistaDlm.clear();
        ArrayList<Revista> revistas = modelo.getRevistas();
        for (Revista revista : revistas) {
            vista.revistaDlm.addElement(revista);
        }
    }

    /**
     * No devuelve ningun valor
     * Metodo que se usa para guardar en un fichero los datos de la aplicacion
     * @throws IOException
     */
    private void guardarDatos() throws IOException{
        JFileChooser fileChooser = new JFileChooser();
        int opt = fileChooser.showSaveDialog(vista.frame);
        if (opt == JFileChooser.APPROVE_OPTION) {
            modelo.guardarDatos(fileChooser.getSelectedFile());
        }
    }

    /**
     * Sin Uso
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }
    /**
     * Sin Uso
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * No devuelve nada
     * Este metodo se activa en el momento que al pulsar la tecla la soltamos
     * @param e este objeto recibe nuestras acciones en el objeto que tenga el listener
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.textMangaCaps) {
            try {
                int num = Integer.parseInt(vista.textMangaCaps.getText());
                if (num <= 1)
                    Integer.parseInt("error");
            } catch (Exception ex) {
                vista.textMangaCaps.setText("1");
            }
        }
        if (e.getSource() == vista.txtRevistaEuros) {
            try {
                int num = Integer.parseInt(vista.txtRevistaEuros.getText());
                if (num <= 0)
                    Integer.parseInt("error");
            } catch (Exception ex) {
                vista.txtRevistaEuros.setText("1");
            }
        }
        if (e.getSource() == vista.txtRevistaCentimos) {
            try {
                int num = Integer.parseInt(vista.txtRevistaCentimos.getText());
                if (num > 100)
                    Integer.parseInt("error");
            } catch (Exception ex) {
                vista.txtRevistaCentimos.setText("1");
            }
        }
        if (e.getSource() == vista.textAutorId) {
            try {
                int num = Integer.parseInt(vista.textAutorId.getText());
                if (num <= 0)
                    Integer.parseInt("error");
            } catch (Exception ex) {
                vista.textAutorId.setText("1");
            }
        }
    }

    /**
     * No devuelve nada
     * Este metodo sirve para saber que lista estamos seleccionando
     * @param e recibe un {@code ListSelectionEvent} el cual reacciona al pulsar sobre algun elemento de la lista que tenga listener
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (vista.autoresDlm.size() > 0 && !vista.listAutores.isSelectionEmpty()) {
            int index = vista.listAutores.getSelectedIndex();
            autorSeleccionado = (Autor) vista.autoresDlm.getElementAt(index);
            vista.listAutores.clearSelection();
            vista.lblAutorNombre.setText(autorSeleccionado.getNombre());
            vista.lblAutorApellido.setText(autorSeleccionado.getApellido());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            LocalDate localDate = autorSeleccionado.getFechaNac();
            String fechaNacimiento = localDate.format(formatter);
            vista.lblAutorNacimiento.setText(fechaNacimiento);
            vista.lblAutorNum.setText(String.valueOf(autorSeleccionado.getIdAutor()));
            vista.mangasAutoresDlm.clear();
            for (Manga manga : autorSeleccionado.getPublicaciones()) {
                vista.mangasAutoresDlm.addElement(manga);
            }
        }
        if (vista.mangasAutoresDlm.size() > 0 && !vista.listAutorMangas.isSelectionEmpty()) {
            autorMangaSeleccionado = (ArrayList<Manga>) vista.listAutorMangas.getSelectedValuesList();
        }
        if (vista.mangasRevistasDlm.size() > 0 && !vista.listRevistaPublicaiones.isSelectionEmpty()) {
            revistaMangaSeleccionado = (ArrayList<Manga>)vista.listRevistaPublicaiones.getSelectedValuesList();
        }
        if (vista.mangasDlm.size() > 0 && !vista.listMangas.isSelectionEmpty()) {
            mangaSeleccionado = vista.listMangas.getSelectedValue();
            vista.listMangas.clearSelection();
            vista.lblMangaNombre.setText(mangaSeleccionado.getNombre());
            vista.lblMangaCaps.setText(String.valueOf(mangaSeleccionado.getCapitulos()));
            vista.mangasAutoresDlm.clear();
            ArrayList<Autor> autores =new ArrayList<>();
            for(int aut : mangaSeleccionado.getAutor()){
                for (Autor au : modelo.getAutores()){
                    if(au.getIdAutor()==aut)
                        autores.add(au);
                }
            }
            vista.mangaAutorDlm.clear();
            for (Autor autor : autores) {
                vista.mangaAutorDlm.addElement(autor);
            }
            String estado = "";
            ResourceBundle resourceBundle = ResourceBundle.getBundle("language");
            if (mangaSeleccionado.isFinalizado())
                estado = resourceBundle.getString("objeto.manga.estado.seleccion");
            else
                estado = resourceBundle.getString("objeto.manga.estado.seleccion2");
            vista.lblMangaEstado.setText(estado);
            Image imagen = mangaSeleccionado.getPortada().getImage().getScaledInstance(140, 220, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(imagen);
            vista.imgPortadaManga.setIcon(imageIcon);
        }

        if (vista.mangaAutorDlm.size() > 0 && !vista.listMangaAutores.isSelectionEmpty()) {
            mangaAutorSeleccionado = (ArrayList<Autor>) vista.listMangaAutores.getSelectedValuesList();
        }


        if (vista.revistaDlm.size() > 0 && !vista.listRevista.isSelectionEmpty()) {
            int index = vista.listRevista.getSelectedIndex();
            revistaSeleccionada = (Revista) vista.revistaDlm.getElementAt(index);
            vista.listRevista.clearSelection();
            vista.lblRevistaNombre.setText(revistaSeleccionada.getNombre());
            vista.lblRevistaPrecio.setText(String.valueOf(revistaSeleccionada.getPrecio()) + "€");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            vista.lblRevistaFecha.setText(revistaSeleccionada.getPrimeraEdicion().format(formatter));
            int dia = revistaSeleccionada.getDiaDeVenta();
            String dias = "";
            switch (dia) {
                case 1: {
                    dias = resourceBundle.getString("objeto.revista.diasdelasemana.lunes");
                    break;
                }
                case 2: {
                    dias = resourceBundle.getString("objeto.revista.diasdelasemana.martes");
                    break;
                }
                case 3: {
                    dias = resourceBundle.getString("objeto.revista.diasdelasemana.miercoles");
                    break;
                }
                case 4: {
                    dias = resourceBundle.getString("objeto.revista.diasdelasemana.jueves");
                    break;
                }
                case 5: {
                    dias = resourceBundle.getString("objeto.revista.diasdelasemana.viernes");
                    break;
                }
                case 6: {
                    dias = resourceBundle.getString("objeto.revista.diasdelasemana.sabado");
                    break;
                }
                case 7: {
                    dias = resourceBundle.getString("objeto.revista.diasdelasemana.domingo");
                    break;
                }
            }
            vista.lblRevistaDiaVenta.setText(dias);
            vista.mangasRevistasDlm.clear();
            for (Manga manga : revistaSeleccionada.getPublicaciones()) {
                vista.mangasRevistasDlm.addElement(manga);
            }
        }
    }
}
