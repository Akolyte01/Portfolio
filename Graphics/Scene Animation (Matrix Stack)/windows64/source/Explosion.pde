class Explosion{
  float xPos, zPos, startTime;
  
  Explosion(float x, float z, float time){
    xPos = x;
    zPos = z;
    startTime = time;
  }
  void drawExplosion(float currentTime){
    fill(200+int(random(55)),100+int(random(155)),0);
    shininess(0);
    if(currentTime>startTime && currentTime-startTime<.3){
      pushMatrix();
        translate(xPos, 21, zPos);
        sphere(sqrt((currentTime-startTime)/.3)*100);
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