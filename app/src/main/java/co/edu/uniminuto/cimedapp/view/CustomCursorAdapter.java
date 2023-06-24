package co.edu.uniminuto.cimedapp.view;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import co.edu.uniminuto.cimedapp.R;

public class CustomCursorAdapter extends ResourceCursorAdapter {
    public CustomCursorAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflar el diseño de la fila de la lista
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_pacientes_layout, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Obtener los elementos de texto
        TextView textViewPrincipal = view.findViewById(R.id.textViewPPrincipal);
        TextView textViewSecundario = view.findViewById(R.id.textViewPSecundario);

        // Obtener los índices de las columnas
        int idColumnIndex = cursor.getColumnIndexOrThrow("_id");
        int nombresColumnIndex = cursor.getColumnIndexOrThrow("nombres");
        int apellidosColumnIndex = cursor.getColumnIndexOrThrow("apellidos");
        int correoColumnIndex = cursor.getColumnIndexOrThrow("correo");
        int telefonoColumnIndex = cursor.getColumnIndexOrThrow("telefono");
        int fechaColumnIndex = cursor.getColumnIndexOrThrow("fecha_nacimiento");

        // Obtener los valores del cursor
        String id = cursor.getString(idColumnIndex);
        String nombres = cursor.getString(nombresColumnIndex);
        String apellidos = cursor.getString(apellidosColumnIndex);
        String correo = cursor.getString(correoColumnIndex);
        String telefono = cursor.getString(telefonoColumnIndex);
        String fechaNacimiento = cursor.getString(fechaColumnIndex);

        // Concatenar los textos
        String textoPrincipal = apellidos + " " + nombres + " (" + id + ")";
        String textoSecundario = "Correo: " + correo + " - Tel: " + telefono + " (" + fechaNacimiento + ")";

        // Establecer los textos en los elementos de texto
        textViewPrincipal.setText(textoPrincipal);
        textViewSecundario.setText(textoSecundario);
    }
}
