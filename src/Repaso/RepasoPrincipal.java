/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repaso;

import Actividad4.Conexiones;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class RepasoPrincipal {
    
    private static RepasoMetodos metodos;
    
    public static void main(String[] args) {
//        for (String string : args) {
//            System.out.println(string);
//        }
        
        if (args.length == 4) {
            metodos = new RepasoMetodos(args[0], args[1], args[2]);
            
            metodos.ejecutarSQL(args[3]);
        } else {
            System.out.println("Debe introducir 4 argumentos: BD, Usuario, Contraseña, \"sent1;sent2;...\"");
        }
    }
}
