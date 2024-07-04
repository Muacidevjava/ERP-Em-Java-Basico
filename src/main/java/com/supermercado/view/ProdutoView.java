package com.supermercado.view;


import com.supermercado.model.Produto;

import java.util.Scanner;

public class ProdutoView {

    private Scanner scanner;

    public ProdutoView() {
        scanner = new Scanner(System.in);
    }

    public void mostrarDetalhesDoProduto(Produto produto) {
        System.out.println("Detalhes do Produto:");
        System.out.println("Nome: " + produto.getNome());
        System.out.println("Código: " + produto.getCodigo());
        System.out.println("Preço: " + produto.getPreco());
        System.out.println("Quantidade em Estoque: " + produto.getQuantidadeEmEstoque());
    }

    public Produto criarProduto() {
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o código do produto: ");
        String codigo = scanner.nextLine();

        System.out.print("Digite o preço do produto: ");
        double preco = scanner.nextDouble();

        System.out.print("Digite a quantidade em estoque: ");
        int quantidadeEmEstoque = scanner.nextInt();

        scanner.nextLine();  // Limpar o buffer do scanner

        return new Produto(nome, codigo, preco, quantidadeEmEstoque);
    }

    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }
}