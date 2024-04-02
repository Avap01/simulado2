package br.ucsal.biblioteca.view;

import br.ucsal.biblioteca.model.Emprestimo;
import br.ucsal.biblioteca.model.Livro;
import br.ucsal.biblioteca.model.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.reflect.Field;

// Classes Livro e Usuario como fornecido anteriormente

public class SistemaBiblioteca {



    public void iniciarConsole() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n--- Sistema de Gerenciamento de Biblioteca ---");
            System.out.println("1. Adicionar Livro");
            System.out.println("2. Adicionar Usuário");
            System.out.println("3. Empréstimo de Livro");
            System.out.println("4. Devolução de Livro");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    this.adicionarLivroConsole();
                    break;
                case 2:
                    this.adicionarUsuarioConsole();
                    break;
                case 3:
                    emprestarLivroConsole();
                    break;
                case 4:
                    devolverLivroConsole();
                    break;
                case 5:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
        scanner.close();
    }

    private void adicionarLivroConsole() {
        System.out.println("\n--- Adicionar Livro ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        
        if (!isValid(titulo)) {
            System.out.println("Título do livro deve ter pelo menos 3 caracteres.");
            return;
        }
        
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Ano de Publicação: ");
        int ano = scanner.nextInt();
        Livro livro = new Livro(titulo, autor, ano);
        this.adicionarLivro(livro);
        System.out.println("Livro adicionado com sucesso.");
    }

    private void adicionarUsuarioConsole() {
        System.out.println("\n--- Registrar Usuário ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        Usuario usuario = new Usuario(nome);
        this.adicionarUsuario(usuario);
        System.out.println("ID Usuário: "+usuario.getId());
        System.out.println("Usuário registrado com sucesso.");
    }


    private void emprestarLivroConsole() {
        System.out.println("\n--- Empréstimo de Livro ---");

        Usuario usuario = null;
        while (usuario == null) {
            System.out.print("ID do Usuário: ");
            int idUsuario = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do scanner
            usuario = usuarios.stream()
                    .filter(u -> u.getId() == idUsuario)
                    .findFirst()
                    .orElse(null);
            if(usuario == null) {
                System.out.println("Usuário não encontrado.");
            }
            if(idUsuario == -1){
                break;
            }
        }

        if(usuario != null) {
            System.out.println("Usuário: " + usuario.getNome());

            System.out.print("ID do Livro: ");
            int idLivro = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do scanner

            Livro livroSelecionado = livros.stream()
                    .filter(l -> l.getId() == idLivro)
                    .findFirst()
                    .orElse(null);

            if (livroSelecionado == null) {
                System.out.println("Livro não encontrado.");
                return;
            }

            if (!livroSelecionado.isDisponivel()) {
                System.out.println("Livro não disponível.");
                return;
            }

            System.out.println("Livro: " + livroSelecionado.getTitulo() + ". Confirmar empréstimo? (s/n)");
            String confirmacao = scanner.nextLine();
            if (confirmacao.equalsIgnoreCase("s")) {
                livroSelecionado.setDisponivel(false);
                adicionarEmprestimo(new Emprestimo(usuario, livroSelecionado, LocalDate.now()));
                System.out.println("Emprestimo efetuado com sucesso.");
                } else {
                    System.out.println("Emprestimo cancelado.");
                }

        }
    }



    private void devolverLivroConsole() {
        System.out.println("\n--- Devolução de Livro ---");
        System.out.print("ID do Livro: ");
        int idLivro = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer do scanner

        Emprestimo emprestimoParaRemover = emprestimos.stream()
                .filter(e -> e.getLivro().getId() == idLivro && !e.getLivro().isDisponivel())
                .findFirst()
                .orElse(null);

        if (emprestimoParaRemover == null) {
            System.out.println("Empréstimo não encontrado ou livro já está disponível.");
            return;
        }

        Livro livroParaDevolver = emprestimoParaRemover.getLivro();
        Usuario usuarioQueDevolve = emprestimoParaRemover.getUsuario();
        System.out.println("Livro: " + livroParaDevolver.getTitulo() + " emprestado por " + usuarioQueDevolve.getNome());
        System.out.println("Data de devolução prevista: " + emprestimoParaRemover.getDataDevolucao());

        System.out.println("Confirmar devolução? (s/n)");
        String confirmacao = scanner.nextLine().trim().toLowerCase();

        if (!confirmacao.equals("s")) {
            System.out.println("Devolução cancelada.");
            return;
        }

        livroParaDevolver.setDisponivel(true);
        removerEmprestimo(emprestimoParaRemover);
        System.out.println("Livro devolvido com sucesso.");
    }

    private void listarUsuariosConsole() {
        System.out.println("\n--- Lista de Usuários ---");
        Usuario[] usuarios;
		for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getId());
            Class<? extends Usuario> usuarioClass = usuario.getClass();
            Field[] fields = usuarioClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(usuario);
                    System.out.println(field.getName() + ": " + value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            System.out.println();
        }
    }
    private boolean isValid(String value) {
        try {
            
            MinLength minLengthAnnotation = getClass().getDeclaredField(value).getAnnotation(MinLength.class);
            
            return value.length() >= minLengthAnnotation.value();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
    }



}
