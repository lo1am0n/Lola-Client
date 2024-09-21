package dev.lo1am0n.lolaclient.module.impl.normalmodules.visual.modmanager;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import dev.lo1am0n.lolaclient.module.LolaModule;
import dev.lo1am0n.lolaclient.module.LolaModuleType;
import dev.lo1am0n.lolaclient.module.setting.ModuleSetting;
import dev.lo1am0n.lolaclient.module.setting.impl.BooleanSetting;
import dev.lo1am0n.lolaclient.module.setting.impl.DoubleNumberSetting;
import dev.lo1am0n.lolaclient.module.setting.impl.ModeSetting;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

public class ModManagerGui extends GuiScreen {

    public int x, y;
    public int dragX, dragY;
    public int width, height;
    public boolean dragging;
    public LolaModuleType currentCategory = LolaModuleType.Combat;
    public LolaModule currentModule = null;
    public double scrollBuffer = 0;
    public boolean draggingSlider = false;
    public boolean settingKey = false;

    public ModManagerGui() {
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (dragging) {
            x = dragX + mouseX;
            y = dragY + mouseY;
        }

        scrollBuffer += Mouse.getDWheel() / 100;
        Gui.drawRect(x, y, x + width, y + 15, new Color(15, 15, 15, 150).getRGB());

        mc.fontRendererObj.drawString("Lola Client - Mod Manager (very original design)", x + 3, y + 3, -1);

        Gui.drawRect(x, y + 15, x + width, y + height, new Color(25, 25, 25, 125).getRGB());
        Gui.drawRect(x + 80, y + 15, x + 80 + 2, y + height, 0xff252525);
        Gui.drawRect(x + 80, y + 50, x + width, y + 50 + 2, 0xff252525);
        Color purple = new Color(152, 96, 255);
        int count = 0;
        for (LolaModule m : Minecraft.getMinecraft().getLolaClient().getModulesByCategory(currentCategory)) {
            if (m.isAdvantageModule() && !Minecraft.getMinecraft().getLolaClient().allowAdvantageModules) {
                mc.fontRendererObj.drawString("???", x + 3, (int) (y + 25 + (count * mc.fontRendererObj.FONT_HEIGHT * 1.5)),
                        m.isEnabled() ? purple.getRGB() : -1);
                count++;
            }
            else {
                mc.fontRendererObj.drawString(m.getName(), x + 3, (int) (y + 25 + (count * mc.fontRendererObj.FONT_HEIGHT * 1.5)),
                        m.isEnabled() ? purple.getRGB() : -1);
                count++;
            }
        }
        count = 0;
        for (LolaModuleType c : LolaModuleType.values()) {
            mc.fontRendererObj.drawString(c.name(), x + 100 + (count * mc.fontRendererObj.FONT_HEIGHT * 8), y + 25, -1);
            Gui.drawRect(x + 100 + (count * mc.fontRendererObj.FONT_HEIGHT * 8),
                    y + 25 + mc.fontRendererObj.FONT_HEIGHT,
                    x + 100 + (count * mc.fontRendererObj.FONT_HEIGHT * 8)
                            + mc.fontRendererObj.getStringWidth(c.name()),
                    y + 25 + mc.fontRendererObj.FONT_HEIGHT + 1, currentCategory == c ? -1 : 0x00000000);
            count++;
        }
        if (currentModule != null) {
            mc.fontRendererObj.drawString(currentModule.getName() + " | " + currentModule.getDescription() + ":", x + 100, y + 60, purple.getRGB());
            mc.fontRendererObj.drawString("Key: " + (settingKey ? "Listening..." : Keyboard.getKeyName(currentModule.getKeybind())), x + 100, y + 70, -1);
            count = 0;
            count += scrollBuffer;
            for (ModuleSetting s : currentModule.getModuleSettings()) {
                double offset = y + 70 + 13 + (count * mc.fontRendererObj.FONT_HEIGHT * 1.5);
                if (!((offset + mc.fontRendererObj.FONT_HEIGHT) >= y + height)) {
                    if ((offset + mc.fontRendererObj.FONT_HEIGHT < y + 70 + 10)) {
                        count++;
                        continue;
                    }
                    mc.fontRendererObj.drawString(s.getName() + ":", x + 100, (int) offset, -1);
                    if (s instanceof BooleanSetting) {
                        mc.fontRendererObj.drawString(((BooleanSetting) s).isCurrentValue() + "",
                                x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 2, (int) offset,
                                ((BooleanSetting) s).isCurrentValue() ? 0xff00ff00 : 0xffff0000);
                    }
                    if (s instanceof DoubleNumberSetting) {
                        Gui.drawRect(x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3, (int) offset,
                                x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 120, (int) (offset + 1),
                                0xff252525);
                        Gui.drawRect(x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3, (int) offset,
                                x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3 - 1,
                                (int) (offset + mc.fontRendererObj.FONT_HEIGHT), 0xff252525);
                        Gui.drawRect(x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3,
                                (int) (offset + mc.fontRendererObj.FONT_HEIGHT - 1),
                                x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 120,
                                (int) (offset + mc.fontRendererObj.FONT_HEIGHT), 0xff252525);
                        Gui.drawRect(x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 120, (int) offset,
                                x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 120 + 1,
                                (int) (offset + mc.fontRendererObj.FONT_HEIGHT), 0xff252525);
                        Gui.drawRect(x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3,
                                (int) (offset + 1),
                                (int) (x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3
                                        + 117 * (((DoubleNumberSetting) s).getCurrentValue() - ((DoubleNumberSetting) s).getMinimumValue())
                                        / (((DoubleNumberSetting) s).getMaximumValue() - ((DoubleNumberSetting) s).getMinimumValue())),
                                (int) (offset + mc.fontRendererObj.FONT_HEIGHT - 1), purple.getRGB());
                        mc.fontRendererObj.drawString(((DoubleNumberSetting) s).getCurrentValue() + "",
                                x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 120 / 2 - 10, (int) offset,
                                -1);
                        boolean hoveredSlider = mouseX > x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":")
                                && mouseY > offset + 1
                                && mouseX < x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3 + 120
                                && mouseY < offset + mc.fontRendererObj.FONT_HEIGHT;
                        if (hoveredSlider && draggingSlider) {
                            double diff = Math.min(117, Math.max(0,
                                    mouseX - (x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3)));
                            if (mouseX < (x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3)) {
                                ((DoubleNumberSetting) s).setCurrentValue(((DoubleNumberSetting) s).getMinimumValue());
                            }
                            if (diff <= 0) {
                                ((DoubleNumberSetting) s).setCurrentValue(((DoubleNumberSetting) s).getMinimumValue());
                            } else {
                                double newValue = roundToPlace(
                                        ((diff / 117)
                                                * (((DoubleNumberSetting) s).getMaximumValue() - ((DoubleNumberSetting) s).getMinimumValue())
                                                + ((DoubleNumberSetting) s).getMinimumValue()),
                                        3);
                                ((DoubleNumberSetting) s).setCurrentValue(newValue);
                            }
                        }
                    }
                    if (s instanceof ModeSetting) {
                        int strCount = 0;
                        int xOffset = 0;
                        for (String str : ((ModeSetting) s).getModes()) {
                            mc.fontRendererObj.drawString(str,
                                    x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3 + xOffset,
                                    (int) offset, ((ModeSetting) s).getCurrentMode().equals(str) ? purple.getRGB() : -1);
                            if (!(strCount == ((ModeSetting) s).getModes().size() - 1))
                                mc.fontRendererObj.drawString(",",
                                        (int) (x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":" + str) + 3
                                                                                        + xOffset + 0.5),
                                        (int) offset, -1);
                            xOffset += mc.fontRendererObj.getStringWidth(str + ", ");
                            strCount++;
                        }
                    }
                    /*
                    if (s instanceof BooleansSetting) {
                        int strCount = 0;
                        int xOffset = 0;
                        for (Setting bv : ((BooleansSetting) s).getSettingList()) {
                            mc.fontRendererObj.drawString(bv.getName(),
                                    x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3 + xOffset,
                                    offset, ((BooleanValue) bv).getBoolean() ? purple.getRGB() : -1);
                            if (!(strCount == ((BooleansSetting) s).getSettingList().length - 1))
                                mc.fontRendererObj.drawString(",",
                                        x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":" + bv.getName())
                                                + 3 + xOffset + 0.5,
                                        offset, -1);
                            xOffset += mc.fontRendererObj.getStringWidth(bv.getName() + ", ");
                            strCount++;
                        }
                    }
                     */
                    count++;
                }
            }
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        boolean dragging = mouseX > x && mouseY > y && mouseX < x + width + 30 && mouseY < y + 15;
        if (dragging) {
            this.dragging = true;
            dragX = x - mouseX;
            dragY = y - mouseY;
        }
        int count = 0;
        if(currentModule != null) {
            boolean hoveredSetKey = mouseX > x + 100 && mouseY > y + 70 && mouseX < x + 100 + (settingKey ? mc.fontRendererObj.getStringWidth("Key: ...") : mc.fontRendererObj.getStringWidth("Key: " + Keyboard.getKeyName(currentModule.getKeybind()))) && mouseY < y + 70 + mc.fontRendererObj.FONT_HEIGHT;
            if(hoveredSetKey && mouseButton == 0)
                settingKey = !settingKey;
        }
        for (LolaModule m : Minecraft.getMinecraft().getLolaClient().getModulesByCategory(currentCategory)) {
            boolean hovered = mouseX > x + 3 && mouseY > y + 25 + (count * mc.fontRendererObj.FONT_HEIGHT * 1.5)
                    && mouseX < x + 3 + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + 25
                    + (count * mc.fontRendererObj.FONT_HEIGHT * 1.5) + mc.fontRendererObj.FONT_HEIGHT;

            if (!(m.isAdvantageModule() && !Minecraft.getMinecraft().getLolaClient().allowAdvantageModules)) {
                if (hovered && mouseButton == 0) {
                    m.setEnabled(!m.isEnabled());
                    currentModule = m;
                }
                if (hovered && mouseButton == 1) {
                    currentModule = m;
                }
            }
            count++;
        }
        count = 0;
        for (LolaModuleType c : LolaModuleType.values()) {
            boolean hovered = mouseX > x + 100 + (count * mc.fontRendererObj.FONT_HEIGHT * 8) && mouseY > y + 25
                    && mouseX < x + 100 + (count * mc.fontRendererObj.FONT_HEIGHT * 8)
                    + mc.fontRendererObj.getStringWidth(c.name())
                    && mouseY < y + 25 + mc.fontRendererObj.FONT_HEIGHT;
            if (hovered && mouseButton == 0) {
                currentCategory = c;
            }
            count++;
        }
        count = 0;
        count += scrollBuffer;

        if (currentModule == null) return;

        for (ModuleSetting s : currentModule.getModuleSettings()) {
            double offset = y + 70 + 13 + (count * mc.fontRendererObj.FONT_HEIGHT * 1.5);
            if (!((offset + mc.fontRendererObj.FONT_HEIGHT) >= y + height)) {
                if ((offset + mc.fontRendererObj.FONT_HEIGHT < y + 70 + 10)) {
                    count++;
                    continue;
                }
                if (s instanceof BooleanSetting) {
                    boolean hoveredBooleanValue = mouseX > x + 100
                            + mc.fontRendererObj.getStringWidth(s.getName() + ":")
                            && mouseY > offset
                            && mouseX < x + 100
                            + mc.fontRendererObj.getStringWidth(s.getName() + ":" + ((BooleanSetting) s).isCurrentValue())
                            && mouseY < offset + mc.fontRendererObj.FONT_HEIGHT;
                    if (hoveredBooleanValue && mouseButton == 0)
                        ((BooleanSetting) s).setCurrentValue(!((BooleanSetting) s).isCurrentValue());
                }
                if (s instanceof DoubleNumberSetting) {
                    boolean hoveredSlider = mouseX > x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3
                            && mouseY > offset + 1
                            && mouseX < x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3 + 120
                            && mouseY < offset + mc.fontRendererObj.FONT_HEIGHT;
                    if (hoveredSlider && mouseButton == 0)
                        draggingSlider = true;
                }
                if (s instanceof ModeSetting) {
                    int strCount = 0;
                    int xOffset = 0;
                    for (String str : ((ModeSetting) s).getModes()) {
                        boolean hoveredOption = mouseX > x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":")
                                + 3 + xOffset
                                && mouseY > offset
                                && mouseX < x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3 + xOffset
                                + mc.fontRendererObj.getStringWidth(s.getName() + ":" + str)
                                && mouseY < offset + mc.fontRendererObj.FONT_HEIGHT;
                        if (hoveredOption && mouseButton == 0)
                            ((ModeSetting) s).setCurrentMode(str);
                        xOffset += mc.fontRendererObj.getStringWidth(str + ", ");
                        strCount++;
                    }
                }
                /*
                if (s instanceof BooleansSetting) {
                    int strCount = 0;
                    int xOffset = 0;
                    for (Setting bv : ((BooleansSetting) s).getSettingList()) {
                        boolean hoveredOption = mouseX > x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":")
                                + 3 + xOffset
                                && mouseY > offset
                                && mouseX < x + 100 + mc.fontRendererObj.getStringWidth(s.getName() + ":") + 3 + xOffset
                                + mc.fontRendererObj.getStringWidth(bv.getName())
                                && mouseY < offset + mc.fontRendererObj.FONT_HEIGHT;
                        if (hoveredOption && mouseButton == 0)
                            ((BooleanValue) bv).setBoolean(!((BooleanValue) bv).getBoolean());
                        xOffset += mc.fontRendererObj.getStringWidth(bv.getName() + ", ");
                        strCount++;
                    }
                }
                 */
                count++;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        draggingSlider = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    public void initGui() {
        x = 30;
        y = 30;
        width = 700;
        height = 400;
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(settingKey && currentModule != null) {
            currentModule.setKeybind(keyCode);
            settingKey = false;
        }
        super.keyTyped(typedChar, keyCode);
    }

    private double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
