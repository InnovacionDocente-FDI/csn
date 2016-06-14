<?php
$cadena = $_POST['cadena'];
$ap = $_POST['emailAlumno'];

require_once 'funciones_bd.php';
$db = new funciones_BD();

$idPosGru = $db->devuelveidPositivoGrupo($cadena);
$idAlum = $db->devuelveidAlumno($ap);
$usadoGrup = $db->estadoPositivoGrupo($idPosGru);
$idGrupo = $db->devuelveidGrupoByCadena($cadena);

if($usadoGrup == true)
	$resultado[]=array("logstatus"=>"-2"); //positivo grupal ya usado
else{
	if($db->updatecodigoGrupo($idPosGru)){
			$resultado[]=array("logstatus"=>$idGrupo); //válido
			}
	else{
		$resultado[]=array("logstatus"=>"0"); //inválido
		}
	}

echo json_encode($resultado);

?>