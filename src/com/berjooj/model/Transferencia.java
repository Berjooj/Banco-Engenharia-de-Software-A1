package com.berjooj.model;

import java.util.Date;

public class Transferencia extends Transacao {

    private ContaCorrente contaDestino;

    public Transferencia(Date dataHora, double valor, ContaCorrente contaDestino) {
        super(dataHora, valor);
        this.contaDestino = contaDestino;
    }

    public Transferencia(double valor, ContaCorrente contaDestino) {
        super(valor);
        this.contaDestino = contaDestino;
    }

    public ContaCorrente getContaDestino() {
        return this.contaDestino;
    }

    @Override
    public String toString() {
        return "Transferencia: R$" + String.format("%.2f", this.getValor()) + " em " + this.getDataHoraFormatado()
                + " para "
                + this.getContaDestino().getCliente().getNome();
    }
}