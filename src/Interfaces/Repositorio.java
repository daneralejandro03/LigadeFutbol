/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import java.util.LinkedList;

/**
 *
 * @author daner
 */
public interface Repositorio {
    
    public boolean create(Object elObjeto);
    public Object get(String id);
    public LinkedList <Object> index();
    public Object update(Object elObjeto);
    public boolean delete(String id);
}
