/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Actividad2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Ejercicio2 {
    
    private static Connection conexion;
    
    public static void main(String[] args) {
        conexion = Conexiones.MYSQL.getConexion();
        
        String nomeLocalidade = "Vigo";
        
        mostrarEmpregadosDe(nomeLocalidade);
    }

    private static void mostrarEmpregadosDe(String nomeLocalidade) {
        try {
            
            Statement sentenza = conexion.createStatement();
            
            ResultSet lista = sentenza.executeQuery(
            "SELECT emp.Nome, emp.Apelido_1,emp.Apelido_2,emp.Localidade,emp.Salario,emp.Data_nacemento,Nome_departamento,concat(emp2.Nome, ' ', emp2.Apelido_1, ' ', emp2.Apelido_2) as Supervisor\n" +
            "FROM EMPREGADO as emp\n" +
            "INNER JOIN\n" +
            "DEPARTAMENTO as dep\n" +
            "ON Num_departamento_pertenece = Num_departamento\n" +
            "INNER JOIN\n" +
            "EMPREGADO as emp2\n" +
            "ON emp.NSS_Supervisa = emp2.NSS\n" +
            "WHERE emp.Localidade = '"+ nomeLocalidade +"'");
            
            System.out.println("Nome\t\tApel1\t\tApel2\t\tLocalidade\t\tSalario\t\tNacemento\t\tDepartamento\t\tSupervisor\n"
                    + "---------------------------------------------------------------------------------------------------");
            
            while(lista.next()){
                System.out.println(lista.getString("emp.Nome") + " \t\t" 
                        + lista.getString("emp.Apelido_1") + " \t\t" 
                        + lista.getString("emp.Apelido_2") + " \t\t" 
                        + lista.getString("emp.Localidade") + " \t\t"
                        + lista.getFloat("emp.Salario") + " \t\t"
                        + lista.getDate("emp.Data_nacemento") + " \t\t"
                        + lista.getString("Nome_departamento") + " \t\t"
                        + lista.getString("Supervisor"));
            }
            
        } catch (SQLException ex) {
//            Logger.getLogger(Ejercicio2.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print("Erro de acceso a base de datos");
        }
    }
}
