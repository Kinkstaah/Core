package Repo;

import java.util.ArrayList;

/**
 *
 */
public final class Addons
{
    private static final Addons INSTANCe = new Addons();

    private ArrayList<AddonJson> addons = new ArrayList<>();

    private Addons()
    {

    }

    public static Addons getINSTANCe()
    {
        return INSTANCe;
    }

    public ArrayList<AddonJson> getAddons()
    {
        return addons;
    }

    public void addAddon(AddonJson addonJson)
    {
        addonJson.setTimestamp();
        int num = findAddon(addonJson.getName());

        if (num >= 0)
        {
            addons.remove(num);
            // Find out which is a newer version.
            addonJson = addonJson.isNewerVersion(addons.get(num));
        }
        addons.add(addonJson);
    }

    public int findAddon(String name)
    {
        for (int c = 0; c < addons.size(); c++)
        {
            if (addons.get(c).getName().equals(name))
                return c;
        }
        return -1;
    }

    public AddonJson getAddonByName(String name)
    {
        for  (AddonJson addonJson : addons)
        {
            if (addonJson.getName().equals(name))
                return addonJson;
        }
        return null;
    }
}
