//#define USE_PWM
#define HAS_WIRING

#ifdef HAS_WIRING
#include <wiringPi.h>
#include <softPwm.h>
#endif

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

#ifdef HAS_WIRING
    #ifdef USE_PWM
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
    #endif
#endif

int main() {

	#ifdef HAS_WIRING
        wiringPiSetup () ;
        pinMode (0, OUTPUT) ;
        pinMode (1, OUTPUT) ;
        pinMode (2, OUTPUT) ;
        pinMode (3, OUTPUT) ;
        pinMode (4, OUTPUT) ;
        pinMode (5, OUTPUT) ;

        #ifdef USE_PWM
            int  varLeftMotorPWM = 20;
            int* leftMotorPWM;
            leftMotorPWM = &varLeftMotorPWM;

            int  varRightMotorPWM = 20;
            int* rightMotorPWM;
            rightMotorPWM = &varRightMotorPWM;

            softPwmCreate (4, *leftMotorPWM, 100) ;
            softPwmCreate (5, *rightMotorPWM, 100) ;
        #endif
    #endif

    char message[50] = {0};

    while ( true ) {

        scanf( "%s", message);

        // if the java program send a "end" command (message here) it will break the while
        if ( !strcmp( "end", message ) ) {
            break;
        }

        #ifdef HAS_WIRING
            if ( !strcmp( "w", message ) ) {
                digitalWrite(0, HIGH);
                digitalWrite(1, LOW);
                digitalWrite(2, HIGH);
                digitalWrite(3, LOW);
                #ifdef USE_PWM
                    setPWM(4, leftMotorPWM, 100);
                    setPWM(5, rightMotorPWM, 100);
                #endif
            }
            if ( !strcmp( "d", message ) ) {
                digitalWrite(0, LOW);
                digitalWrite(1, HIGH);
                digitalWrite(2, HIGH);
                digitalWrite(3, LOW);
                #ifdef USE_PWM
                    setPWM(4, leftMotorPWM, 60);
                    setPWM(5, rightMotorPWM, 60);
                #endif
            }
            if ( !strcmp( "a", message ) ) {
                digitalWrite(0, HIGH);
                digitalWrite(1, LOW);
                digitalWrite(2, LOW);
                digitalWrite(3, HIGH);
                #ifdef USE_PWM
                    setPWM(4, leftMotorPWM, 60);
                    setPWM(5, rightMotorPWM, 60);
                #endif
            }
            if ( !strcmp( "s", message ) ) {
                digitalWrite(0, LOW);
                digitalWrite(1, HIGH);
                digitalWrite(2, LOW);
                digitalWrite(3, HIGH);
                #ifdef USE_PWM
                    setPWM(4, leftMotorPWM, 100);
                    setPWM(5, rightMotorPWM, 100);
                #endif
            }
            if ( !strcmp( "q", message ) ) {
                digitalWrite(0, LOW);
                digitalWrite(1, LOW);
                digitalWrite(2, LOW);
                digitalWrite(3, LOW);
                #ifdef USE_PWM
                    setPWM(4, leftMotorPWM, 0);
                    setPWM(5, rightMotorPWM, 0);
                #endif
            }
		#endif
		
        printf( "command \"%s\" finished\n", message );

        // forces the streams to flush
        fflush( stdout );
        fflush( stderr ); // the java program will not write in the error stream, so this line (for this example) is irrelevant

    }

    #ifdef HAS_WIRING
        #ifdef USE_PWM
            free(leftMotorPWM);
            free(rightMotorPWM);
        #endif
    #endif
    return 0;
}

