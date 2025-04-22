import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

// Abstract parent class: Base Journal
abstract class Journal extends JPanel {
    private BufferedImage journalBase;

    public Journal() {
        loadImage();
    }

    protected abstract void loadImage();

    protected void setJournalBase(String filePath) {
        try {
            journalBase = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected BufferedImage getJournalBase() {
        return journalBase;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (journalBase != null) {
            g.drawImage(journalBase, 0, 0, this);
        }
    }
}

// Child class: Adds static image of journal
class JournalWithCover extends Journal {
    private BufferedImage coverImage;

    public JournalWithCover() {
        super();
    }

    @Override
    protected void loadImage() {
        setJournalBase("journal1.jpg");
        try {
            coverImage = ImageIO.read(new File("journal1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (coverImage != null) {
            g.drawImage(coverImage, 0, 0, this);
        }
    }
}

// Grandchild class: Adds a random logo
class JournalWithRandomLogo extends JournalWithCover {
    private BufferedImage logoImage;
    private String logoName;

    public JournalWithRandomLogo() {
        super();
        loadImage();
    }

    @Override
    protected void loadImage() {
        super.loadImage();

        int choice = (int) (Math.random() * 2);
        logoName = (choice == 0) ? "logo1.webp" : "logo2.webp";

        try {
            logoImage = ImageIO.read(new File(logoName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (logoImage != null) {
            g.drawImage(logoImage, 200, 100, this); // Adjust position if needed
        }
        g.setColor(Color.BLACK);
        g.drawString("Logo used: " + logoName, 10, 280);
    }
}

// Main GUI class
public class JournalGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Journal GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 300);

            Journal journal = new JournalWithRandomLogo();
            frame.add(journal);

            frame.setVisible(true);
        });
    }
}
