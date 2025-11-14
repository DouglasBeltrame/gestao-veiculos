package com.gestaoveiculos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "veiculos")
public class VeiculoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String modelo;
    
    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private MarcaModel marca;
    
    @Column(name = "ano_fabricacao", nullable = false)
    private Integer anoFabricacao;
    
    @Column(name = "ano_modelo", nullable = false)
    private Integer anoModelo;
    
    @Column(nullable = false)
    private String cor;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
    
    @Column(nullable = false)
    private Integer quilometragem;
    
    @Column(nullable = false, unique = true)
    private String placa;
    
    @Column(nullable = false, unique = true)
    private String chassi;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVeiculoModel status;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(columnDefinition = "TEXT")
    private String observacoes;
    
    // Construtores
    public VeiculoModel() {
        this.dataCadastro = LocalDateTime.now();
        this.status = StatusVeiculoModel.DISPONIVEL;
    }
    
    public VeiculoModel(String modelo, MarcaModel marca, Integer anoFabricacao, Integer anoModelo, 
                       String cor, BigDecimal preco, Integer quilometragem, String placa, 
                       String chassi, String observacoes) {
        this();
        this.modelo = modelo;
        this.marca = marca;
        this.anoFabricacao = anoFabricacao;
        this.anoModelo = anoModelo;
        this.cor = cor;
        this.preco = preco;
        this.quilometragem = quilometragem;
        this.placa = placa;
        this.chassi = chassi;
        this.observacoes = observacoes;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public MarcaModel getMarca() { return marca; }
    public void setMarca(MarcaModel marca) { this.marca = marca; }
    
    public Integer getAnoFabricacao() { return anoFabricacao; }
    public void setAnoFabricacao(Integer anoFabricacao) { this.anoFabricacao = anoFabricacao; }
    
    public Integer getAnoModelo() { return anoModelo; }
    public void setAnoModelo(Integer anoModelo) { this.anoModelo = anoModelo; }
    
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    
    public Integer getQuilometragem() { return quilometragem; }
    public void setQuilometragem(Integer quilometragem) { this.quilometragem = quilometragem; }
    
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    
    public String getChassi() { return chassi; }
    public void setChassi(String chassi) { this.chassi = chassi; }
    
    public StatusVeiculoModel getStatus() { return status; }
    public void setStatus(StatusVeiculoModel status) { this.status = status; }
    
    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }
    
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}