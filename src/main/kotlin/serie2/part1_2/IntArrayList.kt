package serie2.part1_2

class IntArrayList(k: Int): Iterable <Int> {
    private var lista = mutableListOf<Int>()
    private var tamanho = k

    fun append(x:Int):Boolean {
        if(lista.size >= tamanho) {
            return false
        }
        lista.add(x)
        return true
    }

    fun get(n: Int):Int? {
        if(n >= lista.size) { return null }
        return lista[n]
    }

    fun addToAll(x: Int) {
        lista.replaceAll { it + x }
    }

    fun remove():Boolean {
        if(lista.isEmpty()) { return false }
        lista.removeAt(0)
        return true
    }

    override fun iterator(): Iterator<Int> { // Opcional
        TODO("Not yet implemented")
    }
}