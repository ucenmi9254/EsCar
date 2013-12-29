#include <SoftwareSerial.h> 
#include <Servo.h>

SoftwareSerial mySerial(12, 13); // HC-06 RX, TX

#define Motor1Pin1  6 // pin 2 on L293D
#define Motor1Pin2  7 // pin 7 on L293D
#define SERVO       5

Servo myServo;

void setup() { 
  Serial.begin(9600); 
  pinMode(Motor1Pin1, OUTPUT);
  pinMode(Motor1Pin2, OUTPUT);
  mySerial.begin(9600); 
  myServo.attach(SERVO);  // Servo aktif ediliyor
  Duz();
  delay(1000); 
} 
void loop() // Sonsuz dongu
{ 
  if (mySerial.available()){ 
    Serial.println("Veri Almaya Basladi"); 
    delay(300); 
    char alinan = (char)mySerial.read(); 
    Serial.println(alinan); 

    switch (alinan) {
    case 'A': //Duz İleri
      Duz();
      OnGit();
      break;
    case 'B': //Duz Geri
      Duz();
      ArkaGit();
      break;
    case 'C': //Duz Dur
      Duz();
      Dur();
      break;
    case 'D': //Sol İleri
      Sol();
      OnGit();
      break;
    case 'E': //Sol Geri
      Sol();
      ArkaGit();
      break;
    case 'F': //Sol Dur
      Sol();
      Dur();
      break;
    case 'X': //Sag İleri
      Sag();
      OnGit();
      break;
    case 'Y': //Sag Geri
      Sag();
      ArkaGit();
      break;
    case 'Z': //Sag Dur
      Sag();
      Dur();
      break;
    }
  }
}

void Sag(){
  mySerial.println("Sag");
  myServo.write(90);
}

void Sol(){
  mySerial.println("Sol");
  myServo.write(22);
}

void Duz(){
  mySerial.println("Duz");
  myServo.write(62);
}

void OnGit(){
  mySerial.println("On");
  digitalWrite(Motor1Pin1, HIGH);
  digitalWrite(Motor1Pin2, LOW);
}

void ArkaGit(){
  mySerial.println("Arka");
  digitalWrite(Motor1Pin1, LOW);
  digitalWrite(Motor1Pin2, HIGH);
}

void Dur(){
  mySerial.println("Dur");
  digitalWrite(Motor1Pin1, LOW);
  digitalWrite(Motor1Pin2, LOW);
}


