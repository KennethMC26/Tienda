package org.kennethmazariegos.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.kennethmazariegos.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class LoginController implements Initializable {
    private Main principal;

    @FXML private Button log, regresar;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void GoInicio(){
        principal.InicioView();
    }

    public void GoPrincipal(){
        String correo = txtCorreo.getText();
        String contrasena = txtContrasena.getText();

        if (correo.equals("admin") && contrasena.equals("admin")) {
            principal.PrincipalView();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error de inicio de sesión");
            alert.setHeaderText("Credenciales incorrectas");
            alert.setContentText("El correo electrónico o la contraseña son incorrectos. Por favor, inténtelo de nuevo.");
            alert.showAndWait();
        }
    }

    public void GoRegistro() {
        principal.RegistroView();
    }
}