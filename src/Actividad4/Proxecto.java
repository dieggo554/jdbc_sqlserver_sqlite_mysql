/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Actividad4;

/**
 *
 * @author usuario
 */
public class Proxecto {
    private int Num_proxecto, Num_departamento_controla;
    private String Nome_proxecto, Lugar;

    public Proxecto(){}
    
    public Proxecto(int Num_proxecto, int Num_departamento_controla, String Nome_proxecto, String Lugar) {
        this.Num_proxecto = Num_proxecto;
        this.Num_departamento_controla = Num_departamento_controla;
        this.Nome_proxecto = Nome_proxecto;
        this.Lugar = Lugar;
    }

    public int getNum_proxecto() {
        return Num_proxecto;
    }

    public int getNum_departamento_controla() {
        return Num_departamento_controla;
    }

    public String getNome_proxecto() {
        return Nome_proxecto;
    }

    public String getLugar() {
        return Lugar;
    }

    public void setNum_proxecto(int Num_proxecto) {
        this.Num_proxecto = Num_proxecto;
    }

    public void setNum_departamento_controla(int Num_departamento_controla) {
        this.Num_departamento_controla = Num_departamento_controla;
    }

    public void setNome_proxecto(String Nome_proxecto) {
        this.Nome_proxecto = Nome_proxecto;
    }

    public void setLugar(String Lugar) {
        this.Lugar = Lugar;
    }

    @Override
    public String toString() {
        return "Proxecto{" + "Num_proxecto=" + Num_proxecto + ", Num_departamento_controla=" + Num_departamento_controla + ", Nome_proxecto=" + Nome_proxecto + ", Lugar=" + Lugar + '}';
    }
}
