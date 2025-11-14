package com.gestaoveiculos.repository;

import com.gestaoveiculos.model.MarcaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<MarcaModel, Long> {
    
    // Buscar marca pelo nome
    Optional<MarcaModel> findByNome(String nome);
    
    // Verificar se existe pelo nome
    boolean existsByNome(String nome);
    
    // Buscar marcas ordenadas por nome
    List<MarcaModel> findAllByOrderByNomeAsc();
}