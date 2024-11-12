package mx.sinsel.viewmodel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import mx.sinsel.model.Tsp
import mx.tecnm.cdmadero.EdgeType
import mx.tecnm.cdmadero.ListGraph
import mx.tecnm.cdmadero.Vertex

class RouteViewModel: ViewModel() {
    private var _graph = mutableStateOf(ListGraph<String>())
    val graphState : State<ListGraph<String>> = _graph
    private var _route = mutableStateOf(arrayListOf<Vertex<String>>())
    val route : State<ArrayList<Vertex<String>>> = _route
    var output = mutableStateOf("")

    init {
        val tampico = _graph.value.createVertex("Tampico")
        val panuco = _graph.value.createVertex("Panuco")
        val ciudadValles = _graph.value.createVertex("Cd. Valles")
        val ciudadMante = _graph.value.createVertex("Ciudad Mante")
        val sanLuisPotosi = _graph.value.createVertex("San Luis Potosi")

        _graph.value.add(EdgeType.UNDIRECTED, tampico, ciudadValles, 300.0)
        _graph.value.add(EdgeType.UNDIRECTED, tampico, panuco, 500.0)
        _graph.value.add(EdgeType.UNDIRECTED, ciudadValles, panuco, 250.0)
        _graph.value.add(EdgeType.UNDIRECTED, panuco, ciudadMante, 450.0)
        _graph.value.add(EdgeType.UNDIRECTED, ciudadValles, sanLuisPotosi, 600.0)
        _graph.value.add(EdgeType.UNDIRECTED, sanLuisPotosi, tampico, 600.0)
        _graph.value.add(EdgeType.UNDIRECTED, ciudadMante, sanLuisPotosi, 600.0)
        val tampico1 = _graph.value.createVertex("Tampico")
        _graph.value.add(EdgeType.UNDIRECTED, tampico, ciudadValles, 350.0)
        _graph.value.add(EdgeType.UNDIRECTED, tampico, panuco, 100.0)
    }

    fun createRoute(start : Vertex<String>) : Unit {
        val tsp = Tsp(_graph.value)
        val route = tsp.getRouteGreedy(start)
        if (route.size != _graph.value.numberOfVertices()){
            output.value = "Solución parcial: "
        }else{
            output.value = "Solución: "
        }

        for (vertex in route){
            output.value += " - ${vertex.data}"
        }
    }

}