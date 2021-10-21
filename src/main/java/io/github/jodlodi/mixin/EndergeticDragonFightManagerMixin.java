package io.github.jodlodi.mixin;

import com.minecraftabnormals.endergetic.common.world.EndergeticDragonFightManager;
import io.github.jodlodi.Config;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.server.ServerWorld;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndergeticDragonFightManager.class)
public abstract class EndergeticDragonFightManagerMixin extends DragonFightManager {
    public EndergeticDragonFightManagerMixin(ServerWorld p_i231901_1_, long p_i231901_2_, CompoundNBT p_i231901_4_) {
        super(p_i231901_1_, p_i231901_2_, p_i231901_4_);
    }

    @Shadow public abstract void spawnExitPortal(boolean p_186094_1_);
    @Shadow protected abstract void spawnNewGateway();

    @Redirect(method = "scanForLegacyFight", at = @At(value = "INVOKE", target = "Lcom/minecraftabnormals/endergetic/common/world/EndergeticDragonFightManager;spawnExitPortal(Z)V"))
    private void spawnExitPortalMixinRedirect(EndergeticDragonFightManager instance, boolean active) {
        if (Config.portalState) {
            spawnExitPortal(Config.portalOpen);
        }
        if (Config.gatewayState) {
            spawnNewGateway();
        }
    }

    @Redirect(method = "scanForLegacyFight", at = @At(value = "FIELD", target = "Lcom/minecraftabnormals/endergetic/common/world/EndergeticDragonFightManager;previouslyKilled:Z", opcode = Opcodes.GETFIELD))
    private boolean previouslyKilledMixin(EndergeticDragonFightManager instance) {
        if (Config.naturalState) {
            return instance.hasPreviouslyKilledDragon();
        } else {
            return true;
        }
    }

    @Inject(method = "tryRespawn", at = @At(value = "HEAD"), cancellable = true)
    private void tryRespawnMixin(CallbackInfo ci) {
        if (!Config.dragonState){
            ci.cancel();
        }
    }

    @Redirect(method = "setDragonKilled", at = @At(value = "FIELD", target = "Lcom/minecraftabnormals/endergetic/common/world/EndergeticDragonFightManager;previouslyKilled:Z", opcode = Opcodes.GETFIELD))
    private boolean previouslyKilledEggMixin(EndergeticDragonFightManager instance) {
        if (Config.eggState) {
            return false;
        } else {
            return instance.hasPreviouslyKilledDragon();
        }
    }
}
