package Game

import java.awt.event.ActionListener
import java.awt.{BasicStroke, Color, Dimension, Font, Graphics2D, RenderingHints}
import java.awt.geom.{ GeneralPath, Path2D}
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

  val fontC = new Font("Serif", Font.BOLD, 15)
  var mouseXPos = -1000
  var mouseYPos = -1000
  var isPlaceable = false

  var playSpeed = 1


  def drawMap(path : List[Pos]) = {
    val polyline = new GeneralPath(Path2D.WIND_NON_ZERO, 30)
    polyline.moveTo(path.head.x, path.head.y)
    for (positio <- path) {
      polyline.lineTo(positio.x,positio.y)
    }
    polyline
  }


  val arena = new Panel {

    override def paintComponent(g: Graphics2D): Unit = {
      g.setColor(new Color(50, 117, 62))
      g.fillRect(0, 0, 1000, 1000)

      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
      g.setColor(new Color(121, 97, 60))
      g.setStroke(new BasicStroke(30))
      g.draw(drawMap(peli.enemyPath))

      if (peli.selected) {

        peli.drawOnMouse(g, mouseXPos, mouseYPos, isPlaceable)
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
    text = "Minigun"
    font = fontC
  }
  val towerButton2 = new Button("Cannon") {
    font= fontC
  }
  val towerButton3 = new Button("Rocket Launcher") {
    font= fontC
  }
  val towerButton4 = new Button("Flamethrower") {
    font = fontC
  }

  val nextRoundButton = new Button("Next Round") {
    foreground = new Color(120, 120, 50)
    font = fontC
  }
  val speedUpButton = new Button(">>") {
    font = fontC
  }

  // create buy menu

  val buyMenu = new GridPanel(3,3) {
    contents += towerButton1
    contents += towerButton2
    contents += towerButton3
    contents += towerButton4
    contents += nextRoundButton
    contents += speedUpButton

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
    contents += new Label("Round:")
    contents += roundIndicator
    contents += new Label("Health:")
    contents += healtPointIndicator
    contents += new Label("Coins:")
    contents += coinCounter

  }

  // represent the right side of gui

  val rightSide = new SplitPane(Orientation.Horizontal, stats, buyMenu)
  rightSide.dividerSize = 0
  rightSide.dividerLocation = 100

  val upgradeButton = new Button("Upgrade") {
    font = fontC

  }

  val sellButton = new Button("Sell") {
    font = fontC
  }

  val upgradeMenu = new GridPanel(3,1) {
    background = new Color(1,100,100)
    contents += upgradeButton
  }
  def updateUpgradeMenu(p : Tower) = {
    upgradeMenu.contents += new Label("Upgrade Costs: "+ p.upgradeCost + " Coins")
    upgradeMenu.contents += upgradeButton
    upgradeMenu.contents += sellButton

  }
  upgradeButton.visible = false

  val extendedRightSide = new SplitPane(Orientation.Horizontal, rightSide, upgradeMenu)
  extendedRightSide.dividerSize = 0
  extendedRightSide.dividerLocation = 500



  // all screen elements together in gms

  val gms = new SplitPane(Orientation.Vertical, arenaWithConsole, extendedRightSide) {

  }
  def restartGame() = {
    peli.restart()
    arena.repaint()
    console.text = "Started New Game"
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
        contents += new MenuItem(Action("New Game")
        (restartGame()))
      }
    }
    def stepper() = {
      peli.step()
            coinCounter.text = peli.coins.toString
            roundIndicator.text  = peli.currentRound.toString
            healtPointIndicator.text = peli.healtPoints.toString
            repaint()
    }
    contents = gms
    listenTo(arena.mouse.clicks)
    listenTo(arena.mouse.moves)
    listenTo(towerButton1)
    listenTo(towerButton2)
    listenTo(towerButton3)
    listenTo(towerButton4)
    listenTo(nextRoundButton)
    listenTo(upgradeButton)
    listenTo(sellButton)
    listenTo(speedUpButton)

    //react to button clicks

    reactions += {
      case ButtonClicked(b) => {
        if (b == towerButton1) {
          mouseYPos = -1000
          peli.selectMinigun()
          peli.selectTower()
          console.text = "Minigun Costs 50 Coins. \n Place Down To Buy, Right Click To Cancel"
          repaint()
        }
        if (b == towerButton2) {
          mouseYPos = -1000
          peli.selectCannon()
          peli.selectTower()
          console.text = "Cannon Costs 80 Coins \n Place Down To Buy, Right Click To Cancel"
          repaint()
        }
        if(b == towerButton3) {
          mouseYPos = -1000
          peli.selectRocketLauncher()
          peli.selectTower()
          console.text = "Rocket launcher Costs 250 Coins \n Place Down To Buy, Right Click To Cancel"
          repaint()
        }
        if(b== towerButton4) {
          mouseYPos = -1000
          peli.selectFlamethrower()
          peli.selectTower()
          console.text = "Flamethrower Costs 1500 Coins\n Attacks all enemies in range \n Place Down To Buy, Right Click To Cancel"
          repaint()
        }
        if (b == nextRoundButton) {
          if (peli.paused) {
            peli.towers.foreach(_.isSelected = false)
            peli.advanceRound()
            console.text = "Round " + peli.currentRound + " Started"
          }
        }
        if(b == speedUpButton) {
          if (playSpeed < 8) {
            playSpeed += 3
          } else playSpeed = 1
        }
        if(b ==upgradeButton) {
          peli.towers.foreach(p => if (p.isSelected) {
            if(p.getClass.getName =="Game.MinigunTower" && p.isSelected && p.upgraded) {

              if(peli.coins >= p.getUpgradeCost) {
                p.upgrade2()
                peli.coins -= p.getUpgradeCost
                repaint()
                coinCounter.text = peli.coins.toString
              } else console.text = "Not Enough Coins To Upgrade"
            } else if(!p.upgraded) {
              if (peli.coins >= p.upgradeCost) {
                p.upgrade()
                peli.coins -= p.upgradeCost
                repaint()
                coinCounter.text = peli.coins.toString
                console.text = "Sell For " + (if(p.upgraded) {(p.cost + p.upgradeCost)/2} else p.cost/2) +" Coins\n" + (if(p.upgraded && (!(peli.selectedTowerType == peli.minigun))) "Tier 1"else "Upgrading Costs " + p.getUpgradeCost + " Coins")
              } else console.text = "Not Enough Coins To Upgrade"
            } else console.text = "Already Upgraded"
          })
        }
        if(b == sellButton) {
          peli.towers.foreach(p => if (p.isSelected) {
            peli.sell(p)
            console.text = "Sold For " + (if(p.upgraded) {(p.cost + p.upgradeCost)/2} else p.cost/2) + " Coins"
          })
          peli.towers --= peli.toBeDeletedT
          upgradeMenu.contents.clear()
          upgradeButton.visible = false
          peli.towers.foreach(_.unselectTower())
          coinCounter.text = peli.coins.toString
          repaint()
        }
      }
    }
    // react to mouse movement on map if a tower has been selected
    reactions += {
      case scala.swing.event.MouseMoved(src, point, k) => {
        if (peli.selected) {
          //cant place new tower on top of another tower
          if (peli.towers.forall(p => new Pos(point.x, point.y).distance(p.position)> p.r) ) {

            isPlaceable = true
            // cant place tower on enemy path
            if (new BasicStroke(3).createStrokedShape(drawMap(peli.enemyPath)).intersects(point.x-30, point.y-30, 60, 60)) {
              isPlaceable = false
            }
          } else isPlaceable = false

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
                  if (isPlaceable) {
                    if (peli.selectedTowerType == peli.minigun) {
                      peli.placeTower(new MinigunTower(new Pos(point.x, point.y)))
                    } else if (peli.selectedTowerType == peli.cannon) {
                      peli.placeTower(new CannonTower(new Pos(point.x, point.y)))
                    } else if (peli.selectedTowerType == peli.rocketLauncher) {
                      peli.placeTower(new RocketLauncher(new Pos(point.x, point.y)))
                    } else if (peli.selectedTowerType == peli.flamethrower) {
                      peli.placeTower(new Flamethrower(new Pos(point.x, point.y)))
                    }
                  peli.unselectTower()
                  repaint()
                  peli.coins -= peli.selectedTowerType("cost")
                  coinCounter.text = peli.coins.toString
                  }
                } else if (d == 4096) {
                  peli.unselectTower()
                }

                repaint()
              } else {
                console.text = "Not Enough Money"
                peli.unselectTower()
                repaint()
              }
            } else if(peli.towers.forall(p => !p.isSelected)) { // if no towers are selected
              //select the one that was pressed
              peli.towers.foreach(p => if(new Pos(point.x, point.y).distance(p.position)< (p.r)/2) {
                p.selectTower()
                console.text = "Sell For " + (if(p.upgraded) {(p.cost + p.upgradeCost)/2} else p.cost/2) +" Coins\n" + (if(p.upgraded && (!(peli.selectedTowerType == peli.minigun))) "Tier 1"else "Upgrading Costs " + p.getUpgradeCost + " Coins")
                updateUpgradeMenu(p)
                upgradeButton.visible = true
              })
              repaint()
            } else {
              upgradeMenu.contents.clear()
              upgradeButton.visible = false
              peli.towers.foreach(_.unselectTower())
              repaint()
            }
          }
        }
      }
    val listener = new ActionListener(){
      def actionPerformed(e : java.awt.event.ActionEvent) = {
        if(peli.gameOver) {
          console.text = "Game Over"
        }
        if(!peli.gameOver) {
          if (!peli.paused) {
            (1 to playSpeed).foreach(p => stepper())

          } else if (peli.currentRound != 0 && !peli.selected && console.text != "Not Enough Money") console.text = "Round " + peli.currentRound + " Completed \n Press Next Round To Start Round " + (peli.currentRound + 1)
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
