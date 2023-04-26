package com.berjooj.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.berjooj.ControladorSistema;
import com.google.gson.JsonArray;

public abstract class ConectorDAO {
    protected String caminhoArquivo;
    protected String nomeArquivo;

    protected JsonArray json;

    public ConectorDAO() {
    }

    protected void carregaArquivo() {
        try {
            String caminhoCompleto = this.caminhoArquivo + "/" + this.nomeArquivo;
            File arquivo = new File(caminhoCompleto);

            if (!arquivo.exists()) {
                arquivo.createNewFile();
                this.seedDatabase();
            }

            String conteudo = new String(Files.readAllBytes(Paths.get(caminhoCompleto)));

            if (conteudo.isEmpty()) {
                this.json = null;
            } else {
                this.json = ControladorSistema.gson.fromJson(conteudo, JsonArray.class);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Erro ao carregar arquivo");
        }
    }

    public void montarRepositorio() {
        // função que monta o repositório com os dados do arquivo json
    }

    public void seedDatabase() {
        // função que popula o arquivo json com dados iniciais
    }

    public void atualizarArquivo(String gsonString) {
        try {
            String caminhoCompleto = this.caminhoArquivo + "/" + this.nomeArquivo;

            File arquivoBase = new File(caminhoCompleto);

            if (!arquivoBase.delete()) {
                System.out.println("Erro ao deletar arquivo");
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoCompleto));

            writer.write(gsonString);

            writer.close();

        } catch (Exception e) {
            System.out.println("Erro ao atualizar arquivo");
        }
    }
}