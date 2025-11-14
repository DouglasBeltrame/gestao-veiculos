package com.gestaoveiculos.service;

import com.gestaoveiculos.model.VeiculoModel;
import com.gestaoveiculos.model.StatusVeiculoModel;
import com.gestaoveiculos.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;
    
    // Buscar todos os veículos
    public List<VeiculoModel> findAll() {
        return veiculoRepository.findAll();
    }
    
    // Buscar veículo por ID
    public Optional<VeiculoModel> findById(Long id) {
        return veiculoRepository.findById(id);
    }
    
    // Salvar veículo
    public VeiculoModel save(VeiculoModel veiculo) {
        return veiculoRepository.save(veiculo);
    }
    
    // Deletar veículo
    public void deleteById(Long id) {
        veiculoRepository.deleteById(id);
    }
    
    // Buscar por marca
    public List<VeiculoModel> findByMarcaId(Long marcaId) {
        return veiculoRepository.findByMarcaId(marcaId);
    }
    
    // Buscar por status
    public List<VeiculoModel> findByStatus(StatusVeiculoModel status) {
        return veiculoRepository.findByStatus(status);
    }
    
    // Buscar por ano
    public List<VeiculoModel> findByAnoFabricacao(Integer ano) {
        return veiculoRepository.findByAnoFabricacao(ano);
    }
    
    // Buscar por intervalo de preço
    public List<VeiculoModel> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax) {
        return veiculoRepository.findByPrecoBetween(precoMin, precoMax);
    }
    
    // Buscar por intervalo de ano
    public List<VeiculoModel> findByAnoBetween(Integer anoMin, Integer anoMax) {
        return veiculoRepository.findByAnoFabricacaoBetween(anoMin, anoMax);
    }
    
    // Buscar por modelo
    public List<VeiculoModel> findByModelo(String modelo) {
        return veiculoRepository.findByModeloContainingIgnoreCase(modelo);
    }
    
    // Buscar por cor
    public List<VeiculoModel> findByCor(String cor) {
        return veiculoRepository.findByCorContainingIgnoreCase(cor);
    }
    
    // Buscar veículos disponíveis ordenados por preço
    public List<VeiculoModel> findDisponiveisOrderByPreco() {
        return veiculoRepository.findByStatusOrderByPrecoAsc(StatusVeiculoModel.DISPONIVEL);
    }
    
    // Busca avançada com filtros
    public List<VeiculoModel> findWithFilters(Long marcaId, StatusVeiculoModel status, 
                                             Integer anoMin, Integer anoMax, 
                                             BigDecimal precoMin, BigDecimal precoMax) {
        return veiculoRepository.findWithFilters(marcaId, status, anoMin, anoMax, precoMin, precoMax);
    }
    
    // Verificar se placa existe
    public boolean existsByPlaca(String placa) {
        return veiculoRepository.findAll().stream()
                .anyMatch(v -> v.getPlaca().equalsIgnoreCase(placa));
    }
    
    // Verificar se chassi existe
    public boolean existsByChassi(String chassi) {
        return veiculoRepository.findAll().stream()
                .anyMatch(v -> v.getChassi().equalsIgnoreCase(chassi));
    }
    
    // Atualizar status do veículo
    public VeiculoModel updateStatus(Long id, StatusVeiculoModel status) {
        Optional<VeiculoModel> veiculoOptional = veiculoRepository.findById(id);
        if (veiculoOptional.isPresent()) {
            VeiculoModel veiculo = veiculoOptional.get();
            veiculo.setStatus(status);
            return veiculoRepository.save(veiculo);
        }
        return null;
    }
}