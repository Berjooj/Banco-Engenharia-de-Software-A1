package com.berjooj.model;

import java.util.HashMap;
import java.util.Map;

public class Banco {
    private String nome;
    private int bacen;

    private Map<Integer, Map<Integer, ContaCorrente>> contas;

    public Banco() {
        this.contas = new HashMap<Integer, Map<Integer, ContaCorrente>>();
    }

    public Banco(String nome, int bacen) {
        this.nome = nome;
        this.bacen = bacen;
        this.contas = new HashMap<Integer, Map<Integer, ContaCorrente>>();
    }

    public boolean adicionarConta(ContaCorrente conta) {
        try {
            if (this.contas.containsKey(conta.getAgencia())) {
                Map<Integer, ContaCorrente> contaAgencia = this.contas.get(conta.getAgencia());

                if (conta.getNumero() == 0) {
                    conta.setNumero(contaAgencia.size());
                } else if (contaAgencia.containsKey(conta.getNumero())) {
                    return false;
                }

                contaAgencia.put(conta.getNumero(), conta);
                this.contas.put(conta.getAgencia(), contaAgencia);
            } else {
                Map<Integer, ContaCorrente> contaAgencia = new HashMap<Integer, ContaCorrente>();

                contaAgencia.put(conta.getNumero(), conta);

                this.contas.put(conta.getAgencia(), contaAgencia);
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public ContaCorrente getConta(int agencia, int numero) {
        if (this.contas.containsKey(agencia)) {
            Map<Integer, ContaCorrente> contaAgencia = this.contas.get(agencia);

            if (contaAgencia.containsKey(numero)) {
                return contaAgencia.get(numero);
            }
        }

        return null;
    }

    public ContaCorrente getContaByDocumentoCliente(String documentoCliente) {
        for (Map<Integer, ContaCorrente> contaAgencia : this.contas.values()) {
            for (ContaCorrente conta : contaAgencia.values()) {
                if (conta.getCliente().getDocumento().equals(documentoCliente)) {
                    return conta;
                }
            }
        }

        return null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getBacen() {
        return bacen;
    }

    public void setBacen(int bacen) {
        this.bacen = bacen;
    }

    @Override
    public String toString() {
        return "{" + " nome='" + getNome() + "'" + ", bacen='" + getBacen() + "'" + "}";
    }
}