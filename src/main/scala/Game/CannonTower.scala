package Game

import java.awt.geom.{Ellipse2D, RoundRectangle2D}
import java.awt.{Color, Graphics2D}
import math._

/**
 * MinigunTower is a tower with fixed parameters for damage, range, cost and attackspeed
 * such as
 * Damage = 50
 * Range = 120
 * Cost = 100
 * Attack speed = 5
 *
 * @param position given by player
 */

class CannonTower(position : Pos) extends Tower(50, position, 120, 70, 80) {


  def drawGun(g: Graphics2D) = {
    val old = g.getTransform
    g.translate(position.x, position.y)
    g.rotate(getTargetDirAngle)
    g.fillRoundRect(-10, -5, 40, 10, 5, 5)
    g.setTransform(old)
  }


  def draw(g : Graphics2D) = {

    g.setColor(Color.green)
    g.fillRoundRect((position.x-(r/2)).toInt,(position.y-(r/2)).toInt, r, r, r , r)
    g.setColor(Color.BLACK)
    drawGun(g)
    if (isSelected) {
      g.setColor(new Color(0f,1f,0f,.3f))
      g.fillRoundRect((position.x-(range/2)).toInt,(position.y-(range/2)).toInt, range, range, range , range)
    }

  }

}
