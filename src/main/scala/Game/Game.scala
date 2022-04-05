package Game
import java.awt.Graphics2D
import scala.collection.mutable.Buffer

class Game(var healtPoints : Int) {

  var coins = 0

  var towers = Buffer[Tower]()

  var currentRound = 0


  private var towerSelected = false
  def selectTower() = towerSelected = true
  def unselectTower() = towerSelected = false
  def selected = towerSelected



  def placeTower(torni : Tower) : Unit = {
    towers += torni
  }

  def update(g : Graphics2D) = {
    towers.foreach(_.draw(g))
  }

}
