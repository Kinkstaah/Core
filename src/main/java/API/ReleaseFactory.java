package API;

import Backend.Release;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;

/**
 *
 */
public class ReleaseFactory
{
    public static Release createReleaseFromJsonNode(JsonNode jsonNode)
    {
        if (jsonNode == null)
            return null;

        JsonNode name = jsonNode.get("name");
        String version = name.toString().replace("\"", "");
        JsonNode assets = jsonNode.get("assets");
        Iterator<JsonNode> elements = assets.elements();

        if (elements.hasNext())
        {
            JsonNode zero = elements.next();
            String _name = zero.get("name").toString().replace("\"", "");
            String download_url = zero.get("browser_download_url").toString().replace("\"", "");
            String temp = _name;
            int num = Integer.parseInt(temp.replace("b", "").replace(".jar", ""));

            return new Release(_name, version, download_url, num);
        }
        return null;
    }

    //TODO: Some method that only makes 8 calls every minute.
    // 25 if a token is set.
    // Need to use some caching aswell.
}
