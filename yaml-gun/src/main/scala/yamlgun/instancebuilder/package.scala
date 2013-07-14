package yamlgun

import scala.reflect.runtime.universe._
import scala.reflect.ClassTag

package object instancebuilder {
  type CaseClassApi[T] = ({
    def copy(): T
  }) with Product

  implicit def caseClassInstanceBuilder[T <: CaseClassApi[T] : TypeTag : ClassTag](default: T) =
    new CaseClassInstanceBuilder[T](default)
}
