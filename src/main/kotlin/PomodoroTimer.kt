import java.time.Duration
import java.util.concurrent.TimeUnit

/**
 * Managed logic
 */
class PomodoroTimer constructor(
    private var timeLeft: Duration = Duration.ofMinutes(30),
    private var isBreak: Boolean = false,
    var secondAction: (String) -> Unit = {;},
    var startBreak: () -> Duration = {Duration.ZERO},
    var startWork: () -> Duration = {Duration.ZERO}
) {
    var pauseRequest: Boolean = false
    set(value) {
        if(value) {
            pauseRecover = value
        }
        field = value
    }
    private var pauseRecover: Boolean = false
    private val timeStr: String
        get() {
        return timeLeft.toMinutes().toString() + if(isBreak) "b" else "w"
    }
    fun run() {
        while (timeLeft != Duration.ZERO && !pauseRequest) {
            secondAction(timeStr)
            TimeUnit.MINUTES.sleep(1)
            timeLeft = timeLeft.minus(Duration.ofMinutes(1))
        }
        secondAction(timeStr)
        TimeUnit.MINUTES.sleep(1)

        if(!pauseRecover) {
            isBreak = !isBreak
        }
        if(!pauseRequest) {
            if (isBreak && !pauseRecover) {
                timeLeft = startBreak()
//                isBreak = !isBreak
            }
            else if(!isBreak && !pauseRecover) {
                timeLeft = startWork()
//                isBreak = !isBreak
            }
            pauseRecover = false
        }

        if(timeLeft != Duration.ZERO) {
            run()
        }
        else {
            System.exit(1)
        }

    }

}