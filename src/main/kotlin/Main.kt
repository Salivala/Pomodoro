import kotlinx.coroutines.*
import java.time.Duration

suspend fun main() {
    val tray = TrayManager()
    val pomodoro = PomodoroTimer(
        secondAction = { timeStr -> tray.text = timeStr },
        startBreak = { SoundPlayer().beginBreak(); Duration.ofSeconds(5) },
        startWork = { SoundPlayer().beginWork(); Duration.ofSeconds(30) }
    )
    val job = GlobalScope.launch {
        pomodoro.run()
    }
    tray.toggleItem.addActionListener {
        if (pomodoro.pauseRequest) {
            tray.toggleText = "Pause"
            pomodoro.pauseRequest = false
        }
        else {
            tray.toggleText = "Resume"
            pomodoro.pauseRequest = true
        }
    }

}

