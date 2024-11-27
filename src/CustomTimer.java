import java.util.Timer;
import java.util.TimerTask;

public class CustomTimer {
    private Timer timer;
    private int secondsElapsed = 0;

    public CustomTimer() {
        timer = new Timer();
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                secondsElapsed++;
            }
        }, 0, 1000); // Mise à jour toutes les secondes
    }

    public int getSecondsElapsed() {
        return secondsElapsed;
    }

    public void stop() {
        timer.cancel();
    }
}
