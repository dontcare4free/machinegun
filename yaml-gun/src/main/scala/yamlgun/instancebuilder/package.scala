package yamlgun

import scala.reflect.runtime.universe._

package object instancebuilder {
  private type RawCaseClassApi[T] = {
    def copy(): T with CaseClassApi[T]
  }
  type CaseClassApi[T] = RawCaseClassApi[T] with Product

  implicit def caseClassInstanceBuilder[T <: CaseClassApi[T]](default: T)(implicit t: TypeTag[T]) =
    new CaseClassInstanceBuilder[T](default)
}
