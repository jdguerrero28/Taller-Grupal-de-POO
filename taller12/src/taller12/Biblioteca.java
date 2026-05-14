package taller12;

import java.util.Scanner;

class Biblioteca {

    private String nombre;

    // Colecciones usando arreglos de tamaño fijo
    private static final int MAX_LIBROS   = 50;
    private static final int MAX_USUARIOS = 20;
    private static final int MAX_PRESTAMOS = 50;

    private Libro[]    libros    = new Libro[MAX_LIBROS];
    private Usuario[]  usuarios  = new Usuario[MAX_USUARIOS];
    private Prestamo[] prestamos = new Prestamo[MAX_PRESTAMOS];

    private int totalLibros    = 0;
    private int totalUsuarios  = 0;
    private int totalPrestamos = 0;

    private Scanner sc;

    // ----------------------------------------------------------------
    public Biblioteca(String nombre, Scanner sc) {
        this.nombre = nombre;
        this.sc     = sc;
    }

    // ================================================================
    //  LIBROS
    // ================================================================

    public void menuLibros() {
        int opcion;
        do {
            System.out.println("\n--- GESTION DE LIBROS ---");
            System.out.println("1. Agregar libro");
            System.out.println("2. Listar libros disponibles");
            System.out.println("3. Buscar por genero");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = leerInt();
            switch (opcion) {
                case 1 -> agregarLibro();
                case 2 -> mostrarLibrosDisponibles();
                case 3 -> {
                    System.out.print("Ingrese genero a buscar: ");
                    buscarPorGenero(sc.nextLine().trim());
                }
                case 0 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void agregarLibro() {
        if (totalLibros >= MAX_LIBROS) {
            System.out.println("Capacidad maxima de libros alcanzada.");
            return;
        }
        System.out.print("Titulo : "); String titulo = sc.nextLine().trim();
        System.out.print("Autor  : "); String autor  = sc.nextLine().trim();
        System.out.print("Genero : "); String genero = sc.nextLine().trim();

        libros[totalLibros++] = new Libro(titulo, autor, genero);
        System.out.println("Libro \"" + titulo + "\" registrado correctamente.");
    }

    public void mostrarLibrosDisponibles() {
        System.out.println("\n--- LIBROS DISPONIBLES ---");
        boolean hayAlguno = false;
        for (int i = 0; i < totalLibros; i++) {
            if (libros[i].isDisponible()) {
                System.out.println("[" + i + "]");
                libros[i].mostrarInfo();
                hayAlguno = true;
            }
        }
        if (!hayAlguno) System.out.println("No hay libros disponibles.");
    }

    public void buscarPorGenero(String genero) {
        System.out.println("\n--- LIBROS DEL GENERO: " + genero.toUpperCase() + " ---");
        boolean encontrado = false;
        for (int i = 0; i < totalLibros; i++) {
            if (libros[i].getGenero().equalsIgnoreCase(genero)) {
                libros[i].mostrarInfo();
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("No se encontraron libros de ese genero.");
    }

    // ================================================================
    //  USUARIOS
    // ================================================================

    public void menuUsuarios() {
        int opcion;
        do {
            System.out.println("\n--- GESTION DE USUARIOS ---");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Ver reporte de usuarios");
            System.out.println("3. Pagar multa de un usuario");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = leerInt();
            switch (opcion) {
                case 1 -> registrarUsuario();
                case 2 -> generarReporte();
                case 3 -> pagarMultaUsuario();
                case 0 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void registrarUsuario() {
        if (totalUsuarios >= MAX_USUARIOS) {
            System.out.println("Capacidad maxima de usuarios alcanzada.");
            return;
        }
        System.out.print("Nombre : "); String nombre = sc.nextLine().trim();
        System.out.print("Cedula : "); String cedula = sc.nextLine().trim();

        // Verificar cedula duplicada
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i].getCedula().equals(cedula)) {
                System.out.println("ERROR: ya existe un usuario con esa cedula.");
                return;
            }
        }
        usuarios[totalUsuarios++] = new Usuario(nombre, cedula);
        System.out.println("Usuario \"" + nombre + "\" registrado correctamente.");
    }

    private void pagarMultaUsuario() {
        Usuario u = seleccionarUsuario();
        if (u == null) return;
        if (u.getMultaPendiente() == 0) {
            System.out.println("El usuario no tiene multas pendientes.");
        } else {
            System.out.printf("Multa actual: $%.2f%n", u.getMultaPendiente());
            u.pagarMulta();
        }
    }

    public void generarReporte() {
        System.out.println("\n===== REPORTE DE USUARIOS =====");
        for (int i = 0; i < totalUsuarios; i++) {
            Usuario u = usuarios[i];
            System.out.println("Usuario        : " + u.getNombre());
            System.out.println("Cedula         : " + u.getCedula());
            System.out.println("Libros prestados: " + u.getLibrosPrestados());
            System.out.printf ("Multa pendiente : $%.2f%n", u.getMultaPendiente());
            System.out.println("--------------------------------");
        }
    }

    // ================================================================
    //  PRESTAMOS
    // ================================================================

    public void menuPrestamos() {
        int opcion;
        do {
            System.out.println("\n--- GESTION DE PRESTAMOS ---");
            System.out.println("1. Realizar prestamo");
            System.out.println("2. Devolver libro");
            System.out.println("3. Ver prestamos activos");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = leerInt();
            switch (opcion) {
                case 1 -> realizarPrestamo();
                case 2 -> devolverLibro();
                case 3 -> verPrestamosActivos();
                case 0 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void realizarPrestamo() {
        if (totalUsuarios == 0 || totalLibros == 0) {
            System.out.println("Debe tener usuarios y libros registrados.");
            return;
        }

        Usuario u = seleccionarUsuario();
        if (u == null) return;

        // Validaciones del usuario
        if (u.getLibrosPrestados() >= 3) {
            System.out.println("ERROR: El usuario ya tiene 3 libros prestados (limite maximo).");
            return;
        }
        if (u.getMultaPendiente() > 0) {
            System.out.printf("ERROR: El usuario tiene una multa pendiente de $%.2f. Debe pagarla primero.%n",
                    u.getMultaPendiente());
            return;
        }

        mostrarLibrosDisponibles();
        System.out.print("Ingrese el numero del libro a prestar: ");
        int idx = leerInt();
        if (idx < 0 || idx >= totalLibros) {
            System.out.println("Indice invalido.");
            return;
        }
        Libro libro = libros[idx];
        if (!libro.isDisponible()) {
            System.out.println("ERROR: El libro \"" + libro.getTitulo() + "\" ya esta prestado.");
            return;
        }

        System.out.print("Dias de prestamo: ");
        int dias = leerInt();
        if (dias <= 0) { System.out.println("Numero de dias invalido."); return; }

        if (totalPrestamos >= MAX_PRESTAMOS) {
            System.out.println("Capacidad maxima de prestamos alcanzada.");
            return;
        }

        prestamos[totalPrestamos++] = new Prestamo(u, libro, dias);
        libro.setDisponible(false);
        u.agregarPrestamo();
        System.out.println("Prestamo realizado: \"" + libro.getTitulo() + "\" a " + u.getNombre()
                + " por " + dias + " dias.");
    }

    private void devolverLibro() {
        // Mostrar solo prestamos activos
        System.out.println("\n--- PRESTAMOS ACTIVOS ---");
        boolean hayActivos = false;
        for (int i = 0; i < totalPrestamos; i++) {
            if (prestamos[i].isActivo()) {
                Prestamo p = prestamos[i];
                System.out.println("[" + i + "] " + p.getLibro().getTitulo()
                        + " -> " + p.getUsuario().getNombre()
                        + " (" + p.getDias() + " dias)");
                hayActivos = true;
            }
        }
        if (!hayActivos) { System.out.println("No hay prestamos activos."); return; }

        System.out.print("Ingrese el numero del prestamo a devolver: ");
        int idx = leerInt();
        if (idx < 0 || idx >= totalPrestamos || !prestamos[idx].isActivo()) {
            System.out.println("Prestamo invalido o ya devuelto.");
            return;
        }

        Prestamo p = prestamos[idx];
        p.finalizarPrestamo();
        p.getLibro().setDisponible(true);
        p.getUsuario().devolverPrestamo();

        double multa = p.calcularMulta();
        if (multa > 0) {
            p.getUsuario().agregarMulta(multa);
            System.out.printf("Libro \"%s\" devuelto CON MULTA de $%.2f (excedio 7 dias permitidos).%n",
                    p.getLibro().getTitulo(), multa);
        } else {
            System.out.println("Libro \"" + p.getLibro().getTitulo() + "\" devuelto a tiempo. Sin multa.");
        }
    }

    private void verPrestamosActivos() {
        System.out.println("\n--- PRESTAMOS ACTIVOS ---");
        boolean hay = false;
        for (int i = 0; i < totalPrestamos; i++) {
            if (prestamos[i].isActivo()) {
                Prestamo p = prestamos[i];
                System.out.println("  Libro   : " + p.getLibro().getTitulo());
                System.out.println("  Usuario : " + p.getUsuario().getNombre());
                System.out.println("  Dias    : " + p.getDias());
                System.out.println("  ------------------------");
                hay = true;
            }
        }
        if (!hay) System.out.println("No hay prestamos activos.");
    }

    // ================================================================
    //  UTILIDADES INTERNAS
    // ================================================================

    /** Muestra la lista de usuarios y devuelve el elegido, o null si cancela. */
    private Usuario seleccionarUsuario() {
        if (totalUsuarios == 0) { System.out.println("No hay usuarios registrados."); return null; }
        System.out.println("--- USUARIOS ---");
        for (int i = 0; i < totalUsuarios; i++) {
            System.out.println("[" + i + "] " + usuarios[i].getNombre()
                    + " (Cedula: " + usuarios[i].getCedula() + ")");
        }
        System.out.print("Seleccione usuario: ");
        int idx = leerInt();
        if (idx < 0 || idx >= totalUsuarios) {
            System.out.println("Indice invalido.");
            return null;
        }
        return usuarios[idx];
    }

    /** Lee un entero; si el input no es numerico devuelve -1. */
    private int leerInt() {
        try {
            String linea = sc.nextLine().trim();
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida. Se esperaba un numero.");
            return -1;
        }
    }
}
