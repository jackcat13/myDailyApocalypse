package skills.passive

class RunningShoes(): PassiveSkill("Running shoes") {
    override fun additionalDamageFlat(): Double = 0.0

    override fun additionalDamageMultiplier(): Double = 0.0

    override fun additionalRange(): Double = 0.0

    override fun additionalSpeed(): Double = 3.0
}