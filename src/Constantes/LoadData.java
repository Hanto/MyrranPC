package Constantes;
import Main.Mundo;
import Main.Mundo;
import Skills.Spell;
import TiposSpell.AbstractTipoSpell;
import TiposSpell.Bolt;
import TiposSpell.EditarMuro;
import TiposSpell.EditarTerreno;
//* @author Ivan Delgado Huerta

public class LoadData 
{
    public static void cargarListaDeTiposSpell ()
    {   //BOLT:
        AbstractTipoSpell bolt = new Bolt();
        Main.Mundo.listaDeTiposSpell.add(bolt);
        //EDITAR TERRENO:
        AbstractTipoSpell editar = new EditarTerreno();
        Main.Mundo.listaDeTiposSpell.add(editar);
        //EDITAR MURO:
        AbstractTipoSpell muro = new EditarMuro();
        Main.Mundo.listaDeTiposSpell.add(muro);
    }
    
    public static void cargarListaDeSpells ()
    {   //FIREBOLT:
        Spell spell = new Spell(Main.Mundo.listaDeTiposSpell.get(SpellData.BOLT_ID));
        spell.setNombre(SpellData.FIREBOLT_Nombre);
        spell.setDescripcion(SpellData.FIREBOLT_Descripcion);
        spell.setIcono(SpellData.FIREBOLT_Icono);
        Main.Mundo.listaDeSpells.add(spell);
        
        //FROSBOLT:
        spell = new Spell(Main.Mundo.listaDeTiposSpell.get(SpellData.BOLT_ID));
        spell.setNombre(SpellData.FROSTBOLT_Nombre);
        spell.setDescripcion(SpellData.FROSTBOLT_Descripcion);
        spell.setIcono(SpellData.FROSTBOLT_Icono);
        spell.pixieSeleccionado()[1]=1;
        spell.pixieSeleccionado()[0]=1;
        Main.Mundo.listaDeSpells.add(spell);
        
        //TERRAFORMAR:
        spell = new Spell(Main.Mundo.listaDeTiposSpell.get(SpellData.EDITARTERRENO_ID));
        spell.setNombre(SpellData.TERRAFORMAR_Nombre);
        spell.setDescripcion(SpellData.TERRAFORMAR_Descripcion);
        spell.setIcono(SpellData.TERRAFORMAR_Icono);
        Main.Mundo.listaDeSpells.add(spell);
        
        //MUROFORMAR:
        spell = new Spell(Main.Mundo.listaDeTiposSpell.get(SpellData.EDITARMURO_ID));
        spell.setNombre(SpellData.MUROFORMAR_Nombre);
        spell.setDescripcion(SpellData.MUROFORMAR_Descripcion);
        spell.setIcono(SpellData.MUROFORMAR_Icono);
        Main.Mundo.listaDeSpells.add(spell);
    }
}
