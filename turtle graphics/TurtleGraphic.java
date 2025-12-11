package graphicproject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.Color;


import uk.ac.leedsbeckett.oop.LBUGraphics;


// Requirement1: Inherit from LBUGraphics and set up GUI
public class TurtleGraphic extends LBUGraphics {

    private List<String> commandHistory = new ArrayList<>();
    private boolean isDrawing = true;
    private boolean isImageSaved = true;
    private boolean hasUnsavedChanges = false;


    
 
 // The direction is updated and wrapped around within the range of 0 to 359 degrees
 // to ensure it remains valid (0 <= direction < 360).
    
    public void right(int degrees) {
        direction += degrees;
        direction %= 360;
    }

 
    // Requirement 1
    public TurtleGraphic() {
    	
        JFrame frame = new JFrame("Smriti Shrestha - Turtle Graphic OOP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        
        clear();
        reset(); 
        about();
        
        
    }
    
    //Requirement 4
    //about() method customized to display my name.

    @Override
    public void about() {
        super.about();

        Graphics2D g = (Graphics2D) getGraphicsContext(); 
        g.setFont(new Font("Arial", Font.ITALIC, 24));       
        g.setColor(Color.GREEN);                         
        g.drawString("Smriti Shrestha", 200, 100);         
    }

    
    // Requirement 2
    public void processCommand(String commandInput) {
        String[] parts = commandInput.trim().split("\\s+");
        

        if (parts.length == 0 || parts[0].isEmpty()) {
            showError("No command entered.");
            return;
        }
        String command = parts[0].toLowerCase();

        try {
        	
        	System.out.println("Processing command: " + commandInput); 
        	
            switch (command) {
                case "about":
                    about();
                    break;

                 
                case "forward":
                	
                	// Requirement 3: validation
                    if (parts.length != 2) {
                        showError("Usage: forward <positive distance>");
                    } else {
                        int distance = Integer.parseInt(parts[1]);
                        if (distance < 0) {
                            showError("Distance must be a positive number.");
                        } else {
                            forward(distance);
                        }
                    }
                    break;
                    
                case "backward":
                	if (parts.length != 2) {
                        showError("Usage: backward <positive distance>");
                    } else {
                        int dist = Integer.parseInt(parts[1]);
                        if (dist < 0) {
                            showError("Distance must be a positive number.");
                        } else {
                            forward(-dist);
                        }
                    }
                    break;
             

                case "move":
                    validateAndMove(parts);
                    break;

                case "reverse":
                    validateAndReverse(parts);
                    break;

                case "left":
                	validateAndRotate(command, parts);
                	
                    break;
                	
                case "right":
                	validateAndRotate(command, parts);
                	
                    break;
                    

                case "clear":
                    clear();
                    break;

                case "reset":
                    reset();
                    break;

                case "drawon":
                    setDrawing(true);
                    break;

                case "drawoff":
                    setDrawing(false);
                    break;

                case "red":
                    setPenColour(Color.RED);
                    break;

                case "green":
                    setPenColour(Color.GREEN);
                    break;

                case "blue":
                    setPenColour(Color.BLUE);
                    break;

                case "white":
                    setPenColour(Color.WHITE);
                    break;

                case "saveimage":
                    saveimagepng();
                    break;

                case "loadimage":
                    loadImage();
                    break;

                case "savecommands":
                    saveCommands();
                    break;

                case "loadcommands":
                    loadCommands();
                    break;

                case "square":
                    drawSquare(parts);
                    break;

                case "triangle":
                    if (parts.length != 2) {
                        showError("Usage: triangle <size> or triangle <side1>,<side2>,<side3>");
                        break;
                    }

                    String arg = parts[1];

                    if (arg.contains(",")) {
                        // triangle <side1>,<side2>,<side3>
                        String[] sides = arg.split(",");
                        if (sides.length == 3) {
                            try {
                                int a = Integer.parseInt(sides[0].trim());
                                int b = Integer.parseInt(sides[1].trim());
                                int c = Integer.parseInt(sides[2].trim());

                                if (a > 0 && b > 0 && c > 0) {
                                    drawTriangle(a, b, c);
                                } else {
                                    showError("All sides must be positive numbers.");
                                }
                            } catch (NumberFormatException e) {
                                showError("Invalid number format in triangle sides.");
                            }
                        } else {
                            showError("Usage: triangle <side1>,<side2>,<side3>");
                        }
                    } else {
                        
                        try {
                            int size = Integer.parseInt(arg.trim());
                            if (size <= 0) {
                                showError("Size must be positive.");
                            } else {
                                drawEquilateralTriangle(size);
                            }
                        } catch (NumberFormatException e) {
                            showError("Invalid size format for triangle.");
                        }
                    }
                    break;

                    
                    
                    
                case "penwidth":
                    changePenWidth(parts);
                    break;

                case "pencolour": 
                    changePenColour(parts);
                break;
                
                case "circle":
                	drawCircle(parts);
                	break;
                	
                	
                case "polygon":                                   
                    drawPolygon(parts);                           
                    break;
                    
                case "exit":
                    if (hasUnsavedChanges) {
                        int option = JOptionPane.showConfirmDialog(this, 
                            "You have unsaved changes. Do you want to save before exiting?", 
                            "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

                        if (option == JOptionPane.YES_OPTION) {
                            saveimagepng();  
                            if (isImageSaved) {
                                System.exit(0);  
                            }
                        } else if (option == JOptionPane.NO_OPTION) {
                            System.exit(0);  
                        }
                    } else {
                        System.exit(0); 
                    }
                    break;

 
                    
                default:
                    showError("Invalid command: \"" + command + "\" is not recognized.");
                    return;
            }

            commandHistory.add(commandInput);
            isImageSaved = false;

        } catch (NumberFormatException e) {
            showError("Invalid number format in command: " + commandInput);
        }
    }
    
    
    
 // Extra feature: Draws a circle by dividing it into 36 steps of small lines and angles.

    private void drawCircle(String[] parts) {
    	
          if (parts.length != 2) {
        	
            showError("Usage: circle <radius>");              
            return;
        }
        
        int radius = Integer.parseInt(parts[1]);             
        int steps  = 36;                                     
        double circumference = 2 * Math.PI * radius;
        double stepLength    = circumference / steps;
        double stepAngle     = 360.0 / steps;

        for (int i = 0; i < steps; i++) {
            forward((int)Math.round(stepLength));            
            right((int)Math.round(stepAngle));                
        }
    }
    
    
 // Extra feature: Draws a regular polygon (triangle, square, pentagon, etc.) using given sides and length.

    private void drawPolygon(String[] parts) {
        if (parts.length != 3) {
            showError("Usage: polygon <sides> <length>");     
            return;
        }
        int sides  = Integer.parseInt(parts[1]);             
        int length = Integer.parseInt(parts[2]);             
        if (sides < 3 || length <= 0) {
            showError("Sides must be ≥3 and length >0.");
            return;
        }
        double turnAngle = 360.0 / sides;                        
        for (int i = 0; i < sides; i++) {
            forward(length);
            right((int)Math.round(turnAngle));
        }
    }
    
    


    @Override
    public void forward(int distance) {
        if (isDrawing) {
            super.forward(distance);
        } else {
            int newX = (int) (getxPos() + Math.cos(Math.toRadians(getDirection())) * distance);
            int newY = (int) (getyPos() + Math.sin(Math.toRadians(getDirection())) * distance);
            setxPos(newX);
            setyPos(newY);
            repaint();
            
         }
        hasUnsavedChanges = true;
    }

    //Requirement 4
    private void drawSquare(String[] parts) {
        if (parts.length != 2) {
            showError("Usage: square <length>");
            return;
        }

        int length = Integer.parseInt(parts[1]);
        for (int i = 0; i < 4; i++) {
            forward(length);
            right(90);
        }
    }


    
    //Requirement 4
    private void drawEquilateralTriangle(int size) {
        for (int i = 0; i < 3; i++) {
            forward(size);
            right(120); // Each angle in equilateral triangle is 60°, so turn 180-60 = 120°
        }
    }

    private void drawTriangle(int a, int b, int c) {
        if (!isValidTriangle(a, b, c)) {
            showError("Invalid triangle sides.");
            return;
        }

        forward(a);
        right(180 - calculateAngle(b, c, a)); 
        forward(b);
        right(180 - calculateAngle(a, c, b)); 
        forward(c);
        right(180 - calculateAngle(a, b, c)); 
    }

  
    private boolean isValidTriangle(int a, int b, int c) {
        return a + b > c && a + c > b && b + c > a;
    }

    private int calculateAngle(int side1, int side2, int opposite) {
        double cosValue = (side1 * side1 + side2 * side2 - opposite * opposite) / (2.0 * side1 * side2);
        cosValue = Math.max(-1.0, Math.min(1.0, cosValue)); // clamp to [-1,1]
        double angle = Math.acos(cosValue);
        return (int) Math.toDegrees(angle);
    }


    //Requirement 4
    private void changePenColour(String[] parts) {
        if (parts.length != 2 || !parts[1].contains(",")) {
            showError("Usage: pencolour <red>,<green>,<blue>");
            return;
        }

        String[] rgb = parts[1].split(",");
        if (rgb.length != 3) {
            showError("Provide 3 RGB values.");
            return;
        }

        int r = Integer.parseInt(rgb[0]);
        int g = Integer.parseInt(rgb[1]);
        int b = Integer.parseInt(rgb[2]);

        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            showError("RGB values must be between 0 and 255.");
            return;
        }

        setPenColour(new Color(r, g, b));
    }

    private void changePenWidth(String[] parts) {
        if (parts.length != 2) {
            showError("Usage: penwidth <width>");
            return;
        }

        int width = Integer.parseInt(parts[1]);
        setStroke(width);
    }

 // Requirement 3: validation
    private void validateAndMove(String[] parts) {
        if (parts.length != 2) {
            showError("Usage: forward/move <positive distance>");
            return;
        }

        int distance = Integer.parseInt(parts[1]);
        if (distance < 0) {
            showError("Distance must be a positive number.");
        } else {
            forward(distance);
        }
    }

    private void validateAndReverse(String[] parts) {
        if (parts.length != 2) {
            showError("Usage: reverse <positive distance>");
            return;
        }

        int distance = Integer.parseInt(parts[1]);
        if (distance < 0) {
            showError("Distance must be a positive number.");
        } else {
            forward(-distance);
        }
    }

    private void validateAndRotate(String command, String[] parts) {
        if (parts.length != 2) {
            showError("Usage: " + command + " <angle>");
            return;
        }

        int angle = 0;
        try {
            angle = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            showError("Angle must be a valid integer.");
            return;
        }

        if (angle < 0 || angle > 360) {
            showError("Angle must be between 0 and 360.");
            return;
        }

        if (command.equals("left")) {
            left(angle);
        } else {
            right(angle);
        }
    }

    private void setDrawing(boolean drawing) {
        isDrawing = drawing;
        setPenState(drawing);
    }

    
    
   //Requirement 4
    
    public void saveimagepng() {
    	
    	
        JFileChooser choose = new JFileChooser();
        choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
        choose.setDialogTitle("Saving in PNG format");

        int input = choose.showSaveDialog(null);
        if (input != JFileChooser.APPROVE_OPTION) {
            displayMessage("Cancelled");
            return;
        }

        try {
            File outFile = choose.getSelectedFile();
            if (!outFile.getName().toLowerCase().endsWith(".png")) {
                outFile = new File(outFile.getAbsolutePath() + ".png");
            }

            ImageIO.write(getBufferedImage(), "png", outFile);
            isImageSaved = true;
            JOptionPane.showMessageDialog(null, "File saved successfully!");
        } catch (IOException e) {
            showError("Error saving image: " + e.getMessage());
        }
        hasUnsavedChanges = true;
    }
    
    
    public boolean isImageSaved() {
        return isImageSaved;
    }
   
    public void clear() {
        if (hasUnsavedChanges) {
            int option = JOptionPane.showConfirmDialog(this, 
                "You have unsaved changes. Do you want to save before clearing?", 
                "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (option == JOptionPane.YES_OPTION) {
                saveimagepng();  // Save image if the user clicks YES
                if (isImageSaved) {
                    super.clear();  // Proceed with clearing after saving
                }
            } else if (option == JOptionPane.NO_OPTION) {
                super.clear();  // Clear without saving if the user clicks NO
            } 
            // If CANCEL, do nothing and return, leaving the drawing intact.
        } else {
            super.clear();  // Proceed with clearing if the image is already saved
        }
    }

    private void loadImage() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                BufferedImage image = ImageIO.read(file);
                Graphics g = getGraphics();
                g.drawImage(image, 0, 0, null);
                JOptionPane.showMessageDialog(this, "Image loaded successfully!");
            } catch (IOException e) {
                showError("Error loading image: " + e.getMessage());
            }
        }
    }

    //Requirement 4
    private void saveCommands() {
    	
    	hasUnsavedChanges = false;
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(chooser.getSelectedFile())) {
                for (String cmd : commandHistory) {
                    writer.write(cmd + "\n");
                }
                JOptionPane.showMessageDialog(this, "Commands saved successfully!");
            } catch (IOException e) {
                showError("Error saving commands: " + e.getMessage());
            }
        }
        hasUnsavedChanges = true;
    }

    private void loadCommands() {

        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    processCommand(line);
                }
            } catch (IOException e) {
                showError("Error loading commands: " + e.getMessage());
            }
        }
    }
    
 private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
 
 
 //EXTRA FEATURE: Enhanced drawing quality using anti-aliasing.
//Overrides paintComponent to enable smoother graphics by applying anti-aliasing rendering hints.
 
 @Override
 public void paintComponent(Graphics g) {                       
    Graphics2D g2d = (Graphics2D) g;                           
     
     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      
                          RenderingHints.VALUE_ANTIALIAS_ON);   
     super.paintComponent(g2d);                                  
 }
    
        

}