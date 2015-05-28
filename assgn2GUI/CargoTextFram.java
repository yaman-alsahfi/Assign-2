package asgn2GUI;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import asgn2Codes.ContainerCode;
import asgn2Containers.FreightContainer;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;

/**
 * The main window for the Cargo Manifest graphics application.
 *
 * @author fatimah-n8631000
 */
public class CargoFrame extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private JButton btnLoad;
    private JButton btnUnload;
    private JButton btnFind;
    private JButton btnNewManifest;

    private CargoCanvas canvas;

    private JPanel pnlControls;
    private JPanel pnlDisplay;

    private CargoManifest cargo;

    /**
     * Constructs the GUI.
     *
     * @param title The frame title to use.
     * @throws HeadlessException from JFrame.
     */
    public CargoFrame(String title) throws HeadlessException {
        super(title);

        constructorHelper();
        disableButtons();
        redraw();
        setVisible(true);
    }

    /**
     * Initialises the container display area.
     *
     * @param cargo The <code>CargoManifest</code> instance containing necessary state for display.
     */
    private void setCanvas(CargoManifest cargo) {
        if (canvas != null) {
            pnlDisplay.remove(canvas);
        }
        if (cargo == null) {
            disableButtons();
        } else {
            canvas = new CargoCanvas(cargo);
          //implementation here 
            this.cargo = cargo;
            pnlDisplay.add(canvas, BorderLayout.CENTER);
            enableButtons();
        }
        redraw();
    }

    /**
     * Enables buttons for user interaction.
     */
    private void enableButtons() {
    	//implementation here    
    	btnLoad.setEnabled(true);
    	btnUnload.setEnabled(true);
    	btnFind.setEnabled(true);
    }

    /**
     * Disables buttons from user interaction.
     */
    private void disableButtons() {
    	//implementation here
    	btnLoad.setEnabled(false);
    	btnUnload.setEnabled(false);
    	btnFind.setEnabled(false);
    }

    /**
     * Initialises and lays out GUI components.
     */
    private void constructorHelper() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnLoad = createButton("Load", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable doRun = new Runnable() {
                    @Override
                    public void run() {
                        CargoFrame.this.resetCanvas();
                        CargoFrame.this.doLoad();
                    }
                };
                SwingUtilities.invokeLater(doRun);
            }
        });
        btnUnload = createButton("Unload", new ActionListener() {
        	//implementation here    
        	@Override
			public void actionPerformed(ActionEvent arg0) {
				Runnable doRun = new Runnable() {
                    @Override
                    public void run() {
                        CargoFrame.this.resetCanvas();
                        CargoFrame.this.doUnload();
                    }
                };
                SwingUtilities.invokeLater(doRun);				
			}        
        });
        btnFind = createButton("Find", new ActionListener() {
        	//implementation here 
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		Runnable doRun = new Runnable() {
                    @Override
                    public void run() {
                        CargoFrame.this.resetCanvas();
                        CargoFrame.this.doFind();
                    }
                };
                SwingUtilities.invokeLater(doRun);				
			}          	
        });
        btnNewManifest = createButton("New Manifest", new ActionListener() {
        	//implementation here  
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		Runnable doRun = new Runnable() {
                    @Override
                    public void run() {
                        CargoFrame.this.resetCanvas();
                        CargoFrame.this.setNewManifest();
                    }
                };
                SwingUtilities.invokeLater(doRun);				
			}  
        });

        //implementation here    
        setLayout(new BorderLayout());
        pnlDisplay = new JPanel(new BorderLayout());
        add(pnlDisplay, BorderLayout.CENTER);
        
        pnlControls = createControlPanel();
        add(pnlControls, BorderLayout.SOUTH);
        
        
        repaint();
    }

    /**
     * Creates a JPanel containing user controls (buttons).
     *
     * @return User control panel.
     */
    private JPanel createControlPanel() {
    	//implementation here  
    	JPanel pnl = new JPanel();
    	pnl.add(btnNewManifest);
    	pnl.add(btnLoad);
    	pnl.add(btnUnload);
    	pnl.add(btnFind);
    	return pnl;
    }

    /**
     * Factory method to create a JButton and add its ActionListener.
     *
     * @param name The text to display and use as the component's name.
     * @param btnListener The ActionListener to add.
     * @return A named JButton with ActionListener added.
     */
    private JButton createButton(String name, ActionListener btnListener) {
        JButton btn = new JButton(name);
        btn.setName(name);
        btn.addActionListener(btnListener);
        return btn;
    }

    /**
     * Initiate the New Manifest dialog which sets the instance of CargoManifest to work with.
     */
    private void setNewManifest() {
    	//implementation here   
    	CargoManifest newCargo = ManifestDialog.showDialog(this);
    	if (newCargo != null){
	    	setCanvas(newCargo);
	    	enableButtons();
    	}
    }

    /**
     * Turns off container highlighting when an action other than Find is initiated.
     */
    private void resetCanvas() {
    	//implementation here  
    	if (canvas != null){
    		canvas.setToFind(null);
    		redraw();
    	}
    }

    /**
     * Initiates the Load Container dialog.
     */
    private void doLoad() {
    	//implementation here 
    	//don't forget to redraw
    	FreightContainer container = LoadContainerDialog.showDialog(this);
    	if (container != null){
    		try {
				cargo.loadContainer(container);
				redraw();
			} catch (ManifestException e) {
				JOptionPane.showMessageDialog(this, "ManifestException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
    	}
    }

    private void doUnload() {
    	//implementation here 
    	//don't forget to redraw
    	ContainerCode code = ContainerCodeDialog.showDialog(this);
    	if (code != null){
    		try {
				cargo.unloadContainer(code);
				redraw();
			} catch (ManifestException e) {
				JOptionPane.showMessageDialog(this, "ManifestException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
    	}
    }

    private void doFind() {
    	//implementation here 
    	ContainerCode code = ContainerCodeDialog.showDialog(this);
    	if (code != null){
    		canvas.setToFind(code);
    		redraw();
    	}
    }

    private void redraw() {
        invalidate();
        validate();
        repaint();
    }
}
