package serie2.part3

class Node<T> (
    var value: T = Any() as T,
    var next: Node<T>? = null,
    var previous: Node<T>? = null) {
}

fun splitEvensAndOdds(list: Node<Int>){
    // Cria o dummy como a cabeça
    val dummy = list

    // x é o próximo valor do dummy
    var x = dummy.next

    // Se o resto da divisão do valor for ímpar, mantém onde está
    // Se não, passa para o primeiro valor
    while (x != dummy) {
        val nextNode = x?.next

        if(x!!.value % 2 == 0) {
            x.previous?.next = x.next
            x.next?.previous = x.previous

            x.next = dummy.next
            x.previous = dummy
            dummy.next?.previous = x
            dummy.next = x
        }
        x = nextNode
    }
}

fun <T> intersection(list1: Node<T>, list2: Node<T>, cmp: Comparator<T>): Node<T>? {
    // Nós seguintes às sentinelas das listas 1 e 2
    var current1 = list1.next
    var current2 = list2.next

    // Cabeça da nova lista
    var head: Node<T>? = null

    // Passa pelos elementos até voltar ao início numa das listas
    while(current1 != list1 && current2 != list2) {
        // Comparação entre os dois valores para ver se são iguais
        val comparar = cmp.compare(current1?.value, current2?.value)

        when {
            // Se os valores forem iguais
            comparar == 0 -> {
                // Guardar o nó current1
                val node = current1

                // Próximos valores dos currents
                val next1 = current1?.next
                val next2 = current2?.next

                // Remover o nó da lista 1
                current1?.previous?.next = current1?.next
                current1?.next?.previous = current1?.previous

                // Remover o nó da lista 2
                current2?.previous?.next = current2?.next
                current2?.next?.previous = current2?.previous

                // Se a cabeça estiver vazia, o elemento fica como cabeça
                if (head == null) {
                    head = node
                    head?.previous = null
                    head?.next = null
                } else {
                    // Se a cabeça não for seguida, o valor fica a seguir à cabeça
                    if (head.next == null) {
                        head.next = node
                        head.next?.previous = head
                        head.next?.next = null
                    } else {
                        var temp = head
                        while (temp?.next != null) {
                            temp = temp.next
                        }
                        temp?.next = node
                        node?.previous = temp
                        node?.next = null
                    }
                }
                current1 = next1
                current2 = next2

            }
            // Se current1.value < current2.value
            comparar < 0 -> {
                current1 = current1?.next
            }
            // Se current1.value > current2.value
            else -> {
                current2 = current2?.next
            }
        }
    }
    return head
}