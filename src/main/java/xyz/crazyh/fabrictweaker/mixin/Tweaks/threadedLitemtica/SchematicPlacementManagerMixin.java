package xyz.crazyh.fabrictweaker.mixin.Tweaks.threadedLitemtica;

import fi.dy.masa.litematica.schematic.placement.SchematicPlacement;
import fi.dy.masa.litematica.schematic.placement.SchematicPlacementManager;
import fi.dy.masa.litematica.util.ReplaceBehavior;
import fi.dy.masa.litematica.util.SchematicPlacingUtils;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;

@Mixin(SchematicPlacementManager.class)
public abstract class SchematicPlacementManagerMixin {

    @Redirect(
            method = "processQueuedChunks",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/litematica/util/SchematicPlacingUtils;placeToWorldWithinChunk(Lnet/minecraft/world/World;Lnet/minecraft/util/math/ChunkPos;Lfi/dy/masa/litematica/schematic/placement/SchematicPlacement;Lfi/dy/masa/litematica/util/ReplaceBehavior;Z)Z"
            )
    )
    private boolean threadedUpdate(World world,
                                   ChunkPos chunkPos,
                                   SchematicPlacement schematicPlacement,
                                   ReplaceBehavior replace,
                                   boolean notifyNeighbors) {
        if (FeatureToggle.THREADED_LITEMATICA_UPDATE.getBooleanValue()) {
            new Thread(
                    () -> SchematicPlacingUtils.placeToWorldWithinChunk(world, chunkPos, schematicPlacement, replace, notifyNeighbors)
            ).start();
            return true;
        }
        return SchematicPlacingUtils.placeToWorldWithinChunk(world, chunkPos, schematicPlacement, replace, notifyNeighbors);
    }
}
