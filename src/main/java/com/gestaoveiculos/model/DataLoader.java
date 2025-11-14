package com.gestaoveiculos.model;

import com.gestaoveiculos.repository.MarcaRepository;
import com.gestaoveiculos.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private MarcaRepository marcaRepository;
    
    @Autowired
    private VeiculoRepository veiculoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Carregar marcas iniciais
        if (marcaRepository.count() == 0) {
            MarcaModel toyota = new MarcaModel("Toyota");
            MarcaModel honda = new MarcaModel("Honda");
            MarcaModel ford = new MarcaModel("Ford");
            MarcaModel chevrolet = new MarcaModel("Chevrolet");
            MarcaModel volkswagen = new MarcaModel("Volkswagen");
            
            marcaRepository.save(toyota);
            marcaRepository.save(honda);
            marcaRepository.save(ford);
            marcaRepository.save(chevrolet);
            marcaRepository.save(volkswagen);
            
            // Carregar veículos iniciais
            VeiculoModel veiculo1 = new VeiculoModel(
                "Corolla", toyota, 2022, 2023, "Prata", 
                new BigDecimal("85000.00"), 15000, "ABC1D23", "9BWZZZ377VT004251", 
                "Veículo em excelente estado, único dono"
            );
            
            VeiculoModel veiculo2 = new VeiculoModel(
                "Civic", honda, 2021, 2022, "Preto", 
                new BigDecimal("78000.00"), 22000, "DEF4G56", "9BWZZZ377VT004252",
                "Completo, ar-condicionado digital"
            );
            
            VeiculoModel veiculo3 = new VeiculoModel(
                "Fiesta", ford, 2020, 2020, "Branco", 
                new BigDecimal("45000.00"), 35000, "GHI7J89", "9BWZZZ377VT004253",
                "Economico, baixo consumo"
            );
            
            veiculoRepository.save(veiculo1);
            veiculoRepository.save(veiculo2);
            veiculoRepository.save(veiculo3);
        }
    }
}