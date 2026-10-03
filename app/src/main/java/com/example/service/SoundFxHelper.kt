package com.example.service

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin

/**
 * Procedural Synthesized Sound Effects Generator
 * Produces crisp, beautiful 16-bit audio tones without needing external assets.
 */
class SoundFxHelper {

    private val sampleRate = 44100
    private val scope = CoroutineScope(Dispatchers.Default)

    fun playCorrect() {
        scope.launch {
            // Bright, cheerful major arpeggio: C5 (523Hz) -> E5 (659Hz) -> G5 (784Hz) -> C6 (1046Hz)
            playToneSequence(
                listOf(
                    Tone(523.25, 70, 0.4f),
                    Tone(659.25, 70, 0.5f),
                    Tone(783.99, 90, 0.6f),
                    Tone(1046.50, 160, 0.7f)
                )
            )
        }
    }

    fun playWrong() {
        scope.launch {
            // Gentle double low buzz: G3 (196Hz) -> D#3 (155Hz)
            playToneSequence(
                listOf(
                    Tone(196.00, 110, 0.5f),
                    Tone(155.56, 180, 0.45f)
                )
            )
        }
    }

    fun playLevelUp() {
        scope.launch {
            // Royal Victory Fanfare: F5 -> A5 -> C6 -> F6
            playToneSequence(
                listOf(
                    Tone(698.46, 80, 0.5f),
                    Tone(880.00, 80, 0.55f),
                    Tone(1046.50, 90, 0.6f),
                    Tone(1396.91, 240, 0.75f)
                )
            )
        }
    }

    fun playPoke() {
        scope.launch {
            // Sweet robot chirp
            playToneSequence(
                listOf(
                    Tone(880.00, 40, 0.4f),
                    Tone(1174.66, 60, 0.45f)
                )
            )
        }
    }

    private data class Tone(val freq: Double, val durationMs: Int, val volume: Float)

    private fun playToneSequence(tones: List<Tone>) {
        try {
            var totalSamples = 0
            tones.forEach { totalSamples += (sampleRate * it.durationMs) / 1000 }
            val generatedSnd = ShortArray(totalSamples)

            var sampleOffset = 0
            for (tone in tones) {
                val numSamples = (sampleRate * tone.durationMs) / 1000
                for (i in 0 until numSamples) {
                    val time = i.toDouble() / sampleRate
                    // Smooth envelope with attack and decay to prevent audio pops
                    val envelope = if (i < numSamples * 0.1) {
                        (i / (numSamples * 0.1)).toFloat()
                    } else if (i > numSamples * 0.7) {
                        ((numSamples - i) / (numSamples * 0.3)).toFloat()
                    } else {
                        1.0f
                    }
                    val sample = sin(2 * PI * tone.freq * time) * Short.MAX_VALUE * tone.volume * envelope
                    if (sampleOffset + i < generatedSnd.size) {
                        generatedSnd[sampleOffset + i] = sample.toInt().toShort()
                    }
                }
                sampleOffset += numSamples
            }

            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(generatedSnd.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(generatedSnd, 0, generatedSnd.size)
            audioTrack.play()
        } catch (e: Exception) {
            // AudioTrack fallback
        }
    }
}
