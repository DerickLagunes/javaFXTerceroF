package mx.edu.utez.controller;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import mx.edu.utez.modelo.Alumno;
import mx.edu.utez.modelo.dao.AlumnoDao;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
    private VBox body;
    @FXML
    private ProgressIndicator spinner;


    @FXML
    private Button eliminarAlumno;


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

        // Ocultar al inicio
        body.setOpacity(0);
        spinner.setVisible(true); // Mostrar spinner

        tablaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tablaEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        // Crear tarea en segundo plano
        Task<List<Alumno>> cargarAlumnosTask = new Task<>() {
            @Override
            protected List<Alumno> call() throws Exception {
                AlumnoDao dao = new AlumnoDao();
                return dao.readAlumnos();
            }
        };

        // Cuando termina correctamente
        cargarAlumnosTask.setOnSucceeded(workerStateEvent -> {
            List<Alumno> datos = cargarAlumnosTask.getValue();
            alumnoTabla.setItems(FXCollections.observableArrayList(datos));

            // Ocultar spinner
            spinner.setVisible(false);

            // Mostrar fade-in
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), body);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });

        // Si ocurre un error
        cargarAlumnosTask.setOnFailed(workerStateEvent -> {
            spinner.setVisible(false);
            System.err.println("Error al cargar alumnos: " + cargarAlumnosTask.getException());
        });

        // Ejecutar el Task en un hilo nuevo
        new Thread(cargarAlumnosTask).start();

        //WIP la selección de items
        alumnoTabla.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && !alumnoTabla.getSelectionModel().isEmpty()){
                Alumno seleccionado = (Alumno) alumnoTabla.getSelectionModel().getSelectedItem();
                //Hay que cargar la siguiente ventana antes de enviar la información
                abrirVentanaEdicion(seleccionado);
            } else {
                eliminarAlumno.setDisable(false);
            }
        });
        alumnoTabla.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.DELETE){
                Alumno seleccionado = (Alumno) alumnoTabla.getSelectionModel().getSelectedItem();
                if(confirmarEliminar()){
                    AlumnoDao dao = new AlumnoDao(); // <-- Esto no lo copien
                    if(dao.deleteAlumno(seleccionado.getId())){
                        alumnoTabla.getItems().remove(seleccionado);
                    }
                }
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

    private boolean confirmarEliminar(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // SI O NO
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Hola usuario");
        alert.setContentText("¿Estas ABSOLUTAMENTE seguro que deseas borrar al alumno?");
        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    public void eliminarAlumno(ActionEvent event){
        //¿Cual es el elemento seleccionado?
        Alumno seleccionado = (Alumno) alumnoTabla.getSelectionModel().getSelectedItem();
        if(seleccionado != null){
            if(confirmarEliminar()){
                //Proceder a eliminar de la BD y de la tabla
                AlumnoDao dao = new AlumnoDao();
                //Intenta eliminar de la base de datos (TRUE o FALSE)
                if(dao.deleteAlumno(seleccionado.getId())){
                    //Si si se elimino de la BD
                    alumnoTabla.getItems().remove(seleccionado);
                }
            }
        }
    }

}//<--- Cierra clase
