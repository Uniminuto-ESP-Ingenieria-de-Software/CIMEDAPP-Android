package co.edu.uniminuto.cimedapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.edu.uniminuto.cimedapp.R;

public class MainActivity extends AppCompatActivity {
    private Button buttonRegistro;
    private Button buttonAsignacion;
    private Button buttonConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar();

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistro = new Intent(MainActivity.this, Registro.class);
                startActivity(intentRegistro);
            }
        });

        buttonAsignacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAsignacion = new Intent(MainActivity.this, Asignacion.class);
                startActivity(intentAsignacion);
            }
        });

        buttonConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentConsulta = new Intent(MainActivity.this, Consulta.class);
                startActivity(intentConsulta);
            }
        });


    }

    private void inicializar() {
        buttonRegistro = (Button) findViewById(R.id.buttonRegistro);
        buttonAsignacion = (Button) findViewById(R.id.buttonAsignacion);
        buttonConsulta = (Button) findViewById(R.id.buttonConsulta);
    }
}