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
    void addTwoVectors() throws Exception {
        double a[] = new double[] {1.0, 2.0, 4.0};
        double b[] = new double[] {-1.0, -2.0, 4.0};
        double r[];
        double exp[] = new double[] {0,0,8.0};
        r =tobject.addTwoVectors(a,b);
        for(int i=0; i<r.length; i++) {
            assertEquals(exp[i], r[i], 0.0001, " bad value at index " + i);
        }
    }

    @Test
    void addTwoVectorsDifferentLenght() {
        double a[] = new double[] {1.0, 2.0, 4.0};
        double b[] = new double[] {-1.0, -2.0, 4.0, 1.1};
        double r[];
        assertThrows(Exception.class, () ->{tobject.addTwoVectors(a,b);} );
    }

    @Test
    void scaleVectorMultiplyBy0() throws Exception{
        double a[] = new double[] {1.0, 2.0, 4.0};
        double r[];
        r = tobject.scaleVector(0,a);
        assertEquals(a.length,r.length,"bad lenght o resulting vector");
        for(int i=0; i<r.length; i++) {
            assertEquals(0, r[i], 0.0001, " bad value at index " + i);
        }
    }

    @Test
    void scaleVectorMultiplyBy2() throws Exception{
        double a[] = new double[] {1.0, 2.0, 4.0};
        double exp[] = new double[] {2.0, 4.0, 8.0};
        double r[];
        r = tobject.scaleVector(2,a);
        assertEquals(a.length,r.length,"bad lenght o resulting vector");
        for(int i=0; i<r.length; i++) {
            assertEquals(exp[i], r[i], 0.0001, " bad value at index " + i);
        }
    }

    @Test
    void scaleVectorMultiplyBy0_v2() throws Exception{
        double a[] = new double[] {1.0, 2.0, 4.0};
        double r[];
        tobject.scaleVectorChangeOriginal(0,a);
        for(int i=0; i<a.length; i++) {
            assertEquals(0, a[i], 0.0001, " bad value at index " + i);
        }
    }

    @Test
    void scaleVectorMultiplyBy2_v2() throws Exception{
        double a[] = new double[] {1.0, 2.0, 4.0};
        double exp[] = new double[] {2.0, 4.0, 8.0};
        double b[] = a;
        tobject.scaleVectorChangeOriginal(2,a);
        for(int i=0; i<a.length; i++) {
            assertEquals(exp[i], a[i], 0.0001, " bad value at index " + i);
        }
        assertSame(b,a);
    }

    //@Test
    void vectorLenght(){
        double a[] = new double[] {-1.0, 0.0};
        double r = tobject.vectorLenght(a);
        assertEquals(1,r);
    }


}