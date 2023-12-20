package com.sample;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.logger;
import org.kie.api.logger.KieRuntimeLogger;

public class GUI extends JFrame {
	private ButtonGroup group;
	private JLabel question = new JLabel("");
	private JButton okButton = new JButton("Next");
	private ArrayList<JRadioButton> buttons = new ArrayList<JRadioButton>(); 
    private ArrayList<String> buttonNames = new ArrayList<String>();
    private KieSession kSession;
    public GUI(KieSession k){
    	this.kSession = k;
    	setTitle("What should I drink? Beer edition");
    	setSize(450,250);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLocationRelativeTo(null);
    	setLayout(new GridLayout(4,1));
    	
    	okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkSelected()) {
                	//displayMessage("Next Question");
                    kSession.insert(getAnswer());
                    System.out.println(getAnswer());
                    group.clearSelection();
                    kSession.fireAllRules();
                    
                    System.out.println("Inserted current selection");
                } else {
                    System.out.println("no way");
                }
            }
        });
    }
    /**
     * Sets the class attributes to those sent by drools
     */
    public void updateGUI(String currentQuestion, String possibleAnswers) {
    	this.remove(question);
    	for(JRadioButton button: buttons) {
    		this.remove(button);
    	}
    	this.remove(okButton);
    	
    	this.setSize(450,250);
    	this.setLayout(new GridLayout(2+possibleAnswers.split(";").length,1));
    	this.question = new JLabel(currentQuestion);
    	this.buttons = new ArrayList<JRadioButton>();
    	this.group = new ButtonGroup();
    	//question name is added
    	this.add(question);
    	//buttons are cleared
    	buttons = new ArrayList<JRadioButton>(); 
    	for(String name : possibleAnswers.split(";")) {
    		//buttons are added to the gui
    		
    		buttons.add(new JRadioButton(name));
    		this.add(buttons.get(buttons.size()-1));
    		group.add(buttons.get(buttons.size()-1));
    	}
    	this.add(okButton);
    	this.revalidate();
    	this.repaint();
    }
    public boolean checkSelected() {
    	for(int i=0;i<buttons.size();i++) {
    		if(buttons.get(i).isSelected()) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public String getAnswer() {
    	for(int i=0;i<buttons.size();i++) {
    		if(buttons.get(i).isSelected()) {
    			return buttons.get(i).getText();
    		}
    	}
    	return "";
    }
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    public void imageGUI(String img) {
    	remove(question);
    	for(JRadioButton button: buttons) {
    		remove(button);
    	}
    	remove(okButton);
    	JLabel stringLabel = new JLabel("You might like these beers:");
    	JLabel emptyLabel = new JLabel(" ");
    	ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    	ArrayList<String> imgpaths = sendImagePaths(img,";");
    	for(String path: imgpaths) {
    		BufferedImage image = loadImage(path);
    		images.add(image);
    		System.out.println(path);
    	}

    	ArrayList<ImageIcon> icons = new ArrayList<ImageIcon>();

    	for(BufferedImage image: images) {
    		ImageIcon icon = new ImageIcon(image);
    		icons.add(icon);
    	}


        setLayout(new GridLayout(0, 2));

        add(stringLabel);
        add(emptyLabel);
        for(ImageIcon icon: icons) {
        	add(new JLabel(icon));
        }

        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void closeGUI(String paths) {
    	dispose();
    	imageGUI(paths);
    }
    private BufferedImage loadImage(String imagePath) {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<String> sendImagePaths(String images, String between){
   	 String[] parts = images.split(between);
        ArrayList<String> resultList = new ArrayList<>();
        for (String part : parts) {
            resultList.add(part);
        }
        return resultList;
   }
}
