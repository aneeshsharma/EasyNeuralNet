package ENN.net.activation;

/**
 * Created by Aneesh Sharma on 30-03-2017.
 */

import ENN.math.Function;

/**
 * @author Aneesh Sharma
 */

/**
 * A linear activation function for ENN
 */
public class Active implements Function {

    /**
     *Name of function - LINEAR
     */
    public final String name = "LINEAR";

    @Override
    public double function(double x) {
        if (x >= 1)
            return 1;
        else if (x <= -1)
            return -1;
        else
            return x;
    }

    @Override
    public double derivative(double x) {
        return 1;
    }

}