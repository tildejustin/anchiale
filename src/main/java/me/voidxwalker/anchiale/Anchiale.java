package me.voidxwalker.anchiale;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class Anchiale {
    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean fastReset = false;
    public static ButtonLocation buttonLocation = ButtonLocation.HIDE;
    public static Path config = FabricLoader.getInstance().getConfigDir().resolve("anchiale.txt");

    static {
        try {
            if (Files.notExists(config)) {
                saveConfig();
            }
            int ordinal = Integer.parseInt(new String(Files.readAllBytes(config), StandardCharsets.UTF_8));
            ordinal = Math.max(0, Math.min(ButtonLocation.values().length - 1, ordinal));
            buttonLocation = ButtonLocation.values()[ordinal];
        } catch (IOException | NumberFormatException e) {
            LOGGER.error("Failed to read Anchiale config", e);
        }
    }

    public static void saveConfig() {
        try {
            Files.write(config, Integer.toString(buttonLocation.ordinal()).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            LOGGER.error("Failed to write Anchiale config", e);
        }
    }

    public enum ButtonLocation {
        BOTTOM_RIGHT("Bottom Right"),
        CENTER("Center"),
        HIDE("Hidden");

        private final String name;

        ButtonLocation(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
