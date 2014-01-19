/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Geo;

import Constantes.MiscData;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**w
 * @author Ivan Delgado Huerta
 */
public class Celda implements KryoSerializable
{
    private int [] terrenoID = new int[MiscData.MAPA_Max_Capas_Terreno];
    private Integer muroID = -1;
    public Muro muro;
    
    //SET
    public void setMuro (int muroID)    { this.muroID = muroID; }
    
    //GET:
    public int [] getTerrenoID()        { return terrenoID; }    
    public int getMuroID()              { return muroID; }   
    
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

    @Override
    public void write(Kryo kryo, Output output) 
    {
        output.writeInts(terrenoID);
        output.writeInt(muroID);
    }

    @Override
    public void read(Kryo kryo, Input input) 
    {
        terrenoID = input.readInts(MiscData.MAPA_Max_Capas_Terreno);
        muroID = input.readInt();
    }    
}
