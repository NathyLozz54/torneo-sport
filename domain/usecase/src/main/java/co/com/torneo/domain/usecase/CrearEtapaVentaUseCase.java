package co.com.torneo.domain.usecase;

import co.com.torneo.domain.model.EtapaVenta;
import co.com.torneo.domain.model.TipoTorneo;
import co.com.torneo.domain.model.Torneo;
import co.com.torneo.domain.model.gateways.TorneoRepository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class CrearEtapaVentaUseCase {

    private final TorneoRepository torneoRepository;

    public CrearEtapaVentaUseCase(TorneoRepository torneoRepository) {
        this.torneoRepository = torneoRepository;
    }

    public Mono<Torneo> crearEtapaVenta(String torneoId, EtapaVenta etapaVenta) {

        if (etapaVenta.getFechaInicio() == null || etapaVenta.getFechaFin() == null) {
            return Mono.error(new IllegalArgumentException("La fecha de inicio y fin son obligatorias"));
        }
        if (!etapaVenta.getFechaFin().isAfter(etapaVenta.getFechaInicio())) {
            return Mono.error(new IllegalArgumentException("La fecha fin debe ser posterior a la fecha inicio"));
        }
        if (etapaVenta.getCupo() <= 0) {
            return Mono.error(new IllegalArgumentException("El cupo debe ser mayor a cero"));
        }

        // Forzar generación de id si no tiene
        etapaVenta.getId();

        return torneoRepository.buscarTorneoPorId(torneoId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Torneo no encontrado")))
                .flatMap(torneo -> {
                    if (torneo.getTipo() != TipoTorneo.PAGADO) {
                        return Mono.error(new IllegalStateException("Solo torneos pagados pueden tener etapas de venta"));
                    }
                    if (torneo.getEtapasVenta() != null) {
                        boolean haySolapamiento = torneo.getEtapasVenta().stream().anyMatch(e ->
                                // Cambio: permite etapas contiguas sin solaparse (<= y >= en lugar de < y >)
                                etapaVenta.getFechaInicio().isBefore(e.getFechaFin()) &&
                                        etapaVenta.getFechaFin().isAfter(e.getFechaInicio())
                        );
                        if (haySolapamiento) {
                            return Mono.error(new IllegalStateException("Las etapas de venta no pueden solaparse"));
                        }
                        torneo.getEtapasVenta().add(etapaVenta);
                    } else {
                        torneo.setEtapasVenta(new ArrayList<>(List.of(etapaVenta)));
                    }
                    return torneoRepository.guardarTorneo(torneo);
                });
    }
    public Mono<java.util.List<EtapaVenta>> listarEtapasVenta(String torneoId) {
        return torneoRepository.buscarTorneoPorId(torneoId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Torneo no encontrado")))
                .map(torneo -> torneo.getEtapasVenta());
    }

}
