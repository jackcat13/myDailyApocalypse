import com.soywiz.korge.gradle.*

val assertjVersion: String by project

buildscript {
	val korgePluginVersion: String by project

	repositories {
		mavenLocal()
		mavenCentral()
		google()
		maven { url = uri("https://plugins.gradle.org/m2/") }
	}
	dependencies {
		classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
	}
}

plugins{
	java
	jacoco
}

tasks.jacocoTestReport {
	dependsOn(tasks.findByName("jvmTest"))
}

apply<KorgeGradlePlugin>()

korge {
	group = "com.jackcat13.rogueLike"
	id = "myDailyApocalypse"
	targetAll()
}
