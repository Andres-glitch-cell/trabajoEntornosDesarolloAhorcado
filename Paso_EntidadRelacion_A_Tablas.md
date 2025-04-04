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
```sql
idUsuario(PK), nombreCompleto, nombreUsuario, correoElectronico, contraseña, fechaRegistro
```

#### **Administrador**
```sql
nivelAdministrador, idUsuario (FK), fechaAsignacion, ultimoAcceso
```

#### **Jugador**
```sql
idUsuario (FK), puntosTotales, primeraPartida, ultimaPartida
```

#### **Cuota**
```sql
TipoDeCuota, idCuota(PK)
```

#### **Juego**
```sql
idJuego(PK), resultado, modoJuego, maxIntentos, dificultadBase
```

#### **Partida** (Ternaria)
```sql
idPartida(PK), idPalabra (FK), resultado, idJuego (FK), nivelDificultad, fechaInicio, fechaFin, puntosObtenidos, partesAhorcado, tiempoTotal, modoEspecial
```

#### **Historial**
```sql
idHistorial(PK), idPalabra (FK), idFrase (FK), idAdministrador (FK), significadoFrase, significadoPalabra
```

#### **Idioma**
```sql
idIdioma(PK), nombreIdioma, codigoISO
```

#### **Palabras || Frases**
```sql
idPalabra(PK), idFrase, categoria, fechaCreacion, contenido, significadoFrase, significadoPalabra
```

---

### Notas
- **(FK):** Indica que el atributo es una clave foránea.
- **Ternaria:** La tabla `Partida` representa una relación ternaria entre varias entidades.
- **Palabras || Frases:** Esta tabla puede representar dos tipos de datos dependiendo del contexto.
- **(PK):** Indica la clave primaria (Primary Key)