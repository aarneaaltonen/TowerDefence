package Game

import java.awt.{Color, Graphics2D}

import scala.collection.mutable

class Tower(val damage : Int, var position : Pos, val range : Int) {
  var r = 50
  var isSelected = false

  def selectTower() = isSelected = true
  def unselectTower() = isSelected = false

  var attackSpeed = 20
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





  def draw(g : Graphics2D) = {
    g.setColor(Color.orange)
    g.fillRoundRect((position.x-(r/2)).toInt,(position.y-(r/2)).toInt, r, r, r , r)
    if (isSelected) {
      g.setColor(new Color(1f,0f,0f,.5f))

      g.fillRoundRect((position.x-(range/2)).toInt,(position.y-(range/2)).toInt, range, range, range , range)
    }

  }











}
