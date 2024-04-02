package br.ucsal.biblioteca.domain;

import java.util.concurrent.atomic.AtomicInteger;

public class Usuario {

    private static final AtomicInteger contadorId = new AtomicInteger(0); // Contador estático

    private int id;

    private String nome;

    public Usuario(String nome) {
        this.id = contadorId.getAndIncrement();
        this.nome = nome;
    }

    // Métodos getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

}
