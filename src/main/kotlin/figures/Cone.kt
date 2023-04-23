package figures

import SphereToConeMapper
import kotlin.math.sqrt
import kotlin.random.Random

data class Cone(
    val h: Double,
    val r: Double,
) {
    val sideArea = Math.PI * r * (sqrt(r*r+h*h))
    val floorArea = Math.PI * r * r
    val area = sideArea + floorArea

    val circumscribedSphere = Sphere((h*h + r*r) / (2*h))

    val center = Point(
        x = 0.0,
        y = 0.0,
        z = if (h > circumscribedSphere.r) {
            -sqrt(circumscribedSphere.r*circumscribedSphere.r - r*r)
        } else {
            circumscribedSphere.r - h
        }
    )

    private val pointMapper = SphereToConeMapper(this, circumscribedSphere)

    data class Point(
        val x: Double,
        val y: Double,
        val z: Double,
    ) {
        fun toPoint3D() = Point3D(x, y, z)
    }

    fun distributePoints(n: Int): List<Point> {
        return circumscribedSphere.distributePoints(n).map { pointMapper.map(it) }
    }

    fun randomPoint(): Point {
        return pointMapper.map(circumscribedSphere.randomPoint())
    }
}
