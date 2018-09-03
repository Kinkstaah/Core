package API.FilterBlast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 */
public class FilterBlast
{
    private static final String FILTER_LIST = "https://filterblast.oversoul.xyz/api/ListFilters/";

    public static ArrayList<FilterBlastFilter> downloadListOfFilters()
    {
        ArrayList<FilterBlastFilter> list = new ArrayList<>();
        try
        {
            URL url = new URL(FILTER_LIST);

            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");

            InputStream inputStream = httpcon.getInputStream();

            String foo = convertStreamToString(httpcon.getInputStream());
            // Remove hidden character from the start.
            foo = findValidString(foo);

            //System.out.println(foo);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(foo);
            Iterator<JsonNode> elements = node.elements();
            for (Iterator<JsonNode> it = elements; it.hasNext(); )
            {
                ArrayList<String> variations = new ArrayList<>();
                JsonNode n = it.next();

                Iterator<JsonNode> presets = n.get("Presets").elements();
                for (Iterator<JsonNode> it1 = presets; it1.hasNext(); )
                {
                    JsonNode no = it1.next();
                    variations.add(no.toString().replace("\"", ""));
                }

                FilterBlastFilter f = new FilterBlastFilter(n.get("Name").toString(), n.get("Version").toString(), n.get("LastUpdate").toString(), n.get("PoEVersion").toString(), n.get("ForumThread").toString(), variations);
                if (!f.getName().equals("PoE Default Filter"))
                {
                    System.out.println(f.toString());
                    list.add(f);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Filterblast has some weird things where it adds some weird characters we're gonna try to attempt to remove them.
     * @return
     */
    public static String findValidString(String in)
    {
        int count = 0;
        for (int c = 0; c < in.length(); c++)
        {
            if (in.charAt(c) == '{')
            {
                break;
            }
            else
            {
                count++;
            }
        }
        return in.substring(count);
    }

    public static String convertStreamToString(java.io.InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
