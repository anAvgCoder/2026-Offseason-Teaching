package frc.robot.subsystems.elevator;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;
import org.littletonrobotics.urcl.URCL;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BuildConstants;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Elevator extends SubsystemBase{
    private final SparkMax motor;
    private final SparkMaxConfig config;
    private final RelativeEncoder encoder;
    
    private final SparkClosedLoopController cLController;
    DigitalInput mastInput = new DigitalInput(9);

    private double speed;

    // private final double[] levelEncoderPositions = {0, 1, 2, 3};
    
    public Elevator() {
        motor = new SparkMax(0, MotorType.kBrushless);
        config = new SparkMaxConfig();

        config
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(40);

        config.signals.appliedOutputPeriodMs(20);

        encoder = motor.getEncoder();

        cLController = motor.getClosedLoopController();

        speed = 0.3;
    }
    @Override
    public void periodic() {
        Logger.recordOutput("MastProximity", mastInput.get());
    }

    public void up() {
        motor.set(speed);
    }

    public void down() {
        motor.set(-speed);
    }

    public void stop() {
        motor.set(0);
    }

    public int goToHome(int level) {
        return level;
    }

    public int goToLevel1(int level) {
        return level;
    }

    public int goToLevel2(int level) {
        return level;
    }

    public int goToLevel3(int level) {
        return level;
    }

    public int goToLevel4(int level) {
        return level;
    }

    public void resetEncoder() {
        encoder.setPosition(0);
    }

    public double getEncoder() {
        return encoder.getPosition();
    }
}
