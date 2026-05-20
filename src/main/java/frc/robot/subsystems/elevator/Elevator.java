package frc.robot.subsystems.elevator;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
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
    private double desiredPosition = 0;
    
    private final SparkClosedLoopController cLController;

    private state currentState;
    enum state{
        HOME,
        LEVEL1,
        LEVEL2,
        LEVEL3,
        LEVEL4,
        UP,
        DOWN,
        STOP
    }
    DigitalInput mastInput = new DigitalInput(9);

    private double speed;

    // private final double[] levelEncoderPositions = {0, 1, 2, 3};
    
    public Elevator() {
        motor = new SparkMax(9, MotorType.kBrushless);
        config = new SparkMaxConfig();

        config
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(40);

        config.signals.appliedOutputPeriodMs(20);

        encoder = motor.getEncoder();

        cLController = motor.getClosedLoopController();

        speed = 0.3;

        if (mastInput.get() == true) {
            encoder.setPosition(0);
            currentState = state.HOME;
        }
    }
    @Override
    public void periodic() {
        Logger.recordOutput("DesiredPosition", desiredPosition);
        Logger.recordOutput("CurrentPosition", encoder.getPosition());
        Logger.recordOutput("MastProximity", mastInput.get());

        if (!((currentState == state.UP) || (currentState == state.DOWN) || (currentState == state.STOP))) {
            cLController.setSetpoint(desiredPosition, ControlType.kPosition, ClosedLoopSlot.kSlot0);
        }
    }

    public void up() {
        motor.set(speed);
        currentState = state.UP;
    }

    public void down() {
        motor.set(-speed);
        currentState = state.DOWN;
    }

    public void stop() {
        motor.set(0.02);
        currentState = state.STOP;
    }

    public void goToHome() {
        desiredPosition = 0;
        currentState = state.HOME;
    }

    public void goToLevel1() {
        desiredPosition = 10;
        currentState = state.LEVEL1;
    }

    public void goToLevel2() {
        desiredPosition = 20;
        currentState = state.LEVEL2;
    }

    public void goToLevel3() {
        desiredPosition = 30;
        currentState = state.LEVEL3;
    }

    public void goToLevel4() {
        desiredPosition = 40;
        currentState = state.LEVEL4;
    }

    public void resetEncoder() {
        encoder.setPosition(0);
    }

    public double getEncoder() {
        if (Math.random() > 0.95) {
            System.out.println(encoder.getPosition());
        }
        return encoder.getPosition();
    }
}
