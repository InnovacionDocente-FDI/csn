<?php
$cadena = $_POST['ruta'];
$ap = $_POST['emailAlumno'];

require_once 'funciones_bd.php';
$db = new funciones_BD();

$idAlum = $db->devuelveidAlumno($ap);

	if($db->addfotoAlumno($idAlum,$cadena))
		$resultado[]=array("logstatus"=>"-1"); //válido
	else{
		$resultado[]=array("logstatus"=>"0"); //inválido
		}

echo json_encode($resultado);

?>