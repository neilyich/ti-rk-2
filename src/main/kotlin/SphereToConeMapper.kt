import figures.Cone
import figures.Sphere
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class SphereToConeMapper(
    private val cone: Cone,
    private val sphere: Sphere,
) {

    fun map(point: Sphere.Point): Cone.Point {
        if (isMappedToSide(point)) {
            return mapToSide(point)
        }
        return mapToFloor(point)
    }

    private fun isMappedToSide(point: Sphere.Point): Boolean {
        val circlePoint = circleProjection(point)
        return if (isCenterInsideCone()) {
            val deltaH = sqrt(sphere.r*sphere.r - cone.r*cone.r)
            circlePoint.y > -deltaH
        } else {
            val deltaH = sphere.r - cone.h
            circlePoint.y > deltaH
        }
    }

    private fun mapToSide(point: Sphere.Point): Cone.Point {
        val side = if (isCenterInsideCone()) {
            val deltaH = sqrt(sphere.r*sphere.r - cone.r*cone.r)
            val a0 = Point(0.0, cone.h - deltaH)
            val a1 = Point(cone.r, -deltaH)
            Line(a0, a1)
        } else {
            val a0 = Point(0.0, sphere.r)
            val a1 = Point(cone.r, sphere.r - cone.h)
            Line(a0, a1)
        }
        return findProjection(point, side, Point(0.0, 0.0))
    }

    private fun mapToFloor(point: Sphere.Point): Cone.Point {
        val floor = if (isCenterInsideCone()) {
            val deltaH = sqrt(sphere.r*sphere.r - cone.r*cone.r)
            val a0 = Point(-cone.r, -deltaH)
            val a1 = Point(cone.r, -deltaH)
            Line(a0, a1)
        } else {
            val a0 = Point(-cone.r, sphere.r - cone.h)
            val a1 = Point(cone.r, sphere.r - cone.h)
            Line(a0, a1)
        }
        return findProjection(point, floor, Point(0.0, sphere.r))
    }

    private fun findProjection(point: Sphere.Point, line: Line, startRay: Point): Cone.Point {
        var circlePoint = circleProjection(point)
        var xCoef = 1.0
        if (circlePoint.x < 0) {
            circlePoint = circlePoint.copy(x = -circlePoint.x)
            xCoef = -1.0
        }
        val ray = Line(startRay, circlePoint)
        val projection = line.intersection(ray)
        return Cone.Point(
            x = xCoef * projection.x * cos(point.theta),
            y = projection.x * sin(point.theta),
            z = projection.y,
        )
    }

    private fun circleProjection(point: Sphere.Point): Point {
        return Point(sphere.r * cos(point.phi), sphere.r * sin(point.phi))
    }

    private fun isCenterInsideCone(): Boolean {
        return cone.h > sphere.r
    }

    private data class Line(
        val a0: Point,
        val a1: Point,
    ) {
        val k = (a0.y - a1.y) / (a0.x -a1.x)
        val b = a0.y - k * a0.x

        fun intersection(other: Line): Point {
            if (abs(k - other.k) < 0.00001) {
                throw IllegalArgumentException("parallel lines do not have intersection: $this, $other")
            }
            val x = (other.b - b) / (k - other.k)
            val y = k * x + b
            return Point(x, y)
        }

    }

    private data class Point(
        val x: Double,
        val y: Double,
    )
}