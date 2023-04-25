package com.berjooj.model;

public class Saque extends Transacao {

    public Saque(double valor) {
        super(valor);
    }

    @Override
    public String toString() {
        return "Saque: R$" + String.format("%.2f", this.getValor()) + " em " + this.getDataHoraFormatado();
    }
}