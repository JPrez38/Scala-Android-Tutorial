package com.example.myapp

import org.scaloid.common._
import android.graphics.Color


class MyActivity extends SActivity {
  onCreate {
    contentView = new SVerticalLayout {
      style {
        case t: STextView => t textSize 10.dip
      }
      STextView("It works")
      STextView("I can’t wait to use scala with android")
      STextView("Scala is the best") textSize 15.dip // overriding
      SButton("To the Scala Activity",startActivity[AwesomeScalaClass]).textColor(Color.BLUE)
      SButton("To the Java Activity",startActivity[SomewhatCoolJavaClass]).textColor(Color.RED)
    } padding 20.dip
  }
}