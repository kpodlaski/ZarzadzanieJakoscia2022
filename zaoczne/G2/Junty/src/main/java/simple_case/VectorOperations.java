package simple_case;

public class VectorOperations {

    public double[] add(double a[], double b[]) throws Exception{
        if (a.length != b.length)
            throw new Exception("Vectors have different size");
        double r[] = new double[a.length];
        for (int i=0; i<r.length; i++){
            r[i]=a[i]+b[i];
        }
        return r;
    }

    public double[] scale(double f, double[] a) {
        double r[] = new double[a.length];
        for (int i=0; i<a.length; i++){
            r[i] = f*a[i];
        }
        return r;
    }

    private double sum(double a[]){
        double r = 0.0;
        for (double el: a ) {
            r+=el;
        }
        return r;
    }

    public double[] mult(double a[], double b[])throws Exception{
        if (a.length != b.length)
            throw new Exception("Vectors have different size");
        double r[] = new double[a.length];
        for (int i=0; i<r.length; i++){
            r[i]=a[i]*b[i];
        }
        return r;
    }

    public double norm(double a[]) {
        double r =0;
        try {
            r = sum(mult(a, a));
        }
        finally {
            return Math.sqrt(r);
        }
    }

}
