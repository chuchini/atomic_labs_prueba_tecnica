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