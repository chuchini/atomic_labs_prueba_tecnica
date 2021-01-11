package com.jesus.moreno.controlador;

import com.jesus.moreno.modelo.Colaborador;
import com.jesus.moreno.modelo.Zombie;
import com.jesus.moreno.vista.Vista;

import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Clase encargada de realizar la conexión entre los modelos y la vista,
 * así como de ejecutar la lógica de los modelos.
 */
public class Controlador {

    // Atributos
    private Vista vista;
    private ArrayList<Colaborador> colaboradores;
    private ArrayList<Zombie> zombies;
    private int[][] tablero;
    private ArrayList<Colaborador> colaboradoresTransformados;
    private ArrayList<Colaborador> colaboradoresSalvados;


    /**
     * Constructor de la clase Controlador.
     * @param tablero La configuración del tablero a utilizar.
     * @param colaboradores Un ArrayList que contiene a los colaboradores.
     * @param zombies Un ArrayList que contiene a los zombies.
     */
    public Controlador(int[][] tablero,
                       ArrayList<Colaborador> colaboradores,
                       ArrayList<Zombie> zombies) {

        this.vista = new Vista(tablero);
        this.colaboradores = colaboradores;
        this.zombies = zombies;
        this.tablero = tablero;
        this.colaboradoresTransformados = new ArrayList<Colaborador>();
        this.colaboradoresSalvados = new ArrayList<Colaborador>();
    }

    /**
     * Ḿétodo encargado de inicializar toda la lógica del programa.
     * Genera a los zombies y colaboradores según la configuración descrita en
     * la tabla que recibe el constructor del controlador. Posteriormente
     * ejecuta la rutina (turno de zombies, turno de colaboradores).
     * @throws InterruptedException
     */
    public void init() throws InterruptedException {
        spawnZombies();
        spawnColaboradores();
        int contador = 0;

        do {
            if (contador == 0) {
                borrarArchivo();
            }
            bitacora(contador);

            for (int i = 0; i < 4; i++) {
                turnoZombies();
                TimeUnit.SECONDS.sleep(1);
                this.vista.repain(this.tablero);
            }

            for (int i = 0; i < 2; i++) {
                turnoColaboradores();

                if (colaboradoresTransformados.size() > 0)
                    colaboradores.removeAll(colaboradoresTransformados);

                if (colaboradoresSalvados.size() > 0)
                    colaboradores.removeAll(colaboradoresSalvados);

                TimeUnit.SECONDS.sleep(1);
                this.vista.repain(this.tablero);
            }
            contador++;
        } while (colaboradores.size() > 0);

        JOptionPane.showMessageDialog(null, "La ejecución del programa ha finalizado.");
    }

    /**
     * Método encargado de dibujar los zombies en pantalla, así como agregarlos
     * al ArrayList correspondiente. La posición de los zombies es aleatoria.
     */
    public void spawnZombies() {
        // El zombie entra por una ventana aleatoria, sabemos que las ventanas
        // están ubicadas en el renglón 0, por lo cual únicamente creamos un
        // número aleatorio correspondiente a la columna, restringiendo este
        // valor para que esté en el intervalo correcto.
        int zombie_1 = (int) (Math.random()*(6  - 3  + 1) + 3);
        int zombie_2 = (int) (Math.random()*(16 - 13 + 1) + 13);

        // Creamos dos objetos de la clase Zombie, asignandole un valor
        // a sus atributos, los cuales hacen referencia a sus coordenadas.
        Zombie zombie = new Zombie(1, zombie_1);
        zombies.add(zombie);
        zombie = new Zombie(1, zombie_2);
        zombies.add(zombie);

        // Indicamos en el tablero que en las casillas correspondientes ha
        // aparecido un zombie.
        this.tablero[1][zombie_1] = 3;
        System.out.println("Zombie llegó por la ventana de la casilla [1," + zombie_1 + "]");
        this.tablero[1][zombie_2] = 3;
        System.out.println("Zombie llegó por la ventana de la casilla [1," + zombie_2 + "]");
        this.vista.repain(this.tablero);
    }

    /**
     * Método encargado de dibujar los colaboradores en pantalla, así como
     * agregarlos al ArrayList correspondiente. Esto se hace con base a la
     * configuración base del problema.
     */
    public void spawnColaboradores() {
        colaboradores.add(new Colaborador(1, 9));
        colaboradores.add(new Colaborador(3, 3));
        colaboradores.add(new Colaborador(3, 6));
        colaboradores.add(new Colaborador(3,11));
        colaboradores.add(new Colaborador(3,15));
        colaboradores.add(new Colaborador(5, 4));
        colaboradores.add(new Colaborador(6,15));
        colaboradores.add(new Colaborador(7, 2));
        colaboradores.add(new Colaborador(7, 7));
        colaboradores.add(new Colaborador(8, 3));
        colaboradores.add(new Colaborador(8,17));
        colaboradores.add(new Colaborador(9,11));
        colaboradores.add(new Colaborador(12,13));
        colaboradores.add(new Colaborador(13, 3));
        colaboradores.add(new Colaborador(13,17));
        colaboradores.add(new Colaborador(14, 6));
        colaboradores.add(new Colaborador(15,10));
        colaboradores.add(new Colaborador(17, 3));
        colaboradores.add(new Colaborador(17, 7));
        colaboradores.add(new Colaborador(17,13));
    }

    /**
     * Método encargado de ejecutar la lógica del turno de los zombies.
     * Se divide en 2 pasos:
     * 1. El zombie verifica si existe un colaborador adyacente. En caso de
     *    de que sí exista, el zombie lo infecta.
     * 2. El zombie se mueve de manera aleatoria en el mapa.
     */
    public void turnoZombies() {
        for (Zombie zombie:
             zombies) {
            buscarColaboradorAdyacente(zombie);
            this.tablero[zombie.getRow()][zombie.getColumn()] = 0;
            zombie.moverse(generarMovimientoAleatorio(zombie));
            this.tablero[zombie.getRow()][zombie.getColumn()] = 3;
        }
    }

    /**
     * Método encargado de ejecutar la lógica del turno de los colaboradores.
     * Se divide en 3 pasos.
     * 1. El colaborador revisa si está infectado, en caso afirmativo, se
     *    verifica si ya han pasado dos turnos para completar la infección.
     * 2. En caso contrario, el colaborador avanza a una casilla que lo acerque
     *    a la meta, dependiendo del cuadrante en el cual se encuentre.
     * 3. El colaborador verifica si ha llegado a la salida, en caso de que sí,
     *    el colaborador desaparece del mapa.
     * @throws InterruptedException
     */
    public void turnoColaboradores() throws InterruptedException {
        for (Colaborador colab :
                colaboradores) {
            this.tablero[colab.getRow()][colab.getColumn()] = 0;
            generarMovimientoColaborador(colab);
            if (colab.isInfectado()) {
                this.tablero[colab.getRow()][colab.getColumn()] = 6;
                completarInfeccion(colab);

            } else {
                this.tablero[colab.getRow()][colab.getColumn()] = 4;
            }

            if (colab.isSalvado()) {
                this.tablero[colab.getRow()][colab.getColumn()] = 5;
            }
        }
    }

    /**
     * Método encargado de generar un movimiento aleatorio para el zombie,
     * dado que el zombie puede hacer un movimiento a alguna de las 8 casillas
     * que lo rodean, se genera un número random que corresponda a una de estas
     * 8 posibilidades, posteriormente se verifica que el movimiento sea valido
     * en caso de que el movimiento no sea válido, se seguira ejecutando el
     * código hasta que el resultado deje de ser invalido.
     * @param zombie El zombie que realizará el movimiento.
     * @return El entero correspondiente a uno de los 8 movimientos posibles.
     */
    public int generarMovimientoAleatorio(Zombie zombie) {
        int opcion;

        do {
            opcion = (int) (Math.random() * (9 - 1) + 1);
        } while(!verificarMovimientoValido(opcion, zombie));

        return opcion;
    }

    /**
     * Método encargado de verificar que el movimiento ha realizar sea válido.
     * Se verifica que los movimientos no se hagan fuera del mapa o que permita
     * que el zombie atraviese alguna pared. También se verifica que no pueda
     * haber 2 zombies en la misma casilla o, de igual manera, que un zombie se
     * mueva a una casilla ya ocupada por algún colaborador.
     * @param opcion Hace referencia a alguna de las 8 posibilidades de movimiento.
     * @param zombie El zombie que quiere realizar el movimiento.
     * @return True, en caso de que el movimiento sea válido. False en caso contrario.
     */
    public boolean verificarMovimientoValido(int opcion, Zombie zombie) {
        int row_actual = zombie.getRow();
        int column_actual = zombie.getColumn();

        if ((opcion == 1 && row_actual <= 1)) {
            return false;
        } else if ((opcion == 2 && row_actual >= 18)) {
            return false;
        } else if ((opcion == 3 && column_actual <= 1)) {
            return false;
        } else if ((opcion == 4 && column_actual >= 18)) {
            return false;
        } else if ((opcion == 5 && (column_actual <= 1 || row_actual <= 1))) {
            return false;
        } else if ((opcion == 6 && (column_actual >= 18 || row_actual <= 1))) {
            return false;
        } else if ((opcion == 7 && (column_actual <= 1 || row_actual >= 18))) {
            return false;
        } else if ((opcion == 8 && (column_actual >= 18 || row_actual >= 18))) {
            return false;
        } else {
            if (opcion == 1 && this.tablero[row_actual - 1][column_actual] != 0) {
                return false;
            } else if (opcion == 2 && this.tablero[row_actual + 1][column_actual] != 0) {
                return false;
            } else if (opcion == 3 && this.tablero[row_actual][column_actual - 1] != 0) {
                return false;
            } else if (opcion == 4 && this.tablero[row_actual][column_actual + 1] != 0) {
                return false;
            } else if (opcion == 5 && this.tablero[row_actual - 1][column_actual - 1] != 0) {
                return false;
            } else if (opcion == 6 && this.tablero[row_actual - 1][column_actual + 1] != 0) {
                return false;
            } else if (opcion == 7 && this.tablero[row_actual + 1][column_actual - 1] != 0) {
                return false;
            } else if (opcion == 8 && this.tablero[row_actual + 1][column_actual + 1] != 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Método que sirve para que el zombie verifique si hay algún olaborador
     * que se encuentre en alguna casilla adyacente a el mismo. En caso de que
     * se encuentre algún colaborador, el zombie lo infectará.
     * @param zombie El zombie que buscará en sus casillas adyacentes.
     */
    public void buscarColaboradorAdyacente(Zombie zombie) {
        int row_actual = zombie.getRow();
        int column_actual = zombie.getColumn();
        int indice;

        if (tablero[row_actual - 1][column_actual] == 4){
            indice = buscarColaborador(row_actual - 1, column_actual);
            colaboradores.get(indice).setInfectado(true);
            imprimirInfección(indice, colaboradores.get(indice));
            tablero[colaboradores.get(indice).getRow()][colaboradores.get(indice).getColumn()] = 6;

        } else if (tablero[row_actual + 1][column_actual] == 4) {
            indice = buscarColaborador(row_actual + 1, column_actual);
            colaboradores.get(indice).setInfectado(true);
            imprimirInfección(indice, colaboradores.get(indice));
            tablero[colaboradores.get(indice).getRow()][colaboradores.get(indice).getColumn()] = 6;

        } else if (tablero[row_actual][column_actual - 1] == 4) {
            indice = buscarColaborador(row_actual, column_actual - 1);
            colaboradores.get(indice).setInfectado(true);
            imprimirInfección(indice, colaboradores.get(indice));
            tablero[colaboradores.get(indice).getRow()][colaboradores.get(indice).getColumn()] = 6;

        } else if (tablero[row_actual][column_actual + 1] == 4) {
            indice = buscarColaborador(row_actual, column_actual + 1);
            colaboradores.get(indice).setInfectado(true);
            imprimirInfección(indice, colaboradores.get(indice));
            tablero[colaboradores.get(indice).getRow()][colaboradores.get(indice).getColumn()] = 6;

        } else if (tablero[row_actual - 1][column_actual - 1] == 4) {
            indice = buscarColaborador(row_actual - 1, column_actual - 1);
            colaboradores.get(indice).setInfectado(true);
            imprimirInfección(indice, colaboradores.get(indice));
            tablero[colaboradores.get(indice).getRow()][colaboradores.get(indice).getColumn()] = 6;

        } else if (tablero[row_actual - 1][column_actual + 1] == 4) {
            indice = buscarColaborador(row_actual - 1, column_actual + 1);
            colaboradores.get(indice).setInfectado(true);
            imprimirInfección(indice, colaboradores.get(indice));
            tablero[colaboradores.get(indice).getRow()][colaboradores.get(indice).getColumn()] = 6;

        } else if (tablero[row_actual + 1][column_actual - 1] == 4) {
            indice = buscarColaborador(row_actual + 1, column_actual - 1);
            colaboradores.get(indice).setInfectado(true);
            imprimirInfección(indice, colaboradores.get(indice));
            tablero[colaboradores.get(indice).getRow()][colaboradores.get(indice).getColumn()] = 6;

        } else if (tablero[row_actual + 1][column_actual + 1] == 4) {
            indice = buscarColaborador(row_actual + 1, column_actual + 1);
            colaboradores.get(indice).setInfectado(true);
            imprimirInfección(indice, colaboradores.get(indice));
            tablero[colaboradores.get(indice).getRow()][colaboradores.get(indice).getColumn()] = 6;

        }
    }

    /**
     * Método encargado de imprimir en consola la casilla donde el colaborador
     * ha sido infectado por un zombie.
     * @param indice El indice del colaborador en el ArrayList
     * @param colab El colaborador infectado
     */
    public void imprimirInfección(int indice, Colaborador colab) {
        System.out.println("Humano " + indice +
                " infectado en la casilla [" +
                colab.getRow() + "," +
                colab.getColumn() + "]");
    }

    /**
     * Método encargado de buscar a un colaborador en la ArrayList de
     * colaboradores. La búsqueda se hace a partir de coordenadas.
     * @param row La fila a buscar.
     * @param column La columna a buscar.
     * @return El índice en la ArrayList correspondiente al colaborador buscado.
     */
    public int buscarColaborador(int row, int column) {
        for (int i = 0; i < colaboradores.size(); i++) {
            if (colaboradores.get(i).getRow() == row &&
                    colaboradores.get(i).getColumn() == column) {
                return i;
            }
        }
        return  -1;
    }


    /**
     * Méotodo encargado de completar el proceso de infección del colaborador.
     * Primero verifica que hayan pasado 2 turnos antes de completar la
     * infección. Si no han pasado 2 turnos, se aumenta el contador de turnos.
     * Cuando hacemos referencias a turnos, nos referimos exclusivamente a
     * turnos de colaboradores. En otras palabras, el contador no se verá
     * afectado en el turno de los zombies.
     *
     * @param colab El colaborador en proceso de infección.
     */
    public void completarInfeccion(Colaborador colab) {
        int contador = colab.getContadorParaTransformarse();
        if (contador > 2) {
            tablero[colab.getRow()][colab.getColumn()] = 3;
            zombies.add(new Zombie(colab.getRow(), colab.getColumn()));
            colaboradoresTransformados.add(colab);
        } else {
            colab.setContadorParaTransformarse(contador + 1);
        }
    }

    /**
     * Método encargado de generar los movimientos de un colaborador.
     * Primeramente, se verifica en qué cuadrante se encuentra el colaborador o,
     * en caso de que el colaborador se encuentre en el medio, se desplazará
     * hacia abajo. En caso de que se encuentre alineado a la meta, realizará
     * movimientos que lo dirigan hacia la meta de manera inmediata.
     * @param colab
     */
    public void generarMovimientoColaborador(Colaborador colab) {
        switch (verificarCuadrante(colab)){
            case 1:
                movimientoPrimerCuadrante(colab);
                break;
            case 2:
                movimientoSegundoCuadrante(colab);
                break;
            case 3:
                movimientoTercerCuadrante(colab);
                break;
            case 4:
                movimientoCuartoCuadrante(colab);
                break;
            case 5:
                movimientoAbajo(colab);
                break;
            case 6:
                movimientoMeta(colab);
                break;
        }
    }

    /**
     * Verifica que el colaborador se encuentre en algún cuadrante o, en su caso,
     * de que se encuentre en medio para avanzar hacia abajo o se encuentre
     * alineado a la meta y avance directamente hacia ella.
     * @param colab El colaborador a verificar su cuadrante.
     * @return 1 sí el colaborador se encuentra en el primer cuadrante.
     *         2 Sí el colaborador se encuentra en el segundo cuadrante.
     *         3 Sí el colaborador se encuentra en el tercer cuadrante.
     *         4 Sí el colaborador se encuentra en el cuarto cuadrante.
     *         5 Sí el colaborador se encuentra en el medio.
     *         6 Sí el colaborador se encuentra alineado a la meta.
     */
    public int verificarCuadrante(Colaborador colab) {
        int row_actual = colab.getRow();
        int column_actual = colab.getColumn();

        if (row_actual <= 10 && column_actual < 9) {
            return 1;
        } else if (row_actual <= 10 && column_actual > 9) {
            return 2;
        } else if (row_actual >= 11 && column_actual < 9) {
            return 3;
        } else if (row_actual >= 11 && column_actual > 9) {
            return 4;
        } else if (column_actual == 9){
            return 5;
        } else {
            return 6;
        }
    }

    /**
     * Método encargado de realizar movimientos correspondientes a la lógica
     * del primer cuadrante. Para encontrarse en el primer cuadrante debe de
     * encontrarse entre las coordenadas [0,0] y [10,9]
     * @param colab El colaborador que realizará el movimiento.
     */
    public void movimientoPrimerCuadrante(Colaborador colab) {
        int row_actual = colab.getRow();
        int column_actual = colab.getColumn();

        if (this.tablero[row_actual + 1][column_actual + 1] == 0) {
            colab.moverse(8);
        } else if (this.tablero[row_actual][column_actual + 1] == 0) {
            colab.moverse(4);
        }
    }

    /**
     * Método encargado de realizar movimientos correspondientes a la lógica
     * del segundo cuadrante. Para encontrarse en el segundo cuadrante debe de
     * encontrarse entre las coordenadas [0,10] y [10,19]
     * @param colab El colaborador que realizará el movimiento.
     */
    public void movimientoSegundoCuadrante(Colaborador colab) {
        int row_actual = colab.getRow();
        int column_actual = colab.getColumn();

        if (this.tablero[row_actual + 1][column_actual - 1] == 0) {
            colab.moverse(7);
        } else if (this.tablero[row_actual][column_actual - 1] == 0) {
            colab.moverse(3);
        } else if (this.tablero[row_actual + 1][column_actual] == 0) {
            colab.moverse(2);
        }
    }

    /**
     * Método encargado de realizar movimientos correspondientes a la lógica
     * del tercer cuadrante. Para encontrarse en el tercer cuadrante debe de
     * encontrarse entre las coordenadas [10,0] y [19,9]
     * @param colab El colaborador que realizará el movimiento.
     */
    public void movimientoTercerCuadrante(Colaborador colab) {
        int row_actual = colab.getRow();
        int column_actual = colab.getColumn();

        if (this.tablero[row_actual + 1][column_actual + 1] == 0) {
            colab.moverse(8);
        } else if (this.tablero[row_actual][column_actual + 1] == 0) {
            colab.moverse(4);
        } else if (this.tablero[row_actual + 1][column_actual] == 0) {
            colab.moverse(2);
        }
    }

    /**
     * Método encargado de realizar movimientos correspondientes a la lógica
     * del cuarto cuadrante. Para encontrarse en el cuarto cuadrante debe de
     * encontrarse entre las coordenadas [10,10] y [19,19]
     * @param colab El colaborador que realizará el movimiento.
     */
    public void movimientoCuartoCuadrante(Colaborador colab) {
        int row_actual = colab.getRow();
        int column_actual = colab.getColumn();

        if (alineadoConMeta(colab)) {
            movimientoMeta(colab);
        } else if (this.tablero[row_actual + 1][column_actual + 1] == 0) {
            colab.moverse(8);
        } else if (this.tablero[row_actual][column_actual + 1] == 0) {
            colab.moverse(4);
        } else if (this.tablero[row_actual + 1][column_actual] == 0) {
            colab.moverse(2);
        }
    }

    /**
     * Método encargado de realizar un movimiento hacia abajo, hasta que el
     * colaborador se encuentre alineado a alguna casilla de salida.
     * El colaborador ejecutará este tipo de movimiento sólo si se encuentra
     * en la casilla 9 de la columnas, es decir, [x,9], donde x ∈ {0, ..., 19}
     * @param colab El colaborador que realizará el movimiento
     */
    public void movimientoAbajo(Colaborador colab) {
        int row_actual = colab.getRow();
        int column_actual = colab.getColumn();

        if (alineadoConMeta(colab)) {
            movimientoMeta(colab);
        } else if (this.tablero[row_actual + 1][column_actual] == 0) {
            colab.moverse(2);
        }
    }

    /**
     * Método encargado de realizar un movimiento directo hacia la meta,
     * esto se realizará en caso de que el colaborador se encuentre alineado
     * a la meta, por preferencia, el colaborador siempre tratará de ir
     * hasta el final de las filas (fila 19), moviendose en diagonal, en caso
     * de que no sea posible, continuará su movimiento en dirección a la meta,
     * es decir, desplazándose havia la derecha.
     * @param colab El colaborador que realizará el movimiento.
     */
    public void movimientoMeta(Colaborador colab) {
        int row_actual = colab.getRow();
        int column_actual = colab.getColumn();

        if (this.tablero[row_actual + 1][column_actual + 1] == 0) {
            colab.moverse(8);
        } else if (this.tablero[row_actual][column_actual + 1] == 0) {
            colab.moverse(4);
        } else if (this.tablero[row_actual][column_actual + 1] == 5) {
            colab.moverse(4);
            colab.setSalvado(true);
            System.out.println("Humano " + buscarColaborador(colab.getRow(), colab.getColumn()) +
                               " salvado en la casilla [" + colab.getRow() + "," + colab.getColumn() + "]");
            colaboradoresSalvados.add(colab);
        }
    }

    /**
     * Método encargado de verificar si el colaborador dado se encuentra
     * alineado con la meta.
     * @param colab El colaborador que desea verificar si se encuentra alineado.
     * @return True, en caso de encontrarse alineado con la meta. False, en caso contrario.
     */
    public boolean alineadoConMeta(Colaborador colab) {
        int row_actual = colab.getRow();

        if (row_actual == 15 || row_actual == 16 || row_actual == 17 || row_actual == 18) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método auxiliar que imprime en pantalla el resultado de las iteraciones.
     * Despliega en consola información relacionada con la iteración actual.
     * Incluyendo el número de zombies que existen,
     * el número de colaboradores que existen.
     * el número de colaboradores que se han salvado.
     * @param iteracion
     */
    private void bitacora(int iteracion) {
        try (
            FileWriter writer = new FileWriter("bitacora.txt", true);
            BufferedWriter buffer = new BufferedWriter(writer);
            PrintWriter contenido = new PrintWriter(buffer)) {
            contenido.println(iteracion + " | " +
                              zombies.size() + " | " +
                              colaboradores.size() + " | " +
                              colaboradoresSalvados.size());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error con la lectura/escritura del archivo.");
            e.printStackTrace();
        }
    }

    /**
     * Método utilizado para borrar el archivo de bitacoras existente.
     * Para su uso correcto se tiene que verificar que es la primera
     * iteración.
     */
    private void borrarArchivo() {
        File archivo1 = new File("./bitacora.txt");
        archivo1.delete();
    }

}
