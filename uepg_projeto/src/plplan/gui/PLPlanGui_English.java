package plplan.gui;

import java.awt.FileDialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import plplan.algorithms.AbstractPlanner;
import plplan.algorithms.Action;
import plplan.algorithms.BWGraphplan;
import plplan.algorithms.FWPlanner;
import plplan.algorithms.Proposition;
import plplan.algorithms.WorldReader;
import plplan.javaapi.StringToByteConvertor;

/**
 * A class that is a GUI for PLPLAN. It allow launching automated tests to compare 
 * the planning algorithms.
 * <p>
 * PLPLAN
 * Authors : Philippe Fournier-Viger and Ludovic lebel
 * <p>
 * This work is licensed under the Creative Commons Attribution 2.5 License. To
 * view a copy of this license, visit
 * http://creativecommons.org/licenses/by/2.5/ or send a letter to Creative
 * Commons, 543 Howard Street, 5th Floor, San Francisco, California, 94105, USA.
 * <p>
 * If you use PLPLAN, we ask you to mention our names and our webpage URL in your work. 
 * The PLPLAN software is copyrighted by Philippe Fournier-Viger and Ludovic Lebel (2005). 
 * Please read carefully the license to know what you can do and cannot do with this software. 
 * You can contact Philippe Fournier-Viger for special permissions. 
 * <p>
 * This sofware is provided "as is", without warranty of any kind. 
 * The user takes the entire risk as to the quality and performance of the software. 
 * The authors accept no responsibility for any problem the user encounters using this software.
 * <p>
 * @author Philippe Fournier-Viger and Ludovic Lebel
 */
public class PLPlanGui_English {

	private JPanel jContentPaneMain = null;

	private JFrame jFrame = null; //  @jve:decl-index=0:visual-constraint="40,11"

	private JPanel jPanelManuel = null;

	private JPanel jPanelAutomatic = null; //  @jve:decl-index=0:visual-constraint="9685,8979"

	private JPanel jPanelOutput = null;

	private JTextField jTextFieldOperator = null;

	private JTextField jTextFieldProblem = null;

	private JTextField jTextFieldInitState = null;

	private JTextField jTextFieldGoal = null;

	private JButton jButtonFileOperator = null;

	private JButton jButtonFileProblem = null;

	private JButton jButtonManualMode = null;

	private JCheckBox jCheckBoxRandomGeneration = null;

	private JTextField jTextFieldNbProb = null;

	private JTextField jTextFieldNbTryByProb = null;

	private JComboBox jComboBoxAlgo = null;

	private JButton jButtonStartAutomaticMode = null;

	private JTextArea jTextAreaOutput = null;

	private JButton jButtonEmpty = null;

	private JButton jButtonSave = null;

	private final static String DEFAULT_OPERATORS = Messages.getString("PLPlanGui_English.0"); //$NON-NLS-1$

	private final static String DEFAULT_PROBLEM = Messages.getString("PLPlanGui_English.1"); //$NON-NLS-1$

	private final static long OUT_OF_MEMORY_CODE = -9999;

	private final static long NO_SOLUTION_CODE = -9999;

	private final static String[] ALGORITHM_LIST = new String[] {
			Messages.getString("PLPlanGui_English.2"), Messages.getString("PLPlanGui_English.3"), //$NON-NLS-1$ //$NON-NLS-2$
			Messages.getString("PLPlanGui_English.4"), Messages.getString("PLPlanGui_English.5"), //$NON-NLS-1$ //$NON-NLS-2$
			Messages.getString("PLPlanGui_English.6"), Messages.getString("PLPlanGui_English.7"), //$NON-NLS-1$ //$NON-NLS-2$
			Messages.getString("PLPlanGui_English.8"), Messages.getString("PLPlanGui_English.9"), //$NON-NLS-1$ //$NON-NLS-2$
			Messages.getString("PLPlanGui_English.10") }; //$NON-NLS-1$

	private String fileSyntaxErrorMsg = Messages.getString("PLPlanGui_English.11"); //$NON-NLS-1$

	private String stringSyntaxErrorMsg = Messages.getString("PLPlanGui_English.12"); //$NON-NLS-1$

	private String readErrorMsg = Messages.getString("PLPlanGui_English.13"); //$NON-NLS-1$

	private JScrollPane jScrollPane = null;

	private JTextField jProblemSizeFirst = null;

	private MessageBox errorMsgBox = null;

	private Random random = new Random(System.currentTimeMillis());

	private JTextField jProblemSizeLast = null;

	private JTextField jTextFieldMaxTime = null;

	private JPanel jPanel = null;

	private JCheckBox jCheckBoxB = null;

	private JCheckBox jCheckBoxF0 = null;

	private JCheckBox jCheckBoxF0SM = null;

	private JCheckBox jCheckBoxF0SS = null;

	private JCheckBox jCheckBoxF0SMSS = null;

	private JCheckBox jCheckBoxF1 = null;

	private JCheckBox jCheckBoxF1SM = null;

	private JCheckBox jCheckBoxF1SS = null;

	private JCheckBox jCheckBoxF1SMSS = null;
    
    private StringToByteConvertor stringByteConvertor = null;
    
    private Set<Action> currentOps = null;
    private Set<Proposition> currentFacts = null;
    private List<Proposition> currentGoal = null;
    
    public StringToByteConvertor getStringToByteConvertor(){
        if(stringByteConvertor == null){
            stringByteConvertor =  new StringToByteConvertor();
        }
        return stringByteConvertor;
    }

	//	private static final Runtime s_runtime = Runtime.getRuntime ();

	public PLPlanGui_English() {
		errorMsgBox = new MessageBox();
		errorMsgBox.setTitle(Messages.getString("PLPlanGui_English.14")); //$NON-NLS-1$
		errorMsgBox.addChoice(Messages.getString("PLPlanGui_English.15"), Messages.getString("PLPlanGui_English.16")); //$NON-NLS-1$ //$NON-NLS-2$
		errorMsgBox.setCloseWindowCommand(Messages.getString("PLPlanGui_English.17")); //$NON-NLS-1$
	}

	/**
	 * This method initializes jContentPaneMain
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPaneMain() {
		if (jContentPaneMain == null) {
			java.awt.GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			jContentPaneMain = new JPanel();
			jContentPaneMain.setLayout(new GridBagLayout());
			gridBagConstraints29.gridx = 0;
			gridBagConstraints29.gridy = 0;
			gridBagConstraints29.ipadx = 50;
			gridBagConstraints29.ipady = 50;
			gridBagConstraints29.insets = new java.awt.Insets(0, 0, 0, 0);
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.ipadx = 50;
			gridBagConstraints4.ipady = 50;
			gridBagConstraints30.gridx = 0;
			gridBagConstraints30.gridy = 1;
			gridBagConstraints30.ipadx = 50;
			gridBagConstraints30.ipady = 50;
			gridBagConstraints30.anchor = java.awt.GridBagConstraints.CENTER;
			gridBagConstraints30.insets = new java.awt.Insets(0, 0, 0, 0);
			jContentPaneMain.add(getJPanelOutput(), gridBagConstraints4);
			jContentPaneMain.setPreferredSize(new java.awt.Dimension(500, 600));
			gridBagConstraints4.gridwidth = 3;
			jContentPaneMain.add(getJPanelManualMode(), gridBagConstraints29);
			jContentPaneMain.add(getJPanelAutomaticMode(), gridBagConstraints30);

			//gridBagConstraints4.insets = new java.awt.Insets(0,0,1,0);
		}
		return jContentPaneMain;
	}

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setContentPane(getJContentPaneMain());
			jFrame.setSize(540, 600);
			jFrame.setResizable(false);
			jFrame.setTitle(Messages.getString("PLPlanGui_English.18")); //$NON-NLS-1$
			jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				}
			});
		}
		return jFrame;
	}

	/**
	 * This method initializes jPanelManuel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelManualMode() {
		if (jPanelManuel == null) {
			java.awt.GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
			javax.swing.JLabel jLabel8 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			javax.swing.JLabel jLabel4 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			javax.swing.JLabel jLabel3 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			javax.swing.JLabel jLabel2 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			javax.swing.JLabel jLabel1 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			javax.swing.JLabel jLabel = new JLabel();
			jPanelManuel = new JPanel();
			jPanelManuel.setLayout(new GridBagLayout());
			//jPanelManuel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.gridy = 0;
			gridBagConstraints38.gridx = 1;
			gridBagConstraints38.gridy = 5;
			gridBagConstraints38.anchor = GridBagConstraints.EAST;
			jLabel8.setText(Messages.getString("PLPlanGui_English.19")); //$NON-NLS-1$
			gridBagConstraints39.gridx = 2;
			gridBagConstraints39.gridy = 5;
			gridBagConstraints39.weightx = 1.0;
			gridBagConstraints39.fill = java.awt.GridBagConstraints.HORIZONTAL;
			jPanelManuel.add(jLabel8, gridBagConstraints38);
			jPanelManuel.add(getJComboBoxAlgorithm(), gridBagConstraints39);
			jPanelManuel.setPreferredSize(new java.awt.Dimension(450, 100));
			jPanelManuel.setBorder(javax.swing.BorderFactory.createLineBorder(
					java.awt.Color.black, 1));
			jLabel.setText(Messages.getString("PLPlanGui_English.20")); //$NON-NLS-1$
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setFont(new java.awt.Font(Messages.getString("PLPlanGui_English.21"), java.awt.Font.BOLD, 14)); //$NON-NLS-1$
			gridBagConstraints18.gridx = 1;
			gridBagConstraints18.gridy = 1;
			gridBagConstraints18.anchor = GridBagConstraints.EAST;
			jLabel1.setText(Messages.getString("PLPlanGui_English.22")); //$NON-NLS-1$
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			jLabel1
					.setHorizontalTextPosition(javax.swing.SwingConstants.TRAILING);
			gridBagConstraints19.gridx = 1;
			gridBagConstraints19.gridy = 2;
			gridBagConstraints19.anchor = GridBagConstraints.EAST;
			jLabel2.setText(Messages.getString("PLPlanGui_English.23")); //$NON-NLS-1$
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			gridBagConstraints20.gridx = 1;
			gridBagConstraints20.gridy = 3;
			gridBagConstraints20.anchor = GridBagConstraints.EAST;
			jLabel3.setText(Messages.getString("PLPlanGui_English.24")); //$NON-NLS-1$
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.gridy = 4;
			gridBagConstraints21.anchor = GridBagConstraints.EAST;
			jLabel4.setText(Messages.getString("PLPlanGui_English.25")); //$NON-NLS-1$
			gridBagConstraints22.gridx = 2;
			gridBagConstraints22.gridy = 1;
			gridBagConstraints22.weightx = 1.0;
			gridBagConstraints22.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints23.gridx = 2;
			gridBagConstraints23.gridy = 2;
			gridBagConstraints23.weightx = 1.0;
			gridBagConstraints23.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints24.gridx = 2;
			gridBagConstraints24.gridy = 3;
			gridBagConstraints24.weightx = 1.0;
			gridBagConstraints24.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints25.gridx = 2;
			gridBagConstraints25.gridy = 4;
			gridBagConstraints25.weightx = 1.0;
			gridBagConstraints25.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints26.gridx = 3;
			gridBagConstraints26.gridy = 1;
			gridBagConstraints27.gridx = 3;
			gridBagConstraints27.gridy = 2;
			gridBagConstraints28.gridx = 3;
			gridBagConstraints28.gridy = 5;
			jPanelManuel.add(getJButtonStartManualMode(), gridBagConstraints28);
			jPanelManuel.add(jLabel, gridBagConstraints17);
			jPanelManuel.add(jLabel1, gridBagConstraints18);
			jPanelManuel.add(jLabel2, gridBagConstraints19);
			jPanelManuel.add(jLabel3, gridBagConstraints20);
			jPanelManuel.add(jLabel4, gridBagConstraints21);
			jPanelManuel.add(getJTextFieldOperator(), gridBagConstraints22);
			jPanelManuel.add(getJTextFieldProblem(), gridBagConstraints23);
			jPanelManuel.add(getJTextFieldInitState(), gridBagConstraints24);
			jPanelManuel.add(getJTextFieldGoal(), gridBagConstraints25);
			jPanelManuel.add(getJButtonOperatorsFile(), gridBagConstraints26);
			jPanelManuel.add(getJButtonProblemFile(), gridBagConstraints27);
		}
		return jPanelManuel;
	}

	/**
	 * This method initializes jPanelAutomatic
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelAutomaticMode() {
		if (jPanelAutomatic == null) {
			java.awt.GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints212 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			javax.swing.JLabel jLabel12 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints211 = new GridBagConstraints();
			javax.swing.JLabel jLabel11 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints210 = new GridBagConstraints();
			javax.swing.JLabel jLabel10 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			javax.swing.JLabel jLabel7 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			javax.swing.JLabel jLabel6 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			javax.swing.JLabel jLabel5 = new JLabel();
			jPanelAutomatic = new JPanel();
			jPanelAutomatic.setLayout(new GridBagLayout());
			//jPanelAutomatic.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			gridBagConstraints30.gridx = 0;
			gridBagConstraints30.gridy = 0;
			jLabel5.setText(Messages.getString("PLPlanGui_English.26")); //$NON-NLS-1$
			jLabel5.setForeground(java.awt.Color.blue);
			jLabel5
					.setFont(new java.awt.Font(Messages.getString("PLPlanGui_English.27"), java.awt.Font.BOLD, 14)); //$NON-NLS-1$
			gridBagConstraints32.gridx = 1;
			gridBagConstraints32.gridy = 1;
			gridBagConstraints33.gridx = 1;
			gridBagConstraints33.gridy = 2;
			gridBagConstraints33.anchor = GridBagConstraints.EAST;
			jLabel6.setText(Messages.getString("PLPlanGui_English.28")); //$NON-NLS-1$
			gridBagConstraints34.gridx = 3;
			gridBagConstraints34.gridy = 2;
			gridBagConstraints34.weightx = 1.0;
			gridBagConstraints34.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints34.gridwidth = 3;
			gridBagConstraints36.gridx = 1;
			gridBagConstraints36.gridy = 4;
			gridBagConstraints36.anchor = GridBagConstraints.EAST;
			jLabel7.setText(Messages.getString("PLPlanGui_English.29")); //$NON-NLS-1$
			gridBagConstraints37.gridx = 3;
			gridBagConstraints37.gridy = 4;
			gridBagConstraints37.weightx = 1.0;
			gridBagConstraints37.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints37.gridwidth = 3;
			gridBagConstraints40.gridx = 6;
			gridBagConstraints40.gridy = 5;
			jPanelAutomatic
					.setPreferredSize(new java.awt.Dimension(450, 180));
			jPanelAutomatic.setBorder(javax.swing.BorderFactory
					.createLineBorder(java.awt.Color.black, 1));
			gridBagConstraints210.gridx = 1;
			gridBagConstraints210.gridy = 3;
			jLabel10.setText(Messages.getString("PLPlanGui_English.30")); //$NON-NLS-1$
			gridBagConstraints3.gridx = 3;
			gridBagConstraints3.gridy = 3;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridx = 5;
			gridBagConstraints1.gridy = 3;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints211.gridx = 4;
			gridBagConstraints211.gridy = 3;
			jLabel11.setText(Messages.getString("PLPlanGui_English.31")); //$NON-NLS-1$
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 5;
			jLabel12.setText(Messages.getString("PLPlanGui_English.32")); //$NON-NLS-1$
			gridBagConstraints212.gridx = 3;
			gridBagConstraints212.gridy = 5;
			gridBagConstraints212.weightx = 1.0;
			gridBagConstraints212.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints212.gridwidth = 3;
			gridBagConstraints31.gridx = 0;
			gridBagConstraints31.gridy = 6;
			gridBagConstraints31.gridwidth = 7;
			jPanelAutomatic.add(jLabel5, gridBagConstraints30);
			jPanelAutomatic.add(getJCheckBoxRandomGeneration(),
					gridBagConstraints32);
			jPanelAutomatic.add(jLabel6, gridBagConstraints33);
			jPanelAutomatic.add(getJTextFieldNumberOfProblems(), gridBagConstraints34);
			jPanelAutomatic.add(jLabel7, gridBagConstraints36);
			jPanelAutomatic.add(getJTextFieldNbTryByProb(),
					gridBagConstraints37);
			jPanelAutomatic.add(getJButtonStartAutomaticMode(),
					gridBagConstraints40);
			jPanelAutomatic.add(jLabel10, gridBagConstraints210);
			jPanelAutomatic.add(getJProblemSizeFirst(), gridBagConstraints3);
			jPanelAutomatic.add(getJProblemSizeLast(), gridBagConstraints1);
			jPanelAutomatic.add(jLabel11, gridBagConstraints211);
			jPanelAutomatic.add(jLabel12, gridBagConstraints11);
			jPanelAutomatic.add(getJTextFieldTempsMaximum(),
					gridBagConstraints212);
			jPanelAutomatic.add(getJPanel(), gridBagConstraints31);
		}
		return jPanelAutomatic;
	}

	/**
	 * This method initializes jPanelOutput
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelOutput() {
		if (jPanelOutput == null) {
			java.awt.GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
			javax.swing.JLabel jLabel9 = new JLabel();
			jPanelOutput = new JPanel();
			jPanelOutput.setLayout(new GridBagLayout());
			//jPanelOutput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			gridBagConstraints43.gridx = 0;
			gridBagConstraints43.gridy = 0;
			jLabel9.setText(Messages.getString("PLPlanGui_English.33")); //$NON-NLS-1$
			jLabel9.setForeground(java.awt.Color.blue);
			jLabel9
					.setFont(new java.awt.Font(Messages.getString("PLPlanGui_English.34"), java.awt.Font.BOLD, 14)); //$NON-NLS-1$
			gridBagConstraints45.gridx = 2;
			gridBagConstraints45.gridy = 1;
			gridBagConstraints45.gridwidth = 2;
			gridBagConstraints46.gridx = 2;
			gridBagConstraints46.gridy = 2;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.weighty = 1.0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints2.gridheight = 4;
			jPanelOutput.setPreferredSize(new java.awt.Dimension(450, 100));
			jPanelOutput.setBorder(javax.swing.BorderFactory.createLineBorder(
					java.awt.Color.black, 1));
			jPanelOutput.add(jLabel9, gridBagConstraints43);
			jPanelOutput.add(getJButtonEmpty(), gridBagConstraints45);
			jPanelOutput.add(getJButtonSave(), gridBagConstraints46);
			jPanelOutput.add(getJScrollPane(), gridBagConstraints2);
		}
		return jPanelOutput;
	}

	/**
	 * This method initializes jTextFieldOperateur
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldOperator() {
		if (jTextFieldOperator == null) {
			jTextFieldOperator = new JTextField();
			jTextFieldOperator.setEditable(false);
			jTextFieldOperator.setText(DEFAULT_OPERATORS);
			try {
                WorldReader worldReader = 
                new WorldReader(DEFAULT_OPERATORS, getStringToByteConvertor());
                worldReader.parseOpsFile(DEFAULT_OPERATORS);
                currentOps  = worldReader.getOpSet();
			} catch (StreamCorruptedException e1) {
				errorMsgBox.ask(fileSyntaxErrorMsg);
			} catch (IOException e1) {
				errorMsgBox.ask(readErrorMsg);
			}
		}
		return jTextFieldOperator;
	}

	/**
	 * This method initializes jTextFieldProbleme
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldProblem() {
		if (jTextFieldProblem == null) {
			jTextFieldProblem = new JTextField();
			jTextFieldProblem.setEditable(false);
			jTextFieldProblem.setText(DEFAULT_PROBLEM);
			try {
                WorldReader worldReader = new WorldReader(DEFAULT_PROBLEM, getStringToByteConvertor());
                worldReader.parseProbFile(DEFAULT_PROBLEM);
				getJTextFieldInitState().setText(worldReader.getFactStr());
				getJTextFieldGoal().setText(worldReader.getGoalStr());
                currentFacts = worldReader.getFactSet();
                currentGoal = worldReader.getGoalList();
			} catch (StreamCorruptedException e1) {
				errorMsgBox.ask(fileSyntaxErrorMsg);
			} catch (IOException e1) {
				errorMsgBox.ask(readErrorMsg);
			}
		}
		return jTextFieldProblem;
	}

	/**
	 * This method initializes jTextFieldEtatInitial
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldInitState() {
		if (jTextFieldInitState == null) {
			jTextFieldInitState = new JTextField();
			jTextFieldInitState.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() != '\n')
						jTextFieldInitState.setForeground(java.awt.Color.red);
				}
			});
			jTextFieldInitState
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
                                WorldReader worldReader = new WorldReader(jTextFieldInitState
                                        .getText(), getStringToByteConvertor());
                                worldReader.parseFacts(jTextFieldInitState
                                        .getText());
                                currentFacts = worldReader.getFactSet();
								jTextFieldInitState
										.setForeground(java.awt.Color.black);
							} catch (StreamCorruptedException e1) {
								errorMsgBox.ask(stringSyntaxErrorMsg);
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					});

		}
		return jTextFieldInitState;
	}

	/**
	 * This method initializes jTextFieldBut
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldGoal() {
		if (jTextFieldGoal == null) {
			jTextFieldGoal = new JTextField();
			jTextFieldGoal.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() != '\n')
						jTextFieldGoal.setForeground(java.awt.Color.red);
				}
			});
			jTextFieldGoal
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
                                WorldReader worldReader = new WorldReader(jTextFieldGoal.getText(), getStringToByteConvertor());
                                worldReader.parseGoal(jTextFieldGoal.getText());
                                currentGoal = worldReader.getGoalList();
								jTextFieldGoal
										.setForeground(java.awt.Color.black);
							} catch (StreamCorruptedException e1) {
								errorMsgBox.ask(stringSyntaxErrorMsg);
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					});
		}
		return jTextFieldGoal;
	}

	/**
	 * This method initializes jButtonFileOperator
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonOperatorsFile() {
		if (jButtonFileOperator == null) {
			jButtonFileOperator = new JButton();
			jButtonFileOperator.setText(Messages.getString("PLPlanGui_English.35")); //$NON-NLS-1$
			jButtonFileOperator
					.setPreferredSize(new java.awt.Dimension(60, 26));
			jButtonFileOperator
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							chooseFile(getJTextFieldOperator());
							try {
                                WorldReader worldReader = new WorldReader(getOperatorFileName(), getStringToByteConvertor());
								worldReader.parseOpsFile(getOperatorFileName());
                                currentOps = worldReader.getOpSet();
							} catch (StreamCorruptedException e1) {
								errorMsgBox.ask(fileSyntaxErrorMsg);
							} catch (IOException e1) {
								errorMsgBox.ask(readErrorMsg);
							}
						}
					});
		}
		return jButtonFileOperator;
	}

	/**
	 * This method initializes jButtonFileProblem
	 * 
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonProblemFile() {
		if (jButtonFileProblem == null) {
			jButtonFileProblem = new JButton();
			jButtonFileProblem.setText(Messages.getString("PLPlanGui_English.36")); //$NON-NLS-1$
			jButtonFileProblem
					.setPreferredSize(new java.awt.Dimension(60, 26));
			jButtonFileProblem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							chooseFile(getJTextFieldProblem());
							try {
                                WorldReader worldReader = new WorldReader(getProblemFileName(), getStringToByteConvertor());
                                worldReader.parseProbFile(getProblemFileName());
								getJTextFieldInitState().setText(
										worldReader.getFactStr());
								getJTextFieldGoal().setText(
										worldReader.getGoalStr());
                                currentFacts = worldReader.getFactSet();
                                currentGoal = worldReader.getGoalList();
							} catch (StreamCorruptedException e1) {
								errorMsgBox.ask(fileSyntaxErrorMsg);
							} catch (IOException e1) {
								errorMsgBox.ask(readErrorMsg);
							}
						}
					});
		}
		return jButtonFileProblem;
	}

	/**
	 * This method initializes jButtonManualMode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonStartManualMode() {
		if (jButtonManualMode == null) {
			jButtonManualMode = new JButton();
			jButtonManualMode.setPreferredSize(new java.awt.Dimension(60,
					26));
			jButtonManualMode.setText(Messages.getString("PLPlanGui_English.37")); //$NON-NLS-1$
			jButtonManualMode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							startManualMode();
						}
					});
		}
		return jButtonManualMode;
	}

	/**
	 * This method initializes jCheckBoxRandomGeneration
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxRandomGeneration() {
		if (jCheckBoxRandomGeneration == null) {
			jCheckBoxRandomGeneration = new JCheckBox();
			jCheckBoxRandomGeneration.setText(Messages.getString("PLPlanGui_English.38")); //$NON-NLS-1$
			jCheckBoxRandomGeneration
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (!jCheckBoxRandomGeneration.isSelected()) {
								getJTextFieldNumberOfProblems().setEditable(false);
								getJProblemSizeFirst().setEditable(false);
								getJProblemSizeLast().setEditable(false);
							} else {
								getJTextFieldNumberOfProblems().setEditable(true);
								getJProblemSizeFirst().setEditable(true);
								getJProblemSizeLast().setEditable(true);
							}
						}
					});
		}
		return jCheckBoxRandomGeneration;
	}

	/**
	 * This method initializes jTextFieldNbProb
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldNumberOfProblems() {
		if (jTextFieldNbProb == null) {
			jTextFieldNbProb = new JTextField();
			jTextFieldNbProb.setEditable(false);
		}
		return jTextFieldNbProb;
	}

	/**
	 * This method initializes jTextFieldNbFoisParProb
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldNbTryByProb() {
		if (jTextFieldNbTryByProb == null) {
			jTextFieldNbTryByProb = new JTextField();
			jTextFieldNbTryByProb.setText(Messages.getString("PLPlanGui_English.39")); //$NON-NLS-1$
		}
		return jTextFieldNbTryByProb;
	}

	/**
	 * This method initializes jComboBoxAlgo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxAlgorithm() {
		if (jComboBoxAlgo == null) {
			jComboBoxAlgo = new JComboBox(ALGORITHM_LIST);
			jComboBoxAlgo.setSelectedIndex(0);
		}
		return jComboBoxAlgo;
	}

	/**
	 * This method initializes jButtonStartAutomaticMode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonStartAutomaticMode() {
		if (jButtonStartAutomaticMode == null) {
			jButtonStartAutomaticMode = new JButton();
			jButtonStartAutomaticMode.setText(Messages.getString("PLPlanGui_English.40")); //$NON-NLS-1$
			jButtonStartAutomaticMode.setPreferredSize(new java.awt.Dimension(
					60, 26));
			jButtonStartAutomaticMode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							int first = getProblemComplexityFirst();
							int last = getProblemComplexityLast();
							for (int i = first; i <= last; i++) {
								startAutomaticMode(i);
							}
						}
					});
		}
		return jButtonStartAutomaticMode;
	}

	/**
	 * This method initializes jTextAreaSortie
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextAreaOutput() {
		if (jTextAreaOutput == null) {
			jTextAreaOutput = new JTextArea();
			jTextAreaOutput.setAutoscrolls(true);
			jTextAreaOutput.setEditable(false);
		}
		return jTextAreaOutput;
	}

	/**
	 * This method initializes jButtonEmpty
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonEmpty() {
		if (jButtonEmpty == null) {
			jButtonEmpty = new JButton();
			jButtonEmpty.setText(Messages.getString("PLPlanGui_English.41")); //$NON-NLS-1$
			jButtonEmpty.setPreferredSize(new java.awt.Dimension(110, 26));
			jButtonEmpty.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJTextAreaOutput().setText(Messages.getString("PLPlanGui_English.42")); //$NON-NLS-1$
				}
			});
		}
		return jButtonEmpty;
	}

	/**
	 * This method initializes jButtonSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonSave() {
		if (jButtonSave == null) {
			jButtonSave = new JButton();
			jButtonSave.setText(Messages.getString("PLPlanGui_English.43")); //$NON-NLS-1$
			jButtonSave
					.setPreferredSize(new java.awt.Dimension(110, 26));
			jButtonSave
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							saveOutputToFile();
						}
					});
		}
		return jButtonSave;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTextAreaOutput());
		}
		return jScrollPane;
	}

	//***********************************************************************//

	public boolean isRandomGenerationActivated() {
		return getJCheckBoxRandomGeneration().isSelected();
	}

	public int getNumberOfProblems() {
		return Integer.parseInt(getJTextFieldNumberOfProblems().getText());
	}

	public int getProblemComplexityFirst() {
		return Integer.parseInt(getJProblemSizeFirst().getText());
	}

	public int getProblemComplexityLast() {
		return Integer.parseInt(getJProblemSizeLast().getText());
	}

	public int getNbTryByProblem() {
		return Integer.parseInt(getJTextFieldNbTryByProb().getText());
	}

	public String getOperatorFileName() {
		return getJTextFieldOperator().getText();
	}

	public String getProblemFileName() {
		return getJTextFieldProblem().getText();
	}

	public String getInitialState() {
		return getJTextFieldInitState().getText();
	}

	public String getGoal() {
		return getJTextFieldGoal().getText();
	}

	public int getSelectedAlgorithmIndex() {
		return getJComboBoxAlgorithm().getSelectedIndex();
	}

	public int getAlgorithmCount() {
		return ALGORITHM_LIST.length;
	}

	public AbstractPlanner getGraphplanAt(Set<Action> ops, Set<Proposition> facts, List<Proposition> goal,
			int index) {
		switch (index) {
		case 0: // Graphplan (arrire)
			return new BWGraphplan(ops, facts, goal);
		case 1: // Avant - Algo 0
			FWPlanner fg = new FWPlanner(ops, facts, goal);
			fg.setAlgorithmePersistentSet(0);
			fg.setWasSeenEnabled(false);
			fg.setSleepSetEnabled(false);
			return fg;
		case 2: //Avant - Algo 0 - state matching
			FWPlanner fg2 = new FWPlanner(ops, facts, goal);
			fg2.setAlgorithmePersistentSet(0);
			fg2.setWasSeenEnabled(true);
			fg2.setSleepSetEnabled(false);
			return fg2;
		case 3: // Avant - Algo 0 - sleep set
			FWPlanner fg3 = new FWPlanner(ops, facts, goal);
			fg3.setAlgorithmePersistentSet(0);
			fg3.setWasSeenEnabled(false);
			fg3.setSleepSetEnabled(true);
			return fg3;
		case 4: // Avant - Algo 0 - sleep set - state matching
			FWPlanner fg4 = new FWPlanner(ops, facts, goal);
			fg4.setAlgorithmePersistentSet(0);
			fg4.setWasSeenEnabled(true);
			fg4.setSleepSetEnabled(true);
			return fg4;
		case 5: // Avant - Algo 1
			FWPlanner fg5 = new FWPlanner(ops, facts, goal);
			fg5.setAlgorithmePersistentSet(1);
			fg5.setWasSeenEnabled(false);
			fg5.setSleepSetEnabled(false);
			return fg5;
		case 6: // Avant - Algo 1 - state matching
			FWPlanner fg6 = new FWPlanner(ops, facts, goal);
			fg6.setAlgorithmePersistentSet(1);
			fg6.setWasSeenEnabled(true);
			fg6.setSleepSetEnabled(false);
			return fg6;
		case 7: // Avant - Algo 1 - sleep set
			FWPlanner fg7 = new FWPlanner(ops, facts, goal);
			fg7.setAlgorithmePersistentSet(1);
			fg7.setWasSeenEnabled(false);
			fg7.setSleepSetEnabled(true);
			return fg7;
		case 8: // Avant - Algo 1 - sleep set - state matching
			FWPlanner fg8 = new FWPlanner(ops, facts, goal);
			fg8.setAlgorithmePersistentSet(1);
			fg8.setWasSeenEnabled(true);
			fg8.setSleepSetEnabled(true);
			return fg8;
		}
		return null;
	}

	public AbstractPlanner getChoosenGraphPlan(Set<Action> ops, 
            Set<Proposition> facts, List<Proposition> goal) {
		return getGraphplanAt(ops, facts, goal, getSelectedAlgorithmIndex());
	}

	public String getSelectedAlgorithmName() {
		return getAlgorithmNameAt(getSelectedAlgorithmIndex());
	}

	public String getAlgorithmNameAt(int index) {
		return ALGORITHM_LIST[index];
	}

	// Write text into "output" field
	public void output(String string) {
		if (Messages.getString("PLPlanGui_English.44").equals(getJTextAreaOutput().getText())) { //$NON-NLS-1$
			getJTextAreaOutput().setText(string);
		} else {
			getJTextAreaOutput().setText(
					getJTextAreaOutput().getText() + Messages.getString("PLPlanGui_English.45") + string); //$NON-NLS-1$
		}
	}

	public String getOutput() {
		return getJTextAreaOutput().getText();
	}

	public void saveOutputToFile() {
		FileDialog fd = new FileDialog(getJFrame(), Messages.getString("PLPlanGui_English.46"), //$NON-NLS-1$
				FileDialog.SAVE);
		fd.setLocation(50, 50);
		fd.setVisible(true);
		String path = fd.getDirectory()
				+ System.getProperty(Messages.getString("PLPlanGui_English.47")).charAt(0) + fd.getFile(); //$NON-NLS-1$
		try {
			FileOutputStream fos = new FileOutputStream(path);
			Writer w = new BufferedWriter(new OutputStreamWriter(fos));
			w.write(getOutput());
			w.flush();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getTempsMaxParAlgo() {
		return Integer.parseInt(getJTextFieldTempsMaximum().getText());
	}

	public void chooseFile(JTextField field) {
		FileDialog fd = new FileDialog(getJFrame(), Messages.getString("PLPlanGui_English.48"), //$NON-NLS-1$
				FileDialog.LOAD);
		fd.setLocation(50, 50);
		fd.setVisible(true);
		field
				.setText(fd.getDirectory()
						+ System.getProperty(Messages.getString("PLPlanGui_English.49")).charAt(0) //$NON-NLS-1$
						+ fd.getFile());
	}

	public void startManualMode() {
		output(Messages.getString("PLPlanGui_English.50")); //$NON-NLS-1$

		Set<Action> ops = currentOps;
		Set<Proposition> facts = currentFacts;
		List<Proposition> goal = currentGoal;

		output(Messages.getString("PLPlanGui_English.51") + getOperatorFileName()); //$NON-NLS-1$

		AbstractPlanner graphplan = getChoosenGraphPlan(ops, facts, goal);
        

		try {
			graphplan.run();

		} catch (OutOfMemoryError e) {
			output(Messages.getString("PLPlanGui_English.52")); //$NON-NLS-1$
		}

		output(graphplan.getPlanningOutput());
		output(Messages.getString("PLPlanGui_English.53")); //$NON-NLS-1$
	}

	public void startAutomaticMode(int nbActions) {

		output(Messages.getString("PLPlanGui_English.54")); //$NON-NLS-1$

		int nbFois = getNbTryByProblem();

		int nbProbleme = isRandomGenerationActivated() ? getNumberOfProblems() : 1;

		Set<Action> ops = currentOps;
		Set<Proposition> facts = currentFacts;
		List<Proposition> goal = currentGoal;

		output(Messages.getString("PLPlanGui_English.55") + getOperatorFileName()); //$NON-NLS-1$
		output(Messages.getString("PLPlanGui_English.56") + nbFois); //$NON-NLS-1$
		output(Messages.getString("PLPlanGui_English.57") + nbProbleme); //$NON-NLS-1$
		output(Messages.getString("PLPlanGui_English.58") + facts); //$NON-NLS-1$

		if (isRandomGenerationActivated()) {
			output(Messages.getString("PLPlanGui_English.59")); //$NON-NLS-1$
			output(Messages.getString("PLPlanGui_English.60") + nbActions); //$NON-NLS-1$
		}

		double[][] maxTimeForEachSearch = new double[getAlgorithmCount()][nbProbleme];
		double[][] solutionSize = new double[getAlgorithmCount()][nbProbleme];
		int nbTimesUp[] = new int[getAlgorithmCount()];
		int nbOutOfMemory[] = new int[getAlgorithmCount()];
		List<List<Proposition>> goalList = new ArrayList<List<Proposition>>(nbFois);

		try {
			for (int i = 0; i < nbProbleme; i++) {
				System.out.println(Messages.getString("PLPlanGui_English.61") + i); //$NON-NLS-1$
				output(Messages.getString("PLPlanGui_English.62") + i); //$NON-NLS-1$
				output(Messages.getString("PLPlanGui_English.63")); //$NON-NLS-1$

				// Generate problem if random generation is activated
				if (isRandomGenerationActivated()) {
					goalList = generateRandomGoal(ops, facts, nbActions, nbFois);
					for (int itrFois = 0; itrFois < nbFois; itrFois++) {
						output(Messages.getString("PLPlanGui_English.64") + itrFois + Messages.getString("PLPlanGui_English.65") + goalList.get(itrFois)); //$NON-NLS-1$ //$NON-NLS-2$
					}
				}

				// For each algorithm
				System.out.print(Messages.getString("PLPlanGui_English.66")); //$NON-NLS-1$
				for (int k = 0; k < getAlgorithmCount(); k++) {

					if (!isAlgorithmEnabled(k))
						continue;

					System.out.print(Messages.getString("PLPlanGui_English.67") + k); //$NON-NLS-1$

					// We do it N times
					double[] timeForTrial = new double[nbFois];
					double[] solutionSizeForTrial = new double[nbFois];
					boolean error = false;
					boolean timesup = false;
					for (int j = 0; j < nbFois; j++) {
						
						List solution = null;

						AbstractPlanner planner = getGraphplanAt(ops,
								facts, goalList.get(j), k);
                        planner.msgStartPlanning();

						//graphplan.setTimeLimit(getTempsMaxParAlgo());
						Runtime.getRuntime().gc();

						long startTime = System.currentTimeMillis();
						timesup = false;
						error = false;
						
						try {
							// RUN THREAD
							GraphplanThread runnable = new GraphplanThread(planner);
							Thread graphThread = new Thread(runnable);
							graphThread.start();

							long maxtime = getTempsMaxParAlgo();
							// on increment par coup de 1/8 du maxtime,
							/*for (int m = 0; m < 10; m++) {
								try {
									Thread.sleep(increment);
								} catch (InterruptedException e) {
									System.out.println("EXCEPTION " + e);
								}
								if (runnable.getSolution() != null)
									break;
							}*/
							while(System.currentTimeMillis() - startTime < maxtime && runnable.getSolution() == null){
								try {
									Thread.sleep(2);
								} catch (InterruptedException e) {
									System.out.println(Messages.getString("PLPlanGui_English.68") + e); //$NON-NLS-1$
								}
							}
							solution = runnable.getSolution();
							if (solution == null) {
								System.out.println(Messages.getString("PLPlanGui_English.69")); //$NON-NLS-1$
								timesup = true;
								graphThread.stop();
							}
						} catch (Error e) {
							error = true;
							solution = null;
						}
                        planner.msgEndPlanning(solution);
                        output(planner.getPlanningOutput());

						if (solution != null && !error && !timesup) {
							timeForTrial[j] = System.currentTimeMillis()
									- startTime;
							solutionSizeForTrial[j] = solutionSize(solution);
							System.out.print(Messages.getString("PLPlanGui_English.70")); //$NON-NLS-1$
						} else if (timesup) {
								solutionSizeForTrial[j] = NO_SOLUTION_CODE;
								nbTimesUp[k]++;
								System.out.print(Messages.getString("PLPlanGui_English.71")); //$NON-NLS-1$
						} 
						else { // Out of memory
								solutionSizeForTrial[j] = NO_SOLUTION_CODE;
								nbOutOfMemory[k]++;
								System.out.print(Messages.getString("PLPlanGui_English.72")); //$NON-NLS-1$
						}

					}
					output(Messages.getString("PLPlanGui_English.73") + getAlgorithmNameAt(k)); //$NON-NLS-1$
					if (error) {
						output(Messages.getString("PLPlanGui_English.74")); //$NON-NLS-1$
						maxTimeForEachSearch[k][i] = mean(timeForTrial);
					} else {
						maxTimeForEachSearch[k][i] = mean(timeForTrial);
						output(Messages.getString("PLPlanGui_English.75") //$NON-NLS-1$
								+ maxTimeForEachSearch[k][i] + Messages.getString("PLPlanGui_English.76")); //$NON-NLS-1$
						solutionSize[k][i] = mean(solutionSizeForTrial);
						output(Messages.getString("PLPlanGui_English.77") + solutionSize[k][i]); //$NON-NLS-1$
					}
			
				}

			} // End of main for loop
		} catch (Exception e) {
			e.printStackTrace();
		}

		output(Messages.getString("PLPlanGui_English.78")); //$NON-NLS-1$
		output(Messages.getString("PLPlanGui_English.79")); //$NON-NLS-1$
        
		// Calculate average for each algorithms and display results
		for (int i = 0; i < getAlgorithmCount(); i++) {
			output(Messages.getString("PLPlanGui_English.80") + getAlgorithmNameAt(i)); //$NON-NLS-1$
			output(Messages.getString("PLPlanGui_English.81") + mean(maxTimeForEachSearch[i]) + Messages.getString("PLPlanGui_English.82")); //$NON-NLS-1$ //$NON-NLS-2$
			output(Messages.getString("PLPlanGui_English.83") + stdDev(maxTimeForEachSearch[i]) //$NON-NLS-1$
					+ Messages.getString("PLPlanGui_English.84")); //$NON-NLS-1$
		}
		output(Messages.getString("PLPlanGui_English.85")); //$NON-NLS-1$
		output(Messages.getString("PLPlanGui_English.86")); //$NON-NLS-1$

		saveResultsToFile(maxTimeForEachSearch, solutionSize, nbTimesUp,
				nbOutOfMemory, nbActions);
	}

	public void saveResultsToFile(double[][] resultatsTemps,
			double[][] tailleSolution, int[] timesUp, int[] outOfMemory,
			int cplx) {


		String virguleFile = getProblemFileName() + Messages.getString("PLPlanGui_English.87") + getNumberOfProblems() + Messages.getString("PLPlanGui_English.88") //$NON-NLS-1$ //$NON-NLS-2$
				+ getNbTryByProblem() + Messages.getString("PLPlanGui_English.89") + cplx + Messages.getString("PLPlanGui_English.90"); //$NON-NLS-1$ //$NON-NLS-2$

		String outputFile = getProblemFileName() + Messages.getString("PLPlanGui_English.91") + getNumberOfProblems() + Messages.getString("PLPlanGui_English.92") //$NON-NLS-1$ //$NON-NLS-2$
				+ getNbTryByProblem() + Messages.getString("PLPlanGui_English.93") + cplx + Messages.getString("PLPlanGui_English.94"); //$NON-NLS-1$ //$NON-NLS-2$

		try {
			File fileVirgule = new File(virguleFile);
			FileOutputStream fosVirgule = new FileOutputStream(fileVirgule
					.getCanonicalPath());

			File fileOutput = new File(outputFile);
			FileOutputStream fosOutput = new FileOutputStream(fileOutput
					.getCanonicalPath());

			Writer w = new BufferedWriter(new OutputStreamWriter(fosVirgule));

			// write temps
			StringBuffer tempString = new StringBuffer();
			for (int i = 0; i < resultatsTemps.length; i++) {
				for (int j = 0; j < resultatsTemps[i].length; j++) {
					tempString.append(resultatsTemps[i][j] + Messages.getString("PLPlanGui_English.95")); //$NON-NLS-1$
				}
				tempString.append(timesUp[i]);
				tempString.append(Messages.getString("PLPlanGui_English.96")); //$NON-NLS-1$
				tempString.append(outOfMemory[i]);
				tempString.append(Messages.getString("PLPlanGui_English.97")); //$NON-NLS-1$
			}

			tempString.append(Messages.getString("PLPlanGui_English.98")); //$NON-NLS-1$

			// write taille solution
			for (int i = 0; i < tailleSolution.length; i++) {
				for (int j = 0; j < tailleSolution[i].length; j++) {
					tempString.append(tailleSolution[i][j] + Messages.getString("PLPlanGui_English.99")); //$NON-NLS-1$
				}
				tempString.append(Messages.getString("PLPlanGui_English.100")); //$NON-NLS-1$
			}

			w.write(tempString.toString());
			w.flush();
			w.close();

			w = new BufferedWriter(new OutputStreamWriter(fosOutput));
			w.write(getOutput());
			w.flush();
			w.close();
			getJTextAreaOutput().setText(Messages.getString("PLPlanGui_English.101")); //$NON-NLS-1$
            
            output(virguleFile);
            output(outputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<List<Proposition>> generateRandomGoal(Set<Action> ops, Set<Proposition> facts, int nbActions,
			int numberOfTimes) {

		System.out.println(Messages.getString("PLPlanGui_English.102") //$NON-NLS-1$
				+ Integer.toString(nbActions));
        List<Proposition> goal = null;
        List<List<Proposition>> goalPermutationList = new ArrayList<List<Proposition>>(numberOfTimes);
		List plan = null;
		do {
			System.out.print(Messages.getString("PLPlanGui_English.103")); //$NON-NLS-1$
			goal = new ArrayList<Proposition>(facts);
			for (int i = 0; i < nbActions; i++) {
                Set<Action> opMap = new HashSet<Action>(ops);
				while (opMap.size() > 0) {
					int index = random.nextInt(opMap.size());
					Iterator<Action> iter = opMap.iterator();
					for (int j = 0; j < index; j++) {
						iter.next();
					}
					Action a = iter.next();
					opMap.remove(a);
					if (a.isApplicableForPropositions(goal)) {
						goal = sucessor(goal, a);
						break;
					}
				}
			}

			try {
				FWPlanner fw = new FWPlanner(ops, facts, goal);
				//fw.setUsingTimeConstraint(false);
				fw.setAlgorithmePersistentSet(0);
				fw.setSleepSetEnabled(false);
				fw.setWasSeenEnabled(true);
				plan = fw.run();
			} catch (Exception e) {
				System.out.println(Messages.getString("PLPlanGui_English.104")); // UN //$NON-NLS-1$
																			// AJOUT
			}
		} while (plan.size() != nbActions);

		System.out.println(Messages.getString("PLPlanGui_English.105")); //$NON-NLS-1$
		for (int i = 0; i < numberOfTimes; i++) {
			Collections.shuffle(goal);
			goalPermutationList.add(new ArrayList<Proposition>(goal));

		}
		return goalPermutationList;
	}

	public ArrayList<Proposition> sucessor(List<Proposition> s, Action a) {
		ArrayList<Proposition> sSucc = new ArrayList<Proposition>();
		//effets ngatifs
		Iterator<Proposition> iter = s.iterator();
		for (int i = 0; i < s.size(); i++) {
			Proposition prop =  iter.next();
			if (!a.getNegEffectSet().contains(prop)) {
				sSucc.add(prop);
			}
		}
		//effets positifs
		iter = a.getPosEffectSet().iterator();
		for (int i = 0; i < a.getPosEffectSet().size(); i++) {
			Proposition prop =  iter.next();
			if (!s.contains(prop)) {
				sSucc.add(prop);
			}
		}
		return sSucc;
	}

	public double mean(double[] tableau) {
		double sum = 0;
		double size = tableau.length;
		for (int i = 0; i < tableau.length; i++) {
			if (tableau[i] != OUT_OF_MEMORY_CODE)
				sum += tableau[i];
		}
		return sum / size;
	}

	public double stdDev(double[] tableau) {
		double mean = mean(tableau);
		double sum = 0;
		for (int i = 0; i < tableau.length; i++) {
			sum += Math.pow(tableau[i] - mean, 2.0);
		}
		return Math.sqrt(sum / ((double) tableau.length));
	}

	public double solutionSize(List solution) {
		double size = 0;
		Iterator iter = solution.iterator();
		while (iter.hasNext()) {
			Object element = (Object) iter.next();
			if (element instanceof List) {
				size += ((List) element).size();
			} else {
				size++;
			}
		}
		return size;
	}

	/**
	 * This method initializes jProblemSize
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJProblemSizeFirst() {
		if (jProblemSizeFirst == null) {
			jProblemSizeFirst = new JTextField();
			jProblemSizeFirst.setEditable(false);
		}
		return jProblemSizeFirst;
	}

	/**
	 * This method initializes jTextFieldMaxTime
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJProblemSizeLast() {
		if (jProblemSizeLast == null) {
			jProblemSizeLast = new JTextField();
			jProblemSizeLast.setEnabled(true);
			jProblemSizeLast.setEditable(false);
		}
		return jProblemSizeLast;
	}

	/**
	 * This method initializes jTextFieldMaxTime
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldTempsMaximum() {
		if (jTextFieldMaxTime == null) {
			jTextFieldMaxTime = new JTextField();
			jTextFieldMaxTime.setText(Messages.getString("PLPlanGui_English.106")); //$NON-NLS-1$
		}
		return jTextFieldMaxTime;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			java.awt.GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			jPanel.setPreferredSize(new java.awt.Dimension(300, 82));
			gridBagConstraints41.gridx = 0;
			gridBagConstraints41.gridy = 0;
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 1;
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 2;
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints111.gridx = 1;
			gridBagConstraints111.gridy = 2;
			gridBagConstraints12.gridx = 2;
			gridBagConstraints12.gridy = 0;
			gridBagConstraints13.gridx = 2;
			gridBagConstraints13.gridy = 1;
			gridBagConstraints14.gridx = 2;
			gridBagConstraints14.gridy = 2;
			jPanel.add(getJCheckBoxB(), gridBagConstraints41);
			jPanel.add(getJCheckBoxF0(), gridBagConstraints5);
			jPanel.add(getJCheckBoxF0SM(), gridBagConstraints6);
			jPanel.add(getJCheckBoxF0SS(), gridBagConstraints7);
			jPanel.add(getJCheckBoxF0SMSS(), gridBagConstraints10);
			jPanel.add(getJCheckBoxF1(), gridBagConstraints111);
			jPanel.add(getJCheckBoxF1SM(), gridBagConstraints12);
			jPanel.add(getJCheckBoxF1SS(), gridBagConstraints13);
			jPanel.add(getJCheckBoxF1SMSS(), gridBagConstraints14);
		}
		return jPanel;
	}

	/**
	 * This method initializes jCheckBoxB
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxB() {
		if (jCheckBoxB == null) {
			jCheckBoxB = new JCheckBox();
			jCheckBoxB.setText(Messages.getString("PLPlanGui_English.107")); //$NON-NLS-1$
			jCheckBoxB.setSelected(true);
		}
		return jCheckBoxB;
	}

	/**
	 * This method initializes jCheckBoxF0
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxF0() {
		if (jCheckBoxF0 == null) {
			jCheckBoxF0 = new JCheckBox();
			jCheckBoxF0.setText(Messages.getString("PLPlanGui_English.108")); //$NON-NLS-1$
			jCheckBoxF0.setSelected(true);
		}
		return jCheckBoxF0;
	}

	/**
	 * This method initializes jCheckBoxF0SM
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxF0SM() {
		if (jCheckBoxF0SM == null) {
			jCheckBoxF0SM = new JCheckBox();
			jCheckBoxF0SM.setText(Messages.getString("PLPlanGui_English.109")); //$NON-NLS-1$
			jCheckBoxF0SM.setSelected(true);
		}
		return jCheckBoxF0SM;
	}

	/**
	 * This method initializes jCheckBoxF0SS
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxF0SS() {
		if (jCheckBoxF0SS == null) {
			jCheckBoxF0SS = new JCheckBox();
			jCheckBoxF0SS.setText(Messages.getString("PLPlanGui_English.110")); //$NON-NLS-1$
			jCheckBoxF0SS.setSelected(true);
		}
		return jCheckBoxF0SS;
	}

	/**d
	 * This method initializes jCheckBoxF0SMSS
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxF0SMSS() {
		if (jCheckBoxF0SMSS == null) {
			jCheckBoxF0SMSS = new JCheckBox();
			jCheckBoxF0SMSS.setText(Messages.getString("PLPlanGui_English.111")); //$NON-NLS-1$
			jCheckBoxF0SMSS.setSelected(true);
		}
		return jCheckBoxF0SMSS;
	}

	/**
	 * This method initializes jCheckBoxF1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxF1() {
		if (jCheckBoxF1 == null) {
			jCheckBoxF1 = new JCheckBox();
			jCheckBoxF1.setText(Messages.getString("PLPlanGui_English.112")); //$NON-NLS-1$
			jCheckBoxF1.setSelected(true);
		}
		return jCheckBoxF1;
	}

	/**
	 * This method initializes jCheckBoxF1SM
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxF1SM() {
		if (jCheckBoxF1SM == null) {
			jCheckBoxF1SM = new JCheckBox();
			jCheckBoxF1SM.setText(Messages.getString("PLPlanGui_English.113")); //$NON-NLS-1$
			jCheckBoxF1SM.setSelected(true);
		}
		return jCheckBoxF1SM;
	}

	/**
	 * This method initializes jCheckBoxF1SS
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxF1SS() {
		if (jCheckBoxF1SS == null) {
			jCheckBoxF1SS = new JCheckBox();
			jCheckBoxF1SS.setText(Messages.getString("PLPlanGui_English.114")); //$NON-NLS-1$
			jCheckBoxF1SS.setSelected(true);
		}
		return jCheckBoxF1SS;
	}

	/**
	 * This method initializes jCheckBoxF1SMSS
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxF1SMSS() {
		if (jCheckBoxF1SMSS == null) {
			jCheckBoxF1SMSS = new JCheckBox();
			jCheckBoxF1SMSS.setText(Messages.getString("PLPlanGui_English.115")); //$NON-NLS-1$
			jCheckBoxF1SMSS.setSelected(true);
		}
		return jCheckBoxF1SMSS;
	}

	public static void main(String[] args) {
		PLPlanGui_English ppgui = new PLPlanGui_English();
		ppgui.getJFrame().setVisible(true);
	}

	private boolean isAlgorithmEnabled(int index) {
		switch (index) {
		case 0:
			return getJCheckBoxB().isSelected();
		case 1:
			return getJCheckBoxF0().isSelected();
		case 2:
			return getJCheckBoxF0SM().isSelected();
		case 3:
			return getJCheckBoxF0SS().isSelected();
		case 4:
			return getJCheckBoxF0SMSS().isSelected();
		case 5:
			return getJCheckBoxF1().isSelected();
		case 6:
			return getJCheckBoxF1SM().isSelected();
		case 7:
			return getJCheckBoxF1SS().isSelected();
		case 8:
			return getJCheckBoxF1SMSS().isSelected();
		default:
			return false;
		}
	}

}