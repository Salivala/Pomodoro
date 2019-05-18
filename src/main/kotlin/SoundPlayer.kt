import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.DataLine
import javax.sound.sampled.DataLine.*

class SoundPlayer {
    fun BeginBreak() {
        var clip = prep("tspt_pull_bell_02_065.wav")
        clip.start()
    }

    fun BeginWork() {
       var clip = prep("impact_metal_bowl_bell_like_002.wav")
        clip.start()
    }

    fun prep(pathname: String): Clip {
        var soundFile = File(pathname)
        var strm = AudioSystem.getAudioInputStream(soundFile)
        var info = Info(Clip::class.java, strm.format)
        var audioClip = AudioSystem.getLine(info) as Clip
        audioClip.open(strm)
        return audioClip
    }
}