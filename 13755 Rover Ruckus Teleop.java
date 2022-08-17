import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.motors.TetrixMotor;
import com.qualcomm.hardware.motors.Matrix12vMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.text.SimpleDateFormat;
import java.util.Date;

@TeleOp
public class First_drive extends OpMode {
    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;
    DcMotor mainarm;
    DcMotor liftup ;
    DcMotor extend ;
    DcMotor flip ;
    CRServo spinner;
        //names all motors and servos
    
public void init() {
        telemetry.addData("Status:", "press play to start" );
        frontleft = hardwareMap.dcMotor.get("frontleft");
        frontright = hardwareMap.dcMotor.get("frontright");
        backleft = hardwareMap.dcMotor.get("backleft");
        backright = hardwareMap.dcMotor.get("backright"); 
        frontleft.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.REVERSE);
        mainarm = hardwareMap.dcMotor.get("mainarm");
        liftup = hardwareMap.dcMotor.get("liftup");
        extend = hardwareMap.dcMotor.get("extend");
        flip = hardwareMap.dcMotor.get("flip");
        spinner = hardwareMap.get(CRServo.class, "spinner");
}
        //initializes all motors and servos

public void loop() {
double Speed = (-gamepad1.left_stick_y) * (1.50);
double Turn = -gamepad1.right_stick_x;
double Strafe = -gamepad1.left_stick_x;

double front_left = Speed - Turn - Strafe;
double front_right = Speed + Turn + Strafe;
double back_left = Speed - Turn + Strafe;
double back_right = Speed + Turn - Strafe;
        //programs our mechanum wheels to strafe

frontleft.setPower(front_left);
frontright.setPower(front_right);
backleft.setPower(back_left);
backright.setPower(back_right);
        


if (gamepad2.left_bumper){
mainarm.setPower(.4);
}
else if (gamepad2.right_bumper){
mainarm.setPower(-.75);
}
else{
    mainarm.setPower(-.13);
      //controls arm going up and down
mainarm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);}


if (gamepad2.y){
    flip.setPower(.75);//bucket up
}   
else if (gamepad2.x){
    flip.setPower(-.65);//bucket down
}
else {
    flip.setPower(.16);
}
        //controls bucket 

if (gamepad2.dpad_down){
    extend.setPower(1);
}
else if (gamepad2.dpad_up){
    extend.setPower(-.7);
} 
 else {
     extend.setPower(0);
 }

if (gamepad2.b){
    spinner.setPower(1);
}
else if (gamepad2.a){
    spinner.setPower(-1);
}
else {
    spinner.setPower(0);
    
}

//controls spinner
if (gamepad1.a){
    liftup.setPower(1);
}

else if (gamepad1.b){
    liftup.setPower(-1);
}    
else {
    liftup.setPower(.0);
    }
        //Makes motor part of lift release and pull

    
}
    
    
}
    

