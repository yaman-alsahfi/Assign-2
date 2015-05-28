package asgn2GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import asgn2Exceptions.CargoException;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;

/**
 * Creates a dialog box allowing the user to enter parameters for a new <code>CargoManifest</code>.
 *
 * @author yaman-n8719471
 */
public class ManifestDialog extends AbstractDialog {

    private static final int HEIGHT = 150;
    private static final int WIDTH = 250;

    private JTextField txtNumStacks;
    private JTextField txtMaxHeight;
    private JTextField txtMaxWeight;

    private CargoManifest manifest;

    /**
     * Constructs a modal dialog box that gathers information required for creating a cargo
     * manifest.
     *
     * @param parent the frame which created this dialog box.
     */
    private ManifestDialog(JFrame parent) {
        super(parent, "Create Manifest", WIDTH, HEIGHT);
        setName("New Manifest");
        setResizable(false);
        manifest = null;
    }

    /**
     * @see AbstractDialog.createContentPanel()
     */
    @Override
    protected JPanel createContentPanel() {

        txtNumStacks = createTextField(8, "Number of Stacks");
        txtMaxHeight = createTextField(8, "Maximum Height");
        txtMaxWeight = createTextField(8, "Maximum Weight");

        JPanel toReturn = new JPanel();
        toReturn.setLayout(new GridBagLayout());

        //Implementation here
        GridBagConstraints c = new GridBagConstraints();
        
        addToPanel(toReturn, txtNumStacks, c, 1, 0, 1, 1);        
        addToPanel(toReturn, txtMaxHeight, c, 1, 1, 1, 1);        
        addToPanel(toReturn, txtMaxWeight, c, 1, 2, 1, 1);
        
        c.anchor = GridBagConstraints.EAST;
        addToPanel(toReturn, new JLabel("Number of Stacks:"), c, 0, 0, 1, 1);
        addToPanel(toReturn, new JLabel("Max Stack Height:"), c, 0, 1, 1, 1);
        addToPanel(toReturn, new JLabel("Max Weight:"), c, 0, 2, 1, 1);        
                
        return toReturn;
    }

    /*
     * Factory method to create a named JTextField
     */
    private JTextField createTextField(int numColumns, String name) {
        JTextField text = new JTextField();
        text.setColumns(numColumns);
        text.setName(name);
        return text;
    }

    @Override
    protected boolean dialogDone() {
        //Implementation here 
    	//Parameters and building a new manifest, all the while handling exceptions
    	try{
    		int numStacks = Integer.parseInt(txtNumStacks.getText());
    		int maxHeight = Integer.parseInt(txtMaxHeight.getText());
    		int maxWeight = Integer.parseInt(txtMaxWeight.getText());
    		
    		manifest = new CargoManifest(numStacks, maxHeight, maxWeight);    		
    		return true;
    	} catch (NumberFormatException e){
    		JOptionPane.showMessageDialog(this, "NumberFormatException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    		
    	} catch (ManifestException e) {
    		JOptionPane.showMessageDialog(this, "ManifestException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
    	return false;
    }

    /**
     * Shows the <code>ManifestDialog</code> for user interaction.
     *
     * @param parent - The parent <code>JFrame</code> which created this dialog box.
     * @return a <code>CargoManifest</code> instance with valid values.
     */
    public static CargoManifest showDialog(JFrame parent) {
        //Implementation again
    	ManifestDialog dlg = new ManifestDialog(parent);
    	dlg.setVisible(true);
    	return dlg.manifest;
    }
}
