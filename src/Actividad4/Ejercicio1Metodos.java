/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actividad4;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Ejercicio1Metodos {

    Connection conexion;

    public Ejercicio1Metodos(Connection conexion) {
        this.conexion = conexion;
    }

    public void eliminarProxecto(int Num_proxecto) throws SQLException {
        String sql1 = "delete from proxecto where Num_proxecto = ?";
        String sql2 = "delete from empregado_proxecto where Num_proxecto = ?";

        PreparedStatement sentenza1 = conexion.prepareStatement(sql1);
        sentenza1.setInt(1, Num_proxecto);

        PreparedStatement sentenza2 = conexion.prepareStatement(sql2);
        sentenza2.setInt(1, Num_proxecto);

        try {

            conexion.setAutoCommit(false);

            int filas = sentenza1.executeUpdate();
            if (filas == 0) {
                System.out.println("Non existe o proxecto " + Num_proxecto);
            } else {
                System.out.println("Proxecto eliminado con exito");
                conexion.commit();
            }

        } catch (SQLException ex) {

            conexion.rollback();
            sentenza2.executeUpdate();
            sentenza1.executeUpdate();
            conexion.commit();

        } finally {

            conexion.setAutoCommit(true);
            sentenza1.close();
            sentenza2.close();

        }

    }

    public void aumentarSalarioDepartamento(int num_departamento, int aumento) throws SQLException {
        String sql1 = "SELECT * FROM DEPARTAMENTO WHERE Num_departamento = ?";
        String sql2 = "UPDATE EMPREGADO SET Salario = Salario + ? WHERE Num_departamento_pertenece = ?";
        PreparedStatement sentenza1 = conexion.prepareStatement(sql1);
        PreparedStatement sentenza2 = conexion.prepareStatement(sql2);

        try {

            sentenza1.setInt(1, num_departamento);
            ResultSet lista = sentenza1.executeQuery();

            if (lista.next()) {

                conexion.setAutoCommit(false);

                sentenza2.setInt(1, aumento);
                sentenza2.setInt(2, num_departamento);

                int actualizacions;
                actualizacions = sentenza2.executeUpdate();

                if (actualizacions > 0) {
                    conexion.commit();
                    System.out.println("Salario aumentado a " + actualizacions + " trabajadores");
                } else {
                    System.out.println("Non existen empleados no departamento");
                }

            } else {
                System.out.println("Non existe o departamento");
            }

        } catch (SQLException ex) {
            conexion.rollback();
        } finally {
            conexion.setAutoCommit(true);
            sentenza1.close();
            sentenza2.close();
        }
    }

    public void infoIllamento() throws SQLException {
        DatabaseMetaData datosbd = conexion.getMetaData();
        System.out.println((datosbd.supportsTransactions() ? "Soporta tansacciones" : "No soporta transaciones"));
        System.out.println("Nivel de illamento actual: " + conexion.getTransactionIsolation());
        System.out.println((datosbd.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED) ? "Soporta aislamiento nivel 1  TRANSACTION_READ_UNCOMMITTED" : "No soporta aislamiento nivel 1  TRANSACTION_READ_UNCOMMITTED"));
        System.out.println((datosbd.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_COMMITTED) ? "Soporta aislamiento nivel 2 TRANSACTION_READ_COMMITTED" : "No soporta aislamiento nivel 2 TRANSACTION_READ_COMMITTED"));
        System.out.println((datosbd.supportsTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ) ? "Soporta aislamiento nivel 3  TRANSACTION_REPEATABLE_READ" : "No soporta aislamiento nivel 3  TRANSACTION_REPEATABLE_READ"));
        if (datosbd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE)) {
            System.out.println("Soporta aislamiento nivel 4 TRANSACTION_SERIALIZABLE");
            conexion.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } else {
            System.out.println("No soporta aislamiento nivel 4 TRANSACTION_SERIALIZABLE");
        }
        System.out.println("Nivel de illamento actual: " + conexion.getTransactionIsolation());
        conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
    }

    public void aumentaSalarioEmpregado(String nss, int aumento, int limite) throws SQLException {
        String sql1 = "SELECT NSS, Salario FROM EMPREGADO WHERE NSS = ?";
        String sql2 = "UPDATE EMPREGADO SET Salario = Salario + ? WHERE NSS = ?";
        PreparedStatement sentenza1 = conexion.prepareStatement(sql1);
        PreparedStatement sentenza2 = conexion.prepareStatement(sql2);

        try {

            conexion.setAutoCommit(false);
            sentenza1.setString(1, nss);
            ResultSet lista = sentenza1.executeQuery();
            if (lista.next()) {
                Savepoint p1 = conexion.setSavepoint();
                sentenza2.setInt(1, aumento);
                sentenza2.setString(2, nss);
                sentenza2.executeUpdate();
                int salario = lista.getInt(2);
                System.out.println(salario);
                if (salario + aumento > limite) {
                    conexion.rollback(p1);
                    System.out.println("Salario previo: " + salario + " + " + aumento + " supera o limite(" + limite + "): " + (salario + aumento));
                } else {
                    conexion.commit();
                    System.out.println("Salario previo: " + salario + " + " + aumento + " non supera o limite(" + limite + "): " + (salario + aumento));
                }
            } else {
                System.out.println("Non existe o empregado");
            }

        } catch (SQLException ex) {
            System.out.println("Error na base de datos");
            conexion.rollback();
        }
        sentenza1.close();
        sentenza2.close();
    }

    void aumentaSalarioLocalidade(int aumento, int limite, String localidade) throws SQLException {
        String sql1 = "SELECT Salario, NSS FROM EMPREGADO WHERE Localidade = ?";
        String sql2 = "UPDATE EMPREGADO SET Salario = Salario + ? WHERE Localidade = ? AND NSS = ?";
        PreparedStatement sentenza1 = conexion.prepareStatement(sql1);
        PreparedStatement sentenza2 = conexion.prepareStatement(sql2);
        String NSS;
        boolean existenRexistros = false;
        
        try {

            conexion.setAutoCommit(false);
            sentenza1.setString(1, localidade);
            ResultSet lista = sentenza1.executeQuery();
            while (lista.next()) {
                existenRexistros = true;
                Savepoint p1 = conexion.setSavepoint();
                sentenza2.setInt(1, aumento);
                sentenza2.setString(2, localidade);
                NSS = lista.getString(2);
                sentenza2.setString(3, NSS);
                sentenza2.executeUpdate();
                int salario = lista.getInt(1);
                if (salario + aumento > limite) {
                    conexion.rollback(p1);
                    System.out.println("Salario previo: " + salario + " + " + aumento + " supera o limite(" + limite + "): " + (salario + aumento));
                } else {
                    conexion.commit();
                    System.out.println("Salario previo: " + salario + " + " + aumento + " non supera o limite(" + limite + "): " + (salario + aumento));
                }
            }
            if (existenRexistros == false){
                System.out.println("Non existen empregados na localidade " + localidade);
            }

        } catch (SQLException ex) {
            System.out.println("Error na base de datos");
            conexion.rollback();
        }
        sentenza1.close();
        sentenza2.close();
    }
    
    //TRANSACION por LOTES
    void engadirLoteProxectos(ArrayList<Proxecto> loteProxectos) throws SQLException {
        try {
            conexion.setAutoCommit(false);
            
            String sql = "INSERT INTO Proxecto (Num_proxecto, Nome_proxecto, Lugar, Num_departamento_controla) VALUES (?,?,?,?)";
            PreparedStatement sentenza1 = conexion.prepareStatement(sql);
            
            for (Proxecto proxecto : loteProxectos) {
                sentenza1.setInt(1, proxecto.getNum_proxecto());
                sentenza1.setString(2, proxecto.getNome_proxecto());
                sentenza1.setString(3, proxecto.getLugar());
                sentenza1.setInt(4, proxecto.getNum_departamento_controla());
                sentenza1.addBatch();
            }
            int[] contador = sentenza1.executeBatch();
            
            conexion.commit();
            sentenza1.close();
            conexion.setAutoCommit(true);

        } catch (SQLException ex) {
            conexion.rollback();
            Logger.getLogger(Ejercicio1Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrar() {
        try {
            conexion.setAutoCommit(true);
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio1Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
