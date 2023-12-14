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
	private ButtonGroup buttonGroup;
	private JLabel label;
    private JRadioButton yesButton = new JRadioButton("Yes");
    private JRadioButton noButton = new JRadioButton("No");
    private JButton okButton = new JButton("Next");
    public GUI(String message, KieSession kSession) {
    	label = new JLabel(message);
    	setTitle("What should I drink? Beer edition");
    	setSize(450,250);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLocationRelativeTo(null);
    	
    	buttonGroup = new ButtonGroup();
    	buttonGroup.add(yesButton);
    	buttonGroup.add(noButton);
    	
    	okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (yesButton.isSelected() || noButton.isSelected()) {
                    //displayMessage("Next Question");
                	
                    kSession.insert(getAnswer());
                    deselect();
                    kSession.fireAllRules();
                    
                    System.out.println("Inserted current selection");
                } else {
                    //displayMessage("Please select an option.");
                }
            }
        });
        
        setLayout(new GridLayout(4, 1));

        add(label);
        add(yesButton);
        add(noButton);
        add(okButton);
    }
    public void imageGUI(String img) {
    	remove(label);
    	remove(yesButton);
    	remove(noButton);
    	remove(okButton);
    	System.out.println("1");
    	JLabel stringLabel = new JLabel("You might like these beers:");
    	ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    	ArrayList<String> imgpaths = sendImagePaths(img);
    	for(String path: imgpaths) {
    		BufferedImage image = loadImage(path);
    		images.add(image);
    		System.out.println(path);
    	}
    	System.out.println("2");
        // Load images (replace these with your image file paths)
        //BufferedImage image1 = loadImage("image1.jpg");
        //BufferedImage image2 = loadImage("image2.jpg");
    	ArrayList<ImageIcon> icons = new ArrayList<ImageIcon>();
        // Create ImageIcon from BufferedImage
    	for(BufferedImage image: images) {
    		ImageIcon icon = new ImageIcon(image);
    		icons.add(icon);
    	}
        //ImageIcon icon1 = new ImageIcon(image1);
        //ImageIcon icon2 = new ImageIcon(image2);

        //imageLabel1 = new JLabel(icon1);
        //imageLabel2 = new JLabel(icon2);
    	System.out.println("3");
        // Set layout
        setLayout(new GridLayout(1+icons.size(), 1));

        // Add components to the frame
        add(stringLabel);
        for(ImageIcon icon: icons) {
        	add(new JLabel(icon));
        }
        System.out.println("4");
        // Set frame properties
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public ArrayList<String> sendImagePaths(String images){
    	 String[] parts = images.split(" ");
         ArrayList<String> resultList = new ArrayList<>();
         for (String part : parts) {
             resultList.add(part);
         }
         return resultList;
    }
    public void updateGUI(String labelText) {
    	label.setText(labelText);
    }
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    public String getAnswer() {
    	if(yesButton.isSelected()) {
    		return "Yes";
    	}else
    		return "No";
    }
    private void deselect() {
    	buttonGroup.clearSelection();
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

}
