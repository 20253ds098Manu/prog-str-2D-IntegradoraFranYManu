package utez.edu.mx.integradora.Repository;


import utez.edu.mx.integradora.Model.Paciente;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CRUDRepository {
    //ruta de nuestro csv
    private final Path path = Paths.get("integradora","data", "pacientes.csv");
    //metodo para asegurar que existen los arhivos y si no crearlos
    private void asegurarExiste(){
        try {
            Files.createDirectories(path.getParent());
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        }catch (Exception e){
            throw new IllegalArgumentException("Error con el archivo");
        }
    }

    public void agregarPaciente(Paciente paciente) throws IOException {
        asegurarExiste();
        //convierto el paciente en un string largo separado por comas y lo meto con append al csv
        String line = paciente.getCurp() +","+paciente.getNombre()+","+paciente.getEdad()+","+paciente.getTelefono()+","+paciente.getAlergias()+",ACTIVO"+"\n";
        Files.writeString(path, line, StandardOpenOption.APPEND);
    }


    public List<Paciente> obtenertodo(){
        //se asegura que el archivo exista antes de leer
        asegurarExiste();
        //lista donde se guardaran los pacientes
        List<Paciente> lista = new ArrayList<>();
        try {
            //lee todas las lineas del archivo
            List<String> lineas = Files.readAllLines(path, StandardCharsets.UTF_8);

            for (String linea : lineas) {
                //separa los datos por comas
                String[] datos = linea.split(",");
                //revisa si el paciente esta activo
                boolean activo = datos[5].equals("ACTIVO");
                //crea el objeto paciente con los datos del archivo
                Paciente paciente = new Paciente(
                        datos[0],
                        datos[1],
                        Integer.parseInt(datos[2]),
                        datos[3],
                        datos[4],
                        activo
                );
                //agrega el paciente a la lista
                lista.add(paciente);
            }
        }catch (Exception e){
            // error si algo falla leyendo
            throw new IllegalArgumentException("Error al buscar datos del csv");
        }
        // regresa la lista completa
        return lista;
    }
    //metodo para vilidar si el curp ya esta ocupado
    public Paciente buscarPaciente(String curp){
        for(Paciente paciente : obtenertodo()){
            if (curp.equals(paciente.getCurp())){
                return paciente;
            }
        }
        return null;

    }
    //metodo para buscar el paciente dentro de una lista que se va a mandar
    public Paciente buscarPaciente(String curp, List<Paciente> listaPacientes){
        for(Paciente paciente : listaPacientes){
            if (curp.equals(paciente.getCurp())){
                return paciente;
            }
        }
        return null;

    }

    public void actualizarTodo(List<Paciente> lista) throws IOException {

        //asegura que el archivo exista
        asegurarExiste();

        //lista de lineas para guardar en el archivo
        List<String> lineas = new ArrayList<>();

        for(Paciente paciente : lista) {

            String status;

            //define si esta activo o inactivo
            if (paciente.isStatus()){
                status = "ACTIVO";
            }else{
                status = "INACTIVO";
            }

            //arma la linea con los datos del paciente
            String line = paciente.getCurp() + "," + paciente.getNombre() + "," + paciente.getEdad() + "," + paciente.getTelefono() + "," + paciente.getAlergias() + ","+status;

            //agrega la linea a la lista
            lineas.add(line);
        }

        //sobrescribe el archivo con todos los datos
        Files.write(path, lineas, StandardCharsets.UTF_8);
    }




}