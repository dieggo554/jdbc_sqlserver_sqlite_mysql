/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Actividad2;

/**
 *
 * @author usuario
 */
public class Ejercicio6Principal {
    public static void main(String[] args) {
        Ejercicio6Metodos metodos = new Ejercicio6Metodos(Conexiones.SQLSERVER.getConexion());
        
//        metodos.tiposResultSet();
        
//        metodos.novoProxecto(new Proxecto(99, 1, "Novo Proxecto", "Vigo"));
        
//        metodos.aumentarSalario(100, 1);
        
        metodos.empregadoConProxectos(3);
        
        metodos.cerrarBD();
    }
}
