package yamlgun

import java.lang.reflect.{Proxy, Method, InvocationHandler}
import scala.reflect.ClassTag
import scala.reflect.runtime.universe.{Try => _, _}
import org.objenesis.ObjenesisStd
import scala.util.Try
import yamlgun.instancebuilder.InstanceBuilder

class DynamicBean {
  var values = Map[String, AnyRef]()

  private object ProxyView extends InvocationHandler {
    import DynamicBean.{nameRegex => nr, fixName}

    def invoke(proxy: Any, method: Method, args: Array[AnyRef]): AnyRef = method.getName match {
      case nr("get", name) =>
        assert(args == null || args.length == 0, s"get$name takes no arguments")
        val realName = fixName(name)
        values(realName)
      case nr("set", name) =>
        assert(args != null && args.length == 1, s"get$name takes exactly one argument")
        val realName = fixName(name)
        values += realName -> args(0)
        null
      case _ => ???
    }
  }
  def proxy[T](implicit t: ClassTag[T]): T =
    Proxy.newProxyInstance(getClass.getClassLoader, Array(t.runtimeClass), ProxyView)
      .asInstanceOf[T]

  def to[T: ClassTag: TypeTag](implicit ib: InstanceBuilder[T]): T =
    ib.build(values).get
}

object DynamicBean {
  val nameRegex = "^(get|set)(.+)$".r

  val allUpperRegex = "^([\\W\\dA-Z]+)".r

  def fixName(s: String): String = s match {
    case "" => ""
    case allUpperRegex(_) => s
    case x => x.charAt(0).toLower + x.drop(1)
  }
}