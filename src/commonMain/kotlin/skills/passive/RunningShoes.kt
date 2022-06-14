package skills.passive

import config.ExcludeFromJacocoGeneratedReport

class RunningShoes(): PassiveSkill("Running shoes") {

    @ExcludeFromJacocoGeneratedReport("This passive has no impact on flat damage.")
    override fun additionalDamageFlat(): Double = 0.0

    @ExcludeFromJacocoGeneratedReport("This passive has no impact on damage multiplier.")
    override fun additionalDamageMultiplier(): Double = 1.0

    @ExcludeFromJacocoGeneratedReport("This passive has no impact on range.")
    override fun additionalRange(): Double = 0.0

    override fun additionalSpeed(): Double = 3.0
}