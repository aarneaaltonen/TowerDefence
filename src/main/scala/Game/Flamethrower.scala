package Game

import java.awt.{Color, Graphics2D}
import scala.collection.mutable

class Flamethrower(position : Pos) extends Tower(2, position, 150, 1500, 8) {

  var upgradeCost = 4000

  private var targetDirAngle = math.Pi
  override def getTargetDirAngle = {
    targetDirAngle
  }

  override def attack(enemies : mutable.Buffer[Enemy]): Unit = {
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
        // flamethrower attacks all enemies in range
         closestEnemy.foreach(p => p.takeDamage(damage))
        hasTarget = true

        targetDirAngle = math.atan2((closestEnemy.head.position.y - position.y),(closestEnemy.head.position.x - position.x))
      } else counter += 1
    } else hasTarget = false
  }

  def drawGun(g: Graphics2D) = {
    val old = g.getTransform
    g.translate(position.x, position.y)
    g.rotate(getTargetDirAngle)
    if(hasTarget) {
      g.setColor(new Color(1f,0.02f,0f,.3f))
      g.fillOval(-10,-10,50,20)
    }
    g.setColor(Color.darkGray)
    g.fillRoundRect(-12, -3, 6, 6, 6, 6)
    g.setTransform(old)
  }

  override def upgrade(): Unit = {
    damage = 4
  }

  def draw(g : Graphics2D) = {
    g.setColor(new Color(65, 52, 43))
    g.fillRoundRect((position.x-(r/2)).toInt,(position.y-(r/2)).toInt, r, r, r , r)

    drawGun(g)
    if (isSelected) {
      g.setColor(new Color(0f,1f,0f,.3f))
      g.fillRoundRect((position.x-(range/2)).toInt,(position.y-(range/2)).toInt, range, range, range , range)
    }

  }

}
