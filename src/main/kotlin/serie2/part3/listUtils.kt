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
    TODO()
}