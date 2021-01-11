package com.jesus.moreno.modelo;

/**
 * Clase encargada de modelar al objeto Colaborador
 */
public class Colaborador {

    // Atributos
    private int row;
    private int column;
    private boolean infectado;
    private boolean salvado;
    private int contadorParaTransformarse;

    /**
     * Constructor del objeto Colaborador
     * @param row La fila donde se encuentra el colaborador.
     * @param column La columna donde se encuentra el colaborador.
     */
    public Colaborador(int row, int column) {
        this.row = row;
        this.column = column;
        this.infectado = false;
        this.salvado = false;
        this.contadorParaTransformarse = 0;
    }

    /**
     * Método encargado de retornar la fila donde se encuentra el colaborador.
     * @return La fila actual del colaborador.
     */
    public int getRow() {
        return row;
    }

    /**
     * Método usado para modificar la fila actual del colaborador.
     * @param row La nueva fila donde se encontrará el colaborador.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Método encargado de retornar la columna donde se encuentra el colaborador.
     * @return La columna actual del colaborador.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Método usado para modificar la columna actual del colaborador.
     * @param column La nueva columna donde se encontrará el colaborador.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Método utilizado para saber si el colaborador se encuentra infectado.
     * @return True, en caso de que sí esté infectado. False, en caso contrario.
     */
    public boolean isInfectado() {
        return infectado;
    }

    /**
     * Método utilizado para cambiar el estado de infectado del colaborador.
     * @param infectado El nuevo estado de infección del colaborador.
     */
    public void setInfectado(boolean infectado) {
        this.infectado = infectado;
    }

    /**
     * Método encargado de retornar el contador de trasnformación del
     * colaborador.
     * @return El contador de transformación.
     */
    public int getContadorParaTransformarse() {
        return contadorParaTransformarse;
    }

    /**
     * Método utilizado para modificar el valor del contador de
     * transformación del colaborador.
     * @param contadorParaTransformarse El nuevo valor del contador.
     */
    public void setContadorParaTransformarse(int contadorParaTransformarse) {
        this.contadorParaTransformarse = contadorParaTransformarse;
    }

    /**
     * Método utilizado para saber si el colaborador se ha salvado.
     * @return True, si sí se ha salvado. False, en caso contrario.
     */
    public boolean isSalvado() {
        return salvado;
    }

    /**
     * Método utilizado para cambiar el valor de salvación del
     * colaborador.
     * @param salvado El nuevo valor de salvación.
     */
    public void setSalvado(boolean salvado) {
        this.salvado = salvado;
    }

    /**
     * Método encargado de realizar un movimiento con base a alguna de las 8
     * posibilidades que existen, correspondientes a cada una de las casillas
     * adyacentes del colaborador.
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
