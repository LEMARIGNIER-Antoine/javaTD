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
        }, 0, 1000); // update 1 s
    }

    public int getSecondsElapsed() {
        return secondsElapsed;
    }

    public void stop() {
        timer.cancel();
    }
}
