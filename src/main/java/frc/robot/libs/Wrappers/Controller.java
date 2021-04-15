/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Wrappers;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Add your docs here.
 */
public class Controller {//might be useful later, idk

    private Joystick controller;

    public Controller(int port) {
        controller = new Joystick(port);
    }
}
