package asgn2GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import asgn2Codes.ContainerCode;
import asgn2Containers.DangerousGoodsContainer;
import asgn2Containers.FreightContainer;
import asgn2Containers.GeneralGoodsContainer;
import asgn2Containers.RefrigeratedContainer;
import asgn2Exceptions.CargoException;
import asgn2Exceptions.InvalidCodeException;
import asgn2Exceptions.InvalidContainerException;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;

/**
 * Creates a dialog box allowing the user to enter information required for loading a container.
 *
 * @author yaman-n8719471
 */
public class LoadContainerDialog extends AbstractDialog implements ActionListener, ItemListener {

    private static final int HEIGHT = 200;
    private static final int WIDTH = 350;

    private JPanel pnlCards;

    private JTextField txtDangerousGoodsType;
    private JTextField txtTemperature;
    private JTextField txtWeight;
    private JTextField txtCode;

    private JComboBox<String> cbType;
    private static String comboBoxItems[] = new String[] { "Dangerous Goods", "General Goods", "Refrigerated Goods" };

    private FreightContainer container;

    /**
     * Constructs a modal dialog box that gathers information required for loading a container.
     *
     * @param parent the frame which created this dialog box.
     */
    private LoadContainerDialog(JFrame parent) {
        super(parent, "Container Information", WIDTH, HEIGHT);
        setResizable(false);
        setName("Container Information");

    }

    /**
     * @see AbstractDialog.createContentPanel()
     */
    @Override
    protected JPanel createContentPanel() {
    	//Left intact as a basis but feel free to modify 
        createCards();

        // add components to grid
        GridBagConstraints constraints = new GridBagConstraints();

        // Defaults
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 100;
        constraints.weighty = 100;

        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new GridBagLayout());
        addToPanel(pnlContent, createCommonControls(), constraints, 0, 0, 2, 1);
        constraints.weighty = 10;
        addToPanel(pnlContent, pnlCards, constraints, 0, 1, 2, 1);

        return pnlContent;
    }

    private JPanel createCommonControls() {
    	//Left intact as a basis but feel free to modify - except for the 
        JPanel pnlCommonControls = new JPanel();
        pnlCommonControls.setLayout(new GridBagLayout());

        // add compents to grid
        GridBagConstraints constraints = new GridBagConstraints();

        // Defaults
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.weightx = 100;
        constraints.weighty = 100;

        //Don't modify - START 
        cbType = new JComboBox<String>(comboBoxItems);
        cbType.setEditable(false);
        cbType.addItemListener(this);
        cbType.setName("Container Type");
      //Don't modify - END 

        txtWeight = createTextField(5, "Container Weight");
        txtCode = createTextField(11, "Container Code");

        addToPanel(pnlCommonControls, new JLabel("Container Type:"), constraints, 0, 0, 2, 1);
        addToPanel(pnlCommonControls, new JLabel("Container Code:"), constraints, 0, 2, 2, 1);
        addToPanel(pnlCommonControls, new JLabel("Container Weight:"), constraints, 0, 4, 2, 1);
        constraints.anchor = GridBagConstraints.WEST;
        addToPanel(pnlCommonControls, cbType, constraints, 3, 0, 2, 1);
        addToPanel(pnlCommonControls, txtCode, constraints, 3, 2, 2, 1);
        addToPanel(pnlCommonControls, txtWeight, constraints, 3, 4, 2, 1);

        return pnlCommonControls;
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

    private void createCards() {
        GridBagConstraints constraints = new GridBagConstraints();

        // Defaults
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.weightx = 45;
        constraints.weighty = 100;

        JPanel cardDangerousGoods = new JPanel();
        cardDangerousGoods.setLayout(new GridBagLayout());
        txtDangerousGoodsType = createTextField(5, "Goods Category");

        JPanel cardRefrigeratedGoods = new JPanel();
        cardRefrigeratedGoods.setLayout(new GridBagLayout());
        txtTemperature = createTextField(5, "Temperature");

        //Finish here 
        addToPanel(cardDangerousGoods, new JLabel("Goods Category:"), constraints, 0, 0, 1, 1);
        addToPanel(cardRefrigeratedGoods, new JLabel("Temperature:"), constraints, 0, 0, 1, 1);
        
        constraints.anchor = GridBagConstraints.WEST;
        addToPanel(cardDangerousGoods, txtDangerousGoodsType, constraints, 1, 0, 1, 1);        
        addToPanel(cardRefrigeratedGoods, txtTemperature, constraints, 1, 0, 1, 1);
        
        pnlCards = new JPanel();
        pnlCards.setLayout(new CardLayout());
        pnlCards.add(cardDangerousGoods, comboBoxItems[0]);
        pnlCards.add(cardRefrigeratedGoods, comboBoxItems[2]);
        pnlCards.add(new JPanel(), comboBoxItems[1]); // empty panel for General Goods
    }

    /**
     * @see java.awt.ItemListener.itemStateChanged(ItemEvent e)
     */
    @Override
    public void itemStateChanged(ItemEvent event) {
        CardLayout cl = (CardLayout) pnlCards.getLayout();
        //Finish here - show cards and set text fields
        cl.show(pnlCards, (String)event.getItem());
    }

    /**
     * @see AbstractDialog.dialogDone()
     */
    @Override
    protected boolean dialogDone() {
        //Implementation here - create the container and set parameters, 
    	//But handle the exceptions properly 
    	try{
    		ContainerCode code = new ContainerCode(txtCode.getText());
    		int weight = Integer.parseInt(txtWeight.getText());
    		if (cbType.getSelectedIndex() == 0){ // Dangerous Goods
    			int category = Integer.parseInt(txtDangerousGoodsType.getText());
    			container = new DangerousGoodsContainer(code, weight, category);
    		}
    		else if (cbType.getSelectedIndex() == 1){ // General Goods
    			container = new GeneralGoodsContainer(code, weight);
    		}
    		else if (cbType.getSelectedIndex() == 2){ // Refrigerated Goods
    			int temperature = Integer.parseInt(txtTemperature.getText());
    			container = new RefrigeratedContainer(code, weight, temperature);
    		}    		
    		    		
    		return true;
    	} catch (NumberFormatException e){
    		JOptionPane.showMessageDialog(this, "NumberFormatException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    		
    	} catch (InvalidCodeException e) {
			JOptionPane.showMessageDialog(this, "InvalidCodeException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (InvalidContainerException e) {
			JOptionPane.showMessageDialog(this, "InvalidContainerException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
    	return false;
    }

    /**
     * Shows a <code>LoadContainerDialog</code> for user interaction.
     *
     * @param parent - The parent <code>JFrame</code> which created this dialog box.
     * @return a <code>FreightContainer</code> instance with valid values.
     */
    public static FreightContainer showDialog(JFrame parent) {
       //Implementation here. 
    	LoadContainerDialog dlg = new LoadContainerDialog(parent);
    	dlg.setVisible(true);
    	return dlg.container;
    }

}
