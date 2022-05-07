package simple_case;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorOperationsTest {

    VectorOperations testObject;

    @BeforeEach
    void initTest(){
        testObject = new VectorOperations();
    }

    @AfterEach
    void tearDown(){
        testObject = null;
    }

    @Test
    void add() throws Exception {
        double a[] = new double[] {1.0, -1.2, 2.1};
        double b[] = new double[] {1.0, 1.2, 2.1};
        double exp[] = new double[] {2.0, 0, 4.2};
        double r[] = testObject.add(a,b);
        for (int i=0; i<a.length; i++){
            assertEquals(exp[i], r[i], 0.0001, "Error on index "+i);
        }
    }

    @Test
    void add_v2() throws Exception {
        double a[] = new double[] {1.0, -1.2, 2.1};
        double b[] = new double[] {1.0, 1.2, 2.1, 1.1};
        assertThrows(Exception.class, () -> { testObject.add(a,b); });
    }

    @Test
    void scaleVector(){
        double a[] = new double[] {1.0, -1.2, 2.1};
        double f = 0;
        double exp[] =new double[] {0.0, 0.0, 0.0};
        double r[] = testObject.scale(f,a);
        assertArrayEquals(exp, r, 0.0001);
    }

    @Test
    void scaleVector_v2(){
        double a[] = new double[] {1.0, -1.2, 2.1};
        double f = -2;
        double exp[] =new double[] {-2.0, 2.4, -4.2};
        double r[] = testObject.scale(f,a);
        assertArrayEquals(exp, r);
    }

    @Test
    void norm(){
        double a[] = new double[] {-1.0, 0.0};
        double r = testObject.norm(a);
        assertEquals(1,r);
        a = new double[] {0.0, 1.0};
        r = testObject.norm(a);
        assertEquals(1,r);
        a = new double[] {-1.0, 1.0};
        r = testObject.norm(a);
        assertEquals(Math.sqrt(2),r);
    }
}