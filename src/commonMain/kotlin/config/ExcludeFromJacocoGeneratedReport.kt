package config;

import kotlin.annotation.AnnotationRetention.BINARY

@Retention(BINARY)
annotation class ExcludeFromJacocoGeneratedReport(val reason: String = "Not provided")

typealias NoCoverage = ExcludeFromJacocoGeneratedReport