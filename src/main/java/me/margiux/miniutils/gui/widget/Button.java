package me.margiux.miniutils.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import me.margiux.miniutils.Main;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class Button extends Widget {
    public final PressAction onPress;

    public Button(int x, int y, int width, int height, String name, String description, PressAction onPress) {
        super(x, y, width, height, name, description);
        this.onPress = onPress;
    }

    public Button(int width, int height, String name, String description, PressAction onPress) {
        this(0, 0, width, height, name, description, onPress);
    }

    @SuppressWarnings("unused")
    public Button(String name, String description, PressAction onPress) {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, name, description, onPress);
    }

    public void renderBackground(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        DrawableHelper.fill(matrices, this.x, this.y, this.x + this.width, this.y + this.height, 0x55092D49);
        DrawableHelper.fill(matrices, this.x + 2, this.y + 2, this.x + this.width - 2, this.y + this.height - 2, 0x550887E7);
    }

    @Override
    public void renderText(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        TextRenderer textRenderer = Main.instance.getClient().textRenderer;
        int j = this.active ? 0xFFFFFF : 0xA0A0A0;
        ClickableWidget.drawCenteredText(matrices, textRenderer, displayName, this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0f) << 24);
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        if (onPress != null) onPress.onPress(this, mouseX, mouseY, button);
    }

    @FunctionalInterface
    public interface PressAction {
        void onPress(Button button, double mouseX, double mouseY, int mouseButton);
    }
}
