package me.margiux.miniutils.module;

import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.TickEvent;

public class AnticheatTrigger2 extends Module {
    int tick;
    public AnticheatTrigger2(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
    }

    @ModuleEventHandler
    public void onTick(TickEvent event) {
        if (++tick == 21) tick = 0;
        if (getClient().player != null) getClient().player.setSprinting(true);
        getClient().options.sprintKey.setPressed(true);
        getClient().options.sneakKey.setPressed(true);
        getClient().options.forwardKey.setPressed(true);
        getClient().options.rightKey.setPressed(tick > 5 && tick < 10);
        getClient().options.leftKey.setPressed(tick > 15 && tick < 20);
        if (getClient().player != null) getClient().player.jump();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        tick = 0;
        if (getClient().player != null) getClient().player.setSprinting(false);
        getClient().options.sprintKey.setPressed(false);
        getClient().options.sneakKey.setPressed(false);
        getClient().options.forwardKey.setPressed(false);
        getClient().options.rightKey.setPressed(false);
        getClient().options.leftKey.setPressed(false);
    }
}
