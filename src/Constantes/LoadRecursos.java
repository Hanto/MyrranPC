/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Constantes;
// @author Ivan Delgado Huerta

import Main.Mundo;
import Resources.Recursos;
import Skill.SkillRecursos;
import UI.BarraTerrenos;

public class LoadRecursos 
{
    public static void cargarRecursos ()
    {
        //RAZAS:
        //Golem:
        Recursos.añadirRaza();
        Recursos.salvarCuerpo(0, "Golem");
        
        Recursos.añadirRaza();
        Recursos.salvarCuerpo           (0, "Golem");
        
        Recursos.salvarYelmo            (0, "Desnudo");
        Recursos.salvarBotas            (0, "Desnudo");
        Recursos.salvarGuantes          (0, "Desnudo");
        Recursos.salvarHombreras        (0, "Desnudo");
        Recursos.salvarPantalones       (0, "Desnudo");
        Recursos.salvarPeto             (0, "Desnudo");
        Recursos.salvarCapasTraseras    (0, "Desnudo");
        Recursos.salvarCapasFrontales   (0, "Desnudo");
        
        Recursos.salvarCabeza           (0, "Cabeza1");
        
        Recursos.salvarBotas            (0, "BotasGolem01");
        Recursos.salvarGuantes          (0, "GuantesGolem01");
        Recursos.salvarPeto             (0, "PetoGolem01");
        Recursos.salvarHombreras        (0, "HombrerasGolem01");
        Recursos.salvarPantalones       (0, "PantalonesGolem01");
        
        //ARBOLES:
        Recursos.salvarTronco           ("Tronco2", -50, 50, -45, 65, 40, 65);
        Recursos.salvarCopa             ("BolaGrandeArbol2");
        Recursos.salvarCopa             ("BolaMedianaArbol2");
        Recursos.salvarCopa             ("Bolapequeñaarbol2");
        
        //ICONOS:
        Recursos.salvarIcono            ("FireBall");
        Recursos.salvarIcono            ("FrostBolt");
        Recursos.salvarIcono            ("Editar");
        Recursos.salvarIcono            ("Muros");
        
        //SPELLS:
        SkillRecursos.setAtlas(Recursos.atlas);
        SkillRecursos.salvarCasteo           ("Fireball01");
        SkillRecursos.salvarEfectoDeSpell    ("Fireball02");
        SkillRecursos.salvarCasteo           ("FrostBolt01");
        SkillRecursos.salvarEfectoDeSpell    ("FrostBolt02");
        
        //TERRENOS:
        Mundo.añadirTerreno             ("Tierra4");
        Mundo.añadirTerreno             ("Tierra3");
        Mundo.añadirTerreno             ("Tierra1");
        Mundo.añadirTerreno             ("Tierra2");
        Mundo.añadirTerreno             ("Cesped1");
        Mundo.añadirTerreno             ("Cesped2");
        Mundo.añadirTerreno             ("Cesped3");
        Mundo.añadirTerreno             ("Baldosas3");
        Mundo.añadirTerreno             ("Baldosas1");
        Mundo.añadirTerreno             ("Baldosas2");
        Mundo.añadirTerreno             ("Arena1");
        
        Mundo.barraTerrenos = new BarraTerrenos();
        
        //MUROS:
        Mundo.añadirMuro("MuroBase", "MuroMedio", "MuroTecho");
    }
}
