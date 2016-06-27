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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import com.herprogramacion.iwish.R;
import com.herprogramacion.iwish.modelo.Grupo;
import com.herprogramacion.iwish.tools.Constantes;

import com.herprogramacion.iwish.ui.GrupoDetalleAdapter;
import com.herprogramacion.iwish.ui.actividades.VoteActivity;
import com.herprogramacion.iwish.web.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Nuria on 25/03/2016.
 */
public class DetailGroupFragment extends Fragment {

    /**
     * Etiqueta de depuración
     */
    private static final String TAG = DetailGroupFragment.class.getSimpleName();

    /*
    Instancias de Views
     */
    private TextView titulo;
    com.melnykov.fab.FloatingActionButton fabVotacion;

    private String extra_id;
    private String extra_email;
    private String extra_idPositivoGrupo;
    Grupo[] grupo;
    private Gson gson = new Gson();

    /*
Adaptador del recycler view
 */
    private GrupoDetalleAdapter adapter;

    /*
    Instancia global del recycler view
     */
    private RecyclerView lista;

    /*
instancia global del administrador
 */
    private RecyclerView.LayoutManager lManager;

    public DetailGroupFragment() {
    }

    public static DetailGroupFragment createInstance(String idGrupo, String user, String code) {
        DetailGroupFragment detailFragment = new DetailGroupFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constantes.EXTRA_GRUPO_ID, idGrupo);
        bundle.putString(Constantes.NOMBRE_USER, user);
        bundle.putString(Constantes.ID_POS_GRUPO, code);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_group, container, false);

        // Obtención de views
        titulo = (TextView) v.findViewById(R.id.nombre);

        // Obtener extra del intent de envío
        extra_id = getArguments().getString(Constantes.EXTRA_GRUPO_ID);
        extra_email = getArguments().getString(Constantes.NOMBRE_USER);
        extra_idPositivoGrupo = getArguments().getString(Constantes.ID_POS_GRUPO);

        lista = (RecyclerView) v.findViewById(R.id.recicladorGrupo);
        lista.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());
        lista.setLayoutManager(lManager);

        if (extra_id != null && extra_email != null) {
            // Cargar datos desde el web service
            cargarDatos();
        }

        // Obtener instancia del FAB
        fabVotacion = (com.melnykov.fab.FloatingActionButton) v.findViewById(R.id.fabVote);
        // Setear escucha para el fab
        fabVotacion.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(extra_idPositivoGrupo == null){
                            Toast.makeText(
                                    getActivity(),
                                    "No ha escaneado ningún positivo grupal",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            // Iniciar actividad de actualización
                            Intent i = new Intent(getActivity(), VoteActivity.class);
                            i.putExtra(Constantes.EXTRA_GRUPO_ID, extra_id);
                            i.putExtra(Constantes.NOMBRE_USER, extra_email);
                            i.putExtra(Constantes.ID_POS_GRUPO, extra_idPositivoGrupo);
                            Bundle contenedor = new Bundle();
                            //le cargamos al bundle un objeto parcelable que se almacenara
                            //bajo la key "array" y contendrá nuestra lista de los integrantes del grupo
                            contenedor.putSerializable("array", grupo);
                            i.putExtras(contenedor);
                            getActivity().startActivityForResult(i, Constantes.CODIGO_ACTUALIZACION);
                        }
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
        String newURL = Constantes.DETALLADO_GRUPO + "?idMeta=" + extra_id;

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
            String estado = response.getString("estado");

            switch (estado) {
                case "1":
                    JSONArray mensaje = response.getJSONArray("meta");
                    // Parsear con Gson
                    grupo = gson.fromJson(mensaje.toString(), Grupo[].class);

                    // Inicializar adaptador
                    adapter = new GrupoDetalleAdapter(Arrays.asList(grupo), getActivity(), extra_email, extra_idPositivoGrupo);
                    // Setear adaptador a la lista
                    lista.setAdapter(adapter);

                    for (int j = 0; j < grupo.length; j++) {
                        // Seteando valores en los views
                        titulo.setText(grupo[j].getNombreGrupo());
                    }

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
}
