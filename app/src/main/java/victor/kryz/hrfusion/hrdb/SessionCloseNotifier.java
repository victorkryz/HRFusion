package victor.kryz.hrfusion.hrdb;

/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */


public interface SessionCloseNotifier {
    void onSessionClose(Session session);
}
