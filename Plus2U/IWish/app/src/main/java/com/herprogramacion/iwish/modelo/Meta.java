package com.herprogramacion.iwish.modelo;

/**
 * Reflejo de la tabla 'meta' en la base de datos
 */
public class Meta {
    /*
        Atributos
         */
    private String id;
    private String nombre;
    private String grupo;
    private String nombreProfe;
    private String apellidos;
    private String nombreGrupo;
    private String foto;

    public Meta(String id,
                String nombre,
                String grupo,
                String nombreProfe,
                String apellidos,
                String nombreGrupo) {
        this.id = id;
        this.nombre = nombre;
        this.grupo = grupo;
        this.nombreProfe=nombreProfe;
        this.apellidos=apellidos;
        this.nombreGrupo=nombreGrupo;
    }

    public Meta(String foto){
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getNombreProfe() {
        return nombreProfe;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public String getFoto() { return foto;}
}
