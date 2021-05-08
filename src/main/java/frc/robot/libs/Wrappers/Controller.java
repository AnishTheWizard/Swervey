/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Wrappers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * Add your docs here.
 */
public class Controller {//might be useful later, idk

    private Joystick controller;

    public Controller(int port) {
        controller = new Joystick(port);
    }

    public JoystickButton getButton(int id) {
        return new JoystickButton(controller, id);
    }

    public double getAxis(int id) {
        return controller.getRawAxis(id);
    }

    public double getLeftJoyX() {
        return -controller.getX();
    }

    public double getLeftJoyY() {
        return controller.getY();
    }

    public double getLeftTrigger() {
        return controller.getRawAxis(2);
    }

    public double getRightTrigger() {
        return controller.getRawAxis(3);
    }

    public double getRightJoyX() {
        return -controller.getRawAxis(4);
    }

    public double getRightJoyY() {
        return controller.getRawAxis(5);
    }

    public JoystickButton getAButton() {
        return new JoystickButton(controller, 1);
    } 
    
    public JoystickButton getBButton() {
        return new JoystickButton(controller, 2);
    }

    public JoystickButton getXButton() {
        return new JoystickButton(controller, 3);
    }

    public JoystickButton getYButton() {
        return new JoystickButton(controller, 4);
    }

    public JoystickButton getLBButton() {
        return new JoystickButton(controller, 5);
    }

    public JoystickButton getRBButton() {
        return new JoystickButton(controller, 6);
    }

    public JoystickButton getSELECTButton() {
        return new JoystickButton(controller, 7);
    }
    
    public JoystickButton getSTARTButton() {
        return new JoystickButton(controller, 8);
    }

    public JoystickButton getLSButton() {
        return new JoystickButton(controller, 9);
    }

    public JoystickButton getRSButton() {
        return new JoystickButton(controller, 10);
    }
}
