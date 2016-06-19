
<?php
session_start();

$idAsig = $_POST['asignaturaId'];
$asig = $_POST['asignaturaNombre'];
$email = $_SESSION['email'];


	require_once 'funciones_bd.php';
	$db = new funciones_BD();
	//if($db->isuserexist($usuario)){
		if($db->deleteAsignatura($idAsig)){
			$resultado[]=array(header("Location: /caramelosconsaboranotaweb/index.php"));
		}else{
			$usuario ="";
			$resultado[]=array(header("Location: /caramelosconsaboranotaweb/index.php?error=falloBorrar"));
		}
	echo json_encode($resultado);
?>
