package Skill;
// @author Ivan Delgado Huerta

import Actores.Mobs.Personaje;
import Skill.Aura.BDebuff;
import Skill.Spell.Spell;

public class SkillInterface 
{
    public interface TipoSkillInterface
    {
        public void inicializarSkillStats ();
        public void inicializarSkillPixies ();
        public void ejecutarCasteo (Spell spell, Personaje caster, float targetX, float targetY);
    }
    
    public interface TipoAuraInterface
    {
        public void inicializarSkillStats ();
        public void inicializarSkillPixies ();
        public void actualizarTick (BDebuff debuff);
    }
}
