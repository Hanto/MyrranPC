package Constantes;
import Constantes.Skills.AurasData;
import Constantes.Skills.TipoSkillsData;
import Constantes.Skills.TipoAurasData;
import Constantes.Skills.SkillsData;
import Skill.Aura.Aura;
import Skill.Aura.TipoAura;
import Skill.Aura.TiposAura.Dot;
import Skill.SpellBook;
import Skill.Skill.Skill;
import Skill.Skill.TipoSkill;
import Skill.Skill.TiposSkill.EditarMuro;
import Skill.Skill.TiposSkill.EditarTerreno;
import Skill.Skill.TiposSkill.Proyectil;
//* @author Ivan Delgado Huerta

public class LoadData 
{
    public static void cargarListaDeTiposSpell ()
    {   //BOLT:
        TipoSkill proyectil = new Proyectil(TipoSkillsData.BOLT_ID);
        SpellBook.añadirTipoSkill(proyectil);
        //EDITAR TERRENO:
        TipoSkill editar = new EditarTerreno(TipoSkillsData.EDITARTERRENO_ID);
        SpellBook.añadirTipoSkill(editar);
        //EDITAR MURO:
        TipoSkill muro = new EditarMuro(TipoSkillsData.EDITARMURO_ID);
        SpellBook.añadirTipoSkill(muro);
    }
    
    public static void cargarListaDeSpells ()
    {   //FIREBOLT: (Bolt)
        Skill skill = new Skill(SpellBook.listaDeTiposSkill.get(TipoSkillsData.BOLT_ID));
        skill.setId(SkillsData.FIREBOLT_ID);
        skill.setNombre(SkillsData.FIREBOLT_Nombre);
        skill.setDescripcion(SkillsData.FIREBOLT_Descripcion);
        skill.setIcono(SkillsData.FIREBOLT_Icono);
        skill.pixieSeleccionado()[1]=0;
        skill.pixieSeleccionado()[0]=0;
        SpellBook.añadirSkill(skill);
        
        //FROSBOLT: (Bolt)
        skill = new Skill(SpellBook.listaDeTiposSkill.get(TipoSkillsData.BOLT_ID));
        skill.setId(SkillsData.FROSTBOLT_ID);
        skill.setNombre(SkillsData.FROSTBOLT_Nombre);
        skill.setDescripcion(SkillsData.FROSTBOLT_Descripcion);
        skill.setIcono(SkillsData.FROSTBOLT_Icono);
        skill.pixieSeleccionado()[1]=1;
        skill.pixieSeleccionado()[0]=1;
        SpellBook.añadirSkill(skill);
        
        //TERRAFORMAR: (Editar Terreno)
        skill = new Skill(SpellBook.listaDeTiposSkill.get(TipoSkillsData.EDITARMURO_ID));
        skill.setId(SkillsData.TERRAFORMAR_ID);
        skill.setNombre(SkillsData.TERRAFORMAR_Nombre);
        skill.setDescripcion(SkillsData.TERRAFORMAR_Descripcion);
        skill.setIcono(SkillsData.TERRAFORMAR_Icono);
        SpellBook.añadirSkill(skill);
        
        //MUROFORMAR: (Editar Muro)
        skill = new Skill(SpellBook.listaDeTiposSkill.get(TipoSkillsData.EDITARMURO_ID));
        skill.setId(SkillsData.MUROFORMAR_ID);
        skill.setNombre(SkillsData.MUROFORMAR_Nombre);
        skill.setDescripcion(SkillsData.MUROFORMAR_Descripcion);
        skill.setIcono(SkillsData.MUROFORMAR_Icono);
        SpellBook.añadirSkill(skill);
    }
    
    public static void cargarListaDeTiposAura ()
    {
        //DOT:
        TipoAura tipoAura = new Dot(TipoAurasData.DOT_ID);
        SpellBook.añadirTipoAura(tipoAura);
    }
    
    public static void cargarListaDeAuras ()
    {
        //POISON DOT:
        Aura aura = new Aura (SpellBook.listaDeTiposAura.get(TipoAurasData.DOT_ID));
        aura.setId(AurasData.POISON_ID);
        aura.setNombre(AurasData.POISON_Nombre);
        aura.setDescripcion(AurasData.Poison_Descripcion);
        SpellBook.añadirAura(aura);
    }
}
