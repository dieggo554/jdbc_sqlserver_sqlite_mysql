/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Actividad2;

import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class Ejercicio3Principal {
    public static void main(String[] args) {
        Ejercicio3Metodos metodos = new Ejercicio3Metodos(Conexiones.MYSQL.getConexion());
        
        String nomeDepartamento = "INFORMÁTICA";
        String nomeProxecto = "PORTAL";
        
        metodos.engadirProxectoDepartamento(nomeDepartamento, nomeProxecto);
        
        Proxecto proxecto = new Proxecto(555, 1, "SegundoNovoProxecto", "Vigo");
        
        metodos.novoProxecto(proxecto);
        
        metodos.borrarProxecto(555);
        
        ArrayList<Proxecto> lista = metodos.mostrarProxectos(nomeDepartamento);
        
        for (Proxecto nProxecto : lista) {
            System.out.println(nProxecto);
        }
        
        metodos.cerrarBD();
    }
}
