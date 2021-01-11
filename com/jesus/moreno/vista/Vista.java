package com.jesus.moreno.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.*;

public class Vista extends javax.swing.JFrame {

    private JFrame mainFrame;
    private HashMap<String, JLabel> labels = new HashMap<String, JLabel>();
    int con = 0;
    public Vista(int[][] tablero) {
        initComponents(tablero);
    }

    public void initComponents(int[][] tablero) {
        this.mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(tablero.length, tablero.length));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setTitle("Atomic labs - Jesús Fernando Moreno Ruíz");

        for (int row = 0; row < tablero.length; row++) {
            for (int col = 0; col < tablero.length; col++) {
                JLabel label = makeLabel(tablero[row][col], row, col);
                mainFrame.add(label);
            }
        }
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    // Código:
    // 1: Casilla Vacía
    // 0: Pared
    // 2: Ventana
    // 3: Zombie
    // 4: Colaborador
    // 5: Salida

    /**
     * Método encargado de crear un nuevo label correspondiente a una casilla
     * @param c la casilla
     * @param row la fila
     * @param col la columna
     * @return
     */
    private JLabel makeLabel(int c, int row, int col) {
        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(25, 25));
        label.setName(row + "" + col);
        labels.put(row + "." + col, label);
        switch(c) {
            case 0:
                label.setBackground(Color.WHITE);
                break;
            case 2:
                label.setBackground(Color.CYAN);
                break;
            case 3:
                label.setBackground(Color.RED);
                break;
            case 4:
                label.setBackground(Color.BLUE);
                break;
            case 5:
                label.setBackground(Color.GREEN);
                break;
            default:
                label.setBackground(Color.BLACK);
                break;
        }
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        return label;
    }

    /**
     * Método encargado de refrescar el tablero, es decir, de pintarlo de nuevo
     * con los cambios que ocurran
     * @param tablero el tablero actualizado
     */
    public void repain(int[][] tablero) {
        for (int row = 0; row < tablero.length; row++) {
            for (int col = 0; col < tablero.length; col++) {
                JLabel label = labels.get(row + "." + col);
                switch(tablero[row][col]) {
                    case 0:
                        label.setBackground(Color.WHITE);
                        break;
                    case 2:
                        label.setBackground(Color.CYAN);
                        break;
                    case 3:
                        label.setBackground(Color.RED);
                        break;
                    case 4:
                        label.setBackground(Color.BLUE);
                        break;
                    case 5:
                        label.setBackground(Color.GREEN);
                        break;
                    case 6:
                        label.setBackground(Color.YELLOW);
                        break;
                    default:
                        label.setBackground(Color.BLACK);
                        break;
                }
            }
        }
        this.mainFrame.revalidate();
        this.mainFrame.repaint();

    }
}
