/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Constantes;

import Graficos.Recursos;
import static Main.Mundo.listaDeSpells;
import static Main.Mundo.listaDeTiposSpell;
import Skills.Spell;
import TiposSpell.AbstractTipoSpell;
import TiposSpell.Bolt;
import TiposSpell.EditarTerreno;

/**
 * @author Ivan Delgado Huerta
 */
public class LoadData 
{
    public static void cargarListaDeTiposSpell ()
    {   //BOLT:
        AbstractTipoSpell bolt = new Bolt();
        listaDeTiposSpell.add(bolt);
        //EDITAR TERRENO:
        AbstractTipoSpell editar = new EditarTerreno();
        listaDeTiposSpell.add(editar);
    }
    
    public static void cargarListaDeSpells ()
    {   //FIREBOLT:
        Spell spell = new Spell(listaDeTiposSpell.get(SpellData.BOLT_ID));
        spell.setNombre(SpellData.FIREBOLT_Nombre);
        spell.setDescripcion(SpellData.FIREBOLT_Descripcion);
        spell.setIcono(Recursos.listaIconos.get(SpellData.FIREBOLT_Icono));
        listaDeSpells.add(spell);
        
        //TERRAFORMAR:
        spell = new Spell(listaDeTiposSpell.get(SpellData.EDITARTERRENO_ID));
        spell.setNombre(SpellData.TERRAFORMAR_Nombre);
        spell.setDescripcion(SpellData.TERRAFORMAR_Descripcion);
        spell.setIcono(Recursos.listaIconos.get(SpellData.TERRAFORMAR_Icono));
        listaDeSpells.add(spell);
    }
}