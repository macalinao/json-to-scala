package pw.ian.jsontoscala

import org.scalajs.dom
import org.scalajs.dom.document
import scala.scalajs.js.annotation.JSExport

@JSExport
object App {

  @JSExport
  def doConvert(source: String, dest: String): Unit = {
    val src = document.getElementById(source).asInstanceOf[dom.raw.HTMLTextAreaElement]
    val tgt = document.getElementById(dest).asInstanceOf[dom.raw.HTMLDivElement]
    tgt.innerHTML = JSONToScala.convert(src.value).reduce(_ + "\n\n" + _)
  }

}
