package co.com.torneo.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

public class EtapaVenta {
    private String id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int cupo;
    public String getId() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }
        return this.id;
    }
}
