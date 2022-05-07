package coverter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class UnitConverterTest {

    private UnitConverter target;
    private double factor = 1609.344;
    @BeforeEach
    void setUp() {
        target = new UnitConverter();
    }

    @AfterEach
    void tearDown() {
        target = null;
    }

    @Test
    void distanceMetersToMiles100Meters() throws Exception{
        float obtained = target.distanceMetersToMiles(100);
        float expected = (float) (100/factor) ;
        assertEquals(expected, obtained, 0.0001);
    }

    @Test
    void distanceMetersToMiles1K() throws Exception{
        float obtained = target.distanceMetersToMiles(1000);
        float expected = (float) (1000/factor) ;
        assertEquals(expected, obtained, 0.0001);
    }

    @Test
    void distanceMetersToMiles() throws Exception{
        Random rand = new Random();
        for (int i=0; i<1000; i++) {
            float x = rand.nextFloat()*10000;
            float obtained = target.distanceMetersToMiles(x);
            float expected = (float) (x / factor);
            assertEquals(expected, obtained, 0.0001, "Error for x="+x);
        }
    }

    @Test()
    void distanceMetersToMilesNegative() throws Exception {
        assertThrows(Exception.class, () -> {target.distanceMetersToMiles(-1);});
    }

}