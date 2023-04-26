package com.berjooj.repositorio;

import java.util.ArrayList;

import com.berjooj.ControladorSistema;
import com.berjooj.dao.BancoDAO;
import com.berjooj.model.Banco;

public class RepositorioBanco {

    private ArrayList<Banco> bancos;

    private static RepositorioBanco instance;

    private BancoDAO bancoDAO;

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
        this.bancoDAO.montarRepositorio();
    }

    public Banco getBanco(int bacen) {
        for (Banco banco : this.bancos) {
            if (banco.getBacen() == bacen) {
                return banco;
            }
        }

        return null;
    }

    public void exit() {
        this.bancoDAO.atualizarArquivo(ControladorSistema.gson.toJson(this.bancos));
    }
}