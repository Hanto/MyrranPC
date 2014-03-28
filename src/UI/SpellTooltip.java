package UI;// Created by Hanto on 27/03/2014.

import Graficos.Texto;
import Resources.Recursos;
import Skill.Aura.Aura;
import Skill.SkillBook;
import Skill.Spell.Spell;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class SpellTooltip
{
    private static final int ALTOLINEA_Stat = 11;
    private static final int ALTOLINEA_Nombre = 14;

    public static Group tooltip (Spell spell)
    {
        Group tooltip = new Group();
        Image fondo = new Image(Recursos.casillero);

        int altoTooltip = altoTooltip(spell);
        fondo.setBounds(0,0,200,altoTooltip);
        fondo.setColor(0, 0, 0, 0.3f);
        tooltip.addActor(fondo);

        Image icono = new Image(spell.getIcono());
        icono.setPosition(-icono.getWidth(), altoTooltip-icono.getHeight()+5);
        tooltip.addActor(icono);

        Texto nombreSpell = new Texto(spell.getNombre(), Recursos.font14, Color.ORANGE, Color.BLACK,
                0, 0, Align.left, Align.center, 1);
        nombreSpell.setPosition(5, altoTooltip);
        tooltip.addActor(nombreSpell);

        int linea = altoTooltip;
        Texto nombreSkillStat;
        Texto nombreAura;
        Texto valorStat;

        linea = linea - 3;

        for (int i=0; i<spell.skillStats().length;i++)
        {
            linea = linea- ALTOLINEA_Stat;

            nombreSkillStat = new Texto(spell.skillStats()[i].nombre, Recursos.font14, Color.WHITE, Color.BLACK,
                                        0, 0, Align.left, Align.center, 1);
            valorStat = new Texto(Float.toString(spell.skillStats()[i].valorBase), Recursos.font14, Color.ORANGE, Color.BLACK,
                                 0, 0, Align.right, Align.center, 1);

            nombreSkillStat.setPosition(7, linea);
            valorStat.setPosition(120, linea);
            tooltip.addActor(nombreSkillStat);
            tooltip.addActor(valorStat);

        }
        for (int i=0; i<spell.getListaDeAurasQueAplica().size;i++)
        {
            Aura aura = SkillBook.listaDeAuras.get(spell.getListaDeAurasQueAplica().get(i));

            linea = linea- ALTOLINEA_Nombre;
            nombreAura = new Texto(aura.getNombre()+":", Recursos.font14, Color.GREEN, Color.BLACK,
                                   0, 0, Align.left, Align.center, 1);
            nombreAura.setPosition(7, linea);
            tooltip.addActor(nombreAura);

            for (int j=0; j<aura.skillStats().length;j++)
            {
                linea = linea- ALTOLINEA_Stat;

                nombreSkillStat = new Texto(aura.skillStats()[j].nombre, Recursos.font14, Color.WHITE, Color.BLACK,
                        0, 0, Align.left, Align.center, 1);
                valorStat = new Texto(Float.toString(aura.skillStats()[i].valorBase), Recursos.font14, Color.ORANGE, Color.BLACK,
                        0, 0, Align.right, Align.center, 1);

                nombreSkillStat.setPosition(7, linea);
                valorStat.setPosition(120, linea);
                tooltip.addActor(nombreSkillStat);
                tooltip.addActor(valorStat);
            }
        }
        return tooltip;
    }

    public static int altoTooltip (Spell spell)
    {   //Es lo que ocupa el nombre del Skill
        int alto = ALTOLINEA_Nombre;
        //Dejamos 11 pixeles por Stat
        alto = alto + spell.skillStats().length* ALTOLINEA_Stat;

        for (int i=0;i<spell.getListaDeAurasQueAplica().size;i++)
        {
            Aura aura = SkillBook.listaDeAuras.get(spell.getListaDeAurasQueAplica().get(i));
            //Dejamos 11 pixeles por Stat y 14 para el nombre del Aura
            alto = alto + aura.skillStats().length* ALTOLINEA_Stat +14;
        }
        return alto;
    }
}
