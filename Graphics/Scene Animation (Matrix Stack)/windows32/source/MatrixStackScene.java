import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MatrixStackScene extends PApplet {

// The tanks are instanced. Buildings are instanced and the city is generated with a simple procedure.
// buildings and tanks are resting flat on the surface of the ground.
// Tank parts rest on surface of each other. 



float time = 0;  // keep track of passing of time

Tank p0,p1;

Shell s;

Explosion e;

Building city[][];


public void setup() {
    // must use OPENGL here !!!
  noStroke();     // do not draw the edges of polygons
  
  p0 = new Tank(650,0,0,0,color(70,135,10));
  
  e = new Explosion(0,0,0);
  
  p1 = new Tank(200,600,PI,PI,color(140,65,15));
//  p1 = new Tank(650,250,PI/2,PI/2,color(140,65,15));
  
  s = new Shell(0,0,0);
  
  city = buildingHelper.generateCity(6);

}

// Draw a scene with a cylinder, a sphere and a box
public void draw() {
  
  resetMatrix();  // set the transformation matrix to the identity (important!)

  background(50,100,200);  // clear the screen to blue
  fill(50,50,50);
  
  // set up for perspective projection
  perspective (PI * 0.333f, 1.0f, 0.01f, 5000.0f);

  
//  if(keyPressed == true){
//    if(key == 'a'){
//      p0.bodyRotation += .025;
//    }
//    if(key == 'd'){
//      p0.bodyRotation -= .025;
//    }
//    if(key == 'w'){
//      p0.drive(1);
//    }
//    if(key == 's'){
//      p0.drive(-1);
//    }
//  }

  animate();

  //p0.turretRotation = p0.bodyRotation;

  
    // change to right-handed coordinate system
    
  scale (1.0f, 1.0f, 1.0f);
  //camera(p0.xPos+40*sin(p0.turretRotation),50,p0.zPos-40*cos(p0.turretRotation),p0.xPos+80*sin(p0.turretRotation),0,p0.zPos+80*cos(p0.turretRotation),0.0,-1.0,0.0);
  
  camera(p0.xPos+40*sin(p0.turretRotation),50,p0.zPos-40*cos(p0.turretRotation), p0.xPos-80*sin(p0.turretRotation),0,p0.zPos+80*cos(p0.turretRotation), 0,-1,0);
  
  // create an ambient light source
  ambientLight(102, 102, 102);
  
  // create two directional light sources
  lightSpecular(200, 200, 200);
  directionalLight(102, 102, 102, -0.7f, -1, .5f);
  
  pushMatrix();
    fill(20,20,20);
    ambient(50,50,50);
    specular(40,40,40);
    shininess(1.0f);
    translate(0,-1,0);
    box(10000,0,10000);
  popMatrix();
  
  pushMatrix();    
    
    p0.drawTank();
    p1.drawTank();
    s.drawShell();
    e.drawExplosion(time);
    
    for (Building row[] : city){
      for (Building building : row){
        building.drawBuilding();
      }
    }

  
  popMatrix();  
  
  
  time += 0.01f;
}


public void animate(){
  
  if(time<1){
    p0.drive(1.5f);
    p0.turretRotation -=.01f;
  }
  else if(time<1.5f){
    p0.turretRotation -=.01f;
  }
  else if(time>1.8f && time<3.3f){
    p0.turretRotation +=.02f;
  }
  else if(time<3.5f){}
  else if(time<4.0f){
    p0.bodyRotation+=.02f;
  }
  else if(time<4.3f){
    p0.drive(1.0f);
    p0.bodyRotation+=.02f;
    p0.turretRotation -=.01f;
  }
  else if (time<5.4f){
    p0.drive(1.5f);
    p0.bodyRotation-=.005f;
    p0.turretRotation -=.008f;
  }
  else if (time<6.5f){
    p0.drive(1.0f);
    p0.bodyRotation-=.01f;
  }
  else if (time<7.0f){
    p0.drive(1.0f);
    p0.turretRotation +=.02f;
    p1.drive(1.0f);
  }
  else if (time<7.5f){
    p0.drive(1.0f);
    p1.drive(1.0f);
    //p0.turretRotation +=.01;
  }  
  else if (time<7.9f){
    p1.drive(1.0f);
    p0.turretRotation+=.005f;
  }  
  else if(time<8.2f){
    p1.drive(1.0f);
  }  
  else if (time<8.25f){
    s.direction = p0.turretRotation;
    s.xPos = p0.xPos-30*sin(s.direction);
    s.zPos = p0.zPos+30*cos(s.direction);
    
    //p0.bodyColor = color(0);
  }
  else if (time<8.44f){
    p1.drive(1.0f);
    s.moveShell(10);
  }
  else if(time<8.46f){
    e.xPos = s.xPos;
    e.zPos = s.zPos;
    e.startTime = time;
    
    //p0.bodyColor = color(0);
  }
  else if(time>8.7f){
    p1.xPos = 0;
    p1.zPos = 0;
    s.xPos = 0;
    s.zPos = 0;
  }

}


// Draw a cylinder of a given radius, height and number of sides.
// The base is on the y=0 plane, and it extends vertically in the y direction.
public void cylinder (float radius, float height, int sides) {
  int i,ii;
  float []c = new float[sides];
  float []s = new float[sides];

  for (i = 0; i < sides; i++) {
    float theta = TWO_PI * i / (float) sides;
    c[i] = cos(theta);
    s[i] = sin(theta);
  }
  
  // bottom end cap
  
  normal (0.0f, -1.0f, 0.0f);
  for (i = 0; i < sides; i++) {
    ii = (i+1) % sides;
    beginShape(TRIANGLES);
    vertex (c[ii] * radius, 0.0f, s[ii] * radius);
    vertex (c[i] * radius, 0.0f, s[i] * radius);
    vertex (0.0f, 0.0f, 0.0f);
    endShape();
  }
  
  // top end cap

  normal (0.0f, 1.0f, 0.0f);
  for (i = 0; i < sides; i++) {
    ii = (i+1) % sides;
    beginShape(TRIANGLES);
    vertex (c[ii] * radius, height, s[ii] * radius);
    vertex (c[i] * radius, height, s[i] * radius);
    vertex (0.0f, height, 0.0f);
    endShape();
  }
  
  // main body of cylinder
  for (i = 0; i < sides; i++) {
    ii = (i+1) % sides;
    beginShape();
    normal (c[i], 0.0f, s[i]);
    vertex (c[i] * radius, 0.0f, s[i] * radius);
    vertex (c[i] * radius, height, s[i] * radius);
    normal (c[ii], 0.0f, s[ii]);
    vertex (c[ii] * radius, height, s[ii] * radius);
    vertex (c[ii] * radius, 0.0f, s[ii] * radius);
    endShape(CLOSE);
  }
}

public void triPrism(float width, float height, float depth){
  beginShape(TRIANGLES);
    vertex(width/2,0,0);
    vertex(width/2,height,0);
    vertex(width/2,0,depth);
  endShape();
  beginShape(TRIANGLES);
    vertex(-width/2,0,0);
    vertex(-width/2,height,0);
    vertex(-width/2,0,depth);
  endShape();
  beginShape(QUADS);
    vertex(width/2,0,0);
    vertex(width/2,height,0);
    vertex(-width/2,height,0);
    vertex(-width/2,0,0);
  endShape();
  beginShape(QUADS);
    vertex(width/2,0,0);
    vertex(width/2,0,depth);
    vertex(-width/2,0,depth);
    vertex(-width/2,0,0);
  endShape();
  beginShape(QUADS);
    vertex(width/2,height,0);
    vertex(width/2,0,depth);
    vertex(-width/2,0,depth);
    vertex(-width/2,height,0);
  endShape();    
}
BuildingHelper buildingHelper = new BuildingHelper();

class Building{
  
  int xWidth, zDepth, yHeight;
  float xPos, zPos;  
  
  float unitWidth = buildingHelper.unitWidth;
  float unitHeight = buildingHelper.unitHeight;
  
  Building(int xW, int yH, int zD, float xP, float zP){
    xWidth = xW;
    zDepth = zD;
    yHeight = yH;
    xPos = xP;
    zPos = zP;
  }
  
  public void drawBuilding(){
    
    float totalWidth = xWidth*unitWidth;
    float totalHeight = yHeight*unitHeight;
    float totalDepth = zDepth*unitWidth;
    
    pushMatrix();
    
    ambient(50,50,50);
    specular(180,180,180);
    shininess(1.0f);
    fill(100,100,100);
    
    translate(xPos, 0.0f, zPos);
    
    //building
    pushMatrix();
      translate(totalWidth/2,totalHeight/2,totalDepth/2);
      
      box(totalWidth,totalHeight+unitHeight/2,totalDepth);    
    popMatrix();
    
    //windows    
    specular(250,250,250);
    shininess(5.0f);
    fill(130,160,200);
    
    for(int i=0; i<xWidth; i++){
      for(int j=0; j<yHeight; j++){
        pushMatrix();
          translate(unitWidth/2+i*unitWidth, .65f*unitHeight+j*unitHeight, totalDepth/2);
          box(unitWidth/2,3*unitHeight/4,totalDepth+2);          
        popMatrix();
//        pushMatrix();
//          translate(unitWidth/2+i*unitWidth, 3*unitHeight/4+j*unitHeight, totalDepth+1);
//          box(unitWidth/2,3*unitHeight/4,2);
//        popMatrix();         
      }
    }
    
    for(int i=0; i<zDepth; i++){
      for(int j=0; j<yHeight; j++){
        pushMatrix();
          translate(totalWidth/2, .65f*unitHeight+j*unitHeight, unitWidth/2+i*unitWidth);
          box(totalWidth+2,3*unitHeight/4,unitWidth/2);          
        popMatrix();
//        pushMatrix();
//          translate(totalWidth+1, 3*unitHeight/4+j*unitHeight, unitWidth/2+i*unitWidth);
//          box(-2,3*unitHeight/4,unitWidth/2); 
//        popMatrix();         
      }
    }
    popMatrix();   
  }
}
class BuildingHelper{
  
  float unitWidth = 30;
  float unitHeight = 40;
  
  public Building[][] generateCity(int numBlocks){
    
    randomSeed(0);
    
    Building[][] city = new Building[numBlocks][numBlocks];
    int bldWidth, bldDepth, strWidth, strDepth;
    float xPos = 0;
    float yPos = 0;
    //city[0][0] = new Building(1,1,1,10,10);
    for(int i=0; i<numBlocks; i++){
      bldWidth = 2+PApplet.parseInt(random(3));
      
      for(int j=0; j<numBlocks; j++){
        
        city[i][j] = new Building(4,1+PApplet.parseInt(random(7)),4,8*unitWidth*i, 8*unitWidth*j);
      }
    }
    
    return city;
  } 
  
}
class Explosion{
  float xPos, zPos, startTime;
  
  Explosion(float x, float z, float time){
    xPos = x;
    zPos = z;
    startTime = time;
  }
  public void drawExplosion(float currentTime){
    fill(200+PApplet.parseInt(random(55)),100+PApplet.parseInt(random(155)),0);
    shininess(0);
    if(currentTime>startTime && currentTime-startTime<.3f){
      pushMatrix();
        translate(xPos, 21, zPos);
        sphere(sqrt((currentTime-startTime)/.3f)*100);
        //sphere(100);
        //sphere((currentTime-startTime)/.3*100);
      popMatrix();
//    pushMatrix();
//    translate(xPos, 21, zPos);
//    sphere(100);
//    popMatrix();
    }

  
  }
}
class Shell{
  float xPos, zPos, direction;
  
  Shell(float x, float z, float d){
    xPos = x;
    zPos = z;
    direction = d;
  }
  
  public void moveShell(float magnitude){
    zPos += magnitude*cos(direction);
    xPos -= magnitude*sin(direction);
  }
  
  public void drawShell(){
    pushMatrix();
    translate(xPos,21,zPos);
    fill(170);
    shininess(10.0f);
    sphere(1.5f);   
    popMatrix();
  }
}
class Tank{
  
  float xPos, zPos, bodyRotation, turretRotation;
  int bodyColor; 
  
  int bodyHeight = 16;
  int turretHeight = 8;
  
  Tank(float x, float z, float bRot, float tRot, int bCol){
    xPos = x;
    zPos = z;
    bodyRotation = bRot;
    turretRotation = tRot;
    bodyColor = bCol;
  }
  
  public void drive(float magnitude){
    zPos += magnitude*cos(bodyRotation);
    xPos -= magnitude*sin(bodyRotation);
  }
  
  public void drawTank(){
    pushMatrix();
    
    ambient(50,50,50);
    specular(180,180,150);
    shininess(4.0f);
    fill(bodyColor);
    
    translate(xPos,bodyHeight/2+6,zPos);
    rotate(-bodyRotation,0.0f,1.0f,0.0f);    
    
    //main body  
    body();
  
    //turret
    turret();     
    
    treads();
    
    popMatrix();
  }
  
  public void body(){
    pushMatrix();
      translate(0.0f,0.0f,5);
      box(30,bodyHeight,40);
    popMatrix();
    pushMatrix();
      translate(0.0f,-bodyHeight/2,25);
      triPrism(30,bodyHeight,14);
    popMatrix();
    pushMatrix();
      translate(0.0f,-bodyHeight/2,-15);
      rotate(PI,0.0f,1.0f,0.0f);      
      triPrism(30,bodyHeight,10);
    popMatrix();
  }
  
  public void turret(){
    pushMatrix();    
      translate(0.0f,bodyHeight/2 + turretHeight/2,0.0f);
      rotate(bodyRotation-turretRotation,0.0f,1.0f,0.0f);
      box(20,turretHeight,20);
    
      pushMatrix();
          translate(0.0f,0.0f,10);
          rotate(PI/2,1.0f,0.0f,0.0f);          
          cylinder(2,20,10);
      popMatrix();
      
      pushMatrix();
          translate(0.0f,0.0f,30);
          rotate(PI/2,1.0f,0.0f,0.0f);          
          cylinder(3,5,15);
      popMatrix();
      
    popMatrix(); 
  }
  
  public void treads(){
    pushMatrix();
      fill(0);
      translate(13,-6,-12);
      rotate(PI/2,0.0f,0.0f,1.0f);
      cylinder(6,26,15);
    popMatrix();
    pushMatrix();
      fill(0);
      translate(13,-6,25);
      rotate(PI/2,0.0f,0.0f,1.0f);
      cylinder(6,26,15);
    popMatrix();
    pushMatrix();
      translate(0,-6,7);
      box(26,12,37);
    popMatrix();
  }
}
  public void settings() {  size(800, 800, OPENGL); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "MatrixStackScene" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
