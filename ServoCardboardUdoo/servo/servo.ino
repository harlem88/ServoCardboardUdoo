  /*
  
   */
#define SERVO_PIN 9   
#define FREQ 40 //~ 50 Hz
#define PWM_W 25 * 1000

int first = 0.7 * 1000;
int end = 3.7 * 1000;
int center = 1.5 * 1000;
  
  void setup() {                
    pinMode(SERVO_PIN, OUTPUT);     
    Serial.begin(115200);
  }
  
  void loop() {
    if (Serial.available() > 0) {
      // get incoming byte:ù
      int in_byte = Serial.read();
        if(in_byte != -1) {
          int value = (in_byte - 48);
          int dir = -1;
          int p_freq;
          switch(value){
            case 1 :
              dir = first;
              p_freq = FREQ;
              break;
            case 2 :
              dir = end;
              p_freq = FREQ;
              break;
            case 0 :
              dir = center;
              p_freq = FREQ/2;
          }
          if(value > -1 && value < 3)
            move_servo(dir, p_freq);
        }
      }
  }
  
void move_servo( int dir, int freq){
  for(int i = 0; i < freq; i++){
    digitalWrite(SERVO_PIN, HIGH);
    delayMicroseconds(dir);
    digitalWrite(SERVO_PIN, LOW);
    delayMicroseconds(PWM_W - dir);
  }
}

