package utez.edu.mx.integradora.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utez.edu.mx.integradora.Model.Paciente;
import utez.edu.mx.integradora.Service.CRUDService;

public class formController {
    //declarar los recursos que se usaron en el fxml
    @FXML private TextField txtcurp;
    @FXML private TextField txtnombre;
    @FXML private TextField txtedad;
    @FXML private TextField txttelefono;
    @FXML private TextField txtalergias;
    @FXML private Label labeltxt;
    @FXML private Label labeldeproceso;

    private final CRUDService service = new CRUDService();
    //aqui se guardara el paciente seleccionado que se manda desde el crudcontroller cuando se cambia de escena
    private Paciente paciente;
    //metodo que intenta poner al paciente seleccionado en los campos
    public void setPaciente(Paciente paciente){
        this.paciente = paciente;
        if(paciente != null){
            //si el paciente no es null significa que se quiere actualizar
            labeldeproceso.setText("Actualizar paciente");
            txtcurp.setText(paciente.getCurp());
            //deshabilita el campo de curp para que no se pueda cambiar
            txtcurp.setDisable(true);
            txtnombre.setText(paciente.getNombre());
            txtedad.setText(String.valueOf(paciente.getEdad()));
            txttelefono.setText(paciente.getTelefono());
            txtalergias.setText(paciente.getAlergias());
        }else {
            //si no hay paciente seleccionado significa que se quiere agregar uno nuevo
            labeldeproceso.setText("Agregar paciente nuevo");

        }
    }

    @FXML
    public void initialize(){
    }

    //el boton guardar funciona en ambos casos, nuevo o actualizar
    @FXML
    public void onGuardar(){
        if(paciente!=null){
            //intenta actualizar
            try{
                //le pasa todos los datos al service para poder validarlos
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
            //intenta guardar nuevo
            try {
                //le pasa todos los datos al service para poder validarlos
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
        //si se cierra el paciente seleccionado se limpia para evitar errores
        paciente=null;
        //cerrar la ventana
        Stage stage = (Stage) txtcurp.getScene().getWindow();
        stage.close();
    }
}