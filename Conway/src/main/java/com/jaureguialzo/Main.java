package com.jaureguialzo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    /**
     * Muestra en tablero en pantalla, escribiendo un '.' si hay un 0 o un '#' si hay un 1
     */
    public static void visualizar(int[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] == 0) {
                    System.out.print(".");
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }

    /**
     * Pide al usuario cuantas veces quiere repetir el ciclo de la vida
     *
     * @return Entero con la respuesta del usuario o 5 si se produce un error de lectura
     */
    public static int numCiclos() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Número de ciclos: ");

        int numero = 5;
        try {
            numero = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numero;
    }

    /**
     * Calcula el número de células vecinas de una dada
     *
     * @param fila    Fila del tablero correspondiente a la célula de la que queremos hacer el cálculo
     * @param columna Columna del tablero correspondiente a la célula de la que queremos hacer el cálculo
     * @return La suma de todas las casillas que rodean a la correspondiente a [fila][columna]
     */
    public static int recuento(int[][] tablero, int fila, int columna) {
        int total = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (fila + i >= 0
                        && fila + i < tablero.length
                        && columna + j >= 0
                        && columna + j < tablero[0].length
                        && i != fila && j != columna) {
                    total += tablero[fila + i][columna + j];
                }
            }
        }

        return total;
    }

    /**
     * Recorre el tablero y, para cada posición, calcula el número de células vecinas llamando a la función recuento()
     *
     * @return Un nuevo array del mismo tamaño que el tablero con los recuentos
     */
    public static int[][] calcularVecinas(int[][] tablero) {

        int[][] temp = new int[tablero.length][tablero[0].length];

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = recuento(tablero, i, j);
            }
        }

        return temp;
    }

    /**
     * Actualiza el tablero comparando el valor de cada casilla con el del array de casillas vecinas
     * Reglas:
     * 1. Una célula viva con 2 o 3 células vecinas vivas sigue viva, en otro caso muere (por "soledad" o "superpoblación")
     * 2. Una célula muerta con exactamente 3 células vecinas vivas "nace" (es decir, al turno siguiente estará viva)
     */
    public static void actualizar(int[][] tablero) {

        int[][] vecinas = calcularVecinas(tablero);

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {

                if (tablero[i][j] == 1 && (vecinas[i][j] == 2 || vecinas[i][j] == 3)) {
                    tablero[i][j] = 1;
                } else if (tablero[i][j] == 0 && vecinas[i][j] == 3) {
                    tablero[i][j] = 1;
                } else {
                    tablero[i][j] = 0;
                }
            }
        }
    }

    /**
     * Programa principal
     */
    public static void main(String[] args) {

        // Posición inicial del tablero (6x25)
        int[][] tablero = new int[][]{
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1,},
                {0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
        };

        // Mostrar el tablero
        System.out.println("Estado inicial: ");
        visualizar(tablero);

        // Pedir al usuario el número de ciclos a repetir
        int ciclos = numCiclos();
        System.out.println();

        // Actualizar el tablero
        for (int i = 0; i < ciclos; i++) {
            System.out.format("Ciclo: %d%n", i + 1);
            actualizar(tablero);
            visualizar(tablero);
        }
    }
}
