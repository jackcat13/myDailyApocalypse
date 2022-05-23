package skills.passive

class LightningStrike(override val name: String = "Lightning strike"): PassiveSkill(name) {

    override fun additionalDamageFlat(): Double = 15.0

    override fun additionalDamagePercent(): Double = 1.0
}