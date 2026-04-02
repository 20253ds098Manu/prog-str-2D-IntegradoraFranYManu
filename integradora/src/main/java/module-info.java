module utez.edu.mx.integradora {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.compiler;
    requires java.xml;
    opens utez.edu.mx.integradora.Model to javafx.base;

    opens utez.edu.mx.integradora to javafx.fxml;
    exports utez.edu.mx.integradora;
    exports utez.edu.mx.integradora.Controller;
    opens utez.edu.mx.integradora.Controller to javafx.fxml;
}