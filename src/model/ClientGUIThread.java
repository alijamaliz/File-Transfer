package model;

/**
 * Created by Baran on 5/12/2017.
 */
public class ClientGUIThread extends Thread {
    private Sender sender;
    private long timeBefore;
    public ClientGUIThread(Sender sender) {
        this.sender = sender;
        timeBefore = System.nanoTime();
    }
    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                sender.clientGUI.setProgressBarValue(sender.getPercentage());
                sender.clientGUI.setPercentTextFieldText((int) (sender.getPercentage() * 100));
                sender.clientGUI.setSizeTextFieldText(sender.sentBytes, (int) sender.fileSize);
                sender.clientGUI.setSpeedTextFieldText(calculateSpeed());
                sleep(500);
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }
    public int calculateSpeed() {
        if(((System.nanoTime() - timeBefore) / 1000000000) == 0)
            return 0;
        else
            return (int) (sender.sentBytes / ((System.nanoTime() - timeBefore) / 1000000000));
    }
}
