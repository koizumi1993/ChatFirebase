package com.fundamentos.abisu.chatfirebase.Clases;

public class RecibirMensaje extends Mensaje {
    private Long hora;

    public RecibirMensaje() {
    }

    public RecibirMensaje(Long hora) {
        this.hora = hora;
    }

    public RecibirMensaje(String mensaje, String fotoMensaje, String nombre, String fotoPerfil, String tipoMensaje, Long hora) {
        super(mensaje, fotoMensaje, nombre, fotoPerfil, tipoMensaje);
        this.hora = hora;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }
}
