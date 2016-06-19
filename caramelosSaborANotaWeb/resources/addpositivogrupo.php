
<?php
session_start();
$num = $_POST['num_pos'];
$idGrupo = $_POST['grupoId'];
$asig = $_POST['asignaturaNombre'];
$email = $_SESSION['email'];


/*$array=explode('-', $asig);
$asignatura=$array[0];
$grupo=$array[1];*/

require_once 'funciones_bd.php';

include('tcpdf/tcpdf.php');



$db = new funciones_BD();

//$idAsig = $db->devuelveidAsignatura($asignatura,$grupo);


	// create new PDF document
	$pdf = new TCPDF(PDF_PAGE_ORIENTATION, PDF_UNIT, PDF_PAGE_FORMAT, true, 'UTF-8', false);
	// set default header data
	$pdf->SetHeaderData(PDF_HEADER_LOGO, PDF_HEADER_LOGO_WIDTH, PDF_HEADER_TITLE.' Número de Positivos: '.$num, PDF_HEADER_STRING);
	// set header and footer fonts
	$pdf->setHeaderFont(Array(PDF_FONT_NAME_MAIN, '', PDF_FONT_SIZE_MAIN));
	$pdf->setFooterFont(Array(PDF_FONT_NAME_DATA, '', PDF_FONT_SIZE_DATA));

	// set default monospaced font
	$pdf->SetDefaultMonospacedFont(PDF_FONT_MONOSPACED);

	// set margins
	$pdf->SetMargins(PDF_MARGIN_LEFT, PDF_MARGIN_TOP, PDF_MARGIN_RIGHT);
	$pdf->SetHeaderMargin(PDF_MARGIN_HEADER);
	$pdf->SetFooterMargin(PDF_MARGIN_FOOTER);

	// set auto page breaks
	$pdf->SetAutoPageBreak(TRUE, PDF_MARGIN_BOTTOM);

	// set image scale factor
	$pdf->setImageScale(PDF_IMAGE_SCALE_RATIO);
	
	// add a page
	$pdf->startPage();
	// print a message
	$pdf->MultiCell(70, 50, $txt, 0, 'J', false, 1, 125, 30, true, 0, false, true, 0, 'T', false);
	
	for ($j=0; $j<$num;++$j){
		
	/*if ($j % 5 == 0){
		// add a page
		$pdf->AddPage();
		// print a message
		$txt = "Los positivos van a continuacion.\n";
		$pdf->MultiCell(70, 50, $txt, 0, 'J', false, 1, 125, 30, true, 0, false, true, 0, 'T', false);
	}*/
	
	// set style for barcode
	$style = array(
		'border' => true,
		'vpadding' => 'auto',
		'hpadding' => 'auto',
		'fgcolor' => array(0,0,0),
		'bgcolor' => false, //array(255,255,255)
		'module_width' => 1, // width of a single module in points
		'module_height' => 1 // height of a single module in points
	);

	$code = '3';
	for ($i = 0; $i < 14; ++$i) {
		$digit = rand(0, 9);
		$code .= ($digit < 10 ? $digit : ($digit - 10 + 'a'));
	}
	
	if($db->addplusGrupo($code,$idGrupo)){
		$idPos=$db->devuelveidPositivo($code);
		$idProfesor=$db->devuelveIdProfesorFromEmail($email);
		$db->addgenera($idProfesor, $idPos);
		//echo(" La asignatura se ha registrado en la base de datos correctamente.");	
		
		//$eje_x=$j*10+10;
		
		//$k=$j;
		
		$eje_x=10;
		$eje_y=(($j % 5)*50+40);
		
		//$pdf->write2DBarcode('caramelosconsaboranotaweb/'.$code, 'QRCODE,M', 10, 45, 25, 25, $style, 'N');
		
		if ($j % 5 == 0 && $j != 0){
			$pdf->endPage();
			$pdf->addPage();
		}
		$pdf->Image(PDF_HEADER_LOGO, $eje_x, $eje_y - 5, 120, 30, 'JPG', '', '', true, 150, '', false, false, 1, false, false, false);
		$pdf->write2DBarcode($code, 'QRCODE,M', $eje_x, $eje_y, 25, 25, $style, 'N');
		$txt = "Positivo de ".$asig.": ".$code;
		$pdf->MultiCell(100, 30, $txt, 0, 'J', false, 1, $eje_x, $eje_y - 5, true, 0, false, true, 0, 'T', false);
	}
	else{
		//$resultado[]=array(header("Location: /caramelosconsaboranotaweb/postlogin.php"));
		echo(" ha ocurrido un error.");
		}		
}

//Close and output PDF document
ob_end_clean();
//$pdf->Output('positivos.pdf', 'I');
header('Refresh: 1;');
$pdf->Output('positivosGrupales'.$asig.'.pdf', 'D');

$resultado[]=array(header("Location: /caramelosconsaboranotaweb/index-registro.php"));
?>