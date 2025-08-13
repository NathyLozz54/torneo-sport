package co.com.torneo.infrastructure.entrypoints;

import co.com.torneo.domain.model.Torneo;
import co.com.torneo.domain.usecase.CrearTorneoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/torneos")
public class TorneoController {

    private final CrearTorneoUseCase crearTorneoUseCase;

    @Autowired
    public TorneoController(CrearTorneoUseCase crearTorneoUseCase) {
        this.crearTorneoUseCase = crearTorneoUseCase;
    }

    @PostMapping
    public Mono<ResponseEntity<Torneo>> crearTorneo(@RequestBody Torneo torneo) {
        return crearTorneoUseCase.crearTorneo(torneo)
                .map(t -> ResponseEntity.ok(t))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(null)));
    }

    @GetMapping
    public Flux<Torneo> listarTorneos() {
        return crearTorneoUseCase.listarTorneos();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Torneo>> buscarTorneoPorId(@PathVariable("id") String id) {
        return crearTorneoUseCase.buscarTorneoPorId(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
