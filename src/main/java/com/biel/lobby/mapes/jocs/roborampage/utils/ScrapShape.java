package com.biel.lobby.mapes.jocs.roborampage.utils;

import java.util.List;

public record ScrapShape(List<Block> blocks) {
    public ScrapShape {
        blocks = List.copyOf(blocks);
        if (blocks.isEmpty()) throw new IllegalArgumentException("A scrap shape needs at least one block");
    }

    public record Block(ScrapGrid.Cell offset, ScrapMaterial material) {}
}
