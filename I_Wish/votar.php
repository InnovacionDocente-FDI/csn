<?php

/*LOGIN*/

$positivo = $_POST['idPositivoGrupo'];
$votado = $_POST['idAlumnoVotado'];
$punt = $_POST['tantoPorCiento'];
$gru = $_POST['idGrupo'];
$votante = $_POST['idAlumnoVotante'];


require_once 'funciones_bd.php';
$db = new funciones_BD();

$idAlumVotante = $db->devuelveidAlumno($votante);
$idAlumVotado = $db->devuelveidAlumnoByNombre($votado);

	if($db->addRepartoGrupo($positivo,$idAlumVotado,$punt,$idAlumVotante)){
		$resultado[]=array("logstatus"=>"1"); //válido
	}else{
		$resultado[]=array("logstatus"=>"0"); //inválido
	}

echo json_encode($resultado);

?>