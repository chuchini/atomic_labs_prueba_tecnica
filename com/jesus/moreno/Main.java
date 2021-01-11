package com.jesus.moreno;

import com.jesus.moreno.controlador.Controlador;
import com.jesus.moreno.modelo.Colaborador;
import com.jesus.moreno.modelo.Zombie;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Código:
        // 1: Casilla Vacía
        // 0: Pared
        // 2: Ventana
        // 3: Zombie
        // 4: Colaborador
        // 5: Salida
        int[][] tablero =
                { { 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1 },
                  { 1, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                  { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                  { 1, 0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 1 },
                  { 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1 },
                  { 1, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 },
                  { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 4, 0, 0, 0, 1 },
                  { 1, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 },
                  { 1, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 1 },
                  { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 1 },
                  { 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
                  { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                  { 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 4, 0, 0, 1, 0, 0, 1 },
                  { 1, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 4, 0, 1 },
                  { 1, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1 },
                  { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 1, 0, 0, 0, 1, 0, 0, 5 },
                  { 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5 },
                  { 1, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 5 },
                  { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5 },
                  { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

        // Creamos una lista de colaboradores en blanco.
        ArrayList<Colaborador> colaboradores = new ArrayList<>();
        // Creamos una lista de zombies en blanco.
        ArrayList<Zombie> zombies = new ArrayList<>();
        // Creamos un nuevo controlador.
        Controlador controlador = new Controlador(tablero, colaboradores, zombies);

        // Ejecutamos la lógica del programa
        controlador.init();


    }
}
