#include <Pixy.h>
#include <SPI.h>

#define DEBUGPRINT(x) (Serial.print(x))
//#define DEBUGPRINT(x) ;

const int INVALID_TARGETS = 2;
const int TURN_LEFT       = 3;
const int CENTERED        = 4;
const int TURN_RIGHT      = 5;
const int SMALL_TURN      = 6;

const int MAX_X_AXIS_FOR_BIG_TURN_RIGHT   = 165;
const int MAX_X_AXIS_FOR_BIG_TURN_LEFT    = 155;

//const int MAX_X_AXIS_FOR_SMALL_TURN_RIGHT = 162;
//const int MAX_X_AXIS_FOR_SMALL_TURN_LEFT  = 158;


Pixy pixy;
int leftBlockIndex;
int rightBlockIndex;

void setup() {
  Serial.begin(115200);
  pixy.init();

  pinMode(INVALID_TARGETS, OUTPUT);
  pinMode(TURN_LEFT, OUTPUT);
  pinMode(TURN_RIGHT, OUTPUT);
  pinMode(CENTERED, OUTPUT);
  pinMode(SMALL_TURN, OUTPUT);

  leftBlockIndex = -1;
  rightBlockIndex = -1;
}

void loop() {
  uint16_t numOfBlocks;
  delay(100);
  numOfBlocks = pixy.getBlocks();
  DEBUGPRINT(numOfBlocks);
  DEBUGPRINT(" blocks found\n");
  for(int idx=0; idx<numOfBlocks; idx++) {
    //pixy.blocks[idx].print();
  }

  if (numOfBlocks == 0 || numOfBlocks == 1) {
    digitalWrite(INVALID_TARGETS, HIGH);
    digitalWrite(TURN_LEFT, LOW);
    digitalWrite(TURN_RIGHT, LOW);
    digitalWrite(CENTERED, LOW);
    digitalWrite(SMALL_TURN, LOW);
    DEBUGPRINT("INVALID_TARGETS\n");
     leftBlockIndex = -1;
    rightBlockIndex = -1;
    return;
  }

  if (numOfBlocks >= 2) {
    sortBlocks(numOfBlocks);

//    if (areBlocksMismatched()) {
//      digitalWrite(INVALID_TARGETS, HIGH);
//      digitalWrite(TURN_LEFT, LOW);
//      digitalWrite(TURN_RIGHT, LOW);
//      digitalWrite(CENTERED, LOW);
//      digitalWrite(SMALL_TURN, LOW);
//      DEBUGPRINT("INVALID_TARGETS\n");
//      leftBlockIndex = -1;
//      rightBlockIndex = -1;
//      return;
//    }

    sendCommand();
  }
}

void sortBlocks(int numOfBlocks) {
  int indexOne;
  int indexTwo;

  for (int i = 0; i < numOfBlocks; i++) {
    int heightOne = 0;
    int heightTwo = 0;

    //find the two tallest blocks
    if (pixy.blocks[i].height > heightOne) {
      heightTwo = heightOne;
      heightOne = pixy.blocks[i].height;
      indexOne = i;
    } else if  (pixy.blocks[i].height > heightTwo) {
      heightTwo = pixy.blocks[i].height;
      indexTwo = i;
    }
  }

  //set blocks to right and left based on their x axis value
  if (pixy.blocks[indexOne].x < pixy.blocks[indexTwo].x) {
    leftBlockIndex = indexOne;
    rightBlockIndex = indexTwo;
  } else {
    leftBlockIndex = indexTwo;
    rightBlockIndex = indexOne;
  }
}

//check if right and left blocks are with 25% variation of size
//25% is a guess, if we get two many flase positives or false negatives we can adjust
boolean areBlocksMismatched() {
  return pixy.blocks[leftBlockIndex].height <= (pixy.blocks[rightBlockIndex].height * 0.75) || pixy.blocks[leftBlockIndex].height >= (pixy.blocks[rightBlockIndex].height * 1.25);

}

void sendCommand() {
  int averageX = (pixy.blocks[0].x + pixy.blocks[1].x) / 2;

  if (averageX > MAX_X_AXIS_FOR_BIG_TURN_RIGHT) {
    digitalWrite(INVALID_TARGETS, LOW);
    digitalWrite(TURN_LEFT, LOW);
    digitalWrite(TURN_RIGHT, HIGH);
    digitalWrite(CENTERED, LOW);
    digitalWrite(SMALL_TURN, LOW);
    DEBUGPRINT("TURN_RIGHT");
  } else if (averageX < MAX_X_AXIS_FOR_BIG_TURN_LEFT) {
    digitalWrite(INVALID_TARGETS, LOW);
    digitalWrite(TURN_LEFT, HIGH);
    digitalWrite(TURN_RIGHT, LOW);
    digitalWrite(CENTERED, LOW);
    digitalWrite(SMALL_TURN, LOW);
    DEBUGPRINT("TURN_LEFT");
  } else {
    digitalWrite(INVALID_TARGETS, LOW);
    digitalWrite(TURN_LEFT, LOW);
    digitalWrite(TURN_RIGHT, LOW);
    digitalWrite(CENTERED, HIGH);
    digitalWrite(SMALL_TURN, LOW);
    DEBUGPRINT("CENTERED");
  }
}

