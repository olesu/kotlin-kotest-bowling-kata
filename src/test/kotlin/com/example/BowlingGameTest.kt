package com.example

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BowlingGameTest : StringSpec({
    "gutter-game" {
        playedGame(rollMany(20, 0)).score shouldBe 0
    }
    "all ones" {
        playedGame(rollMany(20, 1)).score shouldBe 20
    }

    "one spare" {
        val seriesWithSpare =
            rollSpare() + rollOne(3) + rollMany(17, 0)

        playedGame(seriesWithSpare).score shouldBe 16
    }

    "one strike" {
        val seriesWithStrike =
            rollStrike() + rollOne(3) + rollOne(4) + rollMany(16, 0)

        playedGame(seriesWithStrike).score shouldBe 24
    }

    "perfect game" {
        playedGame(rollMany(12, 10)).score shouldBe 300
    }
})

private fun rollStrike() = rollOne(10)
private fun rollSpare() = rollOne(5) + rollOne(5)
private fun rollOne(pins: Int) = rollMany(1, pins)
private fun rollMany(n: Int, pins: Int) = IntArray(n) { pins }

private fun playedGame(series: IntArray): Game = series.fold(Game()) { game, pins ->
    game.roll(pins)
}

