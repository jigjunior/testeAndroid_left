package com.avanade.santander.testeandroid.contato.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Formulario {

    @SerializedName("cells")
    @Expose
    private List<Cell> cells = null;

    public Formulario(List<Cell> cells) {
        this.cells = cells;
    }

    public List<Cell> getFormulario() {
        return cells;
    }
}
