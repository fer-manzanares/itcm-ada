package mx.sinsel.model

import mx.tecnm.cdmadero.ListGraph
import mx.tecnm.cdmadero.Vertex

class Tsp(
    val graph : ListGraph<String>
) {
    fun getRouteGreedy(start : Vertex<String>) : List<Vertex<String>> {
        val route = ArrayList<Vertex<String>>()
        var current = start
        val size = graph.numberOfVertices()
        var n = 1
        route.add(current)
        while(n < size){
            var min = Double.MAX_VALUE
            var temp : Vertex<String>? = null
            for(edge in graph.edges(current)){
                if (edge.weight!! < min && !route.contains(edge.destination)){
                    temp = edge.destination
                    min = edge.weight!!
                }
            }
            if (temp != null) current = temp!!
            else break
            route.add(current)
            n++
        }
        return route
    }
}