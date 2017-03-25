/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENN.net.activation;

import ENN.math.Function;

/**
 *
 * @author Aneesh Sharma
 */
/**
 * A linear activation function for ENN
 */
public class Linear implements Function{
    
    /**
     *Name of function - LINEAR
     */
    public final String name="LINEAR";
    @Override
    public double function(double x) {
        if(x>=1)
            return 1;
        else if(x<=-1)
            return -1;
        else
            return x;
    }

    @Override
    public double derivative(double x) {
        return 1;
    }
    
}
