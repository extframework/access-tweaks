import dev.extframework.gradle.common.archives
import dev.extframework.gradle.common.boot
import dev.extframework.gradle.common.dm.jobs
import dev.extframework.gradle.common.toolingApi
import dev.extframework.gradle.extframework
import dev.extframework.gradle.publish.ExtensionPublication
import kotlin.jvm.java

plugins {
    kotlin("jvm") version "2.0.21"
    id("dev.extframework.mc") version "1.2.31"
    id("dev.extframework.common") version "1.0.49"
}

group = "dev.extframework.extension"
version = "1.0.1-BETA"

repositories {
    mavenCentral()
    extframework()
    maven {
        url = uri("https://repo.extframework.dev/registry")
    }
}

tasks.launch {
    targetNamespace.set("mojang:obfuscated")
}

extension {
    extensions {
        require("dev.extframework.integrations:fabric-mappings:1.0-BETA")
        require("dev.extframework.extension:mcp-mappings:1.0.4-BETA")
    }
    partitions {
        main {
            extensionClass = "dev.extframework.extension.access.AccessTweaks"
            dependencies {
                implementation("dev.extframework.core:entrypoint:1.0-BETA")
            }
        }
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
    }
    metadata {
        name = "Access tweaks"
        description = "An internal API allowing developers access to private Minecraft packages."
        developers.add("extframework")
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