package Skill.Spell;// Created by Hanto on 26/03/2014.

import Interfaces.Caster;

public interface TipoSpellInterface
{
    public void inicializarSkillStats ();
    public void inicializarSkillPixies ();
    public void ejecutarCasteo (Spell spell, Caster caster, float targetX, float targetY);
}
