/* adds sbt-android plugin for the android project
 * allows for scala code in android
 */
addSbtPlugin("com.hanhuy.sbt" % "android-sdk-plugin" % "1.2.16")

// adds plugin for intelij to be able to build sbt projects
addSbtPlugin("com.github.mpeltonen" %"sbt-idea" % "1.6.0")