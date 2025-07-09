package org.kennethmazariegos.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.kennethmazariegos.system.Main;

/**
 * FXML Controller class
 *
 * @author WELCOME KENNETH
 */
public class PrincipalController implements Initializable {
private Main principal;
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    public void GoInicio(){
        principal.InicioView();
    }
    
    public void GoProductos(){
        principal.ProductosView();
    }
    
}
