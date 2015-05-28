/**
 * Creates a dialog box allowing the user to enter a ContainerCode.
 *
 * @author yaman-n8719471
 */
public class ContainerCodeDialog extends AbstractDialog {

    private final static int WIDTH = 450;
    private final static int HEIGHT = 120;

    private JTextField txtCode;
    private JLabel lblErrorInfo;

    private ContainerCode code;

    /**
     * Constructs a modal dialog box that requests a container code.
     *
     * @param parent the frame which created this dialog box.
     */
    private ContainerCodeDialog(JFrame parent) {
        super(parent, "Container Code", WIDTH, HEIGHT);
        setName("Container Dialog");
        setResizable(true);
    }

    /**
     * @see AbstractDialog.createContentPanel()
     */
    @Override
    protected JPanel createContentPanel() {
        JPanel toReturn = new JPanel();
        toReturn.setLayout(new GridBagLayout());

        // add components to grid
        GridBagConstraints constraints = new GridBagConstraints();

        // Defaults
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.weightx = 100;
        constraints.weighty = 100;

        txtCode = new JTextField();
        txtCode.setColumns(11);
        txtCode.setName("Container Code");
        txtCode.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                validate();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                validate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validate();
            }

            /*
             * Attempts to validate the ContainerCode entered in the Container Code text field.
             */
            private void validate() {
            	//implementation here
            	try {
            		// try to create a ContainerCode
					new ContainerCode(txtCode.getText());
					lblErrorInfo.setText("");
				} catch (InvalidCodeException e) {
					lblErrorInfo.setText(e.getMessage());
				}
            }
        });

        //implementation here
        constraints.anchor = GridBagConstraints.EAST;
        addToPanel(toReturn, new JLabel("Container Code:"), constraints, 0, 0, 1, 1);
        constraints.anchor = GridBagConstraints.WEST;
        addToPanel(toReturn, txtCode, constraints, 1, 0, 1, 1);        
        
        constraints.anchor = GridBagConstraints.WEST;
        lblErrorInfo = new JLabel("");
        addToPanel(toReturn, lblErrorInfo, constraints, 0, 1, 2, 1);

        return toReturn;
    }

    @Override
    protected boolean dialogDone() {
    	//implementation here 
    	try{
    		code = new ContainerCode(txtCode.getText());
    		return true;
    	} catch (NumberFormatException e){
    		JOptionPane.showMessageDialog(this, "NumberFormatException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    		
    	} catch (InvalidCodeException e) {
    		JOptionPane.showMessageDialog(this, "InvalidCodeException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
    	return false;
    }

    /**
     * Shows the <code>ManifestDialog</code> for user interaction.
     *
     * @param parent - The parent <code>JFrame</code> which created this dialog box.
     * @return a <code>ContainerCode</code> instance with valid values.
     */
    public static ContainerCode showDialog(JFrame parent) {
    	//implementation here
    	ContainerCodeDialog dlg = new ContainerCodeDialog(parent);
    	dlg.setVisible(true);
    	return dlg.code;
    }
}
