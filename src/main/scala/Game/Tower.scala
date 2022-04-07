package Game

import java.awt.{Color, Graphics2D}

import scala.collection.mutable

abstract class Tower(var damage : Int, var position : Pos, var range : Int,var cost :Int,var attackSpeed : Int) {
  //every tower has radius of r = 50 (for now)
  var r = 50
  var isSelected = false

  var upgradeCost : Int

  def selectTower() = isSelected = true
  def unselectTower() = isSelected = false
  var hasTarget = false


  var counter = 0
  private var targetDirAngle = math.Pi
  def getTargetDirAngle = {
    targetDirAngle
  }


  def attack(enemies : mutable.Buffer[Enemy]) = {
    var inRange = false
    var closestEnemy = mutable.Buffer[Enemy]()
    for (enemy <- enemies) {
      if (enemy.position.distance(position) < range/2) {
        inRange = true
        closestEnemy += enemy
      }
    }
    if (closestEnemy.nonEmpty) {
      if (counter ==attackSpeed) {
        counter = 0
         closestEnemy.head.takeDamage(damage)
        hasTarget = true

        targetDirAngle = math.atan2((closestEnemy.head.position.y - position.y),(closestEnemy.head.position.x - position.x))
      } else counter += 1
    } else hasTarget = false
  }
  def upgrade() = {
    this.range += 50
  }





  def draw(g : Graphics2D) : Unit
}
