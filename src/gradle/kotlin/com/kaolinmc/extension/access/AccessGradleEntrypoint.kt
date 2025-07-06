package com.kaolinmc.extension.access

import com.kaolinmc.kiln.api.KaolinExtension
import com.kaolinmc.kiln.api.GradleEntrypoint
import com.kaolinmc.minecraft.MinecraftGradleEntrypoint

class AccessGradleEntrypoint : GradleEntrypoint {
    override suspend fun configure(
        extension: KaolinExtension,
        helper: GradleEntrypoint.Helper
    ) {
        for (environment in extension.environments) {
            if (environment.contains(MinecraftGradleEntrypoint.minecraftAwareAttrKey)) {
                helper.tweak(environment)
            }
        }
    }
}