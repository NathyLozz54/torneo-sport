package co.com.torneo.infrastructure.drivenadapters;

import co.com.torneo.domain.model.Torneo;
import co.com.torneo.domain.model.gateways.TorneoRepository;
import co.com.torneo.domain.model.TipoTorneo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TorneoRepositoryInMemory implements TorneoRepository {

    private final Map<String, Torneo> storage = new ConcurrentHashMap<>();

    @Override
    public Mono<Torneo> guardarTorneo(Torneo torneo) {
        if (torneo.getId() == null) {
            torneo.setId(UUID.randomUUID().toString());
        }
        storage.put(torneo.getId(), torneo);
        return Mono.just(torneo);
    }

    @Override
    public Flux<Torneo> buscarResponsableYTipo(String responsableId, TipoTorneo tipo) {
        return Flux.fromIterable(storage.values())
                .filter(torneo ->
                        torneo.getResponsableId().equals(responsableId) && torneo.getTipo() == tipo
                );
    }

    @Override
    public Mono<Torneo> buscarTorneoPorId(String id) {
        Torneo torneo = storage.get(id);
        return torneo != null ? Mono.just(torneo) : Mono.empty();
    }

    @Override
    public Flux<Torneo> buscarTodosLosTorneos() {
        return Flux.fromIterable(storage.values());
    }
}
