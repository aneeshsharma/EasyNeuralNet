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
 * A step activation function for ENN
 */
public class Step implements Function{
    
    /**
     *Name of function - STEP
     */
    public final String name="STEP";
    @Override
    public double function(double x) {
        return (x>=0)?1:0;
    }

    @Override
    public double derivative(double x) {
        return 1;
    }
    
}
