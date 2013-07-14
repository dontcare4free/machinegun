package yamlgun.instancebuilder

import scala.reflect.runtime.universe._
import scala.reflect.ClassTag

class CaseClassInstanceBuilder[T <: CaseClassApi[T]](default: T)(implicit val t: TypeTag[T], val ct: ClassTag[T]) extends ReflectingInstanceBuilder[T] {
  def create: T = default.copy()
}
