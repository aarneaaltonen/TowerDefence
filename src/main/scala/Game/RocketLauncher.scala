package Game

import java.awt.{Color, Graphics2D}

class RocketLauncher(position : Pos) extends Tower(50, position, 500, 250, 50) {
  def draw(g : Graphics2D) = {
    g.setColor(Color.yellow)
    g.fillRoundRect((position.x-(r/2)).toInt,(position.y-(r/2)).toInt, r, r, r , r)
    if (isSelected) {
      g.setColor(new Color(0f,1f,0f,.3f))
      g.fillRoundRect((position.x-(range/2)).toInt,(position.y-(range/2)).toInt, range, range, range , range)
    }
  }

}
