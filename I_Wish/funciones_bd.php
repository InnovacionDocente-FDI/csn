
<?php
 
class funciones_BD {
 
    private $db;
 
    // constructor
    function __construct() {
        require_once 'connectbd.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }
 
    // destructor
    function __destruct() {
 
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * agregar nuevo usuario
     */
    public function adduser($nombre,$ap,$hash,$salt,$correo) {
    $result = mysql_query("INSERT INTO alumno(nombre,apellidos,hash,salt,email,foto) VALUES('$nombre', '$ap', '$hash', '$salt', '$correo', NULL)");
        if ($result) {
            return true;
        } else {
            return false;
        }
    } 
 
     /**
     * Verificar si el usuario ya existe por el username
     */
    public function isuserexist($user) {
        $result = mysql_query("SELECT email from alumno WHERE email = '$user'");
        $num_rows = mysql_num_rows($result); //numero de filas retornadas
        if ($num_rows > 0) {
            // el usuario existe 
            return true;
        } else {
            // no existe
            return false;
        }
    } 
	
	/**
     * Devuelve el id del alumno a través del email enviado como parámetro
     */
    public function devuelveidAlumno($email) {
		$result = mysql_query("SELECT id from alumno WHERE email = '$email'"); 
		return mysql_result($result, 0);
		}
		
	/**
     * Devuelve el id del alumno a través del nombre enviado como parámetro
     */
    public function devuelveidAlumnoByNombre($email) {
		$result = mysql_query("SELECT id from alumno WHERE nombre = '$email'"); 
		return mysql_result($result, 0);
		}
		
	/**
     * agregar foto al usuario
     */
    public function addfotoAlumno($id,$foto) {
    $result = mysql_query("UPDATE alumno SET foto = '$foto' WHERE id = '$id'");
        if ($result) {
            return true;
        } else {
            return false;
        }
    } 
//------------------------------------------------------------------------------------------------------------------------------------------------------	
	/**
     * agregar nuevo profesor
     */
    public function addprofesor($nombre,$ap,$hash,$salt,$correo) {
    $result = mysql_query("INSERT INTO profesor(nombreProfe,apellidos,hash,salt,email,foto) VALUES('$nombre', '$ap', '$hash', '$salt', '$correo', NULL)");
        if ($result) {
            return true;
        } else {
            return false;
        }
    } 
 
     /**
     * Verificar si el profesor ya existe por el username
     */
    public function isprofesorexist($user) {
        $result = mysql_query("SELECT email from profesor WHERE email = '$user'");
        $num_rows = mysql_num_rows($result); //numero de filas retornadas
        if ($num_rows > 0) {
            // el usuario existe 
            return true;
        } else {
            // no existe
            return false;
        }
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
   * Login del alumno
   **/
	public function login($user,$passw){
	
	$result1 =  mysql_query("SELECT hash from alumno WHERE email = '$user'");
	$dbhash = mysql_fetch_array($result1);
	$result = crypt($passw, $dbhash[0]);
	if ($result == $dbhash[0])
		return true;
	else
		return false;
		
	}
	
	/**
   * Login del profesor
   **/
	public function loginprofesor($user,$passw){
	
	$result1 =  mysql_query("SELECT hash from profesor WHERE email = '$user'");
	$dbhash = mysql_fetch_array($result1);
	$result = crypt($passw, $dbhash[0]);
	if ($result == $dbhash[0])
		return true;
	else
		return false;
		
	}

//-------------------------------------------------------------------------------------------------------------------------------------------------------
	 /**
     * agregar nueva asignatura
     */
    public function addasignatura($nombre, $grupo, $codigo) {
    $result = mysql_query("INSERT INTO asignatura(nombre,grupo,cadenaRegistro) VALUES('$nombre', '$grupo', '$codigo')");
        // check for successful store
		//var_dump($result);exit;
        if ($result) {
            return true;
        } else {
            return false;
        }
    }
	
	/**
     * Verificar si la asignatura ya existe por la cadenaRegistro
     */
    public function isasignaturaexist($nombre, $grupo, $codigo) {
        $result = mysql_query("SELECT nombre, grupo, cadenaRegistro from asignatura WHERE nombre = '$nombre' and grupo = '$grupo' and cadenaRegistro = '$codigo'");
        $num_rows = mysql_num_rows($result); //numero de filas retornadas
        if ($num_rows > 0) {
            // el usuario existe 
            return true;
        } else {
            // no existe
            return false;
        }
    }
		
	/**
     * Devuelve la asignatura a través de la cadena de registro
     */
    public function devuelveidAsignatura($cadena) {
		$result = mysql_query("SELECT id from asignatura WHERE cadenaRegistro = '$cadena'"); 
		return mysql_result($result, 0);
		}

//----------------------------------------------------------------------------------------------------------------------------------------------------------
	 /**
     * agregar nueva fila a la tabla intermedia imparte
     */
    public function addimparte($id) {
    $result = mysql_query("INSERT INTO imparte(idProfesor, idAsignatura) VALUES(2,'$id')");
        if ($result) {
            return true;
        } else {
            return false;
        }
    }
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * agregar nueva asistencia (equivalente a escanear un código)
     */
    public function addcodigo($nombre,$ap) {
    $result = mysql_query("INSERT INTO asisten(idAsignatura,idAlumno) VALUES('$nombre', '$ap')");
        if ($result) {
            return true;
        } else {
            return false;
        }
    }
	
	/**
     * agregar nueva asistencia de grupo (equivalente a escanear un código de registro de grupo)
     */
    public function addcodigoGrupo($nombre,$ap,$grup) {
    $result = mysql_query("INSERT INTO asisten(idAsignatura,idAlumno, idGrupo) VALUES('$nombre', '$ap', '$grup')");
        if ($result) {
            return true;
        } else {
            return false;
        }
    }
	
	 /**
     * Verificar si el alumno ya esta registrado en la asignatura para la que está escaneando el código QR
     */
    public function isAsistenExist($asig,$user) {
        $result = mysql_query("SELECT idAsignatura, idAlumno from asisten WHERE idAsignatura = '$asig' and idAlumno = '$user'");
        $num_rows = mysql_num_rows($result); //numero de filas retornadas
        if ($num_rows > 0) {
            // el usuario existe 
            return true;
        } else {
            // no existe
            return false;
        }
    } 
	
	/**
     * Verificar si el alumno ya esta registrado en la asignatura y grupo para la que está escaneando el código QR
     */
    public function isAsistenExistGrupo($asig,$user,$grup) {
        $result = mysql_query("SELECT idAsignatura, idAlumno, idGrupo from asisten WHERE idAsignatura = '$asig' and idAlumno = '$user' and idGrupo = '$grup'");
        $num_rows = mysql_num_rows($result); //numero de filas retornadas
        if ($num_rows > 0) {
            // el usuario existe 
            return true;
        } else {
            // no existe
            return false;
        }
    } 

//-----------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * agregar nuevo positivo
     */
    public function addplus($cadena) {
    $result = mysql_query("INSERT INTO positivo(cadena, Usado, idAlumno) VALUES('$nombre', false, null)");
        if ($result) {
            return true;
        } else {
            return false;
        }
    }
	
	/**
     * Actualiza el positivo que ya estaba creado, asignando un id del alumno que ha escaneado el positivo y poniendo el usado a true
     */
    public function updatecodigo($id,$idAlumno) {
    $result = mysql_query("UPDATE positivo SET idAlumno='$idAlumno', Usado=true WHERE id='$id'");
        if ($result) {
            return true;
        } else {
            return false;
        }
    }

	/**
     * Devuelve el positivo a través de la cadena de registro
     */
    public function devuelveidPositivo($cadena) {
		$result = mysql_query("SELECT id from positivo WHERE cadena = '$cadena'"); 
		return mysql_result($result, 0);
		}	
		
	/**
     * Devuelve el estado del positivo que se pasa por id
     */
    public function estadoPositivo($id) {
		$result = mysql_query("SELECT Usado from positivo WHERE id = '$id'"); 
		return mysql_result($result, 0);
		}	
		
	/**
     * Devuelve la cantidad de positivos que tiene el alumno logado para la asignatura seleccionada
     */
    public function getContador($cadena, $idAlum) {
		$result = mysql_query("SELECT COUNT(*) as conteo FROM positivo where idAlumno='$idAlum' and idAsignatura='$cadena' and Usado=1"); 
		return mysql_result($result, 0);
		}
		
//----------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
     * Devuelve el grupo a través de la cadena de registro
     */
    public function devuelveidGrupo($cadena) {
		$result = mysql_query("SELECT id from grupo WHERE cadenaRegistroGrupo = '$cadena'"); 
		return mysql_result($result, 0);
		}
		
	/**
     * Devuelve la asignatura a la que pertenece el grupo
     */
    public function devuelveAsignaturaDelGrupo($cadena) {
		$result = mysql_query("SELECT idAsignatura from grupo WHERE cadenaRegistroGrupo = '$cadena'"); 
		return mysql_result($result, 0);
		}
		
//----------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
     * Devuelve el positivo del grupo a través de la cadena de registro
     */
    public function devuelveidPositivoGrupo($cadena) {
		$result = mysql_query("SELECT id from positivosgrupo WHERE cadenaGrupo = '$cadena'"); 
		return mysql_result($result, 0);
		}
		
	/**
     * Devuelve el id del positivo del grupo a través de la cadena de registro
     */
    public function devuelveidGrupoByCadena($cadena) {
		$result = mysql_query("SELECT idGrupo from positivosgrupo WHERE cadenaGrupo = '$cadena'"); 
		return mysql_result($result, 0);
		}
		
	/**
     * Devuelve el estado del positivo grupal que se pasa por id
     */
    public function estadoPositivoGrupo($id) {
		$result = mysql_query("SELECT usado from positivosgrupo WHERE id = '$id'"); 
		return mysql_result($result, 0);
		}
		
	/**
     * Actualiza el positivo grupal que ya estaba creado, asignando un id del alumno que ha escaneado el positivo y poniendo el usado a true
     */
    public function updatecodigoGrupo($id) {
    $result = mysql_query("UPDATE positivosgrupo SET usado=true WHERE id='$id'");
        if ($result) {
            return true;
        } else {
            return false;
        }
    }
	
	    /**
     * agregar nueva asistencia (equivalente a escanear un código)
     */
    public function addRepartoGrupo($positivo,$votado,$punt,$votante) {
    $result = mysql_query("INSERT INTO repartosGrupo(idPositivoGrupo, idAlumnoVotado, tantoPorCiento, idAlumnoVotante) VALUES('$positivo', '$votado', '$punt', '$votante')");
        if ($result) {
            return true;
        } else {
            return false;
        }
    }
 }
?>