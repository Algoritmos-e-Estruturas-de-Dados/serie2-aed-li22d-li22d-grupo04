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
    val file1 =
    val file2 =

    val points1 = readPointsFromFile(file1)
    val points2 = readPointsFromFile(file2)

    val union = points1 union points2
    val intersection = points1 intersect points2
    val difference = points1 subtract points2

    writePointsToFile("saida_union.co", union)
    writePointsToFile("saida_intersection.co", intersection)
    writePointsToFile("saida_difference.co", difference)
}
