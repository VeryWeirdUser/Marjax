package me.margiux.miniutils.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import me.margiux.miniutils.module.Category;
import me.margiux.miniutils.utils.RenderUtils;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class HackListWindow extends Window {
    public final Category category;
    public boolean expanded = true;

    public HackListWindow(int x, int y, int width, int height, Category category, List<HackWindow> windows) {
        super(x, y, width, height);
        this.category = category;
        this.children.addAll(windows);
        for (Widget widget : children) {
            widget.setParent(this);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (!this.visible) return;
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderUtils.fill(matrices, this.x, this.y, this.x + this.width, this.y + 15, 0x99001B4D);

        int j = this.active ? 0xFFFFFF : 0xA0A0A0;
        RenderUtils.drawCenteredText(matrices, category.name, this.x + this.width / 2, this.y + (15) / 2, j | MathHelper.ceil(this.alpha * 255.0f) << 24);

        if (expanded) {
            int y = this.y + 15;
            for (Widget window : children) {
                window.x = this.x;
                window.y = y;
                y += window.getHeight() + 1;
            }
        }
        this.setHeight(calculateHeight());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderUtils.fill(matrices, this.x, this.y, this.x + this.width, this.y + this.height, 0x66BED5F7);
        RenderUtils.drawCenteredText(matrices, this.name, this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0f) << 24);

        if (expanded) {
            for (Widget window : children) {
                window.render(matrices, mouseX, mouseY, delta);
            }
        }
    }

    @Override
    public int calculateHeight() {
        int height = 15;
        if (expanded) {
            for (Widget window : children) {
                height += window.getHeight() + (children.indexOf(window) == children.size() - 1 ? 0 : 1);
            }
        }
        return height;
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        if (button == 1 && mouseY < this.y + 15) {
            expanded = !expanded;
        }
    }

    @Override
    public void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        x = (int)Math.round(draggedX);
        y = (int)Math.round(draggedY);
    }
}
