package Interfaces;

import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Created by Hanto on 26/03/2014.
 */
public interface Caster
{
    public void setCastingTime(float totalCastingTime);
    public boolean isCasteando();

    public Group getActor();
    public int getCapaTerrenoSeleccionada();
}
