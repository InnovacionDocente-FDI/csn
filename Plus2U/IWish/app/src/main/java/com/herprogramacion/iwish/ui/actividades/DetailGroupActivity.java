package com.herprogramacion.iwish.ui.actividades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.herprogramacion.iwish.R;
import com.herprogramacion.iwish.tools.Constantes;
import com.herprogramacion.iwish.ui.fragmentos.DetailGroupFragment;

/**
 * Created by Nuria on 25/03/2016.
 */
public class DetailGroupActivity extends AppCompatActivity {
    private String idGrupo;
    private String user;
    private String code;

    /**
     * Inicia una nueva instancia de la actividad
     *
     * @param activity Contexto desde donde se lanzará
     * @param idGrupo   Identificador del grupo escogido de la lista el cual se va a detallar
     * @param user   email del usuario logado
     */
    public static void launch(Activity activity, String idGrupo, String user, String code) {
        Intent intent = getLaunchIntent(activity, idGrupo, user, code);
        activity.startActivityForResult(intent, Constantes.CODIGO_DETALLE);
    }

    /**
     * Construye un Intent a partir del contexto y la actividad
     * de detalle.
     *
     * @param context Contexto donde se inicia
     * @param idGrupo  Identificador de la meta
     * @return Intent listo para usar
     */
    public static Intent getLaunchIntent(Context context, String idGrupo, String user, String code) {
        Intent intent = new Intent(context, DetailGroupActivity.class);
        intent.putExtra(Constantes.EXTRA_GRUPO_ID, idGrupo);
        intent.putExtra(Constantes.NOMBRE_USER, user);
        intent.putExtra(Constantes.ID_POS_GRUPO, code);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            // Dehabilitar titulo de la actividad
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // Setear ícono "X" como Up button
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close);
        }

        // Retener instancia
        if (getIntent().getStringExtra(Constantes.EXTRA_GRUPO_ID) != null && getIntent().getStringExtra(Constantes.NOMBRE_USER) != null) {
            idGrupo = getIntent().getStringExtra(Constantes.EXTRA_GRUPO_ID);
            user = getIntent().getStringExtra(Constantes.NOMBRE_USER);
            code = getIntent().getStringExtra(Constantes.ID_POS_GRUPO);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, DetailGroupFragment.createInstance(idGrupo, user, code), "DetailGroupFragment")
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constantes.CODIGO_ACTUALIZACION) {
            if (resultCode == RESULT_OK) {
                DetailGroupFragment fragment = (DetailGroupFragment) getSupportFragmentManager().
                        findFragmentByTag("DetailGroupFragment");
                fragment.cargarDatos();

                setResult(RESULT_OK); // Propagar código para actualizar
            } else if (resultCode == 203) {
                setResult(203);
                finish();
            } else {
                setResult(RESULT_CANCELED);
            }
        }
    }
}
