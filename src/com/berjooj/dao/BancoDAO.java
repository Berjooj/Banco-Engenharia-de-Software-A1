package com.berjooj.dao;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.berjooj.model.Banco;
import com.berjooj.model.ContaCorrente;
import com.berjooj.model.Pessoa;
import com.berjooj.model.PessoaFisica;
import com.berjooj.model.PessoaJuridica;
import com.berjooj.repositorio.RepositorioBanco;
import com.berjooj.repositorio.RepositorioPessoa;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BancoDAO extends ConectorDAO {

    public BancoDAO() {
        this.caminhoArquivo = "src/com/berjooj/resources";
        this.nomeArquivo = "bancos.json";

        this.carregaArquivo();
    }

    @Override
    public void montarRepositorio() {
        RepositorioBanco repositorio = RepositorioBanco.getInstance();
        RepositorioPessoa repositorioCliente = RepositorioPessoa.getInstance();

        if (this.json != null) {
            for (int i = 0; i < this.json.size(); i++) {
                Banco banco = new Banco();

                banco.setBacen(this.json.get(i).getAsJsonObject().get("bacen").getAsInt());
                banco.setNome(this.json.get(i).getAsJsonObject().get("nome").getAsString());

                // Verifica se existe contas para o banco
                JsonObject contas = this.json.get(i).getAsJsonObject().get("contas").getAsJsonObject().get("1")
                        .getAsJsonObject();

                for (String key : contas.keySet()) {
                    JsonObject pessoaJSON = contas.get(key).getAsJsonObject().get("cliente").getAsJsonObject();

                    Pessoa pessoa;

                    if (pessoaJSON.has("nomeFantasia")) {
                        pessoa = (PessoaJuridica) repositorioCliente
                                .getPessoa(pessoaJSON.get("documento").getAsString());

                        if (pessoa == null) {
                            pessoa = new PessoaJuridica();
                            pessoa.setNome(pessoaJSON.get("nome").getAsString());
                            pessoa.setDocumento(pessoaJSON.get("documento").getAsString());
                            pessoa.setEmail(pessoaJSON.get("email").getAsString());
                            pessoa.setTelefone(pessoaJSON.get("telefone").getAsString());
                            pessoa.setEndereco(pessoaJSON.get("endereco").getAsString());
                            ((PessoaJuridica) pessoa).setNomeFantasia(pessoaJSON.get("nomeFantasia").getAsString());

                            repositorioCliente.addPessoa(pessoa);
                        }
                    } else {
                        pessoa = (PessoaFisica) repositorioCliente
                                .getPessoa(pessoaJSON.get("documento").getAsString());

                        if (pessoa == null) {
                            pessoa = new PessoaFisica();
                            pessoa.setNome(pessoaJSON.get("nome").getAsString());
                            pessoa.setDocumento(pessoaJSON.get("documento").getAsString());
                            pessoa.setEmail(pessoaJSON.get("email").getAsString());
                            pessoa.setTelefone(pessoaJSON.get("telefone").getAsString());
                            pessoa.setEndereco(pessoaJSON.get("endereco").getAsString());

                            repositorioCliente.addPessoa(pessoa);
                        }
                    }

                    ContaCorrente conta = new ContaCorrente(
                            contas.get(key).getAsJsonObject().get("senha").getAsString(),
                            pessoa);

                    conta.setSaldo(contas.get(key).getAsJsonObject().get("saldo").getAsDouble());

                    if (contas.get(key).getAsJsonObject().get("dataCriacao") != null) {
                        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH24:mm:ss");

                        try {
                            Date dataCriacao = formato
                                    .parse(contas.get(key).getAsJsonObject().get("dataCriacao").getAsString());

                            conta.setDataAbertura(dataCriacao);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    banco.adicionarConta(conta);
                }

                if (repositorio.getBanco(banco.getBacen()) == null) {
                    repositorio.addBanco(banco);
                }
            }

            // Verifica se existe transacoes para o banco
            for (int i = 0; i < this.json.size(); i++) {
                Banco banco = repositorio.getBanco(this.json.get(i).getAsJsonObject().get("bacen").getAsInt());

                JsonObject contas = this.json.get(i).getAsJsonObject().get("contas").getAsJsonObject().get("1")
                        .getAsJsonObject();

                for (String key : contas.keySet()) {
                    ContaCorrente conta = banco.getConta(
                            contas.get(key).getAsJsonObject().get("agencia").getAsInt(),
                            contas.get(key).getAsJsonObject().get("numero").getAsInt());

                    if (conta != null) {
                        JsonArray transacoes = contas.get(key).getAsJsonObject().get("transacoes").getAsJsonArray();

                        if (transacoes != null && transacoes.size() > 0) {
                            for (int j = 0; j < transacoes.size(); j++) {
                                double valor = transacoes.get(i).getAsJsonObject().get("valor").getAsDouble();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
                                String dateString = transacoes.get(i).getAsJsonObject().get("dataHora")
                                        .getAsString();
                                Date date = new Date();

                                try {
                                    date = dateFormat.parse(dateString);
                                    System.out.println(date);
                                } catch (ParseException e) {
                                    // Não faz nada
                                }

                                if (transacoes.get(i).getAsJsonObject().has("transferenia")) {
                                    // todo
                                } else if (valor > 0) {
                                    conta.depositar(valor, date);
                                } else {
                                    conta.sacar(valor, date);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            this.seedDatabase();
        }
    }

    @Override
    public void seedDatabase() {
        RepositorioBanco repositorio = RepositorioBanco.getInstance();
        RepositorioPessoa repositorioCliente = RepositorioPessoa.getInstance();

        for (int i = 1; i <= 5; i++) {
            Banco banco = new Banco("Banco " + i, i);

            for (int j = 1; j <= 5; j++) {
                Pessoa pessoa = new PessoaFisica();

                pessoa.setNome("Pessoa " + i + " " + j);
                pessoa.setDocumento("1234567890" + i);
                pessoa.setEmail(pessoa.getNome() + "@gmail.com");
                pessoa.setTelefone("1234567890");
                pessoa.setEndereco("Rua " + i + ", " + i + " - Bairro " + i + ", Cidade " + i + " - Estado " + i);

                ContaCorrente conta = new ContaCorrente("123", pessoa);
                repositorioCliente.addPessoa(pessoa);

                conta.setSaldo(0.00);

                for (int k = 1; k <= 3; k++) {
                    conta.depositar(k * 10.00);
                    conta.sacar(k * 5.00);
                }

                banco.adicionarConta(conta);
            }

            repositorio.addBanco(banco);
        }
    }
}