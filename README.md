# Mi Proyecto Genial
![GitHub stars](https://img.shields.io/github/stars/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado?style=social)

Un juego entretenido para [entretenerse con el ahorcado]. Construido con ❤️ por [Andrés].

## Tabla de Contenidos
- [Instalación](#instalación)
- [Verificación](#verificación)
- [Introducción](#introducción)


# Instalación
1. Clona el repositorio a si es Linux / Ubuntu /home/tu-user/repositorio-creado y en Windows C:\Users\Pc\Desktop\repositorio-creado
   ```bash
   git clone https://github.com/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado.git
   
# Verificación
## Pasos para asegurarte de que funcione:
1. **Crea el repositorio en GitHub**: Si aún no lo has hecho, ve a GitHub, crea un repositorio con el nombre que quieras (ej. `juego-ahorcado`) bajo tu usuario.
2. **Copia la URL del repositorio**: Haz clic en el botón verde "Code" en GitHub y copia el enlace HTTPS (será algo como `https://github.com/tu-nombre-de-usuario/tu-repositorio.git`).
   
   
# Introducción
### El Juego del Ahorcado es un clásico desafío de adivinar palabras letra por letra. Al comenzar, se muestran espacios en blanco correspondientes a cada carácter de la palabra secreta. Con cada letra ingresada. Si aciertas, la letra aparece en su posición correcta, si fallas, se dibuja una parte del ahorcado (cabeza, tronco, etc.), si el dibujo se completa antes de adivinar la palabra, pierdes. Al final de cada partida, se revela la palabra junto con su significado.
### El juego soporta múltiples usuarios, permite varias partidas por sesión y registra puntuaciones para determinar un ganador al final. Antes de empezar, se define el número de jugadores y partidas, y al concluir, se genera un informe con resultados y clasificaciones.

# Idiomas Disponibles 🇻🇪 🇪🇸 🇬🇧 🇫🇷
## El juego está disponible en cuatro idiomas:
## 🇪🇸 Español
## 🇫🇷 Francés
## 🇬🇧 Inglés
## 🇻🇪 Valenciano
## Las palabras a adivinar corresponden exclusivamente al idioma seleccionado.

# Registro e Identificación de Usuarios
### Jugadores: Deben registrarse con número de usuario, nombre y apellidos para participar.
### Administradores: Tienen niveles de acceso:
### Nivel 1: Máxima autoridad.
### Nivel 2: Copias de seguridad y restauraciones.
### Nivel 3: Solo copias de seguridad.
### Usuarios no autenticados pueden registrarse y consultar bases de datos.

# Funcionalidades del Administrador
## Los administradores pueden:
### Gestionar usuarios (altas, bajas, modificaciones).
### Importar diccionarios vía archivos JSON.
### Consultar informes de resultados y datos.
### Añadir o eliminar idiomas.
### Realizar copias de seguridad y restauraciones (según nivel).
### El administrador de Nivel 1 puede modificar o eliminar palabras y frases.

# Estructura de Datos
### Un usuario puede participar en múltiples juegos y partidas.
### Se almacenan datos detallados de cada partida, jugador y juego.
### Palabras y Frases Hechas
### Palabras: Registradas con código, contenido, tipo (nombre, adjetivo, etc.), significado y dificultad.
### Frases: Incluyen refranes y proverbios con código, contenido y descripción.
### Cada palabra/frase pertenece a un único idioma, pero puede vincularse a términos similares en otros idiomas.
### El administrador puede actualizarlas mediante JSON, guardando lo anterior en tablas históricas (historicoPalabras, historicoFrases).
### Dificultades Adicionales
### Variantes opcionales:

## Palabras/frases en orden inverso.
### Uso de capicúas con reemplazos similares.
### Registro de Partidas
### Informes con código de jugador, juego, fecha, hora y puntos.
### Registro de palabras/frases usadas por partida.
### Representación Visual del Ahorcado
### El dibujo se construye con elementos según la longitud de la palabra/frase.
### En modo "frases hechas", el ahorcado aparece boca abajo.
### Se registra qué partes se mostraron en cada partida.
### Almacenamiento de Datos Históricos
### Tabla historico para evitar saturación, gestionada por el administrador de Nivel 1.
### Cuotas de Jugadores
### Normal: Solo palabras.
### Premium: Palabras y frases hechas.
### Datos: código, tipo e importe.
### Hilos Automáticos
### Copias de seguridad cada 5 minutos.
### Reloj digital con hora, minutos y segundos.
### Mejoras en la Jugabilidad
### Modo Cooperativo 🤝
### Equipos para adivinar juntos.
### Modo Torneo 🏆
### Rondas eliminatorias y clasificación.
### Pistas Dinámicas 💡
### Sinónimos, definiciones o letras a cambio de puntos.
### Dificultad Personalizable 🎯
### Fácil, media o difícil con ajustes en intentos o complejidad.
### Desafíos Diarios/Semanales 📅
### Palabras especiales con recompensas.
### Rondas Temporizadas ⏳
### Límite de tiempo con bonificaciones por rapidez.
### Mejoras Visuales y de Interacción
### Animaciones del Ahorcado 🎭
### Reacciones dinámicas a aciertos y fallos.
### Temas Personalizables 🎨
### Estilos desbloqueables (pirata, medieval, etc.).
### Sonidos y Música 🎶
### Efectos para acciones y música de fondo ajustable.
### Mejoras en la Gestión del Juego
### Logros y Recompensas 🏅
### Medallas por hitos y desbloqueo de personalizaciones.
### Modo Educativo 📚
### Aprendizaje de idiomas con diccionario interactivo.
### Categorías 🔍
### Temáticas como animales o películas.
### Juego Online 🌎
### Ranking global y desafíos entre amigos (compatible con Tomcat).
### Mejoras Técnicas y de Seguridad
### Modo IA 🤖
### Bot oponente con niveles de dificultad ajustables.

# Se Pide

## 6. Se pide
### 1. Diagrama Entidad/Relación.
### 2. Paso a tablas.
### 3. Creación de la correspondiente base de datos MySQL con las relaciones entre sus tablas.
### 4. Alta en gitHub de un repositorio del proyecto.
### 5. Diagrama de casos de uso.
### 6. Diagrama de clases
### 7. Codificación en Java de las clases del diagrama de clases.
### 8. Interfaz gráfica Swing del proyecto: Formularios, menús de opciones, botones, …
### 9. Aplicación en Java que cumpla los requisitos funcionales del sistema (diagramas de casos de uso).
### 10. Realización de pruebas del programa (JUnit).
### 11. Subir todo el contenido del proyecto (puntos anteriores) a gitHub.
### 12. Preparación de una versión instalable del programa

# Valoración
## 7. Valoración
### 1. Análisis correcto:  Diagrama de casos de uso, Diagrama de clases (0,75 pt.)
### 2. Diagrama Entidad /Relación, paso a tablas, implementación de la base de datos (0,75 pts.)
### 3. Vista: Interfaz gráfica (2 pts.)
### 4. Controlador: (2 pts.)
### 5. Modelo (3 pts.)
### 6. Pruebas (JUnit) y gestión de errores(try...catch) (0,75 pts.)
### 7. Documentación con JavaDoc y creación del instalable (0,75 pt.) 
### Realización de al menos cuatro mejoras propuestas en los apartados: 2, 3, 4, 5 (2 pts).

# Calendario de Entregas
## 8. Calendario de entregas 
### 21 de Marzo del 2025: Apartados 1, 2: 
### • Análisis correcto:  Diagrama de casos de uso, Diagrama de clases.
### • Diagrama Entidad /Relación, paso a tablas, implementación de la base de datos.
### 4 de Abril del 2025: Apartado 3:
### ▪ Vista: Interfaz gráfica
### 16 de Abril del 2025: Apartado 4:
### ▪ Controlador: Gestión de eventos
### 23 de Mayo del 2025: Apartado 5:
### ▪ Modelo
### 2 de Junio del 2025; Apartados 6, 7:
### ▪ Pruebas (JUnit) y gestión de errores (try...catch)
### ▪ Documentación con JavaDoc y creación del instalable
