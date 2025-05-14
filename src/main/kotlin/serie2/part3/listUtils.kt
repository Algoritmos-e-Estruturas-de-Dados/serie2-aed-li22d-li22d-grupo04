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
    var current1 = list1
    var current2 = list2

    var head: Node<T>? = null

    var next1 = current1.next
    var next2 = current2.next

    while((next1 != null && cmp.compare(current1.value, next1.value) <= 0) ||
          (next2 != null && cmp.compare(current2.value, next2.value) <= 0)) {
        if (cmp.compare(current1.value, current2.value) == 0) {

            // Remover o nó da lista 1
            current1.previous?.next = current1.next
            current1.next?.previous = current1.previous

            // Remover o nó da lista 2
            current2.previous?.next = current2.next
            current2.next?.previous = current2.previous

            // Se a cabeça estiver vazia, o elemento fica como cabeça
            if (head == null) {
                head = current1
            } else {
                // Se a cabeça não for seguida, o valor fica a seguir à cabeça
                if (head.next == null) {
                    head.next = current1
                } else {
                    var temp = head
                    while (temp?.next == null) {
                        temp = temp?.next
                    }
                    temp.next = current1
                    current1.previous = temp
                    current1.next = null
                }
            }

            current1 = current1.next?: break
            current2 = current2.next?: break

        } else if (cmp.compare(current1.value, current2.value) < 0) {
            current1 = current1.next?: break
        } else {
            current2 = current2.next?: break
        }

        next1 = current1.next
        next2 = current2.next
    }
    return head
}