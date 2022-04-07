package Game

import java.awt.{Color, Graphics2D}

class ThirdEnemy(path : List[Pos]) extends Enemy(150, 4, path : List[Pos], 10) {

  def draw(g : Graphics2D) = {
    g.setColor(Color.pink)
    g.fillOval((position.x-(10/2)).toInt,(position.y-(10/2)).toInt, 10,10)
    var precentage = healthPoints.toDouble / maxHealth.toDouble
    g.setColor(Color.red)
    g.fillRect((position.x-(10)).toInt, (position.y-(20)).toInt, (precentage*20).toInt, 5)
  }

}
