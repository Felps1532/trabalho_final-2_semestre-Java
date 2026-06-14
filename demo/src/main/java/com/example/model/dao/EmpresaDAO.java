package com.example.model.dao;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

import com.example.model.Empresa;

public class EmpresaDAO {

    public void cadastrar(Empresa empresa) {
        String sql = "INSERT INTO empresas (nome_fantasia, razao_social, cnpj, telefone, email) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, empresa.getNomeFantasia());
            stmt.setString(2, empresa.getRazaoSocial());
            stmt.setString(3, empresa.getCnpj());
            stmt.setString(4, empresa.getTelefone());
            stmt.setString(5, empresa.getEmail());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Empresa cadastrada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar empresa: " + e.getMessage());
        }
    }

    public ArrayList<Empresa> listar() {
        String sql = "SELECT * FROM empresas";

        ArrayList<Empresa> empresas = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Empresa empresa = new Empresa();

                empresa.setId(rs.getInt("id"));
                empresa.setNomeFantasia(rs.getString("nome_fantasia"));
                empresa.setRazaoSocial(rs.getString("razao_social"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setTelefone(rs.getString("telefone"));
                empresa.setEmail(rs.getString("email"));

                empresas.add(empresa);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar empresas: " + e.getMessage());
        }

        return empresas;
    }

    public Empresa buscarPorId(int id) {
        String sql = "SELECT * FROM empresas WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Empresa empresa = new Empresa();

                empresa.setId(rs.getInt("id"));
                empresa.setNomeFantasia(rs.getString("nome_fantasia"));
                empresa.setRazaoSocial(rs.getString("razao_social"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setTelefone(rs.getString("telefone"));
                empresa.setEmail(rs.getString("email"));

                return empresa;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar empresa: " + e.getMessage());
        }

        return null;
    }

    public void atualizar(Empresa empresa) {
        String sql = "UPDATE empresas SET nome_fantasia = ?, razao_social = ?, cnpj = ?, telefone = ?, email = ? WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, empresa.getNomeFantasia());
            stmt.setString(2, empresa.getRazaoSocial());
            stmt.setString(3, empresa.getCnpj());
            stmt.setString(4, empresa.getTelefone());
            stmt.setString(5, empresa.getEmail());
            stmt.setInt(6, empresa.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Empresa atualizada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar empresa: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM empresas WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Empresa deletada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar empresa: " + e.getMessage());
        }
    }
}
