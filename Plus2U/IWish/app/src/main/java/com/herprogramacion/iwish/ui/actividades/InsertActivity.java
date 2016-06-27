package com.herprogramacion.iwish.ui.actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;
import android.widget.Toast;

import com.herprogramacion.iwish.R;
import com.herprogramacion.iwish.tools.Constantes;
import com.herprogramacion.iwish.ui.fragmentos.InsertFragment;

public class InsertActivity extends AppCompatActivity {

    String resultado;
    TextView tvResult;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            // Quitar el botón del menú superior
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

            //new IntentIntegrator(QrReaderActity.this).initiateScan();
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 0) && (resultCode == -1)) {
            tvResult = (TextView)findViewById(R.id.scan_content);
            tvResult.setText(data.getStringExtra("SCAN_RESULT"));
            resultado = data.getStringExtra("SCAN_RESULT");

            user = getIntent().getExtras().getString(Constantes.NOMBRE_USER);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, InsertFragment.createInstance(resultado, user), "InsertFragment")
                    .commit();
            Intent i=new Intent(InsertActivity.this, MainActivity.class);
            i.putExtra(Constantes.NOMBRE_USER, user);
            startActivity(i);
        } else {
            //Toast.makeText(viewGroup.getContext(), "Error", Toast.LENGTH_SHORT).show();
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Debe seleccionar un lector de código", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
