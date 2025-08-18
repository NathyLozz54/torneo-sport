-- ============================
-- Creación de tablas Torneo, EtapaVenta y Venta
-- ============================

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabla Torneo
CREATE TABLE torneo (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100),
    responsable_id UUID,
    tipo VARCHAR(50), -- puedes mapear el enum TipoTorneo como texto
    limite_participantes INT NOT NULL,
    precio_ticket NUMERIC(12,2) NOT NULL,
    comision_porcentaje NUMERIC(5,2) NOT NULL,
    eventos_gratuitos_creados INT NOT NULL DEFAULT 0
);

-- Tabla EtapaVenta
CREATE TABLE etapa_venta (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    torneo_id UUID NOT NULL,
    fecha_inicio TIMESTAMP NOT NULL,
    fecha_fin TIMESTAMP NOT NULL,
    cupo INT NOT NULL,
    CONSTRAINT fk_torneo_etapaventa FOREIGN KEY (torneo_id) REFERENCES torneo(id) ON DELETE CASCADE
);

-- Tabla Venta
CREATE TABLE venta (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    torneo_id UUID NOT NULL,
    jugador_id UUID NOT NULL,
    tipo_torneo VARCHAR(50), -- igual que arriba, guardar como texto
    monto NUMERIC(12,2) NOT NULL,
    fecha_venta TIMESTAMP NOT NULL DEFAULT now(),
    metodo_pago VARCHAR(100),
    estado VARCHAR(50),
    CONSTRAINT fk_torneo_venta FOREIGN KEY (torneo_id) REFERENCES torneo(id) ON DELETE CASCADE
);

-- Índices útiles
CREATE INDEX idx_torneo_categoria ON torneo(categoria);
CREATE INDEX idx_venta_torneo ON venta(torneo_id);
CREATE INDEX idx_etapaventa_torneo ON etapa_venta(torneo_id);
