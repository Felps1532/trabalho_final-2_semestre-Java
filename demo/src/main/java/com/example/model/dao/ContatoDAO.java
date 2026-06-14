package com.example.model.dao;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.sql.ResultSet;

import com.example.model.Contato;
import com.example.model.Empresa;

public class ContatoDAO {

    public void cadastrar(Contato contato) {
        String sql = "INSERT INTO contatos (nome, telefone, email, id_empresa, temperatura) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());

            if (contato.getEmpresa() != null) {
                stmt.setInt(4, contato.getEmpresa().getId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setString(5, contato.getTemperatura());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Contato cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar contato: " + e.getMessage());
        }
    }

    public ArrayList<Contato> listar() {
        String sql = "SELECT * FROM contatos";

        ArrayList<Contato> contatos = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Contato contato = new Contato();

                contato.setId(rs.getInt("id"));
                contato.setNome(rs.getString("nome"));
                contato.setTelefone(rs.getString("telefone"));
                contato.setEmail(rs.getString("email"));
                contato.setTemperatura(rs.getString("temperatura"));

                int idEmpresa = rs.getInt("id_empresa");
                if (!rs.wasNull()) {
                    Empresa empresa = new Empresa();
                    empresa.setId(idEmpresa);
                    contato.setEmpresa(empresa);
                }

                contatos.add(contato);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar contatos: " + e.getMessage());
        }

        return contatos;
    }

    public Contato buscarPorId(int id) {
        String sql = "SELECT * FROM contatos WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Contato contato = new Contato();

                contato.setId(rs.getInt("id"));
                contato.setNome(rs.getString("nome"));
                contato.setTelefone(rs.getString("telefone"));
                contato.setEmail(rs.getString("email"));
                contato.setTemperatura(rs.getString("temperatura"));

                int idEmpresa = rs.getInt("id_empresa");
                if (!rs.wasNull()) {
                    Empresa empresa = new Empresa();
                    empresa.setId(idEmpresa);
                    contato.setEmpresa(empresa);
                }

                return contato;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar contato: " + e.getMessage());
        }

        return null;
    }

    public void atualizar(Contato contato) {
        String sql = "UPDATE contatos SET nome = ?, telefone = ?, email = ?, id_empresa = ?, temperatura = ? WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());

            if (contato.getEmpresa() != null) {
                stmt.setInt(4, contato.getEmpresa().getId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setString(5, contato.getTemperatura());
            stmt.setInt(6, contato.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Contato atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar contato: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM contatos WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Contato deletado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar contato: " + e.getMessage());
        }
    }
}
