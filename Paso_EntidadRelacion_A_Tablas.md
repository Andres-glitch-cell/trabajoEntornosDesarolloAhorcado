Clave Compuesta
Clave Foránea
Clave Primaria
### Modelo Entidad-Relación a Tablas

#### Clave Compuesta
- **Definición:** Una clave compuesta es una clave primaria que consiste en dos o más atributos que juntos identifican de manera única una fila en una tabla.

#### Clave Foránea
- **Definición:** Una clave foránea es un atributo o conjunto de atributos en una tabla que se utiliza para establecer y reforzar un vínculo entre los datos de dos tablas.

#### Clave Primaria
- **Definición:** Una clave primaria es un atributo o conjunto de atributos que identifica de manera única cada fila en una tabla.

---

### Tablas del Modelo

#### **Usuario**
```plaintext
idUsuario, nombreCompleto, nombreUsuario, correoElectronico, contraseña, fechaRegistro
```

#### **Administrador**
```plaintext
nivelAdministrador, idUsuario (FK), fechaAsignacion, ultimoAcceso
```

#### **Jugador**
```plaintext
idUsuario (FK), puntosTotales, primeraPartida, ultimaPartida
```

#### **Cuota**
```plaintext
TipoDeCuota, idCuota
```

#### **Juego**
```plaintext
idJuego, resultado, modoJuego, maxIntentos, dificultadBase
```

#### **Partida** (Ternaria)
```plaintext
idPartida, idPalabra (FK), resultado, idJuego (FK), nivelDificultad, fechaInicio, fechaFin, puntosObtenidos, partesAhorcado, tiempoTotal, modoEspecial
```

#### **Historial**
```plaintext
idHistorial, idPalabra (FK), idFrase (FK), idAdministrador (FK), significadoFrase, significadoPalabra
```

#### **Idioma**
```plaintext
idIdioma, nombreIdioma, codigoISO
```

#### **Palabras || Frases**
```plaintext
idPalabra, idFrase, categoria, fechaCreacion, contenido, significadoFrase, significadoPalabra
```

---

### Notas
- **(FK):** Indica que el atributo es una clave foránea.
- **Ternaria:** La tabla `Partida` representa una relación ternaria entre varias entidades.
- **Palabras || Frases:** Esta tabla puede representar dos tipos de datos dependiendo del contexto.

Usuario
( idUsuario, nombreCompleto, nombreUsuario, correoElectronico, contraseña, fechaRegistro )

Administrador
( nivelAdministrador, idUsuario, fechaAsignacion, ultimoAcceso )

Jugador
( idUsuario, puntosTotales, primeraPartida, ultimaPartida )

Cuota
( TipoDeCuota, idCuota )

Juego
( idJuego, resultado, modoJuego, maxIntentos, dificultadBase )

Partida {Ternaria}
( idPartida, idPalabra, resultado, idJuego, nivelDificultad, fechaInicio, fechaFin, puntosObtenidos, partesAhorcado, tiempoTotal, modoEspecial )

Historial
( idHistorial, idPalabra, idFrase, idAdministrador, significadoFrase, significadoPalabra )

Idioma
( idIdioma, nombreIdioma, codigoISO )

Palabras || Frases
( idPalabra, idFrase, categoria, fechaCreacion, contenido, significadoFrase, significadoPalabra )