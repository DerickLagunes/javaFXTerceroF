package mx.edu.utez.modelo;

import java.io.InputStream;
import java.util.Scanner;

//Modela un usuario dentro del sistema
public class Usuario {

    private String nombre;
    private int edad;
    private String matricula;
    private String contrasena;

    public Usuario() {}

    public Usuario(String nombre, int edad, String matricula, String contrasena) {
        this.nombre = nombre;
        this.edad = edad;
        this.matricula = matricula;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    //Esta función me ayudará a ver si entra o no al sistema mi usuario
    public boolean login(String matricula, String contrasena){
        //Ver si existe una matricula y su contraseña en la BD

        try{
            String path = "/mx/edu/utez/controller/baseDeDatos.txt";
            InputStream stream = getClass().getResourceAsStream(path);
            Scanner sc = new Scanner(stream);

            while(sc.hasNextLine()){
                //Guardar la linea actual del ciclo
                String linea = sc.nextLine();
                String id = linea.split(",")[2];
                String contra = linea.split(",")[3];

                if (matricula.equals(id) && contrasena.equals(contra)){
                    sc.close();
                    return true; //Si existe esa combinación en la BD (archivo)
                }
            }
            sc.close();
            return false; // Esa combinación no existe en la BD
        }catch(Exception e){
            System.out.println("No se encontro el archivo o el usuario");
            e.printStackTrace();
            return false;
        }
    }
}
