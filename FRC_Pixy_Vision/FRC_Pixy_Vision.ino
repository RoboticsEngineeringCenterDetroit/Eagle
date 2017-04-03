#include <Pixy.h>
#include <PixyI2C.h>
#include <PixySPI_SS.h>
#include <PixyUART.h>
#include <TPixy.h>
#include <SPI.h>

const int INVALID_TARGETS = 3;
const int TURN_LEFT = 4;
const int TURN_RIGHT = 5;
const int CENTERED = 6;
const int SMALL_TURN = 7;

const int MAX_X_AXIS_FOR_SMALL_TURN_RIGHT = 155;
const int MAX_X_AXIS_FOR_BIG_TURN_RIGHT = 145;
const int MAX_X_AXIS_FOR_SMALL_TURN_LEFT = 165;
const int MAX_X_AXIS_FOR_BIG_TURN_LEFT = 175;

Pixy pixy;
int leftBlockIndex;
int rightBlockIndex;

void setup() {
  Serial.begin(9600);

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

  numOfBlocks = pixy.getBlocks();
  Serial.print(numOfBlocks);
  Serial.println(" blocks found");

  if (numOfBlocks == 0 || numOfBlocks == 1) {
    digitalWrite(INVALID_TARGETS, HIGH);
    leftBlockIndex = -1;
    rightBlockIndex = -1;
    return;
  }

  if (numOfBlocks >= 2) {
    sortBlocks(numOfBlocks);

    Serial.print(pixy.blocks[leftBlockIndex].x);
    Serial.print(" ");
    Serial.print(pixy.blocks[rightBlockIndex].x);
    Serial.println();

    if (areBlocksMismatched()) {
      digitalWrite(INVALID_TARGETS, HIGH);
      digitalWrite(TURN_LEFT, LOW);
      digitalWrite(TURN_RIGHT, LOW);
      digitalWrite(CENTERED, LOW);
      digitalWrite(SMALL_TURN, LOW);
      leftBlockIndex = -1;
      rightBlockIndex = -1;
      return;
    }

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
  int averageX = (pixy.blocks[leftBlockIndex].x + pixy.blocks[rightBlockIndex].x) / 2;

  if (averageX > MAX_X_AXIS_FOR_BIG_TURN_RIGHT && averageX < MAX_X_AXIS_FOR_BIG_TURN_LEFT) {
    digitalWrite(INVALID_TARGETS, LOW);
    digitalWrite(TURN_LEFT, LOW);
    digitalWrite(TURN_RIGHT, LOW);
    digitalWrite(CENTERED, LOW);
    digitalWrite(SMALL_TURN, LOW);
  } else if (averageX < MAX_X_AXIS_FOR_BIG_TURN_RIGHT) {
    digitalWrite(INVALID_TARGETS, LOW);
    digitalWrite(TURN_LEFT, LOW);
    digitalWrite(TURN_RIGHT, HIGH);
    digitalWrite(CENTERED, LOW);
    digitalWrite(SMALL_TURN, LOW);
  } else if (averageX < MAX_X_AXIS_FOR_SMALL_TURN_RIGHT) {
    digitalWrite(INVALID_TARGETS, LOW);
    digitalWrite(TURN_LEFT, LOW);
    digitalWrite(TURN_RIGHT, HIGH);
    digitalWrite(CENTERED, LOW);
    digitalWrite(SMALL_TURN, HIGH);
  } else if (averageX > MAX_X_AXIS_FOR_BIG_TURN_LEFT) {
    digitalWrite(INVALID_TARGETS, LOW);
    digitalWrite(TURN_LEFT, HIGH);
    digitalWrite(TURN_RIGHT, LOW);
    digitalWrite(CENTERED, LOW);
    digitalWrite(SMALL_TURN, HIGH);
  } else if (averageX > MAX_X_AXIS_FOR_SMALL_TURN_LEFT) {
    digitalWrite(INVALID_TARGETS, LOW);
    digitalWrite(TURN_LEFT, HIGH);
    digitalWrite(TURN_RIGHT, LOW);
    digitalWrite(CENTERED, LOW);
    digitalWrite(SMALL_TURN, HIGH);
  }
}

