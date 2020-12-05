// Name:        Foysal Ahmed

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/*
 * the process is as follows
 * prepare a collection of elements (toProcess) with all the nodes in it and the value set to infinity
 * from that on each iteration we are supposed to pick a node with min value. for first case we will pick out the source node and set it's value to zero
 * 
 * each removed node will move to the to processed node. the same will be entered in the parent collection.
 * for each removed node we will check it's neighbors and calculate the neighboring node distance and then compare it with the value stores in toProcess collection. if the value is less the same is updated in the toProcess along with it's appropriate update in parent collection
 * during  next iteration we will again pick up the node with minimum value and keep on continuing until the toProcess collection is empty or there is no possible way to reach from start to end node which will be determined when the minimum node found in toProcess collection is at -1 index
 */
public class Dijkstra {

    private int source;
    private int destination;
    private int removedNode;
    private double[][] graph;
    private boolean stuck = false;
    private HashMap<Integer, Double> toProcess = new HashMap<Integer, Double>();
    private HashMap<Integer, Double> processed = new HashMap<Integer, Double>();
    private HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> blockedPath;
    private HashMap<Integer, Integer> finalParentPath = new HashMap<Integer, Integer>();

    public Dijkstra(double[][] graph, int source, int destination, HashMap<Integer, Integer> blockedPath) {
        this.graph = graph;
        this.source = source;
        this.destination = destination;
        this.blockedPath = blockedPath;
    }

    /**
     * find the element with the lowest distance value
     *
     * @return
     */
    private int findMinimumDist() {
        int minIndex = -1;
        double minValue = Integer.MAX_VALUE;

        Set<Integer> keys = toProcess.keySet();
        for (Integer key : keys) {
            if (toProcess.get(key) < minValue) {
                minValue = toProcess.get(key);
                minIndex = key;
            }
        }

        return minIndex;
    }

    /**
     * explore the neighbors of the removed node and update the distance in the
     * toProcess map
     */
    private void exploreNeighbors() {
        for (int index = 0; index < graph.length; index++) {
            if (index == removedNode) {
                continue;
            }

            if (graph[removedNode][index] == 0) {
                continue;
            }

            if (toProcess.containsKey(index) && (processed.get(removedNode) + graph[removedNode][index] < toProcess.get(index))) {
                toProcess.put(index, processed.get(removedNode) + graph[removedNode][index]);
                parent.put(index, removedNode);
                //System.out.println("Updating Parent of " + index + " to " + removedNode);
            }
        }
    }

    public void evaluate() {
        try {
            // first set all the toProcess Nodes to infinity
            for (int index = 0; index < graph.length; index++) {
                if (!blockedPath.containsKey(index)) {
                    toProcess.put(index, (double) Integer.MAX_VALUE);
                }
            }

            // remove the source node first, set the distance to zero and add to processed list
            toProcess.remove(source);
            processed.put(source, 0d);
            parent.put(source, -1); // since it's the source
            removedNode = source;

            // continue the following until toProcess is not empty
            while (!toProcess.isEmpty()) {
				//System.out.println("Removed " + removedNode);

                if (removedNode == -1) {
                    break;
                }
                // find neighbors of recently removed node
                exploreNeighbors();

                // find the node with minimum value
                int minNode = findMinimumDist();
                processed.put(minNode, toProcess.get(minNode));
                toProcess.remove(minNode);
                //System.out.println("Size " + toProcess.size());
                removedNode = minNode;
            }

			//System.out.println(processed.get(destination));
            // fetch parent list
            int currParent = parent.get(destination);
            while (currParent != -1) {
                finalParentPath.put(currParent, 0);
                currParent = parent.get(currParent);
            }
            finalParentPath.put(destination, 0); // just add the destination node in the list
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
    }

    public HashMap<Integer, Integer> fetchPath() {
        return this.finalParentPath;
    }

    public double fetchDistance() {
        double dist = -1;
        if (this.processed.containsKey(destination)) {
            return this.processed.get(destination);
        }
        return dist;
    }

    public boolean getStuck() {
        return this.stuck;
    }
}
