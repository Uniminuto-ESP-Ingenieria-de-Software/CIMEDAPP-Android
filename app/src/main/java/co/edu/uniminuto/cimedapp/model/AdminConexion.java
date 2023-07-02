package co.edu.uniminuto.cimedapp.model;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Clase para el manejo de la conexión a la base de datos
 */
public class AdminConexion {
    private String host = "db-mysql-uniminuto-do-user-1692611-0.b.db.ondigitalocean.com";
    private String port = "25060";
    private String database = "cimed";
    private String user = "doadmin";
    private String password = "AVNS_TUNdnBxo2F6DXw-C3qB";
    private final String url = "jdbc:mysql://"+host+":"+port+"/"+database;
    Connection conexion;

    /**
     * Crear la conexión con la base de datos
     * @return
     */
    public Connection abrirConexion() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(url, user, password);
            return conexion;
        } catch (Exception exc) {
            Log.i("CimedMsg", "Error de conexión a la base de datos: "+exc.getMessage());
            return null;
        }
    }

    public void cerrarConexion(){
        try {
            conexion.close();
        } catch (Exception exc) {
            Log.i("CimedMsg", "Error al cerrar conexión a la base de datos: "+exc.getMessage());
        }
    }
}
