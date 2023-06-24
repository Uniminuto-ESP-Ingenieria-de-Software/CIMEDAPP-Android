package co.edu.uniminuto.cimedapp.view;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import co.edu.uniminuto.cimedapp.R;
import co.edu.uniminuto.cimedapp.model.CitaDAO;
import co.edu.uniminuto.cimedapp.model.ModelCita;
import co.edu.uniminuto.cimedapp.model.PacienteDAO;

public class Asignacion extends AppCompatActivity {
    private EditText editTextAFiltroId;
    private Button buttonAFiltrar;
    private ListView listAPacientes;
    private Spinner spinnerATipo;
    private EditText editTextAFecha;
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
                    PacienteDAO pacienteDAO = new PacienteDAO(Asignacion.this);
                    Cursor resultados;
                    //Reiniciar variables
                    filaSeleccionada = -1;
                    idPacienteSeleccionado = "";
                    //Ejecutar consulta de acuerdo a lo digitado por el usuario en el campo de filtro
                    if (editTextAFiltroId.getText().toString().trim().length() == 0) {
                        resultados = pacienteDAO.fetchAll();
                    } else {
                        resultados = pacienteDAO.fetchById(editTextAFiltroId.getText().toString());
                    }

                    //Verificar si se obtuvieron resultados
                    if(resultados.getCount() != 0) {
                        //Preparar el adaptador para mostrar los resultados
                        CustomCursorAdapter adapter = new CustomCursorAdapter(Asignacion.this,  R.layout.list_pacientes_layout, resultados, 0);
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

                                // Obtener el cursor de la fila seleccionada
                                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                                // Obtener la identificacion del paciente de la fila seleccionada
                                idPacienteSeleccionado = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
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
                        modelCita.setFecha(editTextAFecha.getText().toString());
                        modelCita.setMedio(spinnerATipo.getSelectedItem().toString());


                        //Instanciar la clase de manejo de la persistencia y guardar la información en la base de datos
                        CitaDAO citaDAO = new CitaDAO(Asignacion.this);
                        citaDAO.create(modelCita);

                        //Mensaje de confirmación del alamcenamiento de la información
                        Toast.makeText(Asignacion.this, "REGISTRO CREADO CON ÉXITO", Toast.LENGTH_SHORT).show();

                        //Limpiar los campos del formulario para prepararlos para un nuevo registro.
                        editTextAFiltroId.setText("");
                        listAPacientes.setAdapter(null);
                        filaSeleccionada=-1;
                        idPacienteSeleccionado="";
                        spinnerATipo.setSelection(0);
                        editTextAFecha.setText("");
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
        ArrayAdapter<CharSequence >adapterTipo = ArrayAdapter.createFromResource(Asignacion.this, R.array.tipo, android.R.layout.simple_spinner_item);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerATipo.setAdapter(adapterTipo);
        editTextAFecha= (EditText) findViewById(R.id.editTextAFecha);
        spinnerAMedio = (Spinner) findViewById(R.id.spinnerAMedio);
        ArrayAdapter<CharSequence >adapterMedio = ArrayAdapter.createFromResource(Asignacion.this, R.array.medio, android.R.layout.simple_spinner_item);
        adapterMedio.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerAMedio.setAdapter(adapterMedio);
        buttonAAsignar = (Button) findViewById(R.id.buttonAAsignar);
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
        if (spinnerATipo.getSelectedItem().toString().length() == 0) {
            mensaje += "El campo Tipo es obligatorio.\n";
            valido = false;
        }

        if (editTextAFecha.getText().toString().trim().length() == 0) {
            mensaje += "El campo Fecha es obligatorio.\n";
            valido = false;
        }

        if (spinnerAMedio.getSelectedItem().toString().length() == 0) {
            mensaje += "El campo Apellidos es obligatorio.\n";
            valido = false;
        }

        return valido;
    }
}