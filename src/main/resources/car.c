#include <wiringPi.h>
#include <softPwm.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

int setPWM(int pin, int *currentValue, int finalValue) {
	if (finalValue >= 100) return 1;
	if (*currentValue < finalValue) {
		for (int i = *currentValue; i <= finalValue; i+=5) {
			softPwmWrite (pin, i) ;
			delay (50) ;
		}
	}
	else {
		for (int i = *currentValue; i >= finalValue; i-=5) {
			softPwmWrite (pin, i) ;
			delay (50) ;
		}
	}
	*currentValue = finalValue;
	return 0;
}

int main() {
	
	int  varLeftMotorPWM = 20;
	int* leftMotorPWM;
	leftMotorPWM = &varLeftMotorPWM;

	int  varRightMotorPWM = 20;
	int* rightMotorPWM;
	rightMotorPWM = &varRightMotorPWM;
	
    wiringPiSetup () ;
    pinMode (0, OUTPUT) ;
    pinMode (1, OUTPUT) ;
    pinMode (2, OUTPUT) ;
    pinMode (3, OUTPUT) ;
	pinMode (4, OUTPUT) ;
	pinMode (5, OUTPUT) ;
	
	softPwmCreate (4, *leftMotorPWM, 100) ;
	softPwmCreate (5, *rightMotorPWM, 100) ;


    char message[50] = {0};

    while ( true ) {

        scanf( "%s", message);

        // if the java program send a "end" command (message here) it will break the while
        if ( !strcmp( "end", message ) ) {
            break;
        }

        
        if ( !strcmp( "w", message ) ) {
            digitalWrite(0, HIGH);
            digitalWrite(1, LOW);
            digitalWrite(2, HIGH);
            digitalWrite(3, LOW);
			setPWM(4, leftMotorPWM, 100);
			setPWM(5, rightMotorPWM, 100);
        }
        if ( !strcmp( "d", message ) ) {
            digitalWrite(0, LOW);
            digitalWrite(1, HIGH);
            digitalWrite(2, HIGH);
            digitalWrite(3, LOW);
			setPWM(4, leftMotorPWM, 60);
			setPWM(5, rightMotorPWM, 60);
        }
        if ( !strcmp( "a", message ) ) {
            digitalWrite(0, HIGH);
            digitalWrite(1, LOW);
            digitalWrite(2, LOW);
            digitalWrite(3, HIGH);
			setPWM(4, leftMotorPWM, 60);
			setPWM(5, rightMotorPWM, 60);
        }
        if ( !strcmp( "s", message ) ) {
            digitalWrite(0, LOW);
            digitalWrite(1, HIGH);
            digitalWrite(2, LOW);
            digitalWrite(3, HIGH);
			setPWM(4, leftMotorPWM, 100);
			setPWM(5, rightMotorPWM, 100);
        }
		if ( !strcmp( "q", message ) ) {
            digitalWrite(0, LOW);
            digitalWrite(1, LOW);
            digitalWrite(2, LOW);
            digitalWrite(3, LOW);
			setPWM(4, leftMotorPWM, 0);
			setPWM(5, rightMotorPWM, 0);
        }
		
		
        printf( "command \"%s\" finished\n", message );

        // forces the streams to flush
        fflush( stdout );
        fflush( stderr ); // the java program will not write in the error stream, so this line (for this example) is irrelevant

    }

	free(leftMotorPWM);
	free(rightMotorPWM);
    return 0;
}

