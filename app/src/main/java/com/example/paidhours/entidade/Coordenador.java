package com.example.paidhours.entidade;

import java.io.Serializable;

public class Coordenador extends Usuario implements Serializable {

    private Integer codigo;
    private String nome;
    private String email;

    public Coordenador(Integer codigoLogin, String login, String senha, Integer codigo, String nome, String email) {
        super(codigoLogin, login, senha);
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
