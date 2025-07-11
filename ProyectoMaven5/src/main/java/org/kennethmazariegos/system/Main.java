package org.kennethmazariegos.system;

import java.io.InputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.kennethmazariegos.controller.InicioController;
import org.kennethmazariegos.controller.LoginController;
import org.kennethmazariegos.controller.PrincipalController;
import org.kennethmazariegos.controller.ProductosController;

public class Main extends Application{
    private final String URL="/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.escenarioPrincipal = stage;
        InicioView();
        
        escenarioPrincipal.setTitle("Tienda");
        escenarioPrincipal.show();
    }
    
    public Initializable cambiarEscena(String fxml, double ancho, double alto) throws Exception{
        Initializable interfazCargada = null;
        
        FXMLLoader cargadorFXML = new FXMLLoader();
        
        InputStream archivoFXML = Main.class.getResourceAsStream(URL+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Main.class.getResource(URL+fxml));
        
        escena = new Scene(cargadorFXML.load(archivoFXML), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();//usar el tamaño de la escena en el stage
        
        interfazCargada = cargadorFXML.getController();
        
        return interfazCargada;
    }
    
    public void InicioView(){
        try {
            InicioController ini = (InicioController)cambiarEscena("InicioView.FXML", 1280, 720);
            ini.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al cambio en Inicio...");
                    e.printStackTrace();
        }
    }
    
    public void LoginView(){
        try {
            LoginController lg = (LoginController)cambiarEscena("LoginView.fxml", 1280, 720);
            lg.setPrincipal(this);
        } catch (Exception e) {
             System.out.println("Error al cambio en Login...");
                    e.printStackTrace();
        }
    }
    
    public void PrincipalView(){
        try {
            PrincipalController prc = (PrincipalController)cambiarEscena("Principal.fxml", 1280, 720);
            prc.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al cambio en Principal...");
                    e.printStackTrace();
        }
    }
    
    public void ProductosView(){
        try {
            ProductosController pcc = (ProductosController)cambiarEscena("ProductosView.fxml", 1280, 720);
            pcc.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al cambio en Principal...");
                    e.printStackTrace();
        }
    }
    
}
