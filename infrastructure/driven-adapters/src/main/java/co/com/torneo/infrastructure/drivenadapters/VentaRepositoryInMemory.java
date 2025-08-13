package co.com.torneo.infrastructure.drivenadapters;

import co.com.torneo.domain.model.Torneo;
import co.com.torneo.domain.model.Venta;
import co.com.torneo.domain.model.gateways.VentaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VentaRepositoryInMemory implements VentaRepository {

    private final Map<String, Venta> ventas = new ConcurrentHashMap<>();

    @Override
    public Mono<Venta> guardarVenta(Venta venta) {
        ventas.put(venta.getId(), venta);
        return Mono.just(venta);
    }

    @Override
    public Flux<Venta> buscarVentas() {
        return Flux.fromIterable(ventas.values());
    }

    @Override
    public Mono<Venta> buscarVentaPorId(String id) {
        return Mono.justOrEmpty(ventas.get(id));
    }
}
