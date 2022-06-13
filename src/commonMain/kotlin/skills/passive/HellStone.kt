package skills.passive

class HellStone(): PassiveSkill("Hell stone") {
    override fun additionalDamageFlat(): Double = 0.0

    override fun additionalDamageMultiplier(): Double = 2.0

    override fun additionalRange(): Double = 0.0

    override fun additionalSpeed(): Double = 0.0
}