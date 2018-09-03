package IO;

/**
 *
 */
public class CustomAHK
{
    private String location;
    private boolean launch_with_poe;

    private CustomAHK()
    {

    }

    public CustomAHK(String location, boolean launch_with_poe)
    {
        this.location = location;
        this.launch_with_poe = launch_with_poe;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public boolean isLaunch_with_poe()
    {
        return launch_with_poe;
    }

    public void setLaunch_with_poe(boolean launch_with_poe)
    {
        this.launch_with_poe = launch_with_poe;
    }

    @Override
    public String toString()
    {
        return location;
    }
}
