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
import net.seentro.prehistoriccraft.compat.jei.JEIIntegration;

import java.util.ArrayList;
import java.util.List;

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

        renderProgressArrows(guiGraphics, x, y);

        if (isMouseOverPoint(x + 46, y + 13, 92, 31, mouseX, mouseY)) {
            drawProgressToolTip(guiGraphics, mouseX, mouseY);
        }
    }

    private void renderProgressArrows(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            int halved = menu.getScaledHorizontalHalf();

            guiGraphics.blit(GUI, x + 46, y + 13, 0, 196, halved, 15);
            guiGraphics.blit(GUI, x + 92 + (46 - halved), y + 13, 46 + (46 - halved), 196, halved, 15);

            guiGraphics.blit(GUI, x + 87, y + 21, 93, 196, 10, menu.getScaledVerticalProgress());
        }
    }

    private void drawProgressToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Font font = Minecraft.getInstance().font;

        List<Component> lines = new ArrayList<>();
        lines.add(Component.translatable("gui.prehistoriccraft.progress_percent", menu.getPercent()));

        lines.add(Component.literal(""));
        lines.add(Component.translatable("gui.prehistoriccraft.open_guide"));

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

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int relX = (int) (mouseX - this.leftPos);
        int relY = (int) (mouseY - this.topPos);

        if (button == 0) {
            if (relX >= 46 && relX < 46 + 92 &&
                    relY >= 13 && relY < 13 + 31) {
                if (net.neoforged.fml.ModList.get().isLoaded("jei")) {
                    JEIIntegration.showDnaRecombinatorGuide();
                }
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
}
