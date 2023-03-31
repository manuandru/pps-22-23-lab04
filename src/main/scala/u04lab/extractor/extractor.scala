package u04lab.extractor

enum List[E]:
  case Cons(head: E, tail: List[E])
  case Nil()

object List:
  def empty[E]: List[E] = Nil()

  def cons[E](h: E, t: List[E]): List[E] = Cons(h, t)

  def apply[E](args: E*): List[E] = Nil()