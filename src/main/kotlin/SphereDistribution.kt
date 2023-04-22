import kotlin.math.acos

object SphereDistribution {

    data class Point(
        val theta: Double,
        val phi: Double,
    )

    fun map(points: List<SquareDistribution.Point>): List<Point> {
        return points.map { Point(mapTheta(it.x), mapPhi(it.y)) }
    }

    private fun mapTheta(x: Double): Double {
        return 2.0 * Math.PI * x
    }

    private fun mapPhi(y: Double): Double {
        return acos(1 - 2*y)
    }
}