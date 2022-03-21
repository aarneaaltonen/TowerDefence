import scala.swing._

object TowerDefenceGame extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "towergame"

    minimumSize = new Dimension(1200, 800)
  }
}
