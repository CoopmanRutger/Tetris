package game.info;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimerTest {

    @Test
    public void run() {
        Timer timer = new Timer(20);
        timer.run();
    }
}