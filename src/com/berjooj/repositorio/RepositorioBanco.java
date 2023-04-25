package com.berjooj.repositorio;

import java.util.ArrayList;

import com.berjooj.model.Banco;

public class RepositorioBanco {

    private ArrayList<Banco> bancos;

    private static RepositorioBanco instance;

    private RepositorioBanco() {
        this.bancos = new ArrayList<Banco>();
    }

    public static RepositorioBanco getInstance() {
        if (instance == null) {
            instance = new RepositorioBanco();
        }

        return instance;
    }

    public void addBanco(Banco banco) {
        this.bancos.add(banco);
    }

    public Banco getBanco(int bacen) {
        for (Banco banco : this.bancos) {
            if (banco.getBacen() == bacen) {
                return banco;
            }
        }

        return null;
    }
}