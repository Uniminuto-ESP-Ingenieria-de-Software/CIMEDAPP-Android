package co.edu.uniminuto.cimedapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CitaDAO {
    private Context context;

    /**
     * Constructor que asigna el contexto  que se utilizará como parámetro para invocar la conexión.
     * @param context Contexto desde el cual se ejecuta la conexión
     */
    public CitaDAO(Context context) {
        this.context = context;
    }

    /**
     * Inserta un nuevo paciente en la base de datos.
     * @param cita Información la cita a insertar.
     */
    public void create(ModelCita cita) {
        SQLiteDatabase database = new AdminConexion(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AdminConexion.CITAS_COLUMN_ID, cita.getId());
        values.put(AdminConexion.CITAS_COLUMN_ID_PACIENTE, cita.getIdPaciente());
        values.put(AdminConexion.CITAS_COLUMN_TIPO, cita.getTipo());
        values.put(AdminConexion.CITAS_COLUMN_FECHA, cita.getFecha());
        values.put(AdminConexion.CITAS_COLUMN_MEDIO, cita.getMedio());
        database.insert(AdminConexion.CITAS_TABLE_NAME, null, values);
    }

    /**
     * Obtiene todos los registros de las citas
     * @return Cursor Objeto con el resultado de la busqueda
     */
    public Cursor fetchAll() {
        SQLiteDatabase database = new AdminConexion(context).getReadableDatabase();

        String[] projection = {
                AdminConexion.CITAS_COLUMN_ID + " as _id",
                AdminConexion.CITAS_COLUMN_ID_PACIENTE,
                AdminConexion.CITAS_COLUMN_TIPO,
                AdminConexion.CITAS_COLUMN_FECHA,
                AdminConexion.CITAS_COLUMN_MEDIO
        };

        Cursor cursor = database.query(
                AdminConexion.CITAS_TABLE_NAME,   // The table to query
                projection,                           // The columns to return
                null,                                 // The columns for the WHERE clause
                null,                                 // The values for the WHERE clause
                null,                                 // don't group the rows
                null,                                 // don't filter by row groups
                null                                  // don't sort
        );

        return cursor;
    }
}
