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
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.edusuario) EditText _emailText;
    @InjectView(R.id.edpassword) EditText _passwordText;
    @InjectView(R.id.Blogin) Button _loginButton;
    @InjectView(R.id.BRegistro) TextView _signupLink;
    Httppostaux post;

    String URL_connect = Constantes.LOGIN;

    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        post = new Httppostaux();

        _emailText = (EditText) findViewById(R.id.edusuario);
        _passwordText = (EditText) findViewById(R.id.edpassword);
        _loginButton = (Button) findViewById(R.id.Blogin);
        _signupLink = (TextView) findViewById(R.id.BRegistro);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                //Extreamos datos de los EditText
                String usuario = _emailText.getText().toString();
                String passw = _passwordText.getText().toString();

                //verificamos si estan en blanco
                if (checklogindata(usuario, passw) == true) {

                    //si pasamos esa validacion ejecutamos el asynctask pasando el usuario y clave como parametros

                    new asynclogin().execute(usuario, passw);


                } else {
                    //si detecto un error en la primera validacion vibrar y mostrar un Toast con un mensaje de error.
                    err_login();
                }

            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    //validamos si no hay ningun campo en blanco
    public boolean checklogindata(String username, String password) {

        if (username.equals("") || password.equals("")) {
            Log.e("Login ui", "checklogindata user or pass error");
            return false;

        } else {

            return true;
        }

    }

    //vibra y muestra un Toast
    public void err_login() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast toast1 = Toast.makeText(getApplicationContext(), "Error: Nombre de usuario o password incorrectos", Toast.LENGTH_SHORT);
        toast1.show();
    }

    /*Valida el estado del logueo solamente necesita como parametros el usuario y passw*/
    public boolean loginstatus(String username, String password) {
        int logstatus = -1;

    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();

        postparameters2send.add(new BasicNameValuePair("email", username));
        postparameters2send.add(new BasicNameValuePair("password", password));

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
            if (logstatus == 0) {// [{"logstatus":"0"}]
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

/*		CLASE ASYNCTASK
 *
 * usaremos esta para poder mostrar el dialogo de progreso mientras enviamos y obtenemos los datos
 * podria hacerse lo mismo sin usar esto pero si el tiempo de respuesta es demasiado lo que podria ocurrir
 * si la conexion es lenta o el servidor tarda en responder la aplicacion sera inestable.
 * ademas observariamos el mensaje de que la app no responde.
 */

    class asynclogin extends AsyncTask< String, String, String > {

        String user,pass;
        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Autenticando....");
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            //obtnemos usr y pass
            user=params[0];
            pass=params[1];

            //enviamos y recibimos y analizamos los datos en segundo plano.
            if (loginstatus(user,pass)==true){
                return "ok"; //login valido
            }else{
                return "err"; //login invalido
            }

        }

        /*Una vez terminado doInBackground segun lo que halla ocurrido
        pasamos a la sig. activity
        o mostramos error*/
        protected void onPostExecute(String result) {

            pDialog.dismiss();//ocultamos progess dialog.
            Log.e("onPostExecute=", "" + result);

            if (result.equals("ok")){

                Intent i=new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra(Constantes.NOMBRE_USER, user);
                startActivity(i);

            }else{
                err_login();
            }

        }

    }
}
