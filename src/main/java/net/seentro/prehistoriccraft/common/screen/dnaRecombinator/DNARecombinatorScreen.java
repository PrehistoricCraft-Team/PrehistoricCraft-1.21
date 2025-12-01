package net.seentro.prehistoriccraft.common.screen.dnaRecombinator;

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

public class DNARecombinatorScreen extends AbstractContainerScreen<DNARecombinatorMenu> {
    private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/gui/dna_recombinator_gui.png");

    public DNARecombinatorScreen(DNARecombinatorMenu menu, Inventory playerInventory, Component title) {
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

        guiGraphics.blit(GUI, x, y, 0, 0, 183, 194);

        if (isMouseOverPoint(x + 164, y + 17, 9, 51, mouseX, mouseY)) {
            drawToolTip(guiGraphics, mouseX, mouseY);
        }
    }

    private void drawToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
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
