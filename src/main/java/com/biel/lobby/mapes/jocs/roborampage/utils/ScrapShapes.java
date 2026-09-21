package com.biel.lobby.mapes.jocs.roborampage.utils;

import java.util.ArrayList;
import java.util.List;

/** Integer-only voxel templates used by combat wreckage and occasional junk deliveries. */
public final class ScrapShapes {
    private ScrapShapes() {}

    public static ScrapShape single(ScrapMaterial material) {
        return new ScrapShape(List.of(new ScrapShape.Block(new ScrapGrid.Cell(0, 0, 0), material)));
    }

    /** 24 full blocks: chassis, four wheels, cabin and roof. */
    public static ScrapShape car() {
        List<ScrapShape.Block> blocks = new ArrayList<>();
        for (int x = 0; x < 5; x++) {
            for (int z = 0; z < 2; z++) {
                blocks.add(block(x, 1, z, ScrapMaterial.IRON));
            }
        }
        for (int x : List.of(0, 4)) {
            for (int z = 0; z < 2; z++) {
                blocks.add(block(x, 0, z, ScrapMaterial.COAL));
            }
        }
        for (int x = 1; x <= 3; x++) {
            for (int z = 0; z < 2; z++) {
                blocks.add(block(x, 2, z, ScrapMaterial.GLASS));
            }
        }
        for (int x = 1; x <= 2; x++) {
            for (int z = 0; z < 2; z++) {
                blocks.add(block(x, 3, z, ScrapMaterial.IRON));
            }
        }
        return new ScrapShape(blocks);
    }

    private static ScrapShape.Block block(int x, int y, int z, ScrapMaterial material) {
        return new ScrapShape.Block(new ScrapGrid.Cell(x, y, z), material);
    }
}
