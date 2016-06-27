package com.herprogramacion.iwish.ui.actividades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.herprogramacion.iwish.R;
import com.herprogramacion.iwish.tools.Constantes;
import com.herprogramacion.iwish.ui.fragmentos.DetailFragment;

/**
 * Esta actividad contiene un fragmento que muestra el detalle
 * de las asignaturas.
 */
public class DetailActivity extends AppCompatActivity {

    private String idMeta;
    private String user;

    /**
     * Inicia una nueva instancia de la actividad
     *
     * @param activity Contexto desde donde se lanzará
     * @param idMeta   Identificador de la asignatura a detallar, una vez pulsado sobre uno d ela lista
     */
    public static void launch(Activity activity, String idMeta, String user) {
        Intent intent = getLaunchIntent(activity, idMeta, user);
        activity.startActivityForResult(intent, Constantes.CODIGO_DETALLE);
    }

    /**
     * Construye un Intent a partir del contexto y la actividad
     * de detalle.
     *
     * @param context Contexto donde se inicia
     * @param idMeta  Identificador de la asignatura
     * @param user  email del usuario logado
     * @return Intent listo para usar
     */
    public static Intent getLaunchIntent(Context context, String idMeta, String user) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constantes.EXTRA_ID, idMeta);
        intent.putExtra(Constantes.NOMBRE_USER, user);
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
        if (getIntent().getStringExtra(Constantes.EXTRA_ID) != null && getIntent().getStringExtra(Constantes.NOMBRE_USER) != null) {
            idMeta = getIntent().getStringExtra(Constantes.EXTRA_ID);
            user = getIntent().getStringExtra(Constantes.NOMBRE_USER);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, DetailFragment.createInstance(idMeta, user), "DetailFragment")
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
            default:{
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constantes.CODIGO_ACTUALIZACION) {
            if (resultCode == RESULT_OK) {
                DetailFragment fragment = (DetailFragment) getSupportFragmentManager().
                        findFragmentByTag("DetailFragment");
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