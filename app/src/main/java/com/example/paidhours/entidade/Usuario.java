package com.example.paidhours.entidade;

import java.io.Serializable;

public class Usuario implements Serializable {

    private Integer codigo;
    private String login;
    private String senha;

    public Usuario(Integer codigoLogin, String login, String senha) {
        this.codigo = codigoLogin;
        this.login = login;
        this.senha = senha;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
