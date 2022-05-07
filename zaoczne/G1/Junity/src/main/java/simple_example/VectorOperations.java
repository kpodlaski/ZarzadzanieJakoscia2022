package simple_example;

public class VectorOperations {

    //Oba wektory a, b tej samej długości, jeśli nie zwracam null
    double[] addTwoVectors(double[] a, double[] b){
        if (a.length!= b.length)
            return null;
        double[] result = new double[a.length];
        for (int i=0; i< a.length; i++){
            result[i]=a[i]+b[i];
        }
        return result;
    }


}
