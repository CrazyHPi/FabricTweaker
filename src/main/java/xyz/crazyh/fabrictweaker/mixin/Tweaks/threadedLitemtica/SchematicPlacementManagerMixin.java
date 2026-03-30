package xyz.crazyh.fabrictweaker.mixin.Tweaks.threadedLitemtica;

import fi.dy.masa.litematica.schematic.placement.SchematicPlacementManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SchematicPlacementManager.class)
public abstract class SchematicPlacementManagerMixin {

    //? if = 1.21 {
    /*@Redirect(
            method = "processQueuedChunks",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/litematica/util/SchematicPlacingUtils;placeToWorldWithinChunk(Lnet/minecraft/world/World;Lnet/minecraft/util/math/ChunkPos;Lfi/dy/masa/litematica/schematic/placement/SchematicPlacement;Lfi/dy/masa/litematica/util/ReplaceBehavior;Lfi/dy/masa/litematica/util/PasteLayerBehavior;Z)Z"
            )
    )
    private boolean threadedUpdate(
            World blockEntityMap,
            ChunkPos scheduledBlockTicks,
            SchematicPlacement scheduledFluidTicks,
            ReplaceBehavior entityList,
            PasteLayerBehavior container,
            boolean placement
    ) {
        if (FeatureToggle.THREADED_LITEMATICA_UPDATE.getBooleanValue()) {
            new Thread(
                    () -> SchematicPlacingUtils.placeToWorldWithinChunk(
                            blockEntityMap,
                            scheduledBlockTicks,
                            scheduledFluidTicks,
                            entityList,
                            container,
                            placement
                    )).start();
            return true;
        }

        return SchematicPlacingUtils.placeToWorldWithinChunk(
                blockEntityMap,
                scheduledBlockTicks,
                scheduledFluidTicks,
                entityList,
                container,
                placement
        );
    }

    *///? }
}
