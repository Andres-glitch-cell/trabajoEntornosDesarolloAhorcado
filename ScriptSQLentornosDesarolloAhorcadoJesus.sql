CREATE DATABASE IF NOT EXISTS ahorcadoJuegoEntornosJesus;
USE ahorcadoJuegoEntornosJesus;

-- Tabla: Usuario --
CREATE TABLE IF NOT EXISTS Usuario (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nombreCompleto VARCHAR(100),
    nombreUsuario VARCHAR(50),
    correoElectronico VARCHAR(100),
    contraseña VARCHAR(150),
    fechaRegistro DATETIME
);

-- Tabla: Administrador --
CREATE TABLE IF NOT EXISTS Administrador (
    idAdministrador INT PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT,
    nivelAdministrador VARCHAR(20),
    fechaAsignacion DATETIME,
    ultimoAcceso DATETIME,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);

-- Tabla: Jugador --
CREATE TABLE IF NOT EXISTS Jugador (
    idJugador INT PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT,
    puntosTotales INT,
    primeraPartida DATETIME,
    ultimaPartida DATETIME,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);

-- Tabla: Cuota --
CREATE TABLE IF NOT EXISTS Cuota (
    idCuota INT PRIMARY KEY AUTO_INCREMENT,
    TipoDeCuota VARCHAR(50)
);

-- Tabla: Juego --
CREATE TABLE IF NOT EXISTS Juego (
    idJuego INT PRIMARY KEY AUTO_INCREMENT,
    resultado VARCHAR(50),
    modoJuego VARCHAR(50),
    maxIntentos INT,
    dificultadBase INT
);

-- Tabla: PalabrasFrases --
CREATE TABLE IF NOT EXISTS PalabrasFrases (
    idPalabra INT PRIMARY KEY AUTO_INCREMENT,
    idFrase INT,
    categoria VARCHAR(50),
    fechaCreacion DATETIME,
    contenido TEXT,
    significadoFrase TEXT,
    significadoPalabra TEXT
);

-- Tabla: Partida --
CREATE TABLE IF NOT EXISTS Partida (
    idPartida INT PRIMARY KEY AUTO_INCREMENT,
    idPalabra INT,
    resultado VARCHAR(50),
    idJuego INT,
    nivelDificultad INT,
    fechaInicio DATETIME,
    fechaFin DATETIME,
    puntosObtenidos INT,
    partesAhorcado INT,
    tiempoTotal INT,
    modoEspecial VARCHAR(50),
    FOREIGN KEY (idPalabra) REFERENCES PalabrasFrases(idPalabra),
    FOREIGN KEY (idJuego) REFERENCES Juego(idJuego)
);

-- Tabla: Historial --
CREATE TABLE IF NOT EXISTS Historial (
    idHistorial INT PRIMARY KEY AUTO_INCREMENT,
    idPalabra INT,
    idFrase INT,
    idAdministrador INT,
    significadoFrase TEXT,
    significadoPalabra TEXT,
    FOREIGN KEY (idPalabra) REFERENCES PalabrasFrases(idPalabra),
    FOREIGN KEY (idFrase) REFERENCES PalabrasFrases(idPalabra),
    FOREIGN KEY (idAdministrador) REFERENCES Administrador(idAdministrador)  -- Corregido para referenciar idAdministrador
);

-- Tabla: Idioma --
CREATE TABLE IF NOT EXISTS Idioma (
    idIdioma INT PRIMARY KEY AUTO_INCREMENT,
    nombreIdioma VARCHAR(50),
    codigoISO VARCHAR(10)
);

-- Inserciones de datos (2 valores por tabla)

-- Tabla: Usuario
INSERT INTO Usuario (nombreCompleto, nombreUsuario, correoElectronico, contraseña, fechaRegistro) VALUES
    ('Jesús Pérez', 'jesus123', 'jesus@example.com', 'pass123', '2025-04-01 10:00:00'),
    ('María Gómez', 'maria_g', 'maria@example.com', 'secure456', '2025-04-02 15:30:00');

-- Tabla: Administrador
INSERT INTO Administrador (idUsuario, nivelAdministrador, fechaAsignacion, ultimoAcceso) VALUES
    (1, 'Senior', '2025-04-01 12:00:00', '2025-04-04 09:00:00'),
    (2, 'Junior', '2025-04-02 16:00:00', '2025-04-03 14:00:00');

-- Tabla: Jugador
INSERT INTO Jugador (idUsuario, puntosTotales, primeraPartida, ultimaPartida) VALUES
    (1, 150, '2025-04-01 11:00:00', '2025-04-03 18:00:00'),
    (2, 80, '2025-04-02 17:00:00', '2025-04-04 10:00:00');

-- Tabla: Cuota
INSERT INTO Cuota (TipoDeCuota) VALUES
    ('Mensual'),
    ('Anual');

-- Tabla: Juego
INSERT INTO Juego (resultado, modoJuego, maxIntentos, dificultadBase) VALUES
    ('Ganado', 'Clásico', 6, 3),
    ('Perdido', 'Rápido', 5, 2);

-- Tabla: PalabrasFrases
INSERT INTO PalabrasFrases (idFrase, categoria, fechaCreacion, contenido, significadoFrase, significadoPalabra) VALUES
    (NULL, 'Sustantivo', '2025-04-01 09:00:00', 'Sol', NULL, 'Estrella que ilumina la Tierra'),
    (1, 'Frase', '2025-04-02 12:00:00', 'El sol brilla', 'Descripción del clima', NULL);

-- Tabla: Partida
INSERT INTO Partida (idPalabra, resultado, idJuego, nivelDificultad, fechaInicio, fechaFin, puntosObtenidos, partesAhorcado, tiempoTotal, modoEspecial) VALUES
    (1, 'Ganado', 1, 3, '2025-04-03 10:00:00', '2025-04-03 10:05:00', 50, 2, 300, 'Normal'),
    (2, 'Perdido', 2, 2, '2025-04-04 11:00:00', '2025-04-04 11:03:00', 0, 5, 180, 'Cronometrado');

-- Tabla: Historial
INSERT INTO Historial (idPalabra, idFrase, idAdministrador, significadoFrase, significadoPalabra) VALUES
    (1, NULL, 1, NULL, 'Estrella que ilumina la Tierra'),
    (2, 2, 2, 'Descripción del clima', NULL);

-- Tabla: Idioma
INSERT INTO Idioma (nombreIdioma, codigoISO) VALUES
    ('Español', 'ES'),
    ('Inglés', 'EN');