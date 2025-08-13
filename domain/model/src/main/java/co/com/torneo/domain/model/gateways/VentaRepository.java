package co.com.torneo.domain.model.gateways;

import co.com.torneo.domain.model.Venta;
import reactor.core.publisher.*;

public interface VentaRepository {
    Mono<Venta> guardarVenta(Venta venta);
    Flux<Venta> buscarVentas();
    Mono<Venta> buscarVentaPorId(String id);
}