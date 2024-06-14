package xyz.crazyh.fabrictweaker.utils;

import fi.dy.masa.malilib.util.restrictions.ItemRestriction;
import net.minecraft.item.Item;
import xyz.crazyh.fabrictweaker.config.Configs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class WCItemRestriction extends ItemRestriction {
    private final HashSet<String> blackWildcards = new HashSet<>();
    private final HashSet<String> whiteWildcards = new HashSet<>();

    @Override
    public void setListContents(List<String> namesBlacklist, List<String> namesWhitelist) {
        blackWildcards.clear();
        whiteWildcards.clear();
        List<String> tempB = new ArrayList<>();
        List<String> tempW = new ArrayList<>();

        for (String s : namesBlacklist) {
            if (s.contains("*")) {
                blackWildcards.add(s.replace("minecraft:", "").replace("*", ".*"));
            } else {
                tempB.add(s);
            }
        }
        for (String s : namesWhitelist) {
            if (s.contains("*")) {
                whiteWildcards.add(s.replace("minecraft:", "").replace("*", ".*"));
            } else {
                tempW.add(s);
            }
        }

        super.setListContents(tempB, tempW);
    }

    @Override
    public boolean isAllowed(Item item) {
        ListType type = (ListType) Configs.Lists.DROP_INV_LIST_TYPE.getOptionListValue();
        if (getWCStringForType(type).isEmpty()) {
            return super.isAllowed(item);
        }
        if (type == ListType.NONE) {
            return true;
        }

        String itemName = item.toString();
        boolean result;
        for (String s : getWCStringForType(type)) {
            if (itemName.matches(s)) {
                result = type == ListType.WHITELIST;
                return result;
            }
        }

        return super.isAllowed(item);
    }

    public Set<String> getWCStringForType(ListType type) {
        return type == ListType.WHITELIST ? whiteWildcards : blackWildcards;
    }
}
