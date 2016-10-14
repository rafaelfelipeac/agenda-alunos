package com.example.rafae.agenda.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rafae on 10/10/2016.
 */

public class Prova implements Serializable {

    private String materia;
    private String data;
    private String conteudos;
    private long id;


    public String getConteudos() {
        return conteudos;
    }

    public void setConteudos(String conteudos) {
        this.conteudos = conteudos;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString()
    {
        return getMateria();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
