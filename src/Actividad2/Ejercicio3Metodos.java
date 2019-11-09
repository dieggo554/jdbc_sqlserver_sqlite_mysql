/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actividad2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Ejercicio3Metodos {

    private static Connection conexion;

    public Ejercicio3Metodos(Connection conexion) {
        this.conexion = conexion;
    }
    
    public void cerrarBD() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio6Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void engadirProxectoDepartamento(String nomeDepartamento, String nomeProxecto) {
        nomeDepartamento = nomeDepartamento.toUpperCase();
        nomeProxecto = nomeProxecto.toUpperCase();

        String sql = "UPDATE proxecto "
                + " SET Num_departamento_controla = (SELECT Num_departamento from departamento where Nome_departamento = ?) "
                + " where Nome_proxecto = ? ";

        try {

            PreparedStatement sentenza = conexion.prepareStatement(sql);

            sentenza.setString(1, nomeDepartamento);
            sentenza.setString(2, nomeProxecto);

            sentenza.execute();

            System.out.println("Exito");
            
            sentenza.close();

        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex) {
            System.out.println("Erro de integridade referencial " + ex.getMessage());
            
//          Controlar erros, con if, cada bd e distinta
            System.out.println(ex.getErrorCode());
        } catch (SQLException ex) {
//            Logger.getLogger(Ejercicio3Metodos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro na base de datos");
        }

    }

    public void novoProxecto(Proxecto proxecto) {
        String sql = "INSERT INTO proxecto (Num_proxecto, Nome_proxecto, Lugar, Num_departamento_controla) "
                + "VALUES (?,?,?,?)";

        try {

            PreparedStatement sentenza = conexion.prepareStatement(sql);

            sentenza.setInt(1, proxecto.getNum_proxecto());
            sentenza.setString(2, proxecto.getNome_proxecto());
            sentenza.setString(3, proxecto.getLugar());
            sentenza.setInt(4, proxecto.getNum_departamento_controla());

            sentenza.executeUpdate();

            System.out.println("Proxecto engadido correctamente");
            
            sentenza.close();

        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex) {
            System.out.println("Erro de integridade referencial " + ex.getMessage());
        } catch (SQLException ex) {
//            Logger.getLogger(Ejercicio3Metodos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro na base de datos");
        }
    }

    public void borrarProxecto(int Num_proxecto) {
        String sql = "delete from proxecto where Num_proxecto = ?";

        try {

            PreparedStatement sentenza = conexion.prepareStatement(sql);

            sentenza.setInt(1, Num_proxecto);

            int modificacions = sentenza.executeUpdate();

            if (modificacions == 0) {
                System.out.println("Non existe o proxecto");
            } else {
                System.out.println("Proxecto " + Num_proxecto + " borrado correctamente");
            }
            
            sentenza.close();

        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex) {
            System.out.println("Erro de integridade referencial " + ex.getMessage());
        } catch (SQLException ex) {
//            Logger.getLogger(Ejercicio3Metodos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro na base de datos");
        }
    }

    //Ejercicio 4
    public ArrayList<Proxecto> mostrarProxectos(String Nome_departamento) {
        String sql = "SELECT * FROM proxecto WHERE Num_departamento_controla ="
                + "(SELECT Num_departamento FROM departamento WHERE Nome_departamento = ?)";

        try {

            PreparedStatement sentenza = conexion.prepareStatement(sql);

            sentenza.setString(1, Nome_departamento);

            ResultSet cursor = sentenza.executeQuery();

            Proxecto proxecto = new Proxecto();

            ArrayList<Proxecto> lista = new ArrayList<>();

            while (cursor.next()) {
                proxecto.setNum_proxecto(cursor.getInt("Num_proxecto"));
                proxecto.setNome_proxecto(cursor.getString("Nome_proxecto"));
                proxecto.setLugar(cursor.getString("Lugar"));
                proxecto.setNum_departamento_controla(cursor.getInt("Num_departamento_controla"));
                lista.add(proxecto);
            }
            
            sentenza.close();
            
            return lista;
            
        } catch (SQLException ex) {
//            Logger.getLogger(Ejercicio3Metodos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro na base de datos");
            return null;
        }
    }
}
