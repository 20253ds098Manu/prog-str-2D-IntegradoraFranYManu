# prog-str-2D-In# prog-str-2D-IntegradoraFranYManu

# Directorio de Pacientes — JavaFX CRUD

Sistema de escritorio para la gestión de pacientes de un consultorio médico, desarrollado como proyecto integrador para la
asignatura de Aplicaciones de Escritorio / Programación Orientada a Objetos.

Grupo: 2°D
Integrantes:
-Erick Manuel Guerrero Guevara
-Francisco Emmanuel Fuentes Pérez
Tecnologías: Java 17+, JavaFX (FXML), Scene Builder.
Persistencia: Archivo local (`.txt` / `.csv`).

-----

## Características (CRUD)

El sistema permite la administración completa de pacientes con las siguientes funciones:

1.  Alta: Registro de nuevos pacientes mediante un formulario validado.
2.  Consulta: Visualización dinámica en un `TableView`.
3.  Actualización: Modificación de datos existentes.
4.  Baja Lógica (Estatus): Cambio de estado entre **ACTIVO** e **INACTIVO** (sin eliminar físicamente el registro de inmediato).
5.  Persistencia:Los datos se guardan automáticamente en un archivo local, permitiendo que la información prevalezca al cerrar la aplicación.

-----

### Validaciones Implementadas

Campos Obligatorios: No se permiten registros vacíos.
Nombre: Mínimo 5 caracteres.
Edad: Rango lógico de 0 a 120 años.
Teléfono: Únicamente dígitos (mínimo 10).
CURP Único:Validación para evitar duplicados en el sistema.

### Funcionalidad Extra

Resumen en Tiempo Real: Contador automático en la pantalla principal que muestra:
* Total de registros.
* Pacientes Activos.
* Pacientes Inactivos.

-----

## Estructura del Proyecto (POO)

El proyecto sigue una estructura organizada basada en responsabilidades:

`model`: Clase `Paciente` con sus atributos y encapsulamiento.
`controller`: Controladores de la interfaz (MainController, FormController).
`service` / `repository`: Lógica de gestión de datos y manejo de lectura/escritura de archivos (I/O).
`view`: Archivos `.fxml` y hojas de estilo `.css`.



##  Ejecución del Proyecto

1.  Clonar el repositorio:
    git clone https://github.com/20253ds098Manu/prog-str-2D-IntegradoraFranYManu.git
2.  Importar en tu IDE: (IntelliJ IDEA, NetBeans o Eclipse).
3.  Configurar JavaFX: Asegúrate de tener configuradagits las librerías de JavaFX y los módulos correspondientes.
4.  Correr la clase: `Main.java` o `App.java`.

-----

##  Git Flow Utilizado

Para el desarrollo de este proyecto se siguió el siguiente flujo de trabajo:

1.  `main`: Rama de producción (entrega final).
2.  `dev`: Rama de integración de funciones.
3.  `ramas-personales`: Trabajo individual de cada integrante antes del merge a `dev`.tegradoraFranYManu