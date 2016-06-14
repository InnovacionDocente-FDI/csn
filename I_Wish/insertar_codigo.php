<?php
$cadena = $_POST['cadena'];
$ap = $_POST['emailAlumno'];

require_once 'funciones_bd.php';
$db = new funciones_BD();

$idAsig = $db->devuelveidAsignatura($cadena);
$idAlum = $db->devuelveidAlumno($ap);

if($db->isAsistenExist($idAsig,$idAlum))
			$resultado[]=array("logstatus"=>"-3"); //inválido porque la consulta nos devuelve al menos una fila y eso indica que que ese alumno ya esta registrado
												  // en esa asignatura
else{
	if($db->addcodigo($idAsig,$idAlum))
		$resultado[]=array("logstatus"=>"-1"); //válido
	else{
		$resultado[]=array("logstatus"=>"0"); //inválido
		}
	}

echo json_encode($resultado);

?>