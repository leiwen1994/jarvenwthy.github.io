float PADDLE_Y;
float PADDLE_X;
int PADDLE_HEIGHT=10;
int PADDLE_WIDTH=150;
int c=5;
float BALL_X=400;
float BALL_Y=780;
float BALL_SPEED_Y=4;
float BALL_SPEED_X=4;
int BALL_SIZE=30;
float boundary=800;
int i, j;
float tempx, tempy;
int bfill, brickw, brickh;
boolean[][]brick=new boolean[10][5];


void setup()
{
  size(800, 800);
  frameRate(60);
  //noLoop();
  noCursor();
  brickw=80;
  brickh=40;
  for (i=0; i<10; i++)
  {
    for (j=0; j<5; j++)
    {
      brick[i][j]=true;
    }
  }
}

void draw()
{

  background(70);
  PADDLE();
  bouncingball();
  brick();
  brickcollision();

  fill(255);
  textSize(40);
  text("Life "+(c-1), 670, 270);

  println("PADDLE_X: "+PADDLE_X+",PADDLE_Y: "+PADDLE_Y+"   "+"BALLX: "+BALL_X+" BALLY: "+BALL_Y + "   VX: "+BALL_SPEED_X+" VY: "+BALL_SPEED_Y+bfill+"   tempx:"+tempx + " tempy: "+tempy);
  //stop game when no life left
  if (c==0)
  {
    noLoop();
    fill(255);
    textSize(50);
    text("GAME OVER", 270, 400);
    textSize(50);
    text("Press SPACE to Restart", 130, 500);
  }
  //stop game when all bricks hit
  if (rem==50)
  {
    noLoop();
    fill(255);
    textSize(40);
    text("You Win !", 300, 300);
    text("Press Space to Restart", 180, 400);
  }
}

void PADDLE()
{
  PADDLE_Y=750;
  PADDLE_X=mouseX;
  fill(#B96DA8);
  stroke(0);
  strokeWeight(4);
  rect(PADDLE_X-PADDLE_WIDTH/2, PADDLE_Y-PADDLE_HEIGHT/2, PADDLE_WIDTH, PADDLE_HEIGHT);
  fill(255);
  ellipse(PADDLE_X, PADDLE_Y, 10, 10);
}

void bouncingball()
{
  BALL_X=BALL_X+(BALL_SPEED_X);
  BALL_Y=BALL_Y+(BALL_SPEED_Y);

  //bounce wall
  if (BALL_X+BALL_SIZE/2>boundary || BALL_X-BALL_SIZE/2<0)
  {
    BALL_SPEED_X *=-1;
  } else if ( BALL_Y-BALL_SIZE/2<0 )
  {
    BALL_SPEED_Y *=-1;
  }
  //bounce paddle and change x speed
  else if (BALL_Y>=750-PADDLE_HEIGHT/2-BALL_SIZE/2 && BALL_Y+BALL_SIZE/2<790 && BALL_X>=PADDLE_X-PADDLE_WIDTH/2 && BALL_X<=PADDLE_X+PADDLE_WIDTH/2)
  {
    BALL_SPEED_Y *=-1;

    if (BALL_X>PADDLE_X)
    {
      BALL_SPEED_X = BALL_SPEED_X+(dist(BALL_X, BALL_Y, PADDLE_X+PADDLE_WIDTH/2, PADDLE_Y)/35);
    } else if ( BALL_X<PADDLE_X)
    {
      BALL_SPEED_X = BALL_SPEED_X-(dist(BALL_X, BALL_Y, PADDLE_X+PADDLE_WIDTH/2, PADDLE_Y)/35);
    }
  } 
  //stop when hit bottom
  else if (BALL_Y+BALL_SIZE/2>790)
  {
    BALL_SPEED_X=0;
    BALL_SPEED_Y=0;
    textSize(50);
    text("Click Mouse to Start", 145, 400);
  }

  fill(255);
  ellipse(BALL_X, BALL_Y, BALL_SIZE, BALL_SIZE);
}

//deduct life and activate ball
void mousePressed()
{

  if (BALL_Y+BALL_SIZE/2>790 && c>=0)
  {
    BALL_X=PADDLE_X;
    BALL_Y=750-PADDLE_HEIGHT/2-BALL_SIZE/2;
    BALL_SPEED_X=0;
    BALL_SPEED_Y=-8;
    c=c-1;
    loop();
  }
}

//restart, and see ending screen when h pressed
void keyPressed()
{
  if ((key==' '&&c<=0))
  {
    loop();
    c=5;
    for (i=0; i<10; i++)
    {
      for (j=0; j<5; j++)
      {
        brick[i][j]=true;
      }
    }
  } else if (key==' '&& rem==50)
  {
    rem=0;
    BALL_X=480;
    BALL_Y=780;
    loop();
    c=5;
  }

  if (key=='h')
  {
    rem=50;
  }
}

void brick()
{

  for (i=0; i<10; i++)
  {
    for (j=0; j<5; j++)
    {
      if (brick[i][j])
      {
        fill(#64C1C0);
        stroke(225);
        rect(80*i, 40*j, 80, 40);
      }
    }
  }
}
int rem=0;

void brickcollision()
{
  for (i=0; i<10; i++)
  {
    for (j=0; j<5; j++)
    {
      if (brick[i][j])
      {
        if (BALL_X>i*80 && BALL_X<i*80+80 && BALL_Y <= j*40+40 && BALL_Y>=j*40)
        {
          brick[i][j]=false;
          BALL_SPEED_Y*=-1;
          rem++;
        } else if ((BALL_X>i*80-1 && BALL_X<i*80+1 && BALL_Y <= j*40+40 && BALL_Y>=j*40) )
        {
          brick[i][j]=false;
          BALL_SPEED_X*=-1;
          rem++;
        }
      }
    }
  }
}