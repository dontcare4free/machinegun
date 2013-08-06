package machinegun

/**
 * Config base trait
 */
trait MachineGunConfig {
  trait Listen {
    val Hosts = Seq("127.0.0.1")
    val Port = 1997
  }
  val Listen: Listen
}

/**
 * Main class base class, doubles as configuration storage.
 * Implement the members inherited from MachineGunConfig in your main class with the config data.
 * @note If you use anything that consumes config data, consume MachineGunConfig instead.
 */
trait MachineGunServer extends MachineGunConfig {
  def main(args: Array[String]) {
    ???
  }
}