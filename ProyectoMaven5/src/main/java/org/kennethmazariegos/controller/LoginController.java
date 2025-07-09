/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.kennethmazariegos.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.kennethmazariegos.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class LoginController implements Initializable {
private Main principal;
    @FXML private Button log, regresar;
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    public void GoPrincipal(){
        principal.PrincipalView();
    }
    
    public void GoInicio(){
        principal.InicioView();
    }
}
