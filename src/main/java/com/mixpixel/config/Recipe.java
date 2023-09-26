package com.mixpixel.config;

import java.util.List;

public record Recipe(String name, boolean useShard, String useShardName, int useShardAmount, boolean useProtection,
                     String useProtectionName, int useProtectionAmount, int winProbability, String winItemName,
                     List<String> winItemLore) {
}
