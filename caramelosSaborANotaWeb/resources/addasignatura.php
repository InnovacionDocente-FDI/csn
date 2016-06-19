
<?php
session_start();
$nombre = $_POST['nombre'];
$grupo = $_POST['grupo'];
$email = $_SESSION['email'];

//$code = $_POST['code'];

require_once 'funciones_bd.php';
$db = new funciones_BD();

	$code = '111';
	for ($i = 0; $i < 12; ++$i) {
		$digit = rand(0, 9);
		$code .= ($digit < 10 ? $digit : ($digit - 10 + 'a'));
	}
	//$min=111000000000000;
	//$max=111999999999999;
	//$code = (rand() % ($max-$min)) + $min;//
	//$code = rand(111000000000000, 111999999999999);
	if($db->isasignaturaexist($nombre,$grupo, $code)){
		//$resultado[]=array(header("Location: /caramelosconsaboranotaweb/postlogin.php"));
		echo(" Esta asignatura y este grupo ya existen");
	}else{
		if($db->addasignatura($nombre,$grupo,$code)){
			$idenAsignatura=$db->devuelveidAsignatura($code);
			$idProfesor=$db->devuelveIdProfesorFromEmail($email);
			$db->addimparte($idProfesor,$idenAsignatura);
			//$db->addCodigoEnPositivo($code,$idenAsignatura);
			$resultado[]=array(header("Location: /caramelosconsaboranotaweb"));
			//echo(" La asignatura se ha registrado en la base de datos correctamente.");	
		}
		else{
			//$resultado[]=array(header("Location: /caramelosconsaboranotaweb/postlogin.php"));
			echo(" ha ocurrido un error.");
			}		

	}
?>