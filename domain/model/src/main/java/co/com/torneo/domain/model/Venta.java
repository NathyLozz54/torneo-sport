package co.com.torneo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Venta {

    private String id;
    private String torneoId;
    private String jugadorId;
    private TipoTorneo tipoTorneo;
    private BigDecimal monto;
    private LocalDateTime fechaVenta;
    private String metodoPago;
    private String estado;


    public static Venta crearNueva(Venta venta) {
        venta.setId(UUID.randomUUID().toString());
        venta.setFechaVenta(LocalDateTime.now());
        return venta;
    }
}
