package com.berjooj;

import com.berjooj.model.ContaCorrente;

public class ATM {

    private static ATM instance;

    private int bacen;

    private ATM(int bacen) {
        this.bacen = bacen;
    }

    private ATM() {
    }

    public static ATM getInstance(int bacen) {
        if (instance == null) {
            instance = new ATM(bacen);
        }

        return instance;
    }

    public static ATM getInstance() {
        if (instance == null) {
            throw new RuntimeException("ATM não inicializado!");
        }

        if (instance.getBacen() == 0) {
            throw new RuntimeException("Banco do ATM não informado!");
        }

        return instance;
    }

    public int getBacen() {
        return this.bacen;
    }

    public void telaBoasVindas() {
        this.limparTela();

        System.out.println("-----------------------------");
        System.out.println("|                           |");
        System.out.println("|                           |");
        System.out.println("|                           |");
        System.out.println("|        Bem-vindo ao       |");
        System.out.println("|      Banco Rendimento     |");
        System.out.println("|       acima de 9000       |");
        System.out.println("|                           |");
        System.out.println("|                           |");
        System.out.println("-----------------------------");
    }

    public void telaOpcoes(boolean isLogado) {
        this.limparTela();

        if (isLogado) {
            this.telaOpcoesLogado();
        } else {
            this.telaOpcoesNaoLogado();
        }
    }

    private void telaOpcoesNaoLogado() {
        System.out.println("-----------------------------");
        System.out.println("|                           |");
        System.out.println("|                           |");
        System.out.println("| 1 - Abrir conta           |");
        System.out.println("| 2 - Acessar conta         |");
        System.out.println("| 3 - Encerrar              |");
        System.out.println("|                           |");
        System.out.println("-----------------------------");
    }

    private void telaOpcoesLogado() {
        ContaCorrente conta = OperacaoBanco.getInstance().getContaOperacao();

        System.out.println("-----------------------------");
        System.out.println("| Ola, " + conta.getCliente().getNome() + "         |");
        System.out.println("| Ag/Num: " + String.format("%03d", conta.getAgencia()) + "/"
                + String.format("%03d", conta.getNumero()) + "           |");
        System.out.println("| Saldo: R$" + String.format("%-17.2f", conta.getSaldo()) + " |");
        System.out.println("|                           |");
        System.out.println("| 1 - Depositar             |");
        System.out.println("| 2 - Sacar                 |");
        System.out.println("| 3 - Transferir            |");
        System.out.println("| 4 - Extrato               |");
        System.out.println("| 5 - Sair                  |");
        System.out.println("|                           |");
        System.out.println("-----------------------------");

    }

    public void telaLogin() {
        this.limparTela();

        System.out.println("-----------------------------");
        System.out.println("|                           |");
        System.out.println("|                           |");
        System.out.println("|   Para acessar o sistem   |");
        System.out.println("|      informe o numero     |");
        System.out.println("|         da conta          |");
        System.out.println("|                           |");
        System.out.println("|                           |");
        System.out.println("-----------------------------");
    }

    public void telaDeposito() {
        this.limparTela();

        System.out.println("-----------------------------");
        System.out.println("|                           |");
        System.out.println("|                           |");
        System.out.println("|        Digite o valor     |");
        System.out.println("|          a depositar:     |");
        System.out.println("|                           |");
        System.out.println("|                           |");
        System.out.println("-----------------------------");
    }

    public void telaSaque() {
        this.limparTela();

        System.out.println("-----------------------------");
        System.out.println("|                           |");
        System.out.println("|                           |");
        System.out.println("|        Digite o valor     |");
        System.out.println("|          a sacar:         |");
        System.out.println("|                           |");
        System.out.println("|                           |");
        System.out.println("-----------------------------");
    }

    public void telaTransferencia() {
        this.limparTela();

        System.out.println("-----------------------------");
        System.out.println("|                           |");
        System.out.println("|                           |");
        System.out.println("|        Digite o valor     |");
        System.out.println("|          a transferir:    |");
        System.out.println("|                           |");
        System.out.println("|                           |");
        System.out.println("-----------------------------");
    }

    public void telaAbrirConta() {
        this.limparTela();

        System.out.println("-----------------------------");
        System.out.println("|                           |");
        System.out.println("|                           |");
        System.out.println("|      Informe os dados     |");
        System.out.println("|       para abrir uma      |");
        System.out.println("|          conta:           |");
        System.out.println("|                           |");
        System.out.println("-----------------------------");
    }

    private void limparTela() {
        System.out.println("\033[H\033[2J");
    }
}