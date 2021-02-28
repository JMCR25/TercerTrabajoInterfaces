package com.company.base;
/**
 * @author Jose Maria Cenador Ramirez
 * @version 3.0
 * @since 2021
 */

public class EntradaInforme {
    private Manga manga;
    private Revista revista;
    /**
     * Clase creada con la unica intencion de mostrar el informe de {@code Manga} / {@code Revista}
     * @param manga recibe un manga perteneciente a la revista.
     * @param revista recibe la revista.
     */
    public EntradaInforme(Manga manga, Revista revista) {
        this.manga = manga;
        this.revista = revista;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public Revista getRevista() {
        return revista;
    }

    public void setRevista(Revista revista) {
        this.revista = revista;
    }
}
