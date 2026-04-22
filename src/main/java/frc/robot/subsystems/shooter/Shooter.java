package frc.robot.subsystems.shooter;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{
    SparkMax motor;
    SparkMaxConfig config;

    double speed;

    public Shooter() {

        motor = new SparkMax(0, MotorType.kBrushless);
        config = new SparkMaxConfig();

        config
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(40);

        config
            .signals.appliedOutputPeriodMs(20);

        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        speed = 0.3;
    }

    public void shoot() {
        motor.set(speed);
    }
    
    public void reverse() {
        motor.set(-speed);
    }

    public void stop() {
        motor.set(0);
    }
}
