package simple_example;

public class VectorOperations {

    //Oba wektory a, b tej samej długości, jeśli nie zwracam null
    public double[] addTwoVectors(double[] a, double[] b) throws Exception {
        if (a.length!= b.length)
            throw new Exception("Two vectors of differen lenght");
        double[] result = new double[a.length];
        for (int i=0; i< a.length; i++){
            result[i]=a[i]+b[i];
        }
        return result;
    }

    public double[] scaleVector(double f, double a[]){
        double r[] = new double[a.length];
        for (int i=0; i<r.length; i++){
            r[i]=f*a[i];
        }
        return r;
    }

    public void scaleVectorChangeOriginal(double f, double a[]){
        for (int i=0; i<a.length; i++){
            a[i]=f * a[i];
        }
    }

    private double sumOfVectorElements(double a[]){
        double s = 0;
        for ( double e: a) {
            s+=e;
        }
        return s;
    }

    public double[] multiplyTwoVectors(double a[], double b[]){
        double r[] = new double[a.length];
        for (int i=0; i<a.length; i++){
            r[i]=a[i]*b[i];
        }
        return r;
    }

    public double vectorLenght(double a[]){
        double r[] = multiplyTwoVectors(a,a);
        double s = sumOfVectorElements(r);
        return Math.sqrt(s);
    }
}
