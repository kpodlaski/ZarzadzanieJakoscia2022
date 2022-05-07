package coverter;

public class UnitConverter  {

    public float distanceMetersToMiles(float dist) throws Exception{
        if (dist<0) {
            throw new Exception("Negative input value");
        }
        return dist/1609.344f;
    }

}
