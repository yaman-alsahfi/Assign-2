package asgn2GUI;

/**
 * Main programme for the CargoTextFrame 
 *
 * @author yaman-n8719471
 */
public class ManifestTextManager {

    /**
     * @param args
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CargoTextFrame("Cargo Text Manager 1.0");
            }
        });
    }

}
