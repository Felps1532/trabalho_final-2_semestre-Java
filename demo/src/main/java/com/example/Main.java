package com.example;

import com.example.model.dao.UsuarioDAO;
import com.example.model.Usuario;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String email = JOptionPane.showInputDialog("Email");
        String senha = JOptionPane.showInputDialog("Senha");

        Usuario usuarioLogado = usuarioDAO.autenticar(email, senha);

        if (usuarioLogado != null) {
            JOptionPane.showMessageDialog(null,
                    "Login realizado com sucesso!\n\nSeja bem-vindo(a), " + usuarioLogado.getNome());
        } else {
            JOptionPane.showMessageDialog(null, "Usuário não encontrado!\nEmail ou senha incorretos!");
        }
    }
}