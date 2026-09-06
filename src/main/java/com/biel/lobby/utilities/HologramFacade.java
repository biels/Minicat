package com.biel.lobby.utilities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;

public final class HologramFacade {
    private static final String NAME_PREFIX = "minicat-";
    private static final Set<Handle> ACTIVE_HANDLES = new HashSet<>();

    private HologramFacade() {
    }

    public static Handle create(Location location) {
        String name = NAME_PREFIX + UUID.randomUUID();
        Hologram hologram = DHAPI.createHologram(name, location, false, List.of());
        Handle handle = new Handle(hologram);
        ACTIVE_HANDLES.add(handle);
        return handle;
    }

    public static void deleteAll() {
        for (Handle handle : List.copyOf(ACTIVE_HANDLES)) {
            handle.delete();
        }
    }

    public static final class Handle {
        private final Hologram hologram;

        private Handle(Hologram hologram) {
            this.hologram = hologram;
        }

        public void clearLines() {
            DHAPI.setHologramLines(hologram, List.of());
        }

        public void appendTextLine(String text) {
            DHAPI.addHologramLine(hologram, text);
        }

        public void setLines(String... lines) {
            DHAPI.setHologramLines(hologram, Arrays.asList(lines));
        }

        public void showOnlyTo(Player player) {
            hologram.setDefaultVisibleState(false);
            hologram.setShowPlayer(player);
        }

        public void delete() {
            hologram.delete();
            ACTIVE_HANDLES.remove(this);
        }
    }
}
