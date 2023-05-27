import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val springBootVersion = "2.4.6"
	val springDependencyManagementVersion = "1.0.11.RELEASE"
	val kotlinVersion = "1.7.10"
	kotlin("jvm") version kotlinVersion  apply false
	kotlin("plugin.serialization") version kotlinVersion apply false
	kotlin("plugin.spring") version kotlinVersion apply false
	kotlin("plugin.noarg") version kotlinVersion apply false

	id("org.springframework.boot") version springBootVersion apply false
	id("io.spring.dependency-management") version springDependencyManagementVersion apply false
}

allprojects {
	group = "com.freshtyre"
	version = "0.0.1-SNAPSHOT"

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "1.8"
		}
	}

	repositories {
		mavenCentral()
		jcenter()
		maven("https://www.dcm4che.org/maven2/")
		maven("https://dl.bintray.com/kotlin/exposed/")
		maven("https://dl.bintray.com/kotlin/kotlinx/")
		maven("https://dl.bintray.com/kotlin/kotlin-js-wrappers")
	}


}

subprojects {
	apply {
		plugin("io.spring.dependency-management")
	}
}
