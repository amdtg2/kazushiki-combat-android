package com.kazushiki.combat.ui

enum class ProgramLevel(val label: String) {
    FOUNDATIONS("FOUNDATIONS"),
    INTERMEDIATE("INTERMEDIATE"),
    ADVANCED("ADVANCED")
}

data class ProgramSession(
    val id: String,
    val day: Int,
    val title: String,
    val summary: String,
    val durationMinutes: Int,
    val drillIds: List<String>,
    val restAfterSeconds: Int = 30
) {
    val drillNames: List<String>
        get() = drillIds.map(::displayDrillName)
}

data class ProgramWeek(
    val number: Int,
    val title: String,
    val focus: String,
    val sessions: List<ProgramSession>
)

data class TrainingProgram(
    val id: String,
    val title: String,
    val tagline: String,
    val styleName: String,
    val level: ProgramLevel,
    val summary: String,
    val weeks: List<ProgramWeek>
) {
    val sessions: List<ProgramSession> get() = weeks.flatMap { it.sessions }
    val totalSessions: Int get() = sessions.size
}

private fun s(
    id: String,
    day: Int,
    title: String,
    summary: String,
    minutes: Int,
    vararg drills: String,
    rest: Int = 30
) = ProgramSession(id, day, title, summary, minutes, drills.toList(), rest)

private fun w(number: Int, title: String, focus: String, vararg sessions: ProgramSession) =
    ProgramWeek(number, title, focus, sessions.toList())

private fun displayDrillName(id: String): String {
    val overrides = mapOf(
        "boxing.foundations.stance-guard" to "Stance & Guard",
        "boxing.foundations.jab" to "Jab Mechanics",
        "boxing.foundations.cross" to "Cross Mechanics",
        "boxing.foundations.one-two" to "Jab · Cross",
        "muaythai.foundations.stance-guard" to "Thai Stance & Guard",
        "kickboxing.foundations.stance-guard" to "Kickboxing Stance & Guard",
        "mma.foundations.stance-guard" to "MMA Stance & Guard"
    )
    overrides[id]?.let { return it }
    return id.substringAfterLast('.')
        .split('-')
        .joinToString(" ") { token -> token.replaceFirstChar { it.uppercase() } }
}

object TrainingProgramLibrary {
    val all: List<TrainingProgram> = listOf(
        boxingFoundations,
        boxingIntermediate,
        boxingAdvanced,
        muayThaiFoundations,
        muayThaiIntermediate,
        muayThaiAdvanced,
        kickboxingFoundations,
        kickboxingIntermediate,
        kickboxingAdvanced,
        mmaFoundations,
        mmaIntermediate,
        mmaAdvanced
    )

    fun forStyle(styleName: String): List<TrainingProgram> =
        all.filter { it.styleName == styleName }.sortedBy { it.level.ordinal }

    fun program(id: String): TrainingProgram? = all.firstOrNull { it.id == id }

    private val boxingFoundations = TrainingProgram(
        id = "boxing-foundations",
        title = "Boxing Foundations",
        tagline = "Build clean hands, defense, and footwork that hold up under pressure.",
        styleName = "BOXING",
        level = ProgramLevel.FOUNDATIONS,
        summary = "A 12-session boxing progression that starts with stance and straight punches, adds defensive responsibility, then builds combinations, reactions, and controlled pressure.",
        weeks = listOf(
            w(1, "Build the Base", "Stance, jab quality, straight punches, and balanced exits.",
                s("boxing-foundations-w1-d1", 1, "Jab & Stance", "Build stance, guard, movement, and a clean jab before adding combinations.", 20, "boxing.foundations.stance-guard", "boxing.foundations.jab", "boxing.jab-only-movement"),
                s("boxing-foundations-w1-d2", 2, "Straight Line", "Learn the rear cross on its own, then connect a balanced one-two.", 25, "boxing.foundations.cross", "boxing.foundations.one-two", "boxing.high-output-one-two"),
                s("boxing-foundations-w1-d3", 3, "Guard & Exit", "Learn basic forward, backward, lateral movement and safe exits after straight punches.", 25, "boxing.foundations.footwork", "boxing.foundations.exit-step", "boxing.double-jab-exit")
            ),
            w(2, "Defend, Then Answer", "Head movement, guard recovery, counters, and angle changes.",
                s("boxing-foundations-w2-d1", 1, "Slip Basics", "Learn clean slips first, then add a simple straight return.", 25, "boxing.foundations.slip", "boxing.foundations.slip-return", "boxing.jab-cross-slip"),
                s("boxing-foundations-w2-d2", 2, "Hook Basics", "Build a compact lead hook before adding a simple angle exit.", 30, "boxing.foundations.lead-hook", "boxing.foundations.hook-one-two", "boxing.angle-after-combo"),
                s("boxing-foundations-w2-d3", 3, "Roll & Return", "Learn a basic roll, recover your stance, then answer with a short counter.", 30, "boxing.foundations.roll", "boxing.slip-roll-return", "boxing.foundations.counter-reset")
            ),
            w(3, "Build Combinations", "Longer sequences, speed changes, power placement, and clean resets.",
                s("boxing-foundations-w3-d1", 1, "Fast Hands", "Speed up the fundamentals with short, clean combinations.", 30, "boxing.foundations.one-two", "boxing.one-two-three", "boxing.cross-hook-cross"),
                s("boxing-foundations-w3-d2", 2, "Power Behind the Jab", "Use the jab to set up a balanced cross and basic power combinations.", 30, "boxing.foundations.jab", "boxing.foundations.cross", "boxing.power-cross", "boxing.jab-body-cross"),
                s("boxing-foundations-w3-d3", 3, "Combination Defense", "Finish simple combinations with a slip, roll, guard recovery, or angle exit.", 35, "boxing.jab-cross-slip", "boxing.angle-after-combo", "boxing.combo-defense-round")
            ),
            w(4, "Put It Together", "Decision-making, pressure, conditioning, and confident rounds.",
                s("boxing-foundations-w4-d1", 1, "React & Counter", "Use the fundamentals under light reaction pressure: defend, answer, and reset.", 35, "boxing.foundations.slip-return", "boxing.foundations.counter-reset", "boxing.pull-counter-flow"),
                s("boxing-foundations-w4-d2", 2, "Pressure Rounds", "Keep stance, guard, and clean combinations together as the pace rises.", 40, "boxing.high-output-one-two", "boxing.one-two-three", "boxing.combo-defense-round", "boxing.punch-angle-round"),
                s("boxing-foundations-w4-d3", 3, "Boxing Test", "Put stance, jab, cross, hook, footwork, slips, rolls, and exits together.", 45, "boxing.foundations.jab", "boxing.foundations.cross", "boxing.foundations.lead-hook", "boxing.foundations.slip", "boxing.foundations.roll", "boxing.jab-cross-hook-exit", "boxing.combo-defense-round")
            )
        )
    )

    private val muayThaiFoundations = TrainingProgram(
        id = "muay-thai-foundations",
        title = "Muay Thai Foundations",
        tagline = "Develop balanced eight-limb offense with reliable defense and composure.",
        styleName = "MUAY THAI",
        level = ProgramLevel.FOUNDATIONS,
        summary = "A 12-session Muay Thai plan that layers punches, kicks, teeps, knees, elbows, checks, and defensive resets into increasingly complete rounds.",
        weeks = listOf(
            w(1, "Balance & Weapons", "Thai stance, basic punches, teeps, and round kicks.",
                s("muay-thai-foundations-w1-d1", 1, "Thai Base", "Build stance, guard, balance, and clean straight punches.", 20, "muaythai.foundations.stance-guard", "muaythai.foundations.jab", "muaythai.foundations.cross", "muaythai.foundations.one-two"),
                s("muay-thai-foundations-w1-d2", 2, "Teep Control", "Learn the lead and rear teep before using it for range control.", 25, "muaythai.foundations.stance-guard", "muaythai.foundations.teep-mechanics", "muaythai.teep-control"),
                s("muay-thai-foundations-w1-d3", 3, "Round Kick Mechanics", "Learn the round kick step by step: base, pivot, hip turn, contact, and recovery.", 25, "muaythai.foundations.stance-guard", "muaythai.foundations.round-kick-mechanics", "muaythai.foundations.round-kick-recovery")
            ),
            w(2, "Add the Inside Game", "Knees, elbows, short-range structure, and defensive responsibility.",
                s("muay-thai-foundations-w2-d1", 1, "Knee Basics", "Build straight-knee mechanics, posture, and a balanced return to stance.", 25, "muaythai.foundations.knee-mechanics", "muaythai.foundations.knee-step", "muaythai.knee-combination"),
                s("muay-thai-foundations-w2-d2", 2, "Elbow Basics", "Learn compact horizontal and diagonal elbow mechanics before combining them.", 30, "muaythai.foundations.elbow-horizontal", "muaythai.foundations.elbow-diagonal", "muaythai.foundations.elbow-guard-return"),
                s("muay-thai-foundations-w2-d3", 3, "Check Basics", "Learn a balanced kick check first, then add a simple return.", 30, "muaythai.foundations.check-mechanics", "muaythai.teep-check-flow", "muaythai.check-counter")
            ),
            w(3, "Link the Eight Limbs", "Punch-kick links, kick-punch returns, and layered offense.",
                s("muay-thai-foundations-w3-d1", 1, "Hands to Kicks", "Now connect the fundamentals: simple punches into a balanced round kick.", 30, "muaythai.foundations.one-two", "muaythai.jab-rear-kick", "muaythai.jab-cross-rear-kick"),
                s("muay-thai-foundations-w3-d2", 2, "Power Chains", "Blend basic punches, kicks, and knees without sacrificing balance.", 35, "muaythai.punch-kick-reset", "muaythai.cross-hook-knee", "muaythai.knee-combination"),
                s("muay-thai-foundations-w3-d3", 3, "Defensive Flow", "Combine checks, teeps, guarded movement, and simple returns.", 35, "muaythai.foundations.check-mechanics", "muaythai.teep-check-flow", "muaythai.body-kick-defense", "muaythai.defend-return-round")
            ),
            w(4, "Fight Pace", "Composure, reactions, conditioning, and complete Muay Thai rounds.",
                s("muay-thai-foundations-w4-d1", 1, "Reaction Rounds", "Use the basics under light decision pressure: check, teep, defend, and return.", 35, "muaythai.check-counter", "muaythai.body-kick-defense", "muaythai.defend-return-round"),
                s("muay-thai-foundations-w4-d2", 2, "Thai Pressure", "Maintain clean fundamentals while the pace climbs.", 40, "muaythai.punch-kick-reset", "muaythai.teep-check-flow", "muaythai.weapons-round", "muaythai.defend-return-round"),
                s("muay-thai-foundations-w4-d3", 3, "Eight-Limb Test", "Put stance, hands, teeps, kicks, knees, elbows, and checks together with control.", 45, "muaythai.foundations.one-two", "muaythai.foundations.teep-mechanics", "muaythai.foundations.round-kick-mechanics", "muaythai.foundations.knee-mechanics", "muaythai.foundations.elbow-horizontal", "muaythai.foundations.check-mechanics", "muaythai.weapons-round")
            )
        )
    )

    private val kickboxingFoundations = TrainingProgram(
        id = "kickboxing-foundations",
        title = "Kickboxing Foundations",
        tagline = "Connect fast boxing combinations to sharp kicks and active movement.",
        styleName = "KICKBOXING",
        level = ProgramLevel.FOUNDATIONS,
        summary = "A 12-session kickboxing progression focused on punch-kick transitions, angle changes, defensive counters, speed, and sustained combination work.",
        weeks = listOf(
            w(1, "Hands Meet Kicks", "Stance, boxing entries, basic kicks, and balanced transitions.",
                s("kickboxing-foundations-w1-d1", 1, "Kickboxing Base", "Build stance, guard, movement, and clean straight punches before adding kicks.", 20, "kickboxing.foundations.stance-guard", "kickboxing.foundations.jab-cross", "boxing.jab-only-movement"),
                s("kickboxing-foundations-w1-d2", 2, "Round Kick Basics", "Learn the rear round kick by itself before connecting it to punches.", 25, "kickboxing.foundations.rear-round-kick", "kickboxing.foundations.kick-recovery", "kickboxing.one-two-low-kick"),
                s("kickboxing-foundations-w1-d3", 3, "Lead Kick & Low Kick", "Learn basic lead-side kicking and low-kick mechanics with a stable recovery.", 25, "kickboxing.foundations.lead-round-kick", "kickboxing.foundations.low-kick", "kickboxing.foundations.kick-recovery")
            ),
            w(2, "Move & Counter", "Angles, exits, defensive responses, and return combinations.",
                s("kickboxing-foundations-w2-d1", 1, "Angle Out", "Learn a basic angle step and use it after simple punch-kick work.", 25, "kickboxing.foundations.angle-step", "kickboxing.kick-exit", "boxing.angle-after-combo"),
                s("kickboxing-foundations-w2-d2", 2, "Check Basics", "Learn a balanced kick check first, then add a simple return.", 30, "kickboxing.foundations.check", "kickboxing.check-return-combo", "muaythai.body-kick-defense"),
                s("kickboxing-foundations-w2-d3", 3, "Entry & Reset", "Enter behind simple punches, add one kick, then recover before repeating.", 30, "kickboxing.foundations.jab-cross", "kickboxing.punch-kick-chain", "kickboxing.foundations.kick-recovery")
            ),
            w(3, "Combination Depth", "Longer combinations, power placement, and pace changes.",
                s("kickboxing-foundations-w3-d1", 1, "Combination Chains", "Connect the basics into simple punch-kick sequences.", 30, "kickboxing.one-two-low-kick", "kickboxing.jab-hook-rear-kick", "kickboxing.punch-kick-chain"),
                s("kickboxing-foundations-w3-d2", 2, "Power Finish", "Use clean hand combinations to set up a balanced power kick.", 35, "kickboxing.foundations.rear-round-kick", "kickboxing.jab-hook-rear-kick", "kickboxing.cross-lead-kick-cross"),
                s("kickboxing-foundations-w3-d3", 3, "Defense to Offense", "Check, move, return a simple combination, and reset.", 35, "kickboxing.foundations.check", "kickboxing.check-return-combo", "kickboxing.kick-exit")
            ),
            w(4, "Pressure & Pace", "Complete rounds, defensive responsibility, and confident output.",
                s("kickboxing-foundations-w4-d1", 1, "Counter Pressure", "Use checks, exits, and simple returns while the pace increases.", 35, "kickboxing.check-return-combo", "kickboxing.kick-exit", "kickboxing.combo-range-round"),
                s("kickboxing-foundations-w4-d2", 2, "High Output", "Keep stance and clean punch-kick mechanics together through harder rounds.", 40, "kickboxing.punch-kick-chain", "kickboxing.mixed-range-round", "kickboxing.combo-range-round"),
                s("kickboxing-foundations-w4-d3", 3, "Kickboxing Test", "Put stance, straight punches, kicks, checks, exits, and simple combinations together.", 45, "kickboxing.foundations.jab-cross", "kickboxing.foundations.rear-round-kick", "kickboxing.foundations.low-kick", "kickboxing.foundations.check", "kickboxing.kick-exit", "kickboxing.one-two-low-kick", "kickboxing.combo-range-round")
            )
        )
    )

    private val mmaFoundations = TrainingProgram(
        id = "mma-striking-foundations",
        title = "MMA Striking Foundations",
        tagline = "Build striking that respects range changes, pressure, and MMA movement.",
        styleName = "MMA STRIKING",
        level = ProgramLevel.FOUNDATIONS,
        summary = "A 12-session MMA striking plan built around adaptable stance, range control, layered offense, defensive exits, and composure during unpredictable exchanges.",
        weeks = listOf(
            w(1, "MMA Range", "Adaptable stance, straight shots, kicks, and safe movement.",
                s("mma-striking-foundations-w1-d1", 1, "MMA Stance & Range", "Build an adaptable MMA stance, guard, and movement before adding offense.", 20, "mma.foundations.stance-guard", "mma.foundations.range-step", "mma.foundations.jab"),
                s("mma-striking-foundations-w1-d2", 2, "Straight Weapons", "Learn the jab, cross, and basic low kick separately before linking them.", 25, "mma.foundations.jab", "mma.foundations.cross", "mma.foundations.low-kick"),
                s("mma-striking-foundations-w1-d3", 3, "Safe Exits", "Throw a simple straight combination, move off line, and rebuild your stance.", 25, "mma.foundations.one-two-exit", "mma.foundations.circle-exit", "mma.jab-cross-angle-exit")
            ),
            w(2, "React to Pressure", "Defensive movement, counters, and range changes.",
                s("mma-striking-foundations-w2-d1", 1, "Pressure Escape", "Learn to circle away, create space, and reset your striking stance.", 25, "mma.foundations.circle-exit", "mma.long-range-reset", "mma.strike-exit-round"),
                s("mma-striking-foundations-w2-d2", 2, "Defend & Return", "Use a simple guard or movement response, then answer with one clean straight attack.", 30, "mma.foundations.defend-return", "mma.foundations.one-two-exit", "mma.long-range-reset"),
                s("mma-striking-foundations-w2-d3", 3, "Range Switch", "Move from long-range kicks into straight punches, then exit safely.", 30, "mma.foundations.front-kick", "mma.foundations.low-kick", "mma.jab-rear-kick-exit")
            ),
            w(3, "Layer the Offense", "Mixed attacks, feints, rhythm changes, and controlled power.",
                s("mma-striking-foundations-w3-d1", 1, "Mixed Combinations", "Connect basic straight punches and kicks while staying ready to move.", 30, "mma.foundations.one-two-exit", "mma.jab-rear-kick-exit", "mma.entry-exit-round"),
                s("mma-striking-foundations-w3-d2", 2, "Basic Feints", "Introduce simple level and hand feints without abandoning stance or balance.", 35, "mma.foundations.level-feint", "mma.level-feint-strike", "mma.feint-jab-cross-exit"),
                s("mma-striking-foundations-w3-d3", 3, "Power Without Chasing", "Add controlled power to straight shots and kicks while keeping your exit available.", 35, "mma.foundations.cross", "mma.foundations.low-kick", "mma.jab-rear-kick-exit", "mma.long-range-reset")
            ),
            w(4, "Unpredictable Rounds", "Conditioning, decisions, pressure, and complete MMA striking rounds.",
                s("mma-striking-foundations-w4-d1", 1, "Decision Rounds", "Choose between jab, kick, exit, or simple return while keeping your base under you.", 35, "mma.foundations.defend-return", "mma.long-range-reset", "mma.entry-exit-round"),
                s("mma-striking-foundations-w4-d2", 2, "Pressure Pace", "Keep clean entries, exits, and basic mixed attacks together as the pace rises.", 40, "mma.jab-cross-angle-exit", "mma.jab-rear-kick-exit", "mma.strike-exit-round", "mma.entry-exit-round"),
                s("mma-striking-foundations-w4-d3", 3, "MMA Striking Test", "Put stance, range, straight punches, kicks, exits, defense, and basic feints together.", 45, "mma.foundations.jab", "mma.foundations.cross", "mma.foundations.low-kick", "mma.foundations.front-kick", "mma.foundations.circle-exit", "mma.foundations.level-feint", "mma.entry-exit-round")
            )
        )
    )

    private val boxingIntermediate = TrainingProgram(
        id = "boxing-intermediate",
        title = "Boxing Intermediate",
        tagline = "Layer combinations, counters, angles, rhythm changes, and pressure without losing your base.",
        styleName = "BOXING",
        level = ProgramLevel.INTERMEDIATE,
        summary = "A 12-session boxing progression for fighters who already understand stance and basic punches. Sessions build longer combinations, defense-to-counter chains, feints, angle changes, rhythm control, and higher-output decision rounds.",
        weeks = listOf(
            w(1, "Layer the Offense", "Longer combinations, body-head changes, and exits after offense.",
                s("boxing-intermediate-w1-d1", 1, "Double Jab Angles", "Use the double jab to enter, score, and leave from a new angle.", 30, "boxing.double-jab-cross-pivot", "boxing.intermediate.double-jab-angle", "boxing.angle-after-combo"),
                s("boxing-intermediate-w1-d2", 2, "Body to Head", "Change levels inside combinations, then move before the return.", 35, "boxing.jab-body-cross", "boxing.intermediate.body-head-angle", "boxing.jab-cross-body-head"),
                s("boxing-intermediate-w1-d3", 3, "Hook Chains", "Build compact hook-cross sequences with defensive responsibility.", 35, "boxing.cross-hook-cross", "boxing.intermediate.hook-cross-roll", "boxing.jab-cross-hook-exit")
            ),
            w(2, "Defense Becomes Offense", "Slips, rolls, pulls, counters, and pivots chained into returns.",
                s("boxing-intermediate-w2-d1", 1, "Slip & Fire", "Turn a slip into a fast counter and leave before the next exchange.", 35, "boxing.jab-cross-slip", "boxing.intermediate.slip-cross-hook", "boxing.intermediate.counter-choice"),
                s("boxing-intermediate-w2-d2", 2, "Roll & Pivot", "Roll under pressure, return with punches, and pivot out cleanly.", 35, "boxing.slip-roll-return", "boxing.intermediate.roll-cross-pivot", "boxing.angle-after-combo"),
                s("boxing-intermediate-w2-d3", 3, "Pull Counter", "Use distance as defense, then answer immediately without overreaching.", 40, "boxing.pull-counter-flow", "boxing.intermediate.feint-entry", "boxing.intermediate.counter-choice")
            ),
            w(3, "Control the Rhythm", "Feints, tempo changes, combination depth, and pressure exits.",
                s("boxing-intermediate-w3-d1", 1, "Feint & Enter", "Create reactions before committing to your combination.", 40, "boxing.intermediate.feint-entry", "boxing.intermediate.double-jab-angle", "boxing.intermediate.rhythm-change"),
                s("boxing-intermediate-w3-d2", 2, "Change the Beat", "Break predictable timing while keeping every punch technically clean.", 40, "boxing.intermediate.rhythm-change", "boxing.one-two-three", "boxing.cross-hook-cross"),
                s("boxing-intermediate-w3-d3", 3, "Pressure With Exits", "Sustain offense without getting stuck in front of the target.", 45, "boxing.intermediate.pressure-exit", "boxing.combo-defense-round", "boxing.punch-angle-round")
            ),
            w(4, "Intermediate Fight Rounds", "Decision-making, counters, pressure, and complete boxing rounds.",
                s("boxing-intermediate-w4-d1", 1, "Counter Decisions", "Read the imagined attack and choose the correct response instead of pre-planning one counter.", 45, "boxing.intermediate.counter-choice", "boxing.pull-counter-flow", "boxing.intermediate.slip-cross-hook"),
                s("boxing-intermediate-w4-d2", 2, "Pressure & Rhythm", "Blend pressure with tempo changes, angles, and defensive finishes.", 45, "boxing.intermediate.rhythm-change", "boxing.intermediate.pressure-exit", "boxing.combo-defense-round"),
                s("boxing-intermediate-w4-d3", 3, "Intermediate Boxing Test", "Put layered offense, defense, counters, feints, rhythm, and movement together.", 50, "boxing.intermediate.double-jab-angle", "boxing.intermediate.body-head-angle", "boxing.intermediate.hook-cross-roll", "boxing.intermediate.counter-choice", "boxing.intermediate.pressure-exit")
            )
        )
    )

    private val muayThaiIntermediate = TrainingProgram(
        id = "muay-thai-intermediate",
        title = "Muay Thai Intermediate",
        tagline = "Turn individual weapons into setups, counters, range transitions, and composed fight-paced rounds.",
        styleName = "MUAY THAI",
        level = ProgramLevel.INTERMEDIATE,
        summary = "A 12-session intermediate Muay Thai progression built around punch-kick setups, intercepting teeps, checks into counters, long-guard transitions, elbows and knees inside combinations, rhythm changes, and higher-output eight-limb rounds.",
        weeks = listOf(
            w(1, "Set Up the Weapons", "Use feints and hands to create cleaner kicking opportunities.",
                s("muay-thai-intermediate-w1-d1", 1, "Feint to Rear Kick", "Use a believable hand reaction to open the rear kick.", 30, "muaythai.intermediate.jab-feint-kick", "muaythai.cross-lead-hook-kick", "muaythai.punch-kick-reset"),
                s("muay-thai-intermediate-w1-d2", 2, "Teep to Hands to Kick", "Control range with the teep, then transition into boxing and the rear kick.", 35, "muaythai.intermediate.teep-cross-hook-kick", "muaythai.teep-control", "muaythai.jab-cross-rear-kick"),
                s("muay-thai-intermediate-w1-d3", 3, "Hands Hide the Kick", "Build longer hand-to-kick sequences while staying balanced.", 35, "muaythai.cross-lead-hook-kick", "muaythai.intermediate.jab-feint-kick", "muaythai.intermediate.rhythm-round")
            ),
            w(2, "Defend & Return", "Checks, intercepting teeps, long guard, and immediate balanced counters.",
                s("muay-thai-intermediate-w2-d1", 1, "Check Into Combination", "Turn the kick check into a layered return rather than a single counter.", 35, "muaythai.intermediate.check-cross-hook-kick", "muaythai.check-counter", "muaythai.body-kick-defense"),
                s("muay-thai-intermediate-w2-d2", 2, "Intercept & Return", "Use the teep to stop pressure and immediately reclaim offense.", 35, "muaythai.intermediate.teep-intercept-return", "muaythai.teep-check-flow", "muaythai.intermediate.rhythm-round"),
                s("muay-thai-intermediate-w2-d3", 3, "Long Guard to Knee", "Use the long guard to manage the pocket, knee, and exit without standing square.", 40, "muaythai.long-guard-exit", "muaythai.intermediate.long-guard-knee-exit", "muaythai.intermediate.pressure-round")
            ),
            w(3, "Inside to Outside", "Elbows, knees, kicks, and clean transitions between ranges.",
                s("muay-thai-intermediate-w3-d1", 1, "Elbow Entry & Exit", "Enter behind punches, place a controlled elbow, then leave the pocket.", 40, "muaythai.intermediate.cross-hook-elbow", "muaythai.long-guard-exit", "muaythai.intermediate.rhythm-round"),
                s("muay-thai-intermediate-w3-d2", 2, "Kick Return Chains", "Recover from your kick, defend the imagined return, and answer again.", 40, "muaythai.intermediate.kick-return-chain", "muaythai.intermediate.check-cross-hook-kick", "muaythai.defend-return-round"),
                s("muay-thai-intermediate-w3-d3", 3, "Range Transition Rounds", "Move cleanly between teep range, kicking range, boxing range, and close range.", 45, "muaythai.intermediate.teep-cross-hook-kick", "muaythai.intermediate.long-guard-knee-exit", "muaythai.intermediate.cross-hook-elbow", "muaythai.intermediate.rhythm-round")
            ),
            w(4, "Intermediate Thai Rounds", "Reaction choices, rhythm changes, pressure, and complete eight-limb work.",
                s("muay-thai-intermediate-w4-d1", 1, "Defense Choice Rounds", "Choose between check, teep, long guard, or movement before returning offense.", 45, "muaythai.intermediate.teep-intercept-return", "muaythai.intermediate.check-cross-hook-kick", "muaythai.intermediate.kick-return-chain"),
                s("muay-thai-intermediate-w4-d2", 2, "Thai Pressure & Rhythm", "Sustain longer exchanges while changing pace and finishing defensively.", 45, "muaythai.intermediate.rhythm-round", "muaythai.intermediate.pressure-round", "muaythai.weapons-round"),
                s("muay-thai-intermediate-w4-d3", 3, "Intermediate Muay Thai Test", "Put setups, counters, range changes, elbows, knees, kicks, and defense together.", 50, "muaythai.intermediate.jab-feint-kick", "muaythai.intermediate.check-cross-hook-kick", "muaythai.intermediate.long-guard-knee-exit", "muaythai.intermediate.cross-hook-elbow", "muaythai.intermediate.pressure-round")
            )
        )
    )

    private val kickboxingIntermediate = TrainingProgram(
        id = "kickboxing-intermediate",
        title = "Kickboxing Intermediate",
        tagline = "Build layered punch-kick offense, counter chains, rhythm changes, and movement under pressure.",
        styleName = "KICKBOXING",
        level = ProgramLevel.INTERMEDIATE,
        summary = "A 12-session kickboxing progression for fighters comfortable with basic punches and kicks. It adds longer punch-kick chains, check-and-return sequences, pull counters, feints, angle changes, rhythm control, and harder mixed-range rounds.",
        weeks = listOf(
            w(1, "Extend the Combinations", "Three- to five-action combinations and clean recovery after kicks.",
                s("kickboxing-intermediate-w1-d1", 1, "Double Jab to Low Kick", "Use the double jab to build a longer entry into the low kick.", 30, "kickboxing.intermediate.double-jab-low-kick", "kickboxing.one-two-low-kick", "kickboxing.punch-kick-chain"),
                s("kickboxing-intermediate-w1-d2", 2, "Kick Back to Hands", "Recover from the kick and continue the combination instead of ending there.", 35, "kickboxing.intermediate.cross-hook-kick-cross", "kickboxing.cross-lead-kick-cross", "kickboxing.intermediate.kick-punch-angle"),
                s("kickboxing-intermediate-w1-d3", 3, "Combination Angles", "Finish longer punch-kick chains by moving to a new position.", 35, "kickboxing.intermediate.kick-punch-angle", "kickboxing.kick-exit", "kickboxing.combo-range-round")
            ),
            w(2, "Counter the Return", "Checks, pulls, returns, and angle exits after defensive moments.",
                s("kickboxing-intermediate-w2-d1", 1, "Check & Angle", "Check the kick, return a combination, then leave on an angle.", 35, "kickboxing.intermediate.check-return-angle", "kickboxing.check-return-combo", "kickboxing.kick-exit"),
                s("kickboxing-intermediate-w2-d2", 2, "Pull & Low Kick", "Use distance defense to create a clean cross-low-kick return.", 35, "kickboxing.intermediate.pull-cross-low-kick", "boxing.pull-counter-flow", "kickboxing.one-two-low-kick"),
                s("kickboxing-intermediate-w2-d3", 3, "Defense Into Volume", "Defend first, then build a longer punch-kick answer without losing stance.", 40, "kickboxing.intermediate.check-return-angle", "kickboxing.intermediate.pressure-counter-round", "kickboxing.combo-range-round")
            ),
            w(3, "Feints & Rhythm", "Create reactions, change tempo, and attack from less predictable timing.",
                s("kickboxing-intermediate-w3-d1", 1, "Jab Feint to Lead Kick", "Use the jab reaction to open a different kicking line.", 40, "kickboxing.intermediate.jab-feint-lead-kick", "kickboxing.jab-hook-rear-kick", "kickboxing.intermediate.rhythm-round"),
                s("kickboxing-intermediate-w3-d2", 2, "Change the Beat", "Vary pace inside punch-kick combinations instead of throwing at one rhythm.", 40, "kickboxing.intermediate.rhythm-round", "kickboxing.intermediate.cross-hook-kick-cross", "kickboxing.mixed-range-round"),
                s("kickboxing-intermediate-w3-d3", 3, "Pressure & Counter", "Alternate offensive pressure with deliberate defensive responses and returns.", 45, "kickboxing.intermediate.pressure-counter-round", "kickboxing.intermediate.check-return-angle", "kickboxing.combo-range-round")
            ),
            w(4, "Intermediate Kickboxing Rounds", "Complete combinations, defense, rhythm, pressure, and decision-making.",
                s("kickboxing-intermediate-w4-d1", 1, "Counter Choice", "Choose the right defensive answer, return, and exit based on the imagined attack.", 45, "kickboxing.intermediate.check-return-angle", "kickboxing.intermediate.pull-cross-low-kick", "kickboxing.intermediate.pressure-counter-round"),
                s("kickboxing-intermediate-w4-d2", 2, "High Output Rhythm", "Keep longer combinations clean while changing pace under fatigue.", 45, "kickboxing.intermediate.rhythm-round", "kickboxing.intermediate.kick-punch-angle", "kickboxing.mixed-range-round"),
                s("kickboxing-intermediate-w4-d3", 3, "Intermediate Kickboxing Test", "Put layered offense, counters, feints, movement, and pressure together.", 50, "kickboxing.intermediate.double-jab-low-kick", "kickboxing.intermediate.cross-hook-kick-cross", "kickboxing.intermediate.check-return-angle", "kickboxing.intermediate.jab-feint-lead-kick", "kickboxing.intermediate.pressure-counter-round")
            )
        )
    )

    private val mmaIntermediate = TrainingProgram(
        id = "mma-striking-intermediate",
        title = "MMA Striking Intermediate",
        tagline = "Layer feints, range changes, exits, and pressure-aware striking into adaptable rounds.",
        styleName = "MMA STRIKING",
        level = ProgramLevel.INTERMEDIATE,
        summary = "A 12-session MMA striking program that builds on basic range management with level feints, mixed punch-kick entries, defensive returns, angle exits, range decisions, and pressure rounds where every exchange must account for changing distance.",
        weeks = listOf(
            w(1, "Layer the Entry", "Feints, straight attacks, low kicks, and immediate exits.",
                s("mma-striking-intermediate-w1-d1", 1, "Level Feint Entry", "Use the level change to create a striking opening without overcommitting.", 30, "mma.intermediate.level-feint-cross-kick", "mma.level-feint-strike", "mma.feint-jab-cross-exit"),
                s("mma-striking-intermediate-w1-d2", 2, "Angle to Low Kick", "Move off the center line before finishing the exchange with a low kick.", 35, "mma.intermediate.jab-cross-angle-kick", "mma.jab-cross-angle-exit", "mma.long-range-reset"),
                s("mma-striking-intermediate-w1-d3", 3, "Front Kick to Boxing", "Use long-range control to enter boxing range, then leave immediately.", 35, "mma.intermediate.front-kick-cross-exit", "mma.jab-rear-kick-exit", "mma.entry-exit-round")
            ),
            w(2, "Defend & Change Range", "Defensive returns and movement between long, boxing, and exit range.",
                s("mma-striking-intermediate-w2-d1", 1, "Defend & Exit", "Answer a straight attack, score, and leave boxing range before the exchange extends.", 35, "mma.intermediate.defend-cross-exit", "mma.long-range-reset", "mma.strike-exit-round"),
                s("mma-striking-intermediate-w2-d2", 2, "Range Choice", "Match your attack to the distance rather than forcing the same combination.", 40, "mma.intermediate.range-choice-round", "mma.intermediate.front-kick-cross-exit", "mma.entry-exit-round"),
                s("mma-striking-intermediate-w2-d3", 3, "Pressure Escape & Return", "Leave pressure, regain range, then re-enter on your terms.", 40, "mma.intermediate.pressure-exit-round", "mma.intermediate.defend-cross-exit", "mma.jab-cross-angle-exit")
            ),
            w(3, "Feint & Decide", "Create reactions and select the attack from the response you imagine.",
                s("mma-striking-intermediate-w3-d1", 1, "Feint to Mixed Attack", "Use level and hand feints to open either punches or kicks.", 40, "mma.intermediate.feint-jab-kick-exit", "mma.intermediate.level-feint-cross-kick", "mma.intermediate.feint-decision-round"),
                s("mma-striking-intermediate-w3-d2", 2, "Range Decisions", "Switch between long attacks, boxing entries, and exits as the imagined range changes.", 45, "mma.intermediate.range-choice-round", "mma.intermediate.jab-cross-angle-kick", "mma.intermediate.front-kick-cross-exit"),
                s("mma-striking-intermediate-w3-d3", 3, "Pressure With Discipline", "Apply pressure without standing square or remaining in the pocket too long.", 45, "mma.intermediate.pressure-exit-round", "mma.intermediate.feint-decision-round", "mma.entry-exit-round")
            ),
            w(4, "Intermediate MMA Striking Rounds", "Adaptive decisions, feints, mixed offense, pressure, and safe exits.",
                s("mma-striking-intermediate-w4-d1", 1, "Decision Rounds", "React to range and pressure cues instead of pre-selecting every exchange.", 45, "mma.intermediate.range-choice-round", "mma.intermediate.feint-decision-round", "mma.intermediate.defend-cross-exit"),
                s("mma-striking-intermediate-w4-d2", 2, "Pressure & Exit Pace", "Maintain output while every exchange still ends with responsible movement.", 45, "mma.intermediate.pressure-exit-round", "mma.intermediate.jab-cross-angle-kick", "mma.strike-exit-round"),
                s("mma-striking-intermediate-w4-d3", 3, "Intermediate MMA Striking Test", "Put feints, range changes, mixed attacks, defense, pressure, and exits together.", 50, "mma.intermediate.level-feint-cross-kick", "mma.intermediate.front-kick-cross-exit", "mma.intermediate.defend-cross-exit", "mma.intermediate.range-choice-round", "mma.intermediate.pressure-exit-round")
            )
        )
    )

    private val boxingAdvanced = TrainingProgram(
        id = "boxing-advanced",
        title = "Boxing Advanced",
        tagline = "Sharpen layered offense, counter-the-counter reactions, broken rhythm, and pressure decisions at fight pace.",
        styleName = "BOXING",
        level = ProgramLevel.ADVANCED,
        summary = "A 12-session advanced boxing program for experienced strikers. Sessions emphasize layered feints, body-head manipulation, second and third defensive decisions, counter-the-counter sequences, broken rhythm, pressure escapes, and technically clean output under fatigue.",
        weeks = listOf(
            w(1, "Layer the Attack", "Feints, level changes, long combinations, and exits after scoring.",
                s("boxing-advanced-w1-d1", 1, "Feint, Change Level, Exit", "Manipulate the guard, attack body and head, then leave before the return.", 40, "boxing.advanced.feint-body-head-angle", "boxing.intermediate.body-head-angle", "boxing.intermediate.feint-entry", rest = 20),
                s("boxing-advanced-w1-d2", 2, "Five-Punch Responsibility", "Build longer combinations that still end with defense and position.", 45, "boxing.advanced.five-punch-exit", "boxing.intermediate.rhythm-change", "boxing.combo-defense-round", rest = 20),
                s("boxing-advanced-w1-d3", 3, "Broken Rhythm Entries", "Use pauses and sudden bursts to enter without becoming predictable.", 45, "boxing.advanced.rhythm-break", "boxing.intermediate.double-jab-angle", "boxing.advanced.feint-body-head-angle", rest = 20)
            ),
            w(2, "Win the Second Exchange", "Counter-the-counter sequences, layered defense, and pressure escapes.",
                s("boxing-advanced-w2-d1", 1, "Counter the Counter", "Defend, answer, defend the return, and score again before exiting.", 45, "boxing.advanced.counter-counter", "boxing.intermediate.counter-choice", "boxing.intermediate.hook-cross-roll", rest = 20),
                s("boxing-advanced-w2-d2", 2, "Escape the Trap", "Create exits under pressure instead of retreating in a straight line.", 45, "boxing.advanced.trap-exit-round", "boxing.intermediate.pressure-exit", "boxing.punch-angle-round", rest = 20),
                s("boxing-advanced-w2-d3", 3, "Reactive Defense Chains", "Respond to changing attacks with different defenses and counters each exchange.", 50, "boxing.advanced.read-react-round", "boxing.advanced.counter-counter", "boxing.intermediate.counter-choice", rest = 20)
            ),
            w(3, "Fight-Pace Control", "Broken rhythm, pressure, power, and technical decisions under fatigue.",
                s("boxing-advanced-w3-d1", 1, "Broken Rhythm Pressure", "Pressure without becoming predictable or standing still after combinations.", 50, "boxing.advanced.rhythm-break", "boxing.advanced.trap-exit-round", "boxing.intermediate.pressure-exit", rest = 20),
                s("boxing-advanced-w3-d2", 2, "Power Under Fatigue", "Hold clean mechanics when output and power demands rise.", 50, "boxing.advanced.power-fatigue", "boxing.advanced.five-punch-exit", "boxing.intermediate.body-head-angle", rest = 20),
                s("boxing-advanced-w3-d3", 3, "Read, React, Reposition", "Make repeated tactical decisions rather than running scripted combinations.", 55, "boxing.advanced.read-react-round", "boxing.advanced.rhythm-break", "boxing.advanced.trap-exit-round", rest = 20)
            ),
            w(4, "Advanced Fight Rounds", "Complete rounds with layered offense, reactions, pressure, and composure.",
                s("boxing-advanced-w4-d1", 1, "Second & Third Decision Rounds", "Keep solving the exchange after your first counter or combination.", 55, "boxing.advanced.counter-counter", "boxing.advanced.read-react-round", "boxing.advanced.fight-round", rest = 20),
                s("boxing-advanced-w4-d2", 2, "Championship Pace", "Blend broken rhythm, pressure, power, and exits without losing technique.", 55, "boxing.advanced.rhythm-break", "boxing.advanced.power-fatigue", "boxing.advanced.fight-round", rest = 20),
                s("boxing-advanced-w4-d3", 3, "Advanced Boxing Test", "Put feints, long combinations, counter chains, pressure escapes, rhythm, and fight pace together.", 60, "boxing.advanced.feint-body-head-angle", "boxing.advanced.counter-counter", "boxing.advanced.trap-exit-round", "boxing.advanced.read-react-round", "boxing.advanced.fight-round", rest = 20)
            )
        )
    )

    private val muayThaiAdvanced = TrainingProgram(
        id = "muay-thai-advanced",
        title = "Muay Thai Advanced",
        tagline = "Layer feints, counters, range transitions, second exchanges, and eight-limb pressure at fight pace.",
        styleName = "MUAY THAI",
        level = ProgramLevel.ADVANCED,
        summary = "A 12-session advanced Muay Thai progression built around layered feints, check-return-check sequences, intercepting reactions, close-to-long-range transitions, rhythm manipulation, pressure, and technically responsible eight-limb work under fatigue.",
        weeks = listOf(
            w(1, "Manipulate the Reaction", "Teep and hand feints, layered kick setups, and multi-range combinations.",
                s("muay-thai-advanced-w1-d1", 1, "Teep Feint to Kick", "Use the teep reaction to build a longer hand-to-kick sequence.", 40, "muaythai.advanced.feint-teep-kick", "muaythai.intermediate.jab-feint-kick", "muaythai.intermediate.teep-cross-hook-kick", rest = 20),
                s("muay-thai-advanced-w1-d2", 2, "Inside to Outside", "Move from punches and elbows to knees, then escape back to kicking range.", 45, "muaythai.advanced.elbow-knee-exit", "muaythai.intermediate.long-guard-knee-exit", "muaythai.advanced.range-cycle", rest = 20),
                s("muay-thai-advanced-w1-d3", 3, "Four-Range Flow", "Use the right weapon as the imagined range changes throughout the round.", 45, "muaythai.advanced.range-cycle", "muaythai.intermediate.rhythm-round", "muaythai.intermediate.cross-hook-elbow", rest = 20)
            ),
            w(2, "Win the Return", "Check-return-check chains, counter selection, and repeated defensive decisions.",
                s("muay-thai-advanced-w2-d1", 1, "Check, Return, Check Again", "Stay composed through the second kick exchange instead of stopping after one counter.", 45, "muaythai.advanced.check-return-check", "muaythai.intermediate.check-cross-hook-kick", "muaythai.intermediate.kick-return-chain", rest = 20),
                s("muay-thai-advanced-w2-d2", 2, "Counter Choice", "Match checks, teeps, long guard, movement, and counters to the imagined attack.", 45, "muaythai.advanced.counter-choice", "muaythai.intermediate.teep-intercept-return", "muaythai.intermediate.long-guard-knee-exit", rest = 20),
                s("muay-thai-advanced-w2-d3", 3, "Second Exchange Rounds", "Defend and counter through multiple actions before resetting range.", 50, "muaythai.advanced.check-return-check", "muaythai.advanced.counter-choice", "muaythai.intermediate.kick-return-chain", rest = 20)
            ),
            w(3, "Pressure With Structure", "Rhythm manipulation, long exchanges, range transitions, and fatigue control.",
                s("muay-thai-advanced-w3-d1", 1, "Rhythm Pressure", "Change tempo inside longer eight-limb exchanges while staying balanced.", 50, "muaythai.advanced.rhythm-pressure", "muaythai.intermediate.rhythm-round", "muaythai.advanced.range-cycle", rest = 20),
                s("muay-thai-advanced-w3-d2", 2, "Eight-Limb Fatigue", "Keep punches, kicks, knees, elbows, teeps, and checks technically clean under fatigue.", 50, "muaythai.advanced.fatigue-weapons", "muaythai.advanced.elbow-knee-exit", "muaythai.intermediate.pressure-round", rest = 20),
                s("muay-thai-advanced-w3-d3", 3, "Range & Counter Chaos", "React to changing distance and attacks without forcing one favorite answer.", 55, "muaythai.advanced.counter-choice", "muaythai.advanced.range-cycle", "muaythai.advanced.rhythm-pressure", rest = 20)
            ),
            w(4, "Advanced Thai Rounds", "Fight-paced decisions, pressure, layered counters, and full eight-limb integration.",
                s("muay-thai-advanced-w4-d1", 1, "Advanced Counter Rounds", "Solve repeated attacks with changing defenses and layered returns.", 55, "muaythai.advanced.check-return-check", "muaythai.advanced.counter-choice", "muaythai.advanced.fight-round", rest = 20),
                s("muay-thai-advanced-w4-d2", 2, "Fight-Pace Eight Limbs", "Sustain pressure and weapon variety without losing posture or defensive responsibility.", 55, "muaythai.advanced.rhythm-pressure", "muaythai.advanced.fatigue-weapons", "muaythai.advanced.fight-round", rest = 20),
                s("muay-thai-advanced-w4-d3", 3, "Advanced Muay Thai Test", "Put feints, second exchanges, range transitions, counters, pressure, and all eight limbs together.", 60, "muaythai.advanced.feint-teep-kick", "muaythai.advanced.check-return-check", "muaythai.advanced.elbow-knee-exit", "muaythai.advanced.counter-choice", "muaythai.advanced.fight-round", rest = 20)
            )
        )
    )

    private val kickboxingAdvanced = TrainingProgram(
        id = "kickboxing-advanced",
        title = "Kickboxing Advanced",
        tagline = "Build complex punch-kick chains, layered counters, rhythm traps, and high-output range control.",
        styleName = "KICKBOXING",
        level = ProgramLevel.ADVANCED,
        summary = "A 12-session advanced kickboxing plan centered on six-action combination chains, feints that create opposite-side attacks, check-counter-counter sequences, broken rhythm, range cycling, pressure decisions, and technically clean power under fatigue.",
        weeks = listOf(
            w(1, "Build the Trap", "Feints, long punch-kick chains, and attacks that break established patterns.",
                s("kickboxing-advanced-w1-d1", 1, "Low Feint to High Attack", "Create a low-line reaction, then exploit the opening with hands and a higher finish.", 40, "kickboxing.advanced.feint-low-high", "kickboxing.intermediate.jab-feint-lead-kick", "kickboxing.intermediate.cross-hook-kick-cross", rest = 20),
                s("kickboxing-advanced-w1-d2", 2, "Six-Strike Chains", "Build long combinations without losing stance between punches and kicks.", 45, "kickboxing.advanced.six-strike-chain", "kickboxing.intermediate.kick-punch-angle", "kickboxing.intermediate.rhythm-round", rest = 20),
                s("kickboxing-advanced-w1-d3", 3, "Rhythm Traps", "Establish a pattern, then deliberately break it with a different attack or angle.", 45, "kickboxing.advanced.rhythm-trap", "kickboxing.advanced.feint-low-high", "kickboxing.intermediate.rhythm-round", rest = 20)
            ),
            w(2, "Win the Counter Exchange", "Check-counter-counter sequences, defensive changes, and immediate repositioning.",
                s("kickboxing-advanced-w2-d1", 1, "Check, Counter, Counter Again", "Stay technically responsible through the opponent's imagined return.", 45, "kickboxing.advanced.check-counter-counter", "kickboxing.intermediate.check-return-angle", "kickboxing.intermediate.pressure-counter-round", rest = 20),
                s("kickboxing-advanced-w2-d2", 2, "Range Cycle", "Choose the correct punch-kick sequence as distance changes.", 45, "kickboxing.advanced.range-cycle", "kickboxing.intermediate.kick-punch-angle", "kickboxing.kick-exit", rest = 20),
                s("kickboxing-advanced-w2-d3", 3, "Pressure Counter Decisions", "Switch between attacking pressure and reactive defensive returns.", 50, "kickboxing.advanced.pressure-counter", "kickboxing.advanced.check-counter-counter", "kickboxing.intermediate.pull-cross-low-kick", rest = 20)
            ),
            w(3, "High Output, High Control", "Power, broken rhythm, range control, and long combinations under fatigue.",
                s("kickboxing-advanced-w3-d1", 1, "Broken Rhythm Chains", "Use long combinations without giving the opponent one predictable tempo.", 50, "kickboxing.advanced.rhythm-trap", "kickboxing.advanced.six-strike-chain", "kickboxing.intermediate.rhythm-round", rest = 20),
                s("kickboxing-advanced-w3-d2", 2, "Power Under Fatigue", "Maintain kick recovery and punching structure while power demands rise.", 50, "kickboxing.advanced.power-fatigue", "kickboxing.advanced.six-strike-chain", "kickboxing.intermediate.cross-hook-kick-cross", rest = 20),
                s("kickboxing-advanced-w3-d3", 3, "Range Pressure", "Pressure from the correct range and leave before becoming stationary.", 55, "kickboxing.advanced.range-cycle", "kickboxing.advanced.pressure-counter", "kickboxing.advanced.rhythm-trap", rest = 20)
            ),
            w(4, "Advanced Kickboxing Rounds", "Complete fight-paced combinations, counters, rhythm, power, and movement.",
                s("kickboxing-advanced-w4-d1", 1, "Counter Layers", "React beyond the first defensive exchange and keep solving the return.", 55, "kickboxing.advanced.check-counter-counter", "kickboxing.advanced.pressure-counter", "kickboxing.advanced.fight-round", rest = 20),
                s("kickboxing-advanced-w4-d2", 2, "Fight-Pace Output", "Blend rhythm traps, power, long combinations, and range control under fatigue.", 55, "kickboxing.advanced.rhythm-trap", "kickboxing.advanced.power-fatigue", "kickboxing.advanced.fight-round", rest = 20),
                s("kickboxing-advanced-w4-d3", 3, "Advanced Kickboxing Test", "Put feints, six-strike chains, counter layers, range changes, and fight pace together.", 60, "kickboxing.advanced.feint-low-high", "kickboxing.advanced.check-counter-counter", "kickboxing.advanced.six-strike-chain", "kickboxing.advanced.range-cycle", "kickboxing.advanced.fight-round", rest = 20)
            )
        )
    )

    private val mmaAdvanced = TrainingProgram(
        id = "mma-striking-advanced",
        title = "MMA Striking Advanced",
        tagline = "Make layered striking decisions across changing ranges, pressure, feints, and fatigue.",
        styleName = "MMA STRIKING",
        level = ProgramLevel.ADVANCED,
        summary = "A 12-session advanced MMA striking program built around layered hand and level feints, range-switching counters, wall-pressure escapes, multi-decision exchanges, unpredictable distance changes, and fight-paced striking where every attack must preserve an exit.",
        weeks = listOf(
            w(1, "Layer the Information", "Double feints, mixed attacks, and exits built into every entry.",
                s("mma-striking-advanced-w1-d1", 1, "Feint, Read, Attack", "Use the reaction to choose the attack instead of scripting the whole entry.", 40, "mma.advanced.level-feint-chain", "mma.intermediate.feint-decision-round", "mma.intermediate.level-feint-cross-kick", rest = 20),
                s("mma-striking-advanced-w1-d2", 2, "Double Feint Entry", "Layer hand and level feints before a compact attack and immediate exit.", 45, "mma.advanced.double-feint-entry", "mma.advanced.level-feint-chain", "mma.intermediate.feint-jab-kick-exit", rest = 20),
                s("mma-striking-advanced-w1-d3", 3, "Counter & Switch Range", "Defend, score, and move to a new distance before the exchange continues.", 45, "mma.advanced.counter-range-switch", "mma.intermediate.defend-cross-exit", "mma.intermediate.range-choice-round", rest = 20)
            ),
            w(2, "Escape Pressure, Reclaim Space", "Wall-pressure exits, range resets, and layered defensive choices.",
                s("mma-striking-advanced-w2-d1", 1, "Wall Escape Striking", "Create an angle under pressure, escape, and regain long range before attacking.", 45, "mma.advanced.cage-escape-round", "mma.intermediate.pressure-exit-round", "mma.advanced.counter-range-switch", rest = 20),
                s("mma-striking-advanced-w2-d2", 2, "Unpredictable Range", "Change distance constantly so every attack fits the range in front of you.", 45, "mma.advanced.range-chaos", "mma.intermediate.range-choice-round", "mma.advanced.level-feint-chain", rest = 20),
                s("mma-striking-advanced-w2-d3", 3, "Multi-Decision Defense", "Make several tactical choices inside one exchange without freezing or overcommitting.", 50, "mma.advanced.decision-chain", "mma.advanced.counter-range-switch", "mma.advanced.cage-escape-round", rest = 20)
            ),
            w(3, "Chaos With Structure", "Pressure, range unpredictability, layered feints, and decision quality under fatigue.",
                s("mma-striking-advanced-w3-d1", 1, "Range Chaos", "Attack from changing distances without settling into one predictable striking range.", 50, "mma.advanced.range-chaos", "mma.advanced.double-feint-entry", "mma.intermediate.range-choice-round", rest = 20),
                s("mma-striking-advanced-w3-d2", 2, "Fatigue Decisions", "Keep decision quality and exits sharp while the work rate rises.", 50, "mma.advanced.fatigue-decisions", "mma.advanced.decision-chain", "mma.intermediate.pressure-exit-round", rest = 20),
                s("mma-striking-advanced-w3-d3", 3, "Pressure Escape & Re-entry", "Escape pressure, reset range, then re-enter behind a layered feint.", 55, "mma.advanced.cage-escape-round", "mma.advanced.level-feint-chain", "mma.advanced.range-chaos", rest = 20)
            ),
            w(4, "Advanced MMA Striking Rounds", "Complete fight-paced decisions across range, pressure, counters, and fatigue.",
                s("mma-striking-advanced-w4-d1", 1, "Multi-Decision Rounds", "Solve several changing problems inside every exchange without scripting the outcome.", 55, "mma.advanced.decision-chain", "mma.advanced.counter-range-switch", "mma.advanced.fight-round", rest = 20),
                s("mma-striking-advanced-w4-d2", 2, "Fight-Pace Range Chaos", "Maintain output while range, pressure, and attack choices keep changing.", 55, "mma.advanced.range-chaos", "mma.advanced.fatigue-decisions", "mma.advanced.fight-round", rest = 20),
                s("mma-striking-advanced-w4-d3", 3, "Advanced MMA Striking Test", "Put double feints, range changes, pressure escapes, counter layers, and fatigue decisions together.", 60, "mma.advanced.double-feint-entry", "mma.advanced.counter-range-switch", "mma.advanced.cage-escape-round", "mma.advanced.decision-chain", "mma.advanced.fight-round", rest = 20)
            )
        )
    )
}
