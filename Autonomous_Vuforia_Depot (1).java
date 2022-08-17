/* Copyright (c) 2018 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/**
 * This 2018-2019 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the gold and silver minerals.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
 
@Autonomous(name = "Auto_Depot", group = "Autonomous")

public class Autonomous_Vuforia_Depot extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    DcMotor Front_Left;
    DcMotor Front_Right;
    DcMotor Back_Left;
    DcMotor Back_Right;
    DcMotor flip;
    DcMotor liftup;
    
            //Names all motors and servos
    
    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY = "AfTN7Qv/////AAABmbquXbEjOUIyhW5yAiDSqs1pByuNJMipV6u0KWq3+yYU7ubZFVMBe3Ns0pLZmcprOI37r6CBHSHChxWgmtgXNLrLNv+SxF/FxXbpbTN3VVMXfB8o+nzb5NHakk9T9KM2qS9OjzfPcjaDB5Hbv5TEHtNC8hh02TZy68KoXEGyLCg88ix/tFsvlmgvKy2zytBJKCnhVZcy6nFbeHQq5LuCcWicr5tmkId8p1VzdcjaziZZJBcVhzDkUu7eC68ws8WsBhsfH+b3/zn0WA2a2+q8n1ZQWykmGW1Tjn3KQ7twCXjNsFBsASgMyvyilSEcUh8+LZBW6C/rkkdyOw1RfHDyXr6iIprZDPs7sbo4YtYN0SJ8";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;
    public void drive(double Front_Leftpower, double Front_Rightpower, double Back_Leftpower, double Back_Rightpower, int msec)
throws InterruptedException {
        if (opModeIsActive()) {
            Front_Left.setPower(Front_Leftpower);//gets the info to run the left front motor in the drive method above.
            Front_Right.setPower(Front_Rightpower);//gets the info to run the right front motor in the drive method above.
            Back_Left.setPower(Back_Leftpower);//gets the info to run the left rear motor in the drive method above.
            Back_Right.setPower(Back_Rightpower);//gets the info to run the right rear motor in the drive method above. 
            sleep(msec);//sets the sleep for msec in the drive method above.
        }
    }

    @Override
    public void runOpMode()throws InterruptedException {
        telemetry.addData("Status:", "press play to start" );
    Front_Left = hardwareMap.dcMotor.get("frontleft");
    Front_Right = hardwareMap.dcMotor.get("frontright");
    Back_Left = hardwareMap.dcMotor.get("backleft");
    Back_Right = hardwareMap.dcMotor.get("backright"); 
    Front_Left.setDirection(DcMotor.Direction.REVERSE);
    Back_Left.setDirection(DcMotor.Direction.REVERSE);
    //holdmover = hardwareMap.dcMotor.get("holdmover");
    flip = hardwareMap.dcMotor.get("flip");
    flip.setPower(.1);
    liftup = hardwareMap.dcMotor.get("liftup");     
   // spinner = hardwareMap.get(Servo.class, "spinner");
    //spinner.setPosition(.2);
            //Initializes all motors and servos
        
        
        
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("status", "waiting for start command...");
            telemetry.update();
        }

        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                liftup.setPower(1);
                sleep(6700);//descendes Keisha (robot)
                liftup.setPower(0);
                drive(0, 0, 0, 0, 300);
                drive(-.3, .3, -.3, .3, 400);
                drive(0, 0, 0, 0, 100);
                tfod.activate();// turns on Vuforia tracking 
                sleep(500);
                
                }

            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                      telemetry.addData("# Object Detected", updatedRecognitions.size());
                      if (updatedRecognitions.size() == 2) {
                        int goldMineralX = -1;
                        int silverMineral1X = -1;
                        int silverMineral2X = -1;
                        for (Recognition recognition : updatedRecognitions) {
                          if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                          } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                          } else {
                            silverMineral2X = (int) recognition.getLeft();
                          }
                        }
                        if (silverMineral1X != -1 && goldMineralX != -1) {
                         if (goldMineralX < silverMineral1X ) {
                            telemetry.addData("Gold Mineral Position", "Left");
                            //put code to move robot when mineral detected below.
                            tfod.shutdown();
    drive(1, -1, 1, -1, 950);//turns Keisha off the hook
    drive(0, 0, 0, 0, 200);
   // liftup.setPower(-1);
    //sleep(5600);        //Brings lift arm down....maybe take off to save time on auto
    liftup.setPower(0);
    sleep(200);
    drive(0, 0, 0, 0, 200);
    drive(1, 1, 1, 1, 450);//Keisha goes forward
    drive(0, 0, 0, 0, 150);
    drive(-1, 1, 1, -1, 1000);//Keisha Strafes left
    drive(.5, .5, .5, .5, 800);//move mineral forward
    drive(0, 0, 0, 0, 200);
    drive(-.5, -.5, -.5, -.5, 650);//move backwards
    drive(0, 0, 0, 0, 200);
    drive(-1, 1, 1, -1, 1000);//keep strafing left.
    drive(1, -1, 1, -1, 300);//Keisha turns right
    drive(-1, 1, 1, -1, 700); //Keisha strafes left
    drive(1, 1, 1, 1, 1850); //Keisha goes forward
    drive( 0, 0, 0, 0, 200); 
   flip.setPower(-.4);
    sleep(1000);        //lowers keishas bucket
    drive(-.3, -.3, -.3, -.3, 500);
    flip.setPower(.7);
    sleep(800);
    //spinner.setPosition(.7);
    //sleep(500);         //Pushes out extras
    drive(-.85, -1, -.85, -1, 2100); //Keisha goes backwards
    drive(0,0,0,0, 10000);



                            
                          } else if (goldMineralX > silverMineral1X) {
                            telemetry.addData("Gold Mineral Position", "Center");
                            //put code to move robot when mineral detected below.
                            tfod.shutdown();
    drive(1, -1, 1, -1, 950);//turns Keisha off the hook
    drive(0, 0, 0, 0, 200);
    //liftup.setPower(-1);
    //sleep(5600);        //Brings lift arm down....maybe take off to save time on auto
    //liftup.setPower(0);
    //sleep(200);
    drive(0, 0, 0, 0, 200);
    drive(1, 1, 1, 1, 450);//Keisha goes forward
    drive(0, 0, 0, 0, 150);
    //drive(-1, 1, 1, -1, 1350);//Keisha Strafes left...cut down or remove time in order to get in front of center mineral.
    drive(.5, .5, .5, .5, 800);
    drive(0, 0, 0, 0, 200);
    drive(-.5, -.5, -.5, -.5, 700);
    drive(-1, 1, 1, -1, 1900);
    drive(1, -1, 1, -1, 300);//Keisha turns right
    drive(-1, 1, 1, -1, 700); //Keisha strafes left
    drive(1, 1, 1, 1, 1850); //Keisha goes forward
    drive( 0, 0, 0, 0, 200); 
   flip.setPower(-.4);
    sleep(1000);        //lowers keishas bucket
    drive(-.3, -.3, -.3, -.3, 500);
    flip.setPower(.7);
    sleep(800);
    //spinner.setPosition(.7);
    //sleep(500);         //Pushes out extras
    drive(-.85, -1, -.85, -1, 2100); //Keisha goes backwards
    drive(0,0,0,0, 10000);

                            
                            
                          } }
                          if(silverMineral1X != -1 && silverMineral2X != -1){
                          if(silverMineral1X > silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Right");
                            //put code to move robot when mineral detected below.
                            tfod.shutdown();
    drive(1, -1, 1, -1, 950);//turns Keisha off the hook
    drive(0, 0, 0, 0, 200);
    //liftup.setPower(-1);
    //sleep(5600);        //Brings lift arm down....maybe take off to save time on auto
    //liftup.setPower(0);
    //sleep(200);
    //drive(0, 0, 0, 0, 200);
    drive(1, 1, 1, 1, 450);//Keisha goes forward
    drive(0, 0, 0, 0, 150);
    drive(1, -1, -1, 1, 1000);
    drive(.5, .5, .5, .5, 800);
    drive(0, 0, 0, 0, 200);
    drive(-.5, -.5, -.5, -.5, 600);
    drive(0, 0, 0, 0, 200);
    drive(-1, 1, 1, -1, 3000);//Keisha Strafes left...add more time to get to exact or close spot to turn and go to depot!!
    drive(1, -1, 1, -1, 300);//Keisha turns right
    drive(-1, 1, 1, -1, 650); //Keisha strafes left
    drive(1, 1, 1, 1, 1850); //Keisha goes forward
    drive( 0, 0, 0, 0, 200); 
   flip.setPower(-.4);
    sleep(1000);        //lowers keishas bucket
    drive(-.3, -.3, -.3, -.3, 500);
    flip.setPower(.7);
    sleep(800);
    //spinner.setPosition(.7);
    //sleep(500);         //Pushes out extras
    drive(-.85, -1, -.85, -1, 2100); //Keisha goes backwards
    drive(0,0,0,0, 10000);
                            
                            
                          }}
                        
                      }
                      telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
