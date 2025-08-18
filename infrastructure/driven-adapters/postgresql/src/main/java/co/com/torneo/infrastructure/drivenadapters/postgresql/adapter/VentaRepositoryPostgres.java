package co.com.torneo.infrastructure.drivenadapters.postgresql.adapter;

import co.com.torneo.domain.model.Venta;
import co.com.torneo.domain.model.gateways.TorneoRepository;
import org.springframework.stereotype.Repository;
import co.com.torneo.domain.model.gateways.VentaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VentaRepositoryPostgres implements VentaRepository {

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
