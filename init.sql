-- =======================
-- Tabla: Torneo
-- =======================
CREATE TABLE torneos (
    id                  VARCHAR(36) PRIMARY KEY,
    nombre              VARCHAR(255) NOT NULL,
    categoria           VARCHAR(100),
    responsable_id      VARCHAR(36),
    tipo                VARCHAR(50), -- Enum en Java, aquí lo guardamos como texto
    limite_participantes INT NOT NULL,
    precio_ticket       NUMERIC(10,2) NOT NULL,
    comision_porcentaje NUMERIC(5,2) NOT NULL,
    eventos_gratuitos_creados INT NOT NULL DEFAULT 0
);

-- =======================
-- Tabla: EtapaVenta
-- =======================
CREATE TABLE etapas_venta (
    id              VARCHAR(36) PRIMARY KEY,
    torneo_id       VARCHAR(36) NOT NULL REFERENCES torneo(id) ON DELETE CASCADE,
    fecha_inicio    TIMESTAMP NOT NULL,
    fecha_fin       TIMESTAMP NOT NULL,
    cupo            INT NOT NULL,
    CONSTRAINT fk_torneo_etapa
        FOREIGN KEY (torneo_id) REFERENCES torneos(id)
        ON DELETE CASCADE
);

-- =======================
-- Tabla: Venta
-- =======================
CREATE TABLE ventas (
    id           VARCHAR(36) PRIMARY KEY,
    torneo_id    VARCHAR(36) NOT NULL,
    jugador_id   VARCHAR(36) NOT NULL,
    tipo_torneo  VARCHAR(50) NOT NULL, -- Enum en Java, guardado como texto
    monto        NUMERIC(12,2) NOT NULL,
    fecha_venta  TIMESTAMP NOT NULL,
    metodo_pago  VARCHAR(50) NOT NULL,
    estado       VARCHAR(50) NOT NULL,
    CONSTRAINT fk_torneo_venta
        FOREIGN KEY (torneo_id) REFERENCES torneos(id)
        ON DELETE CASCADE
);
