package com.nisovin.shopkeepers.shopobjects;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.nisovin.shopkeepers.api.shopkeeper.ShopCreationData;
import com.nisovin.shopkeepers.api.shopobjects.ShopObjectType;
import com.nisovin.shopkeepers.shopkeeper.AbstractShopkeeper;
import com.nisovin.shopkeepers.types.AbstractSelectableType;
import com.nisovin.shopkeepers.util.Utils;

public abstract class AbstractShopObjectType<T extends AbstractShopObject> extends AbstractSelectableType implements ShopObjectType<T> {

	protected AbstractShopObjectType(String identifier, String permission) {
		super(identifier, permission);
	}

	/**
	 * Creates a shop object.
	 * 
	 * @param shopkeeper
	 *            the shopkeeper, not <code>null</code>
	 * @param creationData
	 *            the used shop creation data, can be <code>null</code> (for ex. if the shopkeeper gets loaded)
	 * @return the shop object
	 */
	public abstract T createObject(AbstractShopkeeper shopkeeper, ShopCreationData creationData);

	@Override
	public abstract boolean needsSpawning();

	/**
	 * Whether shop objects of this type get despawned right before world saves and respawned afterwards.
	 * 
	 * @return <code>true</code> if the shop objects of this type get despawned during world saves
	 */
	public boolean despawnDuringWorldSaves() {
		return this.needsSpawning();
	}

	@Override
	public boolean isValidSpawnLocation(Location spawnLocation, BlockFace targetedBlockFace) {
		// TODO check actual object size?
		if (spawnLocation == null || spawnLocation.getWorld() == null) return false;
		Block spawnBlock = spawnLocation.getBlock();
		if (!spawnBlock.isPassable()) return false;
		if (targetedBlockFace != null) {
			if (targetedBlockFace == BlockFace.DOWN || !Utils.isBlockSide(targetedBlockFace)) {
				return false;
			}
		}
		return true;
	}
}
