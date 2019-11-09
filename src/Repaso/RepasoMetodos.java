/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repaso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class RepasoMetodos {

    private static Connection conexion;

    public RepasoMetodos(String bd, String user, String pass) {
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conexion = (DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=" + bd, user, pass));

        } catch (ClassNotFoundException ex) {
            System.out.println("Error en el conector de la base de datos");
            Logger.getLogger(RepasoPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("Error al conectarse a la base de datos");
            Logger.getLogger(RepasoPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void ejecutarSQL(String cadeas) {
        int actualizados;
        ResultSet recuperados;
        Statement sentenza;
        String[] partes = cadeas.split(";");
        
        for (String cadea : partes) {
//            System.out.println(cadea);
            try {
                
                sentenza = conexion.createStatement();
                if (sentenza.execute(cadea)){
                    recuperados = sentenza.getResultSet();
                    System.out.println("\"" + cadea.trim() + "\""  + " devolve:");
                    mostrarResultSet(recuperados);
                } else {
                    actualizados = sentenza.getUpdateCount();
                    System.out.println("\nActualizados " + actualizados + " rexistros coa instrucción \"" + cadea + "\"");
                }
                
            } catch (SQLException ex) {
                System.out.println("\nErro na cadea \"" + cadea.trim() + "\": " + ex.getMessage());
//                Logger.getLogger(RepasoMetodos.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("");
        }
    }

    private void mostrarResultSet(ResultSet recuperados) throws SQLException {
        ResultSetMetaData metadatos = null;
        int numColumnas;
        
        metadatos = recuperados.getMetaData();
            numColumnas = metadatos.getColumnCount();
                for (int i = 0; i < numColumnas; i++) {
                    System.out.printf("%-15s", metadatos.getColumnName(i + 1));
            }
        
        while(recuperados.next()){
                System.out.println("");
                for (int i = 0; i < numColumnas; i++) {
                    System.out.printf("%-15s", recuperados.getObject(i + 1));
            }
        }
    }
}
