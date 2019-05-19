import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.DataLine.*

class SoundPlayer {
    fun beginBreak() {
        val clip = prep("tspt_pull_bell_02_065.wav")
        clip.start()
    }

    fun beginWork() {
       val clip = prep("impact_metal_bowl_bell_like_002.wav")
        clip.start()
    }

    private fun prep(pathname: String): Clip {
        val soundFile = File(pathname)
        val strm = AudioSystem.getAudioInputStream(soundFile)
        val info = Info(Clip::class.java, strm.format)
        val audioClip = AudioSystem.getLine(info) as Clip
        audioClip.open(strm)
        return audioClip
    }
}