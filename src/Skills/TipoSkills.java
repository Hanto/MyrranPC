package Skills;
// @author Ivan Delgado Huerta

import Mobiles.Personaje;
import Skills.Aura.BDebuff;

public class TipoSkills 
{
    public interface TipoSpell
    {
        public void ejecutarCasteo (Spell spell, Personaje caster, float targetX, float targetY);
        public SpellStat [] getSpellStats ();
        public SpellStat.SpellPixie [] getSpellPixies();
    }
    
    public interface TipoAura
    {
        public void aplicarAura (Aura aura, Personaje caster, Personaje target);
        public void actualizarAura (BDebuff bdebuff);
    }
}
