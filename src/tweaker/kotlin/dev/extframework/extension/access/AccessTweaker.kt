package dev.extframework.extension.access

import dev.extframework.core.instrument.instrumentAgentsAttrKey
import dev.extframework.core.minecraft.api.MappingNamespace
import dev.extframework.core.minecraft.environment.mappingTargetAttrKey
import dev.extframework.core.minecraft.environment.minecraft
import dev.extframework.tooling.api.environment.ExtensionEnvironment
import dev.extframework.tooling.api.environment.ValueAttribute
import dev.extframework.tooling.api.tweaker.EnvironmentTweaker

class AccessTweaker : EnvironmentTweaker {
    override fun tweak(environment: ExtensionEnvironment) {
        // TODO temporary, eventually dont want to rely on fabric continuing to provide mappings
        if (environment[mappingTargetAttrKey].value == MappingNamespace("mojang", "obfuscated")) {
            // TODO Hacky
            val version = environment.minecraft.version
            environment += if (version != "1.8.9") {
                ValueAttribute(mappingTargetAttrKey,MappingNamespace("mojang", "deobfuscated"))
            } else {
                ValueAttribute(mappingTargetAttrKey,MappingNamespace("mcp-legacy", "deobfuscated"))
            }
        }

        environment[instrumentAgentsAttrKey].add(AccessWidenerMixinAgent())
    }
}