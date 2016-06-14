<?php
/**
 * Obtiener las asignaturas a las que asiste un alumno
 * su identificador "email"
 */

require 'Meta.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    if (isset($_GET['email'])) { //isset: Determina si una variable está definida y no es NULL
//Si son pasados varios parámetros, entonces isset() devolverá TRUE únicamente si todos los parámetros están definidos

        // Obtener parámetro email
        $parametro = $_GET['email'];

        // Tratar retorno
        $metas = Meta::getById($parametro);


        if ($metas) {

            $datos["estado"] = "1";
            $datos["meta"] = $metas;
            // Enviar objeto json de la meta
            print json_encode($datos);
        } else {
            // Enviar respuesta de error general
            print json_encode(
                array(
                    'estado' => '2',
                    'mensaje' => 'No se obtuvo el registro'
                )
            );
        }

    } else {
        // Enviar respuesta de error
        print json_encode(
            array(
                'estado' => '3',
                'mensaje' => 'Se necesita un identificador'
            )
        );
    }
}

