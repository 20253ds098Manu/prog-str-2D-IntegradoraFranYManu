package utez.edu.mx.integradora.Service;

import utez.edu.mx.integradora.Model.Paciente;
import utez.edu.mx.integradora.Repository.CRUDRepository;

import javax.lang.model.element.NestingKind;
import java.io.IOException;
import java.util.List;

public class CRUDService {
    CRUDRepository repository = new CRUDRepository();
    //TODAS las validaciones para ver si los datos son correctos o no, de no ser asi se
    //devuelve una excepcion personalizada que se atrapara con un catch y se mostrara en el label
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

        if(nombre.trim().length()<5){
            throw new IllegalArgumentException("El nombre debe de contener al menos 5 caracteres");
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
        //despues de pasar todas las validaciones se crea un paciente nuevo y se le pasa a repository
        Paciente newpaciente = new Paciente(curp,nombre,iedad,telefono,alergias,true);
        repository.agregarPaciente(newpaciente);


    }
    //metodo "tunel" para no romper la jerarquia
    public List<Paciente> obtenerTodo(){
        return repository.obtenertodo();
    }

    //TODAS las validaciones para ver si los datos son correctos o no, de no ser asi se
    //devuelve una excepcion personalizada que se atrapara con un catch y se mostrara en el label
    public void actualizar(Paciente pacientecurpabuscar, String curp, String nombre, String edad, String telefono, String alergias){
        if (pacientecurpabuscar==null){
            throw new IllegalArgumentException("Seleccione el usario a actualizar");
        }
        //se crea una lista-arreglo con todos los pacientes del csv
        List<Paciente> lista = repository.obtenertodo();
        //mediante un metodo en el repository encontramos el paciente que queremos actualiar en ESTA lista
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

        if(nombre.trim().length()<5){
            throw new IllegalArgumentException("El nombre debe de contener al menos 5 caracteres");
        }

        if(edad.trim().isEmpty()){
            throw new IllegalArgumentException("La edad no debe estar vacia");
        }
        //variable tipo int ya que edad venia en string y no se puede validar asi
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
        //actualizar el paciente mediante setters en nuestra lista recien creada
        pacienteAActualizar.setAlergias(alergias);
        pacienteAActualizar.setCurp(curp);
        pacienteAActualizar.setEdad(iedad);
        pacienteAActualizar.setTelefono(telefono);
        pacienteAActualizar.setNombre(nombre);
        try {
            //intenta poner la lista recien creada y actualizada en el csv
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
            //hacemos lo mismo que en actualizar para conseguir la lista y su paciente a cambiar
            List<Paciente> lista = repository.obtenertodo();
            Paciente pacienteStatus = repository.buscarPaciente(pacienteabuscar.getCurp(), lista);
            //invertimos su status
            pacienteStatus.setStatus((!pacienteStatus.isStatus()));
            //volvemos a pasar toda la lista para el csv
            repository.actualizarTodo(lista);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al cambiar el status");
        }
    }

    public void eliminar(Paciente pacienteAEliminar) throws IOException {
        if(pacienteAEliminar==null){
            throw new IllegalArgumentException("Seleccione un paciente a eliminar");
        }
        //mismo proceso pero que antes pero ahora para eliminar
        List<Paciente> list = obtenerTodo();
        Paciente pacienteDeLista = repository.buscarPaciente(pacienteAEliminar.getCurp(),list);
        list.remove(pacienteDeLista);
        repository.actualizarTodo(list);


    }

    //3 metodos muy parecidos para poder contar los status de los pacientes
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