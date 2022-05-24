package skills.passive

class LightningStrike(override val name: String = "Lightning strike"): PassiveSkill(name) {

    override fun additionalDamageFlat(): Double = 15.0

    override fun additionalDamageMultiplier(): Double = 1.0

    override fun additionalRange(): Double = 0.0

    override fun additionalSpeed(): Double = 0.0
}