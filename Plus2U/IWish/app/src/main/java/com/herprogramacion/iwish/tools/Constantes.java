package com.herprogramacion.iwish.tools;

/**
 * Clase que contiene los códigos usados en "I Wish" para
 * mantener la integridad en las interacciones entre actividades
 * y fragmentos
 */
public class Constantes {
    /**
     * Transición Home -> Detalle
     */
    public static final int CODIGO_DETALLE = 100;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION = 101;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION2 = 102;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION3 = 103;
    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta carácteristica.
     */
    private static final String PUERTO_HOST = ":20037";
    /**
     * Dirección IP para trabajar en local
     */
    //private static final String IP = "192.168.1.36"; //192.168.1.36
    private static final String IP = "container.fdi.ucm.es";
    /**
     * URLs del Web Service, las comentadas son en local, las otras las del servidor
     */
    //public static final String SIGNUP = "http://" + IP +  "/I_Wish/registro.php";
    public static final String SIGNUP = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/registro.php";
    //public static final String LOGIN = "http://" + IP +  "/I_Wish/acces.php";
    public static final String LOGIN = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/acces.php";
    //public static final String GET_BY_ID = "http://" + IP + "/I_Wish/obtener_meta_por_id.php";
    public static final String GET_BY_ID = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/obtener_meta_por_id.php";
    //public static final String DETALLADO = "http://" + IP + "/I_Wish/obtener_meta_detallado.php";
    public static final String DETALLADO = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/obtener_meta_detallado.php";
    //public static final String INSERT = "http://" + IP +  "/I_Wish/insertar_codigo.php";
    public static final String INSERT = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/insertar_codigo.php";
    //public static final String INSERT_POSITIVO = "http://" + IP +  "/I_Wish/insertar_positivo.php";
    public static final String INSERT_POSITIVO = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/insertar_positivo.php";
    //public static final String INSERT_REGISTROGRUPO = "http://" + IP +  "/I_Wish/insertar_registroGrupo.php";
    public static final String INSERT_REGISTROGRUPO = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/insertar_registroGrupo.php";
    //public static final String INSERT_POSITIVOGRUPO = "http://" + IP +  "/I_Wish/insertar_positivoGrupo.php";
    public static final String INSERT_POSITIVOGRUPO = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/insertar_positivoGrupo.php";
   // public static final String CONTADOR = "http://" + IP +  "/I_Wish/contador_positivos.php";
    public static final String CONTADOR = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/contador_positivos.php";
    //public static final String RANKING_ASIGNATURAS = "http://" + IP +  "/I_Wish/ranking_asignaturas.php";
    public static final String RANKING_ASIGNATURAS = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/ranking_asignaturas.php";
    //public static final String RANKING_MAXIMO = "http://" + IP +  "/I_Wish/ranking_maximo.php";
    public static final String RANKING_MAXIMO = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/ranking_maximo.php";
    //public static final String RANKING_TOTAL = "http://" + IP +  "/I_Wish/ranking_total.php";
    public static final String RANKING_TOTAL = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/ranking_total.php";
    //public static final String GET_GROUP_BY_EMAIL = "http://" + IP + "/I_Wish/obtener_grupos_por_email.php";
    public static final String GET_GROUP_BY_EMAIL = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/obtener_grupos_por_email.php";
    //public static final String DETALLADO_GRUPO = "http://" + IP + "/I_Wish/obtener_grupo_detallado.php";
    public static final String DETALLADO_GRUPO = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/obtener_grupo_detallado.php";
    //public static final String VOTAR = "http://" + IP + "/I_Wish/votar.php";
    public static final String VOTAR = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/votar.php";
    //public static final String INSERT_FOTO = "http://" + IP +  "/I_Wish/insertar_foto.php";
    public static final String INSERT_FOTO = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/insertar_foto.php";
    //public static final String OBTENER_FOTO = "http://" + IP +  "/I_Wish/obtener_foto.php";
    public static final String OBTENER_FOTO = "http://" + IP + PUERTO_HOST + "/caramelosconsaboranotaweb/resources/obtener_foto.php";

    /**
     * Clave para el valor extra que representa al identificador de una asignatura
     */
    public static final String EXTRA_ID = "IDEXTRA";

    /**
     * email del usuario logado
     */
    public static final String NOMBRE_USER = "NOMBREUSER";

    /**
     * contador de positivos del usuario logado en EXTRA_ID (asignatura escogida para visualizar las estadísticas)
     */
    public static final String POS_ALUMNO_LOGADO = "POSITIVOSALUMNOLOGADO";

    /**
     * Clave para el valor extra que representa al identificador de un grupo
     */
    public static final String EXTRA_GRUPO_ID = "IDGRUPOEXTRA";

    /**
     * Clave para identificar el id del positivo grupal escaneado
     */
    public static final String ID_POS_GRUPO = "IDGPOSGRUPO";

}
