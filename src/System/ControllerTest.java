package System;

import java.io.IOException;

import com.studiohartman.jamepad.*;

public class ControllerTest {
    public static void main(String[] args) {
        // 1. Initialize the manager (this unpacks the SDL2 native files for macOS)
        ControllerManager controllers = new ControllerManager();
     // 1. Initialize SDL FIRST
        controllers.initSDLGamepad();

     // 1. Load the mappings (WITH the comma in the text file!)
        try {
			controllers.addMappingsFromFile("/custom_mappings.txt");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("Total controllers ready: " + controllers.getNumControllers());

        // 2. Grab the controller at slot 0
        ControllerIndex myController = controllers.getControllerIndex(0);

     // 3. The Diagnostic Game Loop
        boolean running = true;
        while (running) {
            // Fetch the newest inputs from the hardware
            controllers.update(); 
            
            // Get a fresh snapshot of the controller at slot 0
            ControllerState state = controllers.getState(0);
            
            if (state.isConnected) {
                // Let's test multiple inputs in case 'A' is just mapped wrong
                if (state.a) System.out.println("A Button Pressed!");
                if (state.b) System.out.println("B Button Pressed!");
                if (state.dpadUp) System.out.println("D-Pad Up Pressed!");
                
                // Test the joystick (Crucial for testing if macOS is blocking input)
                if (state.leftStickX > 0.5 || state.leftStickX < -0.5) {
                    System.out.println("Left Stick moving! Value: " + state.leftStickX);
                }
            } else {
                // If it prints this, the controller is dropping connection during the loop
                System.out.println("Controller lost connection!");
            }
            
            // Add a small pause so you can read the console (100ms)
            try { 
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 3. Always clean up when done to prevent memory leaks
        controllers.quitSDLGamepad();
    }
}