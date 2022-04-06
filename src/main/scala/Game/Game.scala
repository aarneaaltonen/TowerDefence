package Game
import java.awt.{Color, Graphics2D}
import scala.collection.mutable.Buffer
import scala.util.Random

class Game(var healtPoints : Int, var startingCoins : Int = 300) {



  var coins = startingCoins

  var paused = false

  var towers = Buffer[Tower]()
  var enemies = Buffer[Enemy]()

  var currentRound = 0

  val enemyPath : List[(Pos)] = List(     new Pos(27,75),
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
                                          new Pos(875,400),
)

  /**
   *List rounds houses the amount of different types of enemies per round on corresponding index
   * first one has to be empty
   *  */
  var rounds = List[(Int, Int, Int, Int, Int)](
    (0,0,0,0,0),
    (3,2,0,0,0),
    (5,2,3,1,0),
    (10,2,1,2,0),
    (20,3,1,2,0),
    (30,2,1,2,0),
    (40,2,1,2,0),
    (80,2,1,2,0),
    (40,30,1,2,0),
    (40,2,30,2,0),
    (100,10,10,10,0),
    (0,0,0,0,1)


  )

  def createEnemies(): Unit = {
    (1 to rounds(currentRound)._1).foreach(p => addEnemy(new Enemy(50, 3, enemyPath))) // make first enemytype slow

    (1 to rounds(currentRound)._2).foreach(p => addEnemy(new Enemy(30, 5, enemyPath))) // second has lower hp but is faster

    (1 to rounds(currentRound)._3).foreach(p => addEnemy(new Enemy(100, 4, enemyPath))) // third one tanky

    (1 to rounds(currentRound)._4).foreach(p => addEnemy(new Enemy(5, 20, enemyPath))) // fouth enemytype a nimble one

    (1 to rounds(currentRound)._5).foreach(p => addEnemy(new Enemy(8000, 4, enemyPath))) // a boss of sorts


  }


  private var towerSelected = false
  def selectTower() = towerSelected = true
  def unselectTower() = towerSelected = false
  def selected = towerSelected
  var minigun = Map("Damage" -> 10, "range" -> 300, "cost" -> 50)
  var cannon = Map("Damage" -> 50, "range" -> 120, "cost" -> 80)
  def selectCannon() = selectedTowerType = cannon
  def selectMinigun() = selectedTowerType = minigun
  var selectedTowerType = minigun
  var rangeRadius = selectedTowerType("range")



  def placeTower(torni : Tower) : Unit = {
    towers += torni
  }



  def addEnemy(enemy : Enemy) = {
    enemies += enemy
    (1 to 20).foreach(p => step())
  }

  def advanceRound() = {
    currentRound += 1
    paused = false
    createEnemies()
    println(currentRound)
  }
  def step() = {
    enemies.foreach(_.move())
    towers.foreach(_.attack(enemies))
    var toBeDeleted = Buffer[Enemy]()
    enemies.foreach(p => if (p.healthPoints <= 0) toBeDeleted += p)
    toBeDeleted.foreach(p => coins += p.coinReward)
    enemies --= toBeDeleted
    if(enemies.isEmpty) {
      paused = true

    }


  }

  def update(g : Graphics2D) = {
    towers.foreach(_.draw(g))
    enemies.foreach(p => p.draw(g))
    rangeRadius = selectedTowerType("range")
  }

  //torniolio luodaan vasta asetusvaiheessa
  //funktio pitää lisätä tänne
  def drawOnMouse(g : Graphics2D, x : Int, y : Int) = {
    var r = 50
    g.setColor(Color.white)
    g.fillRoundRect((x-(r/2)),(y-(r/2)), r, r, r , r)

    g.setColor(new Color(1f,0f,0f,.5f))
    g.fillRoundRect((x-(rangeRadius / 2)),(y-(rangeRadius/2)), rangeRadius, rangeRadius, rangeRadius , rangeRadius)
  }
}
