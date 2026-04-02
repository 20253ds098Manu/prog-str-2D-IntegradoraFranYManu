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

public class CRUDController {
    @FXML
    private Label labeltxt;
    @FXML
    private Label contador;
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
            txtcurp.setText(newselection.getCurp());
            txtnombre.setText(newselection.getNombre());
            txttelefono.setText(newselection.getTelefono());
            txtalergias.setText(newselection.getAlergias());
            txtedad.setText(String.valueOf(newselection.getEdad()));
            pacienteSeleccionado= newselection;
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
            oblpaciente.setAll(service.obtenerTodo());
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
    }

    @FXML
    public void onActualizar(){
        try {
            service.actualizar(pacienteSeleccionado.getCurp(), txtcurp.getText().trim(), txtnombre.getText(), txtedad.getText(), txttelefono.getText(), txtalergias.getText());
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

    }
    @FXML
    public void onRecargar(){
        oblpaciente.setAll(service.obtenerTodo());

    }@FXML
    public void onEliminar(){

    }


}