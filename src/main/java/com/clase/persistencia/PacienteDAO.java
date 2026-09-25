package com.clase.persistencia;
import java.util.List;

import com.clase.modelo.Paciente;

// Es la interfaz. Solo dice qué operaciones podemos hacer con un paciente, no cómo se hace.

public interface PacienteDAO {
    void guardarPaciente(Paciente paciente);
    List<Paciente> cargarPacientes();
}
