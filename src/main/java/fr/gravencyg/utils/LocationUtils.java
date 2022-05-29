package fr.gravencyg.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    public static String fromLocToString(Location location) {
        return location.getX() + "/" + location.getY() + "/" + location.getZ();
    }

    public static String fromLocToString2(Location location) {
        return "x:" + (int)location.getX() + " y:" + (int)location.getY() + " z:" + (int)location.getZ() + " yaw:" + (int)location.getYaw() + " pitch:" + (int)location.getPitch();
    }

    public static Location fromString2toLoc(String ownerUUID, String string) {
        String[] sloc = string.split(" ");
        double x = Double.valueOf(sloc[0].replace("x:",""));
        double y = Double.valueOf(sloc[1].replace("y:",""));
        double z = Double.valueOf(sloc[2].replace("z:",""));
        float yaw = Float.valueOf(sloc[3].replace("yaw:",""));
        float pitch = Float.valueOf(sloc[4].replace("pitch:",""));
        return new Location(Bukkit.getWorld(ownerUUID), x, y, z, yaw, pitch);
    }

    public static Location fromStringToLoc(String uuid, String string) {
        String[] sloc = string.split("/");
        double x = Double.valueOf(sloc[0]);
        double y = Double.valueOf(sloc[1]);
        double z = Double.valueOf(sloc[2]);
        return new Location(Bukkit.getWorld(uuid), x, y, z);
    }

    public static Location fromStringToLocComplete(String uuid, String string) {
        String[] sloc = string.split("/");
        double x = Double.valueOf(sloc[0]);
        double y = Double.valueOf(sloc[1]);
        double z = Double.valueOf(sloc[2]);
        float yaw = Float.valueOf(sloc[3]);
        float pitch = Float.valueOf(sloc[4]);
        return new Location(Bukkit.getWorld(uuid), x, y, z, yaw, pitch);
    }

    public static String fromLocToStringComplete(Location location) {
        return location.getX() + "/" + location.getY() + "/" + location.getZ() +
                "/" + location.getYaw() + "/" + location.getPitch();
    }

    public static String fromStringToSimpleString(String uuid, String string) {
        return fromLocToString2(fromStringToLocComplete(uuid, string));
    }

    public static String defaultSpawn() {
        return "0/50/0/0/0";
    }
}
