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
        g2d.font = Font(g2d.font.name, g2d.font.style, 9)
        g2d.drawString(value, 0, 16)
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