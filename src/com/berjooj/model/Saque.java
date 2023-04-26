package com.berjooj.model;

import java.util.Date;

public class Saque extends Transacao {

    public Saque(double valor) {
        super(valor);
    }

    public Saque(Date dataHora, double valor) {
        super(dataHora, valor);
    }

    @Override
    public String toString() {
        return "Saque: R$" + String.format("%.2f", Math.abs(this.getValor())) + " em " + this.getDataHoraFormatado();
    }
}