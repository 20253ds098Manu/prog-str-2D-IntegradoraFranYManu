package utez.edu.mx.integradora.Service;

import utez.edu.mx.integradora.Model.Paciente;
import utez.edu.mx.integradora.Repository.CRUDRepository;

import javax.lang.model.element.NestingKind;
import java.io.IOException;
import java.util.List;

public class CRUDService {
    CRUDRepository repository = new CRUDRepository();

    public void agregarpaciente(String curp, String nombre, String edad, String telefono, String alergias) throws IOException {

        if (curp.trim().isEmpty()){
            throw new IllegalArgumentException("El curp no debe estar vacio");
        }
        if (repository.buscarPaciente(curp)!=null){
            throw new IllegalArgumentException("El curp ya esta ocupado");
        }

        if(nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre no debe de estar vacio");
        }

        if(edad.trim().isEmpty()){
            throw new IllegalArgumentException("La edad no debe estar vacia");
        }

        int iedad;
        try{
            iedad = Integer.parseInt(edad);
            if (iedad < 0 || iedad > 120){
                throw new IllegalArgumentException("Ingresar una edad real");
            }
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("Formato de edad invalida");
        }

        if(telefono.trim().isEmpty()){
            throw new IllegalArgumentException("El telefono no debe estar vacio");
        }

        if(!telefono.matches("\\d{10}")){
            throw new IllegalArgumentException("Formato de telefono incorrecto");
        }

        if(alergias.trim().isEmpty()){
            alergias = "Ninguna";
        }
        Paciente newpaciente = new Paciente(curp,nombre,iedad,telefono,alergias,true);
        repository.agregarPaciente(newpaciente);

    }

    public List<Paciente> obtenerTodo(){
        return repository.obtenertodo();
    }

    public void actualizar(Paciente pacientecurpabuscar, String curp, String nombre, String edad, String telefono, String alergias){
        if (pacientecurpabuscar==null){
            throw new IllegalArgumentException("Seleccione el usario a actualizar");
        }
        List<Paciente> lista = repository.obtenertodo();
        Paciente pacienteAActualizar = repository.buscarPaciente(pacientecurpabuscar.getCurp(), lista);
        //Validar los datos nuevos:
        if (curp.trim().isEmpty()){
            throw new IllegalArgumentException("El curp no debe estar vacio");
        }
        if (repository.buscarPaciente(curp)!=null && !curp.equals(pacientecurpabuscar.getCurp())){
            throw new IllegalArgumentException("El curp ya esta ocupado");
        }

        if(nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre no debe de estar vacio");
        }

        if(edad.trim().isEmpty()){
            throw new IllegalArgumentException("La edad no debe estar vacia");
        }

        int iedad;
        try{
            iedad = Integer.parseInt(edad);
            if (iedad < 0 || iedad > 120){
                throw new IllegalArgumentException("Ingresar una edad real");
            }
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("Formato de edad invalida");
        }

        if(telefono.trim().isEmpty()){
            throw new IllegalArgumentException("El telefono no debe estar vacio");
        }

        if(!telefono.matches("\\d{10}")){
            throw new IllegalArgumentException("Formato de telefono incorrecto");
        }

        if(alergias.trim().isEmpty()){
            alergias = "Ninguna";
        }

        pacienteAActualizar.setAlergias(alergias);
        pacienteAActualizar.setCurp(curp);
        pacienteAActualizar.setEdad(iedad);
        pacienteAActualizar.setTelefono(telefono);
        pacienteAActualizar.setNombre(nombre);
        try {
            repository.actualizarTodo(lista);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error actualizando la lista");
        }

    }

    public void cambiarStatus( Paciente pacienteabuscar){
        if(pacienteabuscar==null){
            throw new IllegalArgumentException("Seleccione un paciente a cambiar su status");
        }
        try {
            List<Paciente> lista = repository.obtenertodo();
            Paciente pacienteStatus = repository.buscarPaciente(pacienteabuscar.getCurp(), lista);
            pacienteStatus.setStatus((!pacienteStatus.isStatus()));
            repository.actualizarTodo(lista);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al cambiar el status");
        }
    }

    public void eliminar(Paciente pacienteAEliminar) throws IOException {
        if(pacienteAEliminar==null){
            throw new IllegalArgumentException("Seleccione un paciente a eliminar");
        }
        List<Paciente> list = obtenerTodo();
        Paciente pacienteDeLista = repository.buscarPaciente(pacienteAEliminar.getCurp(),list);
        list.remove(pacienteDeLista);
        repository.actualizarTodo(list);


    }
    public int obtenerActivos(){
        List<Paciente> list = repository.obtenertodo();
        int activos = 0;
        for(Paciente paciente : list){
            if (paciente.isStatus()){
                activos++;
            }
        }
        return activos;

    }
    public int obtenerInactivos(){
        List<Paciente> list = repository.obtenertodo();
        int inactivos = 0;
        for(Paciente paciente : list){
            if (!paciente.isStatus()){
                inactivos++;
            }
        }

        return inactivos;

    }
    public int obtenerTotal(){
        List<Paciente> list = repository.obtenertodo();
        int total = 0;
        for(Paciente paciente : list){
                total++;
            
        }
        return total;

    }




}