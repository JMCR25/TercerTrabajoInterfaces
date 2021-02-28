package com.company.mvc.modelo;

import com.company.base.*;
import com.company.util.Util;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */
public class Modelo implements Serializable{
    private ArrayList<Manga>mangas;
    private ArrayList<Autor>autores;
    private ArrayList<Revista>revistas;
    private ResourceBundle resourceBundle;
    public Modelo() {
        resourceBundle = resourceBundle.getBundle("language");
        this.mangas = new ArrayList<>();
        this.autores = new ArrayList<>();
        this.revistas = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String date = "01/01/0001";
        LocalDate localDate = LocalDate.parse(date, formatter);
        Autor anonimo =new Autor("Anonimo",0,localDate,new ArrayList<Manga>(),"Anonimo");
        ArrayList<Manga> mangs = new ArrayList<>();
        anonimo.setPublicaciones(mangs);
        autores.add(anonimo);
    }
    /**
     * La funcion de este metodo es la de devolvernos
     * el listado de mangas
     * @return ArrayList
     */
    public ArrayList<Manga> getMangas() {
        return mangas;
    }

    /**
     * La funcion de este metodo es la de devolvernos
     * el listado de autores
     * @return ArrayList
     */
    public ArrayList<Autor> getAutores() {
        return autores;
    }

    /**
     * La funcion de este metodo es la de devolvernos
     * el listado de revista
     * @return ArrayList
     */
    public ArrayList<Revista> getRevistas() {
        return revistas;
    }
    /**
     * No devuelve ningun valor
     * La funcion de este metodo es la de añadir autores.
     * Si el id esta repetido nos mostrara un mendsaje de error
     * @param autor recoge el objeto {@code Autor} para añadirlo a nuestra lista de autores
     */
    public void addAutores(Autor autor){
        boolean repetido = false;
        for (Autor aut : autores){
            if(aut.getIdAutor()==autor.getIdAutor()){
                repetido=true;
            }
        }
        if(repetido)
            Util.mostrarDialogoError(resourceBundle.getString("mensaje.error.autorrepetido"));
        else
            autores.add(autor);
    }
    /**
     * No devuelve ningun valor
     * La funcion de este metodo es la de añadir mangas.
     * Si el nombre esta repetido nos mostrara un mendsaje de error
     * @param manga recoge el objeto {@code Manga} para añadirlo a nuestra lista de mangas
     */
    public void addManga(Manga manga){
        boolean sePuede = true;
        for(Manga mang : mangas){
            if (mang.getNombre().equals(manga.getNombre())) {
                Util.mostrarDialogoError(resourceBundle.getString("mensaje.error.mangarepetido"));
                sePuede=false;
            }
        }
        if(sePuede) {
            mangas.add(manga);
            ArrayList<Autor> autors = new ArrayList<>();
            int conta = 0;
            for (Autor autor : autores) {
                for(int aut :  manga.getAutor()) {
                    if (autor.getIdAutor() ==aut)
                        autor.addPublicacion(manga);
                }
                autors.add(autor);
            }

            autores = autors;
        }


    }
    /**
     * No devuelve ningun valor
     * La funcion de este metodo es la de añadir un manga a un autor concreto.
     * @param autor recoge el objeto {@code int} el cual al ser unico por autor sirve para seleccionar un autor concreto
     * @param manga recoge el objeto {@code Manga} para añadirlo al autor que se identifica con el parametro autor
     */
    public void addAutorManga(int autor, Manga manga){
        ArrayList<Manga> mangs = new ArrayList<>();
        ArrayList<Autor>autors=new ArrayList<>();
        manga.addAutor(autor);
        for(Autor autor1: autores){
            if(autor1.getIdAutor()==autor)
                autor1.addPublicacion(manga);
            else{
                mangs = new ArrayList<>();
                for (Manga manga1:autor1.getPublicaciones()){

                        mangs.add(manga1);
                }
                autor1.setPublicaciones(mangs);
            }
            autors.add(autor1);
        }
        autores=autors;
        mangs= new ArrayList<>();
        for(Manga mang:mangas){
            if (!mang.getNombre().equals(manga.getNombre()))
                mangs.add(mang);
        }
        mangs.add(manga);
        mangas=mangs;
    }
    /**
     * No devuelve ningun valor
     * La funcion de este metodo es la de añadir autores.
     * Si el id esta repetido nos mostrara un mendsaje de error
     * @param revista recoge el objeto {@code Autor} para añadirlo a nuestra lista de autores
     */
    public void addRevista(Revista revista){
        boolean repetido = true;
        for(Revista revist:revistas){
            if(revist.getNombre().equals(revista.getNombre()))
                repetido=false;
        }
        if (repetido)
            revistas.add(revista);
        else
            Util.mostrarDialogoError(resourceBundle.getString("mensaje.error.revistarepetido"));
    }
    /**
     * No devuelve ningun valor
     * Metodo para eliminar autor previamente ha sido seleccionada
     * @param autor recoge el objeto {@code Autor} que se desea eliminar
     */
    public void eliminarAutor(Autor autor){
        Autor anonimo = autores.get(0);
        int conta = autores.size();
        for (int i = 0; i!=conta;i++){
            Autor autorM= autores.get(i);
            if(autorM==autor){
                int cont2=0;
                for (Manga manga: mangas) {
                    int tamanyo = manga.getAutor().size();
                    for (int j = 0; j>=tamanyo; j++) {
                        if (manga.getAutor().get(j) == autor.getIdAutor()) {
                             if (manga.getAutor().size()==0){
                                manga.addAutor(0);
                                anonimo.addPublicacion(manga);
                                mangas.set(cont2, manga);
                             }
                        }
                    }
                    cont2++;
                }
                autores.remove(i);
            }
        }
    }
    /**
     * No devuelve ningun valor
     * Metodo para la carga de datos
     * Abre un buscador de archivos en el que podremos seleccionar un archivo de datos
     * para transmitirlos a la aplicación
     * @param file recibe un objeto de tipo {@code File}
     * @throws IOException lanza excepcion de entrada/salida
     * @throws ClassNotFoundException lanza excepcion cuando la clase en cuestion no es encontrada
     */
    public void cargarDatos(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream deserializador = new ObjectInputStream(fis);
        autores = (ArrayList<Autor>)deserializador.readObject();
        mangas = (ArrayList<Manga>)deserializador.readObject();
        revistas=(ArrayList<Revista>)deserializador.readObject();


        deserializador.close();
    }
    /**
     * No devuelve ningun valor
     * Metodo para la carga de datos
     * Abre un buscador de archivos en el que podremos guardar un archivo con los datos de la aplicación
     * y el nombre que nosotros queramos
     * @param file recibe un objeto de tipo {@code File}
     * @throws IOException lanza excepcion de entrada/salida
    */
    public void guardarDatos(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream serializador = new ObjectOutputStream(fos);
        serializador.writeObject(autores);
        serializador.writeObject(mangas);
        serializador.writeObject(revistas);
        serializador.close();
    }
    /**
     * No devuelve ningun valor
     * Metodo para eLiminar la publicacion seleccionada en la lista de publicaciones del autor
     * @param autor recoge el objeto {@code Autor} del que se desea eliminar manga
     * @param manga recoge el objeto {@code Manga} que se desea eliminar
     */
    public void eliminarMangaAutor(Autor autor, Manga manga){
        autor.getPublicaciones().remove(manga);
        manga.getAutor().remove(autor.getIdAutor());

    }

    /**
     * No devuelve ningun valor
     * Añade publicaciones a la revista pasada por parametro y la remplaza por la misma
     * en la lista de revistas
     * @param revista recoge el objeto {@code Revista} que se desea reemplazar
     */
    public void addMangaRevista(Revista revista){
        ArrayList<Revista>revists=new ArrayList<>();
        for (Revista revista1:revistas){
            if(revista1.getNombre().equals(revista.getNombre()))
                revists.add(revista);
            else
                revists.add(revista1);
        }

    }

    /**
     * No devuelve ningun valor
     * Metodo para eliminar publicacion de la revista la cual previamente ha sido seleccionada
     * @param revista recoge el objeto {@code Revista} del que se desea eliminar manga
     * @param manga recoge el objeto {@code Manga} que se desea eliminar
     */
    public void eliminarRevistaManga(Revista revista, Manga manga){
        for(Revista revista1:revistas){
            if(revista==revista1)
                revista1.getPublicaciones().remove(manga);
        }
    }

    /**
     * No devuelve ningun valor
     * Elimina el manga seleccionado en la lista de mangas y por ende en los autores y revistas pertinentes
     * @param manga recoge el objeto {@code Manga} que se desea eliminar
     */
    public void eliminarManga(Manga manga){
        mangas.remove(manga);

        for(Autor autor : autores){
            autor.getPublicaciones().remove(manga);
        }
        for(Revista revista : revistas){
            revista.getPublicaciones().remove(manga);
        }

    }
    /**
     * No devuelve ningun valor
     * Metodo para eliminar la revista seleccionada pasada por parametro
     * @param revista recoge el objeto {@code Revista} que se desea eliminar
     */
    public void eliminarRevista(Revista revista){
        revistas.remove(revista);
    }
    /**
     * No devuelve ningun valor
     * Metodo para modificar el autor seleccionado obteniendo el id anterior y el autor cambiado
     * @param autor recoge el objeto {@code int} que es el id del autor a modificar
     * @param mod recoge el objeto {@code Autor} que es el autor modificado
     */

    public void modAutor(int autor,Autor mod){
        for (Autor aut : autores){
            if (autor==aut.getIdAutor()){
                aut.setPublicaciones(mod.getPublicaciones());
                aut.setNombre(mod.getNombre());
                aut.setApellido(mod.getApellido());
                aut.setIdAutor(mod.getIdAutor());
                aut.setFechaNac(mod.getFechaNac());
            }
        }
        for (Manga manga : mangas){
            manga.removeAutor(autor);
            manga.addAutor(mod.getIdAutor());
        }
    }
    /**
     * No devuelve ningun valor
     * La funcion de este metodo es el de modificar los parametros
     * del manga seleccionado reemplazando el original por el
     * modificado
     * @param manga recoge el objeto {@code Manga} original a modificar
     * @param mod recoge el objeto {@code Manga} modificado
     */
    public void modManga(Manga manga, Manga mod){
       mangas.remove(manga);
       mangas.add(mod);
       for(Autor autor : autores){
           if (autor.getPublicaciones().remove(manga))
               autor.addPublicacion(mod);
       }
        for(Revista revista : revistas){
            if (revista.getPublicaciones().remove(manga))
                revista.addPublicacion(mod);
        }

        }

    /**
     * No devuelve ningun valor
     * La funcion de este metodo es el de modificar los parametros
     * de la revista seleccionada reemplazando la original por la
     * modificada
     * @param revista recoge el objeto {@code String} que es el nombre de la revista a modificar
     * @param mod recoge el objeto {@code Revista} modificado
     */
    public void modRevista(String revista,Revista mod){
        ArrayList<Revista>revs=new ArrayList<>();
        for (Revista revis : revistas){
            if(!revis.getNombre().equals(revista))
                revs.add(revis);
            else
                revs.add(mod);
        }
        revistas=revs;
    }
    /**
     * No devuelve ningun valor
     * Metodo para desasociar manga de autor
     * @param manga recoge el objeto {@code Manga} que es el manga que queremos desasociar
     * @param autor recoge el objeto {@code int} que es el identificador del autor
     */
    public void eliminarMangaAutor(Manga manga, int autor){
        for (Autor auto: autores){
            if(autor==auto.getIdAutor())
                auto.getPublicaciones().remove(manga);
        }
    }
    public ArrayList<EntradaInforme> obtenerEntradasInforme(){
        ArrayList<EntradaInforme> ei =new ArrayList<>();
        for (Revista revista : getRevistas()){
            for(Manga manga : revista.getPublicaciones()){
                ei.add(new EntradaInforme(manga, revista));
            }
        }
        return ei;
    }

}
