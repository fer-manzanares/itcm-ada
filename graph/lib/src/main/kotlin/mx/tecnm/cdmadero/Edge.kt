package mx.tecnm.cdmadero

data class Edge<T : Any>(
    val source: Vertex<T>,
    val destination: Vertex<T>,
    var weight: Double? = null
)
