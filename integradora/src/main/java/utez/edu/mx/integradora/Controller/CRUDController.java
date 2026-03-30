package utez.edu.mx.integradora.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TableView tvpacientes;

    CRUDService service = new CRUDService();

    ObservableList<Paciente> oblpaciente = FXCollections.observableArrayList();
    @FXML
    public void initialize(){


    }
    @FXML
    public void onAgregar(){
         try {
             service.agregarpaciente(txtcurp.getText(),txtnombre.getText(),txtedad.getText(),txttelefono.getText(),txtalergias.getText());
             labeltxt.setText("Paciente agregado correctamente");
             labeltxt.setStyle("-fx-text-fill: green");
         }catch (Exception e){
             labeltxt.setText(e.getMessage());
             labeltxt.setStyle("-fx-text-fill: red");

         }


    }
    @FXML
    public void onActualizar(){

    }
    @FXML
    public void onCambiarStatus(){

    }
    @FXML
    public void onRecargar(){

    }@FXML
    public void onEliminar(){

    }


}
