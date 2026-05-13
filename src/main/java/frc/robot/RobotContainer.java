package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveCommands;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOSpark;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.elevator.Elevator;

public class RobotContainer {
  // Subsystems
  private final Drive drive;
  private final Shooter shooter;
  private final Elevator elevator;
  // Joysticks
  private static final Joystick leftJoy = new Joystick(0);
  private static final Joystick rightJoy = new Joystick(1);
  private static final Joystick buttonPanel = new Joystick(2);

  // Buttons
  private static final JoystickButton rightJoy1Button = new JoystickButton(rightJoy, 1);

  private static final JoystickButton leftJoy1Button = new JoystickButton(leftJoy, 1);

  private static final JoystickButton buttonPanel1 = new JoystickButton(buttonPanel, 1);
  private static final JoystickButton buttonPanel2 = new JoystickButton(buttonPanel, 2);
  private static final JoystickButton buttonPanel3 = new JoystickButton(buttonPanel, 3);
  private static final JoystickButton buttonPanel4 = new JoystickButton(buttonPanel, 4);
  private static final JoystickButton buttonPanel5 = new JoystickButton(buttonPanel, 5);
  private static final JoystickButton buttonPanel6 = new JoystickButton(buttonPanel, 6);
  private static final JoystickButton buttonPanel7 = new JoystickButton(buttonPanel, 7);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    switch (Constants.currentMode) {
      case REAL:
        drive =
            new Drive(
                new GyroIOPigeon2(),
                new ModuleIOSpark(0),
                new ModuleIOSpark(1),
                new ModuleIOSpark(2),
                new ModuleIOSpark(3));
        break;

      case SIM:
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSim(),
                new ModuleIOSim(),
                new ModuleIOSim(),
                new ModuleIOSim());
        break;

      default:
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {});
        break;
    }

    shooter = new Shooter();

    elevator = new Elevator();

    configureButtonBindings();
  }

  private void configureButtonBindings() {

    drive.setDefaultCommand(
        DriveCommands.joystickDrive(
            drive,
            () -> getClampedDrive(rightJoy) ? -rightJoy.getY() : 0.0,
            () -> getClampedDrive(rightJoy) ? -rightJoy.getX() : 0.0,
            () -> getClampedTurn(leftJoy) ? -leftJoy.getX() : 0.0));

    rightJoy1Button.whileTrue(
      Commands.runOnce(
        () -> shooter.shoot()));

    rightJoy1Button.onFalse(
      Commands.runOnce(
        () -> shooter.stop()));

    leftJoy1Button.whileTrue(
      Commands.runOnce(
        () -> shooter.reverse()));

    leftJoy1Button.onFalse(
      Commands.runOnce(
        () -> shooter.stop()));

    buttonPanel1.whileTrue(
      Commands.runOnce(
        () -> elevator.up()));

    buttonPanel1.onFalse(
      Commands.runOnce(
        () -> elevator.stop()));
    
    buttonPanel2.whileTrue(
    Commands.runOnce(
    () -> elevator.down()));
  
    buttonPanel2.onFalse(
      Commands.runOnce(
      () -> elevator.stop()));

    buttonPanel3.onTrue(
      Commands.runOnce(
      () -> elevator.goToHome(0)));

    buttonPanel4.onTrue(
      Commands.runOnce(
        () -> elevator.goToLevel1(1)));

    buttonPanel5.onTrue(
      Commands.runOnce(
        () -> elevator.goToLevel2(2)));

    buttonPanel6.onTrue(
      Commands.runOnce(
        () -> elevator.goToLevel3(3)));

    buttonPanel7.onTrue(
      Commands.runOnce(
        () -> elevator.goToLevel4(4)));
  }

  public boolean getClampedTurn(Joystick joy) {
    return Math.abs(joy.getX()) >= 0.1;
  }

  public boolean getClampedDrive(Joystick joy) {
    return (Math.abs(joy.getY()) > 0.1) || (Math.abs(joy.getX()) > 0.1);
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
