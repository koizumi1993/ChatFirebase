package com.fundamentos.abisu.chatfirebase.Clases;

public class Mensaje {
    private String mensaje;
    private String fotoMensaje;
    private String nombre;
    private String fotoPerfil;
    private String tipoMensaje;

    public Mensaje() {
    }

    public Mensaje(String mensaje, String nombre, String fotoPerfil, String tipoMensaje) {
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.tipoMensaje = tipoMensaje;
    }

    public Mensaje(String mensaje, String fotoMensaje, String nombre, String fotoPerfil, String tipoMensaje) {
        this.mensaje = mensaje;
        this.fotoMensaje = fotoMensaje;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.tipoMensaje = tipoMensaje;
    }

    public String getFotoMensaje() {
        return fotoMensaje;
    }

    public void setFotoMensaje(String fotoMensaje) {
        this.fotoMensaje = fotoMensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

}
