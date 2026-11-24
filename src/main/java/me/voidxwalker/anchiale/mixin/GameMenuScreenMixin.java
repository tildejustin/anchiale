package me.voidxwalker.anchiale.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.voidxwalker.anchiale.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.menu.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    @Unique
    private static final int id = 1507;

    @ModifyExpressionValue(method = "init", at = @At(value = "NEW", target = "net/minecraft/client/gui/widget/ButtonWidget", ordinal = 0))
    private ButtonWidget addAnchialeButton(ButtonWidget original) {
        if (!this.minecraft.isSingleplayer()) {
            return original;
        }

        int x, y, height = 20, width;
        switch (Anchiale.config.buttonLocation) {
            case CENTER:
                width = original.getWidth();
                x = original.x;
                y = original.y + height + 4;
                break;
            case BOTTOM_RIGHT:
            default:
                width = 100;
                x = this.width - width - 4;
                y = this.height - height - 4;
                break;
        }
        ButtonWidget button = new ButtonWidget(id, x, y, width, height, I18n.translate("anchiale.menu.quitWorld"));
        if (Anchiale.config.buttonLocation == AnchialeConfig.ButtonLocation.HIDE) {
            button.visible = false;
        }
        this.buttons.add(button);
        return original;
    }

    @Inject(method = "buttonClicked", at = @At("HEAD"))
    private void onMenuQuitWorldClicked(ButtonWidget button, CallbackInfo ci) {
        if (button.id == id) {
            Anchiale.fastReset = true;
            button.id = 1;
        }
    }

    @Inject(method = "buttonClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setWorld(Lnet/minecraft/client/world/ClientWorld;)V", shift = At.Shift.AFTER))
    private void fastResetFalse(CallbackInfo ci) {
        Anchiale.fastReset = false;
    }
}
