package srki2k.tweakedexcavation.util;

import srki2k.tweakedexcavation.TweakedExcavation;
import srki2k.tweakedexcavation.api.ihelpers.IMineralMix;
import srki2k.tweakedlib.api.logging.errorlogginglib.ErrorLoggingLib;
import srki2k.tweakedlib.api.logging.errorlogginglib.ICustomLogger;
import srki2k.tweakedlib.api.powertier.PowerTierHandler;

import java.util.ArrayList;
import java.util.List;

import static blusunrize.immersiveengineering.api.tool.ExcavatorHandler.mineralList;
import static srki2k.tweakedexcavation.common.Configs.TPConfig.PowerTiers.defaultPowerTier;

public final class TweakedExcavationErrorLogging implements ICustomLogger {
    public static void register() {
        ErrorLoggingLib.addCustomLogger(new TweakedExcavationErrorLogging());
    }

    private TweakedExcavationErrorLogging() {
    }

    List<String> errors = new ArrayList<>();

    @Override
    public boolean doCustomCheck() {
        return false;
    }

    @Override
    public boolean handleRuntimeErrors() {
        mineralList.keySet().
                forEach(mineralMix -> {
                    if (PowerTierHandler.getPowerTier(((IMineralMix) mineralMix).getPowerTier()) == null) {
                        errors.add("Mineral with the ID (name) " + mineralMix.name + " has no valid Power tier");
                    }
                });

        return !errors.isEmpty();
    }

    @Override
    public boolean discardLoggerAfterStartup() {
        return false;
    }

    @Override
    public String getMODID() {
        return TweakedExcavation.MODID;
    }

    @Override
    public String[] getConfigs() {
        String[] strings = new String[1];

        strings[0] = "Default Minerals PowerTier: " + defaultPowerTier;

        return strings;
    }

    @Override
    public List<String> getErrors() {
        return errors;
    }

    @Override
    public void clean() {
        errors.clear();
    }
}
