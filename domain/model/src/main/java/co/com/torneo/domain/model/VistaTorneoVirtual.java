package co.com.torneo.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

public class VistaTorneoVirtual {
    private String id;
    private String usuarioId;
    private String torneoId;
    private int limiteEspectadores;
}
