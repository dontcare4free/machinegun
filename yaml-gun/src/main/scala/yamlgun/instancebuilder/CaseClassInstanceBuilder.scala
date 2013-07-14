package yamlgun.instancebuilder

import scala.reflect.runtime.universe._

class CaseClassInstanceBuilder[T <: CaseClassApi[T]](default: T)(implicit val t: TypeTag[T]) extends ReflectingInstanceBuilder[T] {
  def create: T = default.copy()
}
