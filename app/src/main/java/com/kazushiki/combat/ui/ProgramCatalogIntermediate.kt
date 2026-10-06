package com.kazushiki.combat.ui

fun intermediatePrograms(): List<TrainingProgram> = listOf(
    boxingIntermediateProgram(),
    muayThaiIntermediateProgram(),
    kickboxingIntermediateProgram(),
    mmaIntermediateProgram()
)

private fun boxingIntermediateProgram() = TrainingProgram(
    id = "boxing-intermediate",
    title = "Boxing Intermediate",
    tagline = "Layer combinations, counters, angles, rhythm changes, and pressure without losing your base.",
    styleName = "BOXING",
    level = ProgramLevel.INTERMEDIATE,
    summary = "A 12-session boxing progression for fighters who already understand stance and basic punches. Sessions build longer combinations, defense-to-counter chains, feints, angle changes, rhythm control, and higher-output decision rounds.",
    weeks = listOf(
        programWeek(1, "Layer the Offense", "Longer combinations, body-head changes, and exits after offense.",
            programSession("boxing-intermediate-w1-d1", 1, "Double Jab Angles", "Use the double jab to enter, score, and leave from a new angle.", 30, "boxing.double-jab-cross-pivot", "boxing.intermediate.double-jab-angle", "boxing.angle-after-combo"),
            programSession("boxing-intermediate-w1-d2", 2, "Body to Head", "Change levels inside combinations, then move before the return.", 35, "boxing.jab-body-cross", "boxing.intermediate.body-head-angle", "boxing.jab-cross-body-head"),
            programSession("boxing-intermediate-w1-d3", 3, "Hook Chains", "Build compact hook-cross sequences with defensive responsibility.", 35, "boxing.cross-hook-cross", "boxing.intermediate.hook-cross-roll", "boxing.jab-cross-hook-exit")
        ),
        programWeek(2, "Defense Becomes Offense", "Slips, rolls, pulls, counters, and pivots chained into returns.",
            programSession("boxing-intermediate-w2-d1", 1, "Slip & Fire", "Turn a slip into a fast counter and leave before the next exchange.", 35, "boxing.jab-cross-slip", "boxing.intermediate.slip-cross-hook", "boxing.intermediate.counter-choice"),
            programSession("boxing-intermediate-w2-d2", 2, "Roll & Pivot", "Roll under pressure, return with punches, and pivot out cleanly.", 35, "boxing.slip-roll-return", "boxing.intermediate.roll-cross-pivot", "boxing.angle-after-combo"),
            programSession("boxing-intermediate-w2-d3", 3, "Pull Counter", "Use distance as defense, then answer immediately without overreaching.", 40, "boxing.pull-counter-flow", "boxing.intermediate.feint-entry", "boxing.intermediate.counter-choice")
        ),
        programWeek(3, "Control the Rhythm", "Feints, tempo changes, combination depth, and pressure exits.",
            programSession("boxing-intermediate-w3-d1", 1, "Feint & Enter", "Create reactions before committing to your combination.", 40, "boxing.intermediate.feint-entry", "boxing.intermediate.double-jab-angle", "boxing.intermediate.rhythm-change"),
            programSession("boxing-intermediate-w3-d2", 2, "Change the Beat", "Break predictable timing while keeping every punch technically clean.", 40, "boxing.intermediate.rhythm-change", "boxing.one-two-three", "boxing.cross-hook-cross"),
            programSession("boxing-intermediate-w3-d3", 3, "Pressure With Exits", "Sustain offense without getting stuck in front of the target.", 45, "boxing.intermediate.pressure-exit", "boxing.combo-defense-round", "boxing.punch-angle-round")
        ),
        programWeek(4, "Intermediate Fight Rounds", "Decision-making, counters, pressure, and complete boxing rounds.",
            programSession("boxing-intermediate-w4-d1", 1, "Counter Decisions", "Read the imagined attack and choose the correct response instead of pre-planning one counter.", 45, "boxing.intermediate.counter-choice", "boxing.pull-counter-flow", "boxing.intermediate.slip-cross-hook"),
            programSession("boxing-intermediate-w4-d2", 2, "Pressure & Rhythm", "Blend pressure with tempo changes, angles, and defensive finishes.", 45, "boxing.intermediate.rhythm-change", "boxing.intermediate.pressure-exit", "boxing.combo-defense-round"),
            programSession("boxing-intermediate-w4-d3", 3, "Intermediate Boxing Test", "Put layered offense, defense, counters, feints, rhythm, and movement together.", 50, "boxing.intermediate.double-jab-angle", "boxing.intermediate.body-head-angle", "boxing.intermediate.hook-cross-roll", "boxing.intermediate.counter-choice", "boxing.intermediate.pressure-exit")
        )
    )
)

private fun muayThaiIntermediateProgram() = TrainingProgram(
    id = "muay-thai-intermediate",
    title = "Muay Thai Intermediate",
    tagline = "Turn individual weapons into setups, counters, range transitions, and composed fight-paced rounds.",
    styleName = "MUAY THAI",
    level = ProgramLevel.INTERMEDIATE,
    summary = "A 12-session intermediate Muay Thai progression built around punch-kick setups, intercepting teeps, checks into counters, long-guard transitions, elbows and knees inside combinations, rhythm changes, and higher-output eight-limb rounds.",
    weeks = listOf(
        programWeek(1, "Set Up the Weapons", "Use feints and hands to create cleaner kicking opportunities.",
            programSession("muay-thai-intermediate-w1-d1", 1, "Feint to Rear Kick", "Use a believable hand reaction to open the rear kick.", 30, "muaythai.intermediate.jab-feint-kick", "muaythai.cross-lead-hook-kick", "muaythai.punch-kick-reset"),
            programSession("muay-thai-intermediate-w1-d2", 2, "Teep to Hands to Kick", "Control range with the teep, then transition into boxing and the rear kick.", 35, "muaythai.intermediate.teep-cross-hook-kick", "muaythai.teep-control", "muaythai.jab-cross-rear-kick"),
            programSession("muay-thai-intermediate-w1-d3", 3, "Hands Hide the Kick", "Build longer hand-to-kick sequences while staying balanced.", 35, "muaythai.cross-lead-hook-kick", "muaythai.intermediate.jab-feint-kick", "muaythai.intermediate.rhythm-round")
        ),
        programWeek(2, "Defend & Return", "Checks, intercepting teeps, long guard, and immediate balanced counters.",
            programSession("muay-thai-intermediate-w2-d1", 1, "Check Into Combination", "Turn the kick check into a layered return rather than a single counter.", 35, "muaythai.intermediate.check-cross-hook-kick", "muaythai.check-counter", "muaythai.body-kick-defense"),
            programSession("muay-thai-intermediate-w2-d2", 2, "Intercept & Return", "Use the teep to stop pressure and immediately reclaim offense.", 35, "muaythai.intermediate.teep-intercept-return", "muaythai.teep-check-flow", "muaythai.intermediate.rhythm-round"),
            programSession("muay-thai-intermediate-w2-d3", 3, "Long Guard to Knee", "Use the long guard to manage the pocket, knee, and exit without standing square.", 40, "muaythai.long-guard-exit", "muaythai.intermediate.long-guard-knee-exit", "muaythai.intermediate.pressure-round")
        ),
        programWeek(3, "Inside to Outside", "Elbows, knees, kicks, and clean transitions between ranges.",
            programSession("muay-thai-intermediate-w3-d1", 1, "Elbow Entry & Exit", "Enter behind punches, place a controlled elbow, then leave the pocket.", 40, "muaythai.intermediate.cross-hook-elbow", "muaythai.long-guard-exit", "muaythai.intermediate.rhythm-round"),
            programSession("muay-thai-intermediate-w3-d2", 2, "Kick Return Chains", "Recover from your kick, defend the imagined return, and answer again.", 40, "muaythai.intermediate.kick-return-chain", "muaythai.intermediate.check-cross-hook-kick", "muaythai.defend-return-round"),
            programSession("muay-thai-intermediate-w3-d3", 3, "Range Transition Rounds", "Move cleanly between teep range, kicking range, boxing range, and close range.", 45, "muaythai.intermediate.teep-cross-hook-kick", "muaythai.intermediate.long-guard-knee-exit", "muaythai.intermediate.cross-hook-elbow", "muaythai.intermediate.rhythm-round")
        ),
        programWeek(4, "Intermediate Thai Rounds", "Reaction choices, rhythm changes, pressure, and complete eight-limb work.",
            programSession("muay-thai-intermediate-w4-d1", 1, "Defense Choice Rounds", "Choose between check, teep, long guard, or movement before returning offense.", 45, "muaythai.intermediate.teep-intercept-return", "muaythai.intermediate.check-cross-hook-kick", "muaythai.intermediate.kick-return-chain"),
            programSession("muay-thai-intermediate-w4-d2", 2, "Thai Pressure & Rhythm", "Sustain longer exchanges while changing pace and finishing defensively.", 45, "muaythai.intermediate.rhythm-round", "muaythai.intermediate.pressure-round", "muaythai.weapons-round"),
            programSession("muay-thai-intermediate-w4-d3", 3, "Intermediate Muay Thai Test", "Put setups, counters, range changes, elbows, knees, kicks, and defense together.", 50, "muaythai.intermediate.jab-feint-kick", "muaythai.intermediate.check-cross-hook-kick", "muaythai.intermediate.long-guard-knee-exit", "muaythai.intermediate.cross-hook-elbow", "muaythai.intermediate.pressure-round")
        )
    )
)

private fun kickboxingIntermediateProgram() = TrainingProgram(
    id = "kickboxing-intermediate",
    title = "Kickboxing Intermediate",
    tagline = "Build layered punch-kick offense, counter chains, rhythm changes, and movement under pressure.",
    styleName = "KICKBOXING",
    level = ProgramLevel.INTERMEDIATE,
    summary = "A 12-session kickboxing progression for fighters comfortable with basic punches and kicks. It adds longer punch-kick chains, check-and-return sequences, pull counters, feints, angle changes, rhythm control, and harder mixed-range rounds.",
    weeks = listOf(
        programWeek(1, "Extend the Combinations", "Three- to five-action combinations and clean recovery after kicks.",
            programSession("kickboxing-intermediate-w1-d1", 1, "Double Jab to Low Kick", "Use the double jab to build a longer entry into the low kick.", 30, "kickboxing.intermediate.double-jab-low-kick", "kickboxing.one-two-low-kick", "kickboxing.punch-kick-chain"),
            programSession("kickboxing-intermediate-w1-d2", 2, "Kick Back to Hands", "Recover from the kick and continue the combination instead of ending there.", 35, "kickboxing.intermediate.cross-hook-kick-cross", "kickboxing.cross-lead-kick-cross", "kickboxing.intermediate.kick-punch-angle"),
            programSession("kickboxing-intermediate-w1-d3", 3, "Combination Angles", "Finish longer punch-kick chains by moving to a new position.", 35, "kickboxing.intermediate.kick-punch-angle", "kickboxing.kick-exit", "kickboxing.combo-range-round")
        ),
        programWeek(2, "Counter the Return", "Checks, pulls, returns, and angle exits after defensive moments.",
            programSession("kickboxing-intermediate-w2-d1", 1, "Check & Angle", "Check the kick, return a combination, then leave on an angle.", 35, "kickboxing.intermediate.check-return-angle", "kickboxing.check-return-combo", "kickboxing.kick-exit"),
            programSession("kickboxing-intermediate-w2-d2", 2, "Pull & Low Kick", "Use distance defense to create a clean cross-low-kick return.", 35, "kickboxing.intermediate.pull-cross-low-kick", "boxing.pull-counter-flow", "kickboxing.one-two-low-kick"),
            programSession("kickboxing-intermediate-w2-d3", 3, "Defense Into Volume", "Defend first, then build a longer punch-kick answer without losing stance.", 40, "kickboxing.intermediate.check-return-angle", "kickboxing.intermediate.pressure-counter-round", "kickboxing.combo-range-round")
        ),
        programWeek(3, "Feints & Rhythm", "Create reactions, change tempo, and attack from less predictable timing.",
            programSession("kickboxing-intermediate-w3-d1", 1, "Jab Feint to Lead Kick", "Use the jab reaction to open a different kicking line.", 40, "kickboxing.intermediate.jab-feint-lead-kick", "kickboxing.jab-hook-rear-kick", "kickboxing.intermediate.rhythm-round"),
            programSession("kickboxing-intermediate-w3-d2", 2, "Change the Beat", "Vary pace inside punch-kick combinations instead of throwing at one rhythm.", 40, "kickboxing.intermediate.rhythm-round", "kickboxing.intermediate.cross-hook-kick-cross", "kickboxing.mixed-range-round"),
            programSession("kickboxing-intermediate-w3-d3", 3, "Pressure & Counter", "Alternate offensive pressure with deliberate defensive responses and returns.", 45, "kickboxing.intermediate.pressure-counter-round", "kickboxing.intermediate.check-return-angle", "kickboxing.combo-range-round")
        ),
        programWeek(4, "Intermediate Kickboxing Rounds", "Complete combinations, defense, rhythm, pressure, and decision-making.",
            programSession("kickboxing-intermediate-w4-d1", 1, "Counter Choice", "Choose the right defensive answer, return, and exit based on the imagined attack.", 45, "kickboxing.intermediate.check-return-angle", "kickboxing.intermediate.pull-cross-low-kick", "kickboxing.intermediate.pressure-counter-round"),
            programSession("kickboxing-intermediate-w4-d2", 2, "High Output Rhythm", "Keep longer combinations clean while changing pace under fatigue.", 45, "kickboxing.intermediate.rhythm-round", "kickboxing.intermediate.kick-punch-angle", "kickboxing.mixed-range-round"),
            programSession("kickboxing-intermediate-w4-d3", 3, "Intermediate Kickboxing Test", "Put layered offense, counters, feints, movement, and pressure together.", 50, "kickboxing.intermediate.double-jab-low-kick", "kickboxing.intermediate.cross-hook-kick-cross", "kickboxing.intermediate.check-return-angle", "kickboxing.intermediate.jab-feint-lead-kick", "kickboxing.intermediate.pressure-counter-round")
        )
    )
)

private fun mmaIntermediateProgram() = TrainingProgram(
    id = "mma-striking-intermediate",
    title = "MMA Striking Intermediate",
    tagline = "Layer feints, range changes, exits, and pressure-aware striking into adaptable rounds.",
    styleName = "MMA STRIKING",
    level = ProgramLevel.INTERMEDIATE,
    summary = "A 12-session MMA striking program that builds on basic range management with level feints, mixed punch-kick entries, defensive returns, angle exits, range decisions, and pressure rounds where every exchange must account for changing distance.",
    weeks = listOf(
        programWeek(1, "Layer the Entry", "Feints, straight attacks, low kicks, and immediate exits.",
            programSession("mma-striking-intermediate-w1-d1", 1, "Level Feint Entry", "Use the level change to create a striking opening without overcommitting.", 30, "mma.intermediate.level-feint-cross-kick", "mma.level-feint-strike", "mma.feint-jab-cross-exit"),
            programSession("mma-striking-intermediate-w1-d2", 2, "Angle to Low Kick", "Move off the center line before finishing the exchange with a low kick.", 35, "mma.intermediate.jab-cross-angle-kick", "mma.jab-cross-angle-exit", "mma.long-range-reset"),
            programSession("mma-striking-intermediate-w1-d3", 3, "Front Kick to Boxing", "Use long-range control to enter boxing range, then leave immediately.", 35, "mma.intermediate.front-kick-cross-exit", "mma.jab-rear-kick-exit", "mma.entry-exit-round")
        ),
        programWeek(2, "Defend & Change Range", "Defensive returns and movement between long, boxing, and exit range.",
            programSession("mma-striking-intermediate-w2-d1", 1, "Defend & Exit", "Answer a straight attack, score, and leave boxing range before the exchange extends.", 35, "mma.intermediate.defend-cross-exit", "mma.long-range-reset", "mma.strike-exit-round"),
            programSession("mma-striking-intermediate-w2-d2", 2, "Range Choice", "Match your attack to the distance rather than forcing the same combination.", 40, "mma.intermediate.range-choice-round", "mma.intermediate.front-kick-cross-exit", "mma.entry-exit-round"),
            programSession("mma-striking-intermediate-w2-d3", 3, "Pressure Escape & Return", "Leave pressure, regain range, then re-enter on your terms.", 40, "mma.intermediate.pressure-exit-round", "mma.intermediate.defend-cross-exit", "mma.jab-cross-angle-exit")
        ),
        programWeek(3, "Feint & Decide", "Create reactions and select the attack from the response you imagine.",
            programSession("mma-striking-intermediate-w3-d1", 1, "Feint to Mixed Attack", "Use level and hand feints to open either punches or kicks.", 40, "mma.intermediate.feint-jab-kick-exit", "mma.intermediate.level-feint-cross-kick", "mma.intermediate.feint-decision-round"),
            programSession("mma-striking-intermediate-w3-d2", 2, "Range Decisions", "Switch between long attacks, boxing entries, and exits as the imagined range changes.", 45, "mma.intermediate.range-choice-round", "mma.intermediate.jab-cross-angle-kick", "mma.intermediate.front-kick-cross-exit"),
            programSession("mma-striking-intermediate-w3-d3", 3, "Pressure With Discipline", "Apply pressure without standing square or remaining in the pocket too long.", 45, "mma.intermediate.pressure-exit-round", "mma.intermediate.feint-decision-round", "mma.entry-exit-round")
        ),
        programWeek(4, "Intermediate MMA Striking Rounds", "Adaptive decisions, feints, mixed offense, pressure, and safe exits.",
            programSession("mma-striking-intermediate-w4-d1", 1, "Decision Rounds", "React to range and pressure cues instead of pre-selecting every exchange.", 45, "mma.intermediate.range-choice-round", "mma.intermediate.feint-decision-round", "mma.intermediate.defend-cross-exit"),
            programSession("mma-striking-intermediate-w4-d2", 2, "Pressure & Exit Pace", "Maintain output while every exchange still ends with responsible movement.", 45, "mma.intermediate.pressure-exit-round", "mma.intermediate.jab-cross-angle-kick", "mma.strike-exit-round"),
            programSession("mma-striking-intermediate-w4-d3", 3, "Intermediate MMA Striking Test", "Put feints, range changes, mixed attacks, defense, pressure, and exits together.", 50, "mma.intermediate.level-feint-cross-kick", "mma.intermediate.front-kick-cross-exit", "mma.intermediate.defend-cross-exit", "mma.intermediate.range-choice-round", "mma.intermediate.pressure-exit-round")
        )
    )
)
