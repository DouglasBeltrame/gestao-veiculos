package com.gestaoveiculos.model;

public enum StatusVeiculoModel {
    DISPONIVEL("Disponível"),
    VENDIDO("Vendido"), 
    MANUTENCAO("Em Manutenção"),
    RESERVADO("Reservado");
    
    private final String descricao;
    
    StatusVeiculoModel(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}