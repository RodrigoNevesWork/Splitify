import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.4"
	id("io.spring.dependency-management") version "1.0.14.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "Splitify"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {

	implementation ("javax.validation:validation-api:2.0.1.Final")
	implementation ("org.hibernate.validator:hibernate-validator:6.0.13.Final")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.security:spring-security-core:5.7.3")

	//for JWT implementation
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// for JDBI
	implementation("org.jdbi:jdbi3-core:3.33.0")
	implementation("org.jdbi:jdbi3-kotlin:3.33.0")
	implementation("org.jdbi:jdbi3-postgres:3.33.0")
	implementation("org.postgresql:postgresql:42.5.0")
	testImplementation(kotlin("test"))
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
