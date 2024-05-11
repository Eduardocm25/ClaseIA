import java.util.*;
public class ArbolBusqueda {
        Nodo raiz;
    String objetivo;
    
    public ArbolBusqueda(Nodo raiz, String objetivo)
    {
        this.raiz = raiz;
        this.objetivo = objetivo;
    }
    
    //Busqueda por Anchura
    public void busquedaPorAnchura()
    {
        Nodo nodoActual = raiz;
        Collection<String> estadosVisitados = new ArrayList();
        Queue<Nodo> estadosPorVisitar = new LinkedList();
        while(!nodoActual.getEstado().equals(objetivo))
        {
            estadosVisitados.add(nodoActual.getEstado());
            //Generar a los Nodos Hijos
            Collection<String> hijos = nodoActual.generaHijos();	//<-- Cada Equipo tiene que ingeniarselas para crear este metodo!
            for (String hijo : hijos) {
                if(!estadosVisitados.contains(hijo))
                {
                    //System.out.println("---Metiendo nuevo Nodo");
                    //Crear nuevo Nodo.
                    Nodo nHijo = new Nodo(hijo);
                    nHijo.setPadre(nodoActual);
                    estadosPorVisitar.add(nHijo);
                }
            }
            nodoActual = estadosPorVisitar.poll();
        }
        imprimeNodos(nodoActual);
        System.out.println("YA SE ENCONTRO EL NODO OBJETIVO");
        nodoActual.imprimeSolucion();
    }
    //Se implementa el metodo por prioridad con una cola de prioridad, además
    // de las demás heruisticas vistas en clase
    public void busquedaPorAnchuraPrioridad()
    {
        Nodo nodoActual = raiz;
        Collection<String> estadosVisitados = new ArrayList();
        Queue<Nodo> estadosPorVisitar = new PriorityQueue<>();
        while(!nodoActual.getEstado().equals(objetivo))
        {
            estadosVisitados.add(nodoActual.getEstado());
            //Generar a los Nodos Hijos
            Collection<String> hijos = nodoActual.generaHijos();	//<-- Cada Equipo tiene que ingeniarselas para crear este metodo!
            for (String hijo : hijos) {
                if(!estadosVisitados.contains(hijo))
                {
                    //System.out.println("---Metiendo nuevo Nodo");
                    //Crear nuevo Nodo.
                    Nodo nHijo = new Nodo(hijo);
                    nHijo.costo =  calcularCostoPropio(nHijo.getEstado(),
                            this.objetivo);
                    nHijo.setPadre(nodoActual);
                    estadosPorVisitar.add(nHijo);
                }
            }
            nodoActual = estadosPorVisitar.poll();
        }
        imprimeNodos(nodoActual);
        System.out.println("YA SE ENCONTRO EL NODO OBJETIVO");
        nodoActual.imprimeSolucion();
    }

    public void busquedaPorProfundidad()
    {
        Nodo nodoActual = raiz;
        Collection<String> estadosVisitados = new ArrayList();
        Stack<Nodo> estadosPorVisitar = new Stack<Nodo>();
        int iteraciones = 0;
        while(!nodoActual.getEstado().equals(objetivo))
        {
            estadosVisitados.add(nodoActual.getEstado());
            //Generar a los Nodos Hijos
            Collection<String> hijos = nodoActual.generaHijos();	//<-- Cada Equipo tiene que ingeniarselas para crear este metodo!
            for (String hijo : hijos) {
                if(!estadosVisitados.contains(hijo))
                {
                    //System.out.println("---Metiendo nuevo Nodo");
                    //Crear nuevo Nodo.
                    Nodo nHijo = new Nodo(hijo);
                    nHijo.setPadre(nodoActual);
                    estadosPorVisitar.add(nHijo);
                }
            }
            nodoActual = estadosPorVisitar.pop();
        }

        imprimeNodos(nodoActual);
        System.out.println("YA SE ENCONTRO EL NODO OBJETIVO");
        nodoActual.imprimeSolucion();
    }
    public void imprimeNodos(Nodo n) {
        Nodo nodoActual = n;
        while(!nodoActual.getEstado().equals(this.raiz.getEstado())) {
            nodoActual.imprimeSolucion();
            nodoActual= nodoActual.getPadre();
        }
        this.raiz.imprimeSolucion();
    }
    //Calcula el valor absoluto de la diferencia del índice del estado contra
    // el objetivo y va acumulando la suma de estos valores
    public int calculaCostoSuma(String Estado, String Objetivo){
        int costo = 0;
        String estado = Estado.replace(" ", "0");
        String objetivoS = Objetivo.replace(" ", "0");
        String[] indicesEstado = estado.split("");
        String[] indicesObjetivo = objetivoS.split("");
        for (int i = 0; i < indicesEstado.length; i++) {
            costo += Math.abs(Integer.parseInt(
                    String.valueOf(indicesEstado[i]))-
                    Integer.parseInt(
                            String.valueOf(indicesObjetivo[i])));
        }

        return costo;
    }

    // Calcula cuantos nodos están desordenados con respecto al no objetivo
    public int calculaCostoManhattan(String Estado, String Objetivo){
        int costo = 0;
        String estado = Estado.replace(" ", "0");
        String objetivoS = Objetivo.replace(" ", "0");
        String[] indicesEstado = estado.split("");
        String[] indicesObjetivo = objetivoS.split("");
        for (int i = 0; i < indicesEstado.length; i++) {
            int temp = Math.abs(Integer.parseInt(
                    String.valueOf(indicesEstado[i]))-
                    Integer.parseInt(
                            String.valueOf(indicesObjetivo[i])));
            if(temp!=0)
                  costo++;
        }

        return costo;
    }

    //Mi enfoque heurístico implica obtener el resultado de sumar los nodos relevantes y 
    //luego aplicar la operación de resta sin tener en cuenta el valor absoluto

    public int calcularCostoPropio(String estadoInicial, String objetivoFinal) {
        int costo = 0;
        String estado = estadoInicial.replace(" ", "0");
        String objetivo = objetivoFinal.replace(" ", "0");
        String[] indicesEstado = estado.split("");
        String[] indicesObjetivo = objetivo.split("");
        for (int i = 0; i < indicesEstado.length; i++) {
            int temp = Integer.parseInt(String.valueOf(indicesEstado[i])) -
                       Integer.parseInt(String.valueOf(indicesObjetivo[i]));
            if (temp != 0)
                costo++;
        }
        return costo;
    }
}
