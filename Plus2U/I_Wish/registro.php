<?php
$nombre = $_POST['nombre'];
$ap = $_POST['apellidos'];
$password = $_POST['password'];
$correo = $_POST['email'];
// Validar que $nombre est� disponible, que si contenga
// un rango de letras, numeros, etc etc.. y luego:
// Generamos un salt aleatoreo, de 22 caracteres para Bcrypt

require_once 'funciones_bd.php';
$db = new funciones_BD();

$salt = substr(base64_encode(openssl_random_pseudo_bytes('30')), 0, 22);
// A Crypt no le gustan los '+' as� que los vamos a reemplazar por puntos.
$salt = strtr($salt, array('+' => '.')); 
// Generamos el hash
$hash = crypt($password, '$2y$10$' . $salt);
// Guardamos los datos en la base de datos

	if($nombre != NULL && $ap!= NULL && $hash != NULL && $salt !=NULL && $correo != NULL){
		if($db->isuserexist($correo))
			$resultado[]=array("logstatus"=>"2"); //inv�lido porque el email ya existe en la bbdd
		else{
			if($db->adduser($nombre,$ap,$hash,$salt,$correo))
				$resultado[]=array("logstatus"=>"1"); //v�lido
			else
				$resultado[]=array("logstatus"=>"0"); //inv�lido		
		}			
	}

echo json_encode($resultado);

?>