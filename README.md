# Directorio de Pacientes - JavaFX CRUD

## Descripción

Este proyecto es una aplicación de escritorio desarrollada en JavaFX que permite administrar un directorio de pacientes para un consultorio pequeño. El sistema permite registrar, consultar, actualizar y eliminar pacientes, además de guardar la información en un archivo local para conservar los datos entre ejecuciones.

## Objetivo

Desarrollar una aplicación con CRUD completo utilizando JavaFX, aplicando programación orientada a objetos, validaciones y persistencia en archivo.

## Tecnologías utilizadas

Java
JavaFX
FXML
Programación orientada a objetos
Archivo .csv para persistencia

## Funcionalidades

### CRUD de pacientes

Alta de pacientes mediante formulario
Visualización en tabla (TableView)
Actualización de datos
Eliminación de pacientes

### Persistencia en archivo

Los datos se guardan en un archivo .csv
Al iniciar el programa se cargan automáticamente
Cada cambio actualiza el archivo

### Validaciones

No permite campos vacíos
CURP única sin duplicados
Nombre mínimo 5 caracteres
Edad entre 0 y 120
Teléfono de 10 dígitos

### Funcionalidades extra

Estatus de paciente ACTIVO / INACTIVO
Contadores en pantalla de total, activos e inactivos

## Interfaz

### Pantalla principal

Tabla de pacientes
Botones de nuevo, cambiar status, eliminar y recargar
Contadores de pacientes

### Formulario

Campos de CURP, nombre, edad, teléfono y alergias
Botones de guardar y cancelar

## Estructura del proyecto

Model: Paciente.java
Repository: CRUDRepository.java
Service: CRUDService.java
Controller: CRUDController.java y formController.java
Views: CRUD-view.fxml y formview.fxml

##  Ejecución del Proyecto

1.  Clonar el repositorio:
    git clone https://github.com/20253ds098Manu/prog-str-2D-IntegradoraFranYManu.git
2.  Importar en tu IDE: (IntelliJ IDEA, NetBeans o Eclipse).
3.  Configurar JavaFX: Asegúrate de tener configuradagits las librerías de JavaFX y los módulos correspondientes.
4.  Correr la clase: `Main.java` o `App.java`.

## Archivo de datos

El sistema utiliza un archivo .csv ubicado en integradora/data/pacientes.csv
Este archivo se crea automáticamente si no existe


## Equipo

Integrante 1: Francisco Emmanuel Fuentes Pérez

Integrante 2: Erick Manuel Guerrero Guevara

## Notas

No se utiliza base de datos
Los datos se conservan al reiniciar
Se aplican validaciones y manejo de errores con try/catch

## Conclusión

Este proyecto permitió aplicar conceptos de JavaFX, manejo de archivos y programación orientada a objetos para construir un sistema funcional de gestión de pacientes
