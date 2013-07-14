package yamlgun.instancebuilder

import scala.util.Try
import scala.reflect.runtime.universe._

trait InstanceBuilder[T] {
  /**
   * Create an empty instance filled with the default values
   */
  def create: T

  /**
   * Build an instance containing the specified values
   */
  def build(values: Map[String, Any]): Try[T]
}

trait ReflectingInstanceBuilder[T] extends InstanceBuilder[T] {
  protected implicit val t: TypeTag[T]

  override def build(values: Map[String, Any]): Try[T] = Try[T] {
    val obj = create
    val objMirror = t.mirror.reflect(obj)
    for ((k, v) <- values) {
      val fieldSymbol = objMirror.symbol.typeSignature.member(newTermName(k)).asTerm.accessed.asTerm
      val field = objMirror.reflectField(fieldSymbol)
      field.set(v)
    }
    obj
  }
}