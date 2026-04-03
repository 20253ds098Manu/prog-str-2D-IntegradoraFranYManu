package utez.edu.mx.integradora.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.Text;
import utez.edu.mx.integradora.Model.Paciente;
import utez.edu.mx.integradora.Service.CRUDService;

import java.io.IOException;

public class CRUDController {
    @FXML
    private Label labeltxt;
    @FXML
    private Label lbltotal;
    @FXML
    private Label lblactivos;
    @FXML
    private Label lblinactivos;
    @FXML
    private TextField txtcurp;
    @FXML
    private TextField txtnombre;
    @FXML
    private TextField txtedad;
    @FXML
    private TextField txttelefono;
    @FXML
    private TextField txtalergias;
    @FXML
    private TableView<Paciente> tvpacientes;
    @FXML
    private TableColumn<Paciente, String> colCurp;

    @FXML
    private TableColumn<Paciente, String> colNombre;

    @FXML
    private TableColumn<Paciente, Integer> colEdad;

    @FXML
    private TableColumn<Paciente, String> colTelefono;

    @FXML
    private TableColumn<Paciente, String> colAlergias;

    @FXML
    private TableColumn<Paciente, Boolean> colActivo;
    CRUDService service = new CRUDService();
    private Paciente pacienteSeleccionado;

    ObservableList<Paciente> oblpaciente = FXCollections.observableArrayList();
    @FXML
    public void initialize(){
        cargarContadores();
        //cargar las columnas
        colCurp.setCellValueFactory(new PropertyValueFactory<>("curp"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colAlergias.setCellValueFactory(new PropertyValueFactory<>("alergias"));
        colActivo.setCellValueFactory(new PropertyValueFactory<>("textoStatus"));
        oblpaciente.setAll(service.obtenerTodo());
        tvpacientes.setItems(oblpaciente);
        tvpacientes.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldselection, newselection) -> {
            if (newselection!= null) {
                txtcurp.setText(newselection.getCurp());
                txtnombre.setText(newselection.getNombre());
                txttelefono.setText(newselection.getTelefono());
                txtalergias.setText(newselection.getAlergias());
                txtedad.setText(String.valueOf(newselection.getEdad()));
                pacienteSeleccionado = newselection;
            }
        }
        ));



    }
    @FXML
    public void onAgregar(){
        try {
            service.agregarpaciente(txtcurp.getText().trim(),txtnombre.getText(),txtedad.getText(),txttelefono.getText(),txtalergias.getText());
            limpiar();
            labeltxt.setText("Paciente agregado correctamente");
            labeltxt.setStyle("-fx-text-fill: green");
            onRecargar();
        }catch (Exception e){
            labeltxt.setText(e.getMessage());
            labeltxt.setStyle("-fx-text-fill: red");

        }


    }
    @FXML
    public void onLimpiar(){
        limpiar();
    }
    private void limpiar(){
        labeltxt.setText("");
        txtalergias.clear();
        txtcurp.clear();
        txtedad.clear();
        txtnombre.clear();
        txttelefono.clear();
        pacienteSeleccionado=null;
    }

    @FXML
    public void onActualizar(){
        try {
            service.actualizar(pacienteSeleccionado, txtcurp.getText().trim(), txtnombre.getText(), txtedad.getText(), txttelefono.getText(), txtalergias.getText());
            limpiar();
            labeltxt.setText("Paciente actualizado correctamente");
            labeltxt.setStyle("-fx-text-fill: green");
            oblpaciente.setAll(service.obtenerTodo());
        }catch (Exception e){
            labeltxt.setText(e.getMessage());
            labeltxt.setStyle("-fx-text-fill: red");
        }
    }
    @FXML
    public void onCambiarStatus(){
        try{
            service.cambiarStatus(pacienteSeleccionado);
            labeltxt.setText("Cambio de estatus exitoso");
            labeltxt.setStyle("-fx-text-fill: green");
            oblpaciente.setAll(service.obtenerTodo());
            limpiar();
            onRecargar();
        }catch (Exception e){
            labeltxt.setText(e.getMessage());
            labeltxt.setStyle("-fx-text-fill: red");
        }

    }
    @FXML
    public void onRecargar(){
        oblpaciente.setAll(service.obtenerTodo());
        cargarContadores();

    }@FXML
    public void onEliminar(){
        try{
            if(pacienteSeleccionado==null){
                throw new IllegalArgumentException("Seleccione un paciente a eliminar");
            }
            confirmarEliminacion(pacienteSeleccionado);
            limpiar();
            onRecargar();
            labeltxt.setStyle("-fx-text-fill: green");
            labeltxt.setText("Paciente Eliminado correctamente");
        } catch (Exception e) {
            limpiar();
            onRecargar();
            labeltxt.setText(e.getMessage());
            labeltxt.setStyle("-fx-text-fill: red");
        }


    }
    public boolean confirmarEliminacion(Paciente paciente) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setContentText("¿Estas seguro de querer eliminar permanentemente al paciente "+paciente.getNombre()+"?");

        ButtonType resultado = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (resultado == ButtonType.OK) {
            service.eliminar(pacienteSeleccionado);
        }else {
            throw new IllegalArgumentException("Accion cancelada");
        }

        return false;
    }

    public void cargarContadores(){
        lblactivos.setText("Activos: "+String.valueOf(service.obtenerActivos()));
        lblinactivos.setText("Inactivos: "+String.valueOf(service.obtenerInactivos()));
        lbltotal.setText("Total: "+String.valueOf(service.obtenerTotal()));

    }


}