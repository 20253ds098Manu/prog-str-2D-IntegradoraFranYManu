package utez.edu.mx.integradora.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utez.edu.mx.integradora.Model.Paciente;
import utez.edu.mx.integradora.Service.CRUDService;

public class formController {

    @FXML private TextField txtcurp;
    @FXML private TextField txtnombre;
    @FXML private TextField txtedad;
    @FXML private TextField txttelefono;
    @FXML private TextField txtalergias;
    @FXML private Label labeltxt;
    @FXML private Label labeldeproceso;

    private final CRUDService service = new CRUDService();

    private Paciente paciente;

    public void setPaciente(Paciente paciente){
        this.paciente = paciente;
        if(paciente != null){
            labeldeproceso.setText("Actualizar paciente");
            txtcurp.setText(paciente.getCurp());
            txtcurp.setDisable(true);
            txtnombre.setText(paciente.getNombre());
            txtedad.setText(String.valueOf(paciente.getEdad()));
            txttelefono.setText(paciente.getTelefono());
            txtalergias.setText(paciente.getAlergias());
        }else {
            labeldeproceso.setText("Agregar paciente nuevo");

        }
    }

    @FXML
    public void initialize(){
    }


    @FXML
    public void onGuardar(){
        if(paciente!=null){
            try{
                service.actualizar(
                        paciente,
                        txtcurp.getText().trim(),
                        txtnombre.getText(),
                        txtedad.getText(),
                        txttelefono.getText(),
                        txtalergias.getText()
                );

                labeltxt.setText("Paciente actualizado correctamente");
                labeltxt.setStyle("-fx-text-fill: green");

                cerrar();

            } catch (Exception e) {
                labeltxt.setText(e.getMessage());
                labeltxt.setStyle("-fx-text-fill: red");
            }


        }else{
            try {
                service.agregarpaciente(
                        txtcurp.getText().trim(),
                        txtnombre.getText(),
                        txtedad.getText(),
                        txttelefono.getText(),
                        txtalergias.getText()
                );

                labeltxt.setText("Paciente agregado correctamente");
                labeltxt.setStyle("-fx-text-fill: green");

                cerrar();

            } catch (Exception e) {
                labeltxt.setText(e.getMessage());
                labeltxt.setStyle("-fx-text-fill: red");
            }

        }
    }


    @FXML
    public void onCancelar(){
        cerrar();
    }

    private void cerrar(){
        paciente=null;
        Stage stage = (Stage) txtcurp.getScene().getWindow();
        stage.close();
    }
}