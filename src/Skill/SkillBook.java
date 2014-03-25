package Skill;
// @author Ivan Delgado Huerta

import Skill.Aura.Aura;
import Skill.Aura.TipoAura;
import Skill.Spell.TipoSpell;
import Skill.Spell.Spell;
import java.util.HashMap;

public class SkillBook 
{
    public static HashMap<String, TipoSpell>listaDeTiposSpell = new HashMap<>();
    public static HashMap<String, Spell>listaDeSpells = new HashMap<>();
    
    public static HashMap<String, TipoAura>listaDeTiposAura = new HashMap<>();
    public static HashMap<String, Aura>listaDeAuras = new HashMap<>();
    
    public static void añadirTipoSpell (TipoSpell tipoSkill)    { listaDeTiposSpell.put(tipoSkill.getID(), tipoSkill); }
    public static void añadirSpell (Spell skill)                { listaDeSpells.put(skill.getId(), skill); }
    public static void añadirTipoAura (TipoAura tipoAura)       { listaDeTiposAura.put(tipoAura.getID(), tipoAura); }
    public static void añadirAura (Aura aura)                   { listaDeAuras.put(aura.getID(), aura); }
}
