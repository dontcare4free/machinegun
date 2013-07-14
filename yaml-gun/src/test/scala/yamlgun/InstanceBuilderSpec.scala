package yamlgun

import org.scalatest.FunSpec

package objenesisspec {
  // Have to define these outside of the test closures in order to get TypeTags
  // Also has to be outside of the class, so that there is no $outer reference to get wrong
  // (since objenesis doesn't execute the default constructor)
  case class AnyRefCC(value: AnyRef)
  case class IntCC(value: Int)
  case class IntDefaultCC(value: Int = 5)
}

class InstanceBuilderSpec extends FunSpec {
  describe("ObjenesisInstanceBuilder") {
    import objenesisspec._

    import instancebuilder.{ObjenesisInstanceBuilder => IB}

    describe("create[T]") {
      it("should return an empty instance") {
        assert(new IB[AnyRefCC].create === AnyRefCC(null))
      }

      it("should use the null values even if another default value exists") {
        assert(new IB[IntDefaultCC].create === IntDefaultCC(0))
      }
    }

    describe("build[T]") {
      it("should assign values from the values argument") {
        assert(new IB[IntCC].build(Map("value" -> 5)).get === IntCC(value = 5))
      }

      it("should return a Failure if any names are unused by the instance") {
        intercept[ScalaReflectionException] {
          new IB[IntCC].build(Map("values" -> 11)).get
        }
      }
    }
  }
}