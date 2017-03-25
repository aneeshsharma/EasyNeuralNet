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
 * A sigmoid activation function for ENN
 */
public class Sigmoid implements Function {

    /**
     *Name of function - SIGMOID
     */
    public final String name="SIGMOID";
    @Override
    public double function(double x)
    {
        return (1/((Math.pow(Math.E,-x))+1));
    }

    @Override
    public double derivative(double x) {
        return function(x)*(1-function(x));
    }
}
