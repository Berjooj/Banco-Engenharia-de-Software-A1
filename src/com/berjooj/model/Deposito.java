package com.berjooj.model;

public class Deposito extends Transacao {

    public Deposito(double valor) {
        super(valor);
    }

    @Override
    public String toString() {
        return "Deposito: R$" + String.format("%.2f", this.getValor()) + " em " + this.getDataHoraFormatado();
    }
}