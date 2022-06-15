package skills.passive

import skills.Skill

/**
 * Passive skill abstract class to centralize common behaviors of passive skills.
 * It allows to give power ups to players.
 * @param name Name of the passive skill
 */
abstract class PassiveSkill(override val name: String): Skill(name) {

    /**
     * To give additional flat damage.
     */
    abstract fun additionalDamageFlat(): Double

    /**
     * To multiply damage.
     */
    abstract fun additionalDamageMultiplier(): Double

    /**
     * To give additional flat range
     */
    abstract fun additionalRange(): Double

    /**
     * To give additional flat speed
     */
    abstract fun additionalSpeed(): Double
}