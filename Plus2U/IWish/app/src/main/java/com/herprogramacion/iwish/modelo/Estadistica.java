package com.herprogramacion.iwish.modelo;

/**
 * Created by Nuria on 27/02/2016.
 */
public class Estadistica {
    /*
        Atributos
         */
    private String PositivosAlumno;
    private String email;
    private String Maximo;
    private String Total;


    public Estadistica(String PositivosAlumno,String email) {
        this.PositivosAlumno = PositivosAlumno;
        this.email = email;
    }

    public Estadistica(String Maximo) {
        this.Maximo = Maximo;
    }

    public String getPositivosAlumno() {
        return PositivosAlumno;
    }

    public String getEmailAlumno() {
        return email;
    }

    public String getMaximo(){
        return Maximo;
    }

    public String getTotal(){
        return Total;
    }
}
