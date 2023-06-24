package co.edu.uniminuto.cimedapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase para el manejo de la conexión a la base de datos
 */
public class AdminConexion extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cimed_database";

    public static final String PACIENTES_TABLE_NAME = "pacientes";
    public static final String PACIENTES_COLUMN_IDENTIFICACION = "identificacion";
    public static final String PACIENTES_COLUMN_NOMBRES = "nombres";
    public static final String PACIENTES_COLUMN_APELLIDOS = "apellidos";
    public static final String PACIENTES_COLUMN_CORREO = "correo";
    public static final String PACIENTES_COLUMN_TELEFONO = "telefono";
    public static final String PACIENTES_COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String CITAS_TABLE_NAME = "citas";
    public static final String CITAS_COLUMN_ID = "id";
    public static final String CITAS_COLUMN_ID_PACIENTE = "id_paciente";
    public static final String CITAS_COLUMN_TIPO = "tipo";
    public static final String CITAS_COLUMN_FECHA = "fecha";
    public static final String CITAS_COLUMN_MEDIO = "medio";

    /**
     * Constructor que asigna el contexto.
     * @param context Contexto desde el cual se ejecuta la conexión
     */
    public AdminConexion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " +PACIENTES_TABLE_NAME + " (" +
                PACIENTES_COLUMN_IDENTIFICACION + " TEXT PRIMARY KEY, " +
                PACIENTES_COLUMN_NOMBRES + " TEXT, " +
                PACIENTES_COLUMN_APELLIDOS + " TEXT, " +
                PACIENTES_COLUMN_CORREO + " TEXT, " +
                PACIENTES_COLUMN_TELEFONO + " TEXT, " +
                PACIENTES_COLUMN_FECHA_NACIMIENTO + " TEXT" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + CITAS_TABLE_NAME + " (" +
                CITAS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CITAS_COLUMN_ID_PACIENTE + " TEXT, " +
                CITAS_COLUMN_TIPO + " TEXT, " +
                CITAS_COLUMN_FECHA + " TEXT," +
                CITAS_COLUMN_MEDIO + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CITAS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PACIENTES_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
