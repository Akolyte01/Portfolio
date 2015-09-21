class Shell{
  float xPos, zPos, direction;
  
  Shell(float x, float z, float d){
    xPos = x;
    zPos = z;
    direction = d;
  }
  
  void moveShell(float magnitude){
    zPos += magnitude*cos(direction);
    xPos -= magnitude*sin(direction);
  }
  
  void drawShell(){
    pushMatrix();
    translate(xPos,21,zPos);
    fill(170);
    shininess(10.0);
    sphere(1.5);   
    popMatrix();
  }
}