package com.berjooj.model;

import java.util.ArrayList;
import java.util.Date;

public class ContaCorrente {
    private double saldo;
    private int agencia;
    private int numero;
    private Date dataAbertura;
    private String senha;
    private Pessoa cliente;
    private ArrayList<Transacao> transacoes;

    public ContaCorrente() {
    }

    public ContaCorrente(String senha, Pessoa cliente) {
        this.agencia = 1;
        this.numero = 0;
        this.senha = senha;
        this.saldo = 0;
        this.dataAbertura = new Date(System.currentTimeMillis());
        this.transacoes = new ArrayList<Transacao>();
        this.cliente = cliente;
    }

    public Pessoa getCliente() {
        return this.cliente;
    }

    private boolean podeSacar(double valor) {
        return this.saldo - valor >= 0;
    }

    private boolean podeTransferir(double valor) {
        return this.saldo - valor >= 0;
    }

    public boolean sacar(double valor) {
        if (this.podeSacar(valor)) {
            this.saldo -= valor;

            this.transacoes.add(new Saque(valor));

            return true;
        }

        return false;
    }

    public boolean depositar(double valor) {
        this.saldo += valor;

        this.transacoes.add(new Deposito(valor));

        return true;
    }

    public boolean transferir(double valor, ContaCorrente contaDestino) {
        if (this.podeTransferir(valor)) {
            this.sacar(valor);
            contaDestino.depositar(valor);

            this.transacoes.add(new Transferencia(valor, contaDestino));

            return true;
        }

        return false;
    }

    public ArrayList<Transacao> getTransacoes() {
        return this.transacoes;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}