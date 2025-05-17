package serie2.part4

class AEDHashMap<K, V>(initialCapacity: Int = 16, val loadFactor: Float = 0.75f) : AEDMutableMap<K, V> {
    private class HashNode<K, V>(override var key: K,
                                 override var value: V, var next: HashNode<K, V>? = null) : AEDMutableMap.MutableEntry<K, V> {
        var hc = key.hashCode()
        override fun setValue(newValue: V): V {
            val old = value
            value = newValue
            return old
        }
    }

    private var table: Array<HashNode<K, V>?> = arrayOfNulls(initialCapacity)

    override var size: Int = 0

    override val capacity: Int
        get() = table.size

    override fun put(key: K, value: V): V? {
        // Determina a função de dispersão
        val indice = (HashNode(key, value).hc and 0x7fffffff) % capacity
        // Valor presente na table no dado índice
        var node = table[indice]
        // Nó a adicionar
        val newNode = HashNode(key, value)
        // Valor antigo
        var antigo: V? = null
        // Se no índice não houver valor, ele cria lá um nó e retorna value == null
        if(node == null) {
            table[indice] = newNode
            size++
        }
        else {
            var temp = node
            while (temp != null) {
                // Verifica se a chave já está neste índice. Se sim, substitui o valor
                if (temp.key == key) {
                    antigo = temp.value
                    temp.value = value
                    return antigo
                }
                // Coloca o nó no fim da lista
                if (temp.next == null) {
                    temp.next = newNode
                    size++
                    return null
                }
                temp = temp.next
            }
        }
        // Chama a função expand para função aumentar a tabela
        if (size >= capacity * loadFactor) {
            expand()
        }
        return antigo
    }

    override operator fun get(key: K): V? {
        var value: V? = null
        //Índice da tabela
        val indice = (key.hashCode() and 0x7fffffff) % capacity
        var node = table[indice]

        value = node?.value
        return value
    }

    private fun expand() {
        val oldTable = table
        table = arrayOfNulls(oldTable.size * 2)

        for (oldNode in oldTable) {
            var node = oldNode
            while (node != null) {
                put(node.key, node.value)
                node = node.next
            }
        }
    }

    override fun iterator(): Iterator<AEDMutableMap.MutableEntry<K, V>> {
        val list = mutableListOf<AEDMutableMap.MutableEntry<K, V>>()
        for (bucket in table) {
            var node = bucket
            while (node != null) {
                list.add(node)
                node = node.next
            }
        }
        return list.iterator()
    }
}