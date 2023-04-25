package com.berjooj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.berjooj.model.Banco;
import com.berjooj.model.ContaCorrente;
import com.berjooj.repositorio.RepositorioBanco;
import com.berjooj.repositorio.RepositorioPessoa;

public class ControladorSistema {

    private ATM atm;
    private OperacaoBanco operacaoBanco;
    public static final BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    private RepositorioBanco repoBanco;
    private RepositorioPessoa repoCliente;

    public void init(int bacenBanco) {
        this.repoBanco = RepositorioBanco.getInstance();
        this.repoCliente = RepositorioPessoa.getInstance();

        this.carregaClientes();
        this.carregaBancos();
        this.carregaContas();

        this.atm = ATM.getInstance(bacenBanco);
        this.operacaoBanco = OperacaoBanco.getInstance();
    }

    public void run() {
        try {
            this.atm.telaBoasVindas();

            System.out.println("Pressione ENTER para continuar...");
            ControladorSistema.bf.readLine();

            do {
                this.atm.telaOpcoes(this.operacaoBanco.isLogado());

                System.out.print("Escolha uma opção: ");
                int opcao = Integer.parseInt(ControladorSistema.bf.readLine());

                if (this.operacaoBanco.isLogado()) {
                    this.handleOpcoesComLogin(opcao);
                } else {
                    this.handleOpcoesSemLogin(opcao);
                }
            } while (true);

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
            this.exit(-1);
        }
    }

    private void handleOpcoesComLogin(int opcao) throws IOException {
        switch (opcao) {
            case 1:
                this.operacaoBanco.depositar();
                break;
            case 2:
                this.operacaoBanco.sacar();
                break;
            case 3:
                this.operacaoBanco.transferir();
                break;
            case 4:
            this.operacaoBanco.extrato();
                break;
            case 5:
                System.out.println("Até breve, volte sempre!");
                this.operacaoBanco.actionLogout();

                System.out.println("Pressione ENTER para continuar...");
                ControladorSistema.bf.readLine();
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private void handleOpcoesSemLogin(int opcao) throws IOException {
        switch (opcao) {
            case 1:
                this.atm.telaAbrirConta();

                this.operacaoBanco.actionAbrirConta();

                break;
            case 2:
                this.atm.telaLogin();

                this.operacaoBanco.actionLogin();

                if (this.operacaoBanco.isLogado()) {
                    System.out.println(
                            "Bem vindo, " + this.operacaoBanco.getContaOperacao().getCliente().getNome() + "!");
                    System.out.println("Pressione ENTER para continuar...");
                    ControladorSistema.bf.readLine();
                }

                break;
            case 3:
                System.out.println("Até breve, volte sempre!");

                this.exit(0);
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private void exit(int status) {
        System.exit(status);
    }

    private void carregaClientes() {
    }

    private void carregaBancos() {
        for (int i = 0; i < 10; i++) {
            this.repoBanco.addBanco(new Banco("Banco " + i, i));
        }
    }

    private void carregaContas() {
    }
}