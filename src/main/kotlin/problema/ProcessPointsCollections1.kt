import java.io.File

data class Point(val x: Float, val y: Float) {
    override fun equals(other: Any?) =
        other is Point && x == other.x && y == other.y

    override fun hashCode(): Int = x.hashCode() * 31 + y.hashCode()
}

fun readPointsFromFile(filename: String): Set<Point> {
    val points = mutableSetOf<Point>()
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

fun writePointsToFile(filename: String, points: Set<Point>) {
    File(filename).printWriter().use { out ->
        var id = 1
        for (p in points) {
            out.println("v $id ${p.x} ${p.y}")
            id++
        }
    }
}

fun main() {
    var points1 = emptySet<Point>()
    var points2 = emptySet<Point>()

    println("Aplicação ProcessPointsCollections (Standard Library)")
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
                    points1 = readPointsFromFile(input[1])
                    points2 = readPointsFromFile(input[2])
                    println("Ficheiros carregados com sucesso.")
                } catch (e: Exception) {
                    println("Erro ao carregar ficheiros: ${e.message}")
                }
            }

            "1" -> {
                if (input.size != 2) {
                    println("Uso: 1 nome_output.co")
                    continue
                }
                writePointsToFile(input[1], points1 union points2)
                println("Ficheiro '${input[1]}' criado com a união.")
            }

            "2" -> {
                if (input.size != 2) {
                    println("Uso: 2 nome_output.co")
                    continue
                }
                writePointsToFile(input[1], points1 intersect points2)
                println("Ficheiro '${input[1]}' criado com a interseção.")
            }

            "3" -> {
                if (input.size != 2) {
                    println("Uso: 3 nome_output.co")
                    continue
                }
                writePointsToFile(input[1], points1 subtract points2)
                println("Ficheiro '${input[1]}' criado com a diferença.")
            }

            else -> println("Comando inválido.")
        }
    }
}
