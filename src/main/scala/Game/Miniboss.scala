package Game

import java.awt.{Color, Graphics2D}

class Miniboss(path : List[Pos]) extends Enemy(3300, 5, path : List[Pos], 1000) {
  def draw(g : Graphics2D) = {
    g.setColor(new Color(240,240,240))
    g.fillOval((position.x-(10/2)).toInt,(position.y-(10/2)).toInt, 20,20)
    var precentage = healthPoints.toDouble / maxHealth.toDouble
    g.setColor(new Color(136, 0,90))
    g.fillRect((position.x-(50)).toInt, (position.y-(20)).toInt, (precentage*100).toInt, 5)
  }

}
