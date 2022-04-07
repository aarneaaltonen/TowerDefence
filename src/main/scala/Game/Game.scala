package Game

import java.awt.geom.{Ellipse2D, Path2D}
import java.awt.{Color, Graphics2D}
import scala.collection.mutable.Buffer
import scala.util.Random

class Game(var startingHealt : Int, var startingCoins : Int = 250) {

  var coins = startingCoins
  var healtPoints = startingHealt
  var paused = false
  var towers = Buffer[Tower]()
  var enemies = Buffer[Enemy]()
  var currentRound = 0
  var gameOver = false
  val enemyPath : List[(Pos)] = List(
                                          new Pos(-100,75),
                                          new Pos(159,77),
                                          new Pos(202,154),
                                          new Pos(320,157),
                                          new Pos(407,68),
                                          new Pos(670,71),
                                          new Pos(761,134),
                                          new Pos(730,251),
                                          new Pos(530,229),
                                          new Pos(441,306),
                                          new Pos(116,285),
                                          new Pos(112,502),
                                          new Pos(247,610),
                                          new Pos(392,429),
                                          new Pos(556,614),
                                          new Pos(744,578),
                                          new Pos(752,395),
                                          new Pos(950,400),
)

  /**
   *List rounds houses the amount of different types of enemies per round on corresponding index
   * first one has to be empty
   *  */
  var rounds = List[(Int, Int, Int, Int, Int)](
    (0,0,0,0,0),
    (3,0,0,0,0),
    (5,2,0,0,0),
    (10,2,0,0,0),
    (20,3,1,1,0),
    (30,2,1,2,0),
    (40,2,1,2,0),
    (80,2,1,2,0),
    (40,30,1,2,0),
    (40,2,30,2,0),
    (0,0,0,0,1),
    (200,50,20,20,0),
    (200,100,50,20,0),
    (200,150,50,20,1),
    (800,100,50,20,0),
    (1000,100,50,20,5),
    (1000,100,50,20,10)
  )
  def createEnemies(): Unit = {
    //The number of enemies affects the pace in which enemies are created
    (1 to rounds(currentRound)._1).foreach(p => addEnemy(new FirstEnemy(enemyPath), 100/rounds(currentRound)._1)) // make first enemytype slow

    (1 to rounds(currentRound)._2).foreach(p => addEnemy(new SecondEnemy(enemyPath), 50/rounds(currentRound)._2)) // second has lower hp but is faster

    (1 to rounds(currentRound)._3).foreach(p => addEnemy(new ThirdEnemy(enemyPath), 50/rounds(currentRound)._3)) // third one tanky

    (1 to rounds(currentRound)._4).foreach(p => addEnemy(new FourthEnemy(enemyPath), 2)) // fouth enemytype a nimble one

    (1 to rounds(currentRound)._5).foreach(p => addEnemy(new Miniboss(enemyPath), 1)) // a boss of sorts
  }
  private var towerSelected = false
  def selectTower() = towerSelected = true
  def unselectTower() = towerSelected = false
  def selected = towerSelected
  var minigun = Map("Damage" -> 10, "range" -> 300, "cost" -> 50)
  var cannon = Map("Damage" -> 50, "range" -> 120, "cost" -> 80)
  var rocketLauncher = Map("Damage" -> 50, "range" -> 500, "cost" -> 250)
  var flamethrower = Map("Damage" -> 2, "range" -> 150, "cost" -> 1500)
  def selectCannon() = selectedTowerType = cannon
  def selectMinigun() = selectedTowerType = minigun
  def selectRocketLauncher() = selectedTowerType = rocketLauncher
  def selectFlamethrower() = selectedTowerType = flamethrower
  var selectedTowerType = minigun
  var rangeRadius = selectedTowerType("range")
  def placeTower(torni : Tower) : Unit = {
    towers += torni
  }
  //addEnemy adds inputted enemy to game and waits waitTime amount of steps.
  def addEnemy(enemy : Enemy, waitTime : Int) = {
    enemies += enemy
    (1 to waitTime).foreach(p => step())
  }


  def advanceRound() = {
    currentRound += 1
    paused = false
    createEnemies()
  }

  var toBeDeleted = Buffer[Tower]()
  def sell(tower : Tower): Unit = {
    coins += tower.cost / 2
    toBeDeleted += tower
  }

  def step() = {
    enemies.foreach(_.move())
    towers.foreach(_.attack(enemies))
    var toBeDeleted = Buffer[Enemy]()
    enemies.foreach(p => if (p.healthPoints <= 0) toBeDeleted += p)
    toBeDeleted.foreach(p => coins += p.coinReward)
    enemies.foreach(p => if (p.reachedEnd) healtPoints -= 1)
    enemies.foreach(p => if (p.reachedEnd) toBeDeleted += p)
    enemies --= toBeDeleted

    if(enemies.isEmpty) {
      paused = true

      towers.foreach(_.hasTarget = false)
    }


  }
  def restart() =  {
    currentRound = 0
    coins = startingCoins
    healtPoints = startingHealt
    enemies.clear()
    towers.clear()
    paused = false
    gameOver = false
  }
  def update(g : Graphics2D) = {
    towers.foreach(_.draw(g))
    enemies.foreach(p => p.draw(g))
    rangeRadius = selectedTowerType("range")
    if(healtPoints <= 0) {
      gameOver = true
    }
  }
  //torniolio luodaan vasta asetusvaiheessa
  //funktio pitää lisätä tänne
  def drawOnMouse(g : Graphics2D, x : Int, y : Int, isPlacable : Boolean) = {
    var r = 50
    g.setColor(Color.white)
    g.fillRoundRect((x-(r/2)),(y-(r/2)), r, r, r , r)
    g.setColor(new Color(1f,0f,0f,.5f))
    if (isPlacable) {
      g.setColor(new Color(0f,1f,0f,.3f))
    }
    if(selectedTowerType == rocketLauncher) {
      def shape = {
      var path = new Path2D.Double(Path2D.WIND_EVEN_ODD)
      var innerCircle = new Ellipse2D.Double((x-(230/2)),(y-(230/2)),230,230)
      var outerCircle = new Ellipse2D.Double((x-(500/2)),(y-(500/2)),500,500)
      path.append(outerCircle, false)
      path.append(innerCircle, false)
      path
      }
      g.fill(shape)
    } else g.fillRoundRect((x-(rangeRadius / 2)),(y-(rangeRadius/2)), rangeRadius, rangeRadius, rangeRadius , rangeRadius)
  }
}
