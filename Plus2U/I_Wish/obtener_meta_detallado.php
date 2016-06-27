<?php
/**
 * Obtiene el detalle de una meta especificada por
 * su identificador "idMeta"
 */

require 'Meta.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
     if (isset($_GET['emailUser'])) {
		if (isset($_GET['idMeta'])) {

        // Obtener parámetro idMeta
        $parametro = $_GET['idMeta'];
		
		        // Obtener parámetro idMeta
        $parametro2 = $_GET['emailUser'];

        // Tratar retorno
        $retorno = Meta::getByIdDetallado($parametro, $parametro2);


        if ($retorno) {

            $meta["estado"] = "1";
            $meta["meta"] = $retorno;
            // Enviar objeto json de la meta
            print json_encode($meta);
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
	else {
        // Enviar respuesta de error
        print json_encode(
            array(
                'estado' => '4',
                'mensaje' => 'Se necesita un email de usuario válido'
            )
        );
    }
}

