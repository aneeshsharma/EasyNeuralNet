# EasyNeuralNet
This is a java library to implement simple 3 layered Neural Networks. It is an easy to use library and is quite simple.
I am still developing it but the backpropagation algorithm is fully functional.

# Structure

The NeuralNet uses 3 arrays for representing the individual 3 layers of the network -
<ul>
<li>double[] i - for the input layer</li>
<li>double[] j - for the hidden layer</li>
<li>double[] k - for the output layer</li>
</ul>

The weights of the connections are stored in a two 2-D arrays named w1 and w2.<br>

<ul>
<li>double[][] w1 - Stores the weights from layer i to layer j<br>
w1[i][j] - represents weight from input i to hidden j</li>
<li>double[][] w2 - Stores the weights from layer j to layer k<br>
w2[j][k] - represents weight from hidden j to output k</li>
</ul>

One bias input is included in input layer as a -1 input and one in the hidden layer with value -1.
