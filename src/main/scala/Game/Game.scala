package Game
import java.awt.Graphics2D
import scala.collection.mutable.Buffer

class Game(var healtPoints : Int) {

  var coins = 0

  var towers = Buffer[Tower]()
  var enemies = Buffer[Enemy]()

  var currentRound = 0

  val enemyPath : List[(Pos)] = List(new Pos(27,75),
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


  private var towerSelected = false
  def selectTower() = towerSelected = true
  def unselectTower() = towerSelected = false
  def selected = towerSelected



  def placeTower(torni : Tower) : Unit = {
    towers += torni
  }

  def addEnemy(enemy : Enemy) = {
    enemies += enemy
  }
  def step() = {
    enemies.foreach(_.move())
  }

  def update(g : Graphics2D) = {
    towers.foreach(_.draw(g))
    enemies.foreach(_.draw(g))
  }



}
