package com.gestaoveiculos.controller;

import com.gestaoveiculos.model.MarcaModel;
import com.gestaoveiculos.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/marcas")
@CrossOrigin(origins = "*")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    // GET - Buscar todas as marcas
    @GetMapping
    public List<MarcaModel> getAllMarcas() {
        return marcaService.findAll();
    }

    // GET - Buscar marca por ID
    @GetMapping("/{id}")
    public ResponseEntity<MarcaModel> getMarcaById(@PathVariable Long id) {
        Optional<MarcaModel> marca = marcaService.findById(id);
        return marca.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // GET - Buscar marca por nome
    @GetMapping("/search")
    public ResponseEntity<MarcaModel> getMarcaByNome(@RequestParam String nome) {
        Optional<MarcaModel> marca = marcaService.findByNome(nome);
        return marca.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // POST - Criar nova marca
    @PostMapping
    public ResponseEntity<MarcaModel> createMarca(@RequestBody MarcaModel marca) {
        try {
            // Verificar se já existe marca com esse nome
            if (marcaService.existsByNome(marca.getNome())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            MarcaModel novaMarca = marcaService.save(marca);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaMarca);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // PUT - Atualizar marca
    @PutMapping("/{id}")
    public ResponseEntity<MarcaModel> updateMarca(@PathVariable Long id, @RequestBody MarcaModel marcaDetails) {
        Optional<MarcaModel> marcaOptional = marcaService.findById(id);
        
        if (marcaOptional.isPresent()) {
            MarcaModel marca = marcaOptional.get();
            marca.setNome(marcaDetails.getNome());
            
            MarcaModel marcaAtualizada = marcaService.save(marca);
            return ResponseEntity.ok(marcaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - Deletar marca
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarca(@PathVariable Long id) {
        if (marcaService.findById(id).isPresent()) {
            marcaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}