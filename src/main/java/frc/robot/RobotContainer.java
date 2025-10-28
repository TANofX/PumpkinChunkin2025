// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.input.controllers.XboxControllerWrapper;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.*;


public class RobotContainer {
  // Controllers
  public static final XboxControllerWrapper driver = new XboxControllerWrapper(0, 0.1);
  public static final XboxControllerWrapper coDriver = new XboxControllerWrapper(1, 0.1);

  

  // Subsystems
  public static final Swerve swerve = new Swerve();// new Swerve();
  // public static final LEDs LEDs = new LEDs();
  // Other Hardware
  public static final PowerDistribution powerDistribution = new PowerDistribution();
  public static final Chunker chunker = new Chunker();

  // Vision clients
  // public static final JetsonClient jetson = new JetsonClient();

  public RobotContainer() {
    SmartDashboard.putData(swerve.zeroModulesCommand());
    configureButtonBindings();
    // LEDs.setDefaultCommand(new Notifications());

    SmartDashboard.putData("Reset position",Commands.runOnce(() -> {
      swerve.resetOdometry(Pose2d.kZero);
    }, swerve));

    SmartDashboard.putData("Flip position 180",Commands.runOnce(() -> {
      swerve.resetOdometry(swerve.getPose().transformBy(new Transform2d(
              Translation2d.kZero,
              Rotation2d.fromDegrees(180)
      )));
    }, swerve));

    SmartDashboard.putData("Flip position 90",Commands.runOnce(() -> {
      swerve.resetOdometry(swerve.getPose().transformBy(new Transform2d(
              Translation2d.kZero,
              Rotation2d.fromDegrees(90)
      )));
    }, swerve));

    // SmartDashboard.putData(intake.getIntakePivotTuner());
    // SmartDashboard.putData(intake.getIntakeTuner());
    //SmartDashboard.putData("Tune Elevation", shooterWrist.getElevationTunerCommand());
    //SmartDashboard.putData("Tune Shooter", shooter.getShooterTunerCommand());
    //SmartDashboard.putData("Tune Shooter Intake", shooter.getIntakeTunerCommand());
    //SmartDashboard.putData("Tune Intake", intake.getIntakeTuner());
    // SmartDashboard.putData(Commands.runOnce(() -> {
    // intake.updateRotationOffset();}, intake));

    //SmartDashboard.putData("Tune Elevator Motor", elevator.getHeightTunerCommand());
    //SmartDashboard.putData("Elevator Extents", new FindMotorExtents());

    // SmartDashboard.putData("Robot At Center Blue Ring", Commands.runOnce(() -> {
    //   swerve.resetOdometry(new Pose2d(new Translation2d(2.9, 5.55), Rotation2d.fromDegrees(0)));
    // }, swerve));
    // SmartDashboard.putData("Robot At Red Speaker", new AtRedSubWoofer());

    // Register Named Commands for pathplanner
    //NamedCommands.registerCommand("ReadyToShootInSpeaker", new ShootInSpeaker());
    //NamedCommands.registerCommand("SpeakerShot", new Shoot(false));
    //NamedCommands.registerCommand("New AutoSpeakerShot", newAutoShootInSpeaker());
    // NamedCommands.registerCommand("", );
    
    //PPHolonomicDriveController.setRotationTargetOverride(this::overrideAngle);
  }
  
  private void configureButtonBindings() {    
        //Commands.waitSeconds(.5).andThen(new Shoot().andThen(Commands.waitSeconds(0.5).andThen(Commands.runOnce(() -> {
          //shooter.stopMotors();
       // }, shooter))))));
    //coDriver.X().onTrue(new ElevatorToMin());
    coDriver.START();
    driver.X().onTrue(chunker.windupCommand(0.2));
    driver.Y().onTrue(chunker.releaseCommand());
    driver.A().onTrue(chunker.closeCommand());
    driver.B().onTrue(chunker.stopMotorCommand());

  /*   
        }, shooter))).andThen(new Shoot().andThen(Commands.waitSeconds(0.5).andThen(Commands.runOnce(() -> {
          shooter.stopMotors();

        }))))); */
  
  }
}