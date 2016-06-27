package com.herprogramacion.iwish.ui.actividades;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.herprogramacion.iwish.R;
import com.herprogramacion.iwish.modelo.Estadistica;
import com.herprogramacion.iwish.modelo.Meta;
import com.herprogramacion.iwish.tools.Constantes;
import com.herprogramacion.iwish.web.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nuria on 11/03/2016.
 */
public class UpdateActivity2 extends AppCompatActivity {

    private static final String TAG = UpdateActivity2.class.getSimpleName();
    private String idAsignatura;
    private String emailAlumno;

    /**
     * Instancia Gson para el parsing Json
     */
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        idAsignatura = getIntent().getStringExtra(Constantes.EXTRA_ID);
        emailAlumno = getIntent().getStringExtra(Constantes.NOMBRE_USER);

        if (getSupportActionBar() != null) {
            // Dehabilitar titulo de la actividad
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // Quitar el botón del menú superior
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        if (idAsignatura != null && emailAlumno != null) {
            cargarDatos();
        }
    }

    /**
     * Obtiene los datos desde el servidor
     */
    private void cargarDatos() {
        // Añadiendo idMeta como parámetro a la URL
        String newURL = Constantes.RANKING_ASIGNATURAS + "?idMeta=" + idAsignatura;

        // Consultar el detalle de la meta
        VolleySingleton.getInstance(UpdateActivity2.this).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesa la respuesta GET_BY_ID
                                procesarRespuestaGet(response);
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
     * Procesa la respuesta de obtención obtenida desde el sevidor     *
     */
    private void procesarRespuestaGet(JSONObject response) {

        try {
            String estado = response.getString("estado");

            switch (estado) {
                case "1":
                    // Obtener array "metas" Json
                    JSONArray estadisticas = response.getJSONArray("meta");
                    // Parsear con Gson
                    Estadistica[] metas = gson.fromJson(estadisticas.toString(), Estadistica[].class);

                    cargarViews(metas);
                    break;

                case "2":
                    String mensaje = response.getString("mensaje");
                    // Mostrar mensaje
                    Toast.makeText(
                            UpdateActivity2.this,
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de falla
                    UpdateActivity2.this.setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    UpdateActivity2.this.finish();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga los datos iniciales del formulario con los
     * atributos de un objeto {@link Meta}
     *
     * @param meta Instancia
     */
    private void cargarViews(Estadistica[] meta) {
        ArrayList<BarEntry> entradas = new ArrayList<>();
        // Obteniendo la posición del spinner categorias
        for (int i = 0; i < meta.length; i++) {
            entradas.add(new BarEntry(Integer.parseInt(meta[i].getPositivosAlumno()), i));
        }

        BarDataSet dataset = new BarDataSet(entradas, "Ranking de positivos");
        ArrayList<String> etiquetas = new ArrayList<String>();
        for (int j = 0; j < meta.length; j++) {
            if (meta[j].getEmailAlumno().equals(emailAlumno)) {
                etiquetas.add("Tú " + (j + 1));
            } else
                etiquetas.add("Alumno " + (j + 1));
        }

        BarChart grafica = new BarChart(getApplicationContext());
        setContentView(grafica);

        BarData datos = new BarData(etiquetas, dataset);

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        grafica.setData(datos);

    }
}