<?php
$cadena = $_POST['cadena'];
$ap = $_POST['emailAlumno'];

require_once 'funciones_bd.php';
$db = new funciones_BD();

$idGru = $db->devuelveidGrupo($cadena);
$idAlum = $db->devuelveidAlumno($ap);
$idAsig = $db->devuelveAsignaturaDelGrupo($cadena);

if($db->isAsistenExistGrupo($idAsig,$idAlum,$idGru))
			$resultado[]=array("logstatus"=>"-5"); //inválido porque la consulta nos devuelve al menos una fila y eso indica que que ese alumno ya esta registrado
												  // en esa asignatura y grupo
else{
	if($db->addcodigoGrupo($idAsig,$idAlum,$idGru))
		$resultado[]=array("logstatus"=>"-1"); //válido
	else{
		$resultado[]=array("logstatus"=>"0"); //inválido
		}
	}

echo json_encode($resultado);

?>