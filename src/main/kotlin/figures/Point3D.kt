package figures

import kotlin.math.sqrt

data class Point3D(
    val x: Double,
    val y: Double,
    val z: Double,
) {
    fun distance(other: Point3D): Double {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return sqrt(dx*dx + dy*dy + dz*dz)
    }
}
