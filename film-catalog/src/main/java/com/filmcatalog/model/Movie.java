package com.filmcatalog.model;

public class Movie {

    private String id;
    private String titulo;
    private String video;
    private String thumb;
    private String detalhes;
    private String tipo;

    public Movie() {
    }

    public Movie(String id, String titulo, String video, String thumb, String detalhes, String tipo) {
        this.id = id;
        this.titulo = titulo;
        this.video = video;
        this.thumb = thumb;
        this.detalhes = detalhes;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
