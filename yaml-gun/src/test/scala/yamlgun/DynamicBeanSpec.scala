package yamlgun

import org.scalatest.FunSpec

package dynamicbeanspec {

}

class DynamicBeanSpec extends FunSpec {
  import dynamicbeanspec._

  trait StringBean {
    def getValue: String
    def setValue(x: String)
  }

  // Have to define these outside of the test closures in order to get TypeTags
  case class AnyRefCC(value: AnyRef)
  case class IntCC(value: Int)
  case class IntDefaultCC(value: Int = 5)

  describe("DynamicBean") {
    it("should start out empty") {
      val b = new DynamicBean
      assert(b.values === Map())
    }

    describe("proxy[T]") {
      it("should propagate updates") {
        val b = new DynamicBean
        b.proxy[StringBean].setValue("hello")
        assert(b.values === Map("value" -> "hello"))
      }

      it("should get values from the underlying map") {
        val b = new DynamicBean
        b.values = Map("value" -> "hello")
        assert(b.proxy[StringBean].getValue === "hello")
      }
    }

    def instanceBuilderTests() {
      import DynamicBean.InstanceBuilder

      describe("create[T]") {
        import InstanceBuilder.create

        it("should return an empty instance") {
          assert(create[AnyRefCC] === AnyRefCC(null))
        }

        it("should use the default values if any exist") {
          assert(create[IntDefaultCC] === IntDefaultCC(5))
        }
      }

      describe("build[T]") {
        import InstanceBuilder.build

        it("should assign values from the values argument") {
          assert(build[IntCC](Map("value" -> 5)).get === IntCC(value = 5))
        }

        it("should return a Failure if any names are unused by the instance") {
          build[IntCC](Map("values" -> 11)).get
        }
      }
    }

    describe("fixName") {
      import DynamicBean.fixName

      it("should not affect empty strings") {
        assert(fixName("") === "")
      }

      it("should convert the first letter into lower-case") {
        assert(fixName("Hello") === "hello")
      }

      it("should not affect all-upper-case strings") {
        assert(fixName("HELLO") === "HELLO")
      }
    }
  }
}
