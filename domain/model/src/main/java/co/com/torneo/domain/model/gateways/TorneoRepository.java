package co.com.torneo.domain.model.gateways;

import co.com.torneo.domain.model.TipoTorneo;
import co.com.torneo.domain.model.Torneo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TorneoRepository {
    Mono<Torneo> guardarTorneo(Torneo torneo);
    Mono<Torneo> buscarTorneoPorId(String id);
    Flux<Torneo> buscarResponsableYTipo(String responsableId, TipoTorneo tipo);
    Flux<Torneo> buscarTodosLosTorneos();
}
