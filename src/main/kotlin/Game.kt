data class Game(
    val cone: Cone,
    val pointsCount: Int,
    val e: Double,
) {
    private val pointsArea = pointsCount * Math.PI * e * e

    fun analyticSolution(): Double {
        val solution = pointsArea / cone.area
        if (solution > 1) {
            return 1.0
        }
        return solution
    }

    fun distributePoints(): List<Cone.Point> {
        val sphereDistribution = SphereDistribution.map(SquareDistribution.get(pointsCount))
        return sphereDistribution.map { map(it) }
    }

    private fun map(point: SphereDistribution.Point): Cone.Point {

    }

}