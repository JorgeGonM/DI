
package com.clase;

import java.net.URL;
import java.util.ResourceBundle;

import com.clase.modelo.Doctor;
import com.clase.modelo.Paciente;
import com.clase.modelo.Cita;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.DatePicker;


public class CitasController implements Initializable {

    @FXML
    private TextField idcita, citaiddoc, citadnipac, motivo;

    @FXML
    private TextArea diagnostico, tratamiento;

    @FXML 
    private DatePicker fechacita;

    @FXML 
    private ComboBox<String> horacita;

    @FXML
    private CheckBox chkatendida, chkpendiente, chkanulada;

    @FXML
    private TableView<Cita> tablaCitas;

    @FXML 
    private TableColumn<Doctor, String> colidcita, colcitaiddoc, colespec, colfechacita, colhoracita;

    // Recibe el paciente seleccionado
    public void cargarDatosPaciente(Paciente paciente) {

        citadnipac.setText(paciente.getDni());

    }

   /* public void cargarDatosDoctor(Doctor doctor) {
        // Aquí puedes implementar la lógica para cargar los datos del doctor seleccionado
        // Por ejemplo, podrías mostrar el nombre del doctor en un campo de texto
        // o realizar cualquier otra acción necesaria.
        citaiddoc.setText(String.valueOf(doctor.getID()));
        
    } */

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Inicialización de la vista de citas

    }
 

    @FXML
    private void gestionarExclusion(ActionEvent event) {
        CheckBox seleccionado = (CheckBox) event.getSource();
        
        if (seleccionado == chkpendiente && chkpendiente.isSelected()) {
            chkatendida.setSelected(false);
            chkanulada.setSelected(false);
        } else if (seleccionado == chkatendida && chkatendida.isSelected()) {
            chkpendiente.setSelected(false);
            chkanulada.setSelected(false);
        } else if (seleccionado == chkanulada && chkanulada.isSelected()) {
            chkpendiente.setSelected(false);
            chkatendida.setSelected(false);
        }
    }
}
