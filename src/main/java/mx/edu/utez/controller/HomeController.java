package mx.edu.utez.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.edu.utez.modelo.Alumno;
import mx.edu.utez.modelo.dao.AlumnoDao;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private TextField alumnoId;
    @FXML
    private TextField alumnoNombre;
    @FXML
    private TextField alumnoMatricula;
    @FXML
    private TextField alumnoEdad;
    @FXML
    private Button alumnoBoton;
    @FXML
    private TableView<Alumno> alumnoTabla;
    @FXML
    private TableColumn<Alumno, Integer> tablaId;
    @FXML
    private TableColumn<Alumno, String> tablaNombre;
    @FXML
    private TableColumn<Alumno, String> tablaMatricula;
    @FXML
    private TableColumn<Alumno, Integer> tablaEdad;


    @FXML
    public void registrarAlumno(ActionEvent event){
        int id = Integer.parseInt(alumnoId.getText());
        String nombre = alumnoNombre.getText();
        String matricula = alumnoMatricula.getText();
        int edad = Integer.parseInt(alumnoEdad.getText());

        Alumno carlos = new Alumno(id, nombre, matricula, edad);

        AlumnoDao dao = new AlumnoDao();

        boolean exito = dao.createAlumno(carlos);
        if (exito){
            JOptionPane.showMessageDialog(null, "Se registro!!!");
        }else{
            JOptionPane.showMessageDialog(null, "Hubo un problema favor de ver la consola");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Obtener info
        AlumnoDao dao = new AlumnoDao();
        List<Alumno> datos = dao.readAlumnos();

        /*for(Alumno a : datos){
            System.out.println(a.getNombre());
        }*/

        tablaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tablaEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        ObservableList<Alumno> datosObservables = FXCollections.observableArrayList(datos);

        alumnoTabla.setItems(datosObservables);

        //WIP la selección de items
        alumnoTabla.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && !alumnoTabla.getSelectionModel().isEmpty()){
                Alumno seleccionado = (Alumno) alumnoTabla.getSelectionModel().getSelectedItem();
                //Hay que cargar la siguiente ventana antes de enviar la información
                abrirVentanaEdicion(seleccionado);
            }
        });
    }//<--- que cierra la función

    private void abrirVentanaEdicion(Alumno alumno){
        //Va a cargar la nueva vista
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditarAlumno.fxml"));
            Parent root = loader.load();
            //Vamos a mandar el alumno a la nueva vista
            EditarAlumno controller = loader.getController();
            controller.setAlumno(alumno);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Alumno");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            alumnoTabla.refresh();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}//<--- Cierra clase
