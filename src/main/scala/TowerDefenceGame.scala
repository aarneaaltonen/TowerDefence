import scala.swing._
import java.awt.{Color, Dimension, Font}
import event._

object GameLauncher {
  def main(args : Array[String] = Array()): Unit = {
    new TowerDefenceGame()

  }

}

class TowerDefenceGame extends SwingApplication {

  val fontC = new Font("Sans", java.awt.Font.PLAIN, 13)

    val prgtext = new TextArea {
    background = new Color(250, 250, 250)
    text = "yo"
    columns = 58
    rows = 15
    font = fontC
  }
  val arena = new Panel {
    background = new Color(255,132, 132)

  }
  val console = new TextPane {
    font = fontC
    text = ""
    editable = false
  }

  val arenaWithConsole = new SplitPane(Orientation.Horizontal, arena, console) {}
  arenaWithConsole.dividerSize = 0
  arenaWithConsole.dividerLocation = 700


  val buyMenu = new GridBagPanel
  val stats = new Panel {
    background = new Color(240,240,155)
  }
  val rightSide = new SplitPane(Orientation.Horizontal, stats, buyMenu)
  rightSide.dividerSize = 0
  rightSide.dividerLocation = 300

  val gms = new SplitPane(Orientation.Vertical, arenaWithConsole, rightSide) {
  }
  gms.dividerSize = 0
  gms.dividerLocation = 900



  prgtext.listenTo(prgtext.mouse.clicks)
  prgtext.listenTo(prgtext.keys)
  override def startup(args : Array[String]) = {}
  override def quit() = { }

  def top = new Frame { frame =>
    title = "Tower Defence Game"
    background = new Color(79, 12,123)
    minimumSize = new Dimension(1200,800)
    preferredSize = new Dimension(1200, 800)
    maximumSize = new Dimension(1200,800)
    resizable = false
    menuBar = new MenuBar{
      contents += new Menu("testi") {
        contents += new MenuItem("New Game")
      }
    }
    contents = gms
  }

  val t = top
  t.centerOnScreen()
  t.visible = true


}
