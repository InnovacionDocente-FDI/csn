<?php
$cadena = $_POST['cadena'];
$ap = $_POST['emailAlumno'];

require_once 'funciones_bd.php';
$db = new funciones_BD();

$idPos = $db->devuelveidPositivo($cadena);
$idAlum = $db->devuelveidAlumno($ap);
$usado = $db->estadoPositivo($idPos);

if($usado == true)
	$resultado[]=array("logstatus"=>"-4"); //inválido
else{
	if($db->updatecodigo($idPos,$idAlum))
			$resultado[]=array("logstatus"=>"-1"); //válido
	else{
		$resultado[]=array("logstatus"=>"0"); //inválido
		}
	}

echo json_encode($resultado);

?>