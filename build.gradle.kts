import dev.extframework.core.main.main
import dev.extframework.gradle.common.extFramework
import dev.extframework.gradle.publish.ExtensionPublication

plugins {
    kotlin("jvm") version "2.0.21"
    id("dev.extframework") version "1.4"
    id("dev.extframework.common") version "1.1"
}

group = "dev.extframework.extension"
version = "1.0.4-BETA"

repositories {
    mavenCentral()
    extFramework()
    maven {
        url = uri("https://repo.extframework.dev/registry")
    }
}

extension {
    partitions {
        tweaker {
            tweakerClass = "dev.extframework.extension.access.AccessTweaker"
        }
        main {
            extensionClass = "dev.extframework.extension.access.AccessTweaks"
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