import java.util.*;

class Node {
    int[] state;
    Node parent;
    int moves;

    public Node(int[] state, Node parent, int moves) {
        this.state = state;
        this.parent = parent;
        this.moves = moves;
    }

    public int[] getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public int getMoves() {
        return moves;
    }
}

public class Main {

    // Función para generar un estado aleatorio del juego 8 puzzle
    public static int[] generateRandomState() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        Collections.shuffle(numbers);
        int[] state = new int[9];
        for (int i = 0; i < 9; i++) {
            state[i] = numbers.get(i);
        }
        return state;
    }

    // Imprime la matriz del juego 8 puzzle
    public static void printState(int[] state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(state[i * 3 + j] + " ");
            }
            System.out.println();
        }
    }

    // Función para encontrar el camino desde el estado inicial al estado objetivo
    public static boolean findSolution(int[] initialState, int[] targetState) throws InterruptedException {
        Queue<Node> queue = new LinkedList<>();
        Set<String> visitedStates = new HashSet<>();
        Node initialNode = new Node(initialState, null, 0);
        queue.add(initialNode);
        visitedStates.add(Arrays.toString(initialState));

        int maxQueueSize = queue.size(); // Inicializamos el tamaño máximo de la cola con su tamaño actual
        int expandedNodes = 0;

        while (!queue.isEmpty()) {
            Node currentNode = queue.remove();
            int[] currentState = currentNode.getState();

            if (Arrays.equals(currentState, targetState)) {
                System.out.println("\n¡Estado objetivo encontrado!");
                System.out.println("Cantidad de movimientos realizados: " + currentNode.getMoves());
                System.out.println("Nodos expandidos: " + expandedNodes);
                System.out.println("Espacio recorrido (tamaño máximo de la cola): " + maxQueueSize);
                System.out.println("\nRecorrido de movimientos:");
                printMoves(currentNode);
                return true;
            }

            for (Node succ : successors(currentNode)) {
                int[] succState = succ.getState();
                if (!visitedStates.contains(Arrays.toString(succState))) {
                    queue.add(succ);
                    visitedStates.add(Arrays.toString(succState));
                    maxQueueSize = Math.max(maxQueueSize, queue.size());
                    expandedNodes++; // Incrementamos el contador de nodos expandidos aquí
                }
            }
        }

        System.out.println("\nNo se encontró un estado objetivo.");
        return false;
    }

    // Función para imprimir el recorrido de movimientos desde el estado inicial hasta el estado objetivo
    public static void printMoves(Node node) {
        List<Node> moves = new ArrayList<>();
        while (node != null) {
            moves.add(node);
            node = node.getParent();
        }
        for (int i = moves.size() - 1; i >= 0; i--) {
            System.out.println("\nMovimiento " + (moves.size() - i - 1) + ":");
            printState(moves.get(i).getState());
        }
    }

    // Función para generar los sucesores de un nodo dado
    public static List<Node> successors(Node node) {
        List<Node> successors = new ArrayList<>();
        int[] state = node.getState();
        int zeroIndex = 0;
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                zeroIndex = i;
                break;
            }
        }
        if (zeroIndex / 3 > 0) {
            int[] newState = Arrays.copyOf(state, state.length);
            newState[zeroIndex] = newState[zeroIndex - 3];
            newState[zeroIndex - 3] = 0;
            successors.add(new Node(newState, node, node.getMoves() + 1));
        }
        if (zeroIndex / 3 < 2) {
            int[] newState = Arrays.copyOf(state, state.length);
            newState[zeroIndex] = newState[zeroIndex + 3];
            newState[zeroIndex + 3] = 0;
            successors.add(new Node(newState, node, node.getMoves() + 1));
        }
        if (zeroIndex % 3 > 0) {
            int[] newState = Arrays.copyOf(state, state.length);
            newState[zeroIndex] = newState[zeroIndex - 1];
            newState[zeroIndex - 1] = 0;
            successors.add(new Node(newState, node, node.getMoves() + 1));
        }
        if (zeroIndex % 3 < 2) {
            int[] newState = Arrays.copyOf(state, state.length);
            newState[zeroIndex] = newState[zeroIndex + 1];
            newState[zeroIndex + 1] = 0;
            successors.add(new Node(newState, node, node.getMoves() + 1));
        }
        return successors;
    }

    public static void main(String[] args) throws InterruptedException {
        // Define el estado inicial
        int[] initialState = generateRandomState();

        // Define el estado objetivo
        int[] targetState = {1, 2, 3, 4, 5, 6, 7, 8, 0};

        System.out.println("Estado inicial:");
        printState(initialState);

        System.out.println("\nEstado objetivo:");
        printState(targetState);

        // Encuentra el camino desde el estado inicial al estado objetivo
        long startTime = System.nanoTime();
        if (findSolution(initialState, targetState)) {
            long endTime = System.nanoTime();
            double elapsedTime = (endTime - startTime) / 1e6; // Convertir a milisegundos
            System.out.println("\nTiempo transcurrido: " + elapsedTime + " ms");
        } else {
            System.out.println("\nNo se encontró el estado objetivo.");
        }
    }
}
