package com.example.cimatec_movie;

import androidx.annotation.NonNull;

public class filme {
    private String Id, nome;
    private int ano, curtida;

    public  filme(){

    }

    public filme(String id, String nome, int ano, int curtida) {
        Id = id;
        this.nome = nome;
        this.ano = ano;
        this.curtida = curtida;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getCurtida() {
        return curtida;
    }

    public void setCurtida(int curtida) {
        this.curtida = curtida;
    }

    @NonNull
    @Override
    public String toString() {
        return " Nome: "+ this.nome +" Ano: "+ this.ano +" Cutida: "+ this.curtida;
    }
}
