package Game

import scalafx.geometry.Orientation.Horizontal

import java.awt
import java.awt.event.ActionListener
import java.awt.{BasicStroke, Color, Dimension, Font, GradientPaint, Graphics2D, Paint, RenderingHints}

import java.awt.geom.{Ellipse2D, GeneralPath, Path2D}
import scala.swing._
import scala.swing.event.ButtonClicked

object GameLauncher {
  def main(args : Array[String] = Array()): Unit = {
    new TowerDefenceGame()

  }

}

class TowerDefenceGame extends SwingApplication {
  val peli = new Game(30)


  peli.createEnemies()




  val fontC = new Font("Arial", 0, 15)
  var mouseXPos = 0
  var mouseYPos = 0

    val prgtext = new TextArea {
    background = new Color(250, 250, 250)
    text = "yo"
    columns = 58
    rows = 15
    font = fontC
  }

  def drawMap(path : List[Pos]) = {
    val polyline = new GeneralPath(Path2D.WIND_NON_ZERO, 10)
    polyline.moveTo(path.head.x, path.head.y)
    for (positio <- path) {
      polyline.lineTo(positio.x,positio.y)
    }



    polyline
  }
  val arena = new Panel {
    background = new Color(255,132, 132)
    override def paintComponent(g: Graphics2D): Unit = {

      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
      g.setStroke(new BasicStroke(15))


      g.setColor(Color.CYAN)

      g.draw(drawMap(peli.enemyPath))
      if (peli.selected) {
        peli.drawOnMouse(g, mouseXPos, mouseYPos)
      }

      peli.update(g)

    }
  }

  val console = new TextPane {
    font = fontC
    editable = false
  }

  val arenaWithConsole = new SplitPane(Orientation.Horizontal, arena, console) {}
  arenaWithConsole.dividerSize = 0
  arenaWithConsole.dividerLocation = 650

  // create buttons to buy towers

  val towerButton1 = new Button {
    text = "<<buy minigun>>"
    font = fontC
  }
  val towerButton2 = new Button("Buy")
  val towerButton3 = new Button("Buy")

  val nextRoundButton = new Button("next round")

  // create buy menu

  val buyMenu = new GridPanel(3,3) {
    contents += towerButton1
    contents += towerButton2
    contents += towerButton3
    contents += nextRoundButton

  }

  // stats shows hp, money, current round and points
  var coinCounter = new Label {
    text = "0"
    font = fontC
  }
  var roundIndicator = new Label {
    text = "0"
    font = fontC
  }
  var healtPointIndicator = new Label {
    text = "0"
    font = fontC
  }

  val stats = new GridPanel(3,2) {
    background = new Color(240,240,155)
    contents += new Label("Coins:")
    contents += coinCounter
    contents += new Label("Round:")
    contents += roundIndicator
    contents += new Label("Health:")
    contents += healtPointIndicator

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
    listenTo(nextRoundButton)

    //react to button clicks

    reactions += {
      case ButtonClicked(b) => {
        if (b == towerButton1) {
          peli.selectMinigun()
          peli.selectTower()
          console.text = "Minigun Costs 50 Coins. \n Place Down To Buy, Right Click To Cancel"
          repaint()
        }
        if (b == towerButton2) {
          peli.selectCannon()
          peli.selectTower()
          console.text = "Cannon Costs 80 Coins \n Place Down To Buy, Right Click To Cancel"
          repaint()
        }
        if (b == nextRoundButton) {
          if (peli.paused) {
            peli.advanceRound()
            console.text = "Round " + peli.currentRound + " Started"
          }
        }
      }
    }
    // react to mouse movement on map if a tower has been selected
    reactions += {
      case scala.swing.event.MouseMoved(src, point, k) => {
        if (peli.selected) {
          mouseXPos = point.x
          mouseYPos = point.y
          repaint()
        }
      }
    }
    // react to clicks on map
    // if tower has been selected. It creates new tower to point.x, point.y

    reactions += {
        case scala.swing.event.MousePressed(src, point, d, _, _) => {
          if (src == arena) {
            if (peli.selected) {
              if(peli.coins >= peli.selectedTowerType("cost")) {
                //if enough money, left mouse button places selected tower and removes cost from coins
                if (d == 1024) {
                  if (peli.selectedTowerType == peli.minigun) {
                    peli.placeTower(new MinigunTower(new Pos(point.x, point.y)))
                  } else if (peli.selectedTowerType == peli.cannon) {
                    peli.placeTower(new CannonTower(new Pos(point.x, point.y)))

                  }

                peli.unselectTower()
                repaint()
                peli.coins -= 50
                coinCounter.text = peli.coins.toString
                }

                peli.unselectTower()
                repaint()

              } else {
                console.text = "not enough money"
                peli.unselectTower()
                repaint()
              }
            } else if(peli.towers.forall(p => !p.isSelected)) { // if no towers are selected
              //select the one that was pressed
            peli.towers.foreach(p => if(new Pos(point.x, point.y).distance(p.position)< (p.r)/2) p.selectTower())
              repaint()
            } else  {
              peli.towers.foreach(_.unselectTower())
              repaint()
            }
          }
        }
      }
    val listener = new ActionListener(){
      def actionPerformed(e : java.awt.event.ActionEvent) = {
        if (!peli.paused) {
          peli.step()

          coinCounter.text = peli.coins.toString
          roundIndicator.text  = peli.currentRound.toString
          healtPointIndicator.text = peli.healtPoints.toString

          repaint()
        }
      }
    }
    val timer = new javax.swing.Timer(6, listener)
    timer.start()
  }
  val t = top
  t.centerOnScreen()
  t.visible = true
}
