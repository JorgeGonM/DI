package com.clase;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.clase.modelo.Paciente;
import com.clase.persistencia.PacienteDAOMySQL;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class PacientesController implements Initializable{
   
    @FXML 
    private TextField dnipac, apelpac, nompac, tlfopac, emailpac, dirpac;

    @FXML 
    private DatePicker nacpac;
    
    @FXML 
    private ComboBox<String> cmbpac, locpac;

    @FXML 
    private Button btnsavepac, btnmodifpac, btndelpac;
   


    @Override 
    public void initialize(URL url, ResourceBundle rb){

        cargarProvincias();
        cmbpac.setOnAction(e -> cargarMunicipios());

        dnipac.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                comprobarDNI();
            }
        });

        nompac.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                String nombre = letrasCapitales(nompac.getText());
                nompac.setText(nombre);
            }
        });

        apelpac.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                String apellidos = letrasCapitales(apelpac.getText());
                apelpac.setText(apellidos);
            }
        });

        tlfopac.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                comprobarMovil();
            }
        });

        emailpac.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                comprobarEmail();
            }
        });


    }

    //Comprobar DNI
    @FXML 
    private void comprobarDNI(){
        String dni = dnipac.getText().trim().toUpperCase();
        if(dni.isEmpty())
            return;

        if(validarDNI(dni)){
            dnipac.setStyle("");
            dnipac.setText(dni);
        }else{
            dnipac.setStyle("-fx-border-color: red;");
            dnipac.setText("");
        }
        
    }

    //Validar DNI
    private boolean validarDNI(String documento){
        if (documento.matches("\\d{8}[A-Z]")) {

        int numero = Integer.parseInt(documento.substring(0, 8));
        char letra = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numero % 23);

        return letra == documento.charAt(8);
        }

        if (documento.matches("[XYZ]\\d{7}[A-Z]")) {

            String nie = documento
                    .replace("X", "0")
                    .replace("Y", "1")
                    .replace("Z", "2");

            int numero = Integer.parseInt(nie.substring(0, 8));
            char letra = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numero % 23);

            return letra == documento.charAt(8);
        }

        return false;
    }

    //Letras Capitales
    private String letrasCapitales(String texto){

        String[] palabras = texto.toLowerCase().trim().split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for(String palabra: palabras){
            if (!palabra.isEmpty()) {
                resultado.append(Character.toUpperCase(palabra.charAt(0))).append(palabra.substring(1)).append(" ");
            }
        }

        return resultado.toString().trim();

    }

    //Comprobar móvil
    @FXML
    private void comprobarMovil(){
        String movil = tlfopac.getText().trim().toUpperCase();
        if(movil.isEmpty())
            return;

        if(validarMovil(movil)){
            tlfopac.setStyle("");
            tlfopac.setText(movil);
        }else{
            tlfopac.setStyle("-fx-border-color: red;");
            tlfopac.setText("");
        }
        
    }

    @FXML
    private boolean validarMovil(String movil){
        
        return movil.matches("[67][0-9]{8}");
    }
     
    //Comprobar email
    @FXML
    private void comprobarEmail(){
        String email = emailpac.getText().trim();
        if(email.isEmpty())
            return;

        if(validarEmail(email)){
            emailpac.setStyle("");
            emailpac.setText(email);
        }else{
            emailpac.setStyle("-fx-border-color: red;");
            emailpac.setText("");
        }
        
    }

    @FXML
    private boolean validarEmail(String email){
        
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");    
    
    }

    @FXML 
    private void cargarProvincias(){
        
        // Abrimos el fichero JSON que está dentro de resources
        // usamos la clase de java InputStrem que lee datos en este caso de un fichero

        InputStream is = getClass().getResourceAsStream("/com/clase/data/municipios.json");

 
        // Leemos el JSON y lo convertimos en un objeto JsonObject
        JsonObject json = JsonParser.parseReader(
                new InputStreamReader(is)
        ).getAsJsonObject();

 
        // Obtenemos el array "provincias" del JSON
        JsonArray provincias = json.getAsJsonArray("provincias");

        // Recorremos todas las provincias
        for (var provincia : provincias) {

            // Cada elemento del array es un objeto JSON
            JsonObject p = provincia.getAsJsonObject();

            // Obtenemos el nombre de la provincia
            // y lo añadimos al ComboBox
            cmbpac.getItems().add(
                    p.get("nm").getAsString()
            );
        }
    }

    @FXML
    private void cargarMunicipios() {

        // Abrimos de nuevo el fichero JSON
        InputStream is = getClass()
                .getResourceAsStream("/com/clase/data/municipios.json");

        // Convertimos el contenido del fichero en un JsonObject
        JsonObject json = JsonParser.parseReader(
                new InputStreamReader(is)
        ).getAsJsonObject();

        // Obtenemos los dos arrays que necesitamos
        JsonArray provincias = json.getAsJsonArray("provincias");
        JsonArray municipios = json.getAsJsonArray("municipios");

        // Obtenemos el nombre de la provincia seleccionada
        String nombreProvincia = cmbpac.getValue();

        // Variable donde guardaremos el código de la provincia
        String idProvincia = "";

        // Recorremos las provincias
        for (var provincia : provincias) {

            JsonObject p = provincia.getAsJsonObject();

            // Comprobamos si es la provincia seleccionada
            if (p.get("nm").getAsString().equals(nombreProvincia)) {

                // Obtenemos su código
                idProvincia = p.get("id").getAsString();

                // Ya hemos encontrado la provincia
                break;
            }
        }

        // Eliminamos los municipios que pudiera haber
        // de una selección anterior muy importante sino agrega municipios
        locpac.getItems().clear();

        // Recorremos todos los municipios
        for (var municipio : municipios) {

            JsonObject m = municipio.getAsJsonObject();

            // Comprobamos los dos primeros caracteres del código
            if (m.get("id").getAsString().startsWith(idProvincia)) {

                // Si pertenecen a la provincia,
                // añadimos su nombre al ComboBox
                locpac.getItems().add(
                        m.get("nm").getAsString()
                );
            }
        }

    }


    @FXML
    private void guardarPaciente(){

        // Comprobamos que se haya intriducido la fecha
        if(nacpac.getValue() == null){
            System.out.println("Deber introducir la fecha de nacimiento: ");
            return;
        }

    
        String dni = dnipac.getText();
        String apellidos = apelpac.getText();
        String nombre = nompac.getText();

        LocalDate fechaNacimiento = nacpac.getValue();

        String telefono = tlfopac.getText();
        String email = emailpac.getText();
        String direcion = dirpac.getText();

        String provincia = cmbpac.getValue();
        String localidad = locpac.getValue();

        //Creamos el objeto paciente
        Paciente paciente = new Paciente(dni, apellidos, nombre, telefono, email, fechaNacimiento, direcion, provincia, localidad);


        //Creamos el DAO y guardamos el paciente en MySQL
        PacienteDAOMySQL dao = new PacienteDAOMySQL();
        dao.guardarPaciente(paciente);




    /*
        System.out.println("======== PACIENTE =========");
        System.out.println("DNI: " + dni);        
        System.out.println("Apellidos: " + apellidos);        
        System.out.println("Nombre: " + nombre);        
        System.out.println("Fecha nacimiento: " + fechaNacimiento);        
        System.out.println("Teléfono: " + telefono);        
        System.out.println("Email: " + email);        
        System.out.println("Dirección: " + direcion);        
        System.out.println("Provincia: " + provincia);        
        System.out.println("Localidad: " + localidad);        
        System.out.println("===========================");        
      */


    }


    //Cargar pacientes en la tabla
    @FXML 
    private void cargarPacientes(){

        //Creamos el DAO
        PacienteDAOMySQL dao = new PacienteDAOMySQL();

        //Obetenemos los pacientes de la base de datos
        List<Paciente> pacientes = dao.cargarPacientes();

        //Los mostramos en la tabla
        tablaPacientes.getItems().setAll(pacientes);

    }
    


}
