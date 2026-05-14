package taller12;

public class Usuario {
    private String nombre;
    private String cedula;
    private int librosPrestados;
    private double multaPendiente;

    public Usuario(String nombre, String cedula) {
        // Validación al momento de crear el objeto
        if (!validarCedula(cedula)) {
            throw new IllegalArgumentException("La cédula proporcionada no es válida.");
        }
        this.nombre = nombre;
        this.cedula = cedula;
        this.librosPrestados = 0;
        this.multaPendiente = 0;
    }

    // --- Métodos de la clase Usuario ---
    public String getNombre()          { return nombre; }
    public String getCedula()          { return cedula; }
    public int    getLibrosPrestados() { return librosPrestados; }
    public double getMultaPendiente()  { return multaPendiente; }

    public void agregarPrestamo()          { librosPrestados++; }
    public void devolverPrestamo()         { if (librosPrestados > 0) librosPrestados--; }
    public void agregarMulta(double multa) { multaPendiente += multa; }

    public void pagarMulta() {
        multaPendiente = 0;
        System.out.println("Multa pagada correctamente.");
    }

    // --- Lógica de Validación Integrada ---
    public static boolean validarCedula(String cedula) {
        if (cedula == null || cedula.length() != 10) {
            return false;
        }

        try {
            int suma = 0;
            int[] multiplicadores = {2, 1, 2, 1, 2, 1, 2, 1, 2};

            for (int i = 0; i < 9; i++) {
                int numero = Character.getNumericValue(cedula.charAt(i));
                int resultado = numero * multiplicadores[i];

                if (resultado > 9) {
                    resultado -= 9;
                }
                suma += resultado;
            }

            int decenaSuperior = (suma % 10 == 0) ? suma : ((suma / 10) + 1) * 10;
            int digitoVerificador = decenaSuperior - suma;

            int ultimoDigito = Character.getNumericValue(cedula.charAt(9));

            return digitoVerificador == ultimoDigito;
        } catch (Exception e) {
            return false; 
        }
    }
}
