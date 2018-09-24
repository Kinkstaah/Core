package Repo;

/**
 *
 */
public class NewestVersion
{
    /**
     * Algorithm for figuring out which is a newest version
     * @param current the current version
     * @param potential_new the "new" version
     * @return
     */
    public static boolean isNewer(String current, String potential_new)
    {
        /**
         * This is on an assumption that the LOCAL_PAL_FOLDER version can
         * never be the newest version, which, theoretically it can't.
         */
        return !current.equals(potential_new);
    }
}
