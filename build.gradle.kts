plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "org.pycharm-plugin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://www.jetbrains.com/intellij-repository/releases")
    maven("https://www.jetbrains.com/intellij-repository/snapshots")
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.jetbrains:annotations:26.0.1")
}

// Configure Gradle IntelliJ Plugin
intellij {
    version.set("2024.2.4") // Specify the IntelliJ version
    type.set("PY") // Target IDE Platform
    plugins.set(listOf(/* Plugin Dependencies */)) // Specify any required plugins
}

sourceSets["main"].java.srcDirs("src/main/gen")

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("232") // Adjust based on your plugin's compatibility
        untilBuild.set("242.*") // Adjust based on your plugin's compatibility
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    // Add a task to compile with deprecation warnings
    withType<JavaCompile> {
        options.compilerArgs.add("-Xlint:deprecation") // Enable deprecation warnings
    }
}