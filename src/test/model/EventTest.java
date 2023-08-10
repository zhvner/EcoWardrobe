package model;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the Event class
 * author: CPSC 210
 * author: Zhanerke Zhumash
 */
public class EventTest {
    private Event e;
    private Date d;
    private Event event1;
    private Event event2;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("Sensor open at door");   // (1)
        d = Calendar.getInstance().getTime();   // (2)

        event1 = new Event("Description 1");
        event2 = new Event("Description 1");
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e.getDescription());
        assertTrue(d.getTime() - e.getDate().getTime() <= 10);
    }

    @Test
    public void testEquals() {
        assertFalse(e.equals(null));
        assertFalse(e.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        assertEquals(
                new Event("Sensor open at door").hashCode(),
                new Event("Sensor open at door").hashCode()
        );
    }

    @Test
    public void testEqualsWithSameEvent() {
        assertTrue(event1.equals(event1));
    }

    @Test
    public void testEqualsWithEqualEvents() {
        assertTrue(event1.equals(event2));
    }

    @Test
    public void testEqualsWithDifferentEvents() {
        Event event3 = new Event("Different Description");
        assertFalse(event1.equals(event3));
    }

    @Test
    public void testEqualsWithDifferentObject() {
        assertFalse(event1.equals("Some other object"));
    }

    @Test
    public void testEqualsWithNull() {
        assertFalse(event1.equals(null));
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
    }
}
