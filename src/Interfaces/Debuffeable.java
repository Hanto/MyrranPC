package Interfaces;

import Skill.Aura.BDebuff;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Hanto on 26/03/2014.
 */
public interface Debuffeable
{
    public Array<BDebuff> getListaDeAuras ();
    public Group getDebuffIcons ();
    public Group getBuffIcons ();
}
