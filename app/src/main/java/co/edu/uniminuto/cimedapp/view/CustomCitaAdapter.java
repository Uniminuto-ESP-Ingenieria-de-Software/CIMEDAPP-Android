package co.edu.uniminuto.cimedapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import co.edu.uniminuto.cimedapp.R;
import co.edu.uniminuto.cimedapp.model.ModelCita;
import co.edu.uniminuto.cimedapp.model.ModelPaciente;

public class CustomCitaAdapter extends ArrayAdapter<ModelCita> {
    public CustomCitaAdapter(Context context, int layout, List<ModelCita> objects) {
        super(context, layout, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            view =  inflater.inflate(R.layout.list_pacientes_layout, parent, false);
        }
        // Obtener los elementos de texto
        TextView textViewPrincipal = view.findViewById(R.id.textViewPPrincipal);
        TextView textViewSecundario = view.findViewById(R.id.textViewPSecundario);

        // Obtener los valores
        ModelCita cita = (ModelCita) getItem(position);

        // Concatenar los textos
        String textoPrincipal = cita.getFecha() + " " + cita.getTipo() ;
        String textoSecundario = cita.getMedio() + " - Paciente: " + cita.getIdPaciente();

        // Establecer los textos en los elementos de texto
        textViewPrincipal.setText(textoPrincipal);
        textViewSecundario.setText(textoSecundario);
        return view;
    }
}
