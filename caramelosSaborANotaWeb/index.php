<?php
error_reporting(E_ERROR | E_PARSE );

require_once('resources/config.php');
require_once('resources/connectbd.php');
require_once('resources/funciones_bd.php');

include "phpqrcode.php";
require('fpdf.php');

session_start();




 //set it to writable location, a place for temp generated PNG files
    $PNG_TEMP_DIR = dirname(__FILE__).DIRECTORY_SEPARATOR.'Codigos-Registro'.DIRECTORY_SEPARATOR;
    
    //html PNG location prefix
    $PNG_WEB_DIR = 'Codigos-Registro/';

    //include "phpqrcode.php";   
	$db = new funciones_BD();	
    
    //ofcourse we need rights to create temp dir
    if (!file_exists($PNG_TEMP_DIR))
        mkdir($PNG_TEMP_DIR);    
    
    $filename = $PNG_TEMP_DIR.'test.png';
    
    //processing form input
    //remember to sanitize user input in real-life solution !!!
    $errorCorrectionLevel = 'H';  
    $matrixPointSize = 4;
	$idPreofesor=$email;	
?>


<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>UCM - Caramelos con sabor a nota</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/agency.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>


	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script type="text/javascript" src="js/code.js"></script>
	
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	
	    <script>
    $('document').foundation({
    	"magellan-expedition": {
    	  threshold: 100, // how many pixels until the magellan bar sticks, 0 = auto
    	  destination_threshold: 100, // pixels from the top of destination for it to be considered active
    	  throttle_delay: 50, // calculation throttling to increase framerate
    	  fixed_top: 0, // top distance in pixels assigned to the fixed element on scroll
    	}
    });
	
	
$(document).ready(function() {
	/*Menu-toggle*/
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("active");
    });
	
    /*Scroll Spy*/
    $('body').scrollspy({ target: '#spy', offset:80});

    /*Smooth link animation*/
    $('a[href*=#]:not([href=#])').click(function() {
        if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') || location.hostname == this.hostname) {

            var target = $(this.hash);
            target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
            if (target.length) {
                $('html,body').animate({
                    scrollTop: target.offset().top
                }, 1000);
                return false;
            }
        }
    });
        
});
alert("hola");
$(document).on("click", ".open-AddBookDialog", function () {
     var myBookId = $(this).data('id');
     $(".modal-body #bookId").val( myBookId );
});
    
    </script>

	
	

</head>

<body id="page-top" class="index">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand page-scroll" href="#page-top">Caramelos con sabor a nota</a>
            </div>

			<?php if (isset($_SESSION["email"])) {?>
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li class="hidden">
                        <a href="#page-top"></a>
                    </li>
                    <!--<li>
						<a href="#" data-toggle="modal" data-target="#serviceModal">Services</a>
                    </li>-->
                    <li>
                        <a class="page-scroll" href="#asignatura">Asignaturas</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#contact">Contact</a>
                    </li>
					<li>
						<? if( isset( $_SESSION[$email] ) ) { ?>
							<a class="page-scroll" href="#usuario"><?php print_r($_SESSION['email']); ?></a>
						<? } ?> 
                    </li>
					<li>
						<a href="cerrarsesion.php" class="texto_pie3" title="Cerrar sesi&oacute;n">[x]</a>
					</li>
                </ul>
            </div>
			<?php } else { ?>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">					
					<li class="hidden">
						<a href="#page-top"></a>
					</li>
					<li>
						<a class="page-scroll" href="#about">About</a>
					</li>
					<li>
						<a class="page-scroll" href="#team">Team</a>
					</li>
					<li>
						<a class="page-scroll" href="#contact">Contact</a>
					</li>
					<li>
						<a href="#" data-toggle="modal" data-target="#loginModal"><span class="glyphicon glyphicon-log-in"></span>Login</a>
					</li>
					<li>
						<a href="#" data-toggle="modal" data-target="#registroModal"><span class="glyphicon glyphicon-user"></span>Registro</a>
					</li>
                </ul>
            </div>
			<?php } ?>
        </div>
        <!-- /.container-fluid -->
    </nav>
	<?php if (!isset($_SESSION["email"])) {?>
    <!-- Header Sin Sesion -->
    <header>
        <div class="container">
            <div class="intro-text">
                <div class="intro-lead-in">¡Un positivo para ti!</div>
                <div class="intro-heading">El esfuerzo tiene un sabor dulce</div>
                <a href="#about" class="page-scroll btn btn-xl">¿Quieres saber más?</a>
            </div>
        </div>
    </header>

    <!-- About Section -->
    <section id="about">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">About</h2>
                    <h3 class="section-subheading text-muted">Lorem ipsum dolor sit amet consectetur.</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ul class="timeline">
                        <li>
                            <div class="timeline-image">
                                <img class="img-circle img-responsive" src="img/about/1.jpg" alt="">
                            </div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">
                                    <h4>2009-2011</h4>
                                    <h4 class="subheading">Our Humble Beginnings</h4>
                                </div>
                                <div class="timeline-body">
                                    <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt ut voluptatum eius sapiente, totam reiciendis temporibus qui quibusdam, recusandae sit vero unde, sed, incidunt et ea quo dolore laudantium consectetur!</p>
                                </div>
                            </div>
                        </li>
                        <li class="timeline-inverted">
                            <div class="timeline-image">
                                <img class="img-circle img-responsive" src="img/about/2.jpg" alt="">
                            </div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">
                                    <h4>March 2011</h4>
                                    <h4 class="subheading">An Agency is Born</h4>
                                </div>
                                <div class="timeline-body">
                                    <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt ut voluptatum eius sapiente, totam reiciendis temporibus qui quibusdam, recusandae sit vero unde, sed, incidunt et ea quo dolore laudantium consectetur!</p>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="timeline-image">
                                <img class="img-circle img-responsive" src="img/about/3.jpg" alt="">
                            </div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">
                                    <h4>December 2012</h4>
                                    <h4 class="subheading">Transition to Full Service</h4>
                                </div>
                                <div class="timeline-body">
                                    <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt ut voluptatum eius sapiente, totam reiciendis temporibus qui quibusdam, recusandae sit vero unde, sed, incidunt et ea quo dolore laudantium consectetur!</p>
                                </div>
                            </div>
                        </li>
                        <li class="timeline-inverted">
                            <div class="timeline-image">
                                <img class="img-circle img-responsive" src="img/about/4.jpg" alt="">
                            </div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">
                                    <h4>July 2014</h4>
                                    <h4 class="subheading">Phase Two Expansion</h4>
                                </div>
                                <div class="timeline-body">
                                    <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt ut voluptatum eius sapiente, totam reiciendis temporibus qui quibusdam, recusandae sit vero unde, sed, incidunt et ea quo dolore laudantium consectetur!</p>
                                </div>
                            </div>
                        </li>
                        <li class="timeline-inverted">
                            <div class="timeline-image">
                                <h4>¡Se parte
                                    <br>de Nuestra
                                    <br>Historia!</h4>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </section>
	
    <!-- Team Section -->
    <section id="team" class="bg-light-gray">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">¿Quienes somos?</h2>
                    <h3 class="section-subheading text-muted">Lorem ipsum dolor sit amet consectetur.</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="team-member">
                        <img src="img/team/1.jpg" class="img-responsive img-circle" alt="">
                        <h4>Nuria Martín</h4>
                        <p class="text-muted">Lead Designer</p>
                        <ul class="list-inline social-buttons">
                            <li><a href="#"><i class="fa fa-twitter"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-facebook"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-linkedin"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="team-member">
                        <img src="img/team/2.jpg" class="img-responsive img-circle" alt="">
                        <h4>Federico Martín</h4>
                        <p class="text-muted">Lead Marketer</p>
                        <ul class="list-inline social-buttons">
                            <li><a href="#"><i class="fa fa-twitter"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-facebook"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-linkedin"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <p class="large text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aut eaque, laboriosam veritatis, quos non quis ad perspiciatis, totam corporis ea, alias ut unde.</p>
                </div>
            </div>
        </div>
    </section>
    
    <!-- Contact Section -->
    <section id="contact">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Contacta con nosotros</h2>
                    <h3 class="section-subheading text-muted">Lorem ipsum dolor sit amet consectetur.</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <form name="sentMessage" id="contactForm" novalidate>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Nombre *" id="name" required data-validation-required-message="Please enter your name.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="email" class="form-control" placeholder="Email *" id="email" required data-validation-required-message="Please enter your email address.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="tel" class="form-control" placeholder="Telefono *" id="phone" required data-validation-required-message="Please enter your phone number.">
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <textarea class="form-control" placeholder="Mensaje *" id="message" required data-validation-required-message="Please enter a message."></textarea>
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-lg-12 text-center">
                                <div id="success"></div>
                                <button type="submit" id="contacto" class="btn btn-xl">Enviar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
	
	<!-- Final de la pagina sin sesion -->
	
	<?php } else { ?> 
	
	
	<!-- Asignatura Section -->
    <section id="asignatura" >
        <div class="container">
            <div class="row">
                <div id="wrapper" data-spy="scroll" data-target="#spy" class="">
					<!-- Sidebar -->
					<div id="sidebar-wrapper" class="">
						<nav id="spy">
							<ul class="sidebar-nav nav">
								<li class="sidebar-brand active">
									<a href="#asignatura" class="">
										<span class="fa fa-home solo">Menú Principal</span>
									</a>
								</li>
								<?php
									$db = new funciones_BD();
									$email=$_SESSION["email"];
									$idProfesor=$db->devuelveIdProfesorFromEmail($email);
									$lista=$db->devuelveAsignaturasFromIdProf($idProfesor);
									while($asignauras = mysql_fetch_array($lista)){?>
										<li class="sidebar-brand active">
											<a href="#<?=$asignauras['nombre']."-".$asignauras['grupo']?>" class="">
												<span class="fa fa-home solo"><?=$asignauras['nombre']."-".$asignauras['grupo']?></span>
											</a>
										</li>
										<!--<option value='".$asignauras['nombre']."'> -".utf8_encode($asignauras['nombre'])."</option>";	-->
								<?php } ?>
							</ul>
						</nav>
					</div>
					<!-- Page content -->
					<div id="page-content-wrapper" class="">
						<div class="content-header">
							<h1 id="home" style="color:white" class="">
								<a id="menu-toggle" href="#" class="btn btn-menu btn-lg toggle">
								  <span class="icon-bar"></span>
								  <span class="icon-bar"></span>
								  <span class="icon-bar"></span>
								</a>
								Menú de Asignaturas
							</h1>
						</div>
						<div class="page-content inset">
							<div class="row">
								<div class="col-md-12 well">
									<div class="col-lg-8 col-lg-offset-2 text-center">
										<!--<a href="#" data-toggle="modal" data-target="#addAsigModal"><img src='img/anadir.jpg'></img></a>-->
										<legend id="menuP" class="">Menú Principal</legend>
										<span class="fa-stack fa-4x">
											<i class="fa fa-circle fa-stack-2x text-primary"></i>
												<a href="#addAsigModal" class="portfolio-link" data-toggle="modal">
													<i class="fa fa-plus fa-stack-1x fa-inverse"></i>
												</a>
										</span>
										<p class="large text-muted">Añade tu asignatura</p>
									</div>
								</div>
								<?php 
								$db = new funciones_BD();
								$email=$_SESSION["email"];
								$idProfesor=$db->devuelveIdProfesorFromEmail($email);
								$lista=$db->devuelveAsignaturasFromIdProf($idProfesor);
								while ($asignaturas = mysql_fetch_array($lista)){?>
									<div class="col-md-12 well">
										<legend id="<?=$asignaturas['nombre']."-".$asignaturas['grupo']?>" class=""><?=$asignaturas['nombre']."-".$asignaturas['grupo']?></legend>
										<div class="col-md-6 well">
											<h2 class="section-heading" style="color:black" >C&Oacute;DIGO REGISTRO</h2>
											<?php $nombreAsignaturaSinEspacios = str_replace(" ", "", $asignaturas['nombre']);?>
											<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalGeneraCod<?=$nombreAsignaturaSinEspacios.$asignaturas['grupo']?>" >
												Generar Código de Registro
											</button>
											<!--<form class="codigoRegistro" id="codigoRegistro" action="./resources/addregistro.php" method="post">
												N&uacute;mero de Registros: <input type="text" name="num_cod" ><br>
												<input type="hidden" name="asignaturaCod" value="<?=$asignaturas['nombre']."-".$asignaturas['grupo']?>">
												<button type="submit" class="btn btn-primary btn-sm">Generar</button>
											</form>-->
										</div>
										<div class="col-md-6 well">
											<h2 class="section-heading" style="color:black" >POSITIVOS</h2>
											<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalGeneraPos<?=$nombreAsignaturaSinEspacios.$asignaturas['grupo']?>" >
												Generar Positivos
											</button>
											<!--<form class="codigoPositivo" id="codigoPositivo" action="./resources/addpositivo.php" method="post">
												N&uacute;mero de Positivos: <input type="text" name="num_pos" ><br>
												<input type="hidden" name="asignaturaPos" value="<?=$asignaturas['nombre']."-".$asignaturas['grupo']?>">
												<button type="submit" class="btn btn-primary btn-sm">Generar</button>-->
											</form>
										</div>
										<div class="col-md-6 well">
											<h2 class="section-heading" style="color:black" >Administar Grupos</h2>
											<?php 
											$idAsignatura=$asignaturas['id'];
											$grupos=$db->devuelveGruposFromIdAsig($idAsignatura);
											while ($grupo = mysql_fetch_array($grupos)){
												$nombreGrupoSinEspacios = str_replace(" ", "", $grupo['nombreGrupo']);
											?>
												<!--<a data-toggle="modal" data-id="<?=$grupo['nombreGrupo']?>" title="Add this item" class="open-AddBookDialog btn btn-primary" href="#addBookDialog"><?=$grupo['nombreGrupo']?></a>-->
													<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#<?=$nombreGrupoSinEspacios.$grupo['id']?>" >
														<?=$grupo['nombreGrupo']?>
													</button>
													<p></p>
											<?php } ?>
											<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalCrearGrupo<?=$nombreAsignaturaSinEspacios.$asignaturas['grupo']?>" >
												Crear Grupo Nuevo
											</button>
										</div>
										<div class="col-md-6 well">
											<h2 class="section-heading" style="color:black" >Ver Estadísticas</h2>
											<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalRanking<?=$nombreAsignaturaSinEspacios.$asignaturas['grupo']?>" >
												Visualizar Estad&iacute;sticas
											</button>
										</div>
										<div class="col-md-12 well" style="text-align:center;">
											<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalDelete<?=$nombreAsignaturaSinEspacios.$asignaturas['grupo']?>">Eliminar Asignatura</button>
										</div>
									</div>
								<?php } ?>
							</div>
							<div class="navbar navbar-default navbar-static-bottom">
							</div>
						</div>
					</div>
				</div>
            </div>
        </div>
    </section>
	
	<!-- Contact Section -->
    <section id="contact">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Contacta con nosotros</h2>
                    <h3 class="section-subheading text-muted">Lorem ipsum dolor sit amet consectetur.</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <form name="sentMessage" id="contactForm" novalidate>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Nombre *" id="name" required data-validation-required-message="Please enter your name.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="email" class="form-control" placeholder="Email *" id="email" required data-validation-required-message="Please enter your email address.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="tel" class="form-control" placeholder="Telefono *" id="phone" required data-validation-required-message="Please enter your phone number.">
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <textarea class="form-control" placeholder="Mensaje *" id="message" required data-validation-required-message="Please enter a message."></textarea>
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-lg-12 text-center">
                                <div id="success"></div>
                                <button type="submit" id="contacto" class="btn btn-xl">Enviar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
	<?php } ?>

    <footer>
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <span class="copyright">Copyright &copy; Your Website 2016</span>
                </div>
                <div class="col-md-4">
                    <ul class="list-inline social-buttons">
                        <li><a href="#"><i class="fa fa-twitter"></i></a>
                        </li>
                        <li><a href="#"><i class="fa fa-facebook"></i></a>
                        </li>
                        <li><a href="#"><i class="fa fa-linkedin"></i></a>
                        </li>
                    </ul>
                </div>
                <div class="col-md-4">
                    <ul class="list-inline quicklinks">
                        <li><a href="#">Privacy Policy</a>
                        </li>
                        <li><a href="#">Terms of Use</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </footer>
	
	<!-- Section Modal -->
	
	  <div class="modal fade" id="serviceModal" tabindex="-1" role="dialog" >
		<div class="modal-dialog">
		  <div class="modal-content">
			<div class="modal-header">
			  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			  <h4 class="modal-title">Servicios</h4>
			</div>
			<div class="modal-body">
				<div class="col-md-4">
                    <span class="fa-stack fa-4x">
                        <i class="fa fa-circle fa-stack-2x text-primary"></i>
                        <a href="/caramelosconsaboranotaweb/index-registro.php"><i class="fa fa-qrcode fa-stack-1x fa-inverse"></i></a>
                    </span>
                    <h4 class="service-heading">Generar QR</h4>
                    <p class="text-muted">Aqu&iacute; podr&aacute;s generar los c&oacute;digos de tu asignatura.</p>
                </div>
                <div class="col-md-4">
                    <span class="fa-stack fa-4x">
                        <i class="fa fa-circle fa-stack-2x text-primary"></i>
                        <i class="fa fa-bar-chart fa-stack-1x fa-inverse"></i>
                    </span>
                    <h4 class="service-heading">Visualizar estadísticas</h4>
                    <p class="text-muted">Sirve para visualizar las estad&iacute;sticas de tus alumnos.</p>
                </div>
                <div class="col-md-4">
                    <span class="fa-stack fa-4x">
                        <i class="fa fa-circle fa-stack-2x text-primary"></i>
                        <i class="fa fa-search fa-stack-1x fa-inverse"></i>
                    </span>
                    <h4 class="service-heading">Nota de alumnos</h4>
                    <p class="text-muted">Las notas de todos los alumnos matriculados.</p>
                </div>
			</div>
			<div class="modal-footer">
			  <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		  </div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	  </div><!-- /.modal -->
	  
	  
	  <!--Codigos Registro Modals-->
	  
	  <?php 
			$db = new funciones_BD();
			$lista=$db->devuelveAsignaturas();
			while ($asig = mysql_fetch_array($lista)){
				$nombreAsignaturaSinEspacios = str_replace(" ", "", $asig['nombre']);
		?>
		<form action="./resources/addregistro.php" method="post" id="addGrupoForm">
			<div class="modal fade" id="modalGeneraCod<?=$nombreAsignaturaSinEspacios.$asig['grupo']?>" tabindex="-1">
				<div class="modal-dialog modal-sm">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-tittle" id="loginModalLabel">Generar Códigos de registro para <?=$asig['nombre']?></h4>
						</div>
						<div class="modal-body">
							<div class="form-group" id="inputUserIDGroup">
								<label class="control-label" for="grupo">Número de códigos</label>
								<input type="num_cod" class="form-control" id="num_cod" placeholder="Número de códigos" name="num_cod">
								<input type="hidden" class="form-control" id="asignaturaId" name="asignaturaId" value="<?=$asig['id']?>">
								<input type="hidden" class="form-control" id="asignaturaNombre" name="asignaturaNombre" value="<?=$asig['nombre']?>">
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
							<button type="submit" class="btn btn-primary">Generar</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<?php } ?>
	  
	  
	  <!--Positivos Modals-->
	  
	  <?php 
			$db = new funciones_BD();
			$lista=$db->devuelveAsignaturas();
			while ($asig = mysql_fetch_array($lista)){
				$nombreAsignaturaSinEspacios = str_replace(" ", "", $asig['nombre']);
		?>
		<form action="./resources/addpositivo.php" method="post" id="addGrupoForm">
			<div class="modal fade" id="modalGeneraPos<?=$nombreAsignaturaSinEspacios.$asig['grupo']?>" tabindex="-1">
				<div class="modal-dialog modal-sm">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-tittle" id="loginModalLabel">Generar positivos para <?=$asig['nombre']?></h4>
						</div>
						<div class="modal-body">
							<div class="form-group" id="inputUserIDGroup">
								<label class="control-label" for="grupo">Número de positivos</label>
								<input type="num_pos" class="form-control" id="num_pos" placeholder="Número de positivos" name="num_pos">
								<input type="hidden" class="form-control" id="asignaturaId" name="asignaturaId" value="<?=$asig['id']?>">
								<input type="hidden" class="form-control" id="asignaturaNombre" name="asignaturaNombre" value="<?=$asig['nombre']?>">
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
							<button type="submit" class="btn btn-primary">Generar</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<?php } ?>
		
		
		
		
		<!--Delete Modal-->
	  
	  <?php 
			$db = new funciones_BD();
			$lista=$db->devuelveAsignaturas();
			while ($asig = mysql_fetch_array($lista)){
				$nombreAsignaturaSinEspacios = str_replace(" ", "", $asig['nombre']);
		?>
		<form action="./resources/deleteAsignatura.php" method="post" id="addGrupoForm">
			<div class="modal fade" id="modalDelete<?=$nombreAsignaturaSinEspacios.$asig['grupo']?>" tabindex="-1">
				<div class="modal-dialog modal-sm">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-tittle" id="loginModalLabel">Eliminar <?=$asig['nombre']?></h4>
						</div>
						<div class="modal-body">
							<div class="form-group" id="inputUserIDGroup">
								<label class="control-label" for="grupo">¿Está seguro de eliminar la asignatura?</label>
								<input type="hidden" class="form-control" id="asignaturaId" name="asignaturaId" value="<?=$asig['id']?>">
								<input type="hidden" class="form-control" id="asignaturaNombre" name="asignaturaNombre" value="<?=$asig['nombre']?>">
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
							<button type="submit" class="btn btn-primary">Eliminar</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<?php } ?>
	  
	  <!--Grupos Modals-->
		
		<?php 
			$db = new funciones_BD();
			$lista=$db->devuelveGrupos();
			while ($grupos = mysql_fetch_array($lista)){
				$nombreGrupoSinEspacios = str_replace(" ", "", $grupos['nombreGrupo']);
		?>
		
		<div class="modal fade" id="<?=$nombreGrupoSinEspacios.$grupos['id']?>" tabindex="-1">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title"><?=$grupos['nombreGrupo']?></h4>
					</div>
					<div class="modal-body" id="nombreGrupo" name="nombreGrupo">
						<label class="control-label" for="grupo">C&oacute;digo de registro: <?=$grupos['cadenaRegistroGrupo']?></label></br>
						<label class="control-label" for="grupo">Alumnos: </label></br></br>
						<?php
							$alumnos = $db->devuelveAlumnosGrupo($grupos['id']);
							while ($alumno = mysql_fetch_array($alumnos)){
								$nombre=$db->devuelveNombreById($alumno['idAlumno']);
								$apellidos=$db->devuelveApellidosById($alumno['idAlumno']);
						?>
									<label class="control-label" for="grupo"><?= $nombre ?> <?= $apellidos ?></label></br>
							<?php } ?>
							<button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#modalGeneraPosGrupo<?=$nombreGrupoSinEspacios.$grupos['id']?>" >
								Generar Positivos Grupales
							</button>
							<button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#modalNotas<?=$nombreGrupoSinEspacios.$grupos['id']?>" >
								Ver Notas Grupales
							</button>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					</div>
				</div>
			</div>
		</div>
		<?php } ?>
		
		
		
		
		
		<!--      Añadir Grupo a una asignatura        -->
		<?php 
			$db = new funciones_BD();
			$lista=$db->devuelveAsignaturas();
			while ($asig = mysql_fetch_array($lista)){
				$nombreAsignaturaSinEspacios = str_replace(" ", "", $asig['nombre']);
		?>
		<form action="./resources/addgrupo.php" method="post" id="addGrupoForm">
			<div class="modal fade" id="modalCrearGrupo<?=$nombreAsignaturaSinEspacios.$asig['grupo']?>" tabindex="-1">
				<div class="modal-dialog modal-sm">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-tittle" id="loginModalLabel">Crear grupo para <?=$asig['nombre']?></h4>
						</div>
						<div class="modal-body">
							<div class="form-group" id="inputUserIDGroup">
								<label class="control-label" for="grupo">Nombre del Grupo</label>
								<input type="grupo" class="form-control" id="grupo" placeholder="Nombre del Grupo" name="grupo">
								<input type="hidden" class="form-control" id="asignaturaId" name="asignaturaId" value="<?=$asig['id']?>">
							</div>
							<div class="form-group" id="inputUserIDGroup">
								<label class="control-label" for="participants">Número de participantes</label>
								<input type="participants" class="form-control" id="participants" placeholder="Participantes" name="participants">
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
							<button type="submit" class="btn btn-primary">Crear</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<?php } ?>
		
		
		<!--Estadíticas Modal-->
		
		<?php 
			$db = new funciones_BD();
			$lista=$db->devuelveAsignaturas();
			while ($asig = mysql_fetch_array($lista)){
				$nombreAsignaturaSinEspacios = str_replace(" ", "", $asig['nombre']);
		?>
		<form action="./resources/addgrupo.php" method="post" id="addGrupoForm">
			<div class="modal fade" id="modalRanking<?=$nombreAsignaturaSinEspacios.$asig['grupo']?>" tabindex="-1">
				<div class="modal-dialog modal-md">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-tittle" id="loginModalLabel">Ranking de alumnos: <?=$asig['nombre']?></h4>
						</div>
						<div class="modal-body">
                            <div class="col-md-6">
								<div class="form-group" id="inputUserIDGroup">
									<label class="control-label" for="grupo">Nombre del Alumno</label>
								</div>
							</div>
                            <div class="col-md-6">
								<div class="form-group" id="inputUserIDGroup">
									<label class="control-label" for="grupo">N&uacute;mero de Positivos</label>
								</div>
							</div>
							<?php
								$db = new funciones_BD();
								$listado=$db->devuelveRanking($asig['id']);
								$i=0;
								while ($rank = mysql_fetch_array($listado)){
									$i++;
							?>
								<div class="col-md-6">
									<div class="form-group" id="inputUserIDGroup">
										<label class="control-label" for="grupo"><?= $i." - ".$rank['nombre']." ".$rank['apellidos']?></label>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group" id="inputUserIDGroup">
										<label class="control-label" for="grupo"><?= $db->getContador($asig['id'], $rank['id']) ?></label>
									</div>
								</div>
							<?php } ?>
							<div class="col-md-12">
								<div class="form-group" id="inputUserIDGroup">
									<img src="resources/generar_grafico_barras.php?idAsigantura=<?= $asig['id'] ?>" /> 
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group" id="inputUserIDGroup">
									<img src="resources/generar_grafico_tarta.php?idAsigantura=<?= $asig['id'] ?>" /> 
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<?php } ?>
	  
	  
		<!--Asignaturas Modal-->
		
		<div class="modal fade" id="asignaturaModal" tabindex="-1">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-tittle" id="loginModalLabel"><?= $_GET['asignatura']?></h4>
					</div>
					<div class="modal-body">
						<div class="form-group" id="inputUserIDGroup">
							<label class="control-label" for="email">email</label>
							<input type="email" class="form-control" id="email" placeholder="Email" name="email">
						</div>
						<div class="form-group" id="inputPasswordFormGroup">
							<label class="control-label" for="password">Password</label>
							<input type="password" class="form-control" id="password" placeholder="Password" name="password">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-primary">Login</button>
					</div>
				</div>
			</div>
		</div>
		
		<!--Positivos Grupos Modals-->
	  
	  <?php 
			$db = new funciones_BD();
			$lista=$db->devuelveGrupos();
			while ($grupos = mysql_fetch_array($lista)){
				$nombreGrupoSinEspacios = str_replace(" ", "", $grupos['nombreGrupo']);
		?>
		<form action="./resources/addpositivogrupo.php" method="post" id="addGrupoForm">
			<div class="modal fade" id="modalGeneraPosGrupo<?=$nombreGrupoSinEspacios.$grupos['id']?>" tabindex="-1">
				<div class="modal-dialog modal-sm">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-tittle" id="loginModalLabel">Generar positivos para <?=$grupos['nombreGrupo']?></h4>
						</div>
						<div class="modal-body">
							<div class="form-group" id="inputUserIDGroup">
								<?php
									$db = new funciones_BD();
									$asigId=$db->devuelveAsignaturaDelGrupoID($grupos['id']);
								?>
							
								<label class="control-label" for="grupo">Número de positivos</label>
								<input type="num_pos" class="form-control" id="num_pos" placeholder="Número de positivos" name="num_pos">
								<input type="hidden" class="form-control" id="grupoId" name="grupoId" value="<?=$grupos['id']?>">
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
							<button type="submit" class="btn btn-primary">Generar</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<?php } ?>
		
		<!--Notas Grupos Modals-->
	  
	  <?php 
			$db = new funciones_BD();
			$lista=$db->devuelveGrupos();
			while ($grupos = mysql_fetch_array($lista)){
				$nombreGrupoSinEspacios = str_replace(" ", "", $grupos['nombreGrupo']);
		?>
			<div class="modal fade" id="modalNotas<?=$nombreGrupoSinEspacios.$grupos['id']?>" tabindex="-1">
				<div class="modal-dialog modal-sm">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-tittle" id="loginModalLabel">Notas de <?=$grupos['nombreGrupo']?></h4>
						</div>
						<div class="modal-body">
							<div class="form-group" id="inputUserIDGroup">
								<?php
									$db = new funciones_BD();
									$idPositivoGrupo=$db->estadoPositivoGrupoByIdGrupo($grupos['id']);
										while ($positivos = mysql_fetch_array($idPositivoGrupo)){
											$nota=$db->devuelveNotas($positivos['id']);
											while ($notas = mysql_fetch_array($nota)){
												if ($notas[0]!=''){
													$nombreAlumnoVotante=$db->devuelveNombreById($notas['idAlumnoVotante']);
													$apellidoAlumnoVotante=$db->devuelveApellidosById($notas['idAlumnoVotante']);
													$nombreAlumnoVotado=$db->devuelveNombreById($notas['idAlumnoVotado']);
													$apellidoAlumnoVotado=$db->devuelveApellidosById($notas['idAlumnoVotado']);
									?>
													<label class="control-label" for="grupo">- <?=$nombreAlumnoVotante." ".$apellidoAlumnoVotante?> le ha dado un <?=$notas['tantoPorCiento']?>% a <?=$nombreAlumnoVotado." ".$apellidoAlumnoVotado?> con el positivo <?=$notas['idPositivoGrupo']?></label>
											<?php } else {?>
												No se han repartido Positivos entre los alumnos de este grupo
											<?php } ?>
										<?php } ?>
									<?php } ?>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						</div>
					</div>
				</div>
			</div>
		<?php } ?>
		
	<!-- Login Modal -->
	<form action="./resources/accesweb.php" method="post" id="loginForm">
		<div class="modal fade" id="loginModal" tabindex="-1">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-tittle" id="loginModalLabel">Login</h4>
					</div>
					<div class="modal-body">
						<div class="form-group" id="inputUserIDGroup">
							<label class="control-label" for="email">email</label>
							<input type="email" class="form-control" id="email" placeholder="Email" name="email">
						</div>
						<div class="form-group" id="inputPasswordFormGroup">
							<label class="control-label" for="password">Password</label>
							<input type="password" class="form-control" id="password" placeholder="Password" name="password">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-primary">Login</button>
					</div>
				</div>
			</div>
		</div>
	</form>
	
		<!-- Registro Modal -->
	<form action="./resources/registroweb.php" method="post" id="registroForm">
		<div class="modal fade" id="registroModal" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-tittle" id="registroModalLabel">Registro</h4>
					</div>
					<div class="modal-body">
						<div class="form-group" id="inputUserIDGroup">
							<label class="control-label" for="email">email</label>
							<input type="text" class="form-control" id="email" placeholder="Email" name="email" required data-validation-required-message="Introduzca su Email.">
						</div>
						<div class="form-group" id="inputUserIDGroup">
							<label class="control-label" for="nombre">Nombre</label>
							<input type="text" class="form-control" id="nombre" placeholder="Nombre" name="nombre" required data-validation-required-message="Introduzca su Nombre.">
						</div>
						<div class="form-group" id="inputUserIDGroup">
							<label class="control-label" for="apellidos">Apellidos</label>
							<input type="text" class="form-control" id="apellidos" placeholder="Apellidos" name="apellidos" required data-validation-required-message="Introduzca sus Apellidos.">
						</div>
						<div class="form-group" id="inputPasswordFormGroup">
							<label class="control-label" for="password">Password</label>
							<input type="password" class="form-control" id="password" placeholder="Password" name="password" required data-validation-required-message="Introduzca su Contraseña.">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-primary">Registrar</button>
					</div>
				</div>
			</div>
		</div>
	</form>
	
	
	<!-- AddAsignatura Modal -->
	<form action="./resources/addasignatura.php" method="post" id="addAsigForm">
		<div class="modal fade" id="addAsigModal" tabindex="-1">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-tittle" id="loginModalLabel">Añadir Asinatura</h4>
					</div>
					<div class="modal-body">
						<div class="form-group" id="nombreAsignatura">
							<label class="control-label" for="nombre">Asignatura</label>
							<input type="nombre" class="form-control" id="nombre" placeholder="Nombre de la asignatura" name="nombre">
						</div>
						<div class="form-group" id="grupoAsignatura">
							<label class="control-label" for="password">Grupo</label>
							<input type="grupo" class="form-control" id="grupo" placeholder="Grupo" name="grupo">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-primary">Añadir</button>
					</div>
				</div>
			</div>
		</div>
	</form>
	
	<!--------------------------------------------------------------->
	<div class="modal fade" id="grupos" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title">Grupos</h4>
				</div>
				<div class="modal-body">
					Modal body content
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	
	<!-- Modal Estadísticas-->
	<div class="modal fade" id="estadisticas" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title">Estadísticas</h4>
				</div>
				<div class="modal-body">
					Modal body content
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	


    <!-- Portfolio Modals -->
    <!-- Use the modals below to showcase details about your portfolio projects! -->
	
    <!-- Portfolio Modal 1 -->
    <div class="portfolio-modal modal fade" id="portfolioModal1" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <!-- Project Details Go Here -->
                            <h2>Project Name</h2>
                            <p class="item-intro text-muted">Lorem ipsum dolor sit amet consectetur.</p>
                            <img class="img-responsive img-centered" src="img/portfolio/roundicons-free.png" alt="">
                            <p>Use this area to describe your project. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Est blanditiis dolorem culpa incidunt minus dignissimos deserunt repellat aperiam quasi sunt officia expedita beatae cupiditate, maiores repudiandae, nostrum, reiciendis facere nemo!</p>
                            <p>
                                <strong>Want these icons in this portfolio item sample?</strong>You can download 60 of them for free, courtesy of <a href="https://getdpd.com/cart/hoplink/18076?referrer=bvbo4kax5k8ogc">RoundIcons.com</a>, or you can purchase the 1500 icon set <a href="https://getdpd.com/cart/hoplink/18076?referrer=bvbo4kax5k8ogc">here</a>.</p>
                            <ul class="list-inline">
                                <li>Date: July 2014</li>
                                <li>Client: Round Icons</li>
                                <li>Category: Graphic Design</li>
                            </ul>
                            <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close Project</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Portfolio Modal 2 -->
    <div class="portfolio-modal modal fade" id="portfolioModal2" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <h2>Project Heading</h2>
                            <p class="item-intro text-muted">Lorem ipsum dolor sit amet consectetur.</p>
                            <img class="img-responsive img-centered" src="img/portfolio/startup-framework-preview.png" alt="">
                            <p><a href="http://designmodo.com/startup/?u=787">Startup Framework</a> is a website builder for professionals. Startup Framework contains components and complex blocks (PSD+HTML Bootstrap themes and templates) which can easily be integrated into almost any design. All of these components are made in the same style, and can easily be integrated into projects, allowing you to create hundreds of solutions for your future projects.</p>
                            <p>You can preview Startup Framework <a href="http://designmodo.com/startup/?u=787">here</a>.</p>
                            <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close Project</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Portfolio Modal 3 -->
    <div class="portfolio-modal modal fade" id="portfolioModal3" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <!-- Project Details Go Here -->
                            <h2>Project Name</h2>
                            <p class="item-intro text-muted">Lorem ipsum dolor sit amet consectetur.</p>
                            <img class="img-responsive img-centered" src="img/portfolio/treehouse-preview.png" alt="">
                            <p>Treehouse is a free PSD web template built by <a href="https://www.behance.net/MathavanJaya">Mathavan Jaya</a>. This is bright and spacious design perfect for people or startup companies looking to showcase their apps or other projects.</p>
                            <p>You can download the PSD template in this portfolio sample item at <a href="http://freebiesxpress.com/gallery/treehouse-free-psd-web-template/">FreebiesXpress.com</a>.</p>
                            <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close Project</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Portfolio Modal 4 -->
    <div class="portfolio-modal modal fade" id="portfolioModal4" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <!-- Project Details Go Here -->
                            <h2>Project Name</h2>
                            <p class="item-intro text-muted">Lorem ipsum dolor sit amet consectetur.</p>
                            <img class="img-responsive img-centered" src="img/portfolio/golden-preview.png" alt="">
                            <p>Start Bootstrap's Agency theme is based on Golden, a free PSD website template built by <a href="https://www.behance.net/MathavanJaya">Mathavan Jaya</a>. Golden is a modern and clean one page web template that was made exclusively for Best PSD Freebies. This template has a great portfolio, timeline, and meet your team sections that can be easily modified to fit your needs.</p>
                            <p>You can download the PSD template in this portfolio sample item at <a href="http://freebiesxpress.com/gallery/golden-free-one-page-web-template/">FreebiesXpress.com</a>.</p>
                            <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close Project</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Portfolio Modal 5 -->
    <div class="portfolio-modal modal fade" id="portfolioModal5" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <!-- Project Details Go Here -->
                            <h2>Project Name</h2>
                            <p class="item-intro text-muted">Lorem ipsum dolor sit amet consectetur.</p>
                            <img class="img-responsive img-centered" src="img/portfolio/escape-preview.png" alt="">
                            <p>Escape is a free PSD web template built by <a href="https://www.behance.net/MathavanJaya">Mathavan Jaya</a>. Escape is a one page web template that was designed with agencies in mind. This template is ideal for those looking for a simple one page solution to describe your business and offer your services.</p>
                            <p>You can download the PSD template in this portfolio sample item at <a href="http://freebiesxpress.com/gallery/escape-one-page-psd-web-template/">FreebiesXpress.com</a>.</p>
                            <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close Project</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Portfolio Modal 6 -->
    <div class="portfolio-modal modal fade" id="portfolioModal6" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-content">
            <div class="close-modal" data-dismiss="modal">
                <div class="lr">
                    <div class="rl">
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2">
                        <div class="modal-body">
                            <!-- Project Details Go Here -->
                            <h2>Project Name</h2>
                            <p class="item-intro text-muted">Lorem ipsum dolor sit amet consectetur.</p>
                            <img class="img-responsive img-centered" src="img/portfolio/dreams-preview.png" alt="">
                            <p>Dreams is a free PSD web template built by <a href="https://www.behance.net/MathavanJaya">Mathavan Jaya</a>. Dreams is a modern one page web template designed for almost any purpose. It’s a beautiful template that’s designed with the Bootstrap framework in mind.</p>
                            <p>You can download the PSD template in this portfolio sample item at <a href="http://freebiesxpress.com/gallery/dreams-free-one-page-web-template/">FreebiesXpress.com</a>.</p>
                            <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close Project</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

	 

	
<!--
    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.css"></script>
    <script src="js/bootstrap.min.js"></script>
	

    <!-- Plugin JavaScript -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
    <script src="js/classie.js"></script>
    <script src="js/cbpAnimatedHeader.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="js/agency.js"></script>
	
	
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</body>

</html>
