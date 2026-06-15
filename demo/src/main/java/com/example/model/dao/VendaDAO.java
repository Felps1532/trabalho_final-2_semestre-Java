package com.example.model.dao;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.sql.ResultSet;

import com.example.model.Contato;
import com.example.model.Empresa;
import com.example.model.Produto;
import com.example.model.Usuario;
import com.example.model.Venda;

public class VendaDAO {

    public void cadastrar(Venda venda) {
        String sql = "INSERT INTO vendas (descricao, valor, status, data_fechamento, id_contato, id_vendedor_resp, id_empresa) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, venda.getDescricao());
            stmt.setDouble(2, venda.getValor());
            stmt.setString(3, venda.getStatus());

            if (venda.getDataFechamento() != null) {
                stmt.setTimestamp(4, Timestamp.valueOf(venda.getDataFechamento()));
            } else {
                stmt.setNull(4, Types.TIMESTAMP);
            }

            if (venda.getContato() != null) {
                stmt.setInt(5, venda.getContato().getId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            if (venda.getVendedorResp() != null) {
                stmt.setInt(6, venda.getVendedorResp().getId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            if (venda.getEmpresa() != null) {
                stmt.setInt(7, venda.getEmpresa().getId());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Venda cadastrada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar venda: " + e.getMessage());
        }
    }

    public ArrayList<Venda> listar() {
        String sql = "SELECT * FROM vendas";

        ArrayList<Venda> vendas = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Venda venda = montarVenda(rs);
                vendas.add(venda);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar vendas: " + e.getMessage());
        }

        return vendas;
    }

    public Venda buscarPorId(int id) {
        String sql = "SELECT * FROM vendas WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return montarVenda(rs);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar venda: " + e.getMessage());
        }

        return null;
    }

    public void atualizar(Venda venda) {
        String sql = "UPDATE vendas SET descricao = ?, valor = ?, status = ?, data_fechamento = ?, id_contato = ?, id_vendedor_resp = ?, id_empresa = ? WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, venda.getDescricao());
            stmt.setDouble(2, venda.getValor());
            stmt.setString(3, venda.getStatus());

            if (venda.getDataFechamento() != null) {
                stmt.setTimestamp(4, Timestamp.valueOf(venda.getDataFechamento()));
            } else {
                stmt.setNull(4, Types.TIMESTAMP);
            }

            if (venda.getContato() != null) {
                stmt.setInt(5, venda.getContato().getId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            if (venda.getVendedorResp() != null) {
                stmt.setInt(6, venda.getVendedorResp().getId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            if (venda.getEmpresa() != null) {
                stmt.setInt(7, venda.getEmpresa().getId());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }

            stmt.setInt(8, venda.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Venda atualizada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar venda: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sqlVendaProdutos = "DELETE FROM venda_produtos WHERE id_venda = ?";
        String sql = "DELETE FROM vendas WHERE id = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmtVendaProdutos = conn.prepareStatement(sqlVendaProdutos);
            stmtVendaProdutos.setInt(1, id);
            stmtVendaProdutos.executeUpdate();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Venda deletada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar venda: " + e.getMessage());
        }
    }

    public void vincularProduto(int idVenda, int idProduto) {
        String sql = "INSERT INTO venda_produtos (id_venda, id_produto) VALUES (?, ?)";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idVenda);
            stmt.setInt(2, idProduto);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto vinculado a venda com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao vincular produto a venda: " + e.getMessage());
        }
    }

    public void desvincularProduto(int idVenda, int idProduto) {
        String sql = "DELETE FROM venda_produtos WHERE id_venda = ? AND id_produto = ?";

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idVenda);
            stmt.setInt(2, idProduto);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto desvinculado da venda com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Este produto nao estava vinculado a venda.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao desvincular produto da venda: " + e.getMessage());
        }
    }

    public ArrayList<Produto> listarProdutosDaVenda(int idVenda) {
        String sql = "SELECT p.* FROM produtos p INNER JOIN venda_produtos vp ON p.id = vp.id_produto WHERE vp.id_venda = ?";

        ArrayList<Produto> produtos = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idVenda);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));

                produtos.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos da venda: " + e.getMessage());
        }

        return produtos;
    }

    private Venda montarVenda(ResultSet rs) throws SQLException {
        Venda venda = new Venda();

        venda.setId(rs.getInt("id"));
        venda.setDescricao(rs.getString("descricao"));
        venda.setValor(rs.getDouble("valor"));
        venda.setStatus(rs.getString("status"));

        Timestamp dataFechamento = rs.getTimestamp("data_fechamento");
        if (dataFechamento != null) {
            venda.setDataFechamento(dataFechamento.toLocalDateTime());
        }

        int idContato = rs.getInt("id_contato");
        if (!rs.wasNull()) {
            Contato contato = new Contato();
            contato.setId(idContato);
            venda.setContato(contato);
        }

        int idVendedorResp = rs.getInt("id_vendedor_resp");
        if (!rs.wasNull()) {
            Usuario vendedorResp = new Usuario();
            vendedorResp.setId(idVendedorResp);
            venda.setVendedorResp(vendedorResp);
        }

        int idEmpresa = rs.getInt("id_empresa");
        if (!rs.wasNull()) {
            Empresa empresa = new Empresa();
            empresa.setId(idEmpresa);
            venda.setEmpresa(empresa);
        }

        return venda;
    }
}
