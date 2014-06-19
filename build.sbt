import android.Keys._

android.Plugin.androidBuild

//project name
name := "myproj"

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