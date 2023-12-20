package nonlinear_datastructs;

import linear_datastructs.List;
import linear_datastructs.Queue;

import java.util.*;

/**
 * Implementation of graph data structure through adjacency list.
 * The adjacency list is a map that assigns each vertex its adjacent
 * vertices as list. The list is of type Edge<T> to where an edge contains
 * the end vertex and weight of an edge in this representation.
 *
 * @param <T>       generic type
 * @author          sirjulzz
 */
public class Graph<T> implements Iterable<T> {
    private Map<T, List<Edge<T>>> adjacencyList;
    private final boolean depthFirst;

    /**
     * Class constructor. Initializes a new adjacencyList.
     */
    public Graph(boolean depthFirst) {
        adjacencyList = new HashMap<>();
        this.depthFirst = depthFirst;
    }

    /** Returns true if this graph contains an edge connecting two specified
     * vertices. Runs in time linear in the size of the set of vertices.
     *
     * @param start vertex from which the directed edge departs
     * @param end   vertex into which the directed edge arrives
     * @return      true if this graph contains such an edge
     */
    public boolean hasEdge(T start, T end) {
        if (adjacencyList.isEmpty()) {
            return false;
        }

        if (!adjacencyList.containsKey(start)) {
            return false;
        }

        List<Edge<T>> adjacentToStart = adjacencyList.get(start);

        for (int i = 0; i < adjacentToStart.getSize(); i++) {
           if (adjacentToStart.get(i).getEndVertex().equals(end)) {
                return true;
           }
        }

        return false;
    }

    /** Returns the edge between two specified vertices.
     *
     * @param start                     vertex from which the directed edge departs
     * @param end                       vertex into which the directed edge arrives
     * @return                          edge between the two specified vertices
     * @throws IllegalArgumentException if no such edge exists
     */
    public Edge<T> getEdge(T start, T end) {
        if (!hasEdge(start, end)) {
            throw new IllegalArgumentException("Edge not found!");
        }

        List<Edge<T>> adjacentToStart = adjacencyList.get(start);

        for (int i = 0; i < adjacentToStart.getSize(); i++) {
            if (adjacentToStart.get(i).getEndVertex().equals(end)) {
                return adjacentToStart.get(i);
            }
        }

        return null;
    }

    /** Adds an edge between two specified vertices with certain weight. Returns
     * if edge is already included in this graph.
     * @param start                     vertex from which the directed edge departs
     * @param end                       vertex into which the directed edge arrives
     * @param weight                    weight of edge to be added
     * @throws IllegalArgumentException if one of the vertices is not included
     *                                  in this graph
     */
    public void addEdge(T start, T end, int weight) {
        if (!(adjacencyList.containsKey(start) && adjacencyList.containsKey(end))) {
            throw new IllegalArgumentException("Vertex not found!");
        }

        if (hasEdge(start, end)) {
            return;
        }

        Edge<T> edge = new Edge<>(end, weight);

        adjacencyList.get(start).add(edge);
    }

    /** Removes the edge between two specified vertices from this graph.
     * and returns true. Returns false if no such edge exists.
     *
     * @param start     vertex from which the directed edge departs
     * @param end       vertex into which the directed edge arrives
     */
    public boolean removeEdge(T start, T end) {
        if (!hasEdge(start, end)) {
            return false;
        }

        List<Edge<T>> adjacentToStart = adjacencyList.get(start);

        List<Edge<T>> removedAdjacent = new List<>();

        for (Edge<T> adj : adjacentToStart) {
            if (!adj.endVertex.equals(end)) {
                removedAdjacent.add(adj);
            }
        }

        adjacencyList.put(start, removedAdjacent);

        return true;
    }

    /** Adds a vertex to this graph
     *
     * @param vertex    vertex to be added
     */
    public void addVertex(T vertex) {
        if (adjacencyList.containsKey(vertex)) {
            return;
        }

        Map<T, List<Edge<T>>> nAdjacencyList = new HashMap<>(adjacencyList);
        nAdjacencyList.put(vertex, new List<>());
        adjacencyList = nAdjacencyList;
    }

    /** Removes a vertex from this graph, quits if vertex is not included in this
     * graph.
     *
     * @param vertex    vertex to be removed
     */
    public boolean removeVertex(T vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            return false;
        }

        Map<T, List<Edge<T>>> nAdjacencyList = new HashMap<>(adjacencyList);
        nAdjacencyList.remove(vertex);

        for (T v : nAdjacencyList.keySet()) {
            List<Edge<T>> nAdj = new List<>();

            for (T adj : getNeighbours(v)) {
                if (!adj.equals(vertex)) {
                    Edge<T> edge = getEdge(v, adj);
                    nAdj.add(edge);
                }
            }

            nAdjacencyList.put(v, nAdj);
        }

        this.adjacencyList = nAdjacencyList;

        return true;
    }

    /** Tests whether there is an edge between two vertices
     *
     * @param start     starting vertex
     * @param end       ending vertex
     * @return          true if such an edge included in this otherwise false
     */
    public boolean adjacent(T start, T end) {
        try {
            return getEdge(start, end) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /** Returns all direct neighbours of a vertex as List.
     *
     * @param vertex    Input vertex of which direct neighbours are sought
     * @return          List of all directly neighbouring vertices
     */
    public List<T> getNeighbours(T vertex) {
        if (adjacencyList.isEmpty()) {
            return new List<>();
        }

        if (!adjacencyList.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex not found!");
        }

        List<T> neighbours = new List<>();

        for (int i = 0; i < adjacencyList.get(vertex).getSize(); i++) {
            neighbours.add(adjacencyList.get(vertex).get(i).endVertex);
        }

        return neighbours;
    }

    /** Returns a depth-first/breadth-first search iterator.
     *
     * @return      DFS iterator if depthFirst true, BFS iterator otherwise
     */
    @Override
    public Iterator<T> iterator() {
        if (adjacencyList.isEmpty()) {
            throw new RuntimeException("Empty graph");
        }

        T topVertex = adjacencyList.keySet().iterator().next();

        if (depthFirst) {
            return new DFSIterator<>(this, topVertex);
        } else {
            return new BFSIterator<>(this, topVertex);
        }
    }

    /** Breadth-first search (BFS) explores all nodes at the present depth prior
     * to moving on to the vertices at the next depth level.
     *
     * @param <T>   generic type
     */
    public static class BFSIterator<T> implements Iterator<T> {

        /** Maintains visited vertices. */
        private Set<T> visited;

        /** Marks and stores vertices at the next depth level to be considered
         * for the next iteration. */
        private Queue<T> marked;

        /** Graph which is to be traversed. */
        private Graph<T> graph;

        /** Initializes a breadth-first search iterator.
         *
         * @param graph         graph which is to be traversed
         * @param startVertex   root vertex from which search is initiated
         */
        public BFSIterator(Graph<T> graph, T startVertex) {
            if (graph == null) {
                throw new IllegalArgumentException("Empty graph!");
            }

            if (!graph.adjacencyList.containsKey(startVertex)) {
                throw new IllegalArgumentException("Vertex not found!");
            }

            visited = new HashSet<>();
            marked = new Queue<>();
            this.graph = graph;

            marked.push(startVertex);
            visited.add(startVertex);
        }

        /** Returns true if the iteration has more elements. Iteration
         * has no more elements if queue of marked elements is empty.
         *
         * @return  true if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return !marked.isEmpty();
        }

        /** Returns the next element in the iteration. Push unvisited
         * vertices into marked and visited. Pop from queue for next
         * iteration.
         *
         * @return  next element in the iteration
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Next element not found!");
            }

            T next = marked.pop();

            for (T adjacent : graph.getNeighbours(next)) {
                if (!visited.contains(adjacent)) {
                    marked.push(adjacent);
                    visited.add(adjacent);
                }
            }

            return next;
        }
    }

    /** Depth-first search explores as far as possible along each branch before
     * backtracking.
     * @param <T>   generic type
     */
    public static class DFSIterator<T> implements Iterator<T> {
        private Set<T> visited;
        private Stack<T> marked;
        private Graph<T> graph;

        /** Class constructor. Initializes a depth-first search (DFS) iterator.
         *
         * @param graph         graph to be traversed through starting at
         * @param startVertex   vertex
         */
        public DFSIterator(Graph<T> graph, T startVertex) {
            if (graph == null) {
                throw new IllegalArgumentException("Empty graph!");
            }

            if (!graph.adjacencyList.containsKey(startVertex)) {
                throw new IllegalArgumentException("Vertex not found!");
            }

            visited = new HashSet<>();
            marked = new Stack<>();
            this.graph = graph;

            for (T adj : graph.getNeighbours(startVertex)) {
                marked.push(adj);
            }

            marked.push(startVertex);

            visited.add(startVertex);
        }

        /** Returns true if the iteration has more elements.
         *
         * @return  true if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return !marked.isEmpty();
        }

        /** Returns next element in the iteration.
         *
         * @return  next element in the iteration
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Next element not found!");
            }

            T next = marked.pop();

            if (!visited.contains(next)) {
                visited.add(next);

                for (T adj : graph.getNeighbours(next)) {
                    if (!visited.contains(adj)) {
                        marked.push(adj);
                    }
                }
            }

            return next;
        }
    }

    /** Iterative deepening depth-first search is a state space/graph search
     * strategy in which a depth-limited version of depth-first search is run
     * repeatedly with increasing depth limits until the goal is found.
     *
     * @param <T>   generic type
     */
    public static class IDDFSIterator<T> implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }
    }

    /**
     * Returns a String representation of this graph through adjacency list, where
     * all nodes are listed in chronological order in the first column and separated
     * by vertical bars. For each node all adjacent nodes are listed horizontally
     * separated by arrows mimicking a directed edge. Additionally, weights are added
     * in parentheses after each edge.
     *
     * @return      this adjacencyList as String
     */
    @Override
    public String toString() {
        if (adjacencyList.keySet().isEmpty()) {
            return "";
        }

        StringBuilder out = new StringBuilder();

        for (T key : adjacencyList.keySet()) {
            out.append(key.toString());

            ArrayList<T> adjacentAsList = new ArrayList<>(adjacencyList.keySet());

            for (T adjacent : getNeighbours(key)) {
                out.append(" -> ").append(adjacent.toString()).append(" (").
                        append(getEdge(key, adjacent).getWeight()).append(")");
            }

            if (!adjacentAsList.get(adjacencyList.size() - 1).equals(key)) {
                out.append("\n|\n");
            } else {
                out.append("\n");
            }
        }

        return out.toString();
    }

    /**
     * Returns a String representation to be used for online graph visualization.
     * Weights are NOT displayable.
     *
     * @return this adjacencyList as input String at
     * <a href="https://dreampuf.github.io/GraphvizOnline/">GraphViz Online</a>
     */
    public String toGraphviz() {
        StringBuilder out = new StringBuilder();
        out.append("digraph G {\n");

        for (T key : adjacencyList.keySet()) {
            out.append(key.toString()).append(" [shape=circle]\n");

            for (T adjacent : getNeighbours(key)) {
                out.append(key).append(" -> ").append(adjacent.toString()).append('\n');
            }
        }

        out.append("}\n");
        return out.toString();
    }

    /** Represents an edge through end vertex and weight as integer.
     *
     * @param <T>   generic type
     */
    public static class Edge<T> {
        private T endVertex;
        private int weight;

        /** Class constructor. Initializes a new edge.
         *
         * @param endVertex vertex to the end of the edge
         * @param weight    weight of edge
         */
        public Edge(T endVertex, int weight) {
            this.endVertex = endVertex;
            this.weight = weight;
        }

        public T getEndVertex() {
            return endVertex;
        }

        public void setEndVertex(T endVertex) {
            this.endVertex = endVertex;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return endVertex.toString() + " (" + weight + ")";
        }
    }

    public static final class DijkstraAlgorithm<T> {
        /** Implements Dijkstra's algorithm for shortest paths in graphs with
         * non-negative edges.
         *
         * @param graph     graph in which the shortest path is to be found from
         * @param start     starting vertex to
         * @param end       ending vertex
         * @return          arrayList of vertices included in path
         * @param <T>       generic type
         */
        public static <T> ArrayList<T> dijkstra(Graph<T> graph, T start, T end) {
            if (graph == null) {
                throw new IllegalArgumentException("Null graph!");
            }

            if (!(graph.adjacencyList.containsKey(start) && graph.adjacencyList.containsKey(end))) {
                throw new IllegalArgumentException("At least one of the vertices not found!");
            }

            PriorityQueue<Pair<T>> marked = new PriorityQueue<>();

            ArrayList<Pair<T>> pairs = new ArrayList<>();

            for (T vertex : graph.adjacencyList.keySet()) {
                if (vertex.equals(start)) {
                    Edge<T> edge = new Edge<>(null, 0);
                    Pair<T> pair = new Pair<>(vertex, edge);

                    pairs.add(pair);
                } else {
                    Edge<T> edge = new Edge<>(null, Integer.MAX_VALUE);
                    Pair<T> pair = new Pair<>(vertex, edge);

                    pairs.add(pair);
                }
            }

            Pair<T> current;

            while (!marked.isEmpty()) {
                current = marked.poll();

                if (current.edge.weight >= pairs.get(pairs.indexOf(new Pair<>(end,
                        new Edge<>(null, Integer.MAX_VALUE)))).edge.weight) {
                    break;
                }


                for (T val : graph.getNeighbours(current.getStart())) {
                    int weightVal = pairs.get(pairs.indexOf(new Pair<>(val,
                            new Edge<>(null, Integer.MAX_VALUE)))).edge.weight;
                    if (current.edge.getWeight() + 1 < weightVal) {
                        marked.remove(pairs.get(pairs.indexOf(new Pair<>(val,
                                new Edge<>(null, Integer.MAX_VALUE)))));
                    }
                }


            }

            return null;
        }
    }

    private static class Pair<T> implements Comparable<Pair<T>> {
        private T start;
        private Edge<T> edge;

        public Pair(T start, Edge<T> edge) {
            this.start = start;
            this.edge = edge;
        }

        public T getStart() {
            return start;
        }

        @Override
        public int compareTo(Pair<T> other) {
            return edge.weight - other.edge.weight;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }

            Pair<T> other = (Pair<T>) obj;
            return edge.weight == other.edge.weight && start == other.start;
        }

        @Override
        public int hashCode() {
            return Objects.hash(edge.weight, start);
        }
    }

    public static void main(String[] args) {
        Graph<Integer> adjList1 = new Graph<>(false);

        adjList1.addVertex(0);
        adjList1.addVertex(1);
        adjList1.addVertex(2);
        adjList1.addVertex(3);
        adjList1.addVertex(4);

        adjList1.addEdge(0, 1,1);
        adjList1.addEdge(0,2,1);
        adjList1.addEdge(1,2,1);
        adjList1.addEdge(1,3,1);
        adjList1.addEdge(2,4,1);
        adjList1.addEdge(3,4,1);

        System.out.println(adjList1);
        System.out.println(adjList1.toGraphviz());

        System.out.println("<================ Iterator Test ================>");

        BFSIterator<Integer> bfs = new BFSIterator<>(adjList1, 0);

        StringBuilder out = new StringBuilder();
        out.append("Vertices traversed (BFS): [");

        while (bfs.hasNext()) {
            out.append(bfs.next()).append(" -> ");
        }

        System.out.println(out.substring(0, out.lastIndexOf(" -")) + "]");

        DFSIterator<Integer> dfs = new DFSIterator<>(adjList1, 0);

        StringBuilder sb = new StringBuilder();
        sb.append("Vertices traversed (DFS): [");

        while (dfs.hasNext()) {
            sb.append(dfs.next()).append(" -> ");
        }

        System.out.println(sb.substring(0, sb.lastIndexOf(" -")) + "]");
    }
}