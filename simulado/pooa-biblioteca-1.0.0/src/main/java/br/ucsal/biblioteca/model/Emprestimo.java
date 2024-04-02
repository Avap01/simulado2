package br.ucsal.biblioteca.domain;

import java.time.LocalDate;

public class Emprestimo {
    private Usuario usuario;
    private Livro livro;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;
    private final static int PERIODO_EMPRESTIMO_DIAS = 7; // Período padrão de empréstimo

    public Emprestimo(Usuario usuario, Livro livro, LocalDate dataRetirada) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = calcularDataDevolucao(dataRetirada);
    }

    private LocalDate calcularDataDevolucao(LocalDate dataRetirada) {
        return dataRetirada.plusDays(PERIODO_EMPRESTIMO_DIAS);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    // Setter para data de devolução, caso necessário modificar o período de empréstimo
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "usuario=" + usuario.getNome() +
                ", livro=" + livro.getTitulo() +
                ", dataRetirada=" + dataRetirada +
                ", dataDevolucao=" + dataDevolucao +
                '}';
    }
}
