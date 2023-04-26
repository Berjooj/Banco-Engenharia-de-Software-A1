package com.berjooj.repositorio;

import java.util.ArrayList;

import com.berjooj.ControladorSistema;
import com.berjooj.dao.BancoDAO;
import com.berjooj.dao.ConectorDAO;
import com.berjooj.model.Banco;

public class RepositorioBanco {

    private ArrayList<Banco> bancos;

    private static RepositorioBanco instance;

    private ConectorDAO bancoDAO;

    private RepositorioBanco() {
        this.bancos = new ArrayList<Banco>();
        this.bancoDAO = new BancoDAO();
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

    public void montarRepositorio() {
        for (int i = 0; i < 10; i++) {
            this.addBanco(new Banco("Banco " + i, i));
        }

        this.bancoDAO.montarRepositorio();
        System.out.println(ControladorSistema.gson.toJson(this.bancos.get(0)));
        System.exit(99);

        System.out.println(ControladorSistema.gson.toJson(this.bancos));
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