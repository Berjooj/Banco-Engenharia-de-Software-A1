package com.berjooj.dao;

import java.util.ArrayList;

import com.berjooj.model.Banco;
import com.berjooj.repositorio.RepositorioBanco;
import com.google.gson.JsonArray;

public class BancoDAO extends ConectorDAO {
    private RepositorioBanco repositorio;

    public BancoDAO() {
        this.caminhoArquivo = "src/com/berjooj/resources";
        this.nomeArquivo = "bancos.json";
        this.repositorio = RepositorioBanco.getInstance();

        this.carregaArquivo();
    }

    @Override
    public void montarRepositorio() {
        if (this.json != null) {
            for (int i = 0; i < this.json.size(); i++) {
                Banco banco = new Banco();

                banco.setBacen(this.json.get(i).getAsJsonObject().get("bacen").getAsInt());
                banco.setNome(this.json.get(i).getAsJsonObject().get("nome").getAsString());

                repositorio.addBanco(banco);
            }
        }
    }

    @Override
    public void seedDatabase() {
        for (int i = 1; i <= 10; i++) {
            Banco banco = new Banco("Banco " + i, i);

            this.repositorio.addBanco(banco);
        }

        this.atualizarArquivo(json.toString());
    }
}