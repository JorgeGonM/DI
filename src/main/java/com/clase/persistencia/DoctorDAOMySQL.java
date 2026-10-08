package com.clase.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.clase.modelo.Doctor;

public class DoctorDAOMySQL implements DoctorDAO { // Implementación de la interfaz
    
    @Override 
    public void guardarDoctor(Doctor doctor){
        
        String sql = "INSERT INTO doctores " 
                    + "(iddoc, apeldoc, nomdoc, movildoc, maildoc, coledoc, espedoc) "
                    + "VALUES (?,?,?,?,?,?,?)";

        try (Connection conexion = ConexionMySQL.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setString(1, doctor.getIddoc());
            ps.setString(2, doctor.getApeldoc());                
            ps.setString(3, doctor.getNomdoc());                
            ps.setString(4, doctor.getMovildoc());                
            ps.setString(5, doctor.getMaildoc());                
            ps.setBoolean(6, doctor.getColedoc());                
            ps.setString(7, doctor.getEspedoc());    
            
            ps.executeUpdate();

            System.out.println("Doctor guardado correctamente.");
                
            
        } catch (SQLException e) {
            System.out.println("Error al guardar el doctor: " + e.getMessage());
        }

    }

    // seleccionar doctores de la bbdd
     @Override
    public List<Doctor> cargarDoctores() {

        List<Doctor> doctores = new ArrayList<>();

        // Solo obtenemos los campos que necesitamos para la tabla
        String sql = "SELECT iddoc, apeldoc, nomdoc, movildoc, "
                + "espedoc "
                + "FROM doctores "
                + "ORDER BY apeldoc, nomdoc";

        try (Connection conexion = ConexionMySQL.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Doctor doctor = new Doctor(
                        rs.getString("iddoc"),
                        rs.getString("apeldoc"),
                        rs.getString("nomdoc"),
                        rs.getString("movildoc"),
                        rs.getString("espedoc")
                );

                doctores.add(doctor);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar los doctores: " + e.getMessage());
        }

        //devuelve los doctores que ha cargado de la bbdd
        return doctores;

    }



    @Override 
    public void eliminarDoctor(String id) {
        String sql = "DELETE FROM doctores WHERE iddoc = ?";

        try (Connection conexion = ConexionMySQL.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, id);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Doctor eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún doctor con el ID proporcionado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar el doctor: " + e.getMessage());
        }
    }


    @Override 
    public Doctor buscarDoctor(String id) {
        String sql = "SELECT iddoc, apeldoc, nomdoc, movildoc, "
                + " maildoc, coledoc, espedoc "
                + " FROM doctores "
                + " WHERE iddoc = ?";


        try (Connection conexion = ConexionMySQL.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()){

                if (rs.next()) {
                    Doctor doctor = new Doctor(
                            rs.getString("iddoc"),
                            rs.getString("apeldoc"),
                            rs.getString("nomdoc"),
                            rs.getString("movildoc"),
                            rs.getString("maildoc"),
                            rs.getBoolean("coledoc"),
                            rs.getString("espedoc"));

                    return doctor;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el doctor: " + e.getMessage());
        }
        return null;
    }


    @Override 
    public void modificarDoctor(String id, Doctor doctor) {
        String sql = "UPDATE doctores SET "
                + "apeldoc = ?, "
                + "nomdoc = ?, "
                + "movildoc = ?, "
                + "maildoc = ?, "
                + "coledoc = ?, "
                + "espedoc = ? "
                + "WHERE iddoc = ?";

        try (Connection conexion = ConexionMySQL.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, doctor.getApeldoc());
            ps.setString(2, doctor.getNomdoc());
            ps.setString(3, doctor.getMovildoc());
            ps.setString(4, doctor.getMaildoc());
            ps.setBoolean(5, doctor.getColedoc());
            ps.setString(6, doctor.getEspedoc());
            ps.setString(7, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Doctor modificado correctamente.");
            } else {
                System.out.println("No se encontró ningún doctor con el ID proporcionado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al modificar el doctor: " + e.getMessage());
        }
    }



    @Override 
    public Doctor buscaDocId(String id) {
        String sql = "SELECT iddoc, apeldoc, nomdoc, movildoc, "
                + " maildoc, coledoc, espedoc "
                + " FROM doctores "
                + " WHERE iddoc = ?";

        try (Connection conexion = ConexionMySQL.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()){

                if (rs.next()) {
                    Doctor doctor = new Doctor(
                            rs.getString("iddoc"),
                            rs.getString("apeldoc"),
                            rs.getString("nomdoc"),
                            rs.getString("movildoc"),
                            rs.getString("maildoc"),
                            rs.getBoolean("coledoc"),
                            rs.getString("espedoc"));

                    return doctor;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el doctor: " + e.getMessage());
        }

        return null;
    }

                    




}
