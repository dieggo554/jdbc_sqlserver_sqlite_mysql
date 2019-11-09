/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Actividad2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Ejercicio1 {
    
    private static Connection conexion;
    
    public static void main(String[] args) {
        conexion = Actividad2.Conexiones.MYSQL.getConexion();
        
        String departamento = "aaa";
        int cantidad = 10;
        
//        actualizarSalario(departamento, cantidad); 
        
        int numDepartamento = 555;
        String nomeDepartamento = "NovoDepartamento";
        String NSSDirixe = "1010001";
        
        novoDepartamento(numDepartamento, nomeDepartamento, NSSDirixe);
        
        String NSSEmpregado = "0010010";
        int NumProxecto = 8;
        //horas 20
        
        eliminarEmpregadodeProxecto(NSSEmpregado, NumProxecto);
    }

    private static void actualizarSalario(String departamento, int cantidad) {
        String sql = "UPDATE EMPREGADO SET salario = salario  + ? WHERE (Num_departamento_pertenece = ?)";
        
        try {
            
            PreparedStatement sentenza = conexion.prepareStatement(sql);
            
            sentenza.setInt(1, cantidad);
            
            sentenza.setString(2, departamento);
            
            boolean retorno = sentenza.execute();
            
            System.out.println(retorno);
            
        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void novoDepartamento(int numDepartamento, String nomeDepartamento, String NSSDirixe) {
        
        String sql = "INSERT INTO DEPARTAMENTO (Num_departamento,Nome_departamento,NSS_dirige,Data_direccion) VALUES (?,?,?,?)";
        
        try {
            
            PreparedStatement sentenza = conexion.prepareStatement(sql);
            
            sentenza.setInt(1, numDepartamento);
            sentenza.setString(2, nomeDepartamento);
            sentenza.setString(3, NSSDirixe);
            sentenza.setDate(4, new Date(new java.util.Date().getTime()));
            
            boolean retorno = sentenza.execute();
            
            System.out.println(retorno);
            
        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void eliminarEmpregadodeProxecto(String NSSEmpregado, int NumProxecto) {
        String sql = "DELETE FROM EMPREGADO_PROXECTO WHERE NSS_Empregado = ? AND Num_proxecto = ?";
        
        try {
            
            PreparedStatement sentenza = conexion.prepareStatement(sql);
            
            sentenza.setString(1, NSSEmpregado);
            sentenza.setInt(2, NumProxecto);
            
            boolean retorno = sentenza.execute();
            
            System.out.println(retorno);
            
        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
