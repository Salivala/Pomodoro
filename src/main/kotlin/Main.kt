import kotlinx.coroutines.*
import java.time.Duration
fun main() {
    val tray = TrayManager()
    GlobalScope.launch {
        val pomodoro = PomodoroTimer(
            secondAction = { timeStr -> tray.text = timeStr },
            startBreak = { SoundPlayer().beginBreak(); Duration.ofSeconds(5) },
            startWork = { SoundPlayer().beginWork(); Duration.ofSeconds(30) }
        )
        pomodoro.run()
    }
}