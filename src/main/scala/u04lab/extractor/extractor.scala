package u04lab.extractor

enum List[E]:
  case Cons(head: E, tail: List[E])
  case Nil()

object List:
  def empty[E]: List[E] = Nil()

  def cons[E](h: E, t: List[E]): List[E] = Cons(h, t)

  def append[A](left: List[A], right: List[A]): List[A] = left match
    case Cons(head, rest) => Cons(head, append(rest, right))
    case Nil() => right

  // Factory with varargs
  def apply[E](args: E*): List[E] =
    var list: List[E] = Nil()
    for e <- args do list = append(list, cons(e, Nil()))
    list

  