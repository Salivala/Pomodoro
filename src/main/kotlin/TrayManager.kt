import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import java.awt.*
import java.awt.event.ActionListener
import java.awt.image.BufferedImage
import javax.swing.Action

/**
 * Manages Tray Icon State and update actions
 * NOTE:
 */
class TrayManager {
    private val tray: SystemTray? = if(SystemTray.isSupported()) SystemTray.getSystemTray() else null
    private val popupMenu: PopupMenu = PopupMenu()
    private lateinit var trayIcon: TrayIcon
    private val exitItem = MenuItem("Exit Pomodoro")

    // Text of the icon
    var text: String = ""
    set(value) {
        val image: BufferedImage = BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB)
        val g2d = image.createGraphics()
        var rect = g2d.fontMetrics.getStringBounds(value, g2d)
        g2d.color = if(value.last() == 'w') Color.RED else Color.GREEN
        g2d.fillRect(0,0 - g2d.fontMetrics.ascent, rect.width.toInt(), rect.height.toInt())
        g2d.font = Font(g2d.font.name, g2d.font.style, 13)
        g2d.drawString(value.substring(0, value.length - 1), 1, 16)
        g2d.dispose()
        trayIcon.image = image
        field = value

    }

    init {
        if (tray == null) {
            println("System Tray not supported")
        }
        else {
            trayIcon = TrayIcon(getBlankImage(), "Pomodoro", popupMenu)
            exitItem.addActionListener {System.exit(1); GlobalScope.cancel(cause = null)}
            popupMenu.add(exitItem)
            tray.add(trayIcon)
        }
    }
    private fun getBlankImage(): BufferedImage {
        return BufferedImage(16,16, BufferedImage.TYPE_INT_ARGB)
    }

    fun addItem(text: String, action: ()->Unit) {
        val item = MenuItem(text)
        item.addActionListener { run(action) }
        popupMenu.add(item)
    }

    fun removeItem(){
        popupMenu.remove(popupMenu.itemCount - 1)
    }
}