package com.clase;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import com.clase.modelo.Doctor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DoctoresController implements Initializable{
   
    @FXML 
    private TextField iddoc, apeldoc, nomdoc, movildoc, maildoc;

    
    @FXML 
    private ComboBox<String> espedoc;

    @FXML
    private RadioButton coledocsi, coledocno;

    @FXML 
    private Button btnguardardoc, btndeldoc, btnLimpiarPac, btnBuscarPac;

    @FXML
    private TableView<Doctor> tablaDoctores;

    @FXML 
    private TableColumn<Doctor, String> coliddoc, colapeldoc, colnomdoc, colmovildoc, colespedoc;
   
    boolean doctorExiste = false;

    @Override 
    public void initialize(URL url, ResourceBundle rb){

        iddoc.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                
            }
        });

        nomdoc.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                String nombre = letrasCapitales(nomdoc.getText());
                nomdoc.setText(nombre);
            }
        });

        apeldoc.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                String apellidos = letrasCapitales(apeldoc.getText());
                apeldoc.setText(apellidos);
            }
        });

        movildoc.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                comprobarMovil();
            }
        });

        maildoc.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                comprobarEmail();
            }
        });

        cargarEspecialidades();
        

        //Vinculamos las columnas de la tabla con los atributos del modelo Paciente

        coliddoc.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colapeldoc.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colnomdoc.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colmovildoc.setCellValueFactory(new PropertyValueFactory<>("movil"));
        colespedoc.setCellValueFactory(new PropertyValueFactory<>("especialidad"));        


        //cargarDoctores();

        //Carga un paciente al seleccionarlo en la tabla en el formaulario para poder modificarlo o eliminarlo

        tablaDoctores.getSelectionModel().selectedItemProperty().addListener((observable, anterior, nuevo) -> {
            if (nuevo != null) {
                //cargarDoctor();
            }
        });



    }


    @FXML 
    private void cargarEspecialidades(){
        
        // Abrimos el fichero JSON que está dentro de resources
        // usamos la clase de java InputStrem que lee datos en este caso de un fichero

        InputStream is = getClass().getResourceAsStream("/com/clase/data/especialidades.json");

 
        // Leemos el JSON y lo convertimos en un objeto JsonObject
        JsonObject json = JsonParser.parseReader(
                new InputStreamReader(is)
        ).getAsJsonObject();

 
        // Obtenemos el array "provincias" del JSON
        JsonArray especialidades = json.getAsJsonArray("especialidades");

        // Recorremos todas las provincias
        for (var especialidad : especialidades) {

            // Cada elemento del array es un objeto JSON
            JsonObject p = especialidad.getAsJsonObject();

            // Obtenemos el nombre de la provincia
            // y lo añadimos al ComboBox
            espedoc.getItems().add(
                    p.get("nombre").getAsString()
            );
        }
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
        String movil = movildoc.getText().trim().toUpperCase();
        if(movil.isEmpty())
            return;

        if(validarMovil(movil)){
            movildoc.setStyle("");
            movildoc.setText(movil);
        }else{
            movildoc.setStyle("-fx-border-color: red;");
            movildoc.setText("");
        }
        
    }

    @FXML
    private boolean validarMovil(String movil){
        
        return movil.matches("[67][0-9]{8}");
    }
     

    //Comprobar email
    @FXML
    private void comprobarEmail(){
        String email = maildoc.getText().trim();
        if(email.isEmpty())
            return;

        if(validarEmail(email)){
            maildoc.setStyle("");
            maildoc.setText(email);
        }else{
            maildoc.setStyle("-fx-border-color: red;");
            maildoc.setText("");
        }
        
    }

    @FXML
    private boolean validarEmail(String email){
        
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");    
    
    }


    /* @FXML
    private void guardarDoctor(){


    
        String id = iddoc.getText();
        String apellidos = apeldoc.getText();
        String nombre = nomdoc.getText();

        String telefono = movildoc.getText();
        String email = maildoc.getText();
        String  = dirpac.getText();

        String especialidad = espedoc.getValue();

        //Creamos el objeto paciente
        Paciente paciente = new Paciente(dni, apellidos, nombre, telefono, email, fechaNacimiento, direcion, provincia, municipio);



        if(pacienteExiste){
            //Si el paciente ya existe, lo actualizamos en la BBDD
            PacienteDAOMySQL dao = new PacienteDAOMySQL();
            dao.modificarPaciente(paciente.getDni(), paciente);
            pacienteExiste = false;
            buscaPacDni();
        }else{
            //Si el paciente no existe, lo guardamos en la BBDD
            PacienteDAOMySQL dao = new PacienteDAOMySQL();
            dao.guardarPaciente(paciente);
        }

        //Recargamos la tabla de pacientes
        cargarPacientes();

        limpiarCampos();

        System.out.println("======== PACIENTE =========");
        System.out.println("DNI: " + dni);        
        System.out.println("Apellidos: " + apellidos);        
        System.out.println("Nombre: " + nombre);        
        System.out.println("Fecha nacimiento: " + fechaNacimiento);        
        System.out.println("Teléfono: " + telefono);        
        System.out.println("Email: " + email);        
        System.out.println("Dirección: " + direcion);        
        System.out.println("Provincia: " + provincia);        
        System.out.println("Municipio: " + municipio);        
        System.out.println("===========================");        


    } */


   /*  //Cargar pacientes en la tabla
    @FXML 
    private void cargarPacientes(){

        //Creamos el DAO
        PacienteDAOMySQL dao = new PacienteDAOMySQL();

        //Obetenemos los pacientes de la base de datos
        List<Paciente> pacientes = dao.cargarPacientes();

        //Los mostramos en la tabla
        tablaPacientes.getItems().setAll(pacientes);

    }
 */

    /* @FXML 
    private void eliminarPaciente(){

       Paciente seleccionado = tablaPacientes.getSelectionModel().getSelectedItem();

        if(seleccionado == null) {
            return;
        }

        PacienteDAOMySQL dao = new PacienteDAOMySQL();
        dao.eliminarPaciente(seleccionado.getDni());

        //Recargamos la tabla de pacientes
        cargarPacientes();

    } */


    /* @FXML 
    private void cargarPaciente(){
        Paciente pacienteSelect = tablaPacientes.getSelectionModel().getSelectedItem();

        if(pacienteSelect == null){
            return;
        } else {
            pacienteExiste = true;
        }

        PacienteDAOMySQL dao = new PacienteDAOMySQL();

        Paciente paciente = dao.buscarPaciente(pacienteSelect.getDni());

        if(paciente ==null){
            return;
        }

        iddoc.setText(paciente.getDni());
        apeldoc.setText(paciente.getApellidos());
        nomdoc.setText(paciente.getNombre());
        movildoc.setText(paciente.getMovil());
        maildoc.setText(paciente.getEmail());
        nacpac.setValue(paciente.getNacimiento());
        dirpac.setText(paciente.getDireccion());
        espedoc.setValue(paciente.getProvincia());
        cargarMunicipios();
        munipac.setValue(paciente.getMunicipio());
        
    } */


    /* @FXML 
    private void buscaPacDni(){

        PacienteDAOMySQL dao = new PacienteDAOMySQL();
        Paciente paciente = dao.buscaPacDni(iddoc.getText());

        if(paciente ==null){
            return;
        }else {
            pacienteExiste = true;
        }


        iddoc.setText(paciente.getDni());
        apeldoc.setText(paciente.getApellidos());
        nomdoc.setText(paciente.getNombre());
        movildoc.setText(paciente.getMovil());
        maildoc.setText(paciente.getEmail());
        nacpac.setValue(paciente.getNacimiento());
        dirpac.setText(paciente.getDireccion());
        espedoc.setValue(paciente.getProvincia());
        cargarMunicipios();
        munipac.setValue(paciente.getMunicipio());
        
    } */



    @FXML
    private void limpiarCampos() {
        iddoc.clear();
        apeldoc.clear();
        nomdoc.clear();
        movildoc.clear();
        maildoc.clear();
        espedoc.getSelectionModel().clearSelection();
    } 


    
    


}
