# EasyNeuralNet
This is a well documented java library to implement simple 3 layered Neural Networks. It is an easy to use library and is quite simple.
I am still developing it but the backpropagation algorithm is fully functional.

You can also link this to a Processing sketch and draw the entire network to your screen and watch the individual layers and their values and the outputs.

After training the network, you can save the weights to a file and load those weights again the next time. This helps you to train the network and then use the trained network multiple times to perform particular tasks. Also, you could this helps you to resume the learning process from where it left.

<h4>Documentation can be found <a href="https://aneeshsharma.github.io/EasyNeuralNet/docs/">HERE</a></h4>
<h4>Download The compiled library <a href="https://aneeshsharma.github.io/EasyNeuralNet/docs/">HERE</a></h4>

<h2>Structure</h2>

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
