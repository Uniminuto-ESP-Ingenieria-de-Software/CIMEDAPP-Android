package co.edu.uniminuto.cimedapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

import co.edu.uniminuto.cimedapp.model.AdminConexion;
import co.edu.uniminuto.cimedapp.model.ModelPaciente;
import co.edu.uniminuto.cimedapp.model.PacienteDAO;

public class Registro extends AppCompatActivity {

    private EditText editTextRIdentificacion;
    private EditText editTextRNombre;
    private EditText editTextRApellido;
    private EditText editTextRCorreo;
    private EditText editTextRTelefono;
    private EditText editTextRDate;
    private Button buttonRRegistrar;
    private String mensaje ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        inicializar();
        buttonRRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validar()) {
                    try {
                        ModelPaciente modelPaciente = new ModelPaciente();
                        modelPaciente.setIdentificacion("123");
                        modelPaciente.setNombres("Palito");
                        modelPaciente.setApellidos("Paletas");
                        modelPaciente.setCorreo("to@yo.com");
                        modelPaciente.setTelefono("123");
                        modelPaciente.setFechaNacimiento("20230623");

                        PacienteDAO pacienteDAO = new PacienteDAO(Registro.this);
                        Toast.makeText(Registro.this, "Inicial: " + pacienteDAO.fetchAll().getCount(), Toast.LENGTH_SHORT).show();

                        pacienteDAO.create(modelPaciente);

                        Toast.makeText(Registro.this, "Despues:" + pacienteDAO.fetchAll().getCount(), Toast.LENGTH_SHORT).show();
                    } catch (Exception exc) {
                        Toast.makeText(Registro.this, "Error: " + exc.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Registro.this, mensaje, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicializar() {
        editTextRIdentificacion = (EditText) findViewById(R.id.editTextRIdentificacion);
        editTextRNombre = (EditText) findViewById(R.id.editTextRNombre);
        editTextRApellido = (EditText) findViewById(R.id.editTextRApellido);
        editTextRCorreo = (EditText) findViewById(R.id.editTextRCorreo);
        editTextRTelefono = (EditText) findViewById(R.id.editTextRTelefono);
        editTextRDate = (EditText) findViewById(R.id.editTextRDate);
        buttonRRegistrar = (Button) findViewById(R.id.buttonRRegistrar);
    }

    private boolean validar() {
        mensaje = "";
        boolean valido = true;
        if (editTextRIdentificacion.getText().toString().trim().length() == 0) {
            mensaje += "El campo Identificación es obligatorio.\n";
            valido = false;
        }

        if (editTextRNombre.getText().toString().trim().length() == 0) {
            mensaje += "El campo Nombres es obligatorio.\n";
            valido = false;
        }

        if (editTextRApellido.getText().toString().trim().length() == 0) {
            mensaje += "El campo Apellidos es obligatorio.\n";
            valido = false;
        }

        if (editTextRCorreo.getText().toString().trim().length() == 0) {
            mensaje += "El campo Correo es obligatorio.\n";
            valido = false;
        }

        if (editTextRTelefono.getText().toString().trim().length() == 0) {
            mensaje += "El campo Teléfono es obligatorio.\n";
            valido = false;
        }

        if (editTextRDate.getText().toString().trim().length() == 0)
        {
            mensaje += "El campo Fecha de Nacimiento es obligatorio.\n";
            valido = false;
        }

        return valido;
    }

}