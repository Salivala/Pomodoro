import kotlinx.coroutines.*
import java.time.Duration
import java.util.concurrent.TimeUnit

fun main() {
    val tray = TrayManager()
    val pomodoro = PomodoroTimer(
        secondAction = { timeStr -> tray.text = timeStr },
        startBreak = { SoundPlayer().beginBreak(); Duration.ofSeconds(5) },
        startWork = { SoundPlayer().beginWork(); Duration.ofSeconds(30) }
    )
    GlobalScope.launch {
        pomodoro.run()
    }
    TimeUnit.SECONDS.sleep(4)
    println("go")
    pomodoro.pauseRequest = true
}