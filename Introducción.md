Este repositorio contiene el proyecto "Juego del Ahorcado", desarrollado como parte de la asignatura de Entornos de Desarrollo. El objetivo del proyecto es implementar un juego clásico de ahorcado con una base de datos relacional que gestione usuarios, partidas, palabras y estadísticas.

## Descripción del Proyecto
El juego permite a los usuarios registrarse, jugar partidas de ahorcado y acumular puntos. Incluye roles como jugadores y administradores, quienes pueden gestionar palabras y frases utilizadas en el juego. La base de datos está diseñada para soportar diferentes modos de juego, niveles de dificultad y un historial de partidas.

### Estructura de la Base de Datos
- **Usuario**: Almacena información de los usuarios (nombre, correo, contraseña, etc.).
- **Jugador**: Registra datos de los jugadores, como puntos totales y fechas de partidas.
- **Administrador**: Gestiona los usuarios con privilegios administrativos.
- **PalabrasFrases**: Contiene las palabras y frases usadas en el juego, con categorías y significados.
- **Juego**: Define configuraciones de juego (modo, intentos máximos, dificultad).
- **Partida**: Registra cada partida jugada, incluyendo resultado, puntos y tiempo.
- **Historial**: Guarda un registro de palabras y frases gestionadas por administradores.
- **Cuota**: Tipos de suscripción (mensual, anual).
- **Idioma**: Soporte para múltiples idiomas.

### Características Principales
- Registro y autenticación de usuarios.
- Modos de juego (clásico, rápido, etc.).
- Gestión de palabras y frases por administradores.
- Estadísticas de partidas y puntuaciones.
