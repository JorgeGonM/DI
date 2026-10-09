package com.clase.modelo;

public class Doctor {
    private String iddoc;
    private String apeldoc;
    private String nomdoc;
    private String movildoc;
    private String emaildoc;
    private Boolean coledoc;
    private String espedoc;

    //Modelo para crear doctor
    public Doctor(String apeldoc, String nomdoc,
            String movildoc, String emaildoc, Boolean coledoc,
            String espedoc) {
        this.apeldoc = apeldoc;
        this.nomdoc = nomdoc;
        this.movildoc = movildoc;
        this.emaildoc = emaildoc;
        this.coledoc = coledoc;
        this.espedoc = espedoc;
    }

    //Modelo para la tabla
    public Doctor(String iddcoc, String apeldoc, String nomdoc,
            String movildoc, String espedoc) {
        this.iddoc = iddcoc;
        this.apeldoc = apeldoc;
        this.nomdoc = nomdoc;
        this.movildoc = movildoc;
        this.espedoc = espedoc;
    }

    public Doctor(String iddoc, String apeldoc, String nomdoc,
            String movildoc, String emaildoc, Boolean coledoc,
            String espedoc) {
        this.apeldoc = apeldoc;
        this.nomdoc = nomdoc;
        this.movildoc = movildoc;
        this.emaildoc = emaildoc;
        this.coledoc = coledoc;
        this.espedoc = espedoc;
    }

    public String getIddoc() {
        return iddoc;
    }

    public String getApeldoc() {
        return apeldoc;
    }

    public String getNomdoc() {
        return nomdoc;
    }

    public String getMovildoc() {
        return movildoc;
    }

    public String getEmaildoc() {
        return emaildoc;
    }

    public Boolean getColedoc() {
        return coledoc;
    }

    public String getEspedoc() {
        return espedoc;
    }

    public void setIddoc(String iddoc) {
        this.iddoc = iddoc;
    }

    public void setApeldoc(String apeldoc) {
        this.apeldoc = apeldoc;
    }

    public void setNomdoc(String nomdoc) {
        this.nomdoc = nomdoc;
    }

    public void setMovildoc(String movildoc) {
        this.movildoc = movildoc;
    }

    public void setemaildoc(String emaildoc) {
        this.emaildoc = emaildoc;
    }

    public void setColedoc(Boolean coledoc) {
        this.coledoc = coledoc;
    }

    public void setEspedoc(String espedoc) {
        this.espedoc = espedoc;
    }

    
    
}
