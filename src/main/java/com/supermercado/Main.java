package com.supermercado;


import com.supermercado.controller.ProdutoController;
import com.supermercado.database.DatabaseConnection;
import com.supermercado.view.MainView;
import com.supermercado.view.ProdutoView;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        inicializarBancoDeDados();

        ProdutoView view = new ProdutoView();
        ProdutoController controller = new ProdutoController(view);

        // Inicia a interface gráfica
        new MainView(controller);
    }

    private static void inicializarBancoDeDados() {
        String sql = "CREATE TABLE IF NOT EXISTS produtos (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nome VARCHAR(255) NOT NULL," +
                "codigo VARCHAR(255) NOT NULL UNIQUE," +
                "preco DOUBLE NOT NULL," +
                "quantidadeEmEstoque INT NOT NULL" +
                ")";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}