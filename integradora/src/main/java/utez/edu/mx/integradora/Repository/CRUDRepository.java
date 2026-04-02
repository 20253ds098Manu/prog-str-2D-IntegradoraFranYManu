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

    private final Path path = Paths.get("integradora","data", "pacientes.csv");
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

        String line = paciente.getCurp() +","+paciente.getNombre()+","+paciente.getEdad()+","+paciente.getTelefono()+","+paciente.getAlergias()+",ACTIVO"+"\n";
        Files.writeString(path, line, StandardOpenOption.APPEND);
    }


    public List<Paciente> obtenertodo(){
        asegurarExiste();
        List<Paciente> lista = new ArrayList<>();
        try {
            List<String> lineas = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String linea : lineas) {
                String[] datos = linea.split(",");
                boolean activo = datos[5].equals("ACTIVO");
                Paciente paciente = new Paciente(datos[0],datos[1],Integer.parseInt(datos[2]),datos[3],datos[4],activo);
                lista.add(paciente);
            }
        }catch (Exception e){
            throw new IllegalArgumentException("Error al buscar datos del csv");
        }
        return lista;
    }

    public Paciente buscarPaciente(String curp){
        for(Paciente paciente : obtenertodo()){
            if (curp.equals(paciente.getCurp())){
                return paciente;
            }
        }
        return null;

    }
    public Paciente buscarPaciente(String curp, List<Paciente> listaPacientes){
        for(Paciente paciente : listaPacientes){
            if (curp.equals(paciente.getCurp())){
                return paciente;
            }
        }
        return null;

    }

    public void actualizarTodo(List<Paciente> lista) throws IOException {
        asegurarExiste();

        List<String> lineas = new ArrayList<>();
        for(Paciente paciente : lista) {
            String line = paciente.getCurp() + "," + paciente.getNombre() + "," + paciente.getEdad() + "," + paciente.getTelefono() + "," + paciente.getAlergias() + ",ACTIVO" ;
            lineas.add(line);
        }
        Files.write(path, lineas, StandardCharsets.UTF_8);


    }




}