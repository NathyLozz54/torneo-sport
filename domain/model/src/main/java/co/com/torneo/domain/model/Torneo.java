package co.com.torneo.domain.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Torneo {
    private String id;
    private String nombre;
    private String categoria;
    private String responsableId;
    private TipoTorneo tipo;
    private int limiteParticipantes;
    private double precioTicket;
    private double comisionPorcentaje;
    private List<EtapaVenta> etapasVenta;
    private int eventosGratuitosCreados;
}
