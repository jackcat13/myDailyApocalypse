package skills.passive

import config.ExcludeFromJacocoGeneratedReport

/**
 * Hell stone passive skill class. Object that double the player damage.
 */
class HellStone(): PassiveSkill("Hell stone") {

    @ExcludeFromJacocoGeneratedReport("This passive has no impact on flat damage.")
    override fun additionalDamageFlat(): Double = 0.0

    override fun additionalDamageMultiplier(): Double = 2.0

    @ExcludeFromJacocoGeneratedReport("This passive has no impact on range.")
    override fun additionalRange(): Double = 0.0

    @ExcludeFromJacocoGeneratedReport("This passive has no impact on speed.")
    override fun additionalSpeed(): Double = 0.0
}