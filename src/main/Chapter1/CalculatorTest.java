package main.Chapter1;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    private Calculator cal;

    @Before
    public void setup(){
        cal = new Calculator();
    }

    @Test
    public void add() {
        assertEquals(9, cal.add(6,3));
    }

    @Test
    public void sub() {
        assertEquals(3, cal.subtract(6,3));
    }

    @After
    public void setdown(){
        System.out.println("종료");
    }
}
