package com.herprogramacion.iwish.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Reflejo de la tabla 'grupo' en la base de datos
 */
public class Grupo implements Serializable {
    /*
        Atributos
         */
    private String id;
    private String nombreAsignatura;
    private String nombreGrupo;
    private String sumaPorAlumno;
    private String idAlumnoVotado;
    private String nombre; //nombre del alumno votante

    public  Grupo(){}

    public Grupo(String id, String nombreAsignatura,String nombreGrupo, String sumaPorAlumno, String idAlumnoVotado, String nombre) {
        this.id = id;
        this.nombreAsignatura = nombreAsignatura;
        this.nombreGrupo = nombreGrupo;
        this.sumaPorAlumno = sumaPorAlumno;
        this.idAlumnoVotado = idAlumnoVotado;
        this.nombre = nombre;
    }

    public String getId() { return id;}

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public String getSumaPorAlumno() {
        return sumaPorAlumno;
    }

    public String getIdAlumnoVotado() {
        return idAlumnoVotado;
    }

    public String getNombre() {return nombre;}

}
