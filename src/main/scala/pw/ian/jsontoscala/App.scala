package pw.ian.jsontoscala

import org.scalajs.dom
import org.scalajs.dom.document
import scala.scalajs.js.annotation.JSExport

@JSExport
object App {

  @JSExport
  def doConvert(source: String, dest: String): Unit = {
    val src = document.getElementById(source).asInstanceOf[dom.raw.HTMLTextAreaElement]
    val tgt = document.getElementById(dest).asInstanceOf[dom.raw.HTMLElement]
    val classes = JSONToScala.convert(src.value)
    if (classes.length > 0) {
      tgt.innerHTML = classes.reduce(_ + "\n\n" + _)
    } else {
      tgt.innerHTML = "No classes found"
    }
  }

}
