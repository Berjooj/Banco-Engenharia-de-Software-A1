package com.berjooj;

import java.io.IOException;

import com.berjooj.model.Banco;
import com.berjooj.model.ContaCorrente;
import com.berjooj.model.Pessoa;
import com.berjooj.model.PessoaFisica;
import com.berjooj.model.PessoaJuridica;
import com.berjooj.model.Transacao;
import com.berjooj.repositorio.RepositorioBanco;
import com.berjooj.repositorio.RepositorioPessoa;

public class OperacaoBanco {

    private ContaCorrente contaOperacao;
    private RepositorioBanco repoBanco;
    private RepositorioPessoa repoCliente;
    private ATM atm;

    private static OperacaoBanco instance;

    private OperacaoBanco() {
        this.contaOperacao = null;
        this.repoCliente = RepositorioPessoa.getInstance();
        this.repoBanco = RepositorioBanco.getInstance();
        this.atm = ATM.getInstance();
    }

    public static OperacaoBanco getInstance() {
        if (OperacaoBanco.instance == null) {
            OperacaoBanco.instance = new OperacaoBanco();
        }

        return OperacaoBanco.instance;
    }

    public void actionAbrirConta() {
        try {
            System.out.print("Informe o tipo de pessoa (1 - Física, 2 - Jurídica): ");

            int tipoPessoa = Integer.parseInt(ControladorSistema.bf.readLine());

            if (tipoPessoa != 1 && tipoPessoa != 2) {
                throw new RuntimeException("Tipo de pessoa inválido!");
            }

            Pessoa pessoa = null;
            Banco banco = this.repoBanco.getBanco(this.atm.getBacen());

            if (tipoPessoa == 1) {
                pessoa = new PessoaFisica();
            } else {
                pessoa = new PessoaJuridica();
            }

            System.out.print("Informe seu " + (tipoPessoa == 1 ? "CPF" : "CNPJ") + ": ");
            pessoa.setDocumento(ControladorSistema.bf.readLine());

            boolean pessoaExiste = false;

            if (this.repoCliente.getPessoa(pessoa.getDocumento()) != null) {
                pessoa = this.repoCliente.getPessoa(pessoa.getDocumento());

                if (banco.getContaByDocumentoCliente(pessoa.getDocumento()) != null) {
                    throw new RuntimeException("Cliente já possui conta no banco!");
                }

                System.out.println("Cadastro já existente, atualizando dados...");
                pessoaExiste = true;
            }

            if (tipoPessoa != 1) {
                System.out.print("Informe o nome fantasia: ");
                ((PessoaJuridica) pessoa).setNomeFantasia(ControladorSistema.bf.readLine());
            }

            System.out.print("Informe " + (tipoPessoa == 1 ? "seu nome" : "a razão social") + ": ");
            pessoa.setNome(ControladorSistema.bf.readLine());

            System.out.print("Informe seu endereço: ");
            pessoa.setEndereco(ControladorSistema.bf.readLine());

            System.out.print("Informe seu telefone: ");
            pessoa.setTelefone(ControladorSistema.bf.readLine());

            System.out.print("Informe seu e-mail: ");
            pessoa.setEmail(ControladorSistema.bf.readLine());

            System.out.print("Informe uma senha: ");
            String senha = ControladorSistema.bf.readLine();

            System.out.print("Confirme a senha: ");
            String confirmacaoSenha = ControladorSistema.bf.readLine();

            if (!senha.equals(confirmacaoSenha)) {
                throw new RuntimeException("A senha e a confirmação não conferem!");
            }

            if (!pessoaExiste) {
                this.repoCliente.addPessoa(pessoa);
            }

            if (!banco.adicionarConta(new ContaCorrente(senha, pessoa))) {
                throw new RuntimeException("Não foi possível criar a conta!");
            }

            System.out.println("Conta criada com sucesso!");

            this.contaOperacao = banco.getContaByDocumentoCliente(pessoa.getDocumento());
            return;
        } catch (IOException | RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        try {
            System.out.println("Pressione ENTER para continuar...");
            ControladorSistema.bf.readLine();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        this.contaOperacao = null;
    }

    public ContaCorrente getContaOperacao() {
        return this.contaOperacao;
    }

    public boolean isLogado() {
        return this.contaOperacao != null;
    }

    public void actionLogout() {
        this.contaOperacao = new ContaCorrente();
        this.contaOperacao = null;
    }

    public void actionLogin() {
        try {
            System.out.print("Informe a agência: ");
            int agencia = Integer.parseInt(ControladorSistema.bf.readLine());

            System.out.print("Informe a conta: ");
            int conta = Integer.parseInt(ControladorSistema.bf.readLine());

            System.out.print("Informe a senha: ");
            String senha = ControladorSistema.bf.readLine();

            Banco banco = this.repoBanco.getBanco(this.atm.getBacen());
            ContaCorrente contaCorrente = banco.getConta(agencia, conta);

            if (contaCorrente == null) {
                throw new RuntimeException("Conta não encontrada!");
            }

            if (!contaCorrente.getSenha().equals(senha)) {
                throw new RuntimeException("Credenciais inválidas!");
            }

            this.contaOperacao = contaCorrente;
            return;
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        this.contaOperacao = null;
    }

    public boolean abrirConta(Pessoa novoCliente) {
        RepositorioPessoa repoCliente = RepositorioPessoa.getInstance();

        repoCliente.addPessoa(novoCliente);

        return true;
    }

    public boolean sacar() {
        double valor;

        try {
            System.out.print("Digite o valor a ser sacado: ");
            valor = Double.parseDouble(ControladorSistema.bf.readLine());

            boolean sacou = this.contaOperacao.sacar(valor);

            if (sacou) {
                System.out.println("Saque realizado com sucesso!");
            } else {
                System.out.println("Não foi possível realizar o saque!");
            }

            System.out.println("Pressione ENTER para continuar...");
            ControladorSistema.bf.readLine();

            return sacou;
        } catch (NumberFormatException | IOException e) {
            System.out.println("Valor digitado é inválido!");
        }

        return false;
    }

    public boolean depositar() {
        double valor;

        try {
            System.out.print("Digite o valor a ser depositado: ");
            valor = Double.parseDouble(ControladorSistema.bf.readLine());

            boolean depositou = this.contaOperacao.depositar(valor);

            if (depositou) {
                System.out.println("Depósito realizado com sucesso!");
            } else {
                System.out.println("Não foi possível realizar o depósito!");
            }

            System.out.println("Pressione ENTER para continuar...");
            ControladorSistema.bf.readLine();

            return depositou;
        } catch (NumberFormatException | IOException e) {
            System.out.println("Valor digitado é inválido!");
        }

        return false;
    }

    public void transferir() {
        try {
            double valor;
            int numeroBancoDestino;
            int numeroContaDestino;
            int numeroAgenciaDestino;

            Banco bancoDestino;
            ContaCorrente contaDestino;

            System.out.print("Digite o número do banco destino: ");
            numeroBancoDestino = Integer.parseInt(ControladorSistema.bf.readLine());

            bancoDestino = this.repoBanco.getBanco(numeroBancoDestino);

            if (bancoDestino == null) {
                throw new RuntimeException("Banco destino não encontrado!");
            }

            System.out.print("Digite o número da agência destino: ");
            numeroAgenciaDestino = Integer.parseInt(ControladorSistema.bf.readLine());

            System.out.print("Digite o número da conta destino: ");
            numeroContaDestino = Integer.parseInt(ControladorSistema.bf.readLine());

            contaDestino = bancoDestino.getConta(numeroAgenciaDestino, numeroContaDestino);

            if (contaDestino == null) {
                throw new RuntimeException("Conta destino não encontrada!");
            }

            System.out.print("Digite o valor a ser transferido: ");
            valor = Double.parseDouble(ControladorSistema.bf.readLine());

            if (this.contaOperacao.transferir(valor, contaDestino)) {
                System.out.println("Transferência realizada com sucesso!");
                System.out.println("Pressione ENTER para continuar...");
                ControladorSistema.bf.readLine();
                return;
            }

            System.out.println("Não foi possível realizar a transferência!");
            System.out.println("Pressione ENTER para continuar...");
            ControladorSistema.bf.readLine();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void extrato() {
        try {
            System.out.println(
                    "Extrato da conta " + this.contaOperacao.getAgencia() + " - " + this.contaOperacao.getNumero());
            System.out.println("Saldo: R$" + String.format("%.2f", this.contaOperacao.getSaldo()));

            for (Transacao operacao : this.contaOperacao.getTransacoes()) {
                System.out.println(operacao);
            }

            System.out.println("Pressione ENTER para continuar...");
            ControladorSistema.bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}