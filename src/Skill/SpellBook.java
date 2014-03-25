package Skill;
// @author Ivan Delgado Huerta

import Skill.Aura.Aura;
import Skill.Aura.TipoAura;
import Skill.Skill.TipoSkill;
import Skill.Skill.Skill;
import java.util.HashMap;

public class SpellBook 
{
    public static HashMap<String, TipoSkill>listaDeTiposSkill = new HashMap<>();
    public static HashMap<String, Skill>listaDeSkills = new HashMap<>();
    
    public static HashMap<String, TipoAura>listaDeTiposAura = new HashMap<>();
    public static HashMap<String, Aura>listaDeAuras = new HashMap<>();
    
    public static void añadirTipoSkill (TipoSkill tipoSkill)    { listaDeTiposSkill.put(tipoSkill.getID(), tipoSkill); }
    public static void añadirSkill (Skill skill)                { listaDeSkills.put(skill.getId(), skill); }
    public static void añadirTipoAura (TipoAura tipoAura)       { listaDeTiposAura.put(tipoAura.getID(), tipoAura); }
    public static void añadirAura (Aura aura)                   { listaDeAuras.put(aura.getID(), aura); }
}
