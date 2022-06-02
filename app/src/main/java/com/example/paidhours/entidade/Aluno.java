package com.example.paidhours.entidade;

import java.io.Serializable;

public class Aluno implements Serializable {
    private Integer codigo;
    private String nome;
    private Integer matricula;
    private Boolean status;

    public Aluno(){

    }

    public Aluno(Integer codigo, String nome, Integer matricula, Boolean status) {
        this.codigo = codigo;
        this.nome = nome;
        this.matricula = matricula;
        this.status = status;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
