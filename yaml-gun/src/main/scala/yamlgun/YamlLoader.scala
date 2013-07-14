package yamlgun

import scala.util.Try

trait YamlLoader {
  /**
   * Load a YAML document from a string
   * @param in The document to be loaded
   * @tparam T The resulting type of the document
   */
  def fromString[T](in: String): Try[T]
}