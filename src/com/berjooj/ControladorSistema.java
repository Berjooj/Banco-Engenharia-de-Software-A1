package com.berjooj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.berjooj.dao.BancoDAO;
import com.berjooj.model.Banco;
import com.berjooj.repositorio.RepositorioBanco;
import com.berjooj.repositorio.RepositorioPessoa;
import com.google.gson.Gson;

public class ControladorSistema {

    private ATM atm;
    private OperacaoBanco operacaoBanco;
    public static final BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    private RepositorioBanco repoBanco;

    public static Gson gson;

    public void init(int bacenBanco) {
        this.repoBanco = RepositorioBanco.getInstance();

        ControladorSistema.gson = new Gson();

        this.carregaBancos();

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
                System.out.println("Pressione ENTER para continuar...");
                ControladorSistema.bf.readLine();
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
                } else {
                    System.out.println("Pressione ENTER para continuar...");
                    ControladorSistema.bf.readLine();
                }

                break;
            case 3:
                System.out.println("Até breve, volte sempre!");
                System.out.println("Pressione ENTER para continuar...");
                ControladorSistema.bf.readLine();

                this.exit(0);
                break;
            default:
                System.out.println("Opção inválida!");
                System.out.println("Pressione ENTER para continuar...");
                ControladorSistema.bf.readLine();
                break;
        }
    }

    private void exit(int status) {
        this.repoBanco.exit();
        System.exit(status);
    }

    private void carregaBancos() {
        this.repoBanco.montarRepositorio();
    }
}