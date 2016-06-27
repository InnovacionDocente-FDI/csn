package com.herprogramacion.iwish.ui.actividades;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.google.gson.Gson;
import com.herprogramacion.iwish.R;
import com.herprogramacion.iwish.modelo.Estadistica;
import com.herprogramacion.iwish.modelo.Meta;
import com.herprogramacion.iwish.tools.Constantes;
import com.herprogramacion.iwish.web.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nuria on 20/02/2016.
 */
public class UpdateActivity extends AppCompatActivity {

    private static final String TAG = UpdateActivity.class.getSimpleName();
    private String idAsignatura;
    private String contPositivos;
    private String[] xData = { "Máximo", "Tú" };


    /**
     * Instancia Gson para el parsing Json
     */
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        idAsignatura = getIntent().getStringExtra(Constantes.EXTRA_ID);
        contPositivos = getIntent().getStringExtra(Constantes.POS_ALUMNO_LOGADO);

        if (getSupportActionBar() != null) {
            // Dehabilitar titulo de la actividad
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // Quitar el botón del menú superior
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        if (idAsignatura != null && contPositivos != null) {
            cargarDatos();
        }
    }

    /**
     * Obtiene los datos desde el servidor
     */
    private void cargarDatos() {
        // Añadiendo idMeta como parámetro a la URL
        String newURL = Constantes.RANKING_MAXIMO + "?idMeta=" + idAsignatura;

        // Consultar el detalle de la meta
        VolleySingleton.getInstance(UpdateActivity.this).addToRequestQueue(
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
                    JSONObject estadisticas = response.getJSONObject("meta");
                    //Parsear objeto
                    Estadistica meta = gson.fromJson(estadisticas.toString(), Estadistica.class);

                    cargarViews(meta);
                    break;

                case "2":
                    String mensaje = response.getString("mensaje");
                    // Mostrar mensaje
                    Toast.makeText(
                            UpdateActivity.this,
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de falla
                    UpdateActivity.this.setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    UpdateActivity.this.finish();
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
    private void cargarViews(Estadistica meta) {
        PieChart mChart = new PieChart(getApplicationContext());
        setContentView(mChart);

        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("Tú frente al Máximo");

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        ArrayList<Entry> entradas = new ArrayList<Entry>();
        // Obteniendo la posición del spinner categorias
        entradas.add(new Entry(Integer.parseInt(meta.getMaximo()), 0));
        entradas.add(new Entry(Integer.parseInt(contPositivos), 1));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(entradas, "Leyenda");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();

        // customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }
}
