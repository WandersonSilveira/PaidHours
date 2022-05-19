package com.example.paidhours.entidade;

import java.io.Serializable;

public class Usuario implements Serializable {

    private Integer codigoLogin;
    private String login;
    private String senha;

    public Usuario(Integer codigoLogin, String login, String senha) {
        this.codigoLogin = codigoLogin;
        this.login = login;
        this.senha = senha;
    }

    public Integer getcodigoLogin() {
        return codigoLogin;
    }

    public void setcodigoLogin(Integer codigoLogin) {
        this.codigoLogin = codigoLogin;
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
