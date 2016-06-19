
<?php
session_start();
$_SESSION['email'] = $_POST['email'];
/*LOGIN*/
$usuario = $_POST['email'];
$passw = $_POST['password'];


	require_once 'funciones_bd.php';
	$db = new funciones_BD();
	//if($db->isuserexist($usuario)){
		if($db->loginprofesor($usuario,$passw)){
			//header("Location: /caramelosconsaboranotaweb/session.php");
			//$resultado[]=array("logstatus"=>"1"); //válido
			$resultado[]=array(header("Location: /caramelosconsaboranotaweb/index.php"));
		}else{
			//header("Location: /caramelosconsaboranotaweb/index.html");
			//$resultado[]=array("logstatus"=>"0"); //inválido
			$usuario ="";
			$resultado[]=array(header("Location: /caramelosconsaboranotaweb/cerrarsesion.php"));
		}
	/*}else{
		//el usuario no esta en la ase de datos
		$usuario ="";
		$resultado[]=array(header("Location: /caramelosconsaboranotaweb/cerrarsesion.php"));
	}*/
	echo json_encode($resultado);
?>
