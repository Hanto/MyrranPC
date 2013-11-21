/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Geo;

import Constantes.MiscData;

/**
 * @author Ivan Delgado Huerta
 */
public class Celda 
{
    private Integer[] terreno = new Integer[MiscData.MAPA_Max_Capas_Terreno];
    
    //CONSTRUCTOR:
    public Celda ()
    {
        for (int i=0; i<terreno.length; i++) terreno[i] = -1;         
    }
    
    //GET:
    public Integer[] getTerreno()      { return terreno; }    
}
