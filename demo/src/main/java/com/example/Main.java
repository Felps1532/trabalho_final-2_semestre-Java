package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.example.model.Contato;
import com.example.model.Empresa;
import com.example.model.EntidadeBase;
import com.example.model.Produto;
import com.example.model.Usuario;
import com.example.model.Venda;
import com.example.model.dao.ContatoDAO;
import com.example.model.dao.EmpresaDAO;
import com.example.model.dao.ProdutoDAO;
import com.example.model.dao.UsuarioDAO;
import com.example.model.dao.VendaDAO;

public class Main {
    private static final ContatoDAO contatoDAO = new ContatoDAO();
    private static final EmpresaDAO empresaDAO = new EmpresaDAO();
    private static final ProdutoDAO produtoDAO = new ProdutoDAO();
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final VendaDAO vendaDAO = new VendaDAO();
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        Usuario usuarioLogado = fazerLogin();

        if (usuarioLogado != null) {
            JOptionPane.showMessageDialog(null,
                    "Login realizado com sucesso!\n\nSeja bem-vindo(a), " + usuarioLogado.getNome());
            menuPrincipal(usuarioLogado);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario nao encontrado!\nEmail ou senha incorretos!");
        }
    }

    private static Usuario fazerLogin() {
        String email = lerTexto("Email");
        String senha = lerTexto("Senha");

        return usuarioDAO.autenticar(email, senha);
    }

    private static void menuPrincipal(Usuario usuarioLogado) {
        int opcao = -1;

        while (opcao != 0) {
            opcao = lerInt("Menu Principal\n\n" +
                    "1 - Produtos\n" +
                    "2 - Empresas\n" +
                    "3 - Contatos\n" +
                    "4 - Vendas\n" +
                    "5 - Relatorio de vendas\n" +
                    "6 - Demonstrar polimorfismo\n" +
                    "7 - Alterar senha\n" +
                    "0 - Sair");

            if (opcao == 1) {
                menuProdutos();
            } else if (opcao == 2) {
                menuEmpresas();
            } else if (opcao == 3) {
                menuContatos();
            } else if (opcao == 4) {
                menuVendas(usuarioLogado);
            } else if (opcao == 5) {
                mostrarRelatorioVendas();
            } else if (opcao == 6) {
                demonstrarPolimorfismo();
            } else if (opcao == 7) {
                alterarSenha(usuarioLogado);
            } else if (opcao == 0) {
                JOptionPane.showMessageDialog(null, "Saindo do sistema...");
            } else {
                JOptionPane.showMessageDialog(null, "Opcao invalida.");
            }
        }
    }

    private static void menuProdutos() {
        int opcaoProduto = -1;

        while (opcaoProduto != 0) {
            opcaoProduto = lerInt("Menu de Produtos\n\n" +
                    "1. Cadastrar produto\n" +
                    "2. Listar produtos\n" +
                    "3. Buscar produto por ID\n" +
                    "4. Atualizar produto\n" +
                    "5. Deletar produto\n" +
                    "0. Voltar");

            if (opcaoProduto == 1) {
                Produto produto = new Produto();

                produto.setNome(lerTexto("Digite o nome do produto"));
                produto.setDescricao(lerTexto("Digite a descricao do produto"));

                produtoDAO.cadastrar(produto);
            } else if (opcaoProduto == 2) {
                ArrayList<Produto> produtos = produtoDAO.listar();
                StringBuilder mensagem = new StringBuilder();

                for (Produto produto : produtos) {
                    mensagem.append("ID: ").append(produto.getId()).append("\n");
                    mensagem.append("Nome: ").append(produto.getNome()).append("\n");
                    mensagem.append("Descricao: ").append(produto.getDescricao()).append("\n");
                    mensagem.append("----------------------\n");
                }

                mostrarMensagemOuVazio(mensagem, "Nenhum produto cadastrado.");
            } else if (opcaoProduto == 3) {
                Produto produto = produtoDAO.buscarPorId(lerInt("Digite o id do produto"));

                if (produto != null) {
                    JOptionPane.showMessageDialog(null, montarTextoProduto(produto));
                } else {
                    JOptionPane.showMessageDialog(null, "Produto nao encontrado.");
                }
            } else if (opcaoProduto == 4) {
                Produto produto = produtoDAO.buscarPorId(lerInt("Digite o id do produto"));

                if (produto != null) {
                    produto.setNome(lerTextoComPadrao("Digite o novo nome:", produto.getNome()));
                    produto.setDescricao(lerTextoComPadrao("Digite a nova descricao:", produto.getDescricao()));

                    produtoDAO.atualizar(produto);
                } else {
                    JOptionPane.showMessageDialog(null, "Produto nao encontrado.");
                }
            } else if (opcaoProduto == 5) {
                Produto produto = produtoDAO.buscarPorId(lerInt("Digite o id do produto"));

                if (produto != null) {
                    int confirmar = JOptionPane.showConfirmDialog(null,
                            "Deseja deletar este produto?\n\n" + montarTextoProduto(produto),
                            "Confirmar exclusao",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmar == JOptionPane.YES_OPTION) {
                        produtoDAO.deletar(produto.getId());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Produto nao encontrado.");
                }
            } else if (opcaoProduto == 0) {
                JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
            } else {
                JOptionPane.showMessageDialog(null, "Opcao invalida.");
            }
        }
    }

    private static void menuEmpresas() {
        int opcaoEmpresa = -1;

        while (opcaoEmpresa != 0) {
            opcaoEmpresa = lerInt("Menu de Empresas\n\n" +
                    "1. Cadastrar empresa\n" +
                    "2. Listar empresas\n" +
                    "3. Buscar empresa por ID\n" +
                    "4. Atualizar empresa\n" +
                    "5. Deletar empresa\n" +
                    "0. Voltar");

            if (opcaoEmpresa == 1) {
                Empresa empresa = new Empresa();

                empresa.setNomeFantasia(lerTexto("Digite o nome fantasia da empresa"));
                empresa.setRazaoSocial(lerTexto("Digite a razao social da empresa"));
                empresa.setCnpj(lerTexto("Digite o CNPJ da empresa"));
                empresa.setTelefone(lerTexto("Digite o telefone da empresa"));
                empresa.setEmail(lerTexto("Digite o email da empresa"));

                empresaDAO.cadastrar(empresa);
            } else if (opcaoEmpresa == 2) {
                ArrayList<Empresa> empresas = empresaDAO.listar();
                StringBuilder mensagem = new StringBuilder();

                for (Empresa empresa : empresas) {
                    mensagem.append(montarTextoEmpresa(empresa)).append("\n----------------------\n");
                }

                mostrarMensagemOuVazio(mensagem, "Nenhuma empresa cadastrada.");
            } else if (opcaoEmpresa == 3) {
                Empresa empresa = empresaDAO.buscarPorId(lerInt("Digite o id da empresa"));

                if (empresa != null) {
                    JOptionPane.showMessageDialog(null, montarTextoEmpresa(empresa));
                } else {
                    JOptionPane.showMessageDialog(null, "Empresa nao encontrada.");
                }
            } else if (opcaoEmpresa == 4) {
                Empresa empresa = empresaDAO.buscarPorId(lerInt("Digite o id da empresa"));

                if (empresa != null) {
                    empresa.setNomeFantasia(lerTextoComPadrao("Digite o novo nome fantasia:",
                            empresa.getNomeFantasia()));
                    empresa.setRazaoSocial(lerTextoComPadrao("Digite a nova razao social:",
                            empresa.getRazaoSocial()));
                    empresa.setCnpj(lerTextoComPadrao("Digite o novo CNPJ:", empresa.getCnpj()));
                    empresa.setTelefone(lerTextoComPadrao("Digite o novo telefone:", empresa.getTelefone()));
                    empresa.setEmail(lerTextoComPadrao("Digite o novo email:", empresa.getEmail()));

                    empresaDAO.atualizar(empresa);
                } else {
                    JOptionPane.showMessageDialog(null, "Empresa nao encontrada.");
                }
            } else if (opcaoEmpresa == 5) {
                Empresa empresa = empresaDAO.buscarPorId(lerInt("Digite o id da empresa"));

                if (empresa != null) {
                    int confirmar = JOptionPane.showConfirmDialog(null,
                            "Deseja deletar esta empresa?\n\n" + montarTextoEmpresa(empresa),
                            "Confirmar exclusao",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmar == JOptionPane.YES_OPTION) {
                        empresaDAO.deletar(empresa.getId());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Empresa nao encontrada.");
                }
            } else if (opcaoEmpresa == 0) {
                JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
            } else {
                JOptionPane.showMessageDialog(null, "Opcao invalida.");
            }
        }
    }

    private static void menuContatos() {
        int opcaoContato = -1;

        while (opcaoContato != 0) {
            opcaoContato = lerInt("Menu de Contatos\n\n" +
                    "1. Cadastrar contato\n" +
                    "2. Listar contatos\n" +
                    "3. Buscar contato por ID\n" +
                    "4. Atualizar contato\n" +
                    "5. Deletar contato\n" +
                    "0. Voltar");

            if (opcaoContato == 1) {
                Contato contato = new Contato();

                contato.setNome(lerTexto("Digite o nome do contato"));
                contato.setTelefone(lerTexto("Digite o telefone do contato"));
                contato.setEmail(lerTexto("Digite o email do contato"));
                contato.setTemperatura(lerTexto("Digite a temperatura do contato"));

                Empresa empresa = empresaDAO.buscarPorId(lerInt("Digite o id da empresa vinculada ao contato"));

                if (empresa != null) {
                    contato.setEmpresa(empresa);
                    contatoDAO.cadastrar(contato);
                } else {
                    JOptionPane.showMessageDialog(null, "Empresa nao encontrada. Contato nao cadastrado.");
                }
            } else if (opcaoContato == 2) {
                ArrayList<Contato> contatos = contatoDAO.listar();
                StringBuilder mensagem = new StringBuilder();

                for (Contato contato : contatos) {
                    mensagem.append(montarTextoContato(contato)).append("\n----------------------\n");
                }

                mostrarMensagemOuVazio(mensagem, "Nenhum contato cadastrado.");
            } else if (opcaoContato == 3) {
                Contato contato = contatoDAO.buscarPorId(lerInt("Digite o id do contato"));

                if (contato != null) {
                    JOptionPane.showMessageDialog(null, montarTextoContato(contato));
                } else {
                    JOptionPane.showMessageDialog(null, "Contato nao encontrado.");
                }
            } else if (opcaoContato == 4) {
                Contato contato = contatoDAO.buscarPorId(lerInt("Digite o id do contato"));

                if (contato != null) {
                    contato.setNome(lerTextoComPadrao("Digite o novo nome:", contato.getNome()));
                    contato.setTelefone(lerTextoComPadrao("Digite o novo telefone:", contato.getTelefone()));
                    contato.setEmail(lerTextoComPadrao("Digite o novo email:", contato.getEmail()));
                    contato.setTemperatura(lerTextoComPadrao("Digite a nova temperatura:", contato.getTemperatura()));

                    String idEmpresaAtual = "";
                    if (contato.getEmpresa() != null) {
                        idEmpresaAtual = String.valueOf(contato.getEmpresa().getId());
                    }

                    Empresa empresa = empresaDAO.buscarPorId(
                            lerIntComPadrao("Digite o novo id da empresa vinculada:", idEmpresaAtual));

                    if (empresa != null) {
                        contato.setEmpresa(empresa);
                        contatoDAO.atualizar(contato);
                    } else {
                        JOptionPane.showMessageDialog(null, "Empresa nao encontrada. Contato nao atualizado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Contato nao encontrado.");
                }
            } else if (opcaoContato == 5) {
                Contato contato = contatoDAO.buscarPorId(lerInt("Digite o id do contato"));

                if (contato != null) {
                    int confirmar = JOptionPane.showConfirmDialog(null,
                            "Deseja deletar este contato?\n\n" + montarTextoContato(contato),
                            "Confirmar exclusao",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmar == JOptionPane.YES_OPTION) {
                        contatoDAO.deletar(contato.getId());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Contato nao encontrado.");
                }
            } else if (opcaoContato == 0) {
                JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
            } else {
                JOptionPane.showMessageDialog(null, "Opcao invalida.");
            }
        }
    }

    private static void menuVendas(Usuario usuarioLogado) {
        int opcaoVenda = -1;

        while (opcaoVenda != 0) {
            opcaoVenda = lerInt("Menu de Vendas\n\n" +
                    "1. Cadastrar venda\n" +
                    "2. Listar vendas\n" +
                    "3. Buscar venda por ID\n" +
                    "4. Atualizar venda\n" +
                    "5. Deletar venda\n" +
                    "6. Vincular produto a venda\n" +
                    "0. Voltar");

            if (opcaoVenda == 1) {
                cadastrarVenda(usuarioLogado);
            } else if (opcaoVenda == 2) {
                ArrayList<Venda> vendas = vendaDAO.listar();
                StringBuilder mensagem = new StringBuilder();

                for (Venda venda : vendas) {
                    mensagem.append(montarTextoVenda(venda)).append("\n----------------------\n");
                }

                mostrarMensagemOuVazio(mensagem, "Nenhuma venda cadastrada.");
            } else if (opcaoVenda == 3) {
                Venda venda = vendaDAO.buscarPorId(lerInt("Digite o id da venda"));

                if (venda != null) {
                    JOptionPane.showMessageDialog(null, montarTextoVenda(venda));
                } else {
                    JOptionPane.showMessageDialog(null, "Venda nao encontrada.");
                }
            } else if (opcaoVenda == 4) {
                atualizarVenda(usuarioLogado);
            } else if (opcaoVenda == 5) {
                Venda venda = vendaDAO.buscarPorId(lerInt("Digite o id da venda"));

                if (venda != null) {
                    int confirmar = JOptionPane.showConfirmDialog(null,
                            "Deseja deletar esta venda?\n\n" + montarTextoVenda(venda),
                            "Confirmar exclusao",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmar == JOptionPane.YES_OPTION) {
                        vendaDAO.deletar(venda.getId());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Venda nao encontrada.");
                }
            } else if (opcaoVenda == 6) {
                vincularProdutoVenda();
            } else if (opcaoVenda == 0) {
                JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
            } else {
                JOptionPane.showMessageDialog(null, "Opcao invalida.");
            }
        }
    }

    private static void cadastrarVenda(Usuario usuarioLogado) {
        Venda venda = new Venda();

        venda.setDescricao(lerTexto("Digite a descricao da venda"));
        venda.setValor(lerDouble("Digite o valor da venda"));
        venda.setStatus(lerTexto("Digite o status da venda (EM_ABERTO, GANHA ou PERDIDA)"));
        venda.setDataFechamento(lerDataOpcional("Digite a data de fechamento (yyyy-MM-dd HH:mm), ou deixe vazio"));

        Contato contato = contatoDAO.buscarPorId(lerInt("Digite o id do contato da venda"));
        Empresa empresa = empresaDAO.buscarPorId(lerInt("Digite o id da empresa da venda"));

        if (contato == null) {
            JOptionPane.showMessageDialog(null, "Contato nao encontrado. Venda nao cadastrada.");
            return;
        }

        if (empresa == null) {
            JOptionPane.showMessageDialog(null, "Empresa nao encontrada. Venda nao cadastrada.");
            return;
        }

        venda.setContato(contato);
        venda.setEmpresa(empresa);
        venda.setVendedorResp(usuarioLogado);

        vendaDAO.cadastrar(venda);
    }

    private static void vincularProdutoVenda() {
        Venda venda = vendaDAO.buscarPorId(lerInt("Digite o id da venda"));

        if (venda == null) {
            JOptionPane.showMessageDialog(null, "Venda nao encontrada.");
            return;
        }

        Produto produto = produtoDAO.buscarPorId(lerInt("Digite o id do produto"));

        if (produto == null) {
            JOptionPane.showMessageDialog(null, "Produto nao encontrado.");
            return;
        }

        vendaDAO.vincularProduto(venda.getId(), produto.getId());
    }

    private static void atualizarVenda(Usuario usuarioLogado) {
        Venda venda = vendaDAO.buscarPorId(lerInt("Digite o id da venda"));

        if (venda == null) {
            JOptionPane.showMessageDialog(null, "Venda nao encontrada.");
            return;
        }

        venda.setDescricao(lerTextoComPadrao("Digite a nova descricao:", venda.getDescricao()));
        venda.setValor(lerDoubleComPadrao("Digite o novo valor:", venda.getValor()));
        venda.setStatus(lerTextoComPadrao("Digite o novo status:", venda.getStatus()));
        venda.setDataFechamento(lerDataOpcional("Digite a nova data de fechamento (yyyy-MM-dd HH:mm), ou deixe vazio"));

        String idContatoAtual = "";
        if (venda.getContato() != null) {
            idContatoAtual = String.valueOf(venda.getContato().getId());
        }

        String idEmpresaAtual = "";
        if (venda.getEmpresa() != null) {
            idEmpresaAtual = String.valueOf(venda.getEmpresa().getId());
        }

        Contato contato = contatoDAO.buscarPorId(lerIntComPadrao("Digite o novo id do contato:", idContatoAtual));
        Empresa empresa = empresaDAO.buscarPorId(lerIntComPadrao("Digite o novo id da empresa:", idEmpresaAtual));

        if (contato == null) {
            JOptionPane.showMessageDialog(null, "Contato nao encontrado. Venda nao atualizada.");
            return;
        }

        if (empresa == null) {
            JOptionPane.showMessageDialog(null, "Empresa nao encontrada. Venda nao atualizada.");
            return;
        }

        venda.setContato(contato);
        venda.setEmpresa(empresa);
        venda.setVendedorResp(usuarioLogado);

        vendaDAO.atualizar(venda);
    }

    private static void mostrarRelatorioVendas() {
        ArrayList<Venda> vendas = vendaDAO.listar();

        if (vendas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma venda cadastrada para relatorio.");
            return;
        }

        StringBuilder mensagem = new StringBuilder();
        double totalGanho = 0;
        int qtdGanhas = 0;
        int qtdAbertas = 0;
        int qtdPerdidas = 0;

        mensagem.append("Relatorio de Vendas\n\n");

        for (Venda venda : vendas) {
            mensagem.append(montarTextoVenda(venda)).append("\n----------------------\n");

            if ("GANHA".equalsIgnoreCase(venda.getStatus())) {
                totalGanho += venda.getValor();
                qtdGanhas++;
            } else if ("EM_ABERTO".equalsIgnoreCase(venda.getStatus())) {
                qtdAbertas++;
            } else if ("PERDIDA".equalsIgnoreCase(venda.getStatus())) {
                qtdPerdidas++;
            }
        }

        mensagem.append("\nResumo\n");
        mensagem.append("Vendas ganhas: ").append(qtdGanhas).append("\n");
        mensagem.append("Vendas em aberto: ").append(qtdAbertas).append("\n");
        mensagem.append("Vendas perdidas: ").append(qtdPerdidas).append("\n");
        mensagem.append("Total ganho: R$ ").append(totalGanho);

        JOptionPane.showMessageDialog(null, mensagem.toString());
    }

    private static void demonstrarPolimorfismo() {
        ArrayList<EntidadeBase> entidades = new ArrayList<>();

        ArrayList<Produto> produtos = produtoDAO.listar();
        ArrayList<Empresa> empresas = empresaDAO.listar();
        ArrayList<Contato> contatos = contatoDAO.listar();
        ArrayList<Venda> vendas = vendaDAO.listar();

        if (!produtos.isEmpty()) {
            entidades.add(produtos.get(0));
        }

        if (!empresas.isEmpty()) {
            entidades.add(empresas.get(0));
        }

        if (!contatos.isEmpty()) {
            entidades.add(contatos.get(0));
        }

        if (!vendas.isEmpty()) {
            entidades.add(vendas.get(0));
        }

        if (entidades.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Cadastre pelo menos uma entidade para demonstrar polimorfismo.");
            return;
        }

        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Polimorfismo\n\n");
        mensagem.append("A lista abaixo e do tipo EntidadeBase,\n");
        mensagem.append("mas cada objeto executa seu proprio getResumo().\n\n");

        for (EntidadeBase entidade : entidades) {
            mensagem.append(entidade.getResumo()).append("\n");
            mensagem.append("Classe real: ").append(entidade.getClass().getSimpleName()).append("\n");
            mensagem.append("----------------------\n");
        }

        JOptionPane.showMessageDialog(null, mensagem.toString());
    }

    private static void alterarSenha(Usuario usuarioLogado) {
        String senhaAtual = lerTexto("Digite sua senha atual");
        String novaSenha = lerTexto("Digite sua nova senha");

        usuarioDAO.alterarSenha(usuarioLogado.getId(), senhaAtual, novaSenha);
    }

    private static String montarTextoProduto(Produto produto) {
        return "ID: " + produto.getId() + "\n" +
                "Nome: " + produto.getNome() + "\n" +
                "Descricao: " + produto.getDescricao();
    }

    private static String montarTextoEmpresa(Empresa empresa) {
        return "ID: " + empresa.getId() + "\n" +
                "Nome fantasia: " + empresa.getNomeFantasia() + "\n" +
                "Razao social: " + empresa.getRazaoSocial() + "\n" +
                "CNPJ: " + empresa.getCnpj() + "\n" +
                "Telefone: " + empresa.getTelefone() + "\n" +
                "Email: " + empresa.getEmail();
    }

    private static String montarTextoContato(Contato contato) {
        String texto = "ID: " + contato.getId() + "\n" +
                "Nome: " + contato.getNome() + "\n" +
                "Telefone: " + contato.getTelefone() + "\n" +
                "Email: " + contato.getEmail() + "\n" +
                "Temperatura: " + contato.getTemperatura() + "\n";

        if (contato.getEmpresa() != null) {
            Empresa empresa = empresaDAO.buscarPorId(contato.getEmpresa().getId());

            if (empresa != null) {
                texto += "Empresa: " + empresa.getNomeFantasia() + " (ID " + empresa.getId() + ")";
            } else {
                texto += "ID empresa: " + contato.getEmpresa().getId();
            }
        }

        return texto;
    }

    private static String montarTextoVenda(Venda venda) {
        String nomeContato = "Sem contato";
        String nomeEmpresa = "Sem empresa";
        String nomeVendedor = "Sem vendedor";
        String produtosTexto = "Sem produtos vinculados";

        if (venda.getContato() != null) {
            Contato contato = contatoDAO.buscarPorId(venda.getContato().getId());
            if (contato != null) {
                nomeContato = contato.getNome();
            }
        }

        if (venda.getEmpresa() != null) {
            Empresa empresa = empresaDAO.buscarPorId(venda.getEmpresa().getId());
            if (empresa != null) {
                nomeEmpresa = empresa.getNomeFantasia();
            }
        }

        if (venda.getVendedorResp() != null) {
            Usuario vendedor = usuarioDAO.buscarPorId(venda.getVendedorResp().getId());
            if (vendedor != null) {
                nomeVendedor = vendedor.getNome();
            }
        }

        ArrayList<Produto> produtos = vendaDAO.listarProdutosDaVenda(venda.getId());

        if (!produtos.isEmpty()) {
            StringBuilder nomesProdutos = new StringBuilder();

            for (Produto produto : produtos) {
                nomesProdutos.append(produto.getNome()).append("; ");
            }

            produtosTexto = nomesProdutos.toString();
        }

        return "ID: " + venda.getId() + "\n" +
                "Descricao: " + venda.getDescricao() + "\n" +
                "Valor: R$ " + venda.getValor() + "\n" +
                "Status: " + venda.getStatus() + "\n" +
                "Data fechamento: " + formatarData(venda.getDataFechamento()) + "\n" +
                "Contato: " + nomeContato + "\n" +
                "Empresa: " + nomeEmpresa + "\n" +
                "Vendedor: " + nomeVendedor + "\n" +
                "Produtos: " + produtosTexto;
    }

    private static String lerTexto(String mensagem) {
        String valor = JOptionPane.showInputDialog(mensagem);

        if (valor == null) {
            return "";
        }

        return valor.trim();
    }

    private static String lerTextoComPadrao(String mensagem, String valorAtual) {
        String valor = JOptionPane.showInputDialog(mensagem, valorAtual);

        if (valor == null || valor.trim().isEmpty()) {
            return valorAtual;
        }

        return valor.trim();
    }

    private static int lerInt(String mensagem) {
        while (true) {
            String valor = JOptionPane.showInputDialog(mensagem);

            if (valor == null || valor.trim().isEmpty()) {
                return 0;
            }

            try {
                return Integer.parseInt(valor.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um numero inteiro valido.");
            }
        }
    }

    private static int lerIntComPadrao(String mensagem, String valorAtual) {
        while (true) {
            String valor = JOptionPane.showInputDialog(mensagem, valorAtual);

            if (valor == null || valor.trim().isEmpty()) {
                if (valorAtual == null || valorAtual.trim().isEmpty()) {
                    return 0;
                }

                return Integer.parseInt(valorAtual);
            }

            try {
                return Integer.parseInt(valor.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um numero inteiro valido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            String valor = JOptionPane.showInputDialog(mensagem);

            if (valor == null || valor.trim().isEmpty()) {
                return 0;
            }

            try {
                return Double.parseDouble(valor.trim().replace(",", "."));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um valor numerico valido.");
            }
        }
    }

    private static double lerDoubleComPadrao(String mensagem, double valorAtual) {
        while (true) {
            String valor = JOptionPane.showInputDialog(mensagem, valorAtual);

            if (valor == null || valor.trim().isEmpty()) {
                return valorAtual;
            }

            try {
                return Double.parseDouble(valor.trim().replace(",", "."));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um valor numerico valido.");
            }
        }
    }

    private static LocalDateTime lerDataOpcional(String mensagem) {
        while (true) {
            String valor = JOptionPane.showInputDialog(mensagem);

            if (valor == null || valor.trim().isEmpty()) {
                return null;
            }

            try {
                return LocalDateTime.parse(valor.trim(), FORMATO_DATA);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Digite a data no formato yyyy-MM-dd HH:mm.");
            }
        }
    }

    private static String formatarData(LocalDateTime data) {
        if (data == null) {
            return "Sem data";
        }

        return data.format(FORMATO_DATA);
    }

    private static void mostrarMensagemOuVazio(StringBuilder mensagem, String mensagemVazia) {
        if (mensagem.length() == 0) {
            JOptionPane.showMessageDialog(null, mensagemVazia);
        } else {
            JOptionPane.showMessageDialog(null, mensagem.toString());
        }
    }
}
