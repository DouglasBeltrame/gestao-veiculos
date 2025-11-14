package com.gestaoveiculos.service;

import com.gestaoveiculos.model.MarcaModel;
import com.gestaoveiculos.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;
    
    // Buscar todas as marcas
    public List<MarcaModel> findAll() {
        return marcaRepository.findAll();
    }
    
    // Buscar marca por ID
    public Optional<MarcaModel> findById(Long id) {
        return marcaRepository.findById(id);
    }
    
    // Buscar marca por nome
    public Optional<MarcaModel> findByNome(String nome) {
        return marcaRepository.findByNome(nome);
    }
    
    // Salvar marca
    public MarcaModel save(MarcaModel marca) {
        return marcaRepository.save(marca);
    }
    
    // Deletar marca
    public void deleteById(Long id) {
        marcaRepository.deleteById(id);
    }
    
    // Verificar se existe
    public boolean existsByNome(String nome) {
        return marcaRepository.existsByNome(nome);
    }
}