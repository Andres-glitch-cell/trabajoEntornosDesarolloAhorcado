<p align="left">
  <img src="https://img.shields.io/github/stars/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado?style=for-the-badge&label=Stars&color=6C78AF&logo=github" />
  <img src="https://img.shields.io/github/forks/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado?style=for-the-badge&label=Forks&color=43e97b&logo=github" />
  <img src="https://img.shields.io/github/issues/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado?style=for-the-badge&label=Issues&color=fc466b&logo=github" />
  <img src="https://img.shields.io/github/license/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado?style=for-the-badge&label=License&color=3f5efb" />
  <img src="https://visitor-badge.laobi.icu/badge?page_id=Andres-glitch-cell.trabajoEntornosDesarolloAhorcado&style=for-the-badge&color=38f9d7" />
  <img src="https://img.shields.io/badge/Java-Backend-007396?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-Base%20de%20Datos-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/Swing-GUI-6C78AF?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Estado-Activo-brightgreen?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Soporte-Discord-7289DA?style=for-the-badge&logo=discord&logoColor=white" />
</p>

# 🎮 Juego del Ahorcado - Proyecto de Entornos de Desarrollo

[Ver Presentación Interactiva](#)

Un clásico reinventado con tecnología moderna  
Adivina palabras y frases en este entretenido juego del ahorcado, desarrollado con Java y un diseño futurista.  
Construido con ❤️ por Andrés.




🚀 Descripción
Bienvenido al Juego del Ahorcado, un proyecto para el curso de Entornos de Desarrollo. Desarrollado en Java con una base de datos MySQL para almacenar palabras, frases y puntuaciones, este juego combina lógica de programación con una interfaz moderna basada en Swing y arquitectura MVC.
Características Principales

🖥️ Interfaz gráfica en desarrollo (GUI con Swing).
📊 Base de datos para palabras, frases y estadísticas.
🎨 Estilo visual moderno y temático.
🧩 Arquitectura MVC:
Modelo: Gestión de la base de datos.
Vista: Formularios en .java (carpeta vista).
Controlador: Lógica en .java (carpeta controlador).



Progreso del Proyecto:

Explora la presentación interactiva para una experiencia animada con transiciones suaves.


📸 Vistas del Proyecto
Nota: Las imágenes muestran la estructura principal, no la implementación final.



Modelo E.R
Casos de Uso
Diagrama de Clases










Sugerencia: Reemplaza con GIFs animados (ej. mostrando la creación de diagramas o la interfaz Swing). Sube a /assets (ej. /assets/diagram-animation.gif).


📋 Tabla de Contenidos

Base de Datos
Instalación
Verificación
Introducción
Idiomas
Funcionalidades
Mejoras
Requisitos
Valoración
Calendario
Demo


📊 Base de Datos

Esquema de Tablas

Tabla Principal: Usuario
CREATE TABLE Usuario (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nombreCompleto VARCHAR(100) NOT NULL
);

CREATE TABLE Administrador (
    idAdministrador INT PRIMARY KEY,
    FOREIGN KEY (idAdministrador) REFERENCES Usuario(idUsuario)
);

CREATE TABLE Jugador (
    idJugador INT PRIMARY KEY,
    puntos INT DEFAULT 0,
    FOREIGN KEY (idJugador) REFERENCES Usuario(idUsuario)
);

CREATE TABLE Juego (
    idJuego INT PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT,
    resultado VARCHAR(50),
    idIdioma INT,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario),
    FOREIGN KEY (idIdioma) REFERENCES Idioma(idIdioma)
);

CREATE TABLE Idioma (
    idIdioma INT PRIMARY KEY AUTO_INCREMENT,
    idioma VARCHAR(50) NOT NULL
);

CREATE TABLE PalabrasFrases (
    idPalabraFrase INT PRIMARY KEY AUTO_INCREMENT,
    idIdioma INT,
    sinonimos TEXT,
    FOREIGN KEY (idIdioma) REFERENCES Idioma(idIdioma)
);

CREATE TABLE HistoricoPalabras (
    idHistoricoPalabra INT PRIMARY KEY AUTO_INCREMENT,
    idPalabraFrase INT,
    contenidoAnterior TEXT,
    tipoAnterior VARCHAR(50),
    significadoAnterior TEXT,
    dificultadAnterior VARCHAR(50),
    fechaCambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idPalabraFrase) REFERENCES PalabrasFrases(idPalabraFrase)
);

CREATE TABLE HistoricoFrases (
    idHistoricoFrase INT PRIMARY KEY AUTO_INCREMENT,
    idPalabraFrase INT,
    contenidoAnterior TEXT,
    descripcionAnterior TEXT,
    fechaCambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idPalabraFrase) REFERENCES PalabrasFrases(idPalabraFrase)
);

CREATE TABLE Partida (
    idPartida INT PRIMARY KEY AUTO_INCREMENT,
    idJuego INT,
    fecha DATE,
    hora TIME,
    puntosObtenidos INT,
    dificultad VARCHAR(20),
    modoJuego VARCHAR(20),
    FOREIGN KEY (idJuego) REFERENCES Juego(idJuego)
);

CREATE TABLE PalabraUsada (
    idPalabraUsada INT PRIMARY KEY AUTO_INCREMENT,
    idPartida INT,
    idPalabraFrase INT,
    fueAdivinada BOOLEAN,
    FOREIGN KEY (idPartida) REFERENCES Partida(idPartida),
    FOREIGN KEY (idPalabraFrase) REFERENCES PalabrasFrases(idPalabraFrase)
);

CREATE TABLE CuotaJugador (
    idCuota INT PRIMARY KEY AUTO_INCREMENT,
    idJugador INT,
    tipoCuota ENUM('Normal', 'Premium') NOT NULL,
    importe DECIMAL(5,2),
    fechaInicio DATE,
    fechaFin DATE,
    FOREIGN KEY (idJugador) REFERENCES Jugador(idJugador)
);

Resumen de Claves Primarias y Foráneas



Tabla
Clave Primaria (PK)
Clave Foránea (FK)
Referencia



Administrador
idAdministrador
-
Usuario(idUsuario)


Jugador
idJugador
-
Usuario(idUsuario)


Juego
idJuego
idUsuario, idIdioma
Usuario(idUsuario), Idioma(idIdioma)


Idioma
idIdioma
-
-


PalabrasFrases
idPalabraFrase
idIdioma
Idioma(idIdioma)





⚙️ Instalación

Clona el repositorio:git clone https://github.com/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado.git


Linux/Ubuntu: /home/tu-user/repositorio-creado
Windows: C:\Users\Pc\Desktop\repositorio-creado




✅ Verificación

Crea el repositorio en GitHub: Si no lo has hecho, crea un repositorio (ej. juego-ahorcado).
Copia la URL: Usa el botón "Code" para obtener el enlace HTTPS.


📝 Introducción
El Juego del Ahorcado es un desafío clásico donde adivinas palabras o frases letra por letra:

✅ Acierto: La letra aparece en su posición.
❌ Fallo: Se dibuja una parte del ahorcado (cabeza, tronco, etc.).
🎯 Objetivo: Adivina antes de completar el dibujo o pierdes.

El juego soporta múltiples usuarios, partidas por sesión, y registra puntuaciones. Define jugadores y partidas al inicio, y obtén un informe final con resultados.


Sugerencia: Sube un GIF de la interfaz Swing en acción a /assets/demo.gif.


🌐 Idiomas Disponibles

🇪🇸 Español
🇫🇷 Francés
🇬🇧 Inglés
🇻🇪 Valenciano


Las palabras y frases son específicas del idioma seleccionado.


🔐 Registro e Identificación

Detalles de Usuarios


Jugadores: Registrarse con ID, nombre y apellidos.
Administradores:
Nivel 1: Máxima autoridad.
Nivel 2: Copias de seguridad y restauraciones.
Nivel 3: Solo copias de seguridad.


Usuarios no autenticados: Pueden registrarse y consultar la base de datos.




🛠️ Funcionalidades del Administrador

Ver Funcionalidades


Gestionar usuarios (altas, bajas, modificaciones).
Importar diccionarios vía JSON.
Consultar informes de resultados.
Añadir/eliminar idiomas.
Copias de seguridad y restauraciones (según nivel).
Nivel 1: Modificar/eliminar palabras y frases.




🗃️ Estructura de Datos

Detalles de Datos


Usuarios: Participan en múltiples juegos y partidas.
Palabras y Frases:
Palabras: Código, contenido, tipo (nombre, adjetivo, etc.), significado, dificultad.
Frases: Refranes/proverbios con código y descripción.
Vinculadas a un idioma, con términos similares en otros idiomas.
Actualizaciones vía JSON, con historial en historicoPalabras y historicoFrases.


Partidas: Registro de fecha, hora, puntos, y palabras usadas.
Ahorcado Visual: Construido según la longitud de la palabra; en modo frases, aparece boca abajo.
Cuotas:
Normal: Solo palabras.
Premium: Palabras y frases hechas.


Hilos Automáticos: Copias de seguridad cada 5 minutos y reloj digital.




🎲 Mejoras en la Jugabilidad

Ver Mejoras


🤝 Modo Cooperativo: Equipos para adivinar juntos.
🏆 Modo Torneo: Rondas eliminatorias y clasificación.
💡 Pistas Dinámicas: Sinónimos o letras a cambio de puntos.
🎯 Dificultad Personalizable: Fácil, media o difícil.
📅 Desafíos Diarios/Semanales: Palabras especiales con recompensas.
⏳ Rondas Temporizadas: Bonificaciones por rapidez.




🎨 Mejoras Visuales y de Interacción

Ver Mejoras Visuales


🎭 Animaciones del Ahorcado: Reacciones dinámicas a aciertos/fallos.
🎨 Temas Personalizables: Estilos como pirata o medieval.
🎶 Sonidos y Música: Efectos y música de fondo ajustable.




🔧 Mejoras Técnicas

Ver Mejoras Técnicas


🏅 Logros y Recompensas: Medallas por hitos.
📚 Modo Educativo: Aprendizaje de idiomas.
🔍 Categorías: Temáticas como animales o películas.
🌎 Juego Online: Rankings y desafíos (compatible con Tomcat).
🤖 Modo IA: Bot oponente con dificultad ajustable.




📋 Requisitos del Proyecto

Ver Requisitos


Diagrama Entidad/Relación.
Paso a tablas.
Base de datos MySQL con relaciones.
Repositorio en GitHub.
Diagrama de casos de uso.
Diagrama de clases.
Codificación en Java.
Interfaz gráfica Swing.
Aplicación Java funcional.
Pruebas con JUnit.
Subir todo a GitHub.
Versión instalable.




⭐ Valoración

Ver Criterios


Análisis correcto (0.75 pt.): Diagramas de casos de uso y clases.
Base de datos (0.75 pt.): Diagrama E/R, tablas, implementación.
Vista (2 pt.): Interfaz gráfica.
Controlador (2 pt.): Gestión de eventos.
Modelo (3 pt.): Lógica y datos.
Pruebas y errores (0.75 pt.): JUnit y try-catch.
Documentación e instalable (0.75 pt.): JavaDoc y paquete instalable.
Mejoras (2 pt.): Implementar al menos cuatro mejoras.




📅 Calendario de Entregas

Ver Fechas


21 de Marzo, 2025: Diagramas y base de datos.
4 de Abril, 2025: Interfaz gráfica.
16 de Abril, 2025: Controlador.
23 de Mayo, 2025: Modelo.
2 de Junio, 2025: Pruebas, documentación, instalable.




🎥 Demo del Juego


Sugerencia: Graba un video o GIF de la interfaz Swing y súbelo a /assets/demo.gif.



Contribuye: Abre un issue o envía un pull request.Contacto: AndrésExplora más en la presentación interactiva!

