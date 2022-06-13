package skills.passive

class ViewGlasses(): PassiveSkill("View glasses") {
    override fun additionalDamageFlat(): Double = 0.0

    override fun additionalDamageMultiplier(): Double = 0.0

    override fun additionalRange(): Double = 100.0

    override fun additionalSpeed(): Double = 0.0
}