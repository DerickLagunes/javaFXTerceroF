package mx.edu.utez.modelo.dao;

import mx.edu.utez.utils.OracleDatabaseConnectionManager;
import mx.edu.utez.modelo.Alumno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDao {

    //Este archivo hace CRUD en la BD
    //En el contexto del Alumno

    //Operación de Create
    public boolean createAlumno(Alumno a){
        //Paso 1 obtener una conexión a la BD

        //Paso 2: configurar la query SQL
        String query = "INSERT INTO alumno(id, nombre, matricula, edad) VALUES(?,?,?,?)";
        //Paso 3: intentar ejecutar la query
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            //Preparar la query con parametros
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, a.getId());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getMatricula());
            ps.setInt(4,a.getEdad());
            //Ejecutar la query
            if(ps.executeUpdate() > 0){
                //Que si se ejecuto
                System.out.println("El alumno se inserto correctamente");
                conn.close();
                return true;
            }
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }


    //Función de Read
    public List<Alumno> readAlumnos(){
        //Paso 1 obtener una conexión a la BD

        //Paso 2: configurar la query SQL
        String query = "SELECT * FROM alumno ORDER BY id ASC";
        //Paso 3: intentar ejecutar la query
        List<Alumno> alumnos = new ArrayList<Alumno>();
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            //paso 4: PreparedStatement
            PreparedStatement ps = conn.prepareStatement(query);
            //paso 5: ejecutar la query con executeQuery()
            ResultSet rs = ps.executeQuery();
            //paso 6: traducir lo de BD a Java
            while(rs.next()){
                Alumno a = new Alumno();
                a.setId(rs.getInt("Id"));
                a.setNombre(rs.getString("Nombre"));
                a.setMatricula(rs.getString("Matricula"));
                a.setEdad(rs.getInt("Edad"));
                alumnos.add(a);
            }
            //paso 7: cerrar conexión y reotrnar lista
            rs.close();
            conn.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return alumnos;
    }

    //Función de Update
    public boolean updateAlumno(int idViejito, Alumno a){
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            String query = "UPDATE alumno set id=?, nombre=?, matricula=?, edad=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,a.getId());
            ps.setString(2,a.getNombre());
            ps.setString(3,a.getMatricula());
            ps.setInt(4,a.getEdad());
            ps.setInt(5,idViejito);
            if(ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Función de Delete Fisico
    public boolean deleteAlumno(int id){
        try {
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            String query = "DELETE FROM alumno WHERE id=?"; // <-- NO OLVIDAR EL WHERE
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,id);
            if(ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Función de Delete Logico
    public boolean deleteLAlumno(int id){
        try {
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            String query = "UPDATE alumno SET status = 0 WHERE id=?"; // <-- NO OLVIDAR EL WHERE
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,id);
            if(ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }



}
