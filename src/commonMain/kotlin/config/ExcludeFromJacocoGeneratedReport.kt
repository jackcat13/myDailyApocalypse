package config;

import kotlin.annotation.AnnotationRetention.RUNTIME

@Retention(RUNTIME)
annotation class ExcludeFromJacocoGeneratedReport(val reason: String = "Not provided")