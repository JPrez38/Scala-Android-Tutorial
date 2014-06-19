package com.example.myapp

import android.graphics.Color
import org.scaloid.common._

class AwesomeScalaClass extends SActivity {
  onCreate {
    contentView = new SVerticalLayout {
      style {
        case t: STextView => t textSize 30.dip
      }
      STextView("Hello From Scala")
      SButton("Back to Main Activity",startActivity[MyActivity]).textColor(Color.BLUE)
      SButton("To the Java Activity",startActivity[SomewhatCoolJavaClass]).textColor(Color.RED)
    }
  }
}
