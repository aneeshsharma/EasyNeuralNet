/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENN.math;

/**
 *
 * @author Aneesh Sharma
 */
/**
 * An interface for defining different types of mathematical functions
 * <br>Exclusively designed for use with the ENN package
 */
public interface Function {

    /**
     * A simple mathematical function 
     * @param x the input to the function
     * @return the value of the function
     */
    public double function(double x);
    /**
     * The mathematical derivative of the function(x)
     * @param x the input value for the derivative
     * @return the value of the derivative, that is, the slope of function(x) at x
     */
    public double derivative(double x);
}
