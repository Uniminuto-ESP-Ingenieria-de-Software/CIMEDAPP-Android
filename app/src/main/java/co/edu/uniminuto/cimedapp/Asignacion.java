package co.edu.uniminuto.cimedapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.uniminuto.cimedapp.model.ModelPaciente;
import co.edu.uniminuto.cimedapp.model.PacienteDAO;

public class Asignacion extends AppCompatActivity {
    private EditText editTextAFiltroId;
    private Button buttonAFiltrar;
    private ListView listAPacientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacion);

        inicializar();
        buttonAFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PacienteDAO pacienteDAO = new PacienteDAO(Asignacion.this);
                    Cursor resultados;
                    if (editTextAFiltroId.getText().toString().trim().length() == 0) {
                        resultados = pacienteDAO.fetchAll();
                    } else {
                        resultados = pacienteDAO.fetchById(editTextAFiltroId.getText().toString());
                    }

                    if(resultados.getCount() != 0) {


                        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                                Asignacion.this,
                                R.layout.list_pacientes_layout,
                                resultados,
                                new String[] { "nombres", "identificacion" }, // Reemplaza "column_name" con el nombre de la columna que deseas mostrar
                                new int[] { R.id.textViewPNombre, R.id.textViewPIdentificacion },
                                0
                        );

                        listAPacientes.setAdapter(adapter);
                    } else {
                        Toast.makeText(Asignacion.this, "Sin resultados", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception exc) {
                    Toast.makeText(Asignacion.this, "Error: " + exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicializar() {
        editTextAFiltroId = (EditText) findViewById(R.id.editTextAFiltroId);
        buttonAFiltrar = (Button) findViewById(R.id.buttonAFiltrar);
        listAPacientes = (ListView) findViewById(R.id.listAPacientes);
    }
}