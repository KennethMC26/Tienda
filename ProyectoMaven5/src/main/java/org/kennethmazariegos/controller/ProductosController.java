package org.kennethmazariegos.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kennethmazariegos.database.Conexion;
import org.kennethmazariegos.model.Producto;
import org.kennethmazariegos.system.Main;

public class ProductosController implements Initializable {

    private Main principal;
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    public void GoPrincipal(){
        principal.PrincipalView();
    }

}
