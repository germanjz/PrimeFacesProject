/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.ejemplo.gallery.model;

import java.io.Serializable;

/**
 *
 * @author Germán
 */
public class Fotos implements Serializable {
    private String nombre;
    private boolean seleccionado;

    /**
     * Default Constructor
     */
    public Fotos() {
        super();
    }

    /**
     * 
     * @param nombre
     * @param seleccionado 
     */
    public Fotos(String nombre, boolean seleccionado) {
        this.nombre = nombre;
        this.seleccionado = seleccionado;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the seleccionado
     */
    public boolean isSeleccionado() {
        return seleccionado;
    }

    /**
     * @param seleccionado the seleccionado to set
     */
    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    @Override
    public String toString() {
        return "Fotos{" + "nombre=" + nombre + ", seleccionado=" + seleccionado + '}';
    }
}
