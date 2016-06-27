package com.herprogramacion.iwish.ui.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.herprogramacion.iwish.R;
import com.herprogramacion.iwish.modelo.Grupo;
import com.herprogramacion.iwish.tools.Constantes;
import com.herprogramacion.iwish.ui.GrupoAdapter;
import com.herprogramacion.iwish.ui.actividades.InsertActivity;
import com.herprogramacion.iwish.web.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Nuria on 24/03/2016.
 */
public class GroupFragment extends Fragment {

    /*
    Etiqueta de depuracion
     */
    private static final String TAG = GroupFragment.class.getSimpleName();

    /*
    Adaptador del recycler view
     */
    private GrupoAdapter adapter;

    /*
    Instancia global del recycler view
     */
    private RecyclerView lista;

    /*
    instancia global del administrador
     */
    private RecyclerView.LayoutManager lManager;

    /*
    Instancia global del FAB
     */
    com.melnykov.fab.FloatingActionButton fab;
    private Gson gson = new Gson();

    public GroupFragment() {
    }

    public static GroupFragment createInstance(String user, String idPos) {
        GroupFragment detailFragment = new GroupFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constantes.NOMBRE_USER, user);
        bundle.putString(Constantes.ID_POS_GRUPO, idPos);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final String extra = getArguments().getString(Constantes.NOMBRE_USER);

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        lista = (RecyclerView) v.findViewById(R.id.reciclador);
        lista.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());
        lista.setLayoutManager(lManager);

        // Cargar datos en el adaptador
        cargarAdaptador(extra);

        // Obtener instancia del FAB
        fab = (com.melnykov.fab.FloatingActionButton) v.findViewById(R.id.fab);

        // Asignar escucha al FAB
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de inserción
                        Intent intent = new Intent(getActivity(), InsertActivity.class);
                        intent.putExtra(Constantes.NOMBRE_USER, extra);
                        startActivityForResult(intent, 3);
                    }
                }
        );

        return v;
    }


    public void cargarAdaptador(String mail) {
        // Añadir parámetro a la URL del web service
        String newURL = Constantes.GET_GROUP_BY_EMAIL + "?email=" + mail;

        // Petición GET
        VolleySingleton.
                getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta Json
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.toString());
                            }
                        }

                )
        );
    }

    /**
     * Interpreta los resultados de la respuesta y así
     * realizar las operaciones correspondientes
     *
     * @param response Objeto Json con la respuesta
     */
    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "metas" Json
                    JSONArray mensaje = response.getJSONArray("meta");
                    // Parsear con Gson
                    Grupo[] metas = gson.fromJson(mensaje.toString(), Grupo[].class);

                    final String extra = getArguments().getString(Constantes.NOMBRE_USER);
                    final String idPos = getArguments().getString(Constantes.ID_POS_GRUPO);
                    // Inicializar adaptador
                    adapter = new GrupoAdapter(Arrays.asList(metas), getActivity(), extra, idPos);
                    // Setear adaptador a la lista
                    lista.setAdapter(adapter);
                    break;
                case "2": // FALLIDO
                    Toast.makeText(
                            getActivity(),
                            "No se encuentra registrado en ninguna asignatura",
                            Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }

    }


}
