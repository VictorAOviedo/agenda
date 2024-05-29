package com.vao.agenda.dto;

import java.util.List;

public class ReservaDTO {
    private String local;
    private String tratamiento;
    private List<String> fechaHora;



    // Constructor, getters y setters
    public ReservaDTO() {}

    public ReservaDTO(String local, String tratamiento, List<String> fechaHora) {
        this.local = local;
        this.tratamiento = tratamiento;
        this.fechaHora = fechaHora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public List<String> getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(List<String> fechaHora) {
        this.fechaHora = fechaHora;
    }
}


