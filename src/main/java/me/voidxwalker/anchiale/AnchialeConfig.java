package me.voidxwalker.anchiale;

import me.contaria.speedrunapi.config.api.SpeedrunConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class AnchialeConfig implements SpeedrunConfig {
    public ButtonLocation buttonLocation = ButtonLocation.HIDE;

    {
        Anchiale.config = this;
    }

    @Override
    public void preLoad() {
        Path config = FabricLoader.getInstance().getConfigDir().resolve("anchiale.txt");
        if (!Files.exists(config)) return;
        try {
            int ordinal = Integer.parseInt(new String(Files.readAllBytes(config), StandardCharsets.UTF_8));
            ordinal = Math.max(0, Math.min(ButtonLocation.values().length - 1, ordinal));
            buttonLocation = ButtonLocation.values()[ordinal];
            Files.delete(config);
        } catch (IOException | NumberFormatException e) {
            Anchiale.LOGGER.error("Failed to read Anchiale config", e);
        }
    }

    @Override
    public String modID() {
        return "anchiale";
    }

    public enum ButtonLocation {
        BOTTOM_RIGHT,
        CENTER,
        HIDE
    }
}
