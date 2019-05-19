import java.time.Duration
import java.util.concurrent.TimeUnit

class PomodoroTimer constructor(
    private var timeLeft: Duration = Duration.ofSeconds(30),
    private var isBreak: Boolean = false,
    var secondAction: (String) -> Unit = {;},
    var startBreak: () -> Duration = {Duration.ZERO},
    var startWork: () -> Duration = {Duration.ZERO}
) {
    private val timeStr: String
        get() {
        return timeLeft.seconds.toString() + if(isBreak) "b" else "w"
    }
    fun run() {
        while (timeLeft != Duration.ZERO) {
            secondAction(timeStr)
            TimeUnit.SECONDS.sleep(1)
            timeLeft = timeLeft.minus(Duration.ofSeconds(1))
        }
        secondAction(timeStr)
        TimeUnit.SECONDS.sleep(1)

        timeLeft = if(isBreak) startWork() else startBreak()
        isBreak = !isBreak
        if(timeLeft != Duration.ZERO) {
            run()
        }
        else {
            System.exit(1)
        }
    }
}