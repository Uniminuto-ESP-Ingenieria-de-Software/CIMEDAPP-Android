package co.edu.uniminuto.cimedapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.uniminuto.cimedapp.R;
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

        editTextRDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextRDate);
            }
        });

        buttonRRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validar la información digitada por el usuario
                if(validar()) {
                    try {
                        //Tomar los valores digitados por el usuario
                        ModelPaciente modelPaciente = new ModelPaciente();
                        modelPaciente.setIdentificacion(editTextRIdentificacion.getText().toString().trim());
                        modelPaciente.setNombres(editTextRNombre.getText().toString().trim().toUpperCase());
                        modelPaciente.setApellidos(editTextRApellido.getText().toString().trim().toUpperCase());
                        modelPaciente.setCorreo(editTextRCorreo.getText().toString().trim());
                        modelPaciente.setTelefono(editTextRTelefono.getText().toString().trim());
                        modelPaciente.setFechaNacimiento(editTextRDate.getText().toString().trim());

                        //Instanciar la clase de manejo de la persistencia y guardar la información en la base de datos
                        PacienteDAO pacienteDAO = new PacienteDAO();
                        pacienteDAO.create(modelPaciente);

                        //Mensaje de confirmación del alamcenamiento de la información
                        Toast.makeText(Registro.this, "REGISTRO CREADO CON ÉXITO", Toast.LENGTH_SHORT).show();

                        //Limpiar los campos del formulario para prepararlos para un nuevo registro.
                        editTextRIdentificacion.setText("");
                        editTextRNombre.setText("");
                        editTextRApellido.setText("");
                        editTextRCorreo.setText("");
                        editTextRTelefono.setText("");
                        editTextRDate.setText("");
                    } catch (Exception exc) {
                        //Mostrar mensaje informando el error de ejecución.
                        Toast.makeText(Registro.this, "Error: " + exc.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Mensaje informando los campos que hace falta diligenciar de acuerdo a la validación.
                    Toast.makeText(Registro.this, mensaje, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Asignar las variables de los componentes visuales.
     */
    private void inicializar() {
        editTextRIdentificacion = (EditText) findViewById(R.id.editTextRIdentificacion);
        editTextRNombre = (EditText) findViewById(R.id.editTextRNombre);
        editTextRApellido = (EditText) findViewById(R.id.editTextRApellido);
        editTextRCorreo = (EditText) findViewById(R.id.editTextRCorreo);
        editTextRTelefono = (EditText) findViewById(R.id.editTextRTelefono);
        editTextRDate = (EditText) findViewById(R.id.editTextRDate);
        buttonRRegistrar = (Button) findViewById(R.id.buttonRRegistrar);
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month+1) + "/" + year;
                editText.setText(selectedDate);
            }
        }, "PAST");

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    /**
     * Validar la información digitada por el usuario en el formulario
     * @return boolean valido true si es valida la información digitada por el usuario
     */
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