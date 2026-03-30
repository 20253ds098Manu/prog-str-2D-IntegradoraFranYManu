package utez.edu.mx.integradora.Service;

import javax.lang.model.element.NestingKind;

public class CRUDService {

    public void agregarpaciente(String curp, String nombre, String edad, String telefono, String alergias){

        if (curp.trim().isEmpty()){
            throw new IllegalArgumentException("El curp no debe estar vacio");
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


    }




     }


