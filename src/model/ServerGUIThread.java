package model;

/**
 * Created by Baran on 5/12/2017.
 */
public class ServerGUIThread extends Thread {

    private Listener listener;
    public ServerGUIThread(Listener listener) {
        this.listener = listener;
    }

    public void run() {
        try {
            while (!isInterrupted()) {
                listener.serverGUI.setProgressBarValue(listener.serverGUI.getProgressBarValue());
                listener.serverGUI.setPercentTextFieldText((int)(listener.serverGUI.getProgressBarValue()  * 100));
                listener.serverGUI.setNumberOfClientsText(listener.serverGUI.listeners.size() - 1);
                sleep(500);
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }
}
