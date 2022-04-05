package Game

import java.awt.{Color, Graphics2D}

class Tower(val Damage : Int, var position : Pos, val range : Int) {
  private var r = 50





  def draw(g : Graphics2D) = {
    g.setColor(Color.orange)
    g.fillRoundRect((position.x-(r/2)).toInt,(position.y-(r/2)).toInt, r, r, r , r)

  }











}
