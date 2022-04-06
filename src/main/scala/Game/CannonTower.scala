package Game

import java.awt.{Color, Graphics2D}

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

  def draw(g : Graphics2D) = {
    g.setColor(Color.green)
    g.fillRoundRect((position.x-(r/2)).toInt,(position.y-(r/2)).toInt, r, r, r , r)
    if (isSelected) {
      g.setColor(new Color(1f,0f,0f,.5f))
      g.fillRoundRect((position.x-(range/2)).toInt,(position.y-(range/2)).toInt, range, range, range , range)
    }
  }

}
