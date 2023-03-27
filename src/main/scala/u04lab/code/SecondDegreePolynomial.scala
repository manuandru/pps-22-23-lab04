package u04lab.code

// Express a second degree polynomial
// Structure: secondDegree * X^2 + firstDegree * X + constant
trait SecondDegreePolynomial:
  def constant: Double
  def firstDegree: Double
  def secondDegree: Double
  def +(polynomial: SecondDegreePolynomial): SecondDegreePolynomial
  def -(polynomial: SecondDegreePolynomial): SecondDegreePolynomial


object SecondDegreePolynomial:

  def apply(secondDegree: Double, firstDegree: Double, constant: Double): SecondDegreePolynomial =
    SecondDegreePolynomialImpl(secondDegree, firstDegree, constant)

  private case class SecondDegreePolynomialImpl(secondDegree: Double, firstDegree: Double, constant: Double) extends SecondDegreePolynomial:

    override def +(polynomial: SecondDegreePolynomial): SecondDegreePolynomial =
      perform(_ + _)(polynomial)

    override def -(polynomial: SecondDegreePolynomial): SecondDegreePolynomial =
      perform(_ - _)(polynomial)

    private def perform(op: (Double, Double) => Double)(polynomial: SecondDegreePolynomial): SecondDegreePolynomial =
      SecondDegreePolynomialImpl(
        op(secondDegree, polynomial.secondDegree),
        op(firstDegree, polynomial.firstDegree),
        op(constant, polynomial.constant))


@main def checkComplex(): Unit =
  val simplePolynomial = SecondDegreePolynomial(1.0, 0, 3)
  val anotherPolynomial = SecondDegreePolynomial(0.0, 1, 0.0)
  val fullPolynomial = SecondDegreePolynomial(3.0, 2.0, 5.0)
  val sum = simplePolynomial + anotherPolynomial
  println((sum, sum.secondDegree, sum.firstDegree, sum.constant)) // 1.0 * X^2 + 1.0 * X + 3.0
  assert(sum == simplePolynomial + anotherPolynomial)
  val multipleOperations = fullPolynomial - (anotherPolynomial + simplePolynomial)
  println((multipleOperations, multipleOperations.secondDegree, multipleOperations.firstDegree, multipleOperations.constant)) // 2.0 * X^2 + 1.0 * X + 2.0
  assert(multipleOperations == fullPolynomial - (anotherPolynomial + simplePolynomial))

/** Hints:
  *   - implement SecondDegreePolynomial with a SecondDegreePolynomialImpl class, similar to PersonImpl in slides
  *   - check that equality and toString do not work
  *   - use a case class SecondDegreePolynomialImpl instead
  *   - check equality and toString now
  */
