package skills.passive

import config.ExcludeFromJacocoGeneratedReport

/**
 * View glasses passive skill class. Object that gives additional range.
 */
class ViewGlasses(): PassiveSkill("View glasses") {

    @ExcludeFromJacocoGeneratedReport("This passive has no impact on flat damage.")
    override fun additionalDamageFlat(): Double = 0.0

    @ExcludeFromJacocoGeneratedReport("This passive has no impact on damage multiplier.")
    override fun additionalDamageMultiplier(): Double = 1.0

    override fun additionalRange(): Double = 100.0

    @ExcludeFromJacocoGeneratedReport("This passive has no impact on speed.")
    override fun additionalSpeed(): Double = 0.0
}