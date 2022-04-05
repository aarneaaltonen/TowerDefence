package Game

import java.awt.{Color, Graphics2D}

class Tower(val Damage : Int, var position : Pos, val range : Int) {
  private var r = 24





  def draw(g : Graphics2D) = {
    g.fillRect(position.x-(r/2),position.y-(r/2), r,r)
    g.setColor(Color.orange)
  }











}
