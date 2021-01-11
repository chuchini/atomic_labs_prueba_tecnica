# Prueba técnica del programa de becarios de Atomic Labs
## Jesús Fernando Moreno Ruíz

# Instrucciones de ejecución.
Clonar el repositorio en la carpeta de su preferencia, en su computadora.
Posteriormente es necesario compilar cada una de las clases incluidas con este
repositorio, esto puede hacerse desde la carpeta **./com/jesus/moreno**

```
javac Main.java controlador/Controlador.java modelo/Zombie.java modelo/Colaborador.java vista/Vista.java
```

Seguido de esto y para que sea posbile ejecutar el programa, es necesario retroceder a la carpeta
raíz, es decir, antes de la carpeta **/com**, y ejecutar el programa de la siguiente forma:

```
java com.jesus.moreno.Main
```

Después de lo anterior, se iniciará la ejecución del programa, mostrando en pantalla el tablero y, en consola,
se mostrarán los mensajes de eventos:

1. Zombie llegó por la ventana de la casilla X,Y
2. Humano Z infectado en la casilla X,Y
3. Humano Z salvado en la casilla X,Y

# Explicación del problema.
El problema consiste en un tablero de 20x20 en donde hay colaboradores y zombies. Al principio sólo hay 2 zombies,
los cuales aparecen de manera aleatoria. Seguido de ello comienzan a deambular de manera aleatoria sobre el tablero,
respetando las leyes básicas de la física, es decir, no pueden atravesar paredes, no pueden moverse a una casilla que
ya está ocupada, ya sea por zombies o por colaboradores. En su turno ejecutan 4 movimientos.

Por otra parte, los colaboradores sólo se pueden mover 2 veces en dirección a la salida (puesto que poseen este conocimiento)
y, de igual manera, respetando no atravesar paredes y no moverse a casillas ya ocupadas.

Finalmente, los zombies tratarán de infectar al máximo de colaboradores que sea posible; esto sucedera si algún humano se 
encuentra en alguna casilla adyacente al zombie. Los colaboradores, en su lugar, trataran de escapara por medio de la salida.
La ejecución del programa termina una vez que no haya colaboradores en el tablero.

# Explicación de la solución.
Para llegar a la solución del programa se hizo uso del concepto clásico "divide y vencerás".

Primeramente se dividió el problema en las siguientes partes:
1. Dibujar un tablero de 20x20
2. Realizar la aparición de zombies y colaboradores en el tablero lógico (a partir de ahora, matriz).
3. Dibujar cada uno de los elementos en el tablero de 20x20 (zombies, colaboradores, paredes y salidas) a partir de la matriz.
4. Modelar la clase Zombie referente al mismo objeto, con sus atributos y métodos (características y comportamiento).
5. Modelar la clase Colaborador referente al mismo objeto, con sus atributos y métodos (características y comportamiento).
6. Definir la rutina lógica para el turno de loz zombies.
7. Definir la rutina lógica para el turno de los colaboradores.
8. Llevar un registro de la bitácora del programa.
9. Englobar la rutina de los zombies y los colaboradores junto con la rutina completa del programa, es decir, manejar los turnos y verificar si el programa ha terminado su ejecución

Una vez definido todos estos subproblemas, procedí a resolver cada uno de ellos. Para tener una estructura de código limpia, me apoyé en el patrón
de diseño Modelo-Vista-Controlador. Siguiendo este patrón de diseño nos es posible separar la lógica del programa y las vistas que el usuario
final usará para interactuar con el programa, siendo el controlador el encargado de conectar ambas partes y ejecutar la lógica de los modelos.

### Subproblema I.
Ṕara esta parte bastó con usar una clase que hereda JFrame y dibujar en ella una cuadricula de 20x20 haciendo uso de labels, que corresponden a cada
casilla. Una vez hecho esto lo único que hay que agregar a esta clase es la funcionalidad de saber qué dibujar con la información que le manda el controlador
y, también, cómo procesar la información una vez que se tiene que refrescar la vista (repaint).

### Subproblema II.
Para tener la lógica del tablero (qué se encuentra en cada una de las casillas) se utiliza una matriz bidimensional de 20x20, donde se hace uso del siguiente
código:

| Código        | Significado           |
| ------------- |:---------------------:|
| 0             | Pared                 |
| 1             | Casilla vacía         |
| 2             | Ventana               |
| 3             | Zombie                |
| 4             | Colaborador           |
| 5             | Salida                |
| 6             | Colaborador infectado |

La aparición de loz zombies es aleatoria en alguna de las ventanas, por lo que se usa **Math.random** para obtener un número aleatorio correspondiente a las ventanas,
para hacer que los zombies aparezcan en cada una de las secciones de ventanas, se implementa la aparición aleatoria en cada una, es decir, un zombie aparecerá de manera
aleatoria en las primeras 4 ventanas y el siguiente zombie aparecerá de manera aleatoria en las siguientes 4 ventanas.

Para el caso de los colaboradores es más sencillo, se añaden de manera manual en el tablero, correspondiendo a la configuración ya dada por el problema. Para llevar
un registro del número de zombies y el número de colaboradores, se utilizan dos ArrayList.

### Subproblema III.
Para este problema basta con pasar la matriz a la vista por medio del controlador. La vista procesara la información y con base a ella dibujara el tablero.

### Subproblema IV.
La clase que modela al zombie es sencilla, los únicos atributos que nos interesa tener son su posición en el tablero (fila, columna). De comportamiento únicamente
nos interesa que se mueva, por lo que tenemos un método encargado de realizar el movimiento con base a un número que representa la opción de movimiento.
Este número va del 1 al 8 ya que sólo existen 8 posibles movimientos para el zombie. Esto se aprecia en el siguiente dibujo

| [x-1,y+1] | [x,y+1] | [x+1,y+1] |
|-----------|---------|-----------|
| [x-1,y]   |    Z    | [x+1,y]   |
| [x-1,y-1] | [x,y-1] | [x+1,y-1] |

Ahora, dado que el tablero es una matriz, los movimientos se realizan de otra manera dado que los movimientos en los ejes no son iguales. Por ejemplo, el movimiento en diagonal a la esquina inferior derecha sería [x+1,y+1] dado que eso implicaría un aumento para el eje Y, contrario a un plano convencional en donde estaríamos restandole 1 al eje Y. Todo esto se encuentra documentado en el código de la clase Zombie.

### Subproblema V.
Para el caso de la clase colaborador es muy parecido a la del zombie, de atributos tenemos su posición (fila, columna), sin embargo también tenemos un atributo de tipo boolean que nos indicará si el colaborador esta infectado o no. También tenemos otro atributo de tipo boolean que nos indica si el colaborador se ha salvado o no, y tenemos un último atributo que nos sirve de contador para saber si un colaborador ya se transformará o no.

De igual forma tenemos un método que modela el movimiento del colaborador,, siguiendo la misma lógica del zombie, es decir, 8 posibles movimientos.

### Subproblema VI.
Para la rutina de los zombies se contemplan 2 cosas:
* El zombie verificará si tiene algún colaborador en alguna casilla adyacente, en caso de que sí se encuentre alguno, el zombie lo infectará.
* El zombie se moverá de casilla de manera aleatoria, para validar que el movimiento se pueda realizar se usan método auxiliares que comprueba que a la casilla que quiere moverse no sea pared, no sea ventana, no esté ocupada por otro zombie o colaborador y que no se salga del rango. En caso de que el movimiento no sea válido se ejecutara nuevamente el método envargado de generar un movimiento aleatorio hasta que el movimiento encontrado sea valido.

Básicamente en eso consiste el turno del zombie.

### Subproblema VII.
Antes de explicar la rutina del colaborador, explicaré la forma en que se mueven, es decir, cómo saben a donde moverse para llegar a la meta.
El mapa lo dividimos en 4 cuadrantes. correspondientes a las coordenadas [0,0] y [10,9] para el primer cuadrante. [0,10] y [10,19] para el segundo cuadrante. [10,0] y [19,9] para el tercer cuadrante y, finalmente,   [10,10] y [19,19] para el cuarto cuadrante. Para el primer y segundo cuadrante (toda la parte superior del tablero) los colaboradores tienen como objetivo llegar a la casilla 9, la cual les permitirá moverse hacia abajo. Para el caso de los otros dos cuadrantes, su objetivo será avanzar hacia abajo hasta llegar a una casilla alineada con la meta. Una vez que se encuentren alineados con la meta, se moverán hacia ella (derecha) hasta llegar a ella.
Cabe destacar que si el colaborador se encuentra alineado con la meta. ya no ejecutara el movimiento hacia abajo, puesto que el movimiento hacia la meta tiene prioridad.
Siempre intentarán moverse en diagonal.

Ahora, para la rutina del colaborador se contemplan 3 cosas:
* El colaborador genera un movimiento con base a 6 posibles casos:
    1. Primer cuadrante.
    2. Segundo cuadrante.
    3. Tercer cuadrante.
    4. Cuarto cuadrante.
    5. Movimiento hacia abajo (está en la casilla 9)
    6. Movimiento hacia la meta (alineado con la meta)
Una vez generado el movimiento y que haya sido verificado que sea un movimiento valido (no atraviesa paredes, no se sale del tablero y no se mueve a casillas ocupadas), el colaborador realizará el movimiento.
* El colaborador verificará si esta infectado, en caso de que lo este se ejecutara el método auxiliar encargado de completar el pŕoceso de infección, es decir, aumentar el contador de turnos y, en caso de que hayan pasado 2 turnos, convertir el colaborador en zombie y agregarlo a la lista de zombies y eliminarlo de la lista de colaboradores.
* El colaborador verifica si se ha salvado, en caso de que sí, es agregado a al lista de colaboradores salvados y eliminado de la lista de colaboradores aún en el tablero.

### Subproblema VIII.
Para llevar acabo la bitácora del problema, hacemos uso de las listas del controlador correspondiente a los zombies, colaboradores salvados, infectados y los que se encuentran aún en el tablero tratando de escapar. Con ello únicamente imprimimos en consola cada vez que suceda un evento importante (aparición de un zombie, colaborador infectado, colaborador salvado). De igual forma se guarda en un archivo el número de iteración, el número de zombies, el número de colaboradores en la oficina y el número de colaboradores salvados.

### Subproblema IX.
Por último, en el controlador realizamos las conexiones entre los modelos y las vistas y la ejecución de la lógica del modelo con base a los distintos eventos que se den en el tablero. Para la rutina principal, primeramente se realiza la aparición de los zombies, posteriormente la de los colaboradores. Seguido de lo anterior, se ejecuta un bloque do while, donde se ejecutar, la rutina de la siguiente manera:
1. Primero es el turno de los zombies, se mueven de manera aleatoria y se hace un refresh de la vista para reflejar los movimientos. sólo se ejecutan 4 movimientos por turno para los zombies, se hace uso de un delay de un segundo, durmiendo el programa, para que se pueda apreciar en pantalla todo lo que ocurre.
2. Después es el turno de los colaboradores, una vez terminado su turno, se verifica si hay colaboradores que ya se transformaron, en caso de que sí, se eliminan los colaboradores transformados de la lista de colaboradores sanos. Luego, se verifica si hay colaboradores salvados y, de igual manera, en caso de que si se hayan salvado, se elminan de la lista de colaboradores en oficina. Nuevamente se hace un delay de 1 segundo y se refresca la vista para poder apreciar los cambios.
3. El contador aumenta y el programa terminará su ejecución una vez no queden colaboradores en la oficina.

Esto engloba la lógica de la solución del problema. Todo el código se encuentra documentado para mayor información. De igual manera se explica a mayor profundidad los métodos en la documentación.