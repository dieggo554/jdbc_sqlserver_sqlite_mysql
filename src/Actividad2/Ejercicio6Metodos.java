/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actividad2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Ejercicio6Metodos {

    private static Connection conexion;

    public Ejercicio6Metodos(Connection conexion) {
        this.conexion = conexion;
    }

    public void cerrarBD() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio6Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tiposResultSet() {
        try {
            DatabaseMetaData md = conexion.getMetaData();   // obtén os metadatos

            // Verifica os tipos de ResultSet
            System.out.println("Soporta cursor TYPE_FORWARD_ONLY: "
                    + md.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY));
            System.out.println("Soporta cursor TYPE_SCROLL_INSENSITIVE: "
                    + md.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
            System.out.println("Soporta cursor TYPE_SCROLL_SENSITIVE: "
                    + md.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));

            // Verifica a concorrencia do ResultSet
            System.out.println("Soporta o cursor CONCUR_READ_ONLY: "
                    + md.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY));
            System.out.println("Soporta o cursor CONCUR_UPDATABLE: "
                    + md.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE));

            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio6Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void novoProxecto(Proxecto proxecto) {
        String sql = "SELECT * FROM PROXECTO";

        try {

            Statement sentenza = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet lista = sentenza.executeQuery(sql);

            if (existeNomeProxecto(proxecto.getNum_proxecto())) {
                System.out.println("O numero de proxecto xa existe");
            } else {
                if (existeProxecto(lista, proxecto)) {
                    System.out.println("Número e nome repetidos");
                } else {

                    lista.moveToInsertRow(); //move o cursor a fila de inserción
                    //crear unha fila nova
                    lista.updateInt("Num_proxecto", proxecto.getNum_proxecto());
                    lista.updateString("Nome_proxecto", proxecto.getNome_proxecto());
                    lista.updateString("Lugar", proxecto.getLugar());
                    lista.updateInt("Num_departamento_controla", proxecto.getNum_departamento_controla());
                    lista.insertRow(); //insertamos a fila na base de datos

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio6Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean existeNomeProxecto(int num_proxecto) {
        String sql = "SELECT * FROM PROXECTO";
        boolean existe = false;

        try {

            Statement sentenza = conexion.createStatement();

            ResultSet lista = sentenza.executeQuery(sql);

            while (lista.next()) {
                if (lista.getInt("Num_proxecto") == num_proxecto) {
                    existe = true;
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio6Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }

    private boolean existeProxecto(ResultSet lista, Proxecto proxecto) {
        boolean repetido = false;
        try {

            while (lista.next()) {
                if (lista.getInt("Num_proxecto") == proxecto.getNum_proxecto()) {
                    if (lista.getString("Nome_proxeto").equalsIgnoreCase(proxecto.getNome_proxecto())) {
                        repetido = true;
                        break;
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio6Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repetido;
    }

    void aumentarSalario(int cantidad, int num_departamento) {
        String sql = "SELECT Salario\n"
                + "FROM EMPREGADO\n"
                + "WHERE Num_departamento_pertenece = ?";

        try {

            PreparedStatement sentenza = conexion.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            sentenza.setInt(1, num_departamento);
            
            ResultSet lista = sentenza.executeQuery();

            while (lista.next()) {
                Double cantidadActual = lista.getDouble(1);
                lista.updateDouble(1, cantidadActual + cantidad);
                lista.updateRow(); //actualizar a base de datos
            }

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio6Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void empregadoConProxectos(int i) {
        
    }
}
