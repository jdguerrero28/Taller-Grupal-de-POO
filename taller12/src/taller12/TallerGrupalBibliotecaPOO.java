package taller12;

import java.util.Scanner;

/**
 * Sistema de Gestion de Biblioteca
 *
 * @author José Pablo Ramírez
 *         Diego Torres
 *         Juan Diego Guerrero
 *         Gilson Salas
 */
public class TallerGrupalBibliotecaPOO {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("   SISTEMA DE GESTION - BIBLIOTECA");
        System.out.println("===========================================");
        System.out.print("Ingrese el nombre de la biblioteca: ");
        String nombreBiblioteca = sc.nextLine().trim();

        Biblioteca biblioteca = new Biblioteca(nombreBiblioteca, sc);

        int opcion;
        do {
            System.out.println("\n========== MENU PRINCIPAL ==========");
            System.out.println("1. Gestion de Libros");
            System.out.println("2. Gestion de Usuarios");
            System.out.println("3. Gestion de Prestamos");
            System.out.println("4. Reporte general");
            System.out.println("0. Salir");
            System.out.println("=====================================");
            System.out.print("Seleccione una opcion: ");

            opcion = leerInt(sc);

            switch (opcion) {
                case 1 -> biblioteca.menuLibros();
                case 2 -> biblioteca.menuUsuarios();
                case 3 -> biblioteca.menuPrestamos();
                case 4 -> {
                    biblioteca.generarReporte();
                    biblioteca.mostrarLibrosDisponibles();
                }
                case 0 -> System.out.println("Cerrando sistema. ¡Hasta pronto!");
                default -> System.out.println("Opcion invalida. Intente de nuevo.");
            }

        } while (opcion != 0);

        sc.close();
    }

    private static int leerInt(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida. Se esperaba un numero.");
            return -1;
        }
    }
}
