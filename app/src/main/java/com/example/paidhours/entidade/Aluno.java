package com.example.paidhours.entidade;

import java.io.Serializable;

public class Aluno implements Serializable {
    private Integer codigo;
    private String nome;
    private Long matricula;
    private byte[] imagem;
    private Boolean status;
    private Integer horasCurso;
    private Integer horasCertificado;

    public Aluno(){

    }

    public Aluno(Integer codigo, String nome, Long matricula, byte[] imagem, Boolean status, Integer horasCurso, Integer horasCertificado) {
        this.codigo = codigo;
        this.nome = nome;
        this.matricula = matricula;
        this.imagem = imagem;
        this.status = status;
        this.horasCurso = horasCurso;
        this.horasCertificado = horasCertificado;
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

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getHorasCurso() {
        return horasCurso;
    }

    public void setHorasCurso(Integer horasCurso) {
        this.horasCurso = horasCurso;
    }

    public Integer getHorasCertificado() {
        return horasCertificado;
    }

    public void setHorasCertificado(Integer horasCertificado) {
        this.horasCertificado = horasCertificado;
    }
}
