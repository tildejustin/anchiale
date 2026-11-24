package me.voidxwalker.anchiale.mixin;

import me.voidxwalker.anchiale.Anchiale;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.menu.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;<init>(IIIIILjava/lang/String;)V", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=options.stream")))
    private String renameBroadcastButton(String string) {
        return Anchiale.buttonLocation.toString();
    }

    @Inject(method = "buttonClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getTwitchStream()Lnet/minecraft/client/twitch/TwitchStream;"), cancellable = true)
    private void locationButtonClicked(ButtonWidget button, CallbackInfo ci) {
        Anchiale.ButtonLocation[] locations = Anchiale.ButtonLocation.values();
        Anchiale.buttonLocation = locations[(Anchiale.buttonLocation.ordinal() + 1) % locations.length];
        button.message = Anchiale.buttonLocation.toString();
        Anchiale.saveConfig();
        ci.cancel();
    }
}
