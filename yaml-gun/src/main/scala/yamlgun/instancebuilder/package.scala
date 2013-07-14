package yamlgun

import scala.reflect.runtime.universe._

package object instancebuilder {
  private type RawCaseClassApi[T] = {
    def copy(): CaseClass[T]
  }
  type CaseClassApi[T] = RawCaseClassApi[T] with Product
  type CaseClass[T] = T with CaseClassApi[T]

  implicit def caseClassInstanceBuilder[T <: CaseClassApi](default: T)(implicit t: TypeTag[T]) =
    new CaseClassInstanceBuilder[T](default)
}
