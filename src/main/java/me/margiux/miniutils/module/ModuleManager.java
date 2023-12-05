package me.margiux.miniutils.module;

import me.margiux.miniutils.Mode;
import me.margiux.miniutils.event.EventManager;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final List<Module> modules = new ArrayList<>();
    public static final Truesight truesight = new Truesight("Truesight", "Makes invisible players visible", Category.VISUAL, GLFW.GLFW_KEY_I);
    public static final ChorusFarmer chorusFarmer = new ChorusFarmer("ChorusFarmer", "Farms choruses automatically", Category.MISC, GLFW.GLFW_KEY_G);
    public static final ElytraHunter elytraHunter = new ElytraHunter("ElytraHunter", "Informs if invisible players with an elytra are found and shoots 'em down if required ) xD", Category.COMBAT, GLFW.GLFW_KEY_E);
    public static final AutoSell auctionSeller = new AutoSell("AutoSell", "Sells items to auction", Category.MISC, GLFW.GLFW_KEY_A);
    public static final ChestStealer chestStealer = new ChestStealer("ChestSteal", "Steals items from a container", Category.MISC, GLFW.GLFW_KEY_C);
    public static final TriggerBot triggerBot = new TriggerBot("TriggerBot", "Immediately attacks entities you are looking at", Category.COMBAT, GLFW.GLFW_KEY_T);
    public static final PlayerSearcher playerSearcher = new PlayerSearcher("PlayerSearcher", "Searching for a defined player", Category.MISC, GLFW.GLFW_KEY_T);
    public static final AnticheatTrigger anticheatTrigger = new AnticheatTrigger("AnticheatTrigger", "Anticheat trigger", Category.MISC, GLFW.GLFW_KEY_KP_1);
    public static final AnticheatTrigger2 anticheatTrigger2 = new AnticheatTrigger2("AnticheatTrigger2", "Anticheat trigger", Category.MISC, GLFW.GLFW_KEY_KP_2);

    static {
        modules.add(truesight);
        modules.add(chorusFarmer);
        modules.add(elytraHunter);
        modules.add(auctionSeller);
        modules.add(chestStealer);
        modules.add(triggerBot);
        modules.add(playerSearcher);
        modules.add(anticheatTrigger);
        modules.add(anticheatTrigger2);
        for (Module mod : modules) {
            EventManager.addListener(mod);
            EventManager.addModuleListener(mod, mod);
        }
    }

    public static void disable() {
        for (Module module : modules) {
            if (module.mode.getValue() == Mode.ENABLED) {
                module.changeMode(Mode.FORCE_DISABLED);
                module.disabledByMain = true;
            } else module.changeMode(Mode.FORCE_DISABLED);
        }
    }

    public static void enableDisabled() {
        for (Module module : modules) {
            if (module.mode.getValue() == Mode.FORCE_DISABLED) {
                module.changeMode(module.disabledByMain ? Mode.ENABLED : Mode.DISABLED);
                module.disabledByMain = false;
            }
        }
    }
}
