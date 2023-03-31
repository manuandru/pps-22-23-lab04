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
