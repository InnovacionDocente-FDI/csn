<?php

/**
 * Representa el la estructura de las metas
 * almacenadas en la base de datos
 */
require 'Database.php';

class Meta
{
    function __construct()
    {
    }

    /**
     * Retorna en la fila especificada de la tabla 'meta'
     *
     * @param $idMeta Identificador del registro
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM asignatura";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute();

            return $comando->fetchAll(PDO::FETCH_ASSOC);

        } catch (PDOException $e) {
            return false;
        }
    }

    /**
     * Obtiene los campos de una meta con un identificador
     * determinado
     *
     * @param $idMeta Identificador de la meta
     * @return mixed
     */
    public static function getById($email)
    {
        // Consulta de la meta
        $consulta = "SELECT asignatura.* FROM asisten LEFT OUTER JOIN alumno ON (asisten.idAlumno = alumno.id) LEFT OUTER JOIN asignatura ON 
					(asisten.idAsignatura = asignatura.id) where email = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($email));

            return $comando->fetchAll(PDO::FETCH_ASSOC);
			//fetchAll — Devuelve un array que contiene todas las filas del conjunto de resultados

        } catch (PDOException $e) {
            return false;
        }
    }
	
	/**
     * Obtiene los campos de una meta con un identificador
     * determinado
     *
     * @param $idMeta Identificador de la meta
     * @return mixed
     */
    public static function getByIdDetallado($idMeta, $idUser)
    {
        // Consulta de la meta
		$consulta = "select asig.id, asig.nombre, asig.grupo, p.nombreProfe, p.apellidos, g.nombreGrupo
					from asignatura as asig
					left join imparte as imp on asig.id = imp.idAsignatura 
					left join profesor as p on imp.idProfesor = p.id
					left join asisten as asis on asis.idAsignatura = asig.id 
					left join grupo as g on g.idasignatura = asig.id and g.id = asis.idGrupo
					left join alumno as alum on asis.idAlumno = alum.id 
					where asig.id = '$idMeta' and alum.email = '$idUser'";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($idMeta));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;

        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            return -1;
        }
    }
	
	/**
     * Obtiene el id del alumno en función del email que se pasa como parámetro
     *
     * @param $email Email del alumno
     * @return mixed
     */
    public static function getByIdAlumno($email)
    {
        // Consulta de la meta
		$consulta = "select id from alumno where email = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($email));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;

        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            return -1;
        }
    }
	
		/**
     * Obtiene el id del alumno en función del email que se pasa como parámetro
     *
     * @param $email Email del alumno
     * @return mixed
     */
    public static function getRankingPorAsignatura($idAsig)
    {
        // Consulta de la meta
        $consulta = "select count(*) as PositivosAlumno, email from positivo, alumno where positivo.idAsignatura = ? AND positivo.Usado=1 and 
		positivo.idAlumno = alumno.id group by positivo.idAlumno ORDER BY PositivosAlumno DESC";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($idAsig));

            return $comando->fetchAll(PDO::FETCH_ASSOC);
			//fetchAll — Devuelve un array que contiene todas las filas del conjunto de resultados

        } catch (PDOException $e) {
            return false;
        }
    }
	
			/**
     * Obtiene el id del alumno en función del email que se pasa como parámetro
     *
     * @param $email Email del alumno
     * @return mixed
     */
    public static function getRankingMaximo($idAsig)
    {
        // Consulta de la meta
        $consulta = "SELECT MAX(PositivosAlumno) as Maximo FROM (SELECT count(*) as PositivosAlumno, idAlumno from positivo where positivo.idAsignatura= ? 
		AND positivo.Usado=1 group by positivo.idAlumno) AS counts";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($idAsig));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;

        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            return -1;
        }
    }
	
				/**
     * Obtiene el id del alumno en función del email que se pasa como parámetro
     *
     * @param $email Email del alumno
     * @return mixed
     */
    public static function getRankingTotal($idAsig)
    {
        // Consulta de la meta
        $consulta = "select count(*) as Total from positivo where idAsignatura= ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($idAsig));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;

        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            return -1;
        }
    }
	
	    /**
     * Obtiene los campos de una meta con un identificador
     * determinado
     *
     * @param $idMeta Identificador de la meta
     * @return mixed
     */
    public static function getGroupByEmail($email)
    {
        // Consulta de la meta
        $consulta = "SELECT grupo.id, grupo.nombreGrupo, asignatura.nombre as nombreAsignatura FROM grupo, asisten, asignatura, alumno WHERE alumno.email = ? and 
					 alumno.id = asisten.idAlumno and asisten.idGrupo = grupo.id and grupo.idAsignatura = asignatura.id";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($email));

            return $comando->fetchAll(PDO::FETCH_ASSOC);
			//fetchAll — Devuelve un array que contiene todas las filas del conjunto de resultados

        } catch (PDOException $e) {
            return false;
        }
    }
	
		    /**
     * Obtiene los campos de una meta con un identificador
     * determinado
     *
     * @param $idMeta Identificador de la meta
     * @return mixed
     */
    public static function getSumatorioGrupoPorIntegrante($email)
    {
        // Consulta de la meta
        $consulta = "SELECT Alumno.Id as idAlumnoVotado, Alumno.Nombre as nombre, (SELECT SUM(tantoPorCiento) FROM RepartosGrupo 
					INNER JOIN PositivosGrupo ON (PositivosGrupo.Id = RepartosGrupo.IdPositivoGrupo) WHERE IdAlumnoVotado = Alumno.Id 
					AND PositivosGrupo.IdGrupo = '$email' ) AS sumaPorAlumno, (SELECT Grupo.NombreGrupo FROM Grupo WHERE Grupo.Id = '$email') AS nombreGrupo, 
					(SELECT grupo.id FROM Grupo WHERE Grupo.Id = '$email') AS id FROM Asisten INNER JOIN Alumno ON (Alumno.Id = Asisten.IdAlumno) 
					WHERE asisten.IdGrupo = '$email'";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($email));

            return $comando->fetchAll(PDO::FETCH_ASSOC);
			//fetchAll — Devuelve un array que contiene todas las filas del conjunto de resultados

        } catch (PDOException $e) {
            return false;
        }
    }
	

    /**
     * Actualiza un registro de la bases de datos basado
     * en los nuevos valores relacionados con un identificador
     *
     * @param $idMeta      identificador
     * @param $titulo      nuevo titulo
     * @param $descripcion nueva descripcion
     * @param $fechaLim    nueva fecha limite de cumplimiento
     * @param $categoria   nueva categoria
     * @param $prioridad   nueva prioridad
     */
    public static function update(
        $idMeta,
        $titulo,
        $descripcion,
        $fechaLim,
        $categoria,
        $prioridad
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE meta" .
            " SET titulo=?, descripcion=?, fechaLim=?, categoria=?, prioridad=? " .
            "WHERE idMeta=?";

        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);

        // Relacionar y ejecutar la sentencia
        $cmd->execute(array($titulo, $descripcion, $fechaLim, $categoria, $prioridad, $idMeta));

        return $cmd;
    }

    /**
     * Insertar una nueva meta
     *
     * @param $titulo      titulo del nuevo registro
     * @param $descripcion descripción del nuevo registro
     * @param $fechaLim    fecha limite del nuevo registro
     * @param $categoria   categoria del nuevo registro
     * @param $prioridad   prioridad del nuevo registro
     * @return PDOStatement
     */
    public static function insert($email,$nameAsig)
    {
		$idAlumno = "Select id from alumno where email = ?";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($idAlumno);
            // Ejecutar sentencia preparada
            $comando->execute(array($email));

            return $comando->fetchAll(PDO::FETCH_ASSOC);
			//fetchAll — Devuelve un array que contiene todas las filas del conjunto de resultados

        } catch (PDOException $e) {
            return false;
        }
		
		
		$idAsignatura = "Select id from asignatura where nombre = ?";
		try {
            // Preparar sentencia
            $comando2 = Database::getInstance()->getDb()->prepare($idAsignatura);
            // Ejecutar sentencia preparada
            $comando2->execute(array($nameAsig));

            return $comando2->fetchAll(PDO::FETCH_ASSOC);
			//fetchAll — Devuelve un array que contiene todas las filas del conjunto de resultados

        } catch (PDOException $e) {
            return false;
        }
		
        // Sentencia INSERT
        $comando3 = "INSERT INTO asisten ( " .
            "idAsignatura," .
            " idAlumno)" .
            " VALUES( ?,?)";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando3);

        return $sentencia->execute(
            array(
                $idAsignatura,
                $idAlumno
            )
        );

    }
	
	    public static function InsertPuntuacionGrupo($email,$idGr, $nom1, $pun1)
    {
		$idAlumno = "Select id from alumno where email = '$email'";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($idAlumno);
            // Ejecutar sentencia preparada
            $comando->execute(array($email));

            return $comando->fetchAll(PDO::FETCH_ASSOC);
			//fetchAll — Devuelve un array que contiene todas las filas del conjunto de resultados

        } catch (PDOException $e) {
            return false;
        }
		
		$idAlumno1 = "Select id from alumno where nombre = '$nom1'";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($idAlumno1);
            // Ejecutar sentencia preparada
            $comando->execute(array($nom1));

            return $comando->fetchAll(PDO::FETCH_ASSOC);
			//fetchAll — Devuelve un array que contiene todas las filas del conjunto de resultados

        } catch (PDOException $e) {
            return false;
        }
		
		
        // Sentencia INSERT
        $comando3 = "INSERT INTO repartosgrupo ( " .
            "idPositivoGrupo," .
            " idAlumnoVotado," .
			"tantoPorCiento," .
			"idAlumnoVotante)" .
            " VALUES( '11','$idAlumno1', '$pun1', '$idAlumno')";
			
			var_dump($comando3);

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando3);

        return $sentencia->execute(
            array(
                $idAlumno1,
				$pun1,
				$idAlumno
            )
        );

    }

    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $idMeta identificador de la meta
     * @return bool Respuesta de la eliminación
     */
    public static function delete($idMeta)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM meta WHERE idMeta=?";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(array($idMeta));
    }
}

?>