package skills.passive

import config.ExcludeFromJacocoGeneratedReport

class LightningStrike(override val name: String = "Lightning strike"): PassiveSkill(name) {

    override fun additionalDamageFlat(): Double = 15.0

    @ExcludeFromJacocoGeneratedReport("This passive has no impact on damage multiplier.")
    override fun additionalDamageMultiplier(): Double = 1.0

    @ExcludeFromJacocoGeneratedReport("This passive has no impact on range.")
    override fun additionalRange(): Double = 0.0

    @ExcludeFromJacocoGeneratedReport("This passive has no impact on speed.")
    override fun additionalSpeed(): Double = 0.0
}