package net.seentro.prehistoriccraft.common.screen.fossilAnalysisTable;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.seentro.prehistoriccraft.PrehistoricCraft;

import java.util.ArrayList;
import java.util.List;

public class FossilAnalysisTableScreen extends AbstractContainerScreen<FossilAnalysisTableMenu> {
    private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/gui/fossil_analysis_table_gui.png");

    public FossilAnalysisTableScreen(FossilAnalysisTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelX = 10000;
        this.titleLabelX = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI, x, y, 0, 0, 182, 190);

        renderProgressArrow(guiGraphics, x, y);

        if (isMouseOverPoint(x + 81, y + 44, 18, 7, mouseX, mouseY)) {
            drawProgressToolTip(guiGraphics, mouseX, mouseY);
        }
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(GUI, x + 82, y + 44, 182, 0, menu.getScaledArrowProgress(), 7);
        }
    }

    private void drawProgressToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Font font = Minecraft.getInstance().font;

        List<Component> lines = new ArrayList<>();
        lines.add(Component.translatable("gui.prehistoriccraft.progress_percent", menu.getPercent()));

        guiGraphics.renderComponentTooltip(font, lines, mouseX, mouseY);
    }

    private static boolean isMouseOverPoint(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width
                && mouseY >= y && mouseY <= y + height;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
