package com.herprogramacion.iwish.ui.fragmentos;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.herprogramacion.iwish.Library.Httppostaux;
import com.herprogramacion.iwish.R;
import com.herprogramacion.iwish.modelo.Meta;
import com.herprogramacion.iwish.tools.Constantes;
import com.herprogramacion.iwish.ui.actividades.DetailGroupActivity;
import com.herprogramacion.iwish.ui.actividades.GroupActivity;
import com.herprogramacion.iwish.ui.actividades.UpdateActivity;
import com.herprogramacion.iwish.ui.actividades.UpdateActivity2;
import com.herprogramacion.iwish.ui.actividades.UpdateActivity3;
import com.herprogramacion.iwish.web.VolleySingleton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    /**
     * Etiqueta de depuración
     */
    private static final String TAG = DetailFragment.class.getSimpleName();

    /*
    Instancias de Views
     */
    private TextView titulo;
    private TextView grupo;
    private TextView nombreProfe;
    private TextView apellidoProfe;
    private ImageButton editButtonDerecha;
    private ImageButton editButtonCentro;
    private ImageButton editButtonIzquierda;
    private TextView fecha_text;
    private TextView nombreGrupoText;
    private String extra_id;
    private String extra_email;
    private Gson gson = new Gson();
    String logstatus;


    JSONArray jdata;
    Httppostaux post;
    String URL_connect = Constantes.CONTADOR;

    public DetailFragment() {
    }

    public static DetailFragment createInstance(String idMeta, String user) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constantes.EXTRA_ID, idMeta);
        bundle.putString(Constantes.NOMBRE_USER, user);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        // Obtención de views
        titulo = (TextView) v.findViewById(R.id.nombre);
        grupo = (TextView) v.findViewById(R.id.grupo);
        nombreProfe = (TextView) v.findViewById(R.id.nombreprofe);
        apellidoProfe = (TextView) v.findViewById(R.id.apellidoprofe);
        editButtonDerecha = (ImageButton) v.findViewById(R.id.fab);
        editButtonCentro = (ImageButton) v.findViewById(R.id.fab3);
        editButtonIzquierda = (ImageButton) v.findViewById(R.id.fab2);
        fecha_text = (TextView) v.findViewById(R.id.fecha_ejemplo_text);
        nombreGrupoText = (TextView) v.findViewById(R.id.nameGroupText);
        // Obtener extra del intent de envío
        extra_id = getArguments().getString(Constantes.EXTRA_ID);
        extra_email = getArguments().getString(Constantes.NOMBRE_USER);

        if (extra_id != null && extra_email != null) {
            // Cargar datos desde el web service
            cargarDatos();
            post=new Httppostaux();
            new asynclogin().execute(extra_id, extra_email);
        }

        // Setear escucha para el fab
        editButtonDerecha.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de actualización
                        Intent i = new Intent(getActivity(), UpdateActivity.class);
                        i.putExtra(Constantes.EXTRA_ID, extra_id);
                        i.putExtra(Constantes.NOMBRE_USER, extra_email);
                        i.putExtra(Constantes.POS_ALUMNO_LOGADO, logstatus);
                        getActivity().startActivityForResult(i, Constantes.CODIGO_ACTUALIZACION);
                    }
                }
        );

        // Setear escucha para el fab
        editButtonCentro.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de actualización
                        Intent i = new Intent(getActivity(), UpdateActivity2.class);
                        i.putExtra(Constantes.EXTRA_ID, extra_id);
                        i.putExtra(Constantes.NOMBRE_USER, extra_email);
                        i.putExtra(Constantes.POS_ALUMNO_LOGADO, logstatus);
                        getActivity().startActivityForResult(i, Constantes.CODIGO_ACTUALIZACION2);
                    }
                }
        );

        // Setear escucha para el fab
        editButtonIzquierda.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de actualización
                        Intent i = new Intent(getActivity(), UpdateActivity3.class);
                        i.putExtra(Constantes.EXTRA_ID, extra_id);
                        i.putExtra(Constantes.NOMBRE_USER, extra_email);
                        i.putExtra(Constantes.POS_ALUMNO_LOGADO, logstatus);
                        getActivity().startActivityForResult(i, Constantes.CODIGO_ACTUALIZACION3);
                    }
                }
        );

        return v;
    }

    /**
     * Obtiene los datos desde el servidor
     */
    public void cargarDatos() {

        // Añadir parámetro a la URL del web service
        String newURL = Constantes.DETALLADO + "?idMeta=" + extra_id + "?&emailUser=" + extra_email;

        // Realizar petición DETALLADO
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }
                )
        );
    }

    /**
     * Procesa cada uno de los estados posibles de la
     * respuesta enviada desde el servidor
     *
     * @param response Objeto Json
     */
    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener atributo "mensaje"
            String mensaje = response.getString("estado");

            switch (mensaje) {
                case "1":
                    // Obtener objeto "meta"
                    JSONObject object = response.getJSONObject("meta");

                    //Parsear objeto 
                    Meta meta = gson.fromJson(object.toString(), Meta.class);

                    // Seteando valores en los views
                    titulo.setText(meta.getNombre());
                    grupo.setText(meta.getGrupo());
                    nombreProfe.setText(meta.getNombreProfe());
                    apellidoProfe.setText(meta.getApellidos());
                    nombreGrupoText.setText(meta.getNombreGrupo());

                    if(nombreGrupoText.getText().equals(""))
                        nombreGrupoText.setText("Asignatura individual");

                    nombreGrupoText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (nombreGrupoText.getText().equals("Asignatura individual")){
                                Toast toast1 = Toast.makeText(getActivity(), "No tiene grupo asignado", Toast.LENGTH_SHORT);
                                toast1.show();
                            }
                            else {
                                Intent i = new Intent(getActivity(), GroupActivity.class);
                                i.putExtra(Constantes.EXTRA_ID, extra_id);
                                i.putExtra(Constantes.NOMBRE_USER, extra_email);
                                getActivity().startActivityForResult(i, Constantes.CODIGO_ACTUALIZACION3);
                            }

                        }
                    });

                    break;

                case "2":
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;

                case "3":
                    String mensaje3 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje3,
                            Toast.LENGTH_LONG).show();
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*Valida el estado de registro, necesita como parametros los necesarios de la tabla de alumnos*/
    public boolean registrostatus(String idAsig, String email) {


    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();

        postparameters2send.add(new BasicNameValuePair("cadena",idAsig));
        postparameters2send.add(new BasicNameValuePair("emailAlumno", email));

        //realizamos una peticion y como respuesta obtenes un array JSON
        jdata= post.getserverdata(postparameters2send, URL_connect);

        //si lo que obtuvimos no es null
        if (jdata != null && jdata.length() > 0) {

            JSONObject json_data; //creamos un objeto JSON
            try {
                json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
                logstatus = json_data.getString("logstatus");//accedemos al valor
                Log.e("loginstatus", "logstatus= " + logstatus);//muestro por log que obtuvimos
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //validamos el valor obtenido
            if (logstatus.equals(-1)) {// [{"logstatus":"-1"}]
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

        String idAsig, emailUser;

        protected String doInBackground(String... params) {
            idAsig=params[0];
            emailUser=params[1];


            //enviamos y recibimos y analizamos los datos en segundo plano.
            if (registrostatus(idAsig, emailUser)==true){
                return "ok"; //registro valido
            }else{
                return "err"; //registro invalido
            }

        }

        /*Una vez terminado doInBackground segun lo que halla ocurrido
        pasamos a la sig. activity
        o mostramos error*/
        protected void onPostExecute(String result) {
            Log.e("onPostExecute=", "" + result);

            if (result.equals("ok")){
                fecha_text.setText(logstatus);

            }else{
                Toast.makeText(getActivity(), "Este código ya está escaneado", Toast.LENGTH_LONG).show();
            }
        }

    }
}
