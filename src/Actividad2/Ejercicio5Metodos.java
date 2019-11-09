/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actividad2;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Ejercicio5Metodos {

    private static Connection conexion;

    public Ejercicio5Metodos(Connection conexion) {
        this.conexion = conexion;
    }
    
    public void cerrarBD() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio6Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cambiarDomicilio(String NSS, String rua, int numero, String piso, int codigoPosta, String Localidade) {
        String sql = "{call pr_cambioDomicilio(?,?,?,?,?,?)}";

        try {

            CallableStatement sentenza = conexion.prepareCall(sql);

            sentenza.setString(1, NSS);
            sentenza.setString(2, rua);
            sentenza.setInt(3, numero);
            sentenza.setString(4, piso);
            sentenza.setInt(5, codigoPosta);
            sentenza.setString(6, Localidade);

            sentenza.execute();

            sentenza.close();

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio5Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void verProxecto(int Num_proxecto) {
        String sql = "{call pr_DatosProxecto(?,?,?,?)}";

        try {

            CallableStatement sentenza = conexion.prepareCall(sql);

            sentenza.setInt(1, Num_proxecto);
            sentenza.registerOutParameter(2, Types.VARCHAR);
            sentenza.registerOutParameter(3, Types.VARCHAR);
            sentenza.registerOutParameter(4, Types.INTEGER);

            sentenza.execute();

            if (sentenza.getString(2) != null) {
                
                System.out.println(Num_proxecto + " " + sentenza.getString(2) + " " + sentenza.getString(3) + " " + sentenza.getInt(4));
            
            } else {
                System.out.println("Non existe o proxecto");
            }

            sentenza.close();

        } catch (SQLException ex) {
//            Logger.getLogger(Ejercicio5Metodos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro na base de datos");
        }
    }

    public void verDepartamentoConProxetos(int Cantidade_proxectos) {
        String sql = "{call pr_DepartControlaProxec(?)}";

        try {

            CallableStatement sentenza = conexion.prepareCall(sql);

            sentenza.setInt(1, Cantidade_proxectos);

            boolean seleccion;
            seleccion = sentenza.execute();

            if (!seleccion) {
                System.out.println("Se ha realizado una actualización:");
            } else {
                System.out.println("Se ha realizado una selección");
                ResultSet lista = sentenza.getResultSet();
                while (lista.next()) {
                    System.out.println("Num_departamento: " + lista.getInt(1) + "\tNome_departamento: " + lista.getString(2) + "\tNSS_dirige: " + lista.getString(3) + "\tData_direccion: " + lista.getDate(4));
                }
            }
            sentenza.close();

        } catch (SQLException ex) {
//            Logger.getLogger(Ejercicio5Metodos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro na base de datos");
        }
    }

    public void numEmpleadosDep(String Nome_departamento) {
        String sql = "{?=call fn_nEmpDepart(?)}";

        try {

            CallableStatement sentenza = conexion.prepareCall(sql);

            sentenza.setString(2, Nome_departamento);

            sentenza.registerOutParameter(1, Types.INTEGER);

            sentenza.execute();

            int numEmpl = sentenza.getInt(1);

            System.out.println(Nome_departamento + " tiene " + numEmpl + " empleados");

            sentenza.close();

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio5Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

//PROCEDIMIENTO 1
//
//CREATE PROCEDURE pr_cambioDomicilio(
//    @NSS varchar(15),
//    @rua varchar(30),
//    @numero int,
//    @piso varchar(4),
//    @codigoPostal char(5),
//    @localidade varchar(25))
//AS
//BEGIN
//    UPDATE empregado
//    SET Rua = @rua, Numero_rua = @numero, Piso = @piso, CP = @codigoPostal, Localidade = @Localidade
//    WHERE NSS = @NSS
//END
//PROCEDIMIENTO 2
//
//CREATE PROCEDURE pr_DatosProxecto(
//    @Num_proxecto int,
//    @Nome_proxecto varchar(25) OUTPUT,
//    @Lugar varchar(50) OUTPUT,
//    @Num_departamento_controla int OUTPUT)
//AS
//BEGIN
//    SELECT @Nome_proxecto = Nome_proxecto, @Lugar = Lugar, @Num_departamento_controla = Num_departamento_controla
//    FROM PROXECTO
//    WHERE Num_proxecto = @Num_proxecto
//END
//PROCEDIMIENTO 3
//
//CREATE PROCEDURE pr_DepartControlaProxec(
//    @Cantidade_proxectos int)
//AS
//BEGIN
//    SELECT Num_departamento, Nome_departamento, NSS_dirige, Data_direccion
//    FROM DEPARTAMENTO
//    WHERE Num_departamento IN (
//        SELECT Num_departamento
//        FROM DEPARTAMENTO
//        INNER JOIN PROXECTO ON Num_departamento = Num_departamento_controla
//        GROUP BY Num_departamento
//        HAVING COUNT (Num_departamento) > @Cantidade_proxectos)
//END
//FUNCION 1
//
//CREATE FUNCTION fn_nEmpDepart (
//    @Nome_departamento varchar(25))
//RETURNS int
//AS
//BEGIN
//    DECLARE @resultado int
//    SELECT @resultado = COUNT(NSS)
//    FROM EMPREGADO 
//    WHERE Num_departamento_pertenece = (
//       SELECT Num_departamento
//       FROM DEPARTAMENTO
//       WHERE Nome_departamento = @Nome_departamento)
//    RETURN @resultado
//END
