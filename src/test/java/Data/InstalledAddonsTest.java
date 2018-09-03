package Data;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class InstalledAddonsTest
{

    @Test
    public void scanForInstalledAddons()
    {
        IniHandler.readProperties();
        InstalledAddons.getINSTANCE().scanForInstalledAddons();
    }
}