package figures

import kotlin.math.acos

data class Sphere(
    val r: Double,
    val center: Point3D = Point3D(0.0, 0.0, 0.0)
) {

    val square = Square(1.0)
    data class Point(
        val theta: Double,
        val phi: Double,
    )

    fun distributePoints(n: Int): List<Point> {
        return square.distributePoints(n).map { map(it) }
    }

    private fun map(point: Square.Point): Point {
        return Point(mapTheta(point.x), mapPhi(point.y))
    }

    private fun mapTheta(x: Double): Double {
        return 2.0 * Math.PI * x
    }

    private fun mapPhi(y: Double): Double {
        return acos(1 - 2*y) - Math.PI / 2.0
    }

    fun randomPoint(): Point {
        return map(square.randomPoint())
    }

    operator fun contains(point: Point3D): Boolean {
        return point.distance(center) <= r
    }
}