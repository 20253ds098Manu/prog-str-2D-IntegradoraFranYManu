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
    //declaramos todas las variables que se usan en el fxml
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
    //creamos la instancia del service
    private final CRUDService service = new CRUDService();
    //creamos el paciente que guardara los datos del paciente que seleccionemos mediante el listener
    private Paciente pacienteSeleccionado;
    //la observable list que le pondremos a nuestro table view
    private final ObservableList<Paciente> oblpaciente = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        //todas las columnas de nuestro tableview y su respectivo getter
        colCurp.setCellValueFactory(new PropertyValueFactory<>("curp"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colAlergias.setCellValueFactory(new PropertyValueFactory<>("alergias"));
        //getter "especial" para no consegir el valor booleano
        colActivo.setCellValueFactory(new PropertyValueFactory<>("textoStatus"));
        //usamos un metodo para conseguir meter los datos del csv a los datos de nuestra observable list
        oblpaciente.setAll(service.obtenerTodo());
        //ahora los asignamos al tableviw para que se muestren
        tvpacientes.setItems(oblpaciente);
        //nuestro listener
        tvpacientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            pacienteSeleccionado = newSel;
        });

        cargarContadores();
    }

    @FXML
    public void onNuevo(){
        //si se pulsa nuevo se borrara el paciente seleccionado para que set paciente no lo ponga como si se actualizara
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
            //llama al service para que llame al repositry y cambiar el status en el csv
            service.cambiarStatus(pacienteSeleccionado);
            recargar();
            labeltxt.setText("Cambio de estatus exitoso");
            labeltxt.setStyle("-fx-text-fill: green");
        }catch (Exception e){
            //si ocurre un error el catch lo atrapara y lo mostrara en el label
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
            //la alerta que se mostrara para confirmar la eliminacion permanente
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Eliminar paciente " + pacienteSeleccionado.getNombre() + "?");

            if(alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK){
                //si se pulsa ok entonces se llamara al service para luego que el repository lo borre y se recargue la tbvw
                service.eliminar(pacienteSeleccionado);
                recargar();
                labeltxt.setText("Paciente eliminado");
                labeltxt.setStyle("-fx-text-fill: green");
            }

        }catch (Exception e){
            //mostrar el error si algo sale mal
            labeltxt.setText(e.getMessage());
            labeltxt.setStyle("-fx-text-fill: red");
        }
    }

    @FXML
    public void onRecargar(){
        recargar();
    }

    private void recargar(){
        //se vuelve a conseguir todos loss datos del csv y se ponen en el observablelist
        oblpaciente.setAll(service.obtenerTodo());
        cargarContadores();
    }

    //metodo para que se cuenten los status de los pacientes registrados
    private void cargarContadores(){
        lblactivos.setText("Activos: " + service.obtenerActivos());
        lblinactivos.setText("Inactivos: " + service.obtenerInactivos());
        lbltotal.setText("Total: " + service.obtenerTotal());
    }
    //metodo para lanzar la nueva ventana del formulario, llamando al formview.fxml
    private void cambiarVista(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utez/edu/mx/integradora/Views/formview.fxml"));
            Scene scene = new Scene(loader.load(),400,500);
            //se obtiene el controller de form y se le pasa el paciente seleccionado
            formController fcontroller = loader.getController();
            //se intentara poner al paciente si es que hay uno seleccionado
            fcontroller.setPaciente(pacienteSeleccionado);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Formulario de Paciente");
            //cuando se cierre la ventana se llamara al metodo recargar
            stage.setOnHidden(event -> recargar());

            stage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}