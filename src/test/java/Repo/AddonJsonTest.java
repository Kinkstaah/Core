package Repo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class AddonJsonTest
{

    @Test
    public void isNewerVersion()
    {
        AddonJson a = new AddonJson("foo", "1.0.1b-c18", "","","","","","","","");
        AddonJson a2 = new AddonJson("foo", "1.0.2b-c18", "","","","","","","","");
        assertEquals(a2, a.isNewerVersion(a2));
    }
}