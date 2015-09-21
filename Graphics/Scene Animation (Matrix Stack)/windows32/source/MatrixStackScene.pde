// The tanks are instanced. Buildings are instanced and the city is generated with a simple procedure.
// buildings and tanks are resting flat on the surface of the ground.
// Tank parts rest on surface of each other. 

import processing.opengl.*;

float time = 0;  // keep track of passing of time

Tank p0,p1;

Shell s;

Explosion e;

Building city[][];


void setup() {
  size(800, 800, OPENGL);  // must use OPENGL here !!!
  noStroke();     // do not draw the edges of polygons
  
  p0 = new Tank(650,0,0,0,color(70,135,10));
  
  e = new Explosion(0,0,0);
  
  p1 = new Tank(200,600,PI,PI,color(140,65,15));
//  p1 = new Tank(650,250,PI/2,PI/2,color(140,65,15));
  
  s = new Shell(0,0,0);
  
  city = buildingHelper.generateCity(6);

}

// Draw a scene with a cylinder, a sphere and a box
void draw() {
  
  resetMatrix();  // set the transformation matrix to the identity (important!)

  background(50,100,200);  // clear the screen to blue
  fill(50,50,50);
  
  // set up for perspective projection
  perspective (PI * 0.333, 1.0, 0.01, 5000.0);

  
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
    
  scale (1.0, 1.0, 1.0);
  //camera(p0.xPos+40*sin(p0.turretRotation),50,p0.zPos-40*cos(p0.turretRotation),p0.xPos+80*sin(p0.turretRotation),0,p0.zPos+80*cos(p0.turretRotation),0.0,-1.0,0.0);
  
  camera(p0.xPos+40*sin(p0.turretRotation),50,p0.zPos-40*cos(p0.turretRotation), p0.xPos-80*sin(p0.turretRotation),0,p0.zPos+80*cos(p0.turretRotation), 0,-1,0);
  
  // create an ambient light source
  ambientLight(102, 102, 102);
  
  // create two directional light sources
  lightSpecular(200, 200, 200);
  directionalLight(102, 102, 102, -0.7, -1, .5);
  
  pushMatrix();
    fill(20,20,20);
    ambient(50,50,50);
    specular(40,40,40);
    shininess(1.0);
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
  
  
  time += 0.01;
}


void animate(){
  
  if(time<1){
    p0.drive(1.5);
    p0.turretRotation -=.01;
  }
  else if(time<1.5){
    p0.turretRotation -=.01;
  }
  else if(time>1.8 && time<3.3){
    p0.turretRotation +=.02;
  }
  else if(time<3.5){}
  else if(time<4.0){
    p0.bodyRotation+=.02;
  }
  else if(time<4.3){
    p0.drive(1.0);
    p0.bodyRotation+=.02;
    p0.turretRotation -=.01;
  }
  else if (time<5.4){
    p0.drive(1.5);
    p0.bodyRotation-=.005;
    p0.turretRotation -=.008;
  }
  else if (time<6.5){
    p0.drive(1.0);
    p0.bodyRotation-=.01;
  }
  else if (time<7.0){
    p0.drive(1.0);
    p0.turretRotation +=.02;
    p1.drive(1.0);
  }
  else if (time<7.5){
    p0.drive(1.0);
    p1.drive(1.0);
    //p0.turretRotation +=.01;
  }  
  else if (time<7.9){
    p1.drive(1.0);
    p0.turretRotation+=.005;
  }  
  else if(time<8.2){
    p1.drive(1.0);
  }  
  else if (time<8.25){
    s.direction = p0.turretRotation;
    s.xPos = p0.xPos-30*sin(s.direction);
    s.zPos = p0.zPos+30*cos(s.direction);
    
    //p0.bodyColor = color(0);
  }
  else if (time<8.44){
    p1.drive(1.0);
    s.moveShell(10);
  }
  else if(time<8.46){
    e.xPos = s.xPos;
    e.zPos = s.zPos;
    e.startTime = time;
    
    //p0.bodyColor = color(0);
  }
  else if(time>8.7){
    p1.xPos = 0;
    p1.zPos = 0;
    s.xPos = 0;
    s.zPos = 0;
  }

}


// Draw a cylinder of a given radius, height and number of sides.
// The base is on the y=0 plane, and it extends vertically in the y direction.
void cylinder (float radius, float height, int sides) {
  int i,ii;
  float []c = new float[sides];
  float []s = new float[sides];

  for (i = 0; i < sides; i++) {
    float theta = TWO_PI * i / (float) sides;
    c[i] = cos(theta);
    s[i] = sin(theta);
  }
  
  // bottom end cap
  
  normal (0.0, -1.0, 0.0);
  for (i = 0; i < sides; i++) {
    ii = (i+1) % sides;
    beginShape(TRIANGLES);
    vertex (c[ii] * radius, 0.0, s[ii] * radius);
    vertex (c[i] * radius, 0.0, s[i] * radius);
    vertex (0.0, 0.0, 0.0);
    endShape();
  }
  
  // top end cap

  normal (0.0, 1.0, 0.0);
  for (i = 0; i < sides; i++) {
    ii = (i+1) % sides;
    beginShape(TRIANGLES);
    vertex (c[ii] * radius, height, s[ii] * radius);
    vertex (c[i] * radius, height, s[i] * radius);
    vertex (0.0, height, 0.0);
    endShape();
  }
  
  // main body of cylinder
  for (i = 0; i < sides; i++) {
    ii = (i+1) % sides;
    beginShape();
    normal (c[i], 0.0, s[i]);
    vertex (c[i] * radius, 0.0, s[i] * radius);
    vertex (c[i] * radius, height, s[i] * radius);
    normal (c[ii], 0.0, s[ii]);
    vertex (c[ii] * radius, height, s[ii] * radius);
    vertex (c[ii] * radius, 0.0, s[ii] * radius);
    endShape(CLOSE);
  }
}

void triPrism(float width, float height, float depth){
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