package com.clase;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.clase.modelo.Doctor;
import com.clase.modelo.Paciente;
import com.clase.persistencia.DoctorDAOMySQL;
import com.clase.persistencia.PacienteDAOMySQL;
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
    private Button btnguardardoc, btndeldoc, btnLimpiarDoc, btnBuscarDoc;

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


        cargarDoctores();

        //Carga un doctor al seleccionarlo en la tabla en el formaulario para poder modificarlo o eliminarlo

        tablaDoctores.getSelectionModel().selectedItemProperty().addListener((observable, anterior, nuevo) -> {
            if (nuevo != null) {
                cargarDoctor();
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


     @FXML
    private void guardarDoctor(){

        // Comprobamos que se haya intriducido la fecha
        if(nomdoc == null){
            System.out.println("Deber introducir el nombre: ");
            return;
        }
    
        String id = iddoc.getText();
        String apellidos = apeldoc.getText();
        String nombre = nomdoc.getText();

        String telefono = movildoc.getText();
        String email = maildoc.getText();
        Boolean colegiado = null;
        if(coledocsi.isSelected()){
            colegiado = true;
        }else{
            colegiado = false;
        }
        String especialidad = espedoc.getValue();

        //Creamos el objeto paciente
        Doctor doctor = new Doctor(id, apellidos, nombre, telefono, email, colegiado, especialidad);


        if(doctorExiste){
            //Si el doctor ya existe, lo actualizamos en la BBDD
            DoctorDAOMySQL dao = new DoctorDAOMySQL();
            dao.modificarDoctor(doctor.getIddoc(), doctor);
            doctorExiste = false;
            buscaDocId();
        }else{
            //Si el doctor no existe, lo guardamos en la BBDD
            DoctorDAOMySQL dao = new DoctorDAOMySQL();
            dao.guardarDoctor(doctor);
        }

        //Recargamos la tabla de doctores
        cargarDoctores();

        limpiarCampos();

        System.out.println("======== DOCTOR =========");
        System.out.println("ID: " + id);        
        System.out.println("Apellidos: " + apellidos);        
        System.out.println("Nombre: " + nombre);        
        System.out.println("Teléfono: " + telefono);        
        System.out.println("Email: " + email);        
        System.out.println("Colegiado: " + colegiado);        
        System.out.println("Especialidad: " + especialidad);
        System.out.println("===========================");        


    } 


    //Cargar doctores en la tabla
    @FXML 
    private void cargarDoctores(){

        //Creamos el DAO
        DoctorDAOMySQL dao = new DoctorDAOMySQL();

        //Obetenemos los doctores de la base de datos
        List<Doctor> doctores = dao.cargarDoctores();

        //Los mostramos en la tabla
        tablaDoctores.getItems().setAll(doctores);

    }


    @FXML 
    private void eliminarDoctor(){

       Doctor seleccionado = tablaDoctores.getSelectionModel().getSelectedItem();

        if(seleccionado == null) {
            return;
        }

        DoctorDAOMySQL dao = new DoctorDAOMySQL();
        dao.eliminarDoctor(seleccionado.getIddoc());

        //Recargamos la tabla de doctores
        cargarDoctores();

    }


    @FXML 
    private void cargarDoctor(){
        Doctor doctorSelect = tablaDoctores.getSelectionModel().getSelectedItem();

        if(doctorSelect == null){
            return;
        } else {
            doctorExiste = true;
        }

        DoctorDAOMySQL dao = new DoctorDAOMySQL();

        Doctor doctor = dao.buscarDoctor(doctorSelect.getIddoc());

        if(doctor ==null){
            return;
        }

        iddoc.setText(doctor.getIddoc());
        apeldoc.setText(doctor.getApeldoc());
        nomdoc.setText(doctor.getNomdoc());
        movildoc.setText(doctor.getMovildoc());
        maildoc.setText(doctor.getMaildoc());
        if(doctor.getColedoc() != null){
            if(doctor.getColedoc()){
                coledocsi.setSelected(true);
            }else{
                coledocno.setSelected(true);
            }
        }
        //cargarEspecialidades();
        espedoc.setValue(doctor.getEspedoc());

        
    }


    @FXML 
    private void buscaDocId(){

        DoctorDAOMySQL dao = new DoctorDAOMySQL();
        Doctor doctor = dao.buscarDoctor(iddoc.getText());

        if(doctor ==null){
            return;
        }else {
            doctorExiste = true;
        }


        iddoc.setText(doctor.getIddoc());
        apeldoc.setText(doctor.getApeldoc());
        nomdoc.setText(doctor.getNomdoc());
        movildoc.setText(doctor.getMovildoc());
        maildoc.setText(doctor.getMaildoc());
        if(doctor.getColedoc() != null){
            if(doctor.getColedoc()){
                coledocsi.setSelected(true);
            }else{
                coledocno.setSelected(true);
            }
        }
        //cargarEspecialidades();
        espedoc.setValue(doctor.getEspedoc());


        
    }



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
