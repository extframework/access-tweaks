package com.kaolinmc.extension.access

import com.kaolinmc.gradle.api.ExtframeworkExtension
import com.kaolinmc.gradle.api.GradleEntrypoint
import com.kaolinmc.minecraft.MinecraftGradleEntrypoint

class AccessGradleEntrypoint : GradleEntrypoint {
    override suspend fun configure(
        extension: ExtframeworkExtension,
        helper: GradleEntrypoint.Helper
    ) {
        for (environment in extension.environments) {
            if (environment.contains(MinecraftGradleEntrypoint.minecraftAwareAttrKey)) {
                helper.tweak(environment)
            }
        }
    }
}