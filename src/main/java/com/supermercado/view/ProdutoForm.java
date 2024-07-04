package com.supermercado.view;

import com.supermercado.model.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProdutoForm  extends JFrame {
    private JTextField nomeField;
    private JTextField codigoField;
    private JTextField precoField;
    private JTextField quantidadeField;
    private JButton salvarButton;
    private JButton cancelarButton;
    private Produto produto;

    public ProdutoForm(JFrame parent, Produto produto) {
        super("Formulário de Produto");
        this.produto = produto;

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        formPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField(20);
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Código:"));
        codigoField = new JTextField(20);
        formPanel.add(codigoField);

        formPanel.add(new JLabel("Preço:"));
        precoField = new JTextField(20);
        formPanel.add(precoField);

        formPanel.add(new JLabel("Quantidade em Estoque:"));
        quantidadeField = new JTextField(20);
        formPanel.add(quantidadeField);

        salvarButton = new JButton("Salvar");
        cancelarButton = new JButton("Cancelar");

        formPanel.add(salvarButton);
        formPanel.add(cancelarButton);

        add(formPanel, BorderLayout.CENTER);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSave();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        if (produto != null) {
            nomeField.setText(produto.getNome());
            codigoField.setText(produto.getCodigo());
            precoField.setText(String.valueOf(produto.getPreco()));
            quantidadeField.setText(String.valueOf(produto.getQuantidadeEmEstoque()));
            codigoField.setEnabled(false); // Código não pode ser alterado
        }

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void onSave() {
        String nome = nomeField.getText();
        String codigo = codigoField.getText();
        double preco = Double.parseDouble(precoField.getText());
        int quantidade = Integer.parseInt(quantidadeField.getText());

        if (produto == null) {
            produto = new Produto(nome, codigo, preco, quantidade);
        } else {
            produto.setNome(nome);
            produto.setPreco(preco);
            produto.setQuantidadeEmEstoque(quantidade);
        }

        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public Produto getProduto() {
        return produto;
    }
}
