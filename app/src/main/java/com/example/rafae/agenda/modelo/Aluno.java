package com.example.rafae.agenda.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by rafae on 04/10/2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Aluno implements Serializable{

    private String id;
    private String nome;
    private String endereco;
    private String telefone;
    private String site;
    private Double nota;
    private String caminhofoto;
    private int desativado;
    private int sincronizado;

    public int getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }

    public int getDesativado() {
        return desativado;
    }

    public void setDesativado(int desativado) {
        this.desativado = desativado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getCaminhoFoto() {
        return caminhofoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhofoto = caminhoFoto;
    }

    @Override
    public String toString() {
        return getNome();
    }

    public boolean isActive() {
        return getDesativado() == 0;
    }

    public void sincroniza() {
        this.sincronizado = 1;
    }

    public void desincroniza() {
        this.sincronizado = 0;
    }

    public void desativa() {
        this.desativado = 1;
        desincroniza();
    }
}
