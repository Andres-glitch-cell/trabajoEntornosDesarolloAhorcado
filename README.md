# Introducción
## El Juego del Ahorcado es un clásico desafío de adivinar palabras letra por letra. Al comenzar, se muestran espacios en blanco correspondientes a cada carácter de la palabra secreta. Con cada letra ingresada:

Si aciertas, la letra aparece en su posición correcta.
Si fallas, se dibuja una parte del ahorcado (cabeza, tronco, etc.).
Si el dibujo se completa antes de adivinar la palabra, pierdes. Al final de cada partida, se revela la palabra junto con su significado.

## El juego soporta múltiples usuarios, permite varias partidas por sesión y registra puntuaciones para determinar un ganador al final. Antes de empezar, se define el número de jugadores y partidas, y al concluir, se genera un informe con resultados y clasificaciones.

# Idiomas Disponibles
El juego está disponible en cuatro idiomas:

## 🇪🇸 Español
## 🇫🇷 Francés
## 🇬🇧 Inglés
## 🇻🇪 Valenciano
## Las palabras a adivinar corresponden exclusivamente al idioma seleccionado.

# Registro e Identificación de Usuarios
## Jugadores: Deben registrarse con número de usuario, nombre y apellidos para participar.
## Administradores: Tienen niveles de acceso:
### Nivel 1: Máxima autoridad.
### Nivel 2: Copias de seguridad y restauraciones.
### Nivel 3: Solo copias de seguridad.
### Usuarios no autenticados pueden registrarse y consultar bases de datos.
### Funcionalidades del Administrador
## Los administradores pueden:

### Gestionar usuarios (altas, bajas, modificaciones).
### Importar diccionarios vía archivos JSON.
### Consultar informes de resultados y datos.
### Añadir o eliminar idiomas.
### Realizar copias de seguridad y restauraciones (según nivel).
### El administrador de Nivel 1 puede modificar o eliminar palabras y frases.

Estructura de Datos
Un usuario puede participar en múltiples juegos y partidas.
Se almacenan datos detallados de cada partida, jugador y juego.
Palabras y Frases Hechas
Palabras: Registradas con código, contenido, tipo (nombre, adjetivo, etc.), significado y dificultad.
Frases: Incluyen refranes y proverbios con código, contenido y descripción.
Cada palabra/frase pertenece a un único idioma, pero puede vincularse a términos similares en otros idiomas.
El administrador puede actualizarlas mediante JSON, guardando lo anterior en tablas históricas (historicoPalabras, historicoFrases).
Dificultades Adicionales
Variantes opcionales:

Palabras/frases en orden inverso.
Uso de capicúas con reemplazos similares.
Registro de Partidas
Informes con código de jugador, juego, fecha, hora y puntos.
Registro de palabras/frases usadas por partida.
Representación Visual del Ahorcado
El dibujo se construye con elementos según la longitud de la palabra/frase.
En modo "frases hechas", el ahorcado aparece boca abajo.
Se registra qué partes se mostraron en cada partida.
Almacenamiento de Datos Históricos
Tabla historico para evitar saturación, gestionada por el administrador de Nivel 1.
Cuotas de Jugadores
Normal: Solo palabras.
Premium: Palabras y frases hechas.
Datos: código, tipo e importe.
Hilos Automáticos
Copias de seguridad cada 5 minutos.
Reloj digital con hora, minutos y segundos.
Mejoras en la Jugabilidad
Modo Cooperativo 🤝
Equipos para adivinar juntos.
Modo Torneo 🏆
Rondas eliminatorias y clasificación.
Pistas Dinámicas 💡
Sinónimos, definiciones o letras a cambio de puntos.
Dificultad Personalizable 🎯
Fácil, media o difícil con ajustes en intentos o complejidad.
Desafíos Diarios/Semanales 📅
Palabras especiales con recompensas.
Rondas Temporizadas ⏳
Límite de tiempo con bonificaciones por rapidez.
Mejoras Visuales y de Interacción
Animaciones del Ahorcado 🎭
Reacciones dinámicas a aciertos y fallos.
Temas Personalizables 🎨
Estilos desbloqueables (pirata, medieval, etc.).
Sonidos y Música 🎶
Efectos para acciones y música de fondo ajustable.
Mejoras en la Gestión del Juego
Logros y Recompensas 🏅
Medallas por hitos y desbloqueo de personalizaciones.
Modo Educativo 📚
Aprendizaje de idiomas con diccionario interactivo.
Categorías 🔍
Temáticas como animales o películas.
Juego Online 🌎
Ranking global y desafíos entre amigos (compatible con Tomcat).
Mejoras Técnicas y de Seguridad
Modo IA 🤖
Bot oponente con niveles de dificultad ajustables.

Se Pide
(Detallar aquí los requisitos específicos del proyecto).

Valoración
(Explicar los criterios de evaluación).

Calendario de Entregas
(Indicar fechas clave).
