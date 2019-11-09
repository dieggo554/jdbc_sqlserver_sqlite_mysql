package Actividad2;


import Actividad2.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author usuario
 */
public enum Conexiones {
    MYSQL(new String[]{"com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/bdempresa","root","abc123."}), 
    SQLITE(new String[]{"Driver	org.sqlite.JDBC","jdbc:sqlite:C:\\Users\\usuario\\Desktop\\SQLlite\\BDEMPRESA.db","",""}), 
    SQLSERVER(new String[]{"com.microsoft.sqlserver.jdbc.SQLServerDriver","jdbc:sqlserver://localhost;databaseName=BDEMPRESA","sa","abc123."});
    
    String[] cadena;

    private Conexiones(String[] cadena) {
        this.cadena = cadena;
    }
    
    public String getDriver(){
        return cadena[0];
    }
    
    public String getBD(){
        return cadena[1];
    }
    
    public String getUsuario(){
        return cadena[2];
    }
    
    public String getPass(){
        return cadena[3];
    }
    
    //conexion por defecto
    public Connection getConexion(){
        Connection conexion = null;
        try {
            Class.forName(cadena[0]);
            conexion = DriverManager.getConnection(cadena[1], cadena[2], cadena[3]);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexiones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexiones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    } 
}
