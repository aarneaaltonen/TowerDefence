package Game
import java.awt.{Color, Graphics2D}
import scala.collection.mutable.Buffer
import scala.util.Random

class Game(var healtPoints : Int) {

  var type1Radius = 200

  var coins = 0

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
   *  */
  var rounds = List[(Int, Int, Int, Int)](
    (2,1,0,0),
    (3,2,0,0),
    (5,2,1,2),
    (10,2,1,2),
    (20,3,1,2),
    (30,2,1,2),
    (40,2,1,2),


  )

  def createEnemies(): Unit = {
    (1 to rounds(currentRound)._1).foreach(p => addEnemy(new Enemy(50, 3, enemyPath))) // make first enemytype slow

    (1 to rounds(currentRound)._2).foreach(p => addEnemy(new Enemy(30, 5, enemyPath))) // second has lower hp but is faster

    (1 to rounds(currentRound)._2).foreach(p => addEnemy(new Enemy(100, 4, enemyPath))) // third one tanky

    (1 to rounds(currentRound)._2).foreach(p => addEnemy(new Enemy(5, 20, enemyPath))) // fouth enemytype a nimble one


  }


  private var towerSelected = false
  def selectTower() = towerSelected = true
  def unselectTower() = towerSelected = false
  def selected = towerSelected



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
  }
  def step() = {
    enemies.foreach(_.move())
    towers.foreach(_.attack(enemies))
    var toBeDeleted = Buffer[Enemy]()
    enemies.foreach(p => if (p.healthPoints <= 0) toBeDeleted += p)
    enemies --= toBeDeleted
    if(enemies.isEmpty) {
      paused = true

    }
  }

  def update(g : Graphics2D) = {
    towers.foreach(_.draw(g))
    enemies.foreach(_.draw(g))
  }
  def drawOnMouse(g : Graphics2D, x : Int, y : Int) = {
    var r = 50
    g.setColor(Color.orange)
    g.fillRoundRect((x-(r/2)),(y-(r/2)), r, r, r , r)
    g.setColor(new Color(1f,0f,0f,.5f))
    g.fillRoundRect((x-(type1Radius/2)),(y-(type1Radius/2)), type1Radius, type1Radius, type1Radius , type1Radius)
  }



}
