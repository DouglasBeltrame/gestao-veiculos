package com.gestaoveiculos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "marcas")
public class MarcaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nome;
    
    // Construtor padrão
    public MarcaModel() {}
    
    // Construtor com parâmetros
    public MarcaModel(String nome) {
        this.nome = nome;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
}
