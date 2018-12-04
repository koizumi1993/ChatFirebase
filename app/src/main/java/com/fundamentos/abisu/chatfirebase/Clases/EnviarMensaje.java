package com.fundamentos.abisu.chatfirebase.Clases;

import java.util.Map;

public class EnviarMensaje extends Mensaje{
    private Map hora;

    public EnviarMensaje(Map hora) {
        this.hora=hora;
    }

    public EnviarMensaje(String mensaje, String nombre, String fotoPerfil, String tipoMensaje, Map hora) {
        super(mensaje, nombre, fotoPerfil, tipoMensaje);
        this.hora = hora;
    }

    public EnviarMensaje(String mensaje, String fotoMensaje, String nombre, String fotoPerfil, String tipoMensaje, Map hora) {
        super(mensaje, fotoMensaje, nombre, fotoPerfil, tipoMensaje);
        this.hora = hora;
    }

    public Map getHora() {
        return hora;
    }

    public void setHora(Map hora) {
        this.hora = hora;
    }
}
