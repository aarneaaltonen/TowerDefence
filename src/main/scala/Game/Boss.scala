package Game

import java.awt.{Color, Graphics2D}

class Boss(path : List[Pos]) extends Enemy(25000, 5, path : List[Pos], 10) {

  def draw(g : Graphics2D) = {
    g.setColor(Color.gray)
    g.fillOval((position.x-(30/2)).toInt,(position.y-(30/2)).toInt, 30,30)
    var precentage = healthPoints.toDouble / maxHealth.toDouble
    g.setColor(Color.red)
    g.fillRect((position.x-(100)).toInt, (position.y-(20)).toInt, (precentage*200).toInt, 5)
  }

}
