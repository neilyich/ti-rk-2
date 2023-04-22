import kotlin.math.sqrt

data class Cone(
    val h: Double,
    val r: Double,
) {
    val sideArea = Math.PI * r * (sqrt(r*r+h*h))
    val floorArea = Math.PI * r * r
    val area = sideArea + floorArea

    data class Point(
        val x: Double,
        val y: Double,
        val z: Double,
    )
}
