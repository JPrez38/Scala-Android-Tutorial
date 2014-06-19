Scala-Android-Tutorial-for-Beginners
====================================

Step by Step walk through for programmers wanting to use Scala for android development

 Scala has started to become my personal favorite language, however it is a very difficult language for beginners and it is very hard to find how to set up your development environment, especially for setting it up for use with android. This guide will go step by step on how to set up Scala for use with android. This is intended to be an easy guide even for complete beginners to Scala and it will cover all steps. I find some steps in tutorials that others may skip because they think it is obvious to sometimes cause the worst setup issues. You should have prior java experience and a decent understand of how android works and some Scala knowledge is recommend, but other than that, anyone should be able to jump in to use Scala for android.

I do focus on OSX in this tutorial, however if you are using linux, you will be able to follow along

**Installation**

_Android specific steps_

Step 1: Install java from oracle. Currently Kit-Kat supports java 7, but it is recommend to download java jdk 7 and 6.

Step 2: Install Android. You can use a package manager such as brew or go to [here](http://developer.android.com/sdk/index.html). If you downloaded Android from the website, you will need to set the path in your `~/.bash_profile`. Also, you should set the `ANDROID_HOME` environment variable to the path to your sdk.

Step 3: (Recommended) Install [Intellij](http://www.jetbrains.com/idea/) for development. You can use the command line, but especially with scala Intellij is the best for android development. Other plugins for eclipse and android studio are less developed and Intellij should be used if you want to use an IDE over the command line. 

_Scala specific steps_

Step 4: Install Scala. You can use brew install Scala or another package manager or simply download from http://www.scala-lang.org. If you download it manually, you will again need to set the path in your `~/.bash_profile` file to `/path/to/scala/scala_version/bin`. Verify Scala works by typing `source ~/.bash_profile` and then typing Scala into the terminal. If it pulls up the Scala interpreter, it is successfully installed. 

Step 5: Install SBT. Using Scala with android is best when you use SBT to build your projects instead of traditional Android methods. SBT stands for simple build tool and is used for most Scala projects. You can either brew install SBT or download sbt from the [SBT website](http://www.scala-sbt.org/download.html/). Again, if you install SBT manually, you need to set your path in your profile. Verify it works by sourcing your profile and typing sbt into the command line. 

**Creating the Project**

*Note: Here i will walk through the intelij setup of the project mostly. Setting up from the command line will be relatively straightforward however and still should follow the flow as used in Intellij.

Step 1: Create the android project. Use android create project, build your own or the best option is to create it from within Intellij itself. If within Intellij, choose Application module within Android and name your application, give it a package name, and create a hello world activity that will be your main activity upon launch. Next, set the project location on your local machine and set the Project SDK to the location of your android environment (Note: if you used brew, this will likely be at `/usr/local/Cellar/android-sdk`). It should then show Android API 19(or whatever version of Android you are using). 

Now we will make the project an SBT project

Step 2: First, create a new folder called project in the root directory of the project, i.e. myapp/ from either the command line or from intellij. 

Step 3: Cd into the project folder and create a file called build.properties and another file called plugins.sbt

Step 4: In the build.properties, 'type sbt.version=0.13.0' (or whatever version of sbt you are using)

Step 5: In plugins.sbt, add this:

```
// allows for scala code in android 
addSbtPlugin("com.hanhuy.sbt" % "android-sdk-plugin" % "1.2.16")

// adds plugin for intelij to be able to build sbt projects
addSbtPlugin("com.github.mpeltonen" %"sbt-idea" % “1.6.0")
```

The first plugin allows for android to function with the SBT build. The second plugin allows for integration within intellij so you can build projects from within the IDE.

Step 6: Cd back to the root directory of the project and create a file called build.sbt. Within it put:

```
import android.Keys._

android.Plugin.androidBuild

//project name
name := “myproj"

//specifies scala versions
scalaVersion := "2.11.1"

scalacOptions in Compile += "-feature"

//version of android
platformTarget in Android := "android-19"

run <<= run in Android

install <<= install in Android

proguardCache in Android ++= Seq(
  ProguardCache("org.scaloid") % "org.scaloid"
)

proguardOptions in Android ++= Seq("-dontobfuscate", "-dontoptimize", "-dontwarn scala.collection.mutable.**")

//enables scalaoid framework
libraryDependencies += "org.scaloid" %% "scaloid" % "3.4-10"
```

This file specifies the Scala version you are using, android target, compiling options and it also allows the scaloid framework to be integrated into the SBT build. Essentially this file is used for specifying the build settings for SBT when building the Scala Android project.

Step 7: From the command line and in the root directory, type sbt. All the project dependencies will be fetched and downloaded. Next type gen-idea. This will allow the project files to be generated for intellij.

Step 8: Install the intellij SBT and Scala plugins. Go to Preferences > Plugins and click install JetBrains plugin. Find SBT and Scala and download them.

Step 9: Change the Intellij build process. Click on Run > Edit Configurations. Set the module to myproj and specify the default launch activity. Then at the bottom where it says before launch, remove make and instead click on the plus and choose the SBT option. A window will pop up asking you specify the sbt command. Here type in `android:package`. After you hit enter, it should say in the before launch window `Run SBT Action ‘android:package’`. Apply the changes and hit ok. 

Step 10: Go to your src folder and find your package that you created. Find the main activity (mine is MyActivity) and delete the java file. Create a new scala file in that same package with the same name as the java File. 

Step 11: If you are wanting to use scaloid, your file may look like this. You may also design a traditional xml based file in the res folder and structure the Scala code around that but I personally find [Scaloid](https://github.com/pocorall/scaloid) easy to use and it works well. 
```
package com.example.myapp

import org.scaloid.common._
import android.graphics.Color

class MyActivity extends SActivity {
  onCreate {
    contentView = new SVerticalLayout {
      style {
        case b: SButton => b.textColor(Color.RED)
        case t: STextView => t textSize 10.dip
      }
      STextView(“It works")
      STextView(“Can’t wait to use scala with android")
      STextView(“Scala is the best") textSize 15.dip // overriding
    } padding 20.dip
  }
}
```

Step 12: Run the app. 

Step 13: Add more activities. Just to show you how Java and Scala can run together, I will add both a java activity and a Scala activity that can easily transition between each other. 

Add this to the Main Activity
```
SButton("To the Scala Activity",startActivity[AwesomeScalaClass]).textColor(Color.BLUE) 
SButton("To the Java Activity",startActivity[SomewhatCoolJavaClass]).textColor(Color.RED)
```

Create another Scala activity and make it look like this:
```
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
```

Create a Java class and an XML layout file in the `res/layout/` folder called javaxml
```
package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SomewhatCoolJavaClass extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.javaxml);

        final Button scalaButton = (Button)findViewById(R.id.scalaButton);
        scalaButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent scalaIntent = new Intent(v.getContext(),AwesomeScalaClass.class);
                                startActivityForResult(scalaIntent,0);
                    }
                }
        );

        final Button mainButton = (Button)findViewById(R.id.mainButton);
        mainButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mainIntent = new Intent(v.getContext(),MyActivity.class);
                        startActivityForResult(mainIntent,0);
                    }
                }
        );

    }
}
```

```
<?xml version="1.0" encoding="utf-8"?>

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:orientation="vertical"
              android:layout_width="match_parent"
              android:layout_height="match_parent">
    <TextView
            android:id="@+id/textView1"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:text="Hello From Java"
            android:textSize="30dp"/>
    <Button
        android:id="@+id/mainButton"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:textColor="#0000FF"
        android:text="Back to Main Activity" />
    <Button
            android:id="@+id/scalaButton"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:textColor="#FF0000"
            android:text="To Scala Activity" />

</LinearLayout>
```

Make sure to edit your AndroidManifest.xml file and add the following underneath the main activity
```
 <activity android:name=".AwesomeScalaClass"
                  android:label="@string/app_name">
 </activity>
 <activity android:name=".SomewhatCoolJavaClass"
                  android:label="@string/app_name">
 </activity>
```

Run the project again and you can see how seamless it is to transition from a Java class to a Scala class and vice versa.

Additional Resources
* https://github.com/pocorall/scaloid
* https://github.com/pfn/android-sdk-plugin
* http://www.drdobbs.com/mobile/developing-android-apps-with-scala-and-s/240161584?pgno=2
* http://www.drdobbs.com/mobile/developing-android-apps-with-scala-and-s/240162204
* http://www.scala-lang.org
* http://www.scala-lang.org/api/current/
* http://www.vogella.com/tutorials/Android/article.html (In Java, but Vogella has excellent Android resources)
* http://fxthomas.github.io/android-plugin/ (Uses a different sbt plugin, but after the installation, it is a good getting started guide)
