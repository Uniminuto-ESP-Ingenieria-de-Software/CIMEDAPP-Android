package co.edu.uniminuto.cimedapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Manejador de los métodos de persistencia para el paciente
 */
public class PacienteDAO  {
    private Context context;

    /**
     * Constructor que asigna el contexto  que se utilizará como parámetro para invocar la conexión.
     * @param context Contexto desde el cual se ejecuta la conexión
     */
    public PacienteDAO(Context context) {
        this.context = context;
    }

    /**
     * Inserta un nuevo paciente en la base de datos.
     * @param paciente Información del paciente a insertar.
     */
    public void create(ModelPaciente paciente) {
        SQLiteDatabase database = new AdminConexion(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AdminConexion.PACIENTES_COLUMN_IDENTIFICACION, paciente.getIdentificacion());
        values.put(AdminConexion.PACIENTES_COLUMN_NOMBRES, paciente.getNombres());
        values.put(AdminConexion.PACIENTES_COLUMN_APELLIDOS, paciente.getApellidos());
        values.put(AdminConexion.PACIENTES_COLUMN_CORREO, paciente.getCorreo());
        values.put(AdminConexion.PACIENTES_COLUMN_TELEFONO, paciente.getTelefono());
        values.put(AdminConexion.PACIENTES_COLUMN_FECHA_NACIMIENTO, paciente.getFechaNacimiento());
        database.insert(AdminConexion.PACIENTES_TABLE_NAME, null, values);
    }

    /**
     * Obtiene todos los registros de los pacientes
     * @return Cursor Objeto con el resultado de la busqueda
     */
    public Cursor fetchAll() {
        SQLiteDatabase database = new AdminConexion(context).getReadableDatabase();

        String[] projection = {
                AdminConexion.PACIENTES_COLUMN_IDENTIFICACION + " as _id",
                AdminConexion.PACIENTES_COLUMN_NOMBRES,
                AdminConexion.PACIENTES_COLUMN_APELLIDOS,
                AdminConexion.PACIENTES_COLUMN_CORREO,
                AdminConexion.PACIENTES_COLUMN_TELEFONO,
                AdminConexion.PACIENTES_COLUMN_FECHA_NACIMIENTO
        };

        Cursor cursor = database.query(
                AdminConexion.PACIENTES_TABLE_NAME,   // The table to query
                projection,                           // The columns to return
                null,                                 // The columns for the WHERE clause
                null,                                 // The values for the WHERE clause
                null,                                 // don't group the rows
                null,                                 // don't filter by row groups
                null                                  // don't sort
        );

        return cursor;
    }

    /**
     * Buscar el registro de un paciente en la base de datos filtrando por el id proporcionado.
     * @param identificacion Identificación por la cual se desea filtar la busqueda.
     * @return Cursor Objeto con el resultado de la busqueda
     */
    public Cursor fetchById(String identificacion) {
        /*String name = activityBinding.nameEditText.getText().toString();
        String gender = activityBinding.genderEditText.getText().toString();
        String age = activityBinding.ageEditText.getText().toString();*/

        SQLiteDatabase database = new AdminConexion(context).getReadableDatabase();

        String[] projection = {
                AdminConexion.PACIENTES_COLUMN_IDENTIFICACION + " as _id",
                AdminConexion.PACIENTES_COLUMN_NOMBRES,
                AdminConexion.PACIENTES_COLUMN_APELLIDOS,
                AdminConexion.PACIENTES_COLUMN_CORREO,
                AdminConexion.PACIENTES_COLUMN_TELEFONO,
                AdminConexion.PACIENTES_COLUMN_FECHA_NACIMIENTO
        };

       /* String selection =
                AdminConexion.PACIENTES_COLUMN_NAME + " like ? and " +
                        AdminConexion.PACIENTES_COLUMN_AGE + " &gt; ? and " +
                        AdminConexion.PACIENTES_COLUMN_GENDER + " like ?";

        String[] selectionArgs = {"%" + name + "%", age, "%" + gender + "%"};
        );*/

        String selection =
                AdminConexion.PACIENTES_COLUMN_IDENTIFICACION + " like ? ";

        String[] selectionArgs = {"%" + identificacion + "%"};

        Cursor cursor = database.query(
                AdminConexion.PACIENTES_TABLE_NAME,       // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );

        return cursor;
    }

}
