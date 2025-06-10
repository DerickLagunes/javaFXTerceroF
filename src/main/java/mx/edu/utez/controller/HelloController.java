package mx.edu.utez.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.utez.modelo.Usuario;

import javax.swing.*;

public class HelloController {

    @FXML
    private Label idLabel;
    @FXML
    private Button login;
    @FXML
    private Button registro;
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField contra;


    @FXML
    protected void cambiarLabel(ActionEvent event){
        idLabel.setText("Bienvenidos a JavaFX");
    }

    @FXML
    private void login(ActionEvent event){
        //Conseguir lo que escribio el usuario
        String user = usuario.getText();
        String pass = contra.getText();

        //Usar una instancia de usuario
        Usuario u = new Usuario();
        u.setMatricula(user);
        u.setContrasena(pass);

        //Corroborar que esta info exista en BD
        //Requires: conexion a BD, esto es una emulación
        if(u.login(user, pass)){
            //Si existe en BD y le damos acceso al sistema
            // 1. Confirmar al usuario (UX)
            JOptionPane.showMessageDialog(null, "Bienvenido al sistema");
            // Cambiar de escenario
            try{
                // 1. Obtener el padre o la escena nueva
                Parent root = FXMLLoader.load(getClass().getResource("index.fxml"));
                // 2. Obtener el stage que ya existia
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                // 3. crear la instancia de Scene
                Scene scene = new Scene(root);
                // 4. Colocar la escena en la stage
                stage.setScene(scene);
                // 5. Mostrar el escenario
                stage.show();
            }catch (Exception e){
                System.out.println("Error algo paso cargando la escena");
            }
        }else{
            //No existe en BD y no continua
            JOptionPane.showMessageDialog(null, "No tienes cuenta, registrate");
        }

    }



}