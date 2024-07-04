package com.supermercado.controller;

import com.supermercado.database.DatabaseConnection;
import com.supermercado.model.Produto;
import com.supermercado.view.ProdutoView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    private ProdutoView view;

    public ProdutoController(ProdutoView view) {
        this.view = view;
    }

    public void adicionarProduto(Produto produto) {
        String sql = "INSERT INTO produtos (nome, codigo, preco, quantidadeEmEstoque) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getCodigo());
            statement.setDouble(3, produto.getPreco());
            statement.setInt(4, produto.getQuantidadeEmEstoque());
            statement.executeUpdate();
            view.mostrarMensagem("Produto adicionado com sucesso: " + produto.getNome());
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    public void listarProdutos() {
        String sql = "SELECT * FROM produtos";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Produto> produtos = new ArrayList<>();
            while (resultSet.next()) {
                Produto produto = new Produto(
                        resultSet.getString("nome"),
                        resultSet.getString("codigo"),
                        resultSet.getDouble("preco"),
                        resultSet.getInt("quantidadeEmEstoque")
                );
                produtos.add(produto);
            }
            if (produtos.isEmpty()) {
                view.mostrarMensagem("Nenhum produto encontrado.");
            } else {
                produtos.forEach(view::mostrarDetalhesDoProduto);
            }
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao listar produtos: " + e.getMessage());
        }
    }

    public Produto buscarProdutoPorCodigo(String codigo) {
        String sql = "SELECT * FROM produtos WHERE codigo = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, codigo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Produto(
                            resultSet.getString("nome"),
                            resultSet.getString("codigo"),
                            resultSet.getDouble("preco"),
                            resultSet.getInt("quantidadeEmEstoque")
                    );
                } else {
                    view.mostrarMensagem("Produto não encontrado com o código: " + codigo);
                    return null;
                }
            }
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao buscar produto: " + e.getMessage());
            return null;
        }
    }

    public void atualizarEstoque(String codigo, int quantidade) {
        Produto produto = buscarProdutoPorCodigo(codigo);
        if (produto != null) {
            produto.atualizarEstoque(quantidade);
            String sql = "UPDATE produtos SET quantidadeEmEstoque = ? WHERE codigo = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, produto.getQuantidadeEmEstoque());
                statement.setString(2, produto.getCodigo());
                statement.executeUpdate();
                view.mostrarMensagem("Estoque atualizado para o produto: " + produto.getNome());
            } catch (SQLException e) {
                view.mostrarMensagem("Erro ao atualizar estoque: " + e.getMessage());
            }
        }
    }

    public void removerProduto(String codigo) {
        String sql = "DELETE FROM produtos WHERE codigo = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, codigo);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                view.mostrarMensagem("Produto removido com sucesso: " + codigo);
            } else {
                view.mostrarMensagem("Produto não encontrado com o código: " + codigo);
            }
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao remover produto: " + e.getMessage());
        }
    }

    public List<Produto> listarProdutosComoLista() {
        String sql = "SELECT * FROM produtos";
        List<Produto> produtos = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Produto produto = new Produto(
                        resultSet.getString("nome"),
                        resultSet.getString("codigo"),
                        resultSet.getDouble("preco"),
                        resultSet.getInt("quantidadeEmEstoque")
                );
                produtos.add(produto);
            }
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    public void atualizarPreco(String codigo, double novoPreco) {
        Produto produto = buscarProdutoPorCodigo(codigo);
        if (produto != null) {
            produto.setPreco(novoPreco);
            String sql = "UPDATE produtos SET preco = ? WHERE codigo = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDouble(1, novoPreco);
                statement.setString(2, produto.getCodigo());
                statement.executeUpdate();
                view.mostrarMensagem("Preço atualizado para o produto: " + produto.getNome());
            } catch (SQLException e) {
                view.mostrarMensagem("Erro ao atualizar preço: " + e.getMessage());
            }
        }
    }
}