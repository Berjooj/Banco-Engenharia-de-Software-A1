package com.berjooj.model;

public class Transferencia extends Transacao {

    private ContaCorrente contaDestino;

    public Transferencia(double valor, ContaCorrente contaDestino) {
        super(valor);
        this.contaDestino = contaDestino;
    }

    public ContaCorrente getContaDestino() {
        return this.contaDestino;
    }

    @Override
    public String toString() {
        return "Transferencia: R$" + String.format("%.2f", this.getValor()) + " em " + this.getDataHoraFormatado() + " para "
                + this.getContaDestino().getCliente().getNome();
    }
}