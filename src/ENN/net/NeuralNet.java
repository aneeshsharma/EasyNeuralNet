/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENN.net;

import ENN.math.Function;
import ENN.net.activation.Sigmoid;
import java.io.PrintWriter;
import java.util.Random;
import processing.core.PApplet;

import static processing.core.PApplet.map;
import static processing.core.PConstants.*;
import processing.core.PFont;
import static ENN.net.ENNConstants.*;

/**
 * @author Aneesh Sharma
 * @version 2.5.1
 */
/**
 * Class for creating simple and easy to use neural networks.
 * <br>This version can create 3 layered neural networks. The coming versions would support multi-layer as well as many more features.
 * <br>You can also use the draw() method to draw the neural network on the screen
 * <br>IMPORTANT: You need to import the processing-3 library to use the drawing feature
 */
public class NeuralNet
{
    /**
     * double 2D array for storing weights from input layer to hidden layer
     * <br>NOTE: For weight of connection from input neuron i to hidden neuron j, use - w1[i][j]
     */
    public double w1[][];
    /**
     * double 2D array for storing weights of connections from hidden layer to output layer
     * <br>NOTE: For weight of connection from hidden neuron j to output neuron k, use - w2[i][j]
     */
    public double w2[][];
    /**
     * double 1D array for storing the inputs to input neuron
     * <br>NOTE: to access the nth input, use i[n]
     */
    public double i[];
    /**
     * double 1D array for storing the values of hidden neurons
     * <br>NOTE: to access the nth hidden neuron, use j[n]
     */
    public double j[];
    /**
     * double 1D array for storing the outputs of the network (output neurons)
     * <br>NOTE: to access the nth output, use k[n]
     */
    public double k[];
    /**
     * double 1D array to store the desired output for a given set of inputs as an example for backpropagation (Supervised Learning)
     * <br>RECOMMENDED: Use setRequired(double[]) to set the desired output for the already set input
     */
    public double d[];
    /**
     * double variable to store the Learning Rate for the network
     * <br>RECOMMENDED: choose the Learning Rate carefully according to your project and once set, don't change it during the learning process
     */
    public double LR; 
    /**
     * byte 1D array to store the output in binary form
     * <br>RECOMMENDED: use this as the true output and k[] as the confidence level.
     */
    public byte output[];
    /**
     * byte variable - Stores the size of the circular neuron when drawing. Set it according to your screen size
     */
    public byte size;
    /**
     * byte variable - Stores the text size for the displayed text
     */
    public byte _tSize;
    /**
     * integer variable - Stores number of input neurons
     */
    public int in;
    /**
     * integer variable - Stores number of hidden neurons
     */
    public int hid;
    /**
     * integer variable - Stores number of output neurons
     */
    public int out;
    /**
     * PFont object - Stores the font to be used while drawing the network on PApplet
     * <br>IMPORTANT: You need to import the processing-3 library to use the drawing feature
     */
    private PFont font;
    /**
     * PApplet object - Stores the PApplet object on which the network is drawn
     * <br>IMPORTANT: You need to import the processing-3 library to use the drawing feature
     */
    private PApplet parent;
    
    /**
     * The activation function for the neurons in the network
     * <br>CAN BE SET USING THE SET ACTIVATION METHOD
     */
    private Function activation;
    /**
     * Stores the weighted sum of the hidden neuron inputs
     */
    public double[] jSum;
    /**
     * Stores the weighted sum of the output neuron inputs
     */
    public double[] kSum;
    
    /**
     * Constructor for creating a Neural Network (which can be drawn on a PApplet)
     * @param _in number of inputs
     * @param _hid number of hidden neurons
     * @param _out number of outputs
     * @param _LR the learning rate for the neural network
     * @param _parent the PApplet for drawing (recommended to use for better training)
     */
    
    public NeuralNet(int _in, int _hid, int _out, double _LR, PApplet _parent)
    {
        parent=_parent;
        LR=_LR;
        in=_in+1;
        out=_out;
        hid=_hid+1;
        w1=new double[in][hid-1];
        w2=new double[hid][out];
        i=new double[in];
        j=new double[hid];
        k=new double[out];
        jSum=new double[hid-1];
        kSum=new double[out];
        output=new byte[out];
        i[in-1]=-1;
        j[hid-1]=-1;
        size=50;
        _tSize=22;
        if(parent!=null) font = parent.createFont("Courier New Bold",25);
        activation=new Sigmoid();
    }
    
    /**
     * Constructor for creating a Neural Network (which cannot be drawn on PApplet; for drawing use the constructor with PApplet argument)
     * @param _in number of inputs
     * @param _hid number of hidden neurons
     * @param _out number of outputs
     * @param _LR the learning rate for the neural network
     */
    
    public NeuralNet(int _in, int _hid, int _out, double _LR)
    {
        this(_in,_hid,_out,_LR,null);
    }
    
    /**
     * Constructor for creating a Neural Network from a saved file
     * @param file the file in which the neural network is saved (with '.swts' extension)
     * @param _parent the PApplet for drawing (recommended to use for better training)
     * NOTE: Do not mention any extension with file name. Only mention the file name
     * Exits if file not loaded
     */
    public NeuralNet(String file, PApplet _parent)
    {
      parent=_parent;
      try{
      String[] data;
      data=parent.loadStrings(file+".swts");
      in=Integer.parseInt(data[0])+1;
      hid=Integer.parseInt(data[1])+1;
      out=Integer.parseInt(data[2]);
      LR=Double.parseDouble(data[3]);
      w1=new double[in][hid-1];
      w2=new double[hid][out];
      i=new double[in];
      j=new double[hid];
      k=new double[out];
      jSum=new double[hid];
      kSum=new double[out];
      output=new byte[out];
      i[in-1]=-1;
      j[hid-1]=-1;
      size=50;
      _tSize=22;
      font = parent.createFont("Arial",25);
      int pos=4;
      activation=new Sigmoid();
      for(int m=0;m<in;m++)
          for(int n=0;n<hid-1;n++){
            w1[m][n]=Double.parseDouble(data[pos]);
            pos++;
          }
      for(int m=0;m<hid;m++)
          for(int n=0;n<out;n++){
            w2[m][n]=Double.parseDouble(data[pos]);
            pos++;
          }
      }
      catch(IndexOutOfBoundsException e)
      {
        System.out.println("Index Out Of Bounds Exception");
        parent.exit();
      }
      catch(Exception e)
      {
        System.out.println("An unexpected ERROR ocurred");
        parent.exit();
      }
    }
    
    /**
     * Method for setting the font used while drawing the neural network
     * @param font the font used while drawing the neural network (PFont)
     */
    public void setFont(PFont font)
    {
        this.font=font;
    }
    /**
     * Method for setting the activation function of the network
     * <br>You can implement a class from the Function interface for your own activation function
     * <br>Or you can use some of the already available activation functions from the ENN.net.activation package
     * @param _activation the activation function as Function object(from ENN.math package);
     */
    public void setActivation(Function _activation)
    {
        activation=_activation;
    }
    /**
     * Method for loading the weights from a file
     * @param file the file in which the neural network is saved (with '.swts' extension)
     * @return returns true if the file is successfully loaded or false
     */
    public boolean load(String file)
    {
      try{
      String[] data;
      data=parent.loadStrings(file);
      int pos=4;
      for(int m=0;m<in;m++)
          for(int n=0;n<hid-1;n++){
            w1[m][n]=Double.parseDouble(data[pos]);
            pos++;
          }
      for(int m=0;m<hid;m++)
          for(int n=0;n<out;n++){
            w2[m][n]=Double.parseDouble(data[pos]);
            pos++;
          }
      }
      catch(IndexOutOfBoundsException e)
      {
        System.out.println("Index Out Of Bounds Exception");
        return false;
      }
      catch(Exception e)
      {
        System.out.println("An unexpected ERROR ocurred");
        return false;
      }
      return true;
    }
    /**
     * Method for setting the required output for a given set of inputs
     * @param _d the array of the required outputs (should be of the same length as k[])
     */
    public void setRequired(double[] _d)
    {
        d=_d;
    }
    /**
     * Method for activating the neural network and calculating the output
     */
    public void activate()
    {
        for(int l=0;l<hid-1;l++){
            double[] x=new double[in];
            for(int m=0;m<in;m++)
                x[m]=w1[m][l]*i[m];
            jSum[l]=sigma(x);
            j[l]=activation.function(jSum[l]);
        }
        for(int l=0;l<out;l++){
            double[] x=new double[hid];
            for(int m=0;m<hid;m++)
                x[m]=w2[m][l]*j[m];
            kSum[l]=sigma(x);
            k[l]=activation.function(kSum[l]);
        }
    }
    /**
     * Method to set the weights of connections between neurons to random values
     * <br>IMPORTANT: To be used at initial stage ONLY. Not during the learning process
     */
    public void setWeights()
    {
        for(int l=0;l<hid-1;l++)
            for(int m=0;m<in;m++)
                w1[m][l]=Math.random();
        for(int l=0;l<out;l++)
            for(int m=0;m<hid;m++)
                w2[m][l]=Math.random();
    }
    /**
     * Method for performing the back propagation and updating the weights according to error gradients
     * <br>NOTE:Call activate method before calling backProp or use the 'learn' method for learning process
     */
    public void backProp()
    {
        double gk[] = new double[out];
        for(int n=0;n<gk.length;n++)
            gk[n]=activation.derivative(kSum[n])*(d[n]-k[n]);
        
        double gj[] = new double[hid-1];
        for(int m=0;m<gj.length;m++)
            gj[m]=activation.derivative(jSum[m])*weightedSum(w2[m],gk);
        
        for(int _i=0;_i<in;_i++)
            for(int _j=0;_j<hid-1;_j++)
                w1[_i][_j]+=(LR*i[_i]*gj[_j]);
        
        for(int _j=0;_j<hid;_j++)
            for(int _k=0;_k<out;_k++)
                w2[_j][_k]+=(LR*j[_j]*gk[_k]);
        
    }
    /**
     * Method to calculate the weighted sum
     * @param x the input values
     * @param y the weights to be multiplied by inputs
     * @return the weighted sum
     */
    private double weightedSum(double[] x, double[] y)
    {
        double sum=0; 
        for(int n=0;n<x.length;n++)
            sum+=(x[n]*y[n]);
        return sum;
    }
    /**
     * The mathematical function for summation
     * @param x the input array to the method for calculating the sum of the elements
     * @return the sum of the elements of array x[]
     */
    private double sigma(double[] x)
    {
        double sum=0;
        for(int n=0;n<x.length;n++)
            sum+=x[n];
        return sum;
    }
    /**
     * A method that draws the neural network on the screen(PApplet) within the area given as parameter
     * <br>NOTE: This method would not do anything if the PApplet is not provided
     * @param X1 The x-coordinate of the top left corner of the rectangular area of drawing
     * @param Y1 The y-coordinate of the top left corner of the rectangular area of drawing
     * @param X2 The x-coordinate of the bottom right corner of the rectangular area of drawing
     * @param Y2 The y-coordinate of the bottom right corner of the rectangular area of drawing
     */
    public void draw(int X1,int Y1, int X2, int Y2)
    {
      try{
      int[] _i=new int[in];
      int[] _j=new int[hid];
      int[] _k=new int[out];
      int m;
      parent.textFont(font,_tSize);
      for(m=0;m<in;m++)
      {
        _i[m]=(int)(map((float)(m+1),0f,(float)(in+1),Y1+(size/2),(float)(Y2)))-size;
        parent.ellipseMode(CENTER);
        parent.noStroke();
        parent.fill(255,0,0);
        parent.ellipse(X1+30,_i[m],size,size);
        parent.textAlign(CENTER);
        parent.fill(255);
        parent.textSize(18);
        parent.text(""+i[m],X1+30,_i[m]-(size/2));
      }
      for(m=0;m<hid;m++)
      {
        _j[m]=(int)(map((float)(m+1),0f,(float)(hid+1),(size/2),(float)(Y2)))-size;
        parent.ellipseMode(CENTER);
        parent.noStroke();
        parent.fill(0,255,0);
        parent.ellipse(((X2-X1)/2),_j[m],size,size);
        parent.textAlign(CENTER);
        parent.fill(255);
        parent.text(""+j[m],(X2-X1)/2,_j[m]-(size/2));
      }
      for(m=0;m<out;m++)
      {
        _k[m]=(int)(map((float)(m+1),0f,(float)(out+1),(size/2),(float)(Y2)))-size;
        parent.ellipseMode(CENTER);
        parent.noStroke();
        parent.fill(0,0,255);
        parent.ellipse(X2-30,_k[m],size,size);
        parent.textAlign(CENTER);
        parent.fill(255);
        parent.text(""+k[m],X2-30,_k[m]-(size/2));
      }
      parent.stroke(255);
      for(m=0;m<_i.length;m++)
        for(int n=0;n<_j.length-1;n++)
          parent.line(X1+30,_i[m],((X2-X1)/2),_j[n]);
      for(m=0;m<_j.length;m++)
        for(int n=0;n<_k.length;n++)
          parent.line(((X2-X1)/2),_j[m],X2-30,_k[n]);
      binCalc();
      parent.textAlign(LEFT);
      for(m=0;m<_k.length;m++){
        parent.line((X2-30+(size/2)),_k[m],X2+(size/2),_k[m]);
        parent.text(""+output[m],X2+(size/2)+5,_k[m]+5); 
      }
      }
      catch(NullPointerException e)
      {}
    }
    /**
     * Calculates the binary form of the output values in k[]
     * <br>If k is greater than 0.5 the output is 1 otherwise 0
     */
    public void binCalc()
    {
      for(int m=0;m<output.length;m++)
        output[m]=(byte)Math.round(k[m]);
    }
    /**
     * Saves the neural network to a file
     * <br>DO NOT MENTION THE FILE EXTENSION
     * @param fileName the name of the file in which the neural network is to be stored
     */
    public void save(String fileName)
    {
      PrintWriter file;
      file = parent.createWriter(fileName+".swts");
      file.println(in-1);
      file.println(hid-1);
      file.println(out);
      file.println(LR);
      int m;
      for(m=0;m<in;m++)
        for(int n=0;n<hid-1;n++)
          file.println(w1[m][n]);
      for(m=0;m<hid;m++)
        for(int n=0;n<out;n++)
          file.println(w2[m][n]);
      file.flush();
    }

    /**
     * Function to search for the least important weights
     * @return  The indices of the least important weights
     */

    private int[] leastImportant() {
        activate();
        double o1 = j[0];
        double t = w1[0][0];
        w1[0][0] = 0;
        activate();
        double o2 = j[0];
        w1[0][0] = t;
        double min = Math.abs(o2-o1);
        int[] i = {0, 0, 0, 0};

        for(int l = 0; l < hid - 1 ; l++)
            for(int m = 0; m < in; m++){
                activate();
                o1 = j[l];
                t = w1[m][l];
                w1[m][l] = 0;
                activate();
                o2 = j[l];
                w1[m][l] = t;

                if(Math.abs(o1-o2)<min) {
                    min = o1-o2;
                    i[0] = m;
                    i[1] = l;
                }
            }

        activate();
        o1 = k[0];
        t = w2[0][0];
        w2[0][0] = 0;
        activate();
        o2 = k[0];
        w2[0][0] = t;
        min = Math.abs(o2-o1);

        for(int l = 0; l < out ; l++)
            for(int m = 0; m < hid; m++){
                activate();
                o1 = k[l];
                t = w2[m][l];
                w2[m][l] = 0;
                activate();
                o2 = k[l];
                w2[m][l] = t;

                if(Math.abs(o1-o2)<min) {
                    min = o1-o2;
                    i[2] = m;
                    i[3] = l;
                }
            }

        return i;
    }

    /**
     * A method for mutating the neural network and return a new one. This works on the theory of evolution.
     * Use where you have MANY neural networks and back-propagation cannot be used. 
     * 
     * @return The mutated Neural Network
     */
    public NeuralNet mutateAll()
    {
        NeuralNet n = new NeuralNet(in-1, hid-1, out, LR);
        for(int l = 0; l < hid - 1 ; l++)
            for(int m = 0; m < in; m++)
                n.w1[m][l] = w1[m][l] + (Math.random() * 2 - 1) * LR;
        for(int l = 0; l < out; l++)
            for(int m = 0; m < hid; m++)
                n.w2[m][l] = w2[m][l] + (Math.random() * 2 - 1) * LR;
        return n;
    }

    /**
     * Mutates the least important weights in the network
     * <br>Mutates the least important wights between each layer
     * @return  The mutated Neural Network
     */

    public NeuralNet mutateLeast() {
        NeuralNet n = cloneNet();
        int[] x = leastImportant();
        n.w1[x[0]][x[1]] += (Math.random()*2 - 1) * LR * 10;
        n.w2[x[2]][x[3]] += (Math.random()*2 - 1) * LR * 10;
        return n;
    }

    /**
     * Function to cross over the genes of the Neural Network with a given Neural Network
     * to produce a child neural network. It also mutates the child.
     * (Use for Evolutionary Networks)
     * @param net   The other parent network (other than 'this')
     * @return  The child network
     */

    public NeuralNet cross(NeuralNet net){
        Random r = new Random();

        NeuralNet n = new NeuralNet(in-1, hid-1, out, LR);
        for(int l = 0; l < hid - 1 ; l++)
            for(int m = 0; m < in; m++)
                n.w1[m][l] = (r.nextBoolean()) ? w1[m][l] : net.w1[m][l];
        for(int l = 0; l < out; l++)
            for(int m = 0; m < hid; m++)
                n.w2[m][l] = (r.nextBoolean()) ? w2[m][l] : net.w2[m][l];
        return n;
    }

    /**
     * Function to mutate the neural network mith given mode
     * ALL      -   Mutates all the weights
     * SINGLE   -   Mutates the least important weight
     * @param MODE  The MODE of mutation
     * @return  The mutated neural network
     */

    public NeuralNet mutate(int MODE){
        switch (MODE){
            case ALL:
                return mutateAll();
            case SINGLE:
                return mutateLeast();
            default:
                return null;
        }
    }

    /**
     * Function to clone the neural network object to create a similar object
     * @return  The clone Neural Network
     */

    public NeuralNet cloneNet() {
        NeuralNet n = new NeuralNet(in-1, hid-1, out, LR);
        for(int l = 0; l < hid - 1 ; l++)
            for(int m = 0; m < in; m++)
                n.w1[m][l] = w1[m][l];
        for(int l = 0; l < out; l++)
            for(int m = 0; m < hid; m++)
                n.w2[m][l] = w2[m][l];
        return n;
    }
}
