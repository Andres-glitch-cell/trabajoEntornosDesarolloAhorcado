<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Juego del Ahorcado - Entornos de Desarrollo</title>
    <style>
        :root {
            --primary-color: #1a1a2e;
            --accent-color: #00ff88;
            --text-color: #e0e0e0;
            --card-bg: #16213e;
            --shadow: 0 4px 15px rgba(0, 255, 136, 0.2);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background: linear-gradient(135deg, var(--primary-color), #0f3460);
            color: var(--text-color);
            line-height: 1.6;
            padding: 20px;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        .banner {
            background: url('https://via.placeholder.com/1200x300?text=Hangman+Game+Banner') center/cover;
            height: 300px;
            border-radius: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            color: white;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
            animation: fadeIn 2s ease-in;
            margin-bottom: 30px;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        h1, h2, h3 {
            color: var(--accent-color);
            margin-bottom: 15px;
            animation: slideIn 1s ease-out;
        }

        @keyframes slideIn {
            from { transform: translateX(-50px); opacity: 0; }
            to { transform: translateX(0); opacity: 1; }
        }

        .badges img {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            margin: 5px;
        }

        .badges img:hover {
            transform: scale(1.1);
            box-shadow: var(--shadow);
        }

        .section {
            background: var(--card-bg);
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: var(--shadow);
            animation: fadeInUp 1s ease-out;
        }

        @keyframes fadeInUp {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .toggle-section {
            cursor: pointer;
            background: #0f3460;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 10px;
        }

        .toggle-section:hover {
            background: #1c4e80;
        }

        .content {
            display: none;
            padding: 15px;
        }

        .content.active {
            display: block;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #2a2a4e;
        }

        th {
            background: var(--accent-color);
            color: #000;
        }

        tr:nth-child(even) {
            background: #1c2526;
        }

        tr:hover {
            background: #2a2a4e;
        }

        .progress-bar {
            width: 100%;
            background: #2a2a4e;
            height: 20px;
            border-radius: 10px;
            overflow: hidden;
            margin: 20px 0;
        }

        .progress {
            width: 40%;
            height: 100%;
            background: var(--accent-color);
            animation: progressAnim 2s ease-in-out;
        }

        @keyframes progressAnim {
            from { width: 0; }
            to { width: 40%; }
        }

        pre {
            background: #1c2526;
            padding: 15px;
            border-radius: 8px;
            overflow-x: auto;
        }

        a {
            color: var(--accent-color);
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .gif-container {
            text-align: center;
            margin: 20px 0;
        }

        .gif-container img {
            max-width: 100%;
            border-radius: 10px;
            box-shadow: var(--shadow);
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="banner">
            <h1>Juego del Ahorcado - Proyecto de Entornos de Desarrollo</h1>
        </div>

        <div class="section">
            <h2>🎮 Juego del Ahorcado en Java</h2>
            <p><strong>Un clásico reinventado con tecnología moderna</strong></p>
            <p>Un juego entretenido para <em>entretenerse con el ahorcado</em>. Construido con ❤️ por Andrés.</p>
            <div class="badges">
                <img src="https://img.shields.io/github/stars/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado?style=social" alt="GitHub stars">
                <img src="https://img.shields.io/badge/Estado-En%20Desarrollo-orange" alt="Estado">
                <img src="https://img.shields.io/badge/Base%20de%20Datos-Pendiente-lightgrey" alt="DB">
                <img src="https://img.shields.io/badge/Plataforma-Windows%20%7C%20Linux-lightgrey" alt="Plataforma">
                <img src="https://img.shields.io/badge/Versi%C3%B3n-1.0--alpha-informational" alt="Versión">
                <img src="https://img.shields.io/badge/Licencia-MIT-green" alt="Licencia">
                <img src="https://img.shields.io/badge/Idiomas-ES%20%7C%20EN%20%7C%20FR%20%7C%20VAL-blueviolet" alt="Idiomas">
                <img src="https://img.shields.io/badge/Build-Personalizada-success" alt="Build">
                <img src="https://img.shields.io/badge/Java-17-blue" alt="Java 17">
                <img src="https://img.shields.io/badge/Java-21-red" alt="Java 21">
            </div>
        </div>

        <div class="section">
            <h2>🚀 Descripción</h2>
            <p>Bienvenido al <strong>Juego del Ahorcado</strong>, un proyecto desarrollado en <strong>Java</strong> con una base de datos para almacenar palabras y puntuaciones. Este repositorio es parte de un trabajo para Entornos de Desarrollo, combinando lógica de programación con un diseño futurista y funcional.</p>
            <h3>Características</h3>
            <ul>
                <li>🖥️ Interfaz de usuario en desarrollo (próximamente con GUI).</li>
                <li>📊 Base de datos para palabras y estadísticas.</li>
                <li>🎨 Estilo visual creado por la librería Swing (Java).</li>
                <li>🧩 <strong>Arquitectura MVC</strong>:
                    <ul>
                        <li><strong>Modelo</strong>: Almacena la base de datos.</li>
                        <li><strong>Vista</strong>: Formularios en <code>.java</code> guardados en la carpeta <code>vista</code>.</li>
                        <li><strong>Controlador</strong>: Código Java que ejecuta la lógica en la carpeta <code>controlador</code>.</li>
                    </ul>
                </li>
            </ul>
            <div class="progress-bar">
                <div class="progress"></div>
            </div>
        </div>

        <div class="section">
            <h2>📸 Capturas</h2>
            <div class="gif-container">
                <img src="https://via.placeholder.com/600x400?text=Modelo+E.R+GIF" alt="Modelo E.R">
            </div>
            <p>01_Vista General 00_Modelo E.R</p>
            <div class="gif-container">
                <img src="https://via.placeholder.com/600x400?text=Casos+de+Uso+GIF" alt="Casos de Uso">
            </div>
            <p>02_Vista General del Diagrama de Casos de Uso</p>
            <div class="gif-container">
                <img src="https://via.placeholder.com/600x400?text=Diagrama+de+Clases+GIF" alt="Diagrama de Clases">
            </div>
            <p>03_Vista General del Diagrama de Clases</p>
            <p><em>Nota: Las capturas muestran la estructura principal, no los valores reales de la implementación.</em></p>
        </div>

        <div class="section">
            <h2 class="toggle-section">Tablas</h2>
            <div class="content">
                <h3>Tabla Principal Usuario</h3>
                <pre><code>CREATE TABLE Usuario (
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
</code></pre>
                <h3>Resumen de Claves Primarias y Foráneas</h3>
                <table>
                    <tr>
                        <th>Tabla</th>
                        <th>Clave Primaria (PK)</th>
                        <th>Clave Foránea (FK)</th>
                        <th>Referencia</th>
                    </tr>
                    <tr>
                        <td>Administrador</td>
                        <td>idAdministrador</td>
                        <td>-</td>
                        <td>Usuario(idUsuario)</td>
                    </tr>
                    <tr>
                        <td>Jugador</td>
                        <td>idJugador</td>
                        <td>-</td>
                        <td>Usuario(idUsuario)</td>
                    </tr>
                    <tr>
                        <td>Juego</td>
                        <td>idJuego</td>
                        <td>idUsuario, idIdioma</td>
                        <td>Usuario(idUsuario), Idioma(idIdioma)</td>
                    </tr>
                    <tr>
                        <td>Idioma</td>
                        <td>idIdioma</td>
                        <td>-</td>
                        <td>-</td>
                    </tr>
                    <tr>
                        <td>PalabrasFrases</td>
                        <td>idPalabraFrase</td>
                        <td>idIdioma</td>
                        <td>Idioma(idIdioma)</td>
                    </tr>
                </table>
            </div>
        </div>

        <div class="section">
            <h2 class="toggle-section">Instalación</h2>
            <div class="content">
                <p>1. Clona el repositorio:</p>
                <ul>
                    <li><strong>Linux/Ubuntu</strong>: <code>/home/tu-user/repositorio-creado</code></li>
                    <li><strong>Windows</strong>: <code>C:\Users\Pc\Desktop\repositorio-creado</code></li>
                </ul>
                <pre><code>git clone https://github.com/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado.git</code></pre>
            </div>
        </div>

        <div class="section">
            <h2 class="toggle-section">Verificación</h2>
            <div class="content">
                <ol>
                    <li><strong>Crea el repositorio en GitHub</strong>: Si aún no lo has hecho, crea un repositorio (ej. <code>juego-ahorcado</code>) bajo tu usuario.</li>
                    <li><strong>Copia la URL del repositorio</strong>: Haz clic en el botón verde "Code" y copia el enlace HTTPS (ej. <code>https://github.com/tu-nombre-de-usuario/tu-repositorio.git</code>).</li>
                </ol>
            </div>
        </div>

        <div class="section">
            <h2 class="toggle-section">Introducción</h2>
            <div class="content">
                <p>El <strong>Juego del Ahorcado</strong> es un clásico desafío de adivinar palabras letra por letra. Se muestran espacios en blanco por cada carácter de la palabra secreta. Con cada letra ingresada:</p>
                <ul>
                    <li>✅ <strong>Acierto</strong>: La letra aparece en su posición.</li>
                    <li>❌ <strong>Fallo</strong>: Se dibuja una parte del ahorcado (cabeza, tronco, etc.).</li>
                </ul>
                <p>Si el dibujo se completa antes de adivinar, pierdes. Al final, se revela la palabra con su significado.</p>
                <p>El juego soporta múltiples usuarios, varias partidas por sesión y registra puntuaciones para determinar un ganador. Antes de empezar, se define el número de jugadores y partidas, y al concluir, se genera un informe con resultados y clasificaciones.</p>
            </div>
        </div>

        <div class="section">
            <h2 class="toggle-section">Idiomas Disponibles 🇻🇪 🇪🇸 🇬🇧 🇫🇷</h2>
            <div class="content">
                <ul>
                    <li>🇪🇸 <strong>Español</strong></li>
                    <li>🇫🇷 <strong>Francés</strong></li>
                    <li>🇬🇧 <strong>Inglés</strong></li>
                    <li>🇻🇪 <strong>Valenciano</strong></li>
                </ul>
                <p>Las palabras a adivinar corresponden al idioma seleccionado.</p>
            </div>
        </div>

        <div class="section">
            <h2 class="toggle-section">Registro e Identificación de Usuarios</h2>
            <div class="content">
                <p><strong>Jugadores</strong>: Registrarse con número de usuario, nombre y apellidos.</p>
                <p><strong>Administradores</strong>:</p>
                <ul>
                    <li><strong>Nivel 1</strong>: Máxima autoridad.</li>
                    <li><strong>Nivel 2</strong>: Copias de seguridad y restauraciones.</li>
                    <li><strong>Nivel 3</strong>: Solo copias de seguridad.</li>
                </ul>
                <p><strong>Usuarios no autenticados</strong>: Pueden registrarse y consultar bases de datos.</p>
            </div>
        </div>

        <div class="section">
            <h2 class="toggle-section">Funcionalidades del Administrador</h2>
            <div class="content">
                <p>Los administradores pueden:</p>
                <ul>
                    <li>Gestionar usuarios (altas, bajas, modificaciones).</li>
                    <li>Importar diccionarios vía JSON.</li>
                    <li>Consultar informes de resultados y datos.</li>
                    <li>Añadir o eliminar idiomas.</li>
                    <li>Realizar copias de seguridad y restauraciones (según nivel).</li>
                    <li><strong>Nivel 1</strong>: Modificar o eliminar palabras y frases.</li>
                </ul>
            </div>
        </div>

        <div class="section">
            <h2 class="toggle-section">Estructura de Datos</h2>
            <div class="content">
                <p>Un usuario puede participar en múltiples juegos y partidas.</p>
                <p><strong>Palabras y Frases</strong>:</p>
                <ul>
                    <li><strong>Palabras</strong>: Código, contenido, tipo (nombre, adjetivo, etc.), significado, dificultad.</li>
                    <li><strong>Frases</strong>: Refranes/proverbios con código, contenido y descripción.</li>
                    <li>Cada palabra/frase pertenece a un idioma, con vínculos a términos similares.</li>
                    <li>Actualizaciones vía JSON, con historial en <code>historicoPalabras</code> y <code>historicoFrases</code>.</li>
                </ul>
            </div>
        </div>

        <div class="section">
            <h2 class="toggle-section">Mejoras en la Jugabilidad</h2>
            <div class="content">
                <ul>
                    <li>🤝 <strong>Modo Cooperativo</strong>: Equipos para adivinar juntos.</li>
                    <li>🏆 <strong>Modo Torneo</strong>: Rondas eliminatorias y clasificación.</li>
                    <li>💡 <strong>Pistas Dinámicas</strong>: Sinónimos, definiciones o letras a cambio de puntos.</li>
                    <li>🎯 <strong>Dificultad Personalizable</strong>: Fácil, media o difícil.</li>
                    <li>📅 <strong>Desafíos Diarios/Semanales</strong>: Palabras especiales con recompensas.</li>
                    <li>⏳ <strong>Rondas Temporizadas</strong>: Bonificaciones por rapidez.</li>
                </ul>
            </div>
        </div>

        <div class="section">
            <h2 class="toggle-section">Mejoras Visuales y de Interacción</h2>
            <div class="content">
                <ul>
                    <li>🎭 <strong>Animaciones del Ahorcado</strong>: Reacciones dinámicas a aciertos/fallos.</li>
                    <li>🎨 <strong>Temas Personalizables</strong>: Estilos desbloqueables (pirata, medieval, etc.).</li>
                    <li>🎶 <strong>Sonidos y Música</strong>: Efectos y música de fondo ajustable.</li>
                </ul>
            </div>
        </div>

        <div class="section">
            <h2 class="toggle-section">Mejoras Técnicas</h2>
            <div class="content">
                <ul>
                    <li>🏅 <strong>Logros y Recompensas</strong>: Medallas por hitos.</li>
                    <li>📚 <strong>Modo Educativo</strong>: Aprendizaje de idiomas.</li>
                    <li>🔍 <strong>Categorías</strong>: Temáticas como animales o películas.</li>
                    <li>🌎 <strong>Juego Online</strong>: Ranking global y desafíos (compatible con Tomcat).</li>
                    <li>🤖 <strong>Modo IA</strong>: Bot oponente con dificultad ajustable.</li>
                </ul>
            </div>
        </div>

        <div class="section">
            <h2 class="toggle-section">Requisitos del Proyecto</h2>
            <div class="content">
                <ol>
                    <li>Diagrama Entidad/Relación.</li>
                    <li>Paso a tablas.</li>
                    <li>Base de datos MySQL con relaciones.</li>
                    <li>Repositorio en GitHub.</li>
                    <li>Diagrama de casos de uso.</li>
                    <li>Diagrama de clases.</li>
                    <li>Codificación en Java de las clases.</li>
                    <li>Interfaz gráfica Swing (formularios, menús, botones).</li>
                    <li>Aplicación Java funcional (según casos de uso).</li>
                    <li>Pruebas con JUnit.</li>
                    <li>Subir todo a GitHub.</li>
                    <li>Versión instalable.</li>
                </ol>
            </div>
        </div>

        <div class="section">
            <h2 class="toggle-section">Valoración</h2>
            <div class="content">
                <ol>
                    <li><strong>Análisis correcto</strong> (0.75 pt.): Diagramas de casos de uso y clases.</li>
                    <li><strong>Base de datos</strong> (0.75 pt.): Diagrama E/R, tablas, implementación.</li>
                    <li><strong>Vista</strong> (2 pt.): Interfaz gráfica.</li>
                    <li><strong>Controlador</strong> (2 pt.): Gestión de eventos.</li>
                    <li><strong>Modelo</strong> (3 pt.): Lógica y datos.</li>
                    <li><strong>Pruebas y errores</strong> (0.75 pt.): JUnit y try-catch.</li>
                    <li><strong>Documentación e instalable</strong> (0.75 pt.): JavaDoc y paquete instalable.</li>
                    <li><strong>Mejoras</strong> (2 pt.): Implementar al menos cuatro mejoras propuestas.</li>
                </ol>
            </div>
        </div>

        <div class="section">
            <h2 class="toggle-section">Calendario de Entregas</h2>
            <div class="content">
                <ul>
                    <li><strong>21 de Marzo, 2025</strong>: Diagramas (casos de uso, clases, E/R, tablas, base de datos).</li>
                    <li><strong>4 de Abril, 2025</strong>: Interfaz gráfica.</li>
                    <li><strong>16 de Abril, 2025</strong>: Controlador.</li>
                    <li><strong>23 de Mayo, 2025</strong>: Modelo.</li>
                    <li><strong>2 de Junio, 2025</strong>: Pruebas, documentación, instalable.</li>
                </ul>
            </div>
        </div>
    </div>

    <script>
        document.querySelectorAll('.toggle-section').forEach(section => {
            section.addEventListener('click', () => {
                const content = section.nextElementSibling;
                content.classList.toggle('active');
            });
        });

        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function (e) {
                e.preventDefault();
                document.querySelector(this.getAttribute('href')).scrollIntoView({
                    behavior: 'smooth'
                });
            });
        });
    </script>
</body>
</html>
