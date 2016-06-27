package com.herprogramacion.iwish.ui.actividades;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.herprogramacion.iwish.Library.Httppostaux;
import com.herprogramacion.iwish.R;
import com.herprogramacion.iwish.tools.Constantes;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Nuria on 10/01/2016.
 */
public class SignupActivity extends AppCompatActivity {

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_apellidos) EditText _apellidosText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    Httppostaux post;

    String URL_connect = Constantes.SIGNUP;

    private ProgressDialog pDialog;
    int logstatus = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        post=new Httppostaux();

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extreamos datos de los EditText
                String usuarionombre=_nameText.getText().toString();
                String usuarioapaellido=_apellidosText.getText().toString();
                String usuarioemail=_emailText.getText().toString();
                String passw=_passwordText.getText().toString();

                //verificamos si estan en blanco
                if( validate(usuarionombre, usuarioapaellido, usuarioemail, passw)==true){

                    //si pasamos esa validacion ejecutamos el asynctask pasando el usuario y clave como parametros

                    new asynclogin().execute(usuarionombre, usuarioapaellido, usuarioemail, passw);


                }else{
                    //si detecto un error en la primera validacion vibrar y mostrar un Toast con un mensaje de error.
                    err_login();
                }
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public boolean validate(String name, String apellido, String email, String password) {
        boolean valid = true;


        if (name.isEmpty()) {
            _nameText.setError("Es necesario rellenar este campo");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (apellido.isEmpty()) {
            _apellidosText.setError("Es necesario rellenar este campo");
            valid = false;
        } else {
            _apellidosText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Necesario introducir un email válido");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Contraseña entre 4 y 10 caracteres");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    /*Valida el estado de registro, necesita como parametros los necesarios de la tabla de alumnos*/
    public boolean registrostatus(String username, String userape, String mailuser, String password) {

    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();

        postparameters2send.add(new BasicNameValuePair("nombre",username));
        postparameters2send.add(new BasicNameValuePair("apellidos",userape));
        postparameters2send.add(new BasicNameValuePair("email",mailuser));
        postparameters2send.add(new BasicNameValuePair("password",password));


        //realizamos una peticion y como respuesta obtenes un array JSON
        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);

        //si lo que obtuvimos no es null
        if (jdata != null && jdata.length() > 0) {

            JSONObject json_data; //creamos un objeto JSON
            try {
                json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
                logstatus = json_data.getInt("logstatus");//accedemos al valor
                Log.e("loginstatus", "logstatus= " + logstatus);//muestro por log que obtuvimos
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //validamos el valor obtenido
            if (logstatus == 0 || logstatus == 2) {// [{"logstatus":"0"}]
                Log.e("loginstatus ", "invalido");
                return false;
            } else {// [{"logstatus":"1"}]
                Log.e("loginstatus ", "valido");
                return true;
            }

        } else {    //json obtenido invalido verificar parte WEB.
            Log.e("JSON  ", "ERROR");
            return false;
        }
    }

    //vibra y muestra un Toast
    public void err_login() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast toast1 = Toast.makeText(getApplicationContext(), "Error: No se pudo registrar", Toast.LENGTH_SHORT);
        toast1.show();
    }

    //vibra y muestra un Toast
    public void err_loginYaexiste() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast toast1 = Toast.makeText(getApplicationContext(), "Error: El email ya existe en la base de datos", Toast.LENGTH_SHORT);
        toast1.show();
    }

    /*		CLASE ASYNCTASK
 *
 * usaremos esta para poder mostrar el dialogo de progreso mientras enviamos y obtenemos los datos
 * podria hacerse lo mismo sin usar esto pero si el tiempo de respuesta es demasiado lo que podria ocurrir
 * si la conexion es lenta o el servidor tarda en responder la aplicacion sera inestable.
 * ademas observariamos el mensaje de que la app no responde.
 */

    class asynclogin extends AsyncTask< String, String, String > {

        String user, apellidoUser, emailUser ,pass;
        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Creating Account...");
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            //obtnemos usr y pass
            user=params[0];
            apellidoUser=params[1];
            emailUser=params[2];
            pass=params[3];


            //enviamos y recibimos y analizamos los datos en segundo plano.
            if (registrostatus(user, apellidoUser, emailUser, pass)==true){
                return "ok"; //registro valido
            }else{
                return "err"; //registro invalido
            }

        }

        /*Una vez terminado doInBackground segun lo que halla ocurrido
        pasamos a la sig. activity
        o mostramos error*/
        protected void onPostExecute(String result) {

            pDialog.dismiss();//ocultamos progess dialog.
            Log.e("onPostExecute=", "" + result);

            if (result.equals("ok")){

                Intent i=new Intent(SignupActivity.this, MainActivity.class);
                i.putExtra(Constantes.NOMBRE_USER, emailUser);
                startActivity(i);

            }else
                if (logstatus == 0)
                    err_login();
                else
                    err_loginYaexiste();

        }

    }
}
