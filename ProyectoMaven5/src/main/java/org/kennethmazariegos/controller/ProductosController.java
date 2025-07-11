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

    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn colCodigoProducto, colNombreProducto, colDescripcion, colPrecio, colStock, colFechaIngreso;
    @FXML
    private TextField txtCodigoProducto, txtNombreProducto, txtDescripcion, txtPrecio, txtStock, txtBuscar;
    @FXML
    private DatePicker dpFechaIngreso;
    @FXML
    private Button btnAgregar, btnEditar, btnEliminar, btnBuscar;

    private ObservableList<Producto> listaProductos;
    private Main principal;
    private Producto modeloProducto;

    private enum acciones {
        AGREGAR, ACTUALIZAR, ELIMINAR, NINGUNA
    }

    private acciones tipoDeAccion = acciones.NINGUNA;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColumnas();
        cargarTablaProductos();
        tablaProductos.setOnMouseClicked(e -> cargarProductoTxt());
    }

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    public void GoPrincipal(){
        principal.PrincipalView();
    }

    private void configurarColumnas() {
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("codigoProducto"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("stock"));
        colFechaIngreso.setCellValueFactory(new PropertyValueFactory<Producto, LocalDate>("fechaIngreso"));
    }

    private void cargarTablaProductos() {
        listaProductos = FXCollections.observableArrayList(listarProductos());
        tablaProductos.setItems(listaProductos);
        if (!listaProductos.isEmpty()) {
            tablaProductos.getSelectionModel().selectFirst();
            cargarProductoTxt();
        }
    }

    private void cargarProductoTxt() {
        Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
        if (producto != null) {
            txtCodigoProducto.setText(String.valueOf(producto.getCodigoProducto()));
            txtNombreProducto.setText(producto.getNombreProducto());
            txtDescripcion.setText(producto.getDescripcion());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            txtStock.setText(String.valueOf(producto.getStock()));
            dpFechaIngreso.setValue(producto.getFechaIngreso());
        }
    }

    private Producto cargarModeloProducto() {
        int codigo = txtCodigoProducto.getText().isEmpty() ? 0 : Integer.parseInt(txtCodigoProducto.getText());
        return new Producto(
                codigo,
                txtNombreProducto.getText(),
                txtDescripcion.getText(),
                Double.parseDouble(txtPrecio.getText()),
                Integer.parseInt(txtStock.getText()),
                dpFechaIngreso.getValue()
        );
    }

    public ArrayList<Producto> listarProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ListarProductos();");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getInt(5),
                        rs.getDate(6).toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos");
            e.printStackTrace();
        }
        return productos;
    }

    private void agregarProducto() {
        modeloProducto = cargarModeloProducto();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_AgregarProducto(?,?,?,?,?)");
            cs.setString(1, modeloProducto.getNombreProducto());
            cs.setString(2, modeloProducto.getDescripcion());
            cs.setDouble(3, modeloProducto.getPrecio());
            cs.setInt(4, modeloProducto.getStock());
            cs.setDate(5, Date.valueOf(modeloProducto.getFechaIngreso()));
            cs.execute();
            cargarTablaProductos();
        } catch (SQLException e) {
            System.out.println("Error al agregar producto");
            e.printStackTrace();
        }
    }

    private void actualizarProducto() {
        modeloProducto = cargarModeloProducto();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_ActualizarProducto(?,?,?,?,?,?)");
            cs.setInt(1, modeloProducto.getCodigoProducto());
            cs.setString(2, modeloProducto.getNombreProducto());
            cs.setString(3, modeloProducto.getDescripcion());
            cs.setDouble(4, modeloProducto.getPrecio());
            cs.setInt(5, modeloProducto.getStock());
            cs.setDate(6, Date.valueOf(modeloProducto.getFechaIngreso()));
            cs.execute();
            cargarTablaProductos();
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto");
            e.printStackTrace();
        }
    }

    private void eliminarProducto() {
        modeloProducto = tablaProductos.getSelectionModel().getSelectedItem();
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion().prepareCall("call sp_EliminarProducto(?)");
            cs.setInt(1, modeloProducto.getCodigoProducto());
            cs.execute();
            cargarTablaProductos();
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto");
            e.printStackTrace();
        }
    }

    public void limpiarCampos() {
        txtCodigoProducto.clear();
        txtNombreProducto.clear();
        txtDescripcion.clear();
        txtPrecio.clear();
        txtStock.clear();
        dpFechaIngreso.setValue(null);
    }

    private void cambiarEstado(acciones estado) {
        tipoDeAccion = estado;
        boolean activo = (estado == acciones.AGREGAR || estado == acciones.ACTUALIZAR);
        txtNombreProducto.setDisable(!activo);
        txtDescripcion.setDisable(!activo);
        txtPrecio.setDisable(!activo);
        txtStock.setDisable(!activo);
        dpFechaIngreso.setDisable(!activo);
        tablaProductos.setDisable(activo);
        btnBuscar.setDisable(activo);
        txtBuscar.setDisable(activo);
        btnAgregar.setText(activo ? "Guardar" : "Nuevo");
        btnEliminar.setText(activo ? "Cancelar" : "Eliminar");
        btnEditar.setDisable(activo);
    }

    @FXML
    private void insertarProducto() {
        switch (tipoDeAccion) {
            case NINGUNA:
                limpiarCampos();
                cambiarEstado(acciones.AGREGAR);
                break;
            case AGREGAR:
                agregarProducto();
                cambiarEstado(acciones.NINGUNA);
                break;
            case ACTUALIZAR:
                actualizarProducto();
                cambiarEstado(acciones.NINGUNA);
                break;
        }
    }

    @FXML
    private void cancelarEliminar() {
        if (tipoDeAccion == acciones.NINGUNA) {
            eliminarProducto();
        } else {
            cambiarEstado(acciones.NINGUNA);
        }
    }

    @FXML
    private void editarProducto() {
        cambiarEstado(acciones.ACTUALIZAR);
    }

    @FXML
    private void buscarProducto() {
        ArrayList<Producto> resultado = new ArrayList<>();
        String nombre = txtBuscar.getText().toLowerCase();
        for (Producto p : listaProductos) {
            if (p.getNombreProducto().toLowerCase().contains(nombre)) {
                resultado.add(p);
            }
        }
        tablaProductos.setItems(FXCollections.observableArrayList(resultado));
        if (!resultado.isEmpty()) {
            tablaProductos.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void btnAnteriorAction() {
        int index = tablaProductos.getSelectionModel().getSelectedIndex();
        if (index > 0) {
            tablaProductos.getSelectionModel().select(index - 1);
            cargarProductoTxt();
        }
    }

    @FXML
    private void btnSiguienteAction() {
        int index = tablaProductos.getSelectionModel().getSelectedIndex();
        if (index < listaProductos.size() - 1) {
            tablaProductos.getSelectionModel().select(index + 1);
            cargarProductoTxt();
        }
    }

}