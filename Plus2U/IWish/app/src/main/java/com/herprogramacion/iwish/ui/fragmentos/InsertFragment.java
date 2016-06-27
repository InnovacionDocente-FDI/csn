package com.herprogramacion.iwish.ui.fragmentos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.herprogramacion.iwish.Library.Httppostaux;
import com.herprogramacion.iwish.R;
import com.herprogramacion.iwish.tools.Constantes;
import com.herprogramacion.iwish.ui.actividades.DetailActivity;
import com.herprogramacion.iwish.ui.actividades.DetailGroupActivity;
import com.herprogramacion.iwish.ui.actividades.GroupActivity;
import com.herprogramacion.iwish.ui.actividades.VoteActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Fragmento que permite al usuario insertar un nueva meta
 */
public class InsertFragment extends Fragment {

    Httppostaux post;

    String URL_connect = Constantes.INSERT;
    String URL_connect2 = Constantes.INSERT_POSITIVO;
    String URL_connect3 = Constantes.INSERT_REGISTROGRUPO;
    String URL_connect4 = Constantes.INSERT_POSITIVOGRUPO;
    int logstatus = -1;
    private ProgressDialog pDialog;
    JSONArray jdata;


    public InsertFragment() {
    }

    public static InsertFragment createInstance(String nombre, String user) {
        InsertFragment detailFragment = new InsertFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constantes.NOMBRE_USER, user);
        bundle.putString("name", nombre);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String email = getArguments().getString(Constantes.NOMBRE_USER);
        final String code = getArguments().getString("name");
        final String codefirst=code.substring(0,code.length()-14); //cojo el primer número del código para saber si es cadena de registro o positivo
        post=new Httppostaux();
        new asynclogin().execute(code, email, codefirst); //code =0, email=1, codefirst=2
    }

    /*Valida el estado de registro, necesita como parametros los necesarios de la tabla de alumnos*/
    public boolean registrostatus(String code, String email, String codefirst) {

    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();

        postparameters2send.add(new BasicNameValuePair("cadena",code));
        postparameters2send.add(new BasicNameValuePair("emailAlumno",email));
        postparameters2send.add(new BasicNameValuePair("codefirst",codefirst));

        switch (codefirst) {
            case "1":
                //realizamos una peticion y como respuesta obtenes un array JSON
                jdata= post.getserverdata(postparameters2send, URL_connect);
                break;
            case "0":
                jdata = post.getserverdata(postparameters2send, URL_connect2);
                break;
            case "2":
                jdata = post.getserverdata(postparameters2send, URL_connect3);
                break;
            case "3":
                jdata = post.getserverdata(postparameters2send, URL_connect4);
                break;
        }

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
            if (logstatus == 0 || logstatus == -2 || logstatus == -3 || logstatus == -4 || logstatus == -5) {// [{"logstatus":"0"}]
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

        String code, emailUser, codeFirst;
        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Obteniendo resultados de escaneo...");
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            code=params[0];
            emailUser=params[1];
            codeFirst=params[2]; //para saber el valor del primer número de la cadena


            //enviamos y recibimos y analizamos los datos en segundo plano.
            if (registrostatus(code, emailUser, codeFirst)==true){
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
                Toast.makeText(getActivity(), "Código añadido con éxito", Toast.LENGTH_LONG).show();
                if(logstatus > 0){
                    Intent i=new Intent(getActivity(), GroupActivity.class);
                    i.putExtra(Constantes.EXTRA_GRUPO_ID, logstatus);
                    i.putExtra(Constantes.NOMBRE_USER, emailUser);
                    i.putExtra(Constantes.ID_POS_GRUPO, code);
                    startActivity(i);
                    Toast.makeText(getActivity(), "Selecciona tu grupo y vota!", Toast.LENGTH_LONG).show();
                }

            }else{
                switch (logstatus) {
                    case 0:
                        Toast.makeText(getActivity(), "ERROR al realizar esta operación", Toast.LENGTH_LONG).show();
                        break;
                    case -3:
                        Toast.makeText(getActivity(), "Ya te encuentras registrado en esta asignatura", Toast.LENGTH_LONG).show();
                        break;
                    case -4:
                        Toast.makeText(getActivity(), "Ya has escaneado este positivo anteriormente", Toast.LENGTH_LONG).show();
                        break;
                    case -5:
                        Toast.makeText(getActivity(), "Ya te encuentras registrado en esta asignatura y grupo", Toast.LENGTH_LONG).show();
                        break;
                    case -2:
                        Toast.makeText(getActivity(), "Ya has escaneado este positivo grupal anteriormente", Toast.LENGTH_LONG).show();
                        break;
                }
            }

        }

    }

}
