package yamlgun

import org.scalatest.FunSpec

class DynamicBeanSpec extends FunSpec {
  trait StringBean {
    def getValue: String
    def setValue(x: String)
  }

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
