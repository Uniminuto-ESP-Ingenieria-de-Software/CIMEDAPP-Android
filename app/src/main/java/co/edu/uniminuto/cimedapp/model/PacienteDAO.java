package co.edu.uniminuto.cimedapp.model;

import android.util.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Manejador de los métodos de persistencia para el paciente
 */
public class PacienteDAO  {
    private String tableName = "pacientes";
    private AdminConexion adminConexion;

    /**
     * Constructor que inicializa el objeto administrador de la conexión
     */
    public PacienteDAO() {
        adminConexion = new AdminConexion();
    }

    /**
     * Inserta un nuevo paciente en la base de datos.
     * @param paciente Información del paciente a insertar.
     * @throws Exception Erro cuando no es puede conectar a la base de datos u ocurre un error al ejecutar la sentencia
     */
    public void create(ModelPaciente paciente) throws Exception {
        Connection conexion = adminConexion.abrirConexion();
        if(conexion != null) {
            try {
                String sentenciaSql = "INSERT INTO "+tableName+" VALUES (?,?,?,?,?,?)";
                PreparedStatement insert = conexion.prepareStatement(sentenciaSql);
                insert.setString(1, paciente.getIdentificacion());
                insert.setString(2, paciente.getNombres());
                insert.setString(3, paciente.getApellidos());
                insert.setString(4, paciente.getCorreo());
                insert.setString(5, paciente.getTelefono());
                insert.setString(6, paciente.getFechaNacimiento());
                insert.execute();
            } catch (Exception exc) {
                Log.i("CimedMsg", "Error al insertar paciente en la base de datos: : "+exc.getMessage());
                throw new Exception("Error al registrar paciente en la base de datos." );
            } finally {
                adminConexion.cerrarConexion();
            }
        } else {
            throw new Exception("Error de conexión a la base de datos.");
        }
    }

    /**
     * Obtiene todos los registros de los pacientes
     * @return List<ModelPaciente> Lista con el resultado de la busqueda
     * @throws Exception Error cuando no es puede conectar a la base de datos u ocurre un error al ejecutar la sentencia
     */
    public List<ModelPaciente> fetchAll() throws Exception {
        Connection conexion = adminConexion.abrirConexion();
        if(conexion != null) {
            try {
                String sentenciaSql = "SELECT * FROM "+tableName;
                ResultSet resultados = conexion.prepareStatement(sentenciaSql).executeQuery();
                ArrayList<ModelPaciente> listadoPacientes = new ArrayList<ModelPaciente>();
                if(resultados.isBeforeFirst()) {
                    while(resultados.next()) {
                        ModelPaciente objetoTemporal = new ModelPaciente();
                        objetoTemporal.setIdentificacion(resultados.getString("identificacion"));
                        objetoTemporal.setNombres(resultados.getString("nombres"));
                        objetoTemporal.setApellidos(resultados.getString("apellidos"));
                        objetoTemporal.setCorreo(resultados.getString("correo"));
                        objetoTemporal.setTelefono(resultados.getString("telefono"));
                        objetoTemporal.setFechaNacimiento(resultados.getString("fecha_nacimiento"));
                        listadoPacientes.add(objetoTemporal);
                    }
                }
                return listadoPacientes;
            } catch (Exception exc) {
                Log.i("CimedMsg", "Error al consultar pacientes en la base de datos: : "+exc.getMessage());
                throw new Exception("Error al consultar pacientes.");
            } finally {
                adminConexion.cerrarConexion();
            }
        } else {
            throw new Exception("Error de conexión a la base de datos.");
        }
    }

    /**
     * Buscar el registro de un paciente en la base de datos filtrando por el id proporcionado.
     * @param identificacion Identificación por la cual se desea filtar la busqueda.
     * @return List<ModelPaciente> Lista con el resultado de la busqueda
     * @throws Exception Erro cuando no es puede conectar a la base de datos u ocurre un error al ejecutar la sentencia
     */
    public List<ModelPaciente> filterById(String identificacion) throws Exception {
        Connection conexion = adminConexion.abrirConexion();
        if(conexion != null) {
            try {
                String sentenciaSql = "SELECT *  FROM "+tableName+" WHERE identificacion LIKE ?";
                PreparedStatement select = conexion.prepareStatement(sentenciaSql);
                select.setString(1, "%"+identificacion+"%");
                ResultSet resultados = select.executeQuery();
                ArrayList<ModelPaciente> listadoPacientes = new ArrayList<ModelPaciente>();
                if(resultados.isBeforeFirst()) {
                    while(resultados.next()) {
                        ModelPaciente objetoTemporal = new ModelPaciente();
                        objetoTemporal.setIdentificacion(resultados.getString("identificacion"));
                        objetoTemporal.setNombres(resultados.getString("nombres"));
                        objetoTemporal.setApellidos(resultados.getString("apellidos"));
                        objetoTemporal.setCorreo(resultados.getString("correo"));
                        objetoTemporal.setTelefono(resultados.getString("telefono"));
                        objetoTemporal.setFechaNacimiento(resultados.getString("fecha_nacimiento"));
                        listadoPacientes.add(objetoTemporal);
                    }
                }
                return listadoPacientes;
            } catch (Exception exc) {
                Log.i("CimedMsg", "Error al consultar pacientes en la base de datos: : "+exc.getMessage());
                throw new Exception("Error al consultar pacientes."+exc.getMessage());
            } finally {
                adminConexion.cerrarConexion();
            }
        } else {
            throw new Exception("Error de conexión a la base de datos.");
        }
    }

    public ModelPaciente fetchById(String identificacion) throws Exception {
        Connection conexion = adminConexion.abrirConexion();
        if(conexion != null) {
            try {
                String sentenciaSql = "SELECT *  FROM "+tableName+" WHERE identificacion = ?";
                PreparedStatement select = conexion.prepareStatement(sentenciaSql);
                select.setString(1, identificacion);
                ResultSet resultados = select.executeQuery();
                ModelPaciente paciente = new ModelPaciente();
                if(resultados.next()) {
                    paciente.setIdentificacion(resultados.getString("identificacion"));
                    paciente.setNombres(resultados.getString("nombres"));
                    paciente.setApellidos(resultados.getString("apellidos"));
                    paciente.setCorreo(resultados.getString("correo"));
                    paciente.setTelefono(resultados.getString("telefono"));
                    paciente.setFechaNacimiento(resultados.getString("fecha_nacimiento"));
                    return paciente;
                } else {
                    return null;
                }
            } catch (Exception exc) {
                Log.i("CimedMsg", "Error al consultar pacientes en la base de datos: : "+exc.getMessage());
                throw new Exception("Error al consultar pacientes."+exc.getMessage());
            } finally {
                adminConexion.cerrarConexion();
            }
        } else {
            throw new Exception("Error de conexión a la base de datos.");
        }
    }

}
