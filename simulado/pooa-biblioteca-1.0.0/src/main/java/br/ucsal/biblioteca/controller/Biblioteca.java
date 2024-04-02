package br.ucsal.biblioteca.controller;

import java.util.List;

import br.ucsal.biblioteca.model.Emprestimo;
import br.ucsal.biblioteca.model.LembreteThread;

public class Biblioteca {
	LembreteThread lembreteThread = new LembreteThread();
	lembreteThread.start();

	public static List<Emprestimo> getListaDeEmprestimos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
