package co.edu.uniminuto.cimedapp.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import co.edu.uniminuto.cimedapp.R;
import co.edu.uniminuto.cimedapp.model.CitaDAO;
import co.edu.uniminuto.cimedapp.model.ModelCita;
import co.edu.uniminuto.cimedapp.model.ModelPaciente;
import co.edu.uniminuto.cimedapp.model.PacienteDAO;

public class Asignacion extends AppCompatActivity {
    private EditText editTextAFiltroId;
    private Button buttonAFiltrar;
    private ListView listAPacientes;
    private Spinner spinnerATipo;
    private EditText editTextAFecha;
    private EditText editTextAHora;
    private Spinner spinnerAMedio;
    private Button buttonAAsignar;
    private String mensaje="";
    private int filaSeleccionada = -1;
    private String idPacienteSeleccionado = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacion);

        inicializar();

        buttonAFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Peparar variables para la consulta en la base de datos
                    PacienteDAO pacienteDAO = new PacienteDAO();
                    List<ModelPaciente> resultados;
                    //Reiniciar variables
                    filaSeleccionada = -1;
                    idPacienteSeleccionado = "";
                    //Ejecutar consulta de acuerdo a lo digitado por el usuario en el campo de filtro
                    if (editTextAFiltroId.getText().toString().trim().length() == 0) {
                        resultados = pacienteDAO.fetchAll();
                    } else {
                        resultados = pacienteDAO.filterById(editTextAFiltroId.getText().toString());
                    }

                    //Verificar si se obtuvieron resultados
                    if(resultados.size() != 0) {
                        //Preparar el adaptador para mostrar los resultados
                        CustomPacienteAdapter adapter = new CustomPacienteAdapter(Asignacion.this,  R.layout.list_pacientes_layout, resultados);
                        listAPacientes.setAdapter(adapter);
                        listAPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Desactivar la selección de la fila anterior
                                if (filaSeleccionada != -1) {
                                    View previousSelectedView = parent.getChildAt(filaSeleccionada - listAPacientes.getFirstVisiblePosition());
                                    if (previousSelectedView != null) {
                                        previousSelectedView.setActivated(false);
                                    }
                                }

                                // Actualizar la fila seleccionada
                                filaSeleccionada = position;

                                // Obtener la vista de la fila seleccionada
                                View selectedView = parent.getChildAt(position - listAPacientes.getFirstVisiblePosition());
                                // Establecer el estado activado de la vista seleccionada
                                selectedView.setActivated(true);
                                // Notificar al adaptador que los datos han cambiado para actualizar la vista
                                adapter.notifyDataSetChanged();

                                // Obtener el elemento de la fila seleccionada
                                ModelPaciente pacienteSeleccionado = (ModelPaciente) parent.getItemAtPosition(position);
                                // Obtener la identificacion del paciente de la fila seleccionada
                                idPacienteSeleccionado = pacienteSeleccionado.getIdentificacion();
                            }
                        });
                    } else {
                        listAPacientes.setAdapter(null);
                        //Mensaje en el caso que no se encuentren resultados
                        Toast.makeText(Asignacion.this, "Sin resultados", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception exc) {
                    //Mensjae en caso de error.
                    Toast.makeText(Asignacion.this, "Error: " + exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        editTextAFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextAFecha);
            }
        });

        editTextAHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(editTextAHora);
            }
        });

        buttonAAsignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validar la información digitada por el usuario
                if(validar()) {
                    try {
                        //Tomar los valores digitados por el usuario
                        ModelCita modelCita = new ModelCita();
                        modelCita.setIdPaciente(idPacienteSeleccionado);
                        modelCita.setTipo(spinnerATipo.getSelectedItem().toString());
                        modelCita.setFecha(editTextAFecha.getText().toString()+" "+editTextAHora.getText().toString());
                        modelCita.setMedio(spinnerATipo.getSelectedItem().toString());

                        //Instanciar la clase de manejo de la persistencia y guardar la información en la base de datos
                        CitaDAO citaDAO = new CitaDAO();
                        citaDAO.create(modelCita);

                        //Mensaje de confirmación del alamcenamiento de la información
                        Toast.makeText(Asignacion.this, "CITA ASIGNADA CON ÉXITO", Toast.LENGTH_SHORT).show();

                        //Limpiar los campos del formulario para prepararlos para un nuevo registro.
                        editTextAFiltroId.setText("");
                        listAPacientes.setAdapter(null);
                        filaSeleccionada=-1;
                        idPacienteSeleccionado="";
                        spinnerATipo.setSelection(0);
                        editTextAFecha.setText("");
                        editTextAHora.setText("");
                        spinnerAMedio.setSelection(0);
                    } catch (Exception exc) {
                        //Mostrar mensaje informando el error de ejecución.
                        Toast.makeText(Asignacion.this, "Error: " + exc.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Mensaje informando los campos que hace falta diligenciar de acuerdo a la validación.
                    Toast.makeText(Asignacion.this, mensaje, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Asignar las variables de los componentes visuales.
     */
    private void inicializar() {
        editTextAFiltroId = (EditText) findViewById(R.id.editTextAFiltroId);
        buttonAFiltrar = (Button) findViewById(R.id.buttonAFiltrar);
        listAPacientes = (ListView) findViewById(R.id.listAPacientes);
        spinnerATipo = (Spinner) findViewById(R.id.spinnerATipo);
        ArrayAdapter<CharSequence>adapterTipo = ArrayAdapter.createFromResource(Asignacion.this, R.array.tipo, android.R.layout.simple_spinner_item);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerATipo.setAdapter(adapterTipo);
        editTextAFecha= (EditText) findViewById(R.id.editTextAFecha);
        editTextAHora= (EditText) findViewById(R.id.editTextAHora);
        spinnerAMedio = (Spinner) findViewById(R.id.spinnerAMedio);
        ArrayAdapter<CharSequence>adapterMedio = ArrayAdapter.createFromResource(Asignacion.this, R.array.medio, android.R.layout.simple_spinner_item);
        adapterMedio.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerAMedio.setAdapter(adapterMedio);
        buttonAAsignar = (Button) findViewById(R.id.buttonAAsignar);
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month+1) + "/" + year;
                editText.setText(selectedDate);
            }
        }, "FUTURE");

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void showTimePickerDialog(final EditText editText) {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                String complemento="AM";
                if(hour>12) {
                    complemento="PM";
                }
                final String selectedTime = twoDigits(hour>12?hour-12:hour) + ":" + twoDigits(min)+" "+complemento;

                if(hour<8 || hour>=18) {
                    editText.setText("");
                    Toast.makeText(Asignacion.this, "El horario debe ser entre las 8:00 AM y las 6:00 PM", Toast.LENGTH_SHORT).show();
                } else {
                    editText.setText(selectedTime);
                }
            }
        });

        newFragment.show(getSupportFragmentManager(), "timePicker");
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
        if (idPacienteSeleccionado.matches("")) {
            mensaje += "Debe seleccionar un paciente.\n";
            valido = false;
        }
        if (spinnerATipo.getSelectedItem().toString().length() == 0 || spinnerATipo.getSelectedItem().toString().matches("Seleccione tipo...")) {
            mensaje += "El campo Tipo es obligatorio.\n";
            valido = false;
        }

        if (editTextAFecha.getText().toString().trim().length() == 0) {
            mensaje += "El campo Fecha es obligatorio.\n";
            valido = false;
        }

        if (editTextAHora.getText().toString().trim().length() == 0) {
            mensaje += "El campo Hora es obligatorio.\n";
            valido = false;
        }

        if (spinnerAMedio.getSelectedItem().toString().length() == 0 || spinnerAMedio.getSelectedItem().toString().matches("Seleccione medio...")) {
            mensaje += "El campo Apellidos es obligatorio.\n";
            valido = false;
        }

        return valido;
    }
}