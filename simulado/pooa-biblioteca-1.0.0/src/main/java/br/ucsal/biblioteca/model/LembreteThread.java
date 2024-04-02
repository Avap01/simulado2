package br.ucsal.biblioteca.model;

import java.time.LocalDate;
import java.util.List;

import br.ucsal.biblioteca.controller.Biblioteca;

public class LembreteThread extends Thread {
    private static final long INTERVALO_VERIFICACAO = 24 * 60 * 60 * 1000; 

    @Override
    public void run() {
        while (true) {
            verificarLembretes();
            try {
                Thread.sleep(INTERVALO_VERIFICACAO);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void verificarLembretes() {
    	 List<Emprestimo> listaDeEmprestimos = Biblioteca.getListaDeEmprestimos();
     
        for (Emprestimo emprestimo : Biblioteca.getListaDeEmprestimos()) {
            LocalDate dataAtual = LocalDate.now();
            LocalDate dataDevolucao = emprestimo.getDataDevolucao();
            
            if (emprestimo.getDataDevolucao().isBefore(dataAtual)) {
                
            	 System.out.println("Lembrete: O prazo de devolução do livro '" + emprestimo.getLivro().getTitulo() + "' expirou.");
            }
        }
    }
}