import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class SphereToConeMapper(
    private val sphere: Sphere,
    private val cone: Cone,
) {
    fun map(point: SphereDistribution.Point): Cone.Point {
        if (isMappedToSide(point)) {
            return mapToSide(point)
        }
        return mapToFloor(point)
    }

    private fun isMappedToSide(point: SphereDistribution.Point): Boolean {
        return true
    }

    private fun mapToSide(point: SphereDistribution.Point): Cone.Point {
        val circlePoint = Point(sphere.r * cos(point.phi), sphere.r * sin(point.phi))
        val deltaH = sqrt(sphere.r*sphere.r - cone.r*cone.r)
        val a0 = Point(0.0, cone.h - deltaH)
        val a1 = Point(cone.r, deltaH)
        val side = line(a0, a1)
        val ray = line(Point(0.0, 0.0), circlePoint)
        val projection = intersection(side, ray)
        val coef = circlePoint.x / projection.x
        return Cone.Point(
            x = sphere.r * cos(point.theta) * sin(point.phi) * coef,
            y = sphere.r * sin(point.theta) * sin(point.phi) * coef,
            z = projection.y,
        )
    }

    private fun line(a0: Point, a1: Point): Line {
        val k = (a0.y - a1.y) / (a0.x -a1.x)
        val b = a0.y - k * a0.x
        return Line(k, b)
    }

    private fun intersection(a: Line, b: Line): Point {
        val x = (b.b - a.b) / (a.k - b.k)
        val y = a.k * x + a.b
        return Point(x, y)
    }

    private fun mapToFloor(point: SphereDistribution.Point): Cone.Point {
        TODO("Not yet implemented")
    }

    private data class Line(
        val k: Double,
        val b: Double,
    )

    private data class Point(
        val x: Double,
        val y: Double,
    )
}