<?php 
require_once ('jpgraph/src/jpgraph.php');
require_once ('jpgraph/src/jpgraph_bar.php');

$idAsigantura=$_GET["idAsigantura"];
/*
require_once('/funciones_bd.php');
$db = new funciones_BD();

$listado=$db->devuelveRanking($idAsigantura);
$i=0;
while ($rank = mysql_fetch_array($listado)){
	$positivos=$db->getContador($idAsigantura, $rank['id']);
	$pos = (int) $positivos;
	// Se define el array de datos
	$datosy[]=$pos;
	$i++;
}*/
$datosy = array(2,1);
//var_dump($datosy);exit();
 
// Creamos el grafico
$grafico = new Graph(500,400, 'auto');
$grafico->SetScale('textint');
 
// Ajustamos los margenes del grafico-----    (left,right,top,bottom)
//$grafico->SetMargin(40,30,30,40);
 
// Creamos barras de datos a partir del array de datos
$bplot = new BarPlot($datosy);

// Configuramos color de las barras 
$bplot->SetFillColor('#f6bf01');

// Queremos mostrar el valor numerico de la barra
$bplot->value->Show();

//Añadimos barra de datos al grafico
$grafico->Add($bplot);
 
// Configuracion de los titulos
$grafico->title->Set('Positivos de los alumnos');
$grafico->xaxis->title->Set('Alumno');
$grafico->yaxis->title->Set('Positivos');
 /*
$grafico->title->SetFont(FF_FONT1,FS_BOLD);
$grafico->yaxis->title->SetFont(FF_FONT1,FS_BOLD);
$grafico->xaxis->title->SetFont(FF_FONT1,FS_BOLD);*/
 
// Se muestra el grafico
$grafico->Stroke();
?>
