package scala.u04lab.extractor

import junit.framework
import org.junit.Assert.assertEquals
import org.junit.Test
import u04lab.extractor.*
import u04lab.extractor.List.*


class ExtractorTest:

  @Test def testListCreation() =
    assertEquals(Nil(), List())
    assertEquals(Cons(1, Cons(2, Cons(3, Nil()))), List(1, 2, 3))

  val dellXps = Item(33, "Dell XPS 15", "notebook", "pc")
  val dellInspiron = Item(34, "Dell Inspiron 13", "notebook", "pc")
  val xiaomiMoped = Item(35, "Xiaomi S1", "moped", "mobility")
  @Test def testExtractorNoMatches() =
    val noMatches: Option[List[String]] = List(dellXps, xiaomiMoped) match
      case sameTag(t) => Some(t)
      case _ => None
    assertEquals(None, noMatches)

  @Test def testExtractorWithMatches() =
    val matches: Option[List[String]] = List(dellXps, dellInspiron) match
      case sameTag(t) => Some(t)
      case _ => None
    assertEquals(Some(List("notebook", "pc")), matches)
