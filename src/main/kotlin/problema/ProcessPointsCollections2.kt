package problema2

import serie2.part4.AEDHashMap
import serie2.part4.AEDMutableMap
import java.io.File

data class Point(val x: Float, val y: Float) {
    override fun equals(other: Any?) =
        other is Point && x == other.x && y == other.y

    override fun hashCode(): Int = x.hashCode() * 31 + y.hashCode()
}

fun readPointsFromFile(filename: String): List<Point> {
    val points = mutableListOf<Point>()
    File(filename).forEachLine { line ->
        val parts = line.trim().split("\\s+".toRegex())
        if (parts.isNotEmpty() && parts[0] == "v" && parts.size == 4) {
            val x = parts[2].toFloatOrNull()
            val y = parts[3].toFloatOrNull()
            if (x != null && y != null) {
                points.add(Point(x, y))
            }
        }
    }
    return points
}

fun writePointsToFile(filename: String, entries: AEDMutableMap<Point, Boolean>) {
    File(filename).printWriter().use { out ->
        var id = 1
        for (entry in entries) {
            val p = entry.key
            out.println("v $id ${p.x} ${p.y}")
            id++
        }
    }
}

fun main() {
    var file1Points = AEDHashMap<Point, Boolean>()
    var file2Points = AEDHashMap<Point, Boolean>()

    println("Aplicação ProcessPointsCollections (com AEDHashMap)")
    println("Comandos: load f1.co f2.co | 1 union.co | 2 intersection.co | 3 difference.co | exit")

    while (true) {
        print("> ")
        val input = readlnOrNull()?.trim()?.split("\\s+".toRegex()) ?: continue
        if (input.isEmpty()) continue

        when (input[0]) {
            "exit" -> return

            "load" -> {
                if (input.size != 3) {
                    println("Uso: load file1.co file2.co")
                    continue
                }
                try {
                    file1Points = AEDHashMap()
                    file2Points = AEDHashMap()

                    for (p in readPointsFromFile(input[1])) file1Points.put(p, true)
                    for (p in readPointsFromFile(input[2])) file2Points.put(p, true)

                    println("Ficheiros carregados com sucesso.")
                } catch (e: Exception) {
                    println("Erro ao carregar ficheiros: ${e.message}")
                }
            }

            "1" -> { // UNION
                if (input.size != 2) {
                    println("Uso: 1 output.co")
                    continue
                }
                val result = AEDHashMap<Point, Boolean>()
                for (e in file1Points) result.put(e.key, true)
                for (e in file2Points) result.put(e.key, true)
                writePointsToFile(input[1], result)
                println("Ficheiro '${input[1]}' criado com a união.")
            }

            "2" -> { // INTERSECTION
                if (input.size != 2) {
                    println("Uso: 2 output.co")
                    continue
                }
                val result = AEDHashMap<Point, Boolean>()
                for (e in file1Points) {
                    if (file2Points.get(e.key) != null) {
                        result.put(e.key, true)
                    }
                }
                writePointsToFile(input[1], result)
                println("Ficheiro '${input[1]}' criado com a interseção.")
            }

            "3" -> { // DIFFERENCE
                if (input.size != 2) {
                    println("Uso: 3 output.co")
                    continue
                }
                val result = AEDHashMap<Point, Boolean>()
                for (e in file1Points) {
                    if (file2Points.get(e.key) == null) {
                        result.put(e.key, true)
                    }
                }
                writePointsToFile(input[1], result)
                println("Ficheiro '${input[1]}' criado com a diferença.")
            }

            else -> println("Comando inválido.")
        }
    }
}
