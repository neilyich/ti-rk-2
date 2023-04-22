import kotlin.math.floor
import kotlin.math.sqrt

object SquareDistribution {

    private val phi = (1+sqrt(5.0) / 2.0)

    data class Point(
        val x: Double,
        val y: Double,
    )

    fun get(n: Int): List<Point> {
        return (0 until n).map { Point(x(it, n),  y(it, n)) }
    }

    private fun x(i: Int, n: Int): Double {
        val t = i.toDouble() / phi
        return t - floor(t)
    }

    private fun y(i: Int, n: Int): Double {
        return i.toDouble() / n.toDouble()
    }
}