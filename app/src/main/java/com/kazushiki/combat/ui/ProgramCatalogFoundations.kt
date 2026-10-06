package com.kazushiki.combat.ui

fun foundationsPrograms(): List<TrainingProgram> = listOf(
    boxingFoundationsProgram(),
    muayThaiFoundationsProgram(),
    kickboxingFoundationsProgram(),
    mmaFoundationsProgram()
)

private fun boxingFoundationsProgram() = TrainingProgram(
    id = "boxing-foundations",
    title = "Boxing Foundations",
    tagline = "Build clean hands, defense, and footwork that hold up under pressure.",
    styleName = "BOXING",
    level = ProgramLevel.FOUNDATIONS,
    summary = "A 12-session boxing progression that starts with stance and straight punches, adds defensive responsibility, then builds combinations, reactions, and controlled pressure.",
    weeks = listOf(
        programWeek(1, "Build the Base", "Stance, jab quality, straight punches, and balanced exits.",
            programSession("boxing-foundations-w1-d1", 1, "Jab & Stance", "Build stance, guard, movement, and a clean jab before adding combinations.", 20, "boxing.foundations.stance-guard", "boxing.foundations.jab", "boxing.jab-only-movement"),
            programSession("boxing-foundations-w1-d2", 2, "Straight Line", "Learn the rear cross on its own, then connect a balanced one-two.", 25, "boxing.foundations.cross", "boxing.foundations.one-two", "boxing.high-output-one-two"),
            programSession("boxing-foundations-w1-d3", 3, "Guard & Exit", "Learn basic forward, backward, lateral movement and safe exits after straight punches.", 25, "boxing.foundations.footwork", "boxing.foundations.exit-step", "boxing.double-jab-exit")
        ),
        programWeek(2, "Defend, Then Answer", "Head movement, guard recovery, counters, and angle changes.",
            programSession("boxing-foundations-w2-d1", 1, "Slip Basics", "Learn clean slips first, then add a simple straight return.", 25, "boxing.foundations.slip", "boxing.foundations.slip-return", "boxing.jab-cross-slip"),
            programSession("boxing-foundations-w2-d2", 2, "Hook Basics", "Build a compact lead hook before adding a simple angle exit.", 30, "boxing.foundations.lead-hook", "boxing.foundations.hook-one-two", "boxing.angle-after-combo"),
            programSession("boxing-foundations-w2-d3", 3, "Roll & Return", "Learn a basic roll, recover your stance, then answer with a short counter.", 30, "boxing.foundations.roll", "boxing.slip-roll-return", "boxing.foundations.counter-reset")
        ),
        programWeek(3, "Build Combinations", "Longer sequences, speed changes, power placement, and clean resets.",
            programSession("boxing-foundations-w3-d1", 1, "Fast Hands", "Speed up the fundamentals with short, clean combinations.", 30, "boxing.foundations.one-two", "boxing.one-two-three", "boxing.cross-hook-cross"),
            programSession("boxing-foundations-w3-d2", 2, "Power Behind the Jab", "Use the jab to set up a balanced cross and basic power combinations.", 30, "boxing.foundations.jab", "boxing.foundations.cross", "boxing.power-cross", "boxing.jab-body-cross"),
            programSession("boxing-foundations-w3-d3", 3, "Combination Defense", "Finish simple combinations with a slip, roll, guard recovery, or angle exit.", 35, "boxing.jab-cross-slip", "boxing.angle-after-combo", "boxing.combo-defense-round")
        ),
        programWeek(4, "Put It Together", "Decision-making, pressure, conditioning, and confident rounds.",
            programSession("boxing-foundations-w4-d1", 1, "React & Counter", "Use the fundamentals under light reaction pressure: defend, answer, and reset.", 35, "boxing.foundations.slip-return", "boxing.foundations.counter-reset", "boxing.pull-counter-flow"),
            programSession("boxing-foundations-w4-d2", 2, "Pressure Rounds", "Keep stance, guard, and clean combinations together as the pace rises.", 40, "boxing.high-output-one-two", "boxing.one-two-three", "boxing.combo-defense-round", "boxing.punch-angle-round"),
            programSession("boxing-foundations-w4-d3", 3, "Boxing Test", "Put stance, jab, cross, hook, footwork, slips, rolls, and exits together.", 45, "boxing.foundations.jab", "boxing.foundations.cross", "boxing.foundations.lead-hook", "boxing.foundations.slip", "boxing.foundations.roll", "boxing.jab-cross-hook-exit", "boxing.combo-defense-round")
        )
    )
)

private fun muayThaiFoundationsProgram() = TrainingProgram(
    id = "muay-thai-foundations",
    title = "Muay Thai Foundations",
    tagline = "Develop balanced eight-limb offense with reliable defense and composure.",
    styleName = "MUAY THAI",
    level = ProgramLevel.FOUNDATIONS,
    summary = "A 12-session Muay Thai plan that layers punches, kicks, teeps, knees, elbows, checks, and defensive resets into increasingly complete rounds.",
    weeks = listOf(
        programWeek(1, "Balance & Weapons", "Thai stance, basic punches, teeps, and round kicks.",
            programSession("muay-thai-foundations-w1-d1", 1, "Thai Base", "Build stance, guard, balance, and clean straight punches.", 20, "muaythai.foundations.stance-guard", "muaythai.foundations.jab", "muaythai.foundations.cross", "muaythai.foundations.one-two"),
            programSession("muay-thai-foundations-w1-d2", 2, "Teep Control", "Learn the lead and rear teep before using it for range control.", 25, "muaythai.foundations.stance-guard", "muaythai.foundations.teep-mechanics", "muaythai.teep-control"),
            programSession("muay-thai-foundations-w1-d3", 3, "Round Kick Mechanics", "Learn the round kick step by step: base, pivot, hip turn, contact, and recovery.", 25, "muaythai.foundations.stance-guard", "muaythai.foundations.round-kick-mechanics", "muaythai.foundations.round-kick-recovery")
        ),
        programWeek(2, "Add the Inside Game", "Knees, elbows, short-range structure, and defensive responsibility.",
            programSession("muay-thai-foundations-w2-d1", 1, "Knee Basics", "Build straight-knee mechanics, posture, and a balanced return to stance.", 25, "muaythai.foundations.knee-mechanics", "muaythai.foundations.knee-step", "muaythai.knee-combination"),
            programSession("muay-thai-foundations-w2-d2", 2, "Elbow Basics", "Learn compact horizontal and diagonal elbow mechanics before combining them.", 30, "muaythai.foundations.elbow-horizontal", "muaythai.foundations.elbow-diagonal", "muaythai.foundations.elbow-guard-return"),
            programSession("muay-thai-foundations-w2-d3", 3, "Check Basics", "Learn a balanced kick check first, then add a simple return.", 30, "muaythai.foundations.check-mechanics", "muaythai.teep-check-flow", "muaythai.check-counter")
        ),
        programWeek(3, "Link the Eight Limbs", "Punch-kick links, kick-punch returns, and layered offense.",
            programSession("muay-thai-foundations-w3-d1", 1, "Hands to Kicks", "Now connect the fundamentals: simple punches into a balanced round kick.", 30, "muaythai.foundations.one-two", "muaythai.jab-rear-kick", "muaythai.jab-cross-rear-kick"),
            programSession("muay-thai-foundations-w3-d2", 2, "Power Chains", "Blend basic punches, kicks, and knees without sacrificing balance.", 35, "muaythai.punch-kick-reset", "muaythai.cross-hook-knee", "muaythai.knee-combination"),
            programSession("muay-thai-foundations-w3-d3", 3, "Defensive Flow", "Combine checks, teeps, guarded movement, and simple returns.", 35, "muaythai.foundations.check-mechanics", "muaythai.teep-check-flow", "muaythai.body-kick-defense", "muaythai.defend-return-round")
        ),
        programWeek(4, "Fight Pace", "Composure, reactions, conditioning, and complete Muay Thai rounds.",
            programSession("muay-thai-foundations-w4-d1", 1, "Reaction Rounds", "Use the basics under light decision pressure: check, teep, defend, and return.", 35, "muaythai.check-counter", "muaythai.body-kick-defense", "muaythai.defend-return-round"),
            programSession("muay-thai-foundations-w4-d2", 2, "Thai Pressure", "Maintain clean fundamentals while the pace climbs.", 40, "muaythai.punch-kick-reset", "muaythai.teep-check-flow", "muaythai.weapons-round", "muaythai.defend-return-round"),
            programSession("muay-thai-foundations-w4-d3", 3, "Eight-Limb Test", "Put stance, hands, teeps, kicks, knees, elbows, and checks together with control.", 45, "muaythai.foundations.one-two", "muaythai.foundations.teep-mechanics", "muaythai.foundations.round-kick-mechanics", "muaythai.foundations.knee-mechanics", "muaythai.foundations.elbow-horizontal", "muaythai.foundations.check-mechanics", "muaythai.weapons-round")
        )
    )
)

private fun kickboxingFoundationsProgram() = TrainingProgram(
    id = "kickboxing-foundations",
    title = "Kickboxing Foundations",
    tagline = "Connect fast boxing combinations to sharp kicks and active movement.",
    styleName = "KICKBOXING",
    level = ProgramLevel.FOUNDATIONS,
    summary = "A 12-session kickboxing progression focused on punch-kick transitions, angle changes, defensive counters, speed, and sustained combination work.",
    weeks = listOf(
        programWeek(1, "Hands Meet Kicks", "Stance, boxing entries, basic kicks, and balanced transitions.",
            programSession("kickboxing-foundations-w1-d1", 1, "Kickboxing Base", "Build stance, guard, movement, and clean straight punches before adding kicks.", 20, "kickboxing.foundations.stance-guard", "kickboxing.foundations.jab-cross", "boxing.jab-only-movement"),
            programSession("kickboxing-foundations-w1-d2", 2, "Round Kick Basics", "Learn the rear round kick by itself before connecting it to punches.", 25, "kickboxing.foundations.rear-round-kick", "kickboxing.foundations.kick-recovery", "kickboxing.one-two-low-kick"),
            programSession("kickboxing-foundations-w1-d3", 3, "Lead Kick & Low Kick", "Learn basic lead-side kicking and low-kick mechanics with a stable recovery.", 25, "kickboxing.foundations.lead-round-kick", "kickboxing.foundations.low-kick", "kickboxing.foundations.kick-recovery")
        ),
        programWeek(2, "Move & Counter", "Angles, exits, defensive responses, and return combinations.",
            programSession("kickboxing-foundations-w2-d1", 1, "Angle Out", "Learn a basic angle step and use it after simple punch-kick work.", 25, "kickboxing.foundations.angle-step", "kickboxing.kick-exit", "boxing.angle-after-combo"),
            programSession("kickboxing-foundations-w2-d2", 2, "Check Basics", "Learn a balanced kick check first, then add a simple return.", 30, "kickboxing.foundations.check", "kickboxing.check-return-combo", "muaythai.body-kick-defense"),
            programSession("kickboxing-foundations-w2-d3", 3, "Entry & Reset", "Enter behind simple punches, add one kick, then recover before repeating.", 30, "kickboxing.foundations.jab-cross", "kickboxing.punch-kick-chain", "kickboxing.foundations.kick-recovery")
        ),
        programWeek(3, "Combination Depth", "Longer combinations, power placement, and pace changes.",
            programSession("kickboxing-foundations-w3-d1", 1, "Combination Chains", "Connect the basics into simple punch-kick sequences.", 30, "kickboxing.one-two-low-kick", "kickboxing.jab-hook-rear-kick", "kickboxing.punch-kick-chain"),
            programSession("kickboxing-foundations-w3-d2", 2, "Power Finish", "Use clean hand combinations to set up a balanced power kick.", 35, "kickboxing.foundations.rear-round-kick", "kickboxing.jab-hook-rear-kick", "kickboxing.cross-lead-kick-cross"),
            programSession("kickboxing-foundations-w3-d3", 3, "Defense to Offense", "Check, move, return a simple combination, and reset.", 35, "kickboxing.foundations.check", "kickboxing.check-return-combo", "kickboxing.kick-exit")
        ),
        programWeek(4, "Pressure & Pace", "Complete rounds, defensive responsibility, and confident output.",
            programSession("kickboxing-foundations-w4-d1", 1, "Counter Pressure", "Use checks, exits, and simple returns while the pace increases.", 35, "kickboxing.check-return-combo", "kickboxing.kick-exit", "kickboxing.combo-range-round"),
            programSession("kickboxing-foundations-w4-d2", 2, "High Output", "Keep stance and clean punch-kick mechanics together through harder rounds.", 40, "kickboxing.punch-kick-chain", "kickboxing.mixed-range-round", "kickboxing.combo-range-round"),
            programSession("kickboxing-foundations-w4-d3", 3, "Kickboxing Test", "Put stance, straight punches, kicks, checks, exits, and simple combinations together.", 45, "kickboxing.foundations.jab-cross", "kickboxing.foundations.rear-round-kick", "kickboxing.foundations.low-kick", "kickboxing.foundations.check", "kickboxing.kick-exit", "kickboxing.one-two-low-kick", "kickboxing.combo-range-round")
        )
    )
)

private fun mmaFoundationsProgram() = TrainingProgram(
    id = "mma-striking-foundations",
    title = "MMA Striking Foundations",
    tagline = "Build striking that respects range changes, pressure, and MMA movement.",
    styleName = "MMA STRIKING",
    level = ProgramLevel.FOUNDATIONS,
    summary = "A 12-session MMA striking plan built around adaptable stance, range control, layered offense, defensive exits, and composure during unpredictable exchanges.",
    weeks = listOf(
        programWeek(1, "MMA Range", "Adaptable stance, straight shots, kicks, and safe movement.",
            programSession("mma-striking-foundations-w1-d1", 1, "MMA Stance & Range", "Build an adaptable MMA stance, guard, and movement before adding offense.", 20, "mma.foundations.stance-guard", "mma.foundations.range-step", "mma.foundations.jab"),
            programSession("mma-striking-foundations-w1-d2", 2, "Straight Weapons", "Learn the jab, cross, and basic low kick separately before linking them.", 25, "mma.foundations.jab", "mma.foundations.cross", "mma.foundations.low-kick"),
            programSession("mma-striking-foundations-w1-d3", 3, "Safe Exits", "Throw a simple straight combination, move off line, and rebuild your stance.", 25, "mma.foundations.one-two-exit", "mma.foundations.circle-exit", "mma.jab-cross-angle-exit")
        ),
        programWeek(2, "React to Pressure", "Defensive movement, counters, and range changes.",
            programSession("mma-striking-foundations-w2-d1", 1, "Pressure Escape", "Learn to circle away, create space, and reset your striking stance.", 25, "mma.foundations.circle-exit", "mma.long-range-reset", "mma.strike-exit-round"),
            programSession("mma-striking-foundations-w2-d2", 2, "Defend & Return", "Use a simple guard or movement response, then answer with one clean straight attack.", 30, "mma.foundations.defend-return", "mma.foundations.one-two-exit", "mma.long-range-reset"),
            programSession("mma-striking-foundations-w2-d3", 3, "Range Switch", "Move from long-range kicks into straight punches, then exit safely.", 30, "mma.foundations.front-kick", "mma.foundations.low-kick", "mma.jab-rear-kick-exit")
        ),
        programWeek(3, "Layer the Offense", "Mixed attacks, feints, rhythm changes, and controlled power.",
            programSession("mma-striking-foundations-w3-d1", 1, "Mixed Combinations", "Connect basic straight punches and kicks while staying ready to move.", 30, "mma.foundations.one-two-exit", "mma.jab-rear-kick-exit", "mma.entry-exit-round"),
            programSession("mma-striking-foundations-w3-d2", 2, "Basic Feints", "Introduce simple level and hand feints without abandoning stance or balance.", 35, "mma.foundations.level-feint", "mma.level-feint-strike", "mma.feint-jab-cross-exit"),
            programSession("mma-striking-foundations-w3-d3", 3, "Power Without Chasing", "Add controlled power to straight shots and kicks while keeping your exit available.", 35, "mma.foundations.cross", "mma.foundations.low-kick", "mma.jab-rear-kick-exit", "mma.long-range-reset")
        ),
        programWeek(4, "Unpredictable Rounds", "Conditioning, decisions, pressure, and complete MMA striking rounds.",
            programSession("mma-striking-foundations-w4-d1", 1, "Decision Rounds", "Choose between jab, kick, exit, or simple return while keeping your base under you.", 35, "mma.foundations.defend-return", "mma.long-range-reset", "mma.entry-exit-round"),
            programSession("mma-striking-foundations-w4-d2", 2, "Pressure Pace", "Keep clean entries, exits, and basic mixed attacks together as the pace rises.", 40, "mma.jab-cross-angle-exit", "mma.jab-rear-kick-exit", "mma.strike-exit-round", "mma.entry-exit-round"),
            programSession("mma-striking-foundations-w4-d3", 3, "MMA Striking Test", "Put stance, range, straight punches, kicks, exits, defense, and basic feints together.", 45, "mma.foundations.jab", "mma.foundations.cross", "mma.foundations.low-kick", "mma.foundations.front-kick", "mma.foundations.circle-exit", "mma.foundations.level-feint", "mma.entry-exit-round")
        )
    )
)
