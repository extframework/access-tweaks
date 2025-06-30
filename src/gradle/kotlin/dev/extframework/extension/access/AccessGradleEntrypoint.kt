package dev.extframework.extension.access

import dev.extframework.gradle.api.ExtframeworkExtension
import dev.extframework.gradle.api.GradleEntrypoint
import dev.extframework.minecraft.MinecraftGradleEntrypoint

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