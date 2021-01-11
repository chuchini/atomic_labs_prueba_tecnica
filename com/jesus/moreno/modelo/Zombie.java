package com.jesus.moreno.modelo;

/**
 * Clase encargada de modelar a los objetos de tipo Zombie
 */
public class Zombie {

    // Atributos
    private int row;
    private int column;

    /**
     * Constructor de la clase Zombie
     * @param row La fila donde se encuentra el zombie.
     * @param column La columna donde se encuentra el zombie.
     */
    public Zombie(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Método encargado de retornar la fila donde se encuentra el zombie.
     * @return La fila actual del zombie.
     */
    public int getRow() {
        return row;
    }

    /**
     * Método usado para modificar la fila actual del zombie.
     * @param row La nueva fila donde se encontrará el zombie.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Método encargado de retornar la columna donde se encuentra el zombie.
     * @return La columna actual del zombie.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Método usado para modificar la columna actual del zombie.
     * @param column La nueva columna donde se encontrará el zombie.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Método encargado de realizar un movimiento con base a alguna de las 8
     * posibilidades que existen, correspondientes a cada una de las casillas
     * adyacentes del zombie.
     */
    public void moverse(int opcion) {
        switch (opcion){
            // Moverse hacia arriba
            case 1:
                this.row -= 1;
                break;
            // Moverse hacia abajo
            case 2:
                this.row += 1;
                break;
            // Moverse hacia la izquierda
            case 3:
                this.column -= 1;
                break;
            // Moverse hacia la derecha
            case 4:
                this.column += 1;
                break;
            // Moverse en diagonal a la esquina superior izquierda
            case 5:
                this.column -= 1;
                this.row -= 1;
                break;
            // Moverse en diagonal a la esquina superior derecha
            case 6:
                this.column += 1;
                this.row -= 1;
                break;
            // Moverse en diagonal a la esquina inferior izquierda
            case 7:
                this.column -= 1;
                this.row += 1;
                break;
            // Moverse en diagonal a la esquina inferior derecha
            case 8:
                this.column += 1;
                this.row += 1;
                break;
        }
    }
}
