package eu.abit.lattice

import eu.adrianbrink.parser.{Expression, Variable}

/*
  Intuition: The lattice is used in order to combine information. Thus we can compute the common element of two lattice
  elements by using the binary relation(op) defined over a Set (polymorphic type [A]).
  In addition, to instantiate a lattice its bottom element is needed.
*/
abstract class Lattice[A](private val op: (LatticeElement[A], LatticeElement[A]) => LatticeElement[A],
                          val bottom: LatticeElement[A]) {
  def computeCommon(x: LatticeElement[A], y: LatticeElement[A]): LatticeElement[A] = op(x, y)
}

class LatticeElement[A](val element: Set[A])

object Test extends App {
  val availExprLat: Lattice[Expression] =
    new Lattice[Expression]((e, e1) => new LatticeElement[Expression](e.element.filter(e1.element.contains(_))), new
        LatticeElement[Expression](Set.empty[Expression])){}

  val e1 = new LatticeElement[Expression](Set(new Variable(1, "ds")))
  val e2 = new LatticeElement[Expression](Set(new Variable(1, "ds")))

  val e3 = availExprLat.computeCommon(e1, e2)

  val e4 = new LatticeElement[Int](Set(1))
  val e5 = new LatticeElement[Int](Set(1,2,3))
  val constant: Lattice[Int] = new Lattice[Int]((l1, l2) => new LatticeElement[Int](l1.element ++ l2.element), new
      LatticeElement[Int] (Set.empty[Int])) {}

  val res: LatticeElement[Int] = constant.computeCommon(e4, e5)
  val l = res.element

}