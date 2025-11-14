package com.gestaoveiculos.controller;

import com.gestaoveiculos.model.VeiculoModel;
import com.gestaoveiculos.model.StatusVeiculoModel;
import com.gestaoveiculos.service.VeiculoService;
import com.gestaoveiculos.service.MarcaService;
import com.gestaoveiculos.model.MarcaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/veiculos")
@CrossOrigin(origins = "*")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;
    
    @Autowired
    private MarcaService marcaService;

    // GET - Buscar todos os veículos
    @GetMapping
    public List<VeiculoModel> getAllVeiculos() {
        return veiculoService.findAll();
    }

    // GET - Buscar veículo por ID
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoModel> getVeiculoById(@PathVariable Long id) {
        Optional<VeiculoModel> veiculo = veiculoService.findById(id);
        return veiculo.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // POST - Criar novo veículo
    @PostMapping
    public ResponseEntity<VeiculoModel> createVeiculo(@RequestBody VeiculoModel veiculo) {
        try {
            // Verificar se a marca existe
            if (veiculo.getMarca() == null || veiculo.getMarca().getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            
            Optional<MarcaModel> marca = marcaService.findById(veiculo.getMarca().getId());
            if (marca.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            
            // Verificar se placa já existe
            if (veiculoService.existsByPlaca(veiculo.getPlaca())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            // Verificar se chassi já existe
            if (veiculoService.existsByChassi(veiculo.getChassi())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            veiculo.setMarca(marca.get());
            VeiculoModel novoVeiculo = veiculoService.save(veiculo);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoVeiculo);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // PUT - Atualizar veículo
    @PutMapping("/{id}")
    public ResponseEntity<VeiculoModel> updateVeiculo(@PathVariable Long id, @RequestBody VeiculoModel veiculoDetails) {
        Optional<VeiculoModel> veiculoOptional = veiculoService.findById(id);
        
        if (veiculoOptional.isPresent()) {
            VeiculoModel veiculo = veiculoOptional.get();
            
            // Atualizar campos
            veiculo.setModelo(veiculoDetails.getModelo());
            veiculo.setAnoFabricacao(veiculoDetails.getAnoFabricacao());
            veiculo.setAnoModelo(veiculoDetails.getAnoModelo());
            veiculo.setCor(veiculoDetails.getCor());
            veiculo.setPreco(veiculoDetails.getPreco());
            veiculo.setQuilometragem(veiculoDetails.getQuilometragem());
            veiculo.setObservacoes(veiculoDetails.getObservacoes());
            
            // Atualizar marca se fornecida
            if (veiculoDetails.getMarca() != null && veiculoDetails.getMarca().getId() != null) {
                Optional<MarcaModel> marca = marcaService.findById(veiculoDetails.getMarca().getId());
                marca.ifPresent(veiculo::setMarca);
            }
            
            VeiculoModel veiculoAtualizado = veiculoService.save(veiculo);
            return ResponseEntity.ok(veiculoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH - Atualizar status do veículo
    @PatchMapping("/{id}/status")
    public ResponseEntity<VeiculoModel> updateStatus(@PathVariable Long id, @RequestParam StatusVeiculoModel status) {
        VeiculoModel veiculoAtualizado = veiculoService.updateStatus(id, status);
        if (veiculoAtualizado != null) {
            return ResponseEntity.ok(veiculoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - Deletar veículo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeiculo(@PathVariable Long id) {
        if (veiculoService.findById(id).isPresent()) {
            veiculoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // FILTROS AVANÇADOS
    
    // GET - Buscar por marca
    @GetMapping("/marca/{marcaId}")
    public List<VeiculoModel> getVeiculosByMarca(@PathVariable Long marcaId) {
        return veiculoService.findByMarcaId(marcaId);
    }

    // GET - Buscar por status
    @GetMapping("/status/{status}")
    public List<VeiculoModel> getVeiculosByStatus(@PathVariable StatusVeiculoModel status) {
        return veiculoService.findByStatus(status);
    }

    // GET - Buscar por ano
    @GetMapping("/ano/{ano}")
    public List<VeiculoModel> getVeiculosByAno(@PathVariable Integer ano) {
        return veiculoService.findByAnoFabricacao(ano);
    }

    // GET - Buscar por intervalo de preço
    @GetMapping("/preco")
    public List<VeiculoModel> getVeiculosByPrecoRange(
            @RequestParam BigDecimal precoMin, 
            @RequestParam BigDecimal precoMax) {
        return veiculoService.findByPrecoBetween(precoMin, precoMax);
    }

    // GET - Buscar por intervalo de ano
    @GetMapping("/ano")
    public List<VeiculoModel> getVeiculosByAnoRange(
            @RequestParam Integer anoMin, 
            @RequestParam Integer anoMax) {
        return veiculoService.findByAnoBetween(anoMin, anoMax);
    }

    // GET - Buscar por modelo
    @GetMapping("/modelo/{modelo}")
    public List<VeiculoModel> getVeiculosByModelo(@PathVariable String modelo) {
        return veiculoService.findByModelo(modelo);
    }

    // GET - Buscar por cor
    @GetMapping("/cor/{cor}")
    public List<VeiculoModel> getVeiculosByCor(@PathVariable String cor) {
        return veiculoService.findByCor(cor);
    }

    // GET - Buscar veículos disponíveis ordenados por preço
    @GetMapping("/disponiveis")
    public List<VeiculoModel> getVeiculosDisponiveis() {
        return veiculoService.findDisponiveisOrderByPreco();
    }

    // GET - Busca avançada com múltiplos filtros
    @GetMapping("/filtros")
    public List<VeiculoModel> getVeiculosWithFilters(
            @RequestParam(required = false) Long marcaId,
            @RequestParam(required = false) StatusVeiculoModel status,
            @RequestParam(required = false) Integer anoMin,
            @RequestParam(required = false) Integer anoMax,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax) {
        
        return veiculoService.findWithFilters(marcaId, status, anoMin, anoMax, precoMin, precoMax);
    }
}