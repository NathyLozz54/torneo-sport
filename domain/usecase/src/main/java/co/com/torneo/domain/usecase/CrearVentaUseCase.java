package co.com.torneo.domain.usecase;

import co.com.torneo.domain.model.Venta;
import co.com.torneo.domain.model.gateways.VentaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class CrearVentaUseCase {

    private final VentaRepository ventaRepository;

    public  Mono<Venta> guardarVenta(Venta venta) {
        return ventaRepository.guardarVenta(venta);
    }

    public Flux<Venta> buscarVentas() {
        return ventaRepository.buscarVentas();
    }

    public Mono<Venta> buscarVentaPorId(String id) {
        return ventaRepository.buscarVentaPorId(id);
    }
}