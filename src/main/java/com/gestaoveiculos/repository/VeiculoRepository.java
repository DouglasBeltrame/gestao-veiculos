package com.gestaoveiculos.repository;

import com.gestaoveiculos.model.VeiculoModel;
import com.gestaoveiculos.model.StatusVeiculoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<VeiculoModel, Long> {
    
    // Buscar por marca
    List<VeiculoModel> findByMarcaId(Long marcaId);
    
    // Buscar por status
    List<VeiculoModel> findByStatus(StatusVeiculoModel status);
    
    // Buscar por ano de fabricação
    List<VeiculoModel> findByAnoFabricacao(Integer anoFabricacao);
    
    // Buscar por intervalo de preço
    List<VeiculoModel> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);
    
    // Buscar por intervalo de ano
    List<VeiculoModel> findByAnoFabricacaoBetween(Integer anoMin, Integer anoMax);
    
    // Buscar por cor
    List<VeiculoModel> findByCorContainingIgnoreCase(String cor);
    
    // Buscar por modelo (nome)
    List<VeiculoModel> findByModeloContainingIgnoreCase(String modelo);
    
    // Buscar por quilometragem máxima
    List<VeiculoModel> findByQuilometragemLessThanEqual(Integer quilometragemMax);
    
    // Buscar veículos disponíveis
    List<VeiculoModel> findByStatusOrderByPrecoAsc(StatusVeiculoModel status);
    
    // Busca avançada com múltiplos filtros
    @Query("SELECT v FROM VeiculoModel v WHERE " +
           "(:marcaId IS NULL OR v.marca.id = :marcaId) AND " +
           "(:status IS NULL OR v.status = :status) AND " +
           "(:anoMin IS NULL OR v.anoFabricacao >= :anoMin) AND " +
           "(:anoMax IS NULL OR v.anoFabricacao <= :anoMax) AND " +
           "(:precoMin IS NULL OR v.preco >= :precoMin) AND " +
           "(:precoMax IS NULL OR v.preco <= :precoMax)")
    List<VeiculoModel> findWithFilters(
        @Param("marcaId") Long marcaId,
        @Param("status") StatusVeiculoModel status,
        @Param("anoMin") Integer anoMin,
        @Param("anoMax") Integer anoMax,
        @Param("precoMin") BigDecimal precoMin,
        @Param("precoMax") BigDecimal precoMax
    );
}