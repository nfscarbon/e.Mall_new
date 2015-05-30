package listner;

/**
 * Created by ratnav on 29-05-2015.
 */
public interface Listener_service {

    public void onRequestSuccess(int method,String response);
    public void onRequestFail(int method,String message);
    public void onStatus404();

}
