<!-- Badges agrupados y limpios alineados a la izquierda, tamaño medio -->
<div align="left">
  <a href="https://github.com/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado/stargazers">
    <img src="https://img.shields.io/github/stars/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado?style=plastic&label=Stars&color=6C78AF&logo=github" alt="Stars" height="28"/>
  </a>
  <a href="https://github.com/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado/network/members">
    <img src="https://img.shields.io/github/forks/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado?style=plastic&label=Forks&color=43e97b&logo=github" alt="Forks" height="28"/>
  </a>
  <a href="https://github.com/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado/issues">
    <img src="https://img.shields.io/github/issues/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado?style=plastic&label=Issues&color=fc466b&logo=github" alt="Issues" height="28"/>
  </a>
  <a href="LICENSE">
    <img src="https://img.shields.io/github/license/Andres-glitch-cell/trabajoEntornosDesarolloAhorcado?style=plastic&label=License&color=3f5efb" alt="License" height="28"/>
  </a>
  <a href="#">
    <img src="https://visitor-badge.laobi.icu/badge?page_id=Andres-glitch-cell.trabajoEntornosDesarolloAhorcado&style=plastic&color=38f9d7" alt="Visitors" height="28"/>
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Java-17-007396?style=plastic&logo=java&logoColor=white" alt="Java" height="28"/>
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=plastic&logo=mysql&logoColor=white" alt="MySQL" height="28"/>
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Swing-GUI-6C78AF?style=plastic" alt="Swing" height="28"/>
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Estado-Production%20Ready-brightgreen?style=plastic" alt="Estado" height="28"/>
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Version-1.0.0-blue?style=plastic" alt="Version" height="28"/>
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Soporte-Discord-7289DA?style=plastic&logo=discord&logoColor=white" alt="Discord" height="28"/>
  </a>
</div>

# 🎮 Juego del Ahorcado - Proyecto de Entornos de Desarrollo

> Proyecto completo para la gestión y desarrollo de un juego del ahorcado multijugador, con administración avanzada, mejoras visuales y técnicas, y arquitectura profesional.

---

## 📑 Sumario

1. [Introducción](#introducción)
2. [Funcionalidades del Administrador](#funcionalidades-del-administrador)
3. [Estructura de Datos](#estructura-de-datos)
4. [Palabras y Frases Hechas](#palabras-y-frases-hechas)
5. [Dificultades Adicionales](#dificultades-adicionales)
6. [Registro de Partidas](#registro-de-partidas)
7. [Representación Visual del Ahorcado](#representación-visual-del-ahorcado)
8. [Almacenamiento de Datos Históricos](#almacenamiento-de-datos-históricos)
9. [Cuotas de Jugadores](#cuotas-de-jugadores)
10. [Hilos Automáticos](#hilos-automáticos)
11. [Mejoras en el Juego](#mejoras-en-el-juego)
12. [Mejoras Visuales y de Interacción](#mejoras-visuales-y-de-interacción)
13. [Mejoras en la Gestión del Juego](#mejoras-en-la-gestión-del-juego)
14. [Mejoras Técnicas y de Seguridad](#mejoras-técnicas-y-de-seguridad)
15. [Se Pide](#se-pide)
16. [Valoración](#valoración)
17. [Calendario de Entregas](#calendario-de-entregas)

---

## 1. Introducción

El juego del ahorcado consiste en adivinar una palabra letra por letra.  
Cada vez que se introduce una letra, si está en la palabra, se muestra en su posición; si no, se revela una parte del dibujo del ahorcado.  
Si el dibujo se completa antes de adivinar la palabra, el jugador pierde. Al final, se muestra la palabra correcta y su significado.

- Participación de varios jugadores por sesión y juego.
- Registro de puntuaciones y anuncio de ganador.
- Informe final con resultados y calificaciones.
- Gestión de juegos, partidas, palabras/frases e idiomas.
- Administradores con diferentes niveles de permisos.

---

## 2. Funcionalidades del Administrador

- Gestionar usuarios (altas, bajas, modificaciones).
- Importar diccionarios mediante archivos JSON.
- Consultar informes de resultados y datos de usuarios.
- Añadir y eliminar idiomas.
- Realizar copias de seguridad y restauraciones (según nivel).
- Modificar/eliminar palabras y frases (solo nivel 1).

---

## 3. Estructura de Datos

- Usuarios pueden participar en múltiples juegos y partidas.
- Información detallada de cada juego, partida y jugador.
- Palabras/frases con código, contenido, tipo, significado y dificultad.
- Idiomas con código, nombre y número de palabras/frases.
- Relación de palabras/frases similares entre idiomas.

---

## 4. Palabras y Frases Hechas

- Modalidad de adivinanza de frases hechas, refranes o proverbios.
- Cada frase tiene código, contenido y descripción.
- Relación de frases similares entre idiomas.
- Sustitución de palabras/frases mediante JSON (nivel 1).
- Histórico de palabras/frases antes de sustitución.

---

## 5. Dificultades Adicionales

- Palabras/frases en orden inverso.
- Palabras/frases capicúas y posibilidad de reemplazo.

---

## 6. Registro de Partidas

- Informe con código de jugador, juego, fecha/hora y puntos.
- Registro de aparición de palabras/frases en juegos.
- Identificación única de partidas.

---

## 7. Representación Visual del Ahorcado

- Muñeco y horca con tantos elementos como caracteres tenga la palabra/frase.
- Cada fallo muestra un nuevo componente.
- En frases hechas, el muñeco aparece boca abajo.
- Registro de partes mostradas en cada partida.

---

## 8. Almacenamiento de Datos Históricos

- Tabla histórica para evitar saturación de datos.
- Traslado de información por el administrador de nivel 1.

---

## 9. Cuotas de Jugadores

- Cuota anual: normal (solo palabras) o premium (palabras y frases).
- Un solo tipo de cuota por jugador.
- Registro de código, tipo e importe.

---

## 10. Hilos Automáticos

- Copias de seguridad cada 5 minutos.
- Reloj digital en la interfaz (hora, minutos, segundos).

---

## 11. Mejoras en el Juego

- **Modo Cooperativo 🤝:** Varios jugadores colaboran en la misma partida.
- **Modo Torneo 🏆:** Rondas eliminatorias y tabla de clasificación.
- **Pistas Dinámicas 💡:** Pistas a cambio de puntos (sinónimos, definiciones, letras).
- **Dificultad Personalizable 🎯:** Fácil, media o difícil, con palabras/frases más complejas.
- **Desafíos Diarios/Semanales 📅:** Palabra/frase especial con recompensas.
- **Rondas Temporizadas ⏳:** Límite de tiempo y puntos extra por rapidez.

---

## 12. Mejoras Visuales y de Interacción

- **Dibujos Animados 🎭:** Animaciones del muñeco según aciertos/errores.
- **Temas Personalizables 🎨:** Fondos y estilos desbloqueables.
- **Efectos de Sonido y Música 🎶:** Sonidos y música de fondo personalizables.

---

## 13. Mejoras en la Gestión del Juego

- **Logros y Recompensas 🏅:** Medallas por hitos y personalizaciones.
- **Modo Educativo 📚:** Aprendizaje de idiomas y diccionario interactivo.
- **Categorías 🔍:** Palabras/frases por temática.
- **Juego Online 🌎:** Ranking global y desafíos entre jugadores (compatible con Tomcat).

---

## 14. Mejoras Técnicas y de Seguridad

- **Modo IA 🤖:** Bot oponente con diferentes niveles de dificultad.

---

## 15. Se Pide

1. Diagrama Entidad/Relación.
2. Paso a tablas.
3. Base de datos MySQL con relaciones.
4. Repositorio en GitHub.
5. Diagrama de casos de uso.
6. Diagrama de clases.
7. Codificación en Java.
8. Interfaz gráfica Swing.
9. Aplicación funcional en Java.
10. Pruebas con JUnit.
11. Subir todo a GitHub.
12. Versión instalable.

---

## 16. Valoración

- **Análisis correcto:** Diagramas de casos de uso y clases (0,75 pt.)
- **Base de datos:** Diagrama E/R, tablas, implementación (0,75 pt.)
- **Vista:** Interfaz gráfica (2 pt.)
- **Controlador:** Gestión de eventos (2 pt.)
- **Modelo:** Lógica y datos (3 pt.)
- **Pruebas y errores:** JUnit y try-catch (0,75 pt.)
- **Documentación e instalable:** JavaDoc y paquete instalable (0,75 pt.)
- **Mejoras:** Al menos cuatro mejoras implementadas (2 pt.)

---

## 17. Calendario de Entregas

- **21 de Marzo 2025:** Análisis, diagramas, base de datos.
- **4 de Abril 2025:** Interfaz gráfica.
- **16 de Abril 2025:** Controlador.
- **23 de Mayo 2025:** Modelo.
- **2 de Junio 2025:** Pruebas, documentación, instalable.

---
