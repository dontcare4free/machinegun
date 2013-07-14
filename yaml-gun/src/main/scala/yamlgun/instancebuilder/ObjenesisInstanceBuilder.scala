package yamlgun.instancebuilder

import org.objenesis.ObjenesisStd
import scala.reflect.runtime.universe._
import scala.reflect.ClassTag

object ObjenesisInstanceBuilder {
  private val objenesis = new ObjenesisStd
}

/**
 * @note Objenesis does *not* call the constructor of the class, which means that it has problems dealing with inner classes
 */
class ObjenesisInstanceBuilder[T](implicit val t: TypeTag[T], val ct: ClassTag[T]) extends ReflectingInstanceBuilder[T] {
  import ObjenesisInstanceBuilder.objenesis

  override def create: T = objenesis.newInstance(ct.runtimeClass).asInstanceOf[T]
}
