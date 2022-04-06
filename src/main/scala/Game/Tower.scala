package Game

import java.awt.{Color, Graphics2D}

import scala.collection.mutable

abstract class Tower(val damage : Int, var position : Pos, val range : Int, cost :Int = 50,val attackSpeed : Int) {
  //every tower has radius of r = 50 (for now)
  var r = 50
  var isSelected = false

  def selectTower() = isSelected = true
  def unselectTower() = isSelected = false


  var counter = 0


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
      } else counter += 1


    }
  }





  def draw(g : Graphics2D) : Unit
}
