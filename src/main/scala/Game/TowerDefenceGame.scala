package Game

import scalafx.geometry.Orientation.Horizontal

import java.awt
import java.awt.event.ActionListener
import java.awt.{Color, Dimension, Font, Graphics2D, RenderingHints}
import java.awt.geom.Ellipse2D
import scala.swing._
import scala.swing.event.ButtonClicked

object GameLauncher {
  def main(args : Array[String] = Array()): Unit = {
    new TowerDefenceGame()

  }

}

class TowerDefenceGame extends SwingApplication {
  val peli = new Game(30)

  val fontC = new Font("Courier", java.awt.Font.PLAIN, 13)

    val prgtext = new TextArea {
    background = new Color(250, 250, 250)
    text = "yo"
    columns = 58
    rows = 15
    font = fontC
  }
  val arena = new Panel {
    background = new Color(255,132, 132)
    override def paintComponent(g: Graphics2D): Unit = {

      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
      peli.update(g)
    }


  }



  val console = new TextPane {
    font = fontC
    editable = false
  }

  val arenaWithConsole = new SplitPane(Orientation.Horizontal, arena, console) {}
  arenaWithConsole.dividerSize = 0
  arenaWithConsole.dividerLocation = 700

  // create buttons to buy towers

  val towerButton1 = new Button {
    text = "<<buy minigun>>"
    font = fontC
  }
  val towerButton2 = new Button("Buy")
  val towerButton3 = new Button("Buy")

  // create buy menu

  val buyMenu = new GridPanel(3,3) {
    contents += towerButton1
    contents += towerButton2
    contents += towerButton3


  }

  // stats shows hp, money, current round and points

  val stats = new Panel {
    background = new Color(240,240,155)

  }

  // represent the right side of gui

  val rightSide = new SplitPane(Orientation.Horizontal, stats, buyMenu)
  rightSide.dividerSize = 0
  rightSide.dividerLocation = 300

  // all screen elements together in gms

  val gms = new SplitPane(Orientation.Vertical, arenaWithConsole, rightSide) {
  }
  gms.dividerSize = 0
  gms.dividerLocation = 900




  override def startup(args : Array[String]) = {}


  def top = new MainFrame { frame =>
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

    listenTo(arena.mouse.clicks)
    listenTo(arena.mouse.moves)
    listenTo(towerButton1)
    listenTo(towerButton2)

    //react to button clicks

    reactions += {
      case ButtonClicked(b) => {
        if (b == towerButton1) {
          println("1")
          peli.selectTower()

          // val torni = new Tower(123123123)
          // if peli.money > torni.cost
          // peli.selectTower(torni)
          // else console.text = not enough money y'ä'ä


        }
        if (b == towerButton2) {
          println("2")
        }
      }
    }

    // react to mouse movement on map if a tower has been selected

    reactions += {
      case scala.swing.event.MouseMoved(src, point, k) => {
        if (peli.selected) {
          repaint()


        }
      }
    }

    // react to clicks on map
    // if tower has been selected. It creates new tower to point.x, point.y

    reactions += {
        case scala.swing.event.MousePressed(src, point, _, _, _) => {
          if (src == arena) {
            println("in arena")
            if (peli.selected) {
              peli.placeTower(new Tower(30, new Pos(point.x, point.y), 3))
              peli.unselectTower()
              repaint()
            }

          }
          console.text = point.x.toString
          println(peli.towers)
        }
      }



  }





  val t = top
  t.centerOnScreen()
  t.visible = true


}
