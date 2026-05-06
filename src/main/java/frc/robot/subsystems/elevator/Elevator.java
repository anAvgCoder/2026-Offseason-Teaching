package frc.robot.subsystems.elevator;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Elevator {
    private final SparkMax motor;
    private final SparkMaxConfig config;
    private final RelativeEncoder encoder;
    private final SparkClosedLoopController cLController;

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

    public void up() {
        motor.set(speed);
    }

    public void down() {
        motor.set(-speed);
    }

    public void stop() {
        motor.set(0);
    }

    // public int goToLevel(int level) {

    // }

    public void resetEncoder() {
        encoder.setPosition(0);
    }

    public double getEncoder() {
        return encoder.getPosition();
    }
}
