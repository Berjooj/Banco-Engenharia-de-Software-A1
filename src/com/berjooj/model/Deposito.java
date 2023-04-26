package com.berjooj.model;

import java.util.Date;

public class Deposito extends Transacao {

    public Deposito(double valor) {
        super(valor);
    }

    public Deposito(Date dataHora, double valor) {
        super(dataHora, valor);
    }

    @Override
    public String toString() {
        return "Deposito: R$" + String.format("%.2f", this.getValor()) + " em " + this.getDataHoraFormatado();
    }
}