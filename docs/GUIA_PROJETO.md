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
   - Demonstracao de polimorfismo
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
- Polimorfismo: opcao `Demonstrar polimorfismo`, usando lista de `EntidadeBase` e o metodo sobrescrito `getResumo()`.
- Classe abstrata ou interface: `EntidadeBase` e `Pessoa`.
- Leitura e escrita em banco de dados: DAOs usam JDBC/MySQL.

## Roteiro para apresentar em ate 5 minutos

1. Apresente a ideia:
   - "Meu projeto e um CRM simplificado chamado Floomes, inspirado no Ploomes."
2. Explique as entidades:
   - Usuario: quem acessa o sistema.
   - Empresa: empresa cliente.
   - Contato: pessoa vinculada a empresa.
   - Produto: produto ou servico vendido.
   - Venda: negociacao ou venda feita pelo usuario.
3. Mostre o login:
   - email `felipe@floomes.com`, senha `123`.
4. Mostre rapidamente um CRUD:
   - Produtos ou Empresas.
5. Mostre Contatos:
   - explique que contato tem uma empresa vinculada.
6. Mostre Vendas:
   - explique que venda liga contato, empresa e vendedor.
7. Mostre o relatorio:
   - ele junta varias classes.
8. Mostre polimorfismo:
   - explique que a lista e de `EntidadeBase`, mas cada classe executa seu proprio `getResumo()`.

## Perguntas provaveis do professor

### Onde tem heranca?

`Usuario` e `Contato` herdam de `Pessoa`. `Produto`, `Empresa`, `Venda` e `Pessoa` herdam de `EntidadeBase`.

### Onde tem polimorfismo?

Na opcao "Demonstrar polimorfismo". A `Main` cria uma lista de `EntidadeBase`, coloca objetos de classes diferentes nela e chama `getResumo()`. Cada classe responde de um jeito, porque sobrescreve o metodo.

### O que e DAO?

DAO significa Data Access Object. No projeto, os DAOs separam o acesso ao banco do resto do sistema. Exemplo: `ProdutoDAO` sabe cadastrar, listar, buscar, atualizar e deletar produtos no MySQL.

### O que e composicao?

Composicao acontece quando uma classe possui outra como atributo. Exemplo: `Venda` possui `Contato`, `Usuario` e `Empresa`. Isso representa as foreign keys do banco no Java.

### Por que usar PreparedStatement?

Porque ele prepara o SQL com parametros `?`, permite setar valores com tipo correto e evita montar SQL manualmente concatenando strings.

### Como funciona a regra das 3 ultimas senhas?

Quando o usuario troca a senha, o sistema busca as 3 ultimas senhas em `prev_senhas`. Se a nova senha estiver entre elas, a troca e bloqueada. Se estiver tudo certo, a senha antiga e salva em `prev_senhas` e a nova senha vai para `usuarios`.

## Pontos para falar se perguntarem sobre melhorias

- Hoje as senhas estao em texto simples porque o foco e aprendizado de Java/JDBC. Em um sistema real, usaria hash de senha.
- A interface usa `JOptionPane` para manter o projeto simples.
- A `Main` concentra menus e fluxo; em um projeto maior, isso poderia ser separado em classes de view/controller.
- A tabela `venda_produtos` representa quais produtos foram vinculados a cada venda. No menu de vendas existe a opcao "Vincular produto a venda".
