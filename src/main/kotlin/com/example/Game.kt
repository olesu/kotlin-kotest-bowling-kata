package com.example

class Game(
    private val rolls: IntArray = IntArray(21),
    private val currentRoll: Int = 0
) {
    fun roll(pins: Int): Game = Game(
        rolls = rolls.copyOf().apply { this[currentRoll] = pins },
        currentRoll = currentRoll + 1
    )

    val score: Int
        get() = (0 until 10)
            .fold(Pair(0, 0)) { (score, frameIndex), _ ->
                scoreForFrame(frameIndex, score)
            }
            .let { (score, _) ->
                score
            }

    private fun scoreForFrame(frameIndex: Int, score: Int) =
        when {
            isStrike(frameIndex) ->
                Pair(score + 10 + strikeBonus(frameIndex), frameIndex + 1)

            isSpare(frameIndex) ->
                Pair(score + 10 + spareBonus(frameIndex), frameIndex + 2)

            else ->
                Pair(score + sumOfBallsInFrame(frameIndex), frameIndex + 2)
        }

    private fun strikeBonus(frameIndex: Int) = rolls[frameIndex + 1] + rolls[frameIndex + 2]
    private fun spareBonus(frameIndex: Int) = rolls[frameIndex + 2]
    private fun sumOfBallsInFrame(frameIndex: Int) = rolls[frameIndex] + rolls[frameIndex + 1]

    private fun isStrike(frameIndex: Int) = rolls[frameIndex] == 10
    private fun isSpare(frameIndex: Int) = rolls[frameIndex] + rolls[frameIndex + 1] == 10
}
