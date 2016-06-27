package com.herprogramacion.iwish.ui.actividades;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import com.herprogramacion.iwish.ui.fragmentos.MainFragment;
import com.herprogramacion.iwish.web.VolleySingleton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Actividad principal que contiene un fragmento con una lista.
 * Recuerda que la nueva librería de soporte reemplazó la clase
 * {@link android.support.v7.app.ActionBarActivity} por
 * {@link AppCompatActivity} para el uso de la action bar
 * en versiones antiguas.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String user;
    private String APP_DIRECTORY = "myPictureApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int SELECT_PICTURE = 200;
    private final int PHOTO_CODE = 100;

    Uri path;
    Httppostaux post;
    JSONArray jdata;
    String URL_connect = Constantes.INSERT_FOTO;
    int logstatus = -1;
    private Gson gson = new Gson();
    /**
     * Etiqueta de depuración
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = getIntent().getExtras().getString(Constantes.NOMBRE_USER);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        cargarDatos();

        View headView = navigationView.getHeaderView(0);

        ((TextView) headView.findViewById(R.id.textViewHeaderMenu)).setText(user);


        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Mis asignaturas");

        // Creación del fragmento principal
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, MainFragment.createInstance(user), "MainFragment")
                    .commit();
        }
    }

    /**
     * Obtiene los datos desde el servidor
     */
    public void cargarDatos() {

        // Añadir parámetro a la URL del web service
        String newURL = Constantes.OBTENER_FOTO + "?email=" + user;

        // Realizar petición DETALLADO
        VolleySingleton.getInstance(this).addToRequestQueue(
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

                    if(meta.getFoto() != null) {

                        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        navigationView.setNavigationItemSelectedListener(this);
                        View headView = navigationView.getHeaderView(0);
                        ((ImageView) headView.findViewById(R.id.imageViewMenu)).setImageURI(Uri.parse(meta.getFoto()));
                    }

                    break;

                case "2":
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            this,
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;

                case "3":
                    String mensaje3 = response.getString("mensaje");
                    Toast.makeText(
                            this,
                            mensaje3,
                            Toast.LENGTH_LONG).show();
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        user = getIntent().getExtras().getString(Constantes.NOMBRE_USER);
        if (requestCode == Constantes.CODIGO_DETALLE || requestCode == 3) {
            if (resultCode == RESULT_OK || resultCode == 203) {
                MainFragment fragment = (MainFragment) getSupportFragmentManager().
                        findFragmentByTag("MainFragment");
                fragment.cargarAdaptador(user);
            }
        }

        post=new Httppostaux();

        switch (requestCode){
            case PHOTO_CODE:
                if(resultCode == RESULT_OK){
                    String dir = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator +
                            TEMPORAL_PICTURE_NAME;
                    decodeBitMap(dir);
                    new asynclogin().execute(dir, user);
                }
                break;
            case SELECT_PICTURE:
                if(resultCode == RESULT_OK){
                    path = data.getData();
                    new asynclogin().execute(path.toString(), user);
                    final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    navigationView.setNavigationItemSelectedListener(this);
                    View headView = navigationView.getHeaderView(0);
                    ((ImageView) headView.findViewById(R.id.imageViewMenu)).setImageURI(path);
                }
                break;
        }
    }

    private void decodeBitMap(String dir) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
         navigationView.setNavigationItemSelectedListener(this);
         View headView = navigationView.getHeaderView(0);
         ((ImageView) headView.findViewById(R.id.imageViewMenu)).setImageBitmap(bitmap);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara || id == R.id.nav_estadisticas || id == R.id.nav_reggrupo || id == R.id.nav_posgrupo) {
            try {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                intent.putExtra(Constantes.NOMBRE_USER, user);
                startActivityForResult(intent, 3);
            }
            catch (ActivityNotFoundException exception) {
                Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else if (id == R.id.nav_slideshow) {
            Intent i=new Intent(MainActivity.this, MainActivity.class);
            i.putExtra(Constantes.NOMBRE_USER, user);
            startActivity(i);
        }
        else if (id == R.id.nav_misgrupos){
            Intent i=new Intent(MainActivity.this, GroupActivity.class);
            i.putExtra(Constantes.NOMBRE_USER, user);
            startActivity(i);

        }
        else if (id == R.id.nav_ajustes) {
            final CharSequence[] options = {"Tomar foto", "Elegir de galería", "Cancelar"};
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Elige una opción");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (options[which] == "Tomar foto") {
                        openCamera();
                    } else if (options[which] == "Elegir de galería") {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent.createChooser(intent, "Selecciona aap de imagen"), SELECT_PICTURE);
                    } else if (options[which] == "Cancelar") {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
        else if (id == R.id.nav_loggout) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdirs();

        String path = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator +
                TEMPORAL_PICTURE_NAME;

        File newFile = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        startActivityForResult(intent, PHOTO_CODE);
    }

    /*Valida el estado de registro, necesita como parametros los necesarios de la tabla de alumnos*/
    public boolean registrostatus(String ruta, String email) {

    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();

        postparameters2send.add(new BasicNameValuePair("ruta",ruta));
        postparameters2send.add(new BasicNameValuePair("emailAlumno",email));

        //realizamos una peticion y como respuesta obtenes un array JSON
        jdata= post.getserverdata(postparameters2send, URL_connect);

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
            if (logstatus == 0 ) {// [{"logstatus":"0"}]
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

    class asynclogin extends AsyncTask< String, String, String > {

        String ruta, emailUser;
        protected void onPreExecute() {
        }

        protected String doInBackground(String... params) {
            ruta=params[0];
            emailUser=params[1];

            //enviamos y recibimos y analizamos los datos en segundo plano.
            if (registrostatus(ruta, emailUser)==true){
                return "ok"; //registro valido
            }else{
                return "err"; //registro invalido
            }

        }

        /*Una vez terminado doInBackground segun lo que halla ocurrido
        pasamos a la sig. activity
        o mostramos error*/
        protected void onPostExecute(String result) {

        }

    }
}