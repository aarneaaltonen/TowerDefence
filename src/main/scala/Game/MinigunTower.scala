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

  def draw(g : Graphics2D) = {
    g.setColor(Color.orange)
    g.fillRoundRect((position.x-(r/2)).toInt,(position.y-(r/2)).toInt, r, r, r , r)
    if (isSelected) {
      g.setColor(new Color(1f,0f,0f,.5f))

      g.fillRoundRect((position.x-(range/2)).toInt,(position.y-(range/2)).toInt, range, range, range , range)
    }
  }





}
