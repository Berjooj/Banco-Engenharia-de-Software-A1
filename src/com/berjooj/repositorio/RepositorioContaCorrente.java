package com.berjooj.repositorio;

import java.util.ArrayList;

import com.berjooj.model.ContaCorrente;

public class RepositorioContaCorrente {
    private ArrayList<ContaCorrente> contasCorrentes;

    private static RepositorioContaCorrente instance;

    private RepositorioContaCorrente() {
        this.contasCorrentes = new ArrayList<ContaCorrente>();
    }

    public static RepositorioContaCorrente getInstance() {
        if (instance == null) {
            instance = new RepositorioContaCorrente();
        }

        return instance;
    }

    public void carregarPessoas() {
    }

    public void addContaCorrente(ContaCorrente contaCorrente) {
        this.contasCorrentes.add(contaCorrente);
    }

    public ContaCorrente getContaCorrente(int agencia, int numero) {
        for (ContaCorrente contaCorrente : this.contasCorrentes) {
            if (contaCorrente.getAgencia() == agencia && contaCorrente.getNumero() == numero) {
                return contaCorrente;
            }
        }

        return null;
    }
}