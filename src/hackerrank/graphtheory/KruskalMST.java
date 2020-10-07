package hackerrank.graphtheory;

import java.io.*;
import java.util.*;
import java.util.stream.*;

class KruskalMST {

    /*
     * Complete the 'kruskals' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts WEIGHTED_INTEGER_GRAPH g as parameter.
     */

    /*
     * For the weighted graph, <name>:
     *
     * 1. The number of nodes is <name>Nodes.
     * 2. The number of edges is <name>Edges.
     * 3. An edge exists between <name>From[i] and <name>To[i]. The weight of the edge is <name>Weight[i].
     *
     */

    public static class Edge{
        public Integer source;
        public Integer destination;

        public Edge(Integer src, Integer dest){
            this.source = src;
            this.destination = dest;
        }
    }

    public static class Item{
        public Edge edge;
        public Integer weight;

        public Item(Edge edge, Integer wt){
            this.edge = edge;
            this.weight = wt;
        }

    }

    public static Integer find(Integer i, Integer[] parent){
        if(parent[i] == -1){
            return i;
        }
        return find(parent[i], parent);
    }


    public static void union(Integer a, Integer b, Integer[] parent){
        Integer xSet = find(a, parent);
        Integer ySet = find(b, parent);
        parent[xSet] = ySet;
    }

    static class ItemComparator implements Comparator<Item> {
        @Override
        public int compare(Item a, Item b){
            if(a.weight.equals(b.weight)){
                Integer x = a.weight+ a.edge.source + a.edge.destination;
                Integer y = b.weight+ b.edge.source + b.edge.destination;
                return x.compareTo(y);
            }
            return a.weight.compareTo(b.weight);
        }
    }


    public static int kruskals(int gNodes, List<Integer> gFrom, List<Integer> gTo, List<Integer> gWeight) {

        ArrayList<Item> itemList = new ArrayList<>();

        Integer[] parent = new Integer[gNodes+1];


        for(int i=1;i<=gNodes;i++){
            parent[i] = -1;
        }

        for(int i=0;i<gFrom.size();i++){
            itemList.add(new Item(new Edge(gFrom.get(i), gTo.get(i)), gWeight.get(i)));
        }

        itemList.sort(new ItemComparator());

        int mst = 0;
        int counter = 0;
        for(Item item : itemList){
            Edge edge = item.edge;
            if(!find(edge.source, parent).equals(find(edge.destination, parent)) && counter < gNodes - 1){
                mst+=item.weight;
                counter++;
                union(edge.source, edge.destination, parent);
            }
        }

        return mst;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] gNodesEdges = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int gNodes = Integer.parseInt(gNodesEdges[0]);
        int gEdges = Integer.parseInt(gNodesEdges[1]);

        List<Integer> gFrom = new ArrayList<>();
        List<Integer> gTo = new ArrayList<>();
        List<Integer> gWeight = new ArrayList<>();

        IntStream.range(0, gEdges).forEach(i -> {
            try {
                String[] gFromToWeight = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                gFrom.add(Integer.parseInt(gFromToWeight[0]));
                gTo.add(Integer.parseInt(gFromToWeight[1]));
                gWeight.add(Integer.parseInt(gFromToWeight[2]));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int res = kruskals(gNodes, gFrom, gTo, gWeight);
        bufferedWriter.write(String.valueOf(res));
        // Write your code here.

        bufferedReader.close();
        bufferedWriter.close();
    }
}

// input:
//4 6
//1 2 5
//1 3 3
//4 1 6
//2 4 7
//3 2 4
//3 4 5
// output:
//12


