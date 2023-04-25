package com.berjooj.repositorio;

import java.util.ArrayList;

import com.berjooj.model.Pessoa;

public class RepositorioPessoa {
    private ArrayList<Pessoa> pessoas;

    private static RepositorioPessoa instance;

    private RepositorioPessoa() {
        this.pessoas = new ArrayList<Pessoa>();
    }

    public static RepositorioPessoa getInstance() {
        if (instance == null) {
            instance = new RepositorioPessoa();
        }

        return instance;
    }

    public void carregarPessoas() {
    }

    public void addPessoa(Pessoa pessoa) {
        this.pessoas.add(pessoa);
    }

    public Pessoa getPessoa(String documento) {
        for (Pessoa pessoa : this.pessoas) {
            if (pessoa.getDocumento().equals(documento)) {
                return pessoa;
            }
        }

        return null;
    }
}