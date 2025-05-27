import dev.extframework.core.main.main
import dev.extframework.gradle.common.archives
import dev.extframework.gradle.common.boot
import dev.extframework.gradle.common.dm.jobs
import dev.extframework.gradle.common.extFramework
import dev.extframework.gradle.common.toolingApi
import dev.extframework.gradle.publish.ExtensionPublication
import kotlin.jvm.java

plugins {
    kotlin("jvm") version "2.0.21"
    id("dev.extframework") version "1.3.3"
    id("dev.extframework.common") version "1.0.53"
}

group = "dev.extframework.extension"
version = "1.0.3-BETA"

repositories {
    mavenCentral()
    extFramework()
    maven {
        url = uri("https://repo.extframework.dev/registry")
    }
    mavenLocal()
}

extension {
    partitions {
        tweaker {
            dependencies {
                implementation("dev.extframework.core:minecraft-api:1.0-BETA")
                implementation("dev.extframework.core:app-api:1.0-BETA")
                toolingApi()
                jobs()
                archives()
                boot()
            }
            tweakerClass = "dev.extframework.extension.access.AccessTweaker"
        }
        main {
            extensionClass = "dev.extframework.extension.access.AccessTweaks"
            dependencies {
                implementation("dev.extframework.core:entrypoint:1.0-BETA")
            }
        }
    }
    metadata {
        name = "Access tweaks"
        description = "An internal API allowing developers access to private Minecraft packages."
        developers.add("extframework")
        app = "minecraft"
    }
}

publishing {
    publications {
        create("prod", ExtensionPublication::class.java)
    }
    repositories {
        maven {
            url = uri("https://repo.extframework.dev")
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