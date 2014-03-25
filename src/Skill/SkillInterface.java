package Skill;
// @author Ivan Delgado Huerta

import Mobiles.Mobs.Personaje;
import Skill.Aura.BDebuff;
import Skill.Skill.Skill;

public class SkillInterface 
{
    public interface TipoSkillInterface
    {
        public void inicializarSkillStats ();
        public void inicializarSkillPixies ();
        public void ejecutarCasteo (Skill skill, Personaje caster, float targetX, float targetY);
    }
    
    public interface TipoAuraInterface
    {
        public void inicializarSkillStats ();
        public void inicializarSkillPixies ();
        public void actualizarTick (BDebuff debuff);
    }
}
