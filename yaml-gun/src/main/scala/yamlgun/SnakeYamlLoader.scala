package yamlgun

import scala.util.Try
import org.yaml.snakeyaml.constructor.Constructor

class SnakeYamlLoader extends YamlLoader {
  def fromString[T](in: String): Try[T] = Try {
    ().asInstanceOf[T]
  }
}

/*object SnakeYamlLoader {
  class ScalaConstructor extends Constructor {
    override protected def createDefaultList
  }
}*/