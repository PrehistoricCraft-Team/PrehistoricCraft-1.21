package net.seentro.prehistoriccraft.common.screen.acidCleaningChamber;

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

public class AcidCleaningChamberScreen extends AbstractContainerScreen<AcidCleaningChamberMenu> {
    private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "textures/gui/acid_cleaning_chamber_gui.png");

    public AcidCleaningChamberScreen(AcidCleaningChamberMenu menu, Inventory playerInventory, Component title) {
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

        guiGraphics.blit(GUI, x, y, 0, 0, 184, 175);

        renderProgressArrow(guiGraphics, x, y);
        renderAcidBar(guiGraphics, x, y);

        if (isMouseOverPoint(x + 164, y + 17, 9, 51, mouseX, mouseY)) {
            drawToolTip(guiGraphics, mouseX, mouseY);
        }

        if (isMouseOverPoint(x + 59, y + 61, 36, 8, mouseX, mouseY)) {
            drawProgressToolTip(guiGraphics, mouseX, mouseY);
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

    private void drawToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int fluidAmount = menu.getAcid();
        int fluidCapacity = menu.getMaxAcid();

        Font font = Minecraft.getInstance().font;
        guiGraphics.renderTooltip(font, Component.literal("%s / %s mB".formatted(fluidAmount, fluidCapacity)), mouseX, mouseY);
    }

    private static boolean isMouseOverPoint(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width
                && mouseY >= y && mouseY <= y + height;
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(GUI, x + 61, y + 63, 185, 0, menu.getScaledArrowProgress(), 4);
        }
    }

    private void renderAcidBar(GuiGraphics guiGraphics, int x, int y) {
        int offset = 57 - menu.getScaledAcidFill();
        guiGraphics.blit(GUI, x + 162, y + 14 + offset, 185, 5 + offset, 13, menu.getScaledAcidFill());
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
            if (relX >= 59 && relX < 59 + 36 &&
                    relY >= 61 && relY < 61 + 8) {
                if (net.neoforged.fml.ModList.get().isLoaded("jei")) {
                    JEIIntegration.showAcidCleaningChamberGuide();
                }
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
}
