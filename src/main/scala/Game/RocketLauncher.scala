package Game

import java.awt.geom.{Ellipse2D, Path2D, RoundRectangle2D}
import java.awt.{Color, Graphics2D}
import scala.collection.mutable

class RocketLauncher(position : Pos) extends Tower(15, position, 500, 250, 60) {
  var minRange = 230

  var upgradeCost = 2000

  def drawGun(g: Graphics2D) = {
    val old = g.getTransform
    g.translate(position.x, position.y)
    g.rotate(getTargetDirAngle)
    g.fillRoundRect(-10, -5, 40, 10, 5, 5)
    g.setTransform(old)
  }
  private var targetDirAngle = math.Pi
  override def getTargetDirAngle = {
    targetDirAngle
  }

  override def attack(enemies: mutable.Buffer[Enemy]): Unit = {
    var inRange = false
    var closestEnemy = mutable.Buffer[Enemy]()
    for (enemy <- enemies) {
      if (enemy.position.distance(position) < range/2 && enemy.position.distance(position) > minRange/2) {
        inRange = true
        closestEnemy += enemy
      }
    }
    if (closestEnemy.nonEmpty) {
      if (counter ==attackSpeed) {
        counter = 0
         closestEnemy.take(3).foreach(p => p.takeDamage(damage))
        hasTarget = true
        targetDirAngle = math.atan2((closestEnemy.head.position.y - position.y),(closestEnemy.head.position.x - position.x))
      } else counter += 1
    } else hasTarget = false
  }

  override def upgrade(): Unit = {
    damage = 50
  }

  def draw(g : Graphics2D) = {
    def shape = {
      var path = new Path2D.Double(Path2D.WIND_EVEN_ODD)
      var innerCircle = new Ellipse2D.Double((position.x-(minRange/2)).toInt,(position.y-(minRange/2)).toInt,minRange,minRange)
      var outerCircle = new Ellipse2D.Double((position.x-(range/2)).toInt,(position.y-(range/2)).toInt,range,range)
      path.append(outerCircle, false)
      path.append(innerCircle, false)
      path

    }
    g.setColor(Color.yellow)
    g.fillRoundRect((position.x-(r/2)).toInt,(position.y-(r/2)).toInt, r, r, r , r)
    g.setColor(new Color(23))
    drawGun(g)
    if (isSelected) {
      g.setColor(new Color(0f,1f,0f,.3f))
      g.fill(shape)
      //g.fillRoundRect((position.x-(range/2)).toInt,(position.y-(range/2)).toInt, range, range, range , range)
    }
  }

}
