package net.seentro.prehistoriccraft.datagen;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import org.checkerframework.checker.units.qual.C;

import java.util.Set;

public class PrehistoricBlockStateProvider extends BlockStateProvider {
    public PrehistoricBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PrehistoricCraft.MODID, exFileHelper);
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    @Override
    protected void registerStatesAndModels() {
        /* NATURE */

        blockWithItem(PrehistoricBlocks.CRACKED_DIRT);
        blockWithItem(PrehistoricBlocks.LOAM);
        blockWithItem(PrehistoricBlocks.LOAMY_SILT);
        blockWithItem(PrehistoricBlocks.SILT);
        blockWithItem(PrehistoricBlocks.SANDY_LOAM);
        blockWithItem(PrehistoricBlocks.LOAMY_SAND);
        blockWithItem(PrehistoricBlocks.PEAT);
        blockWithItem(PrehistoricBlocks.RAW_CLAY);
        snowyHorizontalBlockWithDifferentTopAndBottomWithOverlaySides(PrehistoricBlocks.LOAM_GRASS.get(), modLoc("block/loam_grass_block_side"), mcLoc("block/grass_block_top"), blockTexture(PrehistoricBlocks.LOAM.get()), mcLoc("block/grass_block_side_overlay"), modLoc("block/loam_grass_block_snow"), mcLoc("block/grass_block_top"));


        //DAWN REDWOOD
        logBlock((RotatedPillarBlock) PrehistoricBlocks.DAWN_REDWOOD_LOG.get());
        axisBlock((RotatedPillarBlock) PrehistoricBlocks.DAWN_REDWOOD_WOOD.get(), blockTexture(PrehistoricBlocks.DAWN_REDWOOD_LOG.get()), blockTexture(PrehistoricBlocks.DAWN_REDWOOD_LOG.get()));
        logBlock((RotatedPillarBlock) PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_LOG.get());
        axisBlock((RotatedPillarBlock) PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_WOOD.get(), blockTexture(PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_LOG.get()), blockTexture(PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_LOG.get()));
        blockWithOverlayAllSides(PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get(), modLoc("block/dawn_redwood_leaves_cones"));
        crossBlockWithOverlayAllSides(PrehistoricBlocks.DAWN_REDWOOD_CONES, modLoc("block/dawn_redwood_cones_leaves"), modLoc("block/dawn_redwood_cones"));
        blockWithItem(PrehistoricBlocks.DAWN_REDWOOD_PLANKS);
        stairsBlock(PrehistoricBlocks.DAWN_REDWOOD_STAIRS.get(), blockTexture(PrehistoricBlocks.DAWN_REDWOOD_PLANKS.get()));
        slabBlock(PrehistoricBlocks.DAWN_REDWOOD_SLAB.get(), blockTexture(PrehistoricBlocks.DAWN_REDWOOD_PLANKS.get()), blockTexture(PrehistoricBlocks.DAWN_REDWOOD_PLANKS.get()));
        fenceBlock(PrehistoricBlocks.DAWN_REDWOOD_FENCE.get(), blockTexture(PrehistoricBlocks.DAWN_REDWOOD_PLANKS.get()));
        fenceGateBlock(PrehistoricBlocks.DAWN_REDWOOD_FENCE_GATE.get(), blockTexture(PrehistoricBlocks.DAWN_REDWOOD_PLANKS.get()));
        doorBlockWithRenderType(PrehistoricBlocks.DAWN_REDWOOD_DOOR.get(), modLoc("block/dawn_redwood_door_bottom"), modLoc("block/dawn_redwood_door_top"), "cutout");
        customTrapdoorBlock(PrehistoricBlocks.DAWN_REDWOOD_TRAPDOOR.get(), modLoc("block/dawn_redwood_trapdoor"), blockTexture(PrehistoricBlocks.DAWN_REDWOOD_PLANKS.get()), true, "cutout");
        pressurePlateBlock(PrehistoricBlocks.DAWN_REDWOOD_PRESSURE_PLATE.get(), blockTexture(PrehistoricBlocks.DAWN_REDWOOD_PLANKS.get()));
        buttonBlock(PrehistoricBlocks.DAWN_REDWOOD_BUTTON.get(), blockTexture(PrehistoricBlocks.DAWN_REDWOOD_PLANKS.get()));
        signBlock(PrehistoricBlocks.DAWN_REDWOOD_SIGN.get(), PrehistoricBlocks.DAWN_REDWOOD_WALL_SIGN.get(), blockTexture(PrehistoricBlocks.DAWN_REDWOOD_PLANKS.get()));
        hangingSignBlock(PrehistoricBlocks.DAWN_REDWOOD_HANGING_SIGN.get(), PrehistoricBlocks.DAWN_REDWOOD_WALL_HANGING_SIGN.get(), blockTexture(PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_LOG.get()));
        blockItem(PrehistoricBlocks.DAWN_REDWOOD_LOG);
        blockItem(PrehistoricBlocks.DAWN_REDWOOD_WOOD);
        blockItem(PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_LOG);
        blockItem(PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_WOOD);
        blockItem(PrehistoricBlocks.DAWN_REDWOOD_STAIRS);
        blockItem(PrehistoricBlocks.DAWN_REDWOOD_SLAB);
        blockItemFence(PrehistoricBlocks.DAWN_REDWOOD_FENCE, "dawn_redwood_planks");
        blockItem(PrehistoricBlocks.DAWN_REDWOOD_FENCE_GATE);
        blockItemWithItemTexture(PrehistoricBlocks.DAWN_REDWOOD_DOOR, "dawn_redwood_door_item");
        blockItemTrapdoor(PrehistoricBlocks.DAWN_REDWOOD_TRAPDOOR, "dawn_redwood_trapdoor_bottom");
        blockItem(PrehistoricBlocks.DAWN_REDWOOD_PRESSURE_PLATE);
        blockItemButton(PrehistoricBlocks.DAWN_REDWOOD_BUTTON, "dawn_redwood_planks");
        blockItemWithItemTexture(PrehistoricBlocks.DAWN_REDWOOD_SAPLING, "dawn_redwood_sapling_item");

        /* FOSSILIFEROUS STONE */
        blockWithItem(PrehistoricBlocks.PRECAMBRIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.ORDOVICIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.SILURIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.DEVONIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.CARBONIFEROUS_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PERMIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.JURASSIC_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.CRETACEOUS_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PALEOGENE_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.NEOGENE_FOSSILIFEROUS_STONE);

        /* PLASTERED FOSSILIFEROUS STONE */
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_SILURIAN_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_PERMIAN_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_JURASSIC_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_NEOGENE_FOSSILIFEROUS_STONE);

        /* ORES */
        blockWithItem(PrehistoricBlocks.AMBER_BLOCK);
        blockWithItem(PrehistoricBlocks.DEEPSLATE_AMBER_ORE);
        blockWithItem(PrehistoricBlocks.SULFUR_ORE);
        blockWithItem(PrehistoricBlocks.DEEPSLATE_SULFUR_ORE);

        /* GYPSUM */
        itemModels().withExistingParent(PrehistoricBlocks.GYPSUM_CRYSTAL.getId().getPath(), mcLoc("item/generated"))
                        .texture("layer0", modLoc("block/" + PrehistoricBlocks.GYPSUM_CRYSTAL.getId().getPath()));
        blockWithItem(PrehistoricBlocks.GYPSUM_CRYSTAL_BLOCK);
    }

    /* HELPER METHODS */

    private void blockWithItemPlasterTexture(DeferredBlock<?> block) {
        simpleBlockWithItem(block.get(), models().cubeAll("plastered_fossiliferous_stone", modLoc("block/plastered_fossiliferous_stone")));
    }

    public void snowyHorizontalBlockWithDifferentTopAndBottomWithOverlaySides(Block block, ResourceLocation side, ResourceLocation top, ResourceLocation bottom, ResourceLocation overlay, ResourceLocation snowySide, ResourceLocation snowyTop) {
        String name = BuiltInRegistries.BLOCK.getKey(block).toString();

        BlockModelBuilder model = models().getBuilder(name)
                .parent(models().getExistingFile(mcLoc("block/block")))
                .texture("particle", bottom.toString())
                .texture("side", side.toString())
                .texture("top", top.toString())
                .texture("bottom", bottom.toString())
                .texture("overlay", overlay.toString())
                .renderType("cutout");

        model.element()
                .from(0, 0, 0).to(16, 16, 16)
                .allFacesExcept((dir, face) -> face.texture("#side"), Set.of(Direction.UP, Direction.DOWN))
                .face(Direction.UP).texture("#top").tintindex(0).end()
                .face(Direction.DOWN).texture("#bottom");

        model.element()
                .from(0, 0, 0).to(16, 16, 16)
                .allFacesExcept((dir, face) -> face.texture("#overlay").tintindex(0), Set.of(Direction.UP, Direction.DOWN));

        getVariantBuilder(block)
                .partialState().with(SnowyDirtBlock.SNOWY, false).addModels(new ConfiguredModel(model))
                .partialState().with(SnowyDirtBlock.SNOWY, true).addModels(new ConfiguredModel(models().cubeBottomTop(name(block) + "_snow", snowySide, bottom, snowyTop)));
        simpleBlockItem(block, model);
    }

    public void blockWithOverlayAllSides(Block block, ResourceLocation overlay) {
        String name = BuiltInRegistries.BLOCK.getKey(block).toString();
        String path = name(block);
        ResourceLocation texture = modLoc("block/" + path);

        BlockModelBuilder model = models().getBuilder(name)
                .parent(models().getExistingFile(mcLoc("block/block")))
                .texture("particle", texture.toString())
                .texture("texture", texture.toString())
                .texture("overlay", overlay.toString())
                .renderType("cutout");

        model.element()
                .from(0, 0, 0).to(16, 16, 16)
                .allFaces((dir, face) -> face.texture("#texture").tintindex(0));

        model.element()
                .from(0, 0, 0).to(16, 16, 16)
                .allFaces((dir, face) -> face.texture("#overlay"));

        simpleBlockWithItem(block, model);
    }

    public void crossBlockWithOverlayAllSides(DeferredBlock<?> block, ResourceLocation texture, ResourceLocation overlay) {
        String name = BuiltInRegistries.BLOCK.getKey(block.get()).toString();

        BlockModelBuilder model = models().getBuilder(name)
                .parent(models().getExistingFile(mcLoc("block/cross")))
                .texture("particle", texture.toString())
                .texture("cross", texture.toString())
                .texture("overlay", overlay.toString())
                .renderType("cutout");

        model.element()
                .from(0.8f, 0, 8).to(15.2f, 16, 8)
                .face(Direction.NORTH).texture("#cross").tintindex(0).end()
                .face(Direction.SOUTH).texture("#cross").tintindex(0).end()
                .rotation().origin(8, 8, 8).axis(Direction.Axis.Y).angle(45).rescale(true);

        model.element()
                .from(8, 0, 0.8f).to(8, 16, 15.2f)
                .face(Direction.EAST).texture("#cross").tintindex(0).end()
                .face(Direction.WEST).texture("#cross").tintindex(0).end()
                .rotation().origin(8, 8, 8).axis(Direction.Axis.Y).angle(45).rescale(true);

        // OVERLAY

        model.element()
                .from(0.8f, 0, 8).to(15.2f, 16, 8)
                .face(Direction.NORTH).texture("#overlay").end()
                .face(Direction.SOUTH).texture("#overlay").end()
                .rotation().origin(8, 8, 8).axis(Direction.Axis.Y).angle(45).rescale(true);

        model.element()
                .from(8, 0, 0.8f).to(8, 16, 15.2f)
                .face(Direction.EAST).texture("#overlay").end()
                .face(Direction.WEST).texture("#overlay").end()
                .rotation().origin(8, 8, 8).axis(Direction.Axis.Y).angle(45).rescale(true);

        simpleBlock(block.get(), model);
        blockItemWithItemTexture(block, "dawn_redwood_cones_item");
    }

    private void saplingBlock(DeferredBlock<?> block) {
        simpleBlock(block.get(), models().cross(block.getId().getPath(), blockTexture(block.get())).renderType("cutout"));
    }

    private void blockWithItem(DeferredBlock<?> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private void blockItem(DeferredBlock<?> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile("prehistoriccraft:block/" + block.getId().getPath()));
    }

    private void blockItemFence(DeferredBlock<?> block, String texture) {
        customBlockItem(block, modLoc("block/" + texture), mcLoc("block/fence_inventory"), "texture");
    }

    private void blockItemButton(DeferredBlock<?> block, String texture) {
        customBlockItem(block, modLoc("block/" + texture), mcLoc("block/button_inventory"), "texture");
    }

    private void blockItemTrapdoor(DeferredBlock<?> block, String texture) {
        customBlockItemParent(block, modLoc("block/" + texture));
    }

    private void blockItemWithItemTexture(DeferredBlock<?> block, String texture) {
        customBlockItem(block, modLoc("item/" + texture), mcLoc("item/generated"), "layer0");
    }

    private void customBlockItem(DeferredBlock<?> block, ResourceLocation texture, ResourceLocation parent, String textureKey) {
        customBlockItemParent(block, parent).texture(textureKey, texture);
    }

    private ModelBuilder customBlockItemParent(DeferredBlock<?> block, ResourceLocation parent) {
        return itemModels().withExistingParent(block.getId().getPath(), parent);
    }

    private void customTrapdoorBlock(TrapDoorBlock block, ResourceLocation texture, ResourceLocation sideTexture, boolean orientable, String renderType) {
        String name = name(block);
        ModelFile bottom = customTrapdoorBottom(name + "_bottom", texture, sideTexture).renderType("minecraft:" + renderType);
        ModelFile top = customTrapdoorTop(name + "_top", texture, sideTexture).renderType("minecraft:" + renderType);
        ModelFile open = customTrapdoorOpen(name + "_open", texture, sideTexture).renderType("minecraft:" + renderType);
        trapdoorBlock(block, bottom, top, open, orientable);
    }

    private ModelBuilder customTrapdoorBottom(String name, ResourceLocation texture, ResourceLocation other) {
        return customTrapdoorTextureBuilder(name, "prehistoriccraft:block/template_custom_trapdoor_bottom", texture, other);
    }

    private ModelBuilder customTrapdoorTop(String name, ResourceLocation texture, ResourceLocation other) {
        return customTrapdoorTextureBuilder(name, "prehistoriccraft:block/template_custom_trapdoor_top", texture, other);
    }

    private ModelBuilder customTrapdoorOpen(String name, ResourceLocation texture, ResourceLocation other) {
        return customTrapdoorTextureBuilder(name, "prehistoriccraft:block/template_custom_trapdoor_open", texture, other);
    }

    private ModelBuilder customTrapdoorTextureBuilder(String name, String parent, ResourceLocation texture, ResourceLocation other) {
        return models().withExistingParent(name, parent)
                .texture("texture", texture)
                .texture("other", other);
    }
}