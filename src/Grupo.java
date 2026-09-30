// Clase pública Grupo con los atributos privados id (int) y nombre (String). Generar constructor vacío, constructor con todos los parámetros, getters y setters.

public class Grupo {
    private int id;
    private String nombre;

    // Constructor vacío
    public Grupo() {
    }

    // Constructor con todos los parámetros
    public Grupo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
