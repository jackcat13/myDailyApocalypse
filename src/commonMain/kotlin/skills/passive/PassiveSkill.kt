package skills.passive

import skills.Skill

abstract class PassiveSkill(override val name: String): Skill(name) {

    abstract fun additionalDamageFlat(): Double
    abstract fun additionalDamagePercent(): Double
}