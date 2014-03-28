package Constantes;
import Constantes.Skills.AurasData;
import Constantes.Skills.TipoSpellsData;
import Constantes.Skills.TipoAurasData;
import Constantes.Skills.SpellsData;
import Skill.Aura.Aura;
import Skill.Aura.TipoAura;
import Skill.Aura.TiposAura.Bomba;
import Skill.Aura.TiposAura.Dot;
import Skill.SkillBook;
import Skill.Spell.Spell;
import Skill.Spell.TipoSpell;
import Skill.Spell.TiposSpell.EditarMuro;
import Skill.Spell.TiposSpell.EditarTerreno;
import Skill.Spell.TiposSpell.Bolt;
import Skill.Spell.TiposSpell.Heal;
//* @author Ivan Delgado Huerta

public class LoadSkills
{
    public static void LoadAll ()
    {
        cargarListaDeTiposAura();
        cargarListaDeAuras();
        cargarListaDeTiposSpell();
        cargarListaDeSpells();
    }
    
    private static void cargarListaDeTiposAura ()
    {
        //DOT:
        TipoAura tipoAura = new Dot(TipoAurasData.DOT_ID);
        SkillBook.añadirTipoAura(tipoAura);
        //BOMBA:
        tipoAura = new Bomba(TipoAurasData.BOMBA_ID);
        SkillBook.añadirTipoAura(tipoAura);
    }
    
    private static void cargarListaDeAuras ()
    {
        //POISON DOT:
        Aura aura = new Aura (SkillBook.listaDeTiposAura.get(TipoAurasData.DOT_ID));
        aura.setId(AurasData.POISON_ID);
        aura.setNombre(AurasData.POISON_Nombre);
        aura.setDescripcion(AurasData.POISON_Descripcion);
        SkillBook.añadirAura(aura);
        
        //BOMBARETARDADA:
        aura = new Aura (SkillBook.listaDeTiposAura.get(TipoAurasData.BOMBA_ID));
        aura.setId(AurasData.DELAYBOMB_ID);
        aura.setNombre(AurasData.DELAYBOMB_Nombre);
        aura.setDescripcion(AurasData.DELAYBOMB_Descripcion);
        SkillBook.añadirAura(aura);
    }
    
    
    private static void cargarListaDeTiposSpell ()
    {   //BOLT:
        TipoSpell bolt = new Bolt(TipoSpellsData.BOLT_ID);
        SkillBook.añadirTipoSpell(bolt);
        //EDITAR TERRENO:
        TipoSpell editar = new EditarTerreno(TipoSpellsData.EDITARTERRENO_ID);
        SkillBook.añadirTipoSpell(editar);
        //EDITAR MURO:
        TipoSpell muro = new EditarMuro(TipoSpellsData.EDITARMURO_ID);
        SkillBook.añadirTipoSpell(muro);
        //HEAL:
        TipoSpell Heal = new Heal(TipoSpellsData.HEAL_ID);
        SkillBook.añadirTipoSpell(Heal);
    }
    
    private static void cargarListaDeSpells ()
    {   //FIREBOLT: (Bolt)
        Spell skill = new Spell(SkillBook.listaDeTiposSpell.get(TipoSpellsData.BOLT_ID));
        skill.setId(SpellsData.FIREBOLT_ID);
        skill.setNombre(SpellsData.FIREBOLT_Nombre);
        skill.setDescripcion(SpellsData.FIREBOLT_Descripcion);
        skill.setIcono(SpellsData.FIREBOLT_Icono);
        skill.pixieSeleccionado()[1]=0;
        skill.pixieSeleccionado()[0]=0;
        SkillBook.añadirSpell(skill);
        
        //FROSBOLT: (Bolt)
        skill = new Spell(SkillBook.listaDeTiposSpell.get(TipoSpellsData.BOLT_ID));
        skill.setId(SpellsData.FROSTBOLT_ID);
        skill.setNombre(SpellsData.FROSTBOLT_Nombre);
        skill.setDescripcion(SpellsData.FROSTBOLT_Descripcion);
        skill.setIcono(SpellsData.FROSTBOLT_Icono);
        skill.pixieSeleccionado()[1]=1;
        skill.pixieSeleccionado()[0]=1;
        SkillBook.añadirSpell(skill);
        
        //TERRAFORMAR: (Editar Terreno)
        skill = new Spell(SkillBook.listaDeTiposSpell.get(TipoSpellsData.EDITARTERRENO_ID));
        skill.setId(SpellsData.TERRAFORMAR_ID);
        skill.setNombre(SpellsData.TERRAFORMAR_Nombre);
        skill.setDescripcion(SpellsData.TERRAFORMAR_Descripcion);
        skill.setIcono(SpellsData.TERRAFORMAR_Icono);
        SkillBook.añadirSpell(skill);
        
        //MUROFORMAR: (Editar Muro)
        skill = new Spell(SkillBook.listaDeTiposSpell.get(TipoSpellsData.EDITARMURO_ID));
        skill.setId(SpellsData.MUROFORMAR_ID);
        skill.setNombre(SpellsData.MUROFORMAR_Nombre);
        skill.setDescripcion(SpellsData.MUROFORMAR_Descripcion);
        skill.setIcono(SpellsData.MUROFORMAR_Icono);
        SkillBook.añadirSpell(skill);
        
        //INSTAHEAL:
        skill = new Spell(SkillBook.listaDeTiposSpell.get(TipoSpellsData.HEAL_ID));
        skill.setId(SpellsData.INSTAHEAL_ID);
        skill.setNombre(SpellsData.INSTAHEAL_Nombre);
        skill.setDescripcion(SpellsData.INSTAHEAL_Descripcion);
        skill.setIcono(SpellsData.INSTAHEAL_Icono);
        skill.añadirAura(SkillBook.listaDeAuras.get(AurasData.POISON_ID));
        skill.añadirAura(SkillBook.listaDeAuras.get(AurasData.DELAYBOMB_ID));
        SkillBook.añadirSpell(skill);
    }
    
    
}
