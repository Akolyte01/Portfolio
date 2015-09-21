class Tank{
  
  float xPos, zPos, bodyRotation, turretRotation;
  color bodyColor; 
  
  int bodyHeight = 16;
  int turretHeight = 8;
  
  Tank(float x, float z, float bRot, float tRot, color bCol){
    xPos = x;
    zPos = z;
    bodyRotation = bRot;
    turretRotation = tRot;
    bodyColor = bCol;
  }
  
  void drive(float magnitude){
    zPos += magnitude*cos(bodyRotation);
    xPos -= magnitude*sin(bodyRotation);
  }
  
  void drawTank(){
    pushMatrix();
    
    ambient(50,50,50);
    specular(180,180,150);
    shininess(4.0);
    fill(bodyColor);
    
    translate(xPos,bodyHeight/2+6,zPos);
    rotate(-bodyRotation,0.0,1.0,0.0);    
    
    //main body  
    body();
  
    //turret
    turret();     
    
    treads();
    
    popMatrix();
  }
  
  void body(){
    pushMatrix();
      translate(0.0,0.0,5);
      box(30,bodyHeight,40);
    popMatrix();
    pushMatrix();
      translate(0.0,-bodyHeight/2,25);
      triPrism(30,bodyHeight,14);
    popMatrix();
    pushMatrix();
      translate(0.0,-bodyHeight/2,-15);
      rotate(PI,0.0,1.0,0.0);      
      triPrism(30,bodyHeight,10);
    popMatrix();
  }
  
  void turret(){
    pushMatrix();    
      translate(0.0,bodyHeight/2 + turretHeight/2,0.0);
      rotate(bodyRotation-turretRotation,0.0,1.0,0.0);
      box(20,turretHeight,20);
    
      pushMatrix();
          translate(0.0,0.0,10);
          rotate(PI/2,1.0,0.0,0.0);          
          cylinder(2,20,10);
      popMatrix();
      
      pushMatrix();
          translate(0.0,0.0,30);
          rotate(PI/2,1.0,0.0,0.0);          
          cylinder(3,5,15);
      popMatrix();
      
    popMatrix(); 
  }
  
  void treads(){
    pushMatrix();
      fill(0);
      translate(13,-6,-12);
      rotate(PI/2,0.0,0.0,1.0);
      cylinder(6,26,15);
    popMatrix();
    pushMatrix();
      fill(0);
      translate(13,-6,25);
      rotate(PI/2,0.0,0.0,1.0);
      cylinder(6,26,15);
    popMatrix();
    pushMatrix();
      translate(0,-6,7);
      box(26,12,37);
    popMatrix();
  }
}