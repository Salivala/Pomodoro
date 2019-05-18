import java.time.Duration
import java.util.concurrent.TimeUnit

class PomodoroTimer constructor(
    var timeLeft: Duration = Duration.ofSeconds(30),
    var isBreak: Boolean = false,
    var secondAction: (Duration) -> Unit = {;},
    var startBreak: () -> Duration = {Duration.ZERO},
    var startWork: () -> Duration = {Duration.ZERO}
) {
    fun run() {
        while (timeLeft != Duration.ZERO) {
            secondAction(timeLeft)
            TimeUnit.SECONDS.sleep(1)
            timeLeft = timeLeft.minus(Duration.ofSeconds(1))
        }
        secondAction(timeLeft)
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

    private fun executeAction(timeLeft: Duration, action: () -> Duration) {

    }
}