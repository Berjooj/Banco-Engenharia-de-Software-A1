package com.berjooj.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Transacao {
    private Date dataHora;
    private double valor;

    public Transacao(double valor) {
        this.dataHora = new Date();
        this.valor = valor;
    }

    public Date getDataHora() {
        return this.dataHora;
    }

    public String getDataHoraFormatado() {
        return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(this.dataHora);
    }

    public double getValor() {
        return this.valor;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}