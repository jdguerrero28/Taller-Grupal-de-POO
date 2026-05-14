package taller12;

class Prestamo {
    private Usuario usuario;
    private Libro   libro;
    private int     diasPrestado;
    private boolean activo;

    public Prestamo(Usuario usuario, Libro libro, int diasPrestado) {
        this.usuario      = usuario;
        this.libro        = libro;
        this.diasPrestado = diasPrestado;
        this.activo       = true;
    }

    public Usuario getUsuario()  { return usuario; }
    public Libro   getLibro()    { return libro; }
    public int     getDias()     { return diasPrestado; }
    public boolean isActivo()    { return activo; }

    public void finalizarPrestamo() { activo = false; }

    /** $0.50 por cada dia que exceda los 7 dias permitidos */
    public double calcularMulta() {
        return (diasPrestado > 7) ? (diasPrestado - 7) * 0.50 : 0;
    }
}
