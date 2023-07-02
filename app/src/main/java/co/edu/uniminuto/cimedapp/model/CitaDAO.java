package co.edu.uniminuto.cimedapp.model;

import android.util.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    private String tableName = "citas";
    private AdminConexion adminConexion;

    /**
     * Constructor que inicializa el objeto administrador de la conexión
     */
    public CitaDAO() {
        adminConexion = new AdminConexion();
    }

    /**
     * Inserta un nuevo paciente en la base de datos.
     * @param cita Información la cita a insertar.
     * @throws Exception Error cuando no es puede conectar a la base de datos u ocurre un error al ejecutar la sentencia
     */
    public void create(ModelCita cita) throws Exception {
        Connection conexion = adminConexion.abrirConexion();
        if(conexion != null) {
            try {
                String sentenciaSql = "INSERT INTO "+tableName+" VALUES (?,?,?,?,?)";
                PreparedStatement insert = conexion.prepareStatement(sentenciaSql);
                insert.setInt(1, cita.getId());
                insert.setString(2, cita.getIdPaciente());
                insert.setString(3, cita.getTipo());
                insert.setString(4, cita.getFecha());
                insert.setString(5, cita.getMedio());
                insert.execute();
            } catch (Exception exc) {
                Log.i("CimedMsg", "Error al insertar cita en la base de datos: : "+exc.getMessage());
                throw new Exception("Error al registrar cita en la base de datos." );
            } finally {
                adminConexion.cerrarConexion();
            }
        } else {
            throw new Exception("Error de conexión a la base de datos.");
        }
    }

    /**
     * Obtiene todos los registros de las citas
     * @return Cursor Objeto con el resultado de la busqueda
     * @throws Exception Error cuando no es puede conectar a la base de datos u ocurre un error al ejecutar la sentencia
     */
    public List<ModelCita> fetchAll() throws Exception {
        Connection conexion = adminConexion.abrirConexion();
        if(conexion != null) {
            try {
                String sentenciaSql = "SELECT * FROM "+tableName;
                ResultSet resultados = conexion.prepareStatement(sentenciaSql).executeQuery();
                ArrayList<ModelCita> listadoCitas = new ArrayList<ModelCita>();
                if(resultados.isBeforeFirst()) {
                    while(resultados.next()) {
                        ModelCita objetoTemporal = new ModelCita();
                        objetoTemporal.setId(resultados.getInt("id"));
                        objetoTemporal.setIdPaciente(resultados.getString("id_paciente"));
                        objetoTemporal.setTipo(resultados.getString("tipo"));
                        objetoTemporal.setFecha(resultados.getString("fecha"));
                        objetoTemporal.setMedio(resultados.getString("medio"));
                        listadoCitas.add(objetoTemporal);
                    }
                }
                return listadoCitas;
            } catch (Exception exc) {
                Log.i("CimedMsg", "Error al consultar citas en la base de datos: : "+exc.getMessage());
                throw new Exception("Error al consultar citas.");
            } finally {
                adminConexion.cerrarConexion();
            }
        } else {
            throw new Exception("Error de conexión a la base de datos.");
        }
    }

    /**
     * Buscar el registro de citas en la base de datos filtrando por el tipo proporcionado.
     * @param tipo Tipo por la cual se desea filtar la busqueda.
     * @return List<ModelCita> Lista con el resultado de la busqueda
     * @throws Exception Error cuando no es puede conectar a la base de datos u ocurre un error al ejecutar la sentencia
     */
    public List<ModelCita> fetchByTipo(String tipo) throws Exception {
        Connection conexion = adminConexion.abrirConexion();
        if(conexion != null) {
            try {
                String sentenciaSql = "SELECT *  FROM "+tableName+" WHERE tipo = ?";
                PreparedStatement select = conexion.prepareStatement(sentenciaSql);
                select.setString(1, tipo);
                ResultSet resultados = select.executeQuery();
                ArrayList<ModelCita> listadoCitas = new ArrayList<ModelCita>();
                if(resultados.isBeforeFirst()) {
                    while(resultados.next()) {
                        ModelCita objetoTemporal = new ModelCita();
                        objetoTemporal.setId(resultados.getInt("id"));
                        objetoTemporal.setIdPaciente(resultados.getString("id_paciente"));
                        objetoTemporal.setTipo(resultados.getString("tipo"));
                        objetoTemporal.setFecha(resultados.getString("fecha"));
                        objetoTemporal.setMedio(resultados.getString("medio"));
                        listadoCitas.add(objetoTemporal);
                    }
                }
                return listadoCitas;
            } catch (Exception exc) {
                Log.i("CimedMsg", "Error al consultar citas en la base de datos: : "+exc.getMessage());
                throw new Exception("Error al consultar citas."+exc.getMessage());
            } finally {
                adminConexion.cerrarConexion();
            }
        } else {
            throw new Exception("Error de conexión a la base de datos.");
        }
    }
}
