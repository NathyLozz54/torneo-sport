package co.com.torneo.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

public class Usuario {
    private String id;
    private String nombre;
    private int eventosGratuitosCreados; // para controlar máximo 2 torneos gratuitos
    private int vistasGratuitasCreadas;
}
