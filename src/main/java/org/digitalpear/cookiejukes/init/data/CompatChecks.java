package org.digitalpear.cookiejukes.init.data;

import net.fabricmc.loader.api.FabricLoader;

public class CompatChecks {
    public static final String FD_ID = "farmersdelight";
    public static boolean isFDLoaded(){
        return FabricLoader.getInstance().isModLoaded(FD_ID);
    }
    public static boolean isRCLoaded(){
        return FabricLoader.getInstance().isModLoaded("resin_cookie");

    }
}
