# trabajoEntornosDesarolloAhorcado
Este es un juego creado por nustro tutor de DAW (Desarollo de Aplicaciones Web) El juego del Ahorcado hecho en Java con la interfaz de Java (Swing)

h1 **Sumario** h1 
1. Introducción......................................................................................................................................2
   
Idiomas disponibles.....................................................................................................................2

Registro e identificación de usuarios..........................................................................................2

Funcionalidades del administrador............................................................................................. 2

Estructura de datos...................................................................................................................... 3

Palabras y frases hechas.........................................................................................................3

Dificultades adicionales.............................................................................................................. 3

Registro de partidas.....................................................................................................................3

Representación visual del ahorcado............................................................................................4

Almacenamiento de datos históricos...........................................................................................4

Cuotas de jugadores....................................................................................................................4

Hilos automáticos........................................................................................................................4

3. Mejoras en la Jugabilidad.................................................................................................................5
4. 
5. Mejoras Visuales y de Interacción....................................................................................................6
6. 
7. Mejoras en la Gestión del Juego.......................................................................................................7
8. 
9. Mejoras Técnicas y de Seguridad.....................................................................................................8
10. 
11. Se pide.............................................................................................................................................. 9
12. 
13. Valoración.......................................................................................................................................10
14. 
15. Calendario de entregas....................................................................................................................11


1. Introducción (h1)
   
El juego del ahorcado consiste en adivinar una palabra letra por letra. Al inicio de la partida, en el
formulario aparecerán una serie de espacios en blanco, equivalentes al número de caracteres de la
palabra a adivinar. Cada vez que se introduzca una letra, si esta se encuentra en la palabra, se
mostrará en todas las posiciones correspondientes; en caso contrario, se revelará progresivamente
un componente del dibujo del ahorcado (cabeza, cuello, tronco, etc.).
Si el dibujo del ahorcado se completa antes de que la palabra haya sido adivinada, el jugador
perderá la partida. Independientemente del resultado, al finalizar la partida se mostrará la palabra
correcta junto con su significado.
El juego permite la participación de varios usuarios. Durante una sesión, cada usuario podrá jugar
tantas partidas como desee, y una misma partida podrá ser jugada por más de un participante. Se
llevará un registro de la puntuación obtenida por cada jugador, y al final se anunciará el ganador.
Antes de comenzar, será necesario especificar el número de jugadores y de partidas.
Al concluir el juego, el sistema generará un informe con los resultados de cada partida y la
clasificación final de los jugadores.
Idiomas disponibles
El juego podrá jugarse en uno de los siguientes cuatro idiomas: Español, Francés, Inglés o
Valenciano. Las palabras a adivinar estarán exclusivamente en el idioma seleccionado.
Registro e identificación de usuarios
Para participar, cada usuario deberá identificarse en el sistema. Tras una identificación exitosa,
pasará de ser un usuario normal a un usuario jugador. Se registrarán sus datos personales: número
de usuario, nombre y apellidos.
También existirán usuarios administradores, quienes deberán identificarse en el sistema. Se
registrarán su código de usuario, nombre, apellidos y nivel de administración, con tres niveles:
• Nivel 1: Máximo nivel de administración.
• Nivel 2: Puede realizar copias de seguridad y restauraciones.
• Nivel 3: Solo puede realizar copias de seguridad.
Los usuarios que interactúen sin autenticarse podrán registrarse proporcionando su nombre y
apellidos. Además, podrán acceder a las bases de datos del juego.
Funcionalidades del administrador
Los administradores podrán:
• Gestionar usuarios (altas, bajas y modificaciones).
• Importar nuevos diccionarios mediante archivos JSON.
2
Ej juego del ahorcado
• Consultar informes sobre los resultados de los juegos y los datos de los usuarios.
• Añadir nuevos idiomas.
• Eliminar idiomas.
• Realizar copias de seguridad y restauraciones de la base de datos (según su nivel).
El administrador de nivel 1 tendrá la facultad exclusiva de modificar la información de las palabras
(nombre, significado, tipo y nivel de dificultad), así como de eliminarlas si lo considera necesario.
Estructura de datos
Cada usuario podrá participar en múltiples juegos y, dentro de cada juego, en varias partidas. Se
almacenará información detallada sobre cada juego, partida y jugador.
Palabras y frases hechas
Se registrarán las palabras con su código, contenido, tipo (nombre, adjetivo, adverbio), significado y
nivel de dificultad. Para los idiomas, se almacenará su código y nombre. Cada idioma tendrá
múltiples palabras, pero cada palabra pertenecerá a un solo idioma.
Además del modo de juego basado en palabras individuales, habrá una modalidad en la que se
deberán adivinar frases hechas, refranes o proverbios. Se almacenará el código de cada frase, su
contenido y una breve descripción. Cada idioma tendrá múltiples frases hechas, pero cada una
pertenecerá a un único idioma.
Cada palabra o frase podrá estar relacionada con otras de significado similar, tanto en el mismo
idioma como en otros idiomas.
El administrador de nivel 1 podrá reemplazar las palabras y frases existentes mediante la carga de
un nuevo archivo JSON. Antes de realizar la sustitución, se guardarán las palabras y frases actuales
en tablas históricas llamadas historicoPalabras y historicoFrases.
Dificultades adicionales
Se podrán añadir variantes de juego, como:
• Mostrar la palabra o frase a adivinar en orden inverso.
• Incluir palabras o frases capicúas, con la posibilidad de reemplazarlas por otras similares.
Registro de partidas
Cada jugador podrá participar en varios juegos, y cada juego tendrá múltiples jugadores. Se
generará un informe con el código del jugador, el código del juego, la fecha y hora de la partida y
los puntos obtenidos. También se registrará en qué juegos ha aparecido cada palabra o frase hecha.
Cada partida se identificará con un número único, aunque este podrá repetirse en diferentes juegos.
3
Ej juego del ahorcado
Representación visual del ahorcado
El muñeco del ahorcado y la horca estarán compuestos por tantos elementos como caracteres tenga
la palabra o frase a adivinar. Con cada fallo, se mostrará un nuevo componente del dibujo (cabeza,
ojo, nariz, etc.).
Si se juega a adivinar una frase hecha en lugar de una palabra, el muñeco del ahorcado aparecerá
boca abajo.
Para cada partida, se registrará qué partes del muñeco fueron mostradas en el formulario, por
ejemplo: en una partida, solo la cabeza y el cuello; en otra, la cabeza, el cuello, el tronco y los
brazos.
Almacenamiento de datos históricos
Para evitar la saturación de las tablas de datos, el administrador de nivel 1 podrá trasladar
información a una tabla histórica denominada historico.
Cuotas de jugadores
Los jugadores deberán abonar una cuota anual, que podrá ser de dos tipos:
• Cuota normal: Permite jugar únicamente a adivinar palabras.
• Cuota premium: Permite jugar tanto a adivinar palabras como frases hechas.
Cada jugador solo podrá suscribirse a un tipo de cuota. Se almacenarán los siguientes datos de las
cuotas: código, tipo (normal o premium) e importe.
Hilos automáticos
El sistema contará con dos hilos automáticos:
1. Un proceso que realizará copias de seguridad cada 5 minutos.
2. Otro proceso que mostrará en todo momento la fecha y hora del sistema en formato digital
(hora, minutos y segundos).
4
Ej juego del ahorcado
2. Mejoras en la Jugabilidad
1. Modo Cooperativo 🤝
• Permitir que varios jugadores colaboren en la misma partida para adivinar la palabra
o frase en equipo.
2. Modo Torneo 🏆
• Crear un sistema de torneos con rondas eliminatorias y una tabla de clasificación.
• Los jugadores avanzan según su desempeño en cada ronda.
3. Pistas Dinámicas 💡
• Permitir a los jugadores pedir pistas a cambio de una penalización en los puntos.
• Pistas posibles: sinónimos, definiciones, frases de contexto, o mostrar una letra
aleatoria.
4. Niveles de Dificultad Personalizables 🎯
• Incluir una opción para que los jugadores puedan elegir entre dificultad fácil, media
o difícil.
• En dificultad alta, limitar el número de intentos o agregar palabras/frases más
complejas.
5. Desafíos Diarios o Semanales 📅
• Ofrecer una palabra/frase especial cada día con recompensas adicionales para
quienes la acierten.
6. Rondas Temporizadas ⏳
• Introducir un límite de tiempo por turno para aumentar la emoción.
• Se podrían otorgar puntos extra por adivinar rápido.
5
Ej juego del ahorcado
3. Mejoras Visuales y de Interacción
1. Dibujos Animados del Ahorcado 🎭
• En lugar de un simple dibujo estático, animaciones del muñeco reaccionando a los
aciertos o errores.
2. Temas Personalizables 🎨
• Fondos de pantalla y estilos de ahorcado desbloqueables (ej. estilo pirata, medieval,
futurista).
3. Efectos de Sonido y Música 🎶
• Sonidos para cada acción (aciertos, errores, fin de la partida).
• Música de fondo con opción de personalización.
6
Ej juego del ahorcado
4. Mejoras en la Gestión del Juego
1. Sistema de Logros y Recompensas 🏅
Obtener medallas o insignias por logros específicos (ej. ganar 10 partidas seguidas, adivinar
sin errores).
Posibilidad de usar puntos acumulados para desbloquear personalizaciones.
2. Modo Educativo 📚
Implementar una versión del juego enfocada en el aprendizaje de idiomas.
Agregar un diccionario interactivo con ejemplos de uso de las palabras.
3. Palabras y Frases por Categorías 🔍
Permitir a los jugadores elegir una categoría de palabras/frases (ej. animales, películas,
historia).
4. Juego Online con Ranking Global 🌎
Competir contra jugadores de todo el mundo y ver la clasificación en tiempo real.
Modo de desafíos entre amigos o jugadores aleatorios. (En este caso la aplicación debería
poder funcionar en un servidor web Tomcat)
7
Ej juego del ahorcado
5. Mejoras Técnicas y de Seguridad
1. Modo Inteligencia Artificial (IA) 🤖
Un bot que actúe como oponente, con diferentes niveles de dificultad. 
