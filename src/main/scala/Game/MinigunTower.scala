package Game

import java.awt.geom.Ellipse2D
import java.awt.{BasicStroke, Color, Graphics2D}
import scala.util.Random

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
  private var animationFrame = 0
  var upgradeCost = 1000
  var upgradeCost2 = 100000
  var upgradedTwice = false
  val random = scala.util.Random

  override def getUpgradeCost: Int = {
    if(upgraded) {
      upgradeCost2
    } else upgradeCost
  }


  def drawGun(g: Graphics2D) = {
    val old = g.getTransform
    g.translate(position.x, position.y)
    g.rotate(getTargetDirAngle)
    if (hasTarget) {
      g.setColor(Color.darkGray)
      if (animationFrame == 1) {
        g.fillRoundRect(-12, -6, 40, 4, 5, 5)
        g.fillRoundRect(-12, 4, 40, 4, 5, 5)
        animationFrame += 1
      } else if (animationFrame == 2) {
        g.fillRoundRect(-13, -6, 40, 4, 5, 5)
        g.fillRoundRect(-13, 4, 40, 4, 5, 5)
        animationFrame = 0
      } else {
        if(upgraded) {
          g.setColor(new Color(162,138,90))
        }
        g.fillRoundRect( r/2 +random.nextInt(math.max(1,(lenFromTarget.toInt-r/2))),random.nextInt(2), 4, 4, 4 , 4)
        g.setColor(Color.darkGray)
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

      damage = 5
      range = 400

      upgraded = true

  }
  override def upgrade2() = {
    damage = 400
    range = 1000

    upgradedTwice = true
  }


  def draw(g : Graphics2D) = {
    g.setColor(Color.orange)
    g.fillRoundRect((position.x-(r/2)).toInt,(position.y-(r/2)).toInt, r, r, r , r)
    if(upgradedTwice) {
      g.setColor(new Color(103, 74, 147))
      g.fillRoundRect((position.x-(r/2)).toInt,(position.y-(r/2)).toInt, r, r, r , r)
    }
    if(upgraded) {
      g.setColor(new Color(162, 138, 80))
      g.setStroke(new BasicStroke(4))
      g.draw(new Ellipse2D.Double((position.x-(r/2)).toInt,(position.y-(r/2)).toInt,r,r))
    }


    g.setColor(Color.darkGray)
    drawGun(g)
    if (isSelected) {
      g.setColor(new Color(0f,1f,0f,.3f))
      g.fillRoundRect((position.x-(range/2)).toInt,(position.y-(range/2)).toInt, range, range, range , range)
    }
  }





}
