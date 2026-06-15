# Guia do Projeto Floomes

## Ideia do sistema

O Floomes e um CRM simplificado inspirado no Ploomes. Ele permite que um usuario/vendedor entre no sistema, cadastre produtos, empresas, contatos e vendas, consulte essas informacoes e gere um relatorio de vendas.

## Como testar em outra maquina

1. Instale MySQL e deixe o usuario/senha iguais ao projeto:
   - host: `localhost`
   - banco: `floomes_database`
   - usuario: `root`
   - senha: `1234`
2. Execute o script SQL:
   - `database/floomes_database.sql`
3. Rode a classe:
   - `com.example.Main`
4. Use um login de exemplo:
   - email: `felipe@floomes.com`
   - senha: `123`

## Mapa do codigo

- `Main.java`: entrada do sistema, login, menus e chamadas para os DAOs.
- `model/EntidadeBase.java`: classe abstrata com `id`, `dataEntrada` e `getResumo()`.
- `model/Pessoa.java`: classe abstrata para entidades com `nome` e `email`.
- `model/Usuario.java`: usuario que faz login, vendedor ou administrador.
- `model/Empresa.java`: empresa vinculada aos contatos e vendas.
- `model/Contato.java`: pessoa que o vendedor tenta contatar.
- `model/Produto.java`: produtos/servicos oferecidos.
- `model/Venda.java`: venda ou oportunidade comercial.
- `model/dao/ConnectionFactory.java`: cria a conexao JDBC com MySQL.
- `model/dao/*DAO.java`: classes que fazem CRUD no banco.
- `database/floomes_database.sql`: cria banco, tabelas e dados de teste.

## Fluxo principal

1. O sistema pede email e senha.
2. `UsuarioDAO.autenticar()` valida o login no banco.
3. Se o login der certo, abre o menu principal.
4. O menu principal abre submenus:
   - Produtos
   - Empresas
   - Contatos
   - Vendas
   - Relatorio de vendas
   - Alterar senha

## Requisitos obrigatorios

- Login com usuario e senha: `UsuarioDAO.autenticar()`.
- Alterar senha sem repetir as 3 ultimas: `UsuarioDAO.alterarSenha()` e tabela `prev_senhas`.
- Minimo de 5 classes: ha mais de 5, incluindo `Usuario`, `Empresa`, `Contato`, `Produto`, `Venda`.
- Minimo de 3 classes compostas:
  - `Contato` possui `Empresa`.
  - `Venda` possui `Contato`.
  - `Venda` possui `Usuario`.
  - `Venda` possui `Empresa`.
- Atributos privados: entidades usam atributos `private`.
- Menu de cadastro e consulta: `Main.java` possui menus de Produtos, Empresas, Contatos e Vendas.
- Relatorio envolvendo varias classes: opcao `Relatorio de vendas` usa `Venda`, `Contato`, `Empresa`, `Usuario` e `Produto`.
- Heranca: `Usuario` e `Contato` herdam de `Pessoa`; `Produto`, `Empresa` e `Venda` herdam de `EntidadeBase`.
- Polimorfismo: metodo `getResumo()` declarado em `EntidadeBase` e sobrescrito em `Usuario`, `Empresa`, `Contato`, `Produto` e `Venda`.
- Classe abstrata ou interface: `EntidadeBase` e `Pessoa`.
- Leitura e escrita em banco de dados: DAOs usam JDBC/MySQL.

## Roteiro de apresentacao

1. Mostre o login com `UsuarioDAO.autenticar()`.
2. Mostre o menu principal.
3. Mostre um CRUD rapido, por exemplo Produtos.
4. Mostre Contatos e explique que contato possui uma empresa.
5. Mostre Vendas e explique que venda possui contato, empresa e vendedor.
   - No menu de vendas, mostre tambem "Gerenciar produtos da venda" para vincular ou desvincular produtos.
6. Mostre o Relatorio de vendas.
7. Mostre `getResumo()` para explicar polimorfismo.

## Produtos em uma venda

A venda fica na tabela `vendas`, mas os produtos vendidos ficam na tabela intermediaria `venda_produtos`.

No sistema, isso aparece no menu:

- `Vendas`
- `Gerenciar produtos da venda`

Nessa tela e possivel:

- listar produtos vinculados a venda;
- vincular um produto;
- desvincular um produto.

Ao atualizar uma venda, o sistema tambem pergunta se voce deseja gerenciar os produtos dela.
