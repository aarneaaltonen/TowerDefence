package Game

import java.awt.{Color, Graphics2D}

class RocketLauncher(position : Pos) extends Tower(50, position, 500, 250, 50) {
  def drawGun(g: Graphics2D) = {
    val old = g.getTransform
    g.translate(position.x, position.y)
    g.rotate(getTargetDirAngle)
    g.fillRoundRect(-10, -5, 40, 10, 5, 5)
    g.setTransform(old)
  }

  def draw(g : Graphics2D) = {
    g.setColor(Color.yellow)
    g.fillRoundRect((position.x-(r/2)).toInt,(position.y-(r/2)).toInt, r, r, r , r)
    g.setColor(new Color(23))
    drawGun(g)
    if (isSelected) {
      g.setColor(new Color(0f,1f,0f,.3f))
      g.fillRoundRect((position.x-(range/2)).toInt,(position.y-(range/2)).toInt, range, range, range , range)
    }
  }

}
