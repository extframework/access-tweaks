import com.kaolinmc.core.main.main
import com.kaolinmc.gradle.common.*
import com.kaolinmc.gradle.common.gradlePluginApi
import com.kaolinmc.kiln.publish.ExtensionPublication

plugins {
    kotlin("jvm") version "2.0.21"
    id("kaolin.kiln") version "0.1"
    id("com.kaolinmc.common") version "0.1"
}

group = "com.kaolinmc.extension"
version = "1.0.6-BETA"

repositories {
    mavenCentral()
    kaolin()
    maven {
        url = uri("https://repo.kaolinmc.com/registry")
    }
}

extension {
    partitions {
        tweaker {
            tweakerClass = "com.kaolinmc.extension.access.AccessTweaker"
        }
        gradle {
            entrypointClass = "com.kaolinmc.extension.access.AccessGradleEntrypoint"
            dependencies {
                implementation(gradlePluginApi())
            }
        }
    }
    metadata {
        name = "Access tweaks"
        description = "An internal API allowing developers access to private Minecraft packages."
        developers.add("kaolin")
        app = "minecraft"
    }
}

publishing {
    publications {
        create("prod", ExtensionPublication::class.java)
    }
    repositories {
        maven {
            url = uri("https://repo.kaolinmc.com")
            credentials {
                password = properties["creds.ext.key"] as? String
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}