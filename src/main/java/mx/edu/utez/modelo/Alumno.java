package mx.edu.utez.modelo;

public class Alumno {

    private int id;
    private String nombre;
    private String matricula;
    private int edad;

    public Alumno() {
    }

    public Alumno(int id, String nombre, String matricula, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.matricula = matricula;
        this.edad = edad;
    }

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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
