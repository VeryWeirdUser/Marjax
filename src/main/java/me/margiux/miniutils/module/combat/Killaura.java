package me.margiux.miniutils.module.combat;

import me.margiux.miniutils.event.ModuleEventHandler;
import me.margiux.miniutils.event.UpdateEvent;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.module.Module;
import me.margiux.miniutils.module.ModuleManager;
import me.margiux.miniutils.utils.PlayerUtils;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.player.PlayerEntity;

public class Killaura extends Module {
    public Killaura(String name, String description, Category category, int activationKey) {
        super(name, description, category, activationKey);
        addSetting(ModuleManager.triggerBot.safeMode);
    }

    @Override
    protected void onEnable() {
        ModuleManager.triggerBot.enable();
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        ModuleManager.triggerBot.disable();
        super.onDisable();
    }

    @ModuleEventHandler
    public void onUpdate(UpdateEvent e) {
        aim();
    }


    public void aim() {
        if (!isEnabled()) return;
        if (getClient().currentScreen != null) return;
        if (getClient().player == null) return;

        PlayerEntity target = PlayerUtils.getClosestPlayer(getClient().player, 7);
        if (target == null) return;

        double posX = target.getX() - getClient().player.getX();
        double posZ = target.getZ() - getClient().player.getZ();

        float newYaw = (float) Math.toDegrees(Math.atan2(posZ, posX)) - 90F;
        if (newYaw < 0) newYaw = -newYaw;
        float playerYaw = (getClient().player.getYaw() < 0) ? -getClient().player.getYaw() : getClient().player.getYaw();


        if (newYaw - playerYaw > -20 && newYaw - playerYaw < 20) {
            getClient().player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getBoundingBox().getCenter());
        }
    }
}
