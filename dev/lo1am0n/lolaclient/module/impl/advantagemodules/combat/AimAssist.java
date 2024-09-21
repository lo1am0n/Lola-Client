package dev.lo1am0n.lolaclient.module.impl.advantagemodules.combat;

import dev.lo1am0n.lolaclient.event.impl.Render3DEvent;
import dev.lo1am0n.lolaclient.module.LolaModule;
import dev.lo1am0n.lolaclient.module.LolaModuleType;
import dev.lo1am0n.lolaclient.module.setting.impl.BooleanSetting;
import dev.lo1am0n.lolaclient.module.setting.impl.DoubleNumberSetting;
import dev.lo1am0n.lolaclient.module.setting.impl.ModeSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Mouse;

import java.util.concurrent.CopyOnWriteArrayList;

public class AimAssist extends LolaModule {

    /*
    These modules are hidden for a reason - Use at your OWN risk!

    Advantage modules are made for servers that allow an unfair advantage, such as TEST SERVERS and such.
    Please, for the love of god, use these on any type of server that doesn't allow it. Advantage modules
    give you a huge advantage over other players. Make sure that features like these are allowed before
    giving yourself access to these and/or using them.

    Thanks,
    Chloe (Lo1am0n).
     */

    public AimAssist() {
        super("Aim Assist", "Assists you with your aim (BANNABLE!)", LolaModuleType.Combat);

        this.setAdvantageModule(true);

        CopyOnWriteArrayList<String> modes = new CopyOnWriteArrayList<>();
        modes.add("Normal");
        modes.add("Multiplier");

        this.getModuleSettings().add(new ModeSetting("Mode", "", modes, "Multiplier"));
        this.getModuleSettings().add(new DoubleNumberSetting("Minimum Multiplier Not-Over", "", 1.0, 1.0, 5.0, 0.01));
        this.getModuleSettings().add(new DoubleNumberSetting("Maximum Multiplier Not-Over", "", 1.00, 1.0, 5.0, 0.01));

        this.getModuleSettings().add(new DoubleNumberSetting("Minimum Multiplier Over", "", 0.5, 0.1, 1.0, 0.01));
        this.getModuleSettings().add(new DoubleNumberSetting("Maximum Multiplier Over", "", 0.75, 0.1, 1.0, 0.01));
    }

    private float sensitivity = 0.0f;

    @Override
    public void onEnable() {
        sensitivity = Minecraft.getMinecraft().gameSettings.mouseSensitivity;
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.mouseSensitivity = sensitivity;
    }

    @Override
    public void onRender(Render3DEvent e) {
        DoubleNumberSetting minMultNotOver = (DoubleNumberSetting) this.getModuleSettingByName("Minimum Multiplier Not-Over");
        DoubleNumberSetting maxMultNotOver = (DoubleNumberSetting) this.getModuleSettingByName("Maximum Multiplier Not-Over");

        DoubleNumberSetting minMultOver = (DoubleNumberSetting) this.getModuleSettingByName("Minimum Multiplier Over");
        DoubleNumberSetting maxMultOver = (DoubleNumberSetting) this.getModuleSettingByName("Maximum Multiplier Over");

        if (Mouse.isButtonDown(0)) {
            MovingObjectPosition position = Minecraft.getMinecraft().objectMouseOver;

            if (position != null && position.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                Minecraft.getMinecraft().gameSettings.mouseSensitivity = (sensitivity * RandomUtils.nextFloat((float)minMultOver.getCurrentValue(), (float)maxMultOver.getCurrentValue()));
                System.out.println("Over: " +  Minecraft.getMinecraft().gameSettings.mouseSensitivity);
            }
            else {
                Minecraft.getMinecraft().gameSettings.mouseSensitivity = (sensitivity * RandomUtils.nextFloat((float)minMultNotOver.getCurrentValue(), (float)maxMultNotOver.getCurrentValue()));
            }
        }
    }
}
