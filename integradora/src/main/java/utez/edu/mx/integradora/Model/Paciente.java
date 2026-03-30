package utez.edu.mx.integradora.Model;

public class Paciente {

    private String curp;
    private String nombre;
    private int edad;
    private String telefono;
    private String alergias;
    private boolean status;

    public Paciente(String curp, String alergias, String telefono, int edad, String nombre) {
        this.curp = curp;
        this.alergias = alergias;
        this.telefono = telefono;
        this.edad = edad;
        this.nombre = nombre;
        this.status = true;
    }

    public boolean isStatus() {
        return status;

    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "curp='" + curp + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", telefono='" + telefono + '\'' +
                ", alergias='" + alergias + '\'' +
                ", status=" + status +
                '}';
    }
}
