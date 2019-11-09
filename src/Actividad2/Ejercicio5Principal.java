/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Actividad2;

import java.sql.Connection;

/**
 *
 * @author usuario
 */
public class Ejercicio5Principal {
    public static void main(String[] args) {
        Ejercicio5Metodos metodos = new Ejercicio5Metodos(Conexiones.SQLSERVER.getConexion());
    
        metodos.cambiarDomicilio("0010010", "Montero Ríos", 145, "6-G", 36208, "Vigo");
        
        metodos.verProxecto(1);
        
        metodos.verDepartamentoConProxetos(2);
        
        metodos.numEmpleadosDep("PERSOAL");
        
        metodos.cerrarBD();
    }
}
