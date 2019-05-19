import kotlinx.coroutines.*
import java.time.Duration
fun main() {
    println("testing")
    val tray = TrayManager()
    GlobalScope.launch {
        val pomodoro = PomodoroTimer(
            secondAction = { timeLeft -> tray.text = timeLeft.toSeconds().toString() },
            startBreak = { SoundPlayer().BeginBreak(); Duration.ofSeconds(5) },
            startWork = { SoundPlayer().BeginWork(); Duration.ofSeconds(30) }
        )
        pomodoro.run()
    }
    println("testing")
}