package pw.ian.jsontoscala

import scala.scalajs.js

trait Type {
  def name(): String
}

case class ArrayType (
  typeObj: Type
) extends Type {
  def name(): String = s"List[${typeObj.name()}]"
}

case class DictType(
  name: String,
  fields: Map[String, Type]
) extends Type {
  def toCaseClass(): String = {
    List(
      s"case class ${name} {",
      fields.toList.map {  case (key, t) => s"  ${key}: ${t.name()}" }.reduce(_ + ",\n" + _),
      "}"
     ).reduce(_ + "\n" + _)
  }
}

case class NamedType(
  name: String
) extends Type

class TypeFinder(val obj: js.Any) {
  var types: List[DictType] = List[DictType]()

  def findTypes(): List[DictType] = {
    maybeRegister(findType("Thing", obj))
    return types
  }

  def maybeRegister(t: Type): Type = {
    if (t.isInstanceOf[DictType]) {
      types ::= t.asInstanceOf[DictType]
    }
    return t
  }

  def capitalize(s: String): String = {
    s(0).toUpper + s.substring(1)
  }

  def findType(key: String, obj: Any): Type = {
    if (js.Array.isArray(obj.asInstanceOf[js.Any])) {
      return ArrayType(findType(capitalize(key), obj.asInstanceOf[js.Array[js.Any]](0)))
    }
    maybeRegister(obj match {
      case _: Boolean => NamedType("Boolean")
      case _: String => NamedType("String")
      case _: Double => NamedType("Double")
      case x if x == null => NamedType("Any")
      case dict =>
        DictType(capitalize(key), dict.asInstanceOf[js.Dictionary[js.Any]].toMap.map {
          case (key, value) => (key, findType(key, value))
        })
    })
 }

}

object JSONToScala {

  /** Convert converts a JSON string to a list of Scala case classes. */
  def convert(json: String): List[String] = {
    val obj = js.JSON.parse(json)
    return (new TypeFinder(obj)).findTypes().map(_.toCaseClass())
  }

}
