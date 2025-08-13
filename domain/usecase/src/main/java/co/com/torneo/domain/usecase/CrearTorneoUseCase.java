package co.com.torneo.domain.usecase;

import co.com.torneo.domain.model.TipoTorneo;
import co.com.torneo.domain.model.Torneo;
import co.com.torneo.domain.model.gateways.TorneoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CrearTorneoUseCase {

    private final TorneoRepository torneoRepository;

    public CrearTorneoUseCase(TorneoRepository torneoRepository) {
        this.torneoRepository = torneoRepository;
    }

    public Mono<Torneo> crearTorneo(Torneo torneo) {
        // Validaciones comunes
        if (torneo.getNombre() == null || torneo.getNombre().isEmpty()) {
            return Mono.error(new IllegalArgumentException("El nombre del torneo es obligatorio"));
        }
        if (torneo.getCategoria() == null || torneo.getCategoria().isEmpty()) {
            return Mono.error(new IllegalArgumentException("La categoría es obligatoria"));
        }
        if (torneo.getResponsableId() == null || torneo.getResponsableId().isEmpty()) {
            return Mono.error(new IllegalArgumentException("Debe especificar un responsable"));
        }
        if (torneo.getLimiteParticipantes() <= 0) {
            return Mono.error(new IllegalArgumentException("El límite de participantes debe ser mayor a 0"));
        }

        // Lógica según el tipo de torneo
        if (torneo.getTipo() == TipoTorneo.PAGADO) {
            return validarTorneoPagado(torneo)
                    .flatMap(torneoRepository::guardarTorneo);
        }
        else if (torneo.getTipo() == TipoTorneo.GRATUITO) {
            return validarTorneoGratuito(torneo)
                    .flatMap(torneoRepository::guardarTorneo);
        }
        else if (torneo.getTipo() == TipoTorneo.GRATUITO) {
            return validarVistaVirtualGratuita(torneo)
                    .flatMap(torneoRepository::guardarTorneo);
        }

        return Mono.error(new IllegalArgumentException("Tipo de torneo no reconocido"));
    }

    // ✅ Validación para torneos pagados
    private Mono<Torneo> validarTorneoPagado(Torneo torneo) {
        if (torneo.getPrecioTicket() <= 0) {
            return Mono.error(new IllegalArgumentException("El precio del ticket debe ser mayor a 0"));
        }
        if (torneo.getComisionPorcentaje() < 0 || torneo.getComisionPorcentaje() > 100) {
            return Mono.error(new IllegalArgumentException("La comisión debe estar entre 0 y 100"));
        }
        return Mono.just(torneo);
    }

    // ✅ Validación para torneos gratuitos (máximo 2 por organizador + aforo)
    private Mono<Torneo> validarTorneoGratuito(Torneo torneo) {
        torneo.setPrecioTicket(0);
        torneo.setComisionPorcentaje(0);

        if (torneo.getLimiteParticipantes() > 100) { // ejemplo límite de aforo
            return Mono.error(new IllegalArgumentException("El aforo máximo para torneos gratuitos es de 100 participantes"));
        }

        return torneoRepository.buscarResponsableYTipo(torneo.getResponsableId(), TipoTorneo.GRATUITO)
                .count()
                .flatMap(count -> {
                    if (count >= 2) {
                        return Mono.error(new IllegalStateException("El organizador ya tiene el máximo de 2 torneos gratuitos"));
                    }
                    return Mono.just(torneo);
                });
    }

    // ✅ Validación para vistas virtuales gratuitas (máximo 1 por usuario)
    private Mono<Torneo> validarVistaVirtualGratuita(Torneo torneo) {
        torneo.setPrecioTicket(0);
        torneo.setComisionPorcentaje(0);

        if (torneo.getLimiteParticipantes() > 50) { // ejemplo límite transmisión
            return Mono.error(new IllegalArgumentException("El máximo de espectadores para vistas virtuales gratuitas es de 50"));
        }

        return torneoRepository.buscarResponsableYTipo(torneo.getResponsableId(), TipoTorneo.GRATUITO)
                .count()
                .flatMap(count -> {
                    if (count >= 1) {
                        return Mono.error(new IllegalStateException("El usuario ya tiene el máximo de 1 vista virtual gratuita"));
                    }
                    return Mono.just(torneo);
                });
    }

    public Mono<Torneo> buscarTorneoPorId(String id) {
        return torneoRepository.buscarTorneoPorId(id);
    }

    public Flux<Torneo> listarTorneos() {
        return torneoRepository.buscarTodosLosTorneos();
    }
}
