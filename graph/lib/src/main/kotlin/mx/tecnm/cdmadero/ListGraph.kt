package mx.tecnm.cdmadero

class ListGraph<T : Any> : Graph<T> {
    private val adjacencies: HashMap<Vertex<T>,
            ArrayList<Edge<T>>> = HashMap()

    override fun createVertex(data: T): Vertex<T> {
		if (this.contains(data)) return this.get(data)!!
        val vertex = Vertex(adjacencies.count(), data)
        adjacencies[vertex] = ArrayList()
        return vertex
    }

    override fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        var edge = this.get(Edge(source, destination, weight))
		if (edge != null){
			edge!!.weight = weight
		}else{
			edge = Edge(source, destination, weight)
			adjacencies[source]?.add(edge)
		}
    }

    override fun addUndirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        addDirectedEdge(source, destination, weight)
        addDirectedEdge(destination, source, weight)
    }

    override fun add(edge: EdgeType, source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        when (edge) {
            EdgeType.DIRECTED -> addDirectedEdge(source, destination,
                weight)
            EdgeType.UNDIRECTED -> addUndirectedEdge(source,
                destination, weight)
        }
    }

    override fun edges(source: Vertex<T>): ArrayList<Edge<T>> =
        adjacencies[source] ?: arrayListOf()

    override fun weight(source: Vertex<T>, destination: Vertex<T>): Double? {
        return edges(source).firstOrNull { it.destination ==
                destination }?.weight
    }

	fun numberOfVertices() = adjacencies.count()
	
    
    fun numberOfPaths(
        source: Vertex<T>,
        destination: Vertex<T>
    ): Int {
        val numberOfPaths = Ref(0)
        val visited: MutableSet<Vertex<T>> = mutableSetOf()
        paths(source, destination, visited, numberOfPaths)
        return numberOfPaths.value
    }

    fun paths(
        source: Vertex<T>,
        destination: Vertex<T>,
        visited: MutableSet<Vertex<T>>,
        pathCount: Ref<Int>
    ) {
        visited.add(source)
        if (source == destination) {
            pathCount.value++
        } else {
            val neighbors = edges(source)
            neighbors.forEach { edge ->
                if (edge.destination !in visited) {
                    paths(edge.destination, destination, visited, pathCount)
                }
            }
        }
        visited.remove(source)
    }
    override fun toString(): String {
        return buildString {
            adjacencies.forEach { (vertex, edges) ->
                val edgeString = edges.joinToString { it.destination.data.toString() }
                append("${vertex.data} ---> [ $edgeString ]\n")
            }
        }
    }

    fun contains(data : T) : Boolean {
        var result = false
        adjacencies.forEach { (vertex) ->
            if (vertex.data == data){
                result = true
                return@forEach
            }
        }
        return result
    }

    fun get(data : T) : Vertex<T>? {
        var result: Vertex<T>? = null
        adjacencies.forEach { (vertex) ->
            if (vertex.data == data){
                result = vertex
                return@forEach
            }
        }
        return result
    }
	
	fun get(edge : Edge<T>) : Edge<T>? {
        var result: Edge<T>? = null
		var source: Vertex<T>? = this.get(edge.source.data)
        if (source != null){
        val neighbors = edges(source)
            neighbors.forEach { aEdge ->
                if (aEdge.destination.data == edge.destination.data) {
					result = aEdge
					return@forEach
				}
            }
		}
        return result
    }
	
	
}

data class Ref<T>(var value: T)