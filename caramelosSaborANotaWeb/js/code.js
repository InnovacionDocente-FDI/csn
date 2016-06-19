$(document).ready(ini);

function ini(){
    $("#btnregistrar").click(enviarDatos);
    $("#singin").click(validar);
}
function enviarDatos(){

    var nombre = $("#nombre").val();
    var apellidos = $("#apellidos").val();
	var password = $("#password").val();
    var email = $("#email").val();	

    
    $.ajax({
        url:"registro.php",
        success:function(result){
            if(result =="true"){
                $("#resultado").html("se ha registrado el usuario correctamente");   
            }else{
                $("#resultado").html("no se ha podido registrar el usuario correctamente");
            }
        },
        data:{
            nombre:nombre,
			apellidos:apellidos,
            password:password,
			email:email
        },
        type:"GET"
    });
}
function validar(){
    
    var email = $("#email").val();
    var password = $("#password").val();
    
    $.ajax({
        url:"access.php",
        success:function(result){
            if(result =="true"){
               document.location.href="admin.php";    
            }else{
                $("#resultado").html("<div class='alert alert-danger' role='alert'><b>acceso denegado, </b>no se pudo comprobar el usuario</div>");
            }
        },
        data:{
            email:email,
            password:password
        },
        type:"POST"
    });

}