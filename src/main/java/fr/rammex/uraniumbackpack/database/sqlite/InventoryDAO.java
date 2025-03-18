package fr.rammex.uraniumbackpack.database.sqlite;

import fr.rammex.uraniumbackpack.UraniumBackpack;
import fr.rammex.uraniumbackpack.utils.inventory.ItemStackSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

import static fr.rammex.uraniumbackpack.database.sqlite.SQLiteManager.getSQLConnection;

public class InventoryDAO {
    private static SQLiteManager databaseManager;

    public InventoryDAO(SQLiteManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public static void saveInventory(Player player) {
        String playerUUID = player.getUniqueId().toString();
        ItemStack[] inventoryContents = player.getInventory().getContents();
        String inventoryJson = ItemStackSerializer.serialize(inventoryContents);

        try (Connection connection = getSQLConnection()) {
            String query = "INSERT OR REPLACE INTO inventory (player_uuid, item_data) VALUES (?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, playerUUID);
                ps.setString(2, inventoryJson);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            UraniumBackpack.instance.getLogger().log(Level.SEVERE, "Unable to save inventory", ex);
        }
    }

    public static ItemStack[] loadInventory(UUID playerUUID) {
        String query = "SELECT item_data FROM inventory WHERE player_uuid = ?";
        try (Connection connection = getSQLConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String itemData = resultSet.getString("item_data");
                String queryDelete = "DELETE FROM inventory WHERE player_uuid = ?";
                PreparedStatement statementDelete = connection.prepareStatement(queryDelete);
                statementDelete.setString(1, playerUUID.toString());
                statementDelete.executeUpdate();
                return ItemStackSerializer.deserialize(itemData);
            }
        } catch (SQLException ex) {
            UraniumBackpack.instance.getLogger().log(Level.SEVERE, "Unable to load inventory", ex);
        }
        return new ItemStack[0];
    }

    public static void deleteInventory(UUID playerUUID) {

    }

    public static boolean isPlayerRegistered(UUID playerUUID) {
        String query = "SELECT * FROM inventory WHERE player_uuid = ?";
        try (Connection connection = getSQLConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            UraniumBackpack.instance.getLogger().log(Level.SEVERE, "An error occurred", ex);
        }
        return false;
    }
}