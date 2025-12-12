# Turtle-Graphics-Project
Overview

This project is a Java-based Turtle Graphics application built using the LBUGraphics library. It allows users to draw shapes, move the turtle, change pen settings, and save or load images and command history. The program follows object-oriented principles and satisfies multiple requirements such as GUI setup, command processing, validation, and file handling.

The entire program runs inside a graphical window, and user commands are processed through method calls.

Features : 

Core functionalities :

-Movement commands: forward, backward, move, reverse

-Rotation commands: left, right

-Pen controls: drawon, drawoff

-Pen customization: colour changes, width changes

-Canvas operations: clear, reset

-Shape drawing:

Square

Circle

Regular polygons

Equilateral triangles

Custom triangles using three sides

-File Handling

Save the drawing as a PNG file

Load an image into the canvas

Save user command history to a text file

Load commands from a file

-User Safety and Validation

Input validation for distance, angles, RGB values, and triangle sides

Confirmation pop-ups before clearing or exiting if there are unsaved changes

-Extra Features

Smooth graphics using anti-aliasing

Polygon and circle drawing using mathematical calculations

Named signature in GUI through a customized about() method

Technologies used :

-Java

-Swing (JFrame, JPanel, JFileChooser, JOptionPane)

-AWT (Graphics, Color, Font, RenderingHints)

-uk.ac.leedsbeckett.oop.LBUGraphics library for turtle graphics

-ImageIO for PNG handling

-File I/O: BufferedReader, FileWriter, File, FileReader

Future Improvements: 

-Add a command console for typing instructions directly

-Add mouse-based drawing

-Add animation support

-Support JSON-based command history storage

-Add undo/redo functionality
