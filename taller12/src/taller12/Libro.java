package taller12;


class Libro {
    private String titulo;
    private String autor;
    private String genero;
    private boolean disponible;

    public Libro(String titulo, String autor, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.disponible = true;
    }

    public String getTitulo() { return titulo; }
    public String getAutor()  { return autor; }
    public String getGenero() { return genero; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public void mostrarInfo() {
        System.out.println("  Titulo   : " + titulo);
        System.out.println("  Autor    : " + autor);
        System.out.println("  Genero   : " + genero);
        System.out.println("  Disponible: " + (disponible ? "Si" : "No"));
        System.out.println("  ------------------------");
    }
}
