
<?php
session_start();
$nombre = $_POST['grupo'];
$asignaturaId = $_POST['asignaturaId'];

$num = $_POST['participants'];

require_once 'funciones_bd.php';
include('tcpdf/tcpdf.php');
$db = new funciones_BD();

	$code = '21';
	for ($i = 0; $i < 13; ++$i) {
		$digit = rand(0, 9);
		$code .= ($digit < 10 ? $digit : ($digit - 10 + 'a'));
	}
	
	if(!$db->isgrupoexist($nombre, $asignaturaId)){
		$db->addgrupo($nombre, $asignaturaId, $code);
		//$resultado[]=array(header("Location: /caramelosconsaboranotaweb"));
			// create new PDF document
		$pdf = new TCPDF(PDF_PAGE_ORIENTATION, PDF_UNIT, PDF_PAGE_FORMAT, true, 'UTF-8', false);
		// set default header data
		$pdf->SetHeaderData(PDF_HEADER_LOGO, PDF_HEADER_LOGO_WIDTH, 'GRUPOS'.' Número de Códigos: '.$num, PDF_HEADER_STRING);
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
		//$pdf->MultiCell(70, 50, $txt, 0, 'J', false, 1, 125, 30, true, 0, false, true, 0, 'T', false);
		
		for ($j=0; $j<$num;++$j){
		
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
		
		$eje_x=10;
		$eje_y=(($j % 5)*50+40);
		
		if ($j % 5 == 0 && $j != 0){
			$pdf->endPage();
			$pdf->addPage();
		}
		$pdf->Image(PDF_HEADER_LOGO, $eje_x, $eje_y - 5, 120, 30, 'JPG', '', '', true, 150, '', false, false, 1, false, false, false);
		$pdf->write2DBarcode($code, 'QRCODE,M', $eje_x, $eje_y, 25, 25, $style, 'N');
		$txt = "Código de ".$nombre.": ".$code;
		$pdf->MultiCell(100, 30, $txt, 0, 'J', false, 1, $eje_x, $eje_y - 5, true, 0, false, true, 0, 'T', false);		
		}

	//Close and output PDF document
	ob_end_clean();
	//$pdf->Output('positivos.pdf', 'I');
	header('Refresh: 1;');
	$pdf->Output('codigos_grupo'.$nombre.'.pdf', 'D');
	
	$resultado[]=array(header("Location: /caramelosconsaboranotaweb"));
	}
	else{
		//$resultado[]=array(header("Location: /caramelosconsaboranotaweb/postlogin.php"));
		echo(" ha ocurrido un error.");
		}		

?>