package com.herprogramacion.iwish.ui.actividades;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herprogramacion.iwish.Library.Httppostaux;
import com.herprogramacion.iwish.R;
import com.herprogramacion.iwish.modelo.Grupo;

import com.herprogramacion.iwish.tools.Constantes;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.apptik.widget.MultiSlider;

/**
 * Created by Nuria on 24/03/2016.
 */
public class VoteActivity extends Activity {
    private ProgressDialog pDialog;
    Grupo[] grupo;
    String[] valor = new String[2000];
    String[] valoresDefinitivos = new String[15];
    String[] nombresAlmacenados = new String[15];
    int contador = 0;
    int sumatorio = 0;
    private com.melnykov.fab.FloatingActionButton editButtonIzquierda;
    private String extra_id;
    private String extra_email;
    private String extra_idPositivoGrupo;
    Httppostaux post;
    String URL_connect = Constantes.VOTAR;
    int fin=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.probando);

        post = new Httppostaux();

        // Parent layout
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.layout);
        TextView nameGru = (TextView) findViewById(R.id.nombreGrupo);

        // Layout inflater
        LayoutInflater layoutInflater = getLayoutInflater();
        View view;

        grupo = (Grupo[]) getIntent().getExtras().getSerializable("array");

        nameGru.setText(grupo[0].getNombreGrupo());


        for (int i = 0; i < grupo.length; i++) {
            // Add the text layout to the parent layout
            view = layoutInflater.inflate(R.layout.activity_my, parentLayout, false);

            // In order to get the view we have to use the new view with text_layout in it
            LinearLayout textView = (LinearLayout) view.findViewById(R.id.text);
            final TextView min2 = (TextView) view.findViewById(R.id.minValue2);
            final TextView nombreInt = (TextView) view.findViewById(R.id.nombreIntegrante);
            MultiSlider multiSlider2 = (MultiSlider) view.findViewById(R.id.multiSlider2);

            min2.setText("");
            nombreInt.setText(grupo[i].getNombre());

            multiSlider2.setOnThumbValueChangeListener(new MultiSlider.SimpleOnThumbValueChangeListener() {
                @Override
                public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                    min2.setText(String.valueOf(value));
                }

                @Override
                public void onStopTrackingTouch(MultiSlider multiSlider, MultiSlider.Thumb thumb, int value) {
                    min2.setText(String.valueOf(value));
                    valor[contador] = min2.getText().toString() + '-' + nombreInt.getText();
                    contador++;
                }
            });
            // Add the text view to the parent layout
            parentLayout.addView(textView);
        }

        editButtonIzquierda = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.votacion);

        // Setear escucha para el fab
        editButtonIzquierda.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int parar = 0;
                        String nombre;
                        String puntuacion;

                        for (int r = 0; r < nombresAlmacenados.length; r++) {
                            nombresAlmacenados[r] = "null";
                        }

                        for (int i = contador - 1; parar != grupo.length; i--) {
                            puntuacion = valor[i].substring(0, valor[i].indexOf("-"));
                            nombre = valor[i].substring(valor[i].indexOf("-") + 1, valor[i].length());
                            int j = 0;
                            boolean encontrado = false;
                            while (!encontrado) {
                                if (nombresAlmacenados[j].equals(nombre)) {
                                    encontrado = true;
                                } else {
                                    if (!nombresAlmacenados[j].equals("null"))
                                        j++;
                                    else {
                                        nombresAlmacenados[j] = nombre;
                                        valoresDefinitivos[j] = puntuacion;
                                        encontrado = true;
                                        sumatorio = sumatorio + Integer.parseInt(puntuacion);
                                        parar++;
                                    }
                                }
                            }
                        }
                        if (sumatorio > 100) {
                            sumatorio = 0;
                            Toast toast1 = Toast.makeText(getApplicationContext(), "El sumatorio es mayor del 100%", Toast.LENGTH_SHORT);
                            toast1.show();
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            startActivity(intent);
                        } else if (sumatorio != 100) {
                            sumatorio = 0;
                            Toast toast1 = Toast.makeText(getApplicationContext(), "El sumatorio tiene que ser 100%", Toast.LENGTH_SHORT);
                            toast1.show();
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            startActivity(intent);
                        } else {
                            cargarDatos();

                        }

                    }
                }
        );

    }

    public void cargarDatos() {
        extra_id = getIntent().getExtras().getString(Constantes.EXTRA_GRUPO_ID); //No es el id del grupo, es el id del positivo
        extra_email = getIntent().getExtras().getString(Constantes.NOMBRE_USER);
        extra_idPositivoGrupo = getIntent().getExtras().getString(Constantes.ID_POS_GRUPO);
        // Añadir parámetro a la URL del web service
        for (int i = 0; i < grupo.length; i++) {
            new asynclogin().execute(extra_idPositivoGrupo, nombresAlmacenados[i], valoresDefinitivos[i], extra_id, extra_email);

        }
    }

    //vibra y muestra un Toast
    public void err_login() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast toast1 = Toast.makeText(getApplicationContext(), "Error al insertar votación", Toast.LENGTH_SHORT);
        toast1.show();
    }

    //vibra y muestra un Toast
    public void med_login() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast toast1 = Toast.makeText(getApplicationContext(), "Procesando...", Toast.LENGTH_SHORT);
        toast1.show();
    }

    /*Valida el estado del logueo solamente necesita como parametros el usuario y passw*/
    public boolean loginstatus(String idPos, String votado, String puntu, String idGrupo, String votante) {
        int logstatus = -1;

    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();

        postparameters2send.add(new BasicNameValuePair("idPositivoGrupo", idPos));
        postparameters2send.add(new BasicNameValuePair("idAlumnoVotado", votado));
        postparameters2send.add(new BasicNameValuePair("tantoPorCiento", puntu));
        postparameters2send.add(new BasicNameValuePair("idGrupo", idGrupo));
        postparameters2send.add(new BasicNameValuePair("idAlumnoVotante", votante));

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

    class asynclogin extends AsyncTask<String, String, String> {

        String votado, votante, puntu, idPos, idGrupo;

        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(VoteActivity.this, R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Insertando votación....");
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            idPos = params[0];
            votado = params[1];
            puntu = params[2];
            idGrupo = params[3];
            votante = params[4];
            loginstatus(idPos, votado, puntu, idGrupo, votante);
            //enviamos y recibimos y analizamos los datos en segundo plano.
            if (fin == grupo.length) {
                return "ok"; //login valido
            } else {
                fin++;
                return "med"; //login invalido
            }
        }

        /*Una vez terminado doInBackground segun lo que halla ocurrido
        pasamos a la sig. activity
        o mostramos error*/
       protected void onPostExecute(String result) {

            if (result.equals("ok")) {

                pDialog.dismiss();//ocultamos progess dialog.
                Intent i = new Intent(VoteActivity.this, DetailGroupActivity.class);
                i.putExtra(Constantes.EXTRA_GRUPO_ID, extra_id);
                i.putExtra(Constantes.NOMBRE_USER, extra_email);
                startActivity(i);

            }
            else if(result.equals("med"))
                med_login();
            else {
                err_login();
            }

        }

    }
}
