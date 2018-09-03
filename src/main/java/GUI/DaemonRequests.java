package GUI;

import Data.DaemonRequest;

import java.util.Stack;

/**
 * Requests for the UI to complete.
 */
public class DaemonRequests
{
    private static Stack<DaemonRequest> requests = new Stack<>();

    public static void addRequest(DaemonRequest request)
    {
        requests.push(request);
    }

    public static Stack<DaemonRequest> getRequests()
    {
        return requests;
    }
}
