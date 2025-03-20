package br.minilambda.event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
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

    @EventHandler
    public void onWaterFlow(BlockFromToEvent event) {
        /*
         * When water flows.
         */
        // From block.
        Block block = event.getBlock();
        // To block.
        Block toBlock = event.getToBlock();

        // Exit if block type is different of water.
        if(block.getType() != Material.WATER) {
            return;
        }

        // Getting bottom block.
        Block bottomBlock = toBlock.getWorld().getBlockAt(
            toBlock.getX(),
            toBlock.getY() - 1,
            toBlock.getZ())
        ;

        // Exit if bottom block is different of dirt.
        if(bottomBlock.getType() != Material.DIRT){
            return;
        }

        // Set bottom block type to mud block.
        bottomBlock.setType(Material.MUD);
    }

    @EventHandler
    public void onBlockPlaceUnderWater(BlockPlaceEvent event){
        /*
         * When block is placed under water.
         */
        // Getting block placed.
        Block block = event.getBlock();

        // Exit if block is different of dirt.
        if(block.getType() != Material.DIRT){
            return;
        }

        // Getting top block.
        Block topBlock = block.getWorld().getBlockAt(
            block.getX(),
            block.getY() + 1,
            block.getZ()
        );

        // Exit if top block is water.
        if(topBlock.getType() != Material.WATER){
            return;
        }

        // Set block type to mud.
        block.setType(Material.MUD);
    }
}
