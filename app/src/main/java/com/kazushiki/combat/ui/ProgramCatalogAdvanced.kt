package com.kazushiki.combat.ui

fun advancedPrograms(): List<TrainingProgram> = listOf(
    boxingAdvancedProgram(),
    muayThaiAdvancedProgram(),
    kickboxingAdvancedProgram(),
    mmaAdvancedProgram()
)

private fun boxingAdvancedProgram() = TrainingProgram(
    id = "boxing-advanced",
    title = "Boxing Advanced",
    tagline = "Sharpen layered offense, counter-the-counter reactions, broken rhythm, and pressure decisions at fight pace.",
    styleName = "BOXING",
    level = ProgramLevel.ADVANCED,
    summary = "A 12-session advanced boxing program for experienced strikers. Sessions emphasize layered feints, body-head manipulation, second and third defensive decisions, counter-the-counter sequences, broken rhythm, pressure escapes, and technically clean output under fatigue.",
    weeks = listOf(
        programWeek(1, "Layer the Attack", "Feints, level changes, long combinations, and exits after scoring.",
            programSession("boxing-advanced-w1-d1", 1, "Feint, Change Level, Exit", "Manipulate the guard, attack body and head, then leave before the return.", 40, "boxing.advanced.feint-body-head-angle", "boxing.intermediate.body-head-angle", "boxing.intermediate.feint-entry", rest = 20),
            programSession("boxing-advanced-w1-d2", 2, "Five-Punch Responsibility", "Build longer combinations that still end with defense and position.", 45, "boxing.advanced.five-punch-exit", "boxing.intermediate.rhythm-change", "boxing.combo-defense-round", rest = 20),
            programSession("boxing-advanced-w1-d3", 3, "Broken Rhythm Entries", "Use pauses and sudden bursts to enter without becoming predictable.", 45, "boxing.advanced.rhythm-break", "boxing.intermediate.double-jab-angle", "boxing.advanced.feint-body-head-angle", rest = 20)
        ),
        programWeek(2, "Win the Second Exchange", "Counter-the-counter sequences, layered defense, and pressure escapes.",
            programSession("boxing-advanced-w2-d1", 1, "Counter the Counter", "Defend, answer, defend the return, and score again before exiting.", 45, "boxing.advanced.counter-counter", "boxing.intermediate.counter-choice", "boxing.intermediate.hook-cross-roll", rest = 20),
            programSession("boxing-advanced-w2-d2", 2, "Escape the Trap", "Create exits under pressure instead of retreating in a straight line.", 45, "boxing.advanced.trap-exit-round", "boxing.intermediate.pressure-exit", "boxing.punch-angle-round", rest = 20),
            programSession("boxing-advanced-w2-d3", 3, "Reactive Defense Chains", "Respond to changing attacks with different defenses and counters each exchange.", 50, "boxing.advanced.read-react-round", "boxing.advanced.counter-counter", "boxing.intermediate.counter-choice", rest = 20)
        ),
        programWeek(3, "Fight-Pace Control", "Broken rhythm, pressure, power, and technical decisions under fatigue.",
            programSession("boxing-advanced-w3-d1", 1, "Broken Rhythm Pressure", "Pressure without becoming predictable or standing still after combinations.", 50, "boxing.advanced.rhythm-break", "boxing.advanced.trap-exit-round", "boxing.intermediate.pressure-exit", rest = 20),
            programSession("boxing-advanced-w3-d2", 2, "Power Under Fatigue", "Hold clean mechanics when output and power demands rise.", 50, "boxing.advanced.power-fatigue", "boxing.advanced.five-punch-exit", "boxing.intermediate.body-head-angle", rest = 20),
            programSession("boxing-advanced-w3-d3", 3, "Read, React, Reposition", "Make repeated tactical decisions rather than running scripted combinations.", 55, "boxing.advanced.read-react-round", "boxing.advanced.rhythm-break", "boxing.advanced.trap-exit-round", rest = 20)
        ),
        programWeek(4, "Advanced Fight Rounds", "Complete rounds with layered offense, reactions, pressure, and composure.",
            programSession("boxing-advanced-w4-d1", 1, "Second & Third Decision Rounds", "Keep solving the exchange after your first counter or combination.", 55, "boxing.advanced.counter-counter", "boxing.advanced.read-react-round", "boxing.advanced.fight-round", rest = 20),
            programSession("boxing-advanced-w4-d2", 2, "Championship Pace", "Blend broken rhythm, pressure, power, and exits without losing technique.", 55, "boxing.advanced.rhythm-break", "boxing.advanced.power-fatigue", "boxing.advanced.fight-round", rest = 20),
            programSession("boxing-advanced-w4-d3", 3, "Advanced Boxing Test", "Put feints, long combinations, counter chains, pressure escapes, rhythm, and fight pace together.", 60, "boxing.advanced.feint-body-head-angle", "boxing.advanced.counter-counter", "boxing.advanced.trap-exit-round", "boxing.advanced.read-react-round", "boxing.advanced.fight-round", rest = 20)
        )
    )
)

private fun muayThaiAdvancedProgram() = TrainingProgram(
    id = "muay-thai-advanced",
    title = "Muay Thai Advanced",
    tagline = "Layer feints, counters, range transitions, second exchanges, and eight-limb pressure at fight pace.",
    styleName = "MUAY THAI",
    level = ProgramLevel.ADVANCED,
    summary = "A 12-session advanced Muay Thai progression built around layered feints, check-return-check sequences, intercepting reactions, close-to-long-range transitions, rhythm manipulation, pressure, and technically responsible eight-limb work under fatigue.",
    weeks = listOf(
        programWeek(1, "Manipulate the Reaction", "Teep and hand feints, layered kick setups, and multi-range combinations.",
            programSession("muay-thai-advanced-w1-d1", 1, "Teep Feint to Kick", "Use the teep reaction to build a longer hand-to-kick sequence.", 40, "muaythai.advanced.feint-teep-kick", "muaythai.intermediate.jab-feint-kick", "muaythai.intermediate.teep-cross-hook-kick", rest = 20),
            programSession("muay-thai-advanced-w1-d2", 2, "Inside to Outside", "Move from punches and elbows to knees, then escape back to kicking range.", 45, "muaythai.advanced.elbow-knee-exit", "muaythai.intermediate.long-guard-knee-exit", "muaythai.advanced.range-cycle", rest = 20),
            programSession("muay-thai-advanced-w1-d3", 3, "Four-Range Flow", "Use the right weapon as the imagined range changes throughout the round.", 45, "muaythai.advanced.range-cycle", "muaythai.intermediate.rhythm-round", "muaythai.intermediate.cross-hook-elbow", rest = 20)
        ),
        programWeek(2, "Win the Return", "Check-return-check chains, counter selection, and repeated defensive decisions.",
            programSession("muay-thai-advanced-w2-d1", 1, "Check, Return, Check Again", "Stay composed through the second kick exchange instead of stopping after one counter.", 45, "muaythai.advanced.check-return-check", "muaythai.intermediate.check-cross-hook-kick", "muaythai.intermediate.kick-return-chain", rest = 20),
            programSession("muay-thai-advanced-w2-d2", 2, "Counter Choice", "Match checks, teeps, long guard, movement, and counters to the imagined attack.", 45, "muaythai.advanced.counter-choice", "muaythai.intermediate.teep-intercept-return", "muaythai.intermediate.long-guard-knee-exit", rest = 20),
            programSession("muay-thai-advanced-w2-d3", 3, "Second Exchange Rounds", "Defend and counter through multiple actions before resetting range.", 50, "muaythai.advanced.check-return-check", "muaythai.advanced.counter-choice", "muaythai.intermediate.kick-return-chain", rest = 20)
        ),
        programWeek(3, "Pressure With Structure", "Rhythm manipulation, long exchanges, range transitions, and fatigue control.",
            programSession("muay-thai-advanced-w3-d1", 1, "Rhythm Pressure", "Change tempo inside longer eight-limb exchanges while staying balanced.", 50, "muaythai.advanced.rhythm-pressure", "muaythai.intermediate.rhythm-round", "muaythai.advanced.range-cycle", rest = 20),
            programSession("muay-thai-advanced-w3-d2", 2, "Eight-Limb Fatigue", "Keep punches, kicks, knees, elbows, teeps, and checks technically clean under fatigue.", 50, "muaythai.advanced.fatigue-weapons", "muaythai.advanced.elbow-knee-exit", "muaythai.intermediate.pressure-round", rest = 20),
            programSession("muay-thai-advanced-w3-d3", 3, "Range & Counter Chaos", "React to changing distance and attacks without forcing one favorite answer.", 55, "muaythai.advanced.counter-choice", "muaythai.advanced.range-cycle", "muaythai.advanced.rhythm-pressure", rest = 20)
        ),
        programWeek(4, "Advanced Thai Rounds", "Fight-paced decisions, pressure, layered counters, and full eight-limb integration.",
            programSession("muay-thai-advanced-w4-d1", 1, "Advanced Counter Rounds", "Solve repeated attacks with changing defenses and layered returns.", 55, "muaythai.advanced.check-return-check", "muaythai.advanced.counter-choice", "muaythai.advanced.fight-round", rest = 20),
            programSession("muay-thai-advanced-w4-d2", 2, "Fight-Pace Eight Limbs", "Sustain pressure and weapon variety without losing posture or defensive responsibility.", 55, "muaythai.advanced.rhythm-pressure", "muaythai.advanced.fatigue-weapons", "muaythai.advanced.fight-round", rest = 20),
            programSession("muay-thai-advanced-w4-d3", 3, "Advanced Muay Thai Test", "Put feints, second exchanges, range transitions, counters, pressure, and all eight limbs together.", 60, "muaythai.advanced.feint-teep-kick", "muaythai.advanced.check-return-check", "muaythai.advanced.elbow-knee-exit", "muaythai.advanced.counter-choice", "muaythai.advanced.fight-round", rest = 20)
        )
    )
)

private fun kickboxingAdvancedProgram() = TrainingProgram(
    id = "kickboxing-advanced",
    title = "Kickboxing Advanced",
    tagline = "Build complex punch-kick chains, layered counters, rhythm traps, and high-output range control.",
    styleName = "KICKBOXING",
    level = ProgramLevel.ADVANCED,
    summary = "A 12-session advanced kickboxing plan centered on six-action combination chains, feints that create opposite-side attacks, check-counter-counter sequences, broken rhythm, range cycling, pressure decisions, and technically clean power under fatigue.",
    weeks = listOf(
        programWeek(1, "Build the Trap", "Feints, long punch-kick chains, and attacks that break established patterns.",
            programSession("kickboxing-advanced-w1-d1", 1, "Low Feint to High Attack", "Create a low-line reaction, then exploit the opening with hands and a higher finish.", 40, "kickboxing.advanced.feint-low-high", "kickboxing.intermediate.jab-feint-lead-kick", "kickboxing.intermediate.cross-hook-kick-cross", rest = 20),
            programSession("kickboxing-advanced-w1-d2", 2, "Six-Strike Chains", "Build long combinations without losing stance between punches and kicks.", 45, "kickboxing.advanced.six-strike-chain", "kickboxing.intermediate.kick-punch-angle", "kickboxing.intermediate.rhythm-round", rest = 20),
            programSession("kickboxing-advanced-w1-d3", 3, "Rhythm Traps", "Establish a pattern, then deliberately break it with a different attack or angle.", 45, "kickboxing.advanced.rhythm-trap", "kickboxing.advanced.feint-low-high", "kickboxing.intermediate.rhythm-round", rest = 20)
        ),
        programWeek(2, "Win the Counter Exchange", "Check-counter-counter sequences, defensive changes, and immediate repositioning.",
            programSession("kickboxing-advanced-w2-d1", 1, "Check, Counter, Counter Again", "Stay technically responsible through the opponent's imagined return.", 45, "kickboxing.advanced.check-counter-counter", "kickboxing.intermediate.check-return-angle", "kickboxing.intermediate.pressure-counter-round", rest = 20),
            programSession("kickboxing-advanced-w2-d2", 2, "Range Cycle", "Choose the correct punch-kick sequence as distance changes.", 45, "kickboxing.advanced.range-cycle", "kickboxing.intermediate.kick-punch-angle", "kickboxing.kick-exit", rest = 20),
            programSession("kickboxing-advanced-w2-d3", 3, "Pressure Counter Decisions", "Switch between attacking pressure and reactive defensive returns.", 50, "kickboxing.advanced.pressure-counter", "kickboxing.advanced.check-counter-counter", "kickboxing.intermediate.pull-cross-low-kick", rest = 20)
        ),
        programWeek(3, "High Output, High Control", "Power, broken rhythm, range control, and long combinations under fatigue.",
            programSession("kickboxing-advanced-w3-d1", 1, "Broken Rhythm Chains", "Use long combinations without giving the opponent one predictable tempo.", 50, "kickboxing.advanced.rhythm-trap", "kickboxing.advanced.six-strike-chain", "kickboxing.intermediate.rhythm-round", rest = 20),
            programSession("kickboxing-advanced-w3-d2", 2, "Power Under Fatigue", "Maintain kick recovery and punching structure while power demands rise.", 50, "kickboxing.advanced.power-fatigue", "kickboxing.advanced.six-strike-chain", "kickboxing.intermediate.cross-hook-kick-cross", rest = 20),
            programSession("kickboxing-advanced-w3-d3", 3, "Range Pressure", "Pressure from the correct range and leave before becoming stationary.", 55, "kickboxing.advanced.range-cycle", "kickboxing.advanced.pressure-counter", "kickboxing.advanced.rhythm-trap", rest = 20)
        ),
        programWeek(4, "Advanced Kickboxing Rounds", "Complete fight-paced combinations, counters, rhythm, power, and movement.",
            programSession("kickboxing-advanced-w4-d1", 1, "Counter Layers", "React beyond the first defensive exchange and keep solving the return.", 55, "kickboxing.advanced.check-counter-counter", "kickboxing.advanced.pressure-counter", "kickboxing.advanced.fight-round", rest = 20),
            programSession("kickboxing-advanced-w4-d2", 2, "Fight-Pace Output", "Blend rhythm traps, power, long combinations, and range control under fatigue.", 55, "kickboxing.advanced.rhythm-trap", "kickboxing.advanced.power-fatigue", "kickboxing.advanced.fight-round", rest = 20),
            programSession("kickboxing-advanced-w4-d3", 3, "Advanced Kickboxing Test", "Put feints, six-strike chains, counter layers, range changes, and fight pace together.", 60, "kickboxing.advanced.feint-low-high", "kickboxing.advanced.check-counter-counter", "kickboxing.advanced.six-strike-chain", "kickboxing.advanced.range-cycle", "kickboxing.advanced.fight-round", rest = 20)
        )
    )
)

private fun mmaAdvancedProgram() = TrainingProgram(
    id = "mma-striking-advanced",
    title = "MMA Striking Advanced",
    tagline = "Make layered striking decisions across changing ranges, pressure, feints, and fatigue.",
    styleName = "MMA STRIKING",
    level = ProgramLevel.ADVANCED,
    summary = "A 12-session advanced MMA striking program built around layered hand and level feints, range-switching counters, wall-pressure escapes, multi-decision exchanges, unpredictable distance changes, and fight-paced striking where every attack must preserve an exit.",
    weeks = listOf(
        programWeek(1, "Layer the Information", "Double feints, mixed attacks, and exits built into every entry.",
            programSession("mma-striking-advanced-w1-d1", 1, "Feint, Read, Attack", "Use the reaction to choose the attack instead of scripting the whole entry.", 40, "mma.advanced.level-feint-chain", "mma.intermediate.feint-decision-round", "mma.intermediate.level-feint-cross-kick", rest = 20),
            programSession("mma-striking-advanced-w1-d2", 2, "Double Feint Entry", "Layer hand and level feints before a compact attack and immediate exit.", 45, "mma.advanced.double-feint-entry", "mma.advanced.level-feint-chain", "mma.intermediate.feint-jab-kick-exit", rest = 20),
            programSession("mma-striking-advanced-w1-d3", 3, "Counter & Switch Range", "Defend, score, and move to a new distance before the exchange continues.", 45, "mma.advanced.counter-range-switch", "mma.intermediate.defend-cross-exit", "mma.intermediate.range-choice-round", rest = 20)
        ),
        programWeek(2, "Escape Pressure, Reclaim Space", "Wall-pressure exits, range resets, and layered defensive choices.",
            programSession("mma-striking-advanced-w2-d1", 1, "Wall Escape Striking", "Create an angle under pressure, escape, and regain long range before attacking.", 45, "mma.advanced.cage-escape-round", "mma.intermediate.pressure-exit-round", "mma.advanced.counter-range-switch", rest = 20),
            programSession("mma-striking-advanced-w2-d2", 2, "Unpredictable Range", "Change distance constantly so every attack fits the range in front of you.", 45, "mma.advanced.range-chaos", "mma.intermediate.range-choice-round", "mma.advanced.level-feint-chain", rest = 20),
            programSession("mma-striking-advanced-w2-d3", 3, "Multi-Decision Defense", "Make several tactical choices inside one exchange without freezing or overcommitting.", 50, "mma.advanced.decision-chain", "mma.advanced.counter-range-switch", "mma.advanced.cage-escape-round", rest = 20)
        ),
        programWeek(3, "Chaos With Structure", "Pressure, range unpredictability, layered feints, and decision quality under fatigue.",
            programSession("mma-striking-advanced-w3-d1", 1, "Range Chaos", "Attack from changing distances without settling into one predictable striking range.", 50, "mma.advanced.range-chaos", "mma.advanced.double-feint-entry", "mma.intermediate.range-choice-round", rest = 20),
            programSession("mma-striking-advanced-w3-d2", 2, "Fatigue Decisions", "Keep decision quality and exits sharp while the work rate rises.", 50, "mma.advanced.fatigue-decisions", "mma.advanced.decision-chain", "mma.intermediate.pressure-exit-round", rest = 20),
            programSession("mma-striking-advanced-w3-d3", 3, "Pressure Escape & Re-entry", "Escape pressure, reset range, then re-enter behind a layered feint.", 55, "mma.advanced.cage-escape-round", "mma.advanced.level-feint-chain", "mma.advanced.range-chaos", rest = 20)
        ),
        programWeek(4, "Advanced MMA Striking Rounds", "Complete fight-paced decisions across range, pressure, counters, and fatigue.",
            programSession("mma-striking-advanced-w4-d1", 1, "Multi-Decision Rounds", "Solve several changing problems inside every exchange without scripting the outcome.", 55, "mma.advanced.decision-chain", "mma.advanced.counter-range-switch", "mma.advanced.fight-round", rest = 20),
            programSession("mma-striking-advanced-w4-d2", 2, "Fight-Pace Range Chaos", "Maintain output while range, pressure, and attack choices keep changing.", 55, "mma.advanced.range-chaos", "mma.advanced.fatigue-decisions", "mma.advanced.fight-round", rest = 20),
            programSession("mma-striking-advanced-w4-d3", 3, "Advanced MMA Striking Test", "Put double feints, range changes, pressure escapes, counter layers, and fatigue decisions together.", 60, "mma.advanced.double-feint-entry", "mma.advanced.counter-range-switch", "mma.advanced.cage-escape-round", "mma.advanced.decision-chain", "mma.advanced.fight-round", rest = 20)
        )
    )
)
