package co.com.torneo.config;

import co.com.torneo.domain.model.gateways.TorneoRepository;
import co.com.torneo.domain.model.gateways.VentaRepository;

import co.com.torneo.domain.usecase.CrearEtapaVentaUseCase;
import co.com.torneo.domain.usecase.CrearTorneoUseCase;
import co.com.torneo.domain.usecase.CrearVentaUseCase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    private final TorneoRepository torneoRepository;
    private final VentaRepository ventaRepository;

    public UseCasesConfig(TorneoRepository torneoRepository, VentaRepository ventaRepository) {
        this.torneoRepository = torneoRepository;
        this.ventaRepository = ventaRepository;
    }

    @Bean
    public CrearTorneoUseCase crearTorneoUseCase() {
        return new CrearTorneoUseCase(torneoRepository);
    }

    @Bean
    public CrearVentaUseCase crearVentaUseCase(VentaRepository ventaRepository) {
        return new CrearVentaUseCase(ventaRepository);
    }

    @Bean
    public CrearEtapaVentaUseCase crearEtapaVentaUseCase(TorneoRepository torneoRepository) {
        return new CrearEtapaVentaUseCase(torneoRepository);
    }

}
