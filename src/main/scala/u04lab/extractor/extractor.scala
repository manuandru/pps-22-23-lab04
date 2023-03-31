package u04lab.extractor

enum List[E]:
  case Cons(head: E, tail: List[E])
  case Nil()

object List:
  def empty[E]: List[E] = Nil()

  def cons[E](h: E, t: List[E]): List[E] = Cons(h, t)

  def append[E](left: List[E], right: List[E]): List[E] = left match
    case Cons(head, rest) => Cons(head, append(rest, right))
    case Nil() => right
  @scala.annotation.tailrec
  def foreach[E](l: List[E])(op: E => Unit): Unit = l match
    case Cons(h, t) => op(h); foreach(t)(op)
    case Nil() =>
  def length(list: List[_]): Int = List.sum(List.map(list)(_ => 1))
  def sum(l: List[Int]): Int = l match
    case Cons(h, t) => h + sum(t)
    case _ => 0
  def map[A, B](l: List[A])(mapper: A => B): List[B] = l match
    case Cons(h, t) => Cons(mapper(h), map(t)(mapper))
    case Nil() => Nil()
  def filter[A](l1: List[A])(pred: A => Boolean): List[A] = l1 match
    case Cons(h, t) if pred(h) => Cons(h, filter(t)(pred))
    case Cons(_, t) => filter(t)(pred)
    case Nil() => Nil()
  import u04lab.code.Option
  import u04lab.code.Option.*
  def find[A](list: List[A])(f: A => Boolean): Option[A] = list match
    case Cons(elem, rest) if f(elem) => Some(elem)
    case Cons(elem, rest) => find(rest)(f)
    case _ => None()
  def contains[A](list: List[A], elem: A): Boolean = !Option.isEmpty(find(list)(_ == elem))



  // Factory with varargs
  def apply[E](args: E*): List[E] =
    var list: List[E] = Nil()
    for e <- args do list = append(list, cons(e, Nil()))
    list


import u04lab.extractor.List.*
trait Item:
  def code: Int
  def name: String
  def tags: List[String]


object Item:
  def apply(code: Int, name: String, tags: String*): Item =
    var l: List[String] = List.Nil()
    for tag <- tags do l = List.append(l, List.Cons(tag, Nil()))
    ItemImpl(code, name, l)

  private case class ItemImpl(code: Int, name: String, tags: List[String]) extends Item


// Extractor
object sameTag:
  def unapply(l: List[Item]): Option[List[String]] = l match
    case Cons(h, t) =>
      var commonTags: List[String] = Nil()
      foreach(h.tags)(tag => { if all(t)(tag) then commonTags = append(commonTags, cons(tag, Nil())) })
      commonTags match
        case Cons(_, _) => Some(commonTags)
        case _ => None
    case Nil() => None

  private def all(list: List[Item])(tag: String): Boolean =
    length(list) == length(filter(list)(item => contains(item.tags, tag)))
