package dev.lo1am0n.lolaclient;

import dev.lo1am0n.lolaclient.module.LolaModule;
import dev.lo1am0n.lolaclient.module.LolaModuleType;
import dev.lo1am0n.lolaclient.module.impl.visual.ModManager;
import dev.lo1am0n.lolaclient.tag.LolaChatTag;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class LolaClient {
    public String versionName = "v1.0.0";
    public String developerNames = "lo1am0n";

    private CopyOnWriteArrayList<String> preventOnePointSevenHitRegUsage = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<String> preventFreeLookUsage = new CopyOnWriteArrayList<>();

    private Map<String, LolaChatTag> playerTags = new HashMap<>();

    private CopyOnWriteArrayList<LolaModule> lolaModules = new CopyOnWriteArrayList<>();

    public LolaClient() {
        // Servers that don't allow 1.7 hit reg
        preventOnePointSevenHitRegUsage.add("hypixel.net");

        // Servers that don't allow Free Look
        preventFreeLookUsage.add("hypixel.net");

        // Chat Tags (for contributors and other very important people)
        playerTags.put("Lo1am0n", LolaChatTag.OWNER);

        // Actual Features
        lolaModules.add(new ModManager());
    }

    public CopyOnWriteArrayList<LolaModule> getLolaModules() {
        return lolaModules;
    }

    public CopyOnWriteArrayList<String> getPreventFreeLookUsage() {
        return preventFreeLookUsage;
    }

    public CopyOnWriteArrayList<String> getPreventOnePointSevenHitRegUsage() {
        return preventOnePointSevenHitRegUsage;
    }

    public CopyOnWriteArrayList<LolaModule> getModulesByCategory(LolaModuleType category) {
        CopyOnWriteArrayList<LolaModule> theModules = new CopyOnWriteArrayList<>();

        for (LolaModule module : getLolaModules()) {
            if (module.getCategory() == category) {
                theModules.add(module);
            }
        }

        return theModules;
    }
}
