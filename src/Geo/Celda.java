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
    private Integer[] terrenoID = new Integer[MiscData.MAPA_Max_Capas_Terreno];
    private Integer muroID = -1;
    
    //SET
    public void setMuro (int muroID)    { this.muroID = muroID; }
    
    //GET:
    public Integer[] getTerrenoID()      { return terrenoID; }    
    
    //CONSTRUCTOR:
    public Celda ()
    {
        for (int i=0; i<terrenoID.length; i++) terrenoID[i] = -1;         
    }
    
    public Celda(Celda celdaOrigen)
    {
        for (int i=0; i<celdaOrigen.terrenoID.length;i++)
        { terrenoID[i] = celdaOrigen.terrenoID[i];}
        muroID = celdaOrigen.muroID;
    }
        
}
