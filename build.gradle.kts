import com.soywiz.korge.gradle.*

buildscript {
	val korgePluginVersion = "3.0.1"

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
		xml.required.set(true)
	}
}

apply<KorgeGradlePlugin>()

korge {
	group = "com.jackcat13.rogueLike"
	id = "myDailyApocalypse"
	targetAll()

	authorName = "jackCat13"
	authorEmail = "christophehenry11@gmail.com"
	authorHref = "https://chris-henry.notion.site/chris-henry/My-portfolio-def539df86f9498ba50ae85669f659ef"
}
