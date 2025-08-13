package co.com.torneo.infrastructure.entrypoints;

import co.com.torneo.domain.model.EtapaVenta;
import co.com.torneo.domain.model.Torneo;
import co.com.torneo.domain.usecase.CrearEtapaVentaUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/torneos")
public class EtapaVentaController {

    private final CrearEtapaVentaUseCase crearEtapaVentaUseCase;

    @Autowired
    public EtapaVentaController(CrearEtapaVentaUseCase crearEtapaVentaUseCase) {
        this.crearEtapaVentaUseCase = crearEtapaVentaUseCase;
    }

    @PostMapping("/{torneoId}/etapas")
    public Mono<ResponseEntity<Object>> crearEtapa(@PathVariable String torneoId, @RequestBody EtapaVenta etapaVenta) {
        return crearEtapaVentaUseCase.crearEtapaVenta(torneoId, etapaVenta)
                .map(torneo -> ResponseEntity.ok().body((Object) torneo))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body((Object) e.getMessage())));
    }
    @GetMapping("/{torneoId}/etapas")
    public Mono<ResponseEntity<Object>> listarEtapas(@PathVariable String torneoId) {
        return crearEtapaVentaUseCase.listarEtapasVenta(torneoId)
                .map(etapas -> ResponseEntity.ok().body((Object) etapas))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(e.getMessage())));
    }

}
