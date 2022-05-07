package simple_example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorOperationsTest {
    VectorOperations tobject;

    @BeforeEach
    void setUp() {
        tobject = new VectorOperations();
    }

    @AfterEach
    void tearDown() {
        tobject=null;
    }

    @Test
    void addTwoVectors() {
        double a[] = new double[] {1.0, 2.0, 4.0};
        double b[] = new double[] {-1.0, -2.0, 4.0};
        double r[];
        double exp[] = new double[] {0,0,8.0};
        r =tobject.addTwoVectors(a,b);
        for(int i=0; i<r.length; i++) {
            assertEquals(exp[i], r[i], 0.0001, " ba value at index " + i);
        }
    }

    @Test
    void addTwoVectorsDifferentLenght() {
        double a[] = new double[] {1.0, 2.0, 4.0};
        double b[] = new double[] {-1.0, -2.0, 4.0, 1.1};
        double r[];
        r =tobject.addTwoVectors(a,b);
        assertNull(r);
    }
}