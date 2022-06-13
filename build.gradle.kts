import com.soywiz.korge.gradle.*

buildscript {
	val korgePluginVersion = "2.7.0"

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

	val coverageSourceDirs = arrayOf(
			"src/commonMain"
	)
	val classFiles = File("${buildDir}/classes/kotlin/jvm/").walkBottomUp().toSet()
	classDirectories.setFrom(classFiles)
	sourceDirectories.setFrom(files(coverageSourceDirs))
	executionData.setFrom(files("${buildDir}/jacoco/jvmTest.exec"))
	reports{
		html.required.set(true)
		csv.required.set(true)
	}
}

apply<KorgeGradlePlugin>()

korge {
	group = "com.jackcat13.rogueLike"
	id = "myDailyApocalypse"
	targetAll()
}
