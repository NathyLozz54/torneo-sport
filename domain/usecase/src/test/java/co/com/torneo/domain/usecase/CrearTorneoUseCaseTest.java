package co.com.torneo.domain.usecase;

import co.com.torneo.domain.model.TipoTorneo;
import co.com.torneo.domain.model.Torneo;
import co.com.torneo.domain.model.gateways.TorneoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CrearTorneoUseCaseTest {

    private TorneoRepository torneoRepository;
    private CrearTorneoUseCase crearTorneoUseCase;

    @BeforeEach
    void setUp() {
        torneoRepository = Mockito.mock(TorneoRepository.class);
        crearTorneoUseCase = new CrearTorneoUseCase(torneoRepository);
    }

    @Test
    void crearTorneoPagado_Exitoso() {
        Torneo torneo = new Torneo();
        torneo.setNombre("Torneo Pro");
        torneo.setCategoria("Fútbol");
        torneo.setResponsableId("resp1");
        torneo.setLimiteParticipantes(10);
        torneo.setTipo(TipoTorneo.PAGADO);
        torneo.setPrecioTicket(100);
        torneo.setComisionPorcentaje(10);

        when(torneoRepository.guardarTorneo(any(Torneo.class)))
                .thenReturn(Mono.just(torneo));

        StepVerifier.create(crearTorneoUseCase.crearTorneo(torneo))
                .expectNextMatches(t -> t.getNombre().equals("Torneo Pro"))
                .verifyComplete();

        verify(torneoRepository, times(1)).guardarTorneo(any(Torneo.class));
    }

    @Test
    void crearTorneoPagado_PrecioInvalido() {
        Torneo torneo = new Torneo();
        torneo.setNombre("Torneo Pro");
        torneo.setCategoria("Fútbol");
        torneo.setResponsableId("resp1");
        torneo.setLimiteParticipantes(10);
        torneo.setTipo(TipoTorneo.PAGADO);
        torneo.setPrecioTicket(0);
        torneo.setComisionPorcentaje(10);

        StepVerifier.create(crearTorneoUseCase.crearTorneo(torneo))
                .expectErrorMatches(e -> e instanceof IllegalArgumentException &&
                        e.getMessage().equals("El precio del ticket debe ser mayor a 0"))
                .verify();

        verify(torneoRepository, never()).guardarTorneo(any());
    }

    @Test
    void crearTorneoGratuito_Exitoso() {
        Torneo torneo = new Torneo();
        torneo.setNombre("Torneo Gratis");
        torneo.setCategoria("Básquet");
        torneo.setResponsableId("resp1");
        torneo.setLimiteParticipantes(50);
        torneo.setTipo(TipoTorneo.GRATUITO);

        when(torneoRepository.buscarResponsableYTipo(eq("resp1"), eq(TipoTorneo.GRATUITO)))
                .thenReturn(Flux.empty());

        when(torneoRepository.guardarTorneo(any(Torneo.class)))
                .thenReturn(Mono.just(torneo));

        StepVerifier.create(crearTorneoUseCase.crearTorneo(torneo))
                .expectNextMatches(t -> t.getPrecioTicket() == 0)
                .verifyComplete();

        verify(torneoRepository, times(1)).guardarTorneo(any(Torneo.class));
    }

    @Test
    void crearTorneoGratuito_MaximoAlcanzado() {
        Torneo torneo = new Torneo();
        torneo.setNombre("Torneo Gratis");
        torneo.setCategoria("Básquet");
        torneo.setResponsableId("resp1");
        torneo.setLimiteParticipantes(50);
        torneo.setTipo(TipoTorneo.GRATUITO);

        when(torneoRepository.buscarResponsableYTipo(eq("resp1"), eq(TipoTorneo.GRATUITO)))
                .thenReturn(Flux.just(new Torneo(), new Torneo()));

        StepVerifier.create(crearTorneoUseCase.crearTorneo(torneo))
                .expectErrorMatches(e -> e instanceof IllegalStateException &&
                        e.getMessage().contains("máximo de 2 torneos gratuitos"))
                .verify();

        verify(torneoRepository, never()).guardarTorneo(any());
    }
}
