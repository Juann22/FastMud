package br.minilambda.event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class DirtToMudUsingWater implements Listener {
    private Integer[][] blockDirections = {
        {0, -1, 0}, // Bottom.
        {-1, 0, 0}, // X negative.
        {+1, 0, 0}, // X positive.
        {0, 0, -1}, // Z negative.
        {0, 0, +1}  // Z positive.
    };

    @EventHandler
    public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent event){
        /*
         * When player uses water bucket.
         */
        // Exit if not using water bucket.
        if(event.getBucket() != Material.WATER_BUCKET) {
            return;
        }

        // Get block occupied by water.
        Block blockOccupiedByWater = event.getBlock();

        // Iterating block direction weights.
        for(Integer[] weight : this.blockDirections){
            // Getting block.
            Block block = blockOccupiedByWater.getWorld().getBlockAt(
                blockOccupiedByWater.getX() + weight[0],
                blockOccupiedByWater.getY() + weight[1],
                blockOccupiedByWater.getZ() + weight[2]
            );

            // Jump to next loop if block is different of dirt.
            if(block.getType() != Material.DIRT){
                continue;
            }

            // Else, set block type to mud block.
            block.setType(Material.MUD);
        }
    }

}
