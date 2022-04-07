package Game

import java.awt.{Color, Graphics2D}

/**
 * MinigunTower is a tower with fixed parameters for damage, range, cost and attackspeed
 * such as
 * Damage = 10
 * Range = 300
 * Cost = 50
 * Attack speed = 10
 *
 * @param position given by player
 */
class MinigunTower( position : Pos) extends Tower(2, position, 300, 50, 10){
  var animationFrame = 0
  var upgradeCost = 1000

  def drawGun(g: Graphics2D) = {
    val old = g.getTransform
    g.translate(position.x, position.y)
    g.rotate(getTargetDirAngle)
    if (hasTarget) {
      if (animationFrame == 1) {
        g.fillRoundRect(-12, -6, 40, 4, 5, 5)
        g.fillRoundRect(-12, 4, 40, 4, 5, 5)
        animationFrame += 1
      } else if (animationFrame == 2) {
        g.fillRoundRect(-13, -6, 40, 4, 5, 5)
        g.fillRoundRect(-13, 4, 40, 4, 5, 5)
        animationFrame = 0
      } else {
        g.fillRoundRect(-11, -6, 40, 4, 5, 5)
        g.fillRoundRect(-11, 4, 40, 4, 5, 5)
        animationFrame += 1
      }

    } else {
      g.fillRoundRect(-11, -6, 40, 4, 5, 5)
      g.fillRoundRect(-11, 4, 40, 4, 5, 5)
    }
    g.setTransform(old)
  }

  override def upgrade(): Unit = {
    damage += 20
    range += 50
  }


  def draw(g : Graphics2D) = {
    g.setColor(Color.orange)
    g.fillRoundRect((position.x-(r/2)).toInt,(position.y-(r/2)).toInt, r, r, r , r)
    g.setColor(Color.darkGray)
    drawGun(g)
    if (isSelected) {
      g.setColor(new Color(0f,1f,0f,.3f))
      g.fillRoundRect((position.x-(range/2)).toInt,(position.y-(range/2)).toInt, range, range, range , range)
    }
  }





}
