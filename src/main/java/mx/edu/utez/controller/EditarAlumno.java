package mx.edu.utez.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.utez.modelo.Alumno;
import mx.edu.utez.modelo.dao.AlumnoDao;

public class EditarAlumno {

    @FXML
    private TextField actualizarId, actualizarNombre, actualizarMatricula, actualizarEdad;

    private Alumno alumno;
    private int idViejito;

    public void setAlumno(Alumno alumno){
        this.alumno = alumno;
        this.idViejito = alumno.getId();

        actualizarId.setText(String.valueOf(alumno.getId()));
        actualizarNombre.setText(alumno.getNombre());
        actualizarMatricula.setText(alumno.getMatricula());
        actualizarEdad.setText(String.valueOf(alumno.getEdad()));
    }

    //WIP actualizar
    public void actualizarAlumno(ActionEvent event){
        //1 obtener la información nueva
        int nuevoId = Integer.parseInt(actualizarId.getText());
        String nuevoNombre = actualizarNombre.getText();
        String nuevaMatricula = actualizarMatricula.getText();
        int nuevaEdad = Integer.parseInt(actualizarEdad.getText());

        //2 sobreescribir la información
        alumno.setId(nuevoId);
        alumno.setNombre(nuevoNombre);
        alumno.setMatricula(nuevaMatricula);
        alumno.setEdad(nuevaEdad);

        //3 actualizar la BD
        AlumnoDao dao = new AlumnoDao();
        dao.updateAlumno(idViejito, alumno);

        //4 cerrar la ventana
        Stage ventana = (Stage) actualizarId.getScene().getWindow();
        ventana.close();
    }

}
