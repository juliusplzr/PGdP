package nonlinear_datastructs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    @DisplayName("Simple Graph - Integer")
    public void testSimpleIntegerGraphToString() {
        Graph<Integer> g = new Graph<>(false);

        for (int i = 0; i < 5; i++) {
            g.addVertex(i);
        }

        g.addEdge(0,1,1);
        g.addEdge(0,2,1);
        g.addEdge(1,2,1);
        g.addEdge(1,3,1);
        g.addEdge(2,4,1);
        g.addEdge(3,4,1);

        String out = "0 -> 1 (1) -> 2 (1)\n" + "|\n" + "1 -> 2 (1) -> 3 (1)\n" + "|\n" +
                     "2 -> 4 (1)\n" + "|\n" + "3 -> 4 (1)\n" + "|\n" + "4\n";

        assertEquals(out, g.toString());
    }
}