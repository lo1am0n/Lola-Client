package dev.lo1am0n.lolaclient.module.impl.combat;

import dev.lo1am0n.lolaclient.event.impl.HitRegistrationEvent;
import dev.lo1am0n.lolaclient.module.LolaModule;
import dev.lo1am0n.lolaclient.module.LolaModuleType;
import net.minecraft.entity.passive.EntityBat;
import org.lwjgl.input.Keyboard;

public class HitRegFix extends LolaModule {

    public EntityBat iHateBoundingBoxes = null;

    public HitRegFix() {
        super("1.7 Hit Registration", "Removes the 1.8 attack delay (bannable?)", LolaModuleType.Combat, Keyboard.KEY_NONE, true);
    }

    @Override
    public void onHitReg(HitRegistrationEvent e) {
        // TODO: Add blacklist
        e.setCancelled(true);
    }
}
