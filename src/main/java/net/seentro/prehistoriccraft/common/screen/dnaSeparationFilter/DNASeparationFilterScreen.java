package net.seentro.prehistoriccraft.common.screen.dnaSeparationFilter;

import java.util.ArrayList;
import java.util.List;

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
import net.seentro.prehistoriccraft.utils.jei.JEIIntegration;

public class DNASeparationFilterScreen extends AbstractContainerScreen<DNASeparationFilterMenu> {
    private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(
            PrehistoricCraft.MODID, "textures/gui/dna_separation_filter.png"
    );

    private static final int TEX_WIDTH = 256;
    private static final int TEX_HEIGHT = 256;

    private static final int WATER_BAR_X = 68;
    private static final int WATER_BAR_Y = 10;
    private static final int WATER_BAR_H = 20;
    private static final int WATER_BAR_W = 102;

    private static final int WATER_TEX_U = 153;
    private static final int WATER_TEX_V = 236;

    private static final int PROG_BAR_X = 63;
    private static final int PROG_BAR_Y = 80;
    private static final int PROG_BAR_H = 20;
    private static final int PROG_BAR_W = 112;

    private static final int PROG_TEX_U = 1;
    private static final int PROG_TEX_V = 236;

    public DNASeparationFilterScreen(DNASeparationFilterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = TEX_WIDTH;
        this.imageHeight = TEX_HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelX = 10000;
        this.titleLabelX = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);

        int x = this.leftPos;
        int y = this.topPos;

        g.blit(GUI, x, y, 0, 0, this.imageWidth, this.imageHeight - 30);

        renderProgressBar(g, x, y);
        renderWaterBar(g, x, y);

        if (isMouseOverPoint(x + WATER_BAR_X, y + WATER_BAR_Y, WATER_BAR_W, WATER_BAR_H, mouseX, mouseY)) {
            drawWaterToolTip(g, mouseX, mouseY);
        }

        if (isMouseOverPoint(x + PROG_BAR_X, y + PROG_BAR_Y, PROG_BAR_W, PROG_BAR_H, mouseX, mouseY)) {
            drawProgressToolTip(g, mouseX, mouseY);
        }
    }

    private void renderProgressBar(GuiGraphics g, int x, int y) {
        if (menu.isCrafting()) {
            int fillW = menu.getScaledArrowProgress(PROG_BAR_W);
            if (fillW > 0) {
                g.blit(
                    GUI,
                    x + PROG_BAR_X,
                    y + PROG_BAR_Y,
                    PROG_TEX_U,
                    PROG_TEX_V,
                    fillW,
                    PROG_BAR_H
                );
            }
        }
    }

    private void renderWaterBar(GuiGraphics g, int x, int y) {
        int fillW = menu.getScaledWaterFill(WATER_BAR_W);
        if (fillW <= 0) return;
        g.blit(
            GUI,
            x + WATER_BAR_X,
            y + WATER_BAR_Y,
            WATER_TEX_U,
            WATER_TEX_V,
            fillW,
            WATER_BAR_H
        );
    }

    private void drawWaterToolTip(GuiGraphics g, int mouseX, int mouseY) {
        int fluid = menu.getWater();
        int capacity = menu.getMaxWater();
        Font font = Minecraft.getInstance().font;
        g.renderTooltip(font, Component.literal(fluid + " / " + capacity + " mB"), mouseX, mouseY);
    }

    private void drawProgressToolTip(GuiGraphics g, int mouseX, int mouseY) {
        int filled = menu.getScaledArrowProgress(PROG_BAR_W);
        int percent = PROG_BAR_W == 0 ? 0 : filled * 100 / PROG_BAR_W;

        Font font = Minecraft.getInstance().font;

        List<Component> lines = new ArrayList<>();
        lines.add(Component.translatable("gui.prehistoriccraft.dna_separation_progress", percent));

        if (percent == 0) {
            lines.add(Component.literal(""));
            lines.add(Component.translatable("gui.prehistoriccraft.dna_separation_click_me"));
        }

        g.renderComponentTooltip(font, lines, mouseX, mouseY);
    }


    private static boolean isMouseOverPoint(int x, int y, int w, int h, int mx, int my) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);
        this.renderTooltip(g, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int relX = (int) (mouseX - this.leftPos);
        int relY = (int) (mouseY - this.topPos);

        if (button == 0) {
            if (relX >= PROG_BAR_X && relX < PROG_BAR_X + PROG_BAR_W &&
                relY >= PROG_BAR_Y && relY < PROG_BAR_Y + PROG_BAR_H) {
                    System.out.println("Entro");
                try {
                    if (net.neoforged.fml.ModList.get().isLoaded("jei")) {
                        System.out.println("Entro en jei");
                        JEIIntegration.showDnaFilterGuide();
                    }
                } catch (NoClassDefFoundError e) {}
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
}
