package Repo;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public final class LauncherUpdater
{
    public static final LauncherUpdater INSTANCE = new LauncherUpdater();

    private LauncherUpdater()
    {}

    public static LauncherUpdater getINSTANCE()
    {
        return INSTANCE;
    }

    // Credits: https://stackoverflow.com/questions/6293713/java-how-to-create-sha-1-for-a-file
    public String getLauncherSHA1(File file) throws NoSuchAlgorithmException, IOException
    {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        try (InputStream input = new FileInputStream(file))
        {
            byte[] buffer = new byte[8192];
            int len = input.read(buffer);

            while (len != -1)
            {
                sha1.update(buffer, 0, len);
                len = input.read(buffer);
            }

            return new HexBinaryAdapter().marshal(sha1.digest());
        }
    }
}
