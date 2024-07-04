package com.supermercado.view;

import com.supermercado.controller.ProdutoController;
import com.supermercado.model.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView  extends JFrame {
    private ProdutoController produtoController;
    private JTextArea displayArea;
    private JTextField searchField;
    private JButton adicionarButton;
    private JButton listarButton;
    private JButton buscarButton;
    private JButton atualizarButton;
    private JButton removerButton;

    public MainView(ProdutoController produtoController) {
        super("ERP Supermercado");
        this.produtoController = produtoController;

        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel(new GridLayout(1, 5, 10, 10));

        adicionarButton = new JButton("Adicionar Produto");
        listarButton = new JButton("Listar Produtos");
        buscarButton = new JButton("Buscar Produto");
        atualizarButton = new JButton("Atualizar Produto");
        removerButton = new JButton("Remover Produto");

        controlPanel.add(adicionarButton);
        controlPanel.add(listarButton);
        controlPanel.add(buscarButton);
        controlPanel.add(atualizarButton);
        controlPanel.add(removerButton);

        add(controlPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(new JLabel("Código do Produto:"), BorderLayout.WEST);
        searchField = new JTextField();
        searchPanel.add(searchField, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.SOUTH);

        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarProduto();
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProdutos();
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProduto();
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarProduto();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerProduto();
            }
        });

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void adicionarProduto() {
        ProdutoForm form = new ProdutoForm(this, null);
        Produto produto = form.getProduto();
        if (produto != null) {
            produtoController.adicionarProduto(produto);
            displayArea.setText("Produto adicionado:\n" + produto);
        }
    }

    private void listarProdutos() {
        displayArea.setText("");
        for (Produto produto : produtoController.listarProdutosComoLista()) {
            displayArea.append(produto + "\n");
        }
    }

    private void buscarProduto() {
        String codigo = searchField.getText();
        Produto produto = produtoController.buscarProdutoPorCodigo(codigo);
        if (produto != null) {
            displayArea.setText("Produto encontrado:\n" + produto);
        } else {
            displayArea.setText("Produto não encontrado com o código: " + codigo);
        }
    }

    private void atualizarProduto() {
        String codigo = searchField.getText();
        Produto produto = produtoController.buscarProdutoPorCodigo(codigo);
        if (produto != null) {
            ProdutoForm form = new ProdutoForm(this, produto);
            Produto produtoAtualizado = form.getProduto();
            if (produtoAtualizado != null) {
                produtoController.atualizarEstoque(produtoAtualizado.getCodigo(), produtoAtualizado.getQuantidadeEmEstoque());
                produtoController.atualizarPreco(produtoAtualizado.getCodigo(), produtoAtualizado.getPreco());
                displayArea.setText("Produto atualizado:\n" + produtoAtualizado);
            }
        } else {
            displayArea.setText("Produto não encontrado com o código: " + codigo);
        }
    }

    private void removerProduto() {
        String codigo = searchField.getText();
        produtoController.removerProduto(codigo);
        displayArea.setText("Produto removido com o código: " + codigo);
    }
}

