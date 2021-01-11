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