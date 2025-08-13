package co.com.torneo.infrastructure.entrypoints;

import co.com.torneo.domain.model.Venta;
import co.com.torneo.domain.usecase.CrearVentaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final CrearVentaUseCase ventaUseCase;

    @PostMapping
    public Mono<Venta> guardarVenta(@RequestBody Venta venta) {
        return ventaUseCase.guardarVenta(venta);
    }

    @GetMapping
    public Flux<Venta> buscarVentas() {
        return ventaUseCase.buscarVentas();
    }

    @GetMapping("/{id}")
    public Mono<Venta> buscarVentaPorId(@PathVariable String id) {
        return ventaUseCase.buscarVentaPorId(id);
    }

}
