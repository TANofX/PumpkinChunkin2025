// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Chunker extends SubsystemBase {
  private final SparkFlex windupMotor;
  private final Solenoid releasePiston;
  private final SparkLimitSwitch downSwitch;
   private final PneumaticsControlModule releaseModule;
  
  public Chunker() {
    windupMotor = new SparkFlex(40,MotorType.kBrushless);
    releaseModule = new PneumaticsControlModule(Constants.PumpkinChunkin.PCMID); //if the pump no start, uncomment
    releasePiston = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    downSwitch = windupMotor.getForwardLimitSwitch(); //Which one, reverse or forward???
    releaseModule.enableCompressorDigital();
  }
  
  public boolean readyToRelease() {
    return downSwitch.isPressed();
  }

  public void release() {
    releasePiston.set(true); //True or False ???
  }

  public void close() {
    releasePiston.set(false); //True or False ???
  }

  public void windup(double speed) {
    windupMotor.set(speed);
   }
  
  public void stopMotor() {
    windupMotor.stopMotor();
  }
  @Override
  public void periodic() {
  }

  public Command windupCommand(double speed) {  
    return Commands.sequence(
    Commands.run(() -> { windup(speed);
    }, this).until( () -> {
      return readyToRelease();
    })).finallyDo(() -> {stopMotor();});
}

  public Command releaseCommand() {
    return Commands.runOnce(
      () -> {release();}, this);
  }

  public Command closeCommand() {
    return Commands.runOnce(
      () -> {close();}, this);
  }

  public Command stopMotorCommand() {
    return Commands.runOnce(
      ()-> {stopMotor();}, null);
  }
  }
