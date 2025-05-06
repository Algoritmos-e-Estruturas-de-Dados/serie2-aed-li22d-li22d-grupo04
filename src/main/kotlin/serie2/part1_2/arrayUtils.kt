package serie2.part1_2
import kotlin.random.Random

fun minimum(maxHeap: Array<Int>, heapSize: Int): Int {

        val startIndex = heapSize / 2    // Índice onde começam as folhas

        var min = maxHeap[startIndex]  // Começa com o primeiro elemento das folhas

        // Percorre apenas as folhas para encontrar o menor
        for (i in startIndex + 1 until heapSize) {
            if (maxHeap[i] < min) {
                min = maxHeap[i]
            }
        }
    return min
}

