package co.edu.uniminuto.cimedapp.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import co.edu.uniminuto.cimedapp.R;
import co.edu.uniminuto.cimedapp.model.CitaDAO;
import co.edu.uniminuto.cimedapp.model.ModelCita;
import co.edu.uniminuto.cimedapp.model.ModelPaciente;
import co.edu.uniminuto.cimedapp.model.PacienteDAO;

public class Consulta extends AppCompatActivity {
    private Spinner spinnerCTipo;
    private Button buttonCFiltrar;
    private ListView listCCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        inicializar();

        buttonCFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Peparar variables para la consulta en la base de datos
                    CitaDAO citaDAO = new CitaDAO();
                    List<ModelCita> resultados;
                    //Ejecutar consulta de acuerdo a lo seleccionado por el usuario en el campo de filtro
                    if (spinnerCTipo.getSelectedItem().toString().length() == 0 || spinnerCTipo.getSelectedItem().toString().matches("Seleccione tipo...")) {
                        resultados = citaDAO.fetchAll();
                    } else {
                        resultados = citaDAO.fetchByTipo(spinnerCTipo.getSelectedItem().toString());
                    }

                    //Verificar si se obtuvieron resultados
                    if(resultados.size() != 0) {
                        //Preparar el adaptador para mostrar los resultados
                        CustomCitaAdapter adapter = new CustomCitaAdapter(Consulta.this,  R.layout.list_pacientes_layout, resultados);
                        listCCitas.setAdapter(adapter);
                        listCCitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Obtener la vista de la fila seleccionada
                                View selectedView = parent.getChildAt(position - listCCitas.getFirstVisiblePosition());
                                // Obtener el elemento de la fila seleccionada
                                ModelCita citaSeleccionada = (ModelCita) parent.getItemAtPosition(position);
                                //Dialogo para mostrar toda la información
                                AlertDialog.Builder builder = new AlertDialog.Builder(Consulta.this);
                                builder.setTitle("Detalle Cita");
                                String mensaje = "";
                                mensaje +="Fecha: "+citaSeleccionada.getFecha()+"\n";
                                mensaje +="Tipo: "+citaSeleccionada.getTipo()+"\n";
                                mensaje += "Medio: "+citaSeleccionada.getMedio()+"\n";
                                mensaje += "PACIENTE"+"\n";
                                mensaje += "Identificación: "+citaSeleccionada.getIdPaciente()+"\n";

                                PacienteDAO pacienteDAO = new PacienteDAO();
                                try {
                                    ModelPaciente paciente  = pacienteDAO.fetchById(citaSeleccionada.getIdPaciente());
                                    mensaje += "Nombres: "+paciente.getNombres()+"\n";
                                    mensaje += "Apellidos: "+paciente.getApellidos()+"\n";
                                    mensaje += "Correo: "+paciente.getCorreo()+"\n";
                                    mensaje += "Teléfono: "+paciente.getTelefono()+"\n";
                                    mensaje += "Fecha de nacimiento: "+paciente.getFechaNacimiento()+"\n";
                                } catch (Exception e) {
                                    mensaje += "Información del paciente no encontrada.";
                                }
                                builder.setMessage(mensaje);

                                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                    } else {
                        listCCitas.setAdapter(null);
                        //Mensaje en el caso que no se encuentren resultados
                        Toast.makeText(Consulta.this, "Sin resultados", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception exc) {
                    //Mensjae en caso de error.
                    Toast.makeText(Consulta.this, "Error: " + exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicializar() {
        spinnerCTipo = (Spinner) findViewById(R.id.spinnerCTipo);
        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(Consulta.this, R.array.tipo, android.R.layout.simple_spinner_item);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCTipo.setAdapter(adapterTipo);
        buttonCFiltrar = (Button) findViewById(R.id.buttonCFiltrar);
        listCCitas = (ListView) findViewById(R.id.listCCitas);
    }
}