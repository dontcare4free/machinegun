package machinegun

import java.io.{PrintStream, InputStream}

/**
 * A "bullet" is a (remotely) executable action
 */
trait Bullet extends (BulletContext => Unit)

case class BulletIO(in: InputStream, out: PrintStream, err: PrintStream)
case class BulletContext(args: Seq[String], io: BulletIO)

class MainClassBullet(obj: MainClass) extends Bullet {
  /**
   * Replace the current Java runtime's stdIO implementations
   * @param io The new replacement streams
   * @return The old stdIO streams
   * @note This method is of course **NOT** thread-safe
   */
  def replaceIO(io: BulletIO) = {
    import System._

    val oldIO = BulletIO(in, out, err)

    setIn(io.in)
    setOut(io.out)
    setErr(io.err)

    oldIO
  }

  def apply(ctx: BulletContext) {
    val BulletContext(args, io) = ctx

    val oldIO = replaceIO(io)
    obj.main(args.toArray)
    replaceIO(oldIO)
  }
}