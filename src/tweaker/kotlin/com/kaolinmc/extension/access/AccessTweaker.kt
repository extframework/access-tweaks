package com.kaolinmc.extension.access

import com.kaolinmc.core.instrument.instrumentAgentsAttrKey
import com.kaolinmc.core.minecraft.api.MappingNamespace
import com.kaolinmc.core.minecraft.environment.mappingTargetAttrKey
import com.kaolinmc.core.minecraft.environment.minecraft
import com.kaolinmc.tooling.api.environment.ExtensionEnvironment
import com.kaolinmc.tooling.api.environment.ValueAttribute
import com.kaolinmc.tooling.api.tweaker.EnvironmentTweaker

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