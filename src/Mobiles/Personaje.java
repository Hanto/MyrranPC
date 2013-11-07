/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Mobiles;

/**
 * @author Ivan Delgado Huerta
 */

// la clase Personaje incluye a todos los seres vivos del juego, sean controlados por el jugador o por la maquina
public class Personaje extends Mob
{
    protected int nivel;
    protected int actualHPs;
    protected int totalHPs;
    
    protected boolean isCasteando = false;
}
