package me.voidxwalker.anchiale.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import me.voidxwalker.anchiale.Anchiale;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.concurrent.Future;

@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin {
    @WrapOperation(method = "stop", at = @At(value = "INVOKE", target = "Lcom/google/common/util/concurrent/Futures;getUnchecked(Ljava/util/concurrent/Future;)Ljava/lang/Object;"))
    public Object fastReset(Future<?> e, Operation<Object> original) {
        if (Anchiale.fastReset) {
            Anchiale.LOGGER.info("Exiting world without waiting for server tasks to finish");
            return null;
        }
        return original.call(e);
    }
}
