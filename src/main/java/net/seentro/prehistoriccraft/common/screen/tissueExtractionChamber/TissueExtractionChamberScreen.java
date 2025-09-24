package net.seentro.prehistoriccraft.common.screen.tissueExtractionChamber;

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

public class TissueExtractionChamberScreen extends AbstractContainerScreen<TissueExtractionChamberMenu> {
    private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/gui/tissue_extraction_chamber_gui.png");

    public TissueExtractionChamberScreen(TissueExtractionChamberMenu menu, Inventory playerInventory, Component title) {
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

        guiGraphics.blit(GUI, x, y, 0, 0, 184, 195);

        renderProgressArrow(guiGraphics, x, y);
        renderBliceBar(guiGraphics, x, y);
        if (isMouseOverPoint(x + 106, y + 10, 66, 9, mouseX, mouseY)) {
            drawToolTip(guiGraphics, mouseX, mouseY);
        }
    }

    private void drawToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int fluidAmount = menu.getBlice();
        int fluidCapacity = menu.getMaxBlice();

        Font font = Minecraft.getInstance().font;
        guiGraphics.renderTooltip(font, Component.literal("%s / %s mB".formatted(fluidAmount, fluidCapacity)), mouseX, mouseY);
    }

    private static boolean isMouseOverPoint(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width
                && mouseY >= y && mouseY <= y + height;
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(GUI, x + 55, y + 43, 183, 195, 72, menu.getScaledArrowProgress());
        }
    }

    private void renderBliceBar(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(GUI, x + 106, y + 10, 183, 211, menu.getScaledBliceFill(), 9);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
