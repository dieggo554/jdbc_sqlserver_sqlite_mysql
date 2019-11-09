/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Actividad4;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class Ejercicio1Principal {
    public static void main(String[] args) throws SQLException {
        Ejercicio1Metodos metodos = new Ejercicio1Metodos(Conexiones.SQLSERVER.getConexion());
        
        metodos.eliminarProxecto(2);
        
        metodos.aumentarSalarioDepartamento(1, 200);
        
        metodos.infoIllamento();
        
        metodos.aumentaSalarioEmpregado("0010010", 400, 4000);
        
        metodos.aumentaSalarioLocalidade(50, 5500, "Vigo");
        
        ArrayList<Proxecto> loteProxectos = new ArrayList<>();
        loteProxectos.add(new Proxecto(999, 1, "Proxecto1", "Vigo"));
        loteProxectos.add(new Proxecto(998, 1, "Proxecto2", "Vigo"));
        loteProxectos.add(new Proxecto(995, 1, "Proxecto3", "Vigo"));
        loteProxectos.add(new Proxecto(996, 1, "Proxecto4", "Vigo"));
        loteProxectos.add(new Proxecto(995, 1, "Proxecto5", "Vigo"));
        
        metodos.engadirLoteProxectos(loteProxectos);
        
        metodos.cerrar();
    }
}
