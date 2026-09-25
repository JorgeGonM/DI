package com.clase.modelo;
import java.time.LocalDate;

public class Paciente {
    private String dnipac;
    private String apelpac;
    private String nompac;
    private String tlfopac;
    private String emailpac;
    private LocalDate nacpac;
    private String dirpac;
    private String cmbpac;
    private String locpac;

    //Modelo para crear paciente
    public Paciente(String dnipac, String apelpac, String nompac,
            String tlfopac, String emailpac, LocalDate nacpac,
            String dirpac, String cmbpac, String locpac) {
        this.dnipac = dnipac;
        this.apelpac = apelpac;
        this.nompac = nompac;
        this.nacpac = nacpac;
        this.tlfopac = tlfopac;
        this.emailpac = emailpac;
        this.dirpac = dirpac;
        this.cmbpac = cmbpac;
        this.locpac = locpac;
    }

    //Modelo para la tabla
    public Paciente(String dnipac, String apelpac, String nompac,
            String tlfopac, String cmbpac, String locpac) {
        this.dnipac = dnipac;
        this.apelpac = apelpac;
        this.nompac = nompac;
        this.tlfopac = tlfopac;
        this.cmbpac = cmbpac;
        this.locpac = locpac;
    }



    public String getDni() {
        return dnipac;
    }

    public void setDni(String dnipac) {
        this.dnipac = dnipac;
    }
    public String getNombre() {
        return nompac;
    }

    public void setNombre(String nompac) {
        this.nompac = nompac;
    }

    public String getApellidos() {
        return apelpac;
    }

    public void setApellidos(String apelpac) {
        this.apelpac = apelpac;
    }

    public String getMovil() {
        return tlfopac;
    }

    public void setMovil(String tlfopac) {
        this.tlfopac = tlfopac;
    }

    public String getDireccion() {
        return dirpac;
    }

    public void setDireccion(String dirpac) {
        this.dirpac = dirpac;
    }

    public LocalDate getNacimiento() {
        return nacpac;
    }

    public void setNacimiento(LocalDate nacpac) {
        this.nacpac = nacpac;
    }

    public String getEmail() {
        return emailpac;
    }

    public void setEmail(String emailpac) {
        this.emailpac = emailpac;
    }

    public String getProvincia() {
        return cmbpac;
    }

    public void setProvincia(String cmbpac) {
        this.cmbpac = cmbpac;
    }

    public String getMunicipio() {
        return locpac;
    }

    public void setMunicipio(String locpac) {
        this.locpac = locpac;
    }
}