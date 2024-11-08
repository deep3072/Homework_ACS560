import org.junit.*;
import org.junit.rules.ExpectedException;

public class ClockTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConstructor_withNegativeValues_shouldThrowIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Negative values not permitted");
        new Clock(-1, 0, 0);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_withNegativeMinutes_shouldThrowIllegalArgumentException() {
        new Clock(0, -1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_withNegativeSeconds_shouldThrowIllegalArgumentException() {
        new Clock(0, 0, -1);
    }
    @Test
    public void testGetInitialTime_shouldReturnCorrectTime() {
        Clock clock = new Clock(12, 0, 0);
        Assert.assertEquals("12:00:00", clock.get24HourFormat());
    }
    @Test
    public void testAddHour_shouldIncrementHour() {
        Clock clock = new Clock(12, 0, 0);
        clock.addHour();
        Assert.assertEquals("13:00:00", clock.get24HourFormat());
    }

    @Test
    public void testAddMinute_shouldIncrementMinute() {
        Clock clock = new Clock(12, 0, 0);
        clock.addMinute();
        Assert.assertEquals("12:01:00", clock.get24HourFormat());
    }

    @Test
    public void testAddSecond_shouldIncrementSecond() {
        Clock clock = new Clock(12, 0, 0);
        clock.addSecond();
        Assert.assertEquals("12:00:01", clock.get24HourFormat());
    }

    @Test
    public void testGet12HourFormat_shouldReturnCorrectFormat() {
        Clock clock = new Clock(13, 0, 0);
        Assert.assertEquals("1:00:00 PM", clock.get12HourFormat());
    }

    @Test
    public void testGet12HourFormatAtMidnight_shouldReturnCorrectFormat() {
        Clock clock = new Clock(0, 0, 0);
        Assert.assertEquals("12:00:00 AM", clock.get12HourFormat());
    }

    @Test
    public void testGet12HourFormatAtNoon_shouldReturnCorrectFormat() {
        Clock clock = new Clock(12, 0, 0);
        Assert.assertEquals("12:00:00 PM", clock.get12HourFormat());
    }

    @Test
    public void testAddHourWrapAround_shouldResetToZero() {
        Clock clock = new Clock(23, 0, 0);
        clock.addHour();
        Assert.assertEquals("00:00:00", clock.get24HourFormat());
    }

    @Test
    public void testAddMinuteWrapAround_shouldIncrementHour() {
        Clock clock = new Clock(12, 59, 0);
        clock.addMinute();
        Assert.assertEquals("13:00:00", clock.get24HourFormat());
    }

    @Test
    public void testAddSecondWrapAround_shouldIncrementMinute() {
        Clock clock = new Clock(12, 0, 59);
        clock.addSecond();
        Assert.assertEquals("12:01:00", clock.get24HourFormat());
    }
    
}