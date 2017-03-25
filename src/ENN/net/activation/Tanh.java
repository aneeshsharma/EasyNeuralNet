package ENN.net.activation;

import ENN.math.Function;

/**
 * Created by Aneesh Sharma on 21-03-2017.
 */
/**
 * A Hyperbolic Tangent activation function for ENN
 */
public class Tanh implements Function {

    /**
     *Name of function - HYPERBOLIC TANGENT
     */
    public final String name="HYPERBOLIC TANGENT";
    @Override
    public double function(double x) {
        return Math.tanh(x);
    }

    @Override
    public double derivative(double x) {
        return 1 - Math.pow(Math.tanh(x), 2);
    }

}
