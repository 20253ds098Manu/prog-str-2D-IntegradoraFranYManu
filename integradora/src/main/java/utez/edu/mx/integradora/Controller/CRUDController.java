package utez.edu.mx.integradora.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utez.edu.mx.integradora.Model.Paciente;
import utez.edu.mx.integradora.Service.CRUDService;

import java.io.IOException;

public class CRUDController {

    @FXML private Label labeltxt;
    @FXML private Label lbltotal;
    @FXML private Label lblactivos;
    @FXML private Label lblinactivos;

    @FXML private TableView<Paciente> tvpacientes;
    @FXML private TableColumn<Paciente, String> colCurp;
    @FXML private TableColumn<Paciente, String> colNombre;
    @FXML private TableColumn<Paciente, Integer> colEdad;
    @FXML private TableColumn<Paciente, String> colTelefono;
    @FXML private TableColumn<Paciente, String> colAlergias;
    @FXML private TableColumn<Paciente, String> colActivo;

    private final CRUDService service = new CRUDService();
    private Paciente pacienteSeleccionado;

    private final ObservableList<Paciente> oblpaciente = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        colCurp.setCellValueFactory(new PropertyValueFactory<>("curp"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colAlergias.setCellValueFactory(new PropertyValueFactory<>("alergias"));
        colActivo.setCellValueFactory(new PropertyValueFactory<>("textoStatus"));

        oblpaciente.setAll(service.obtenerTodo());
        tvpacientes.setItems(oblpaciente);

        tvpacientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            pacienteSeleccionado = newSel;
        });

        cargarContadores();
    }

    @FXML
    public void onNuevo(){
        pacienteSeleccionado=null;
        cambiarVista();
    }
    @FXML
    public void onActualizar(){
        try {
            if (pacienteSeleccionado==null){
                throw new IllegalArgumentException("Seleccione un paciente a actualizar");
            }
            cambiarVista();
        }catch (Exception e){
            labeltxt.setText(e.getMessage());
            labeltxt.setStyle("-fx-text-fill: red");
        }
    }

    @FXML
    public void onCambiarStatus(){
        try{
            service.cambiarStatus(pacienteSeleccionado);
            recargar();
            labeltxt.setText("Cambio de estatus exitoso");
            labeltxt.setStyle("-fx-text-fill: green");
        }catch (Exception e){
            labeltxt.setText(e.getMessage());
            labeltxt.setStyle("-fx-text-fill: red");
        }
    }

    @FXML
    public void onEliminar(){
        try{
            if(pacienteSeleccionado == null){
                throw new IllegalArgumentException("Seleccione un paciente");
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Eliminar paciente " + pacienteSeleccionado.getNombre() + "?");

            if(alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK){
                service.eliminar(pacienteSeleccionado);
                recargar();
                labeltxt.setText("Paciente eliminado");
                labeltxt.setStyle("-fx-text-fill: green");
            }

        }catch (Exception e){
            labeltxt.setText(e.getMessage());
            labeltxt.setStyle("-fx-text-fill: red");
        }
    }

    @FXML
    public void onRecargar(){
        recargar();
    }

    private void recargar(){
        oblpaciente.setAll(service.obtenerTodo());
        cargarContadores();
    }

    private void cargarContadores(){
        lblactivos.setText("Activos: " + service.obtenerActivos());
        lblinactivos.setText("Inactivos: " + service.obtenerInactivos());
        lbltotal.setText("Total: " + service.obtenerTotal());
    }

    private void cambiarVista(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utez/edu/mx/integradora/Views/formview.fxml"));
            Scene scene = new Scene(loader.load());

            formController controller = loader.getController();
            controller.setPaciente(pacienteSeleccionado);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Formulario de Paciente");

            stage.setOnHidden(event -> recargar());

            stage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}