package com.clase.persistencia;
import java.util.List;

import com.clase.modelo.Doctor;

// Es la interfaz. Solo dice qué operaciones podemos hacer con un doctor, no cómo se hace.

public interface DoctorDAO {
    void guardarDoctor(Doctor doctor);
    List<Doctor> cargarDoctores();
    Doctor buscarDoctor(String iddoc);
    void eliminarDoctor(String id);
    void modificarDoctor(String id, Doctor doctor);
    Doctor buscaDocId(String especialidad);
}
