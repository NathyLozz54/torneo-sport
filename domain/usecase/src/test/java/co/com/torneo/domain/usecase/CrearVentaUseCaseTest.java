package co.com.torneo.domain.usecase;

import co.com.torneo.domain.model.TipoTorneo;
import co.com.torneo.domain.model.Venta;
import co.com.torneo.domain.model.gateways.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CrearVentaUseCaseTest {

    private VentaRepository ventaRepository;
    private CrearVentaUseCase crearVentaUseCase;

    @BeforeEach
    void setUp() {
        ventaRepository = Mockito.mock(VentaRepository.class);
        crearVentaUseCase = new CrearVentaUseCase(ventaRepository);
    }

    @Test
    void guardarVenta_Exitoso() {
        Venta venta = new Venta();
        venta.setId("1");
        venta.setTorneoId("torneo1");
        venta.setJugadorId("cliente1");
        venta.setTipoTorneo(TipoTorneo.PAGADO);
        venta.setMonto(BigDecimal.valueOf(100.00));
        venta.setFechaVenta(LocalDateTime.now());
        venta.setMetodoPago("TARJETA");
        venta.setEstado("COMPLETADO");

        when(ventaRepository.guardarVenta(any(Venta.class)))
                .thenReturn(Mono.just(venta));

        StepVerifier.create(crearVentaUseCase.guardarVenta(venta))
                .expectNextMatches(v -> "1".equals(v.getId())
                        && BigDecimal.valueOf(100.00).compareTo(v.getMonto()) == 0)
                .verifyComplete();

        verify(ventaRepository, times(1)).guardarVenta(any(Venta.class));
    }

    @Test
    void buscarVentas_Exitoso() {
        Venta v1 = new Venta();
        v1.setId("1");
        v1.setTorneoId("torneo1");
        v1.setJugadorId("jug1");
        v1.setTipoTorneo(TipoTorneo.PAGADO);
        v1.setMonto(BigDecimal.valueOf(50.0));
        v1.setFechaVenta(LocalDateTime.now());
        v1.setMetodoPago("TARJETA");
        v1.setEstado("COMPLETADO");

        Venta v2 = new Venta();
        v2.setId("2");
        v2.setTorneoId("torneo2");
        v2.setJugadorId("jug2");
        v2.setTipoTorneo(TipoTorneo.PAGADO);
        v2.setMonto(BigDecimal.valueOf(75.0));
        v2.setFechaVenta(LocalDateTime.now());
        v2.setMetodoPago("PAYPAL");
        v2.setEstado("COMPLETADO");

        when(ventaRepository.buscarVentas()).thenReturn(Flux.just(v1, v2));

        StepVerifier.create(crearVentaUseCase.buscarVentas())
                .expectNextMatches(v -> "1".equals(v.getId()))
                .expectNextMatches(v -> "2".equals(v.getId()))
                .verifyComplete();

        verify(ventaRepository, times(1)).buscarVentas();
    }

    @Test
    void buscarVentaPorId_Encontrada() {
        Venta venta = new Venta();
        venta.setId("1");
        venta.setTorneoId("torneo1");
        venta.setJugadorId("cliente1");
        venta.setTipoTorneo(TipoTorneo.PAGADO);
        venta.setMonto(BigDecimal.valueOf(100.0));
        venta.setFechaVenta(LocalDateTime.now());
        venta.setMetodoPago("TARJETA");
        venta.setEstado("COMPLETADO");

        when(ventaRepository.buscarVentaPorId("1")).thenReturn(Mono.just(venta));

        StepVerifier.create(crearVentaUseCase.buscarVentaPorId("1"))
                .expectNextMatches(v -> "1".equals(v.getId()))
                .verifyComplete();

        verify(ventaRepository, times(1)).buscarVentaPorId("1");
    }

    @Test
    void buscarVentaPorId_NoEncontrada() {
        when(ventaRepository.buscarVentaPorId("99")).thenReturn(Mono.empty());

        StepVerifier.create(crearVentaUseCase.buscarVentaPorId("99"))
                .verifyComplete();

        verify(ventaRepository, times(1)).buscarVentaPorId("99");
    }
}
