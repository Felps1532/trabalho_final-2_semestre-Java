package com.example.model.dao;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

import com.example.model.Usuario;

public class UsuarioDAO {

    public void cadastrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, perfil, email, senha) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getPerfil());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuario: " + e.getMessage());
        }
    }

    public ArrayList<Usuario> listar() {
        String sql = "SELECT * FROM usuarios";

        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setPerfil(rs.getString("perfil"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar usuarios: " + e.getMessage());
        }

        return usuarios;
    }

    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setPerfil(rs.getString("perfil"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));

                return usuario;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar usuario: " + e.getMessage());
        }

        return null;
    }

    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, perfil = ?, email = ?, senha = ? WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getPerfil());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.setInt(5, usuario.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuario atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar usuario: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuario deletado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar usuario: " + e.getMessage());
        }
    }

    public Usuario autenticar(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setPerfil(rs.getString("perfil"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));

                return usuario;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao efetuar login: " + e.getMessage());
        }

        return null;
    }

    public void alterarSenha(int idUsuario, String senhaAtual, String novaSenha) {
        String sqlBuscarUsuario = "SELECT senha FROM usuarios WHERE id = ?";
        String sqlBuscarSenhasAntigas = "SELECT prev_senha FROM prev_senhas WHERE id_usuario = ? ORDER BY data_entrada DESC LIMIT 3";
        String sqlSalvarSenhaAntiga = "INSERT INTO prev_senhas (id_usuario, prev_senha) VALUES (?, ?)";
        String sqlAtualizarSenha = "UPDATE usuarios SET senha = ? WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();

            PreparedStatement stmtUsuario = conn.prepareStatement(sqlBuscarUsuario);
            stmtUsuario.setInt(1, idUsuario);
            ResultSet rsUsuario = stmtUsuario.executeQuery();

            if (!rsUsuario.next()) {
                JOptionPane.showMessageDialog(null, "Usuário não encontrado!");
                return;
            }

            String senhaDoBanco = rsUsuario.getString("senha");

            if (!senhaDoBanco.equals(senhaAtual)) {
                JOptionPane.showMessageDialog(null, "Senha atual incorreta.");
                return;
            }

            if (senhaDoBanco.equals(novaSenha)) {
                JOptionPane.showMessageDialog(null, "A nova senha não pode ser igual a senha atual.");
                return;
            }

            PreparedStatement stmtSenhasAntigas = conn.prepareStatement(sqlBuscarSenhasAntigas);
            stmtSenhasAntigas.setInt(1, idUsuario);
            ResultSet rsSenhas = stmtSenhasAntigas.executeQuery();

            while (rsSenhas.next()) {
                if (rsSenhas.getString("prev_senha").equals(novaSenha)) {
                    JOptionPane.showMessageDialog(null, "A nova senha não pode repetir uma das 3 últimas senhas.");
                    return;
                }
            }

            PreparedStatement stmtSalvarSenhaAntiga = conn.prepareStatement(sqlSalvarSenhaAntiga);
            stmtSalvarSenhaAntiga.setInt(1, idUsuario);
            stmtSalvarSenhaAntiga.setString(2, senhaDoBanco);
            stmtSalvarSenhaAntiga.executeUpdate();

            PreparedStatement stmtAtualizarSenha = conn.prepareStatement(sqlAtualizarSenha);
            stmtAtualizarSenha.setString(1, novaSenha);
            stmtAtualizarSenha.setInt(2, idUsuario);
            stmtAtualizarSenha.executeUpdate();

            JOptionPane.showMessageDialog(null, "Senha alterada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar senha: " + e.getMessage());
        }
    }
}
