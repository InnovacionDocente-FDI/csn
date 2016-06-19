<?php

/*LOGIN*/

$usuario = $_POST['email'];
$passw = $_POST['password'];


require_once 'funciones_bd.php';
$db = new funciones_BD();

	if($db->loginprofesor($usuario,$passw)){
		$resultado[]=array("logstatus"=>"1"); //válido
	}else{
		$resultado[]=array("logstatus"=>"0"); //inválido
	}

echo json_encode($resultado);

?>
