<?php
$cadena = $_POST['cadena'];
$ap = $_POST['emailAlumno'];

require_once 'funciones_bd.php';
$db = new funciones_BD();

$idAlum = $db->devuelveidAlumno($ap);
$respuesta = $db->getContador($cadena,$idAlum);
	if($respuesta)
			$resultado[]=array("logstatus"=>$respuesta); //válido
	else{
		$resultado[]=array("logstatus"=>"0"); //inválido
		}

echo json_encode($resultado);

?>