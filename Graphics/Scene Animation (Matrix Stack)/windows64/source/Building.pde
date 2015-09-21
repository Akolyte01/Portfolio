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
  
  void drawBuilding(){
    
    float totalWidth = xWidth*unitWidth;
    float totalHeight = yHeight*unitHeight;
    float totalDepth = zDepth*unitWidth;
    
    pushMatrix();
    
    ambient(50,50,50);
    specular(180,180,180);
    shininess(1.0);
    fill(100,100,100);
    
    translate(xPos, 0.0, zPos);
    
    //building
    pushMatrix();
      translate(totalWidth/2,totalHeight/2,totalDepth/2);
      
      box(totalWidth,totalHeight+unitHeight/2,totalDepth);    
    popMatrix();
    
    //windows    
    specular(250,250,250);
    shininess(5.0);
    fill(130,160,200);
    
    for(int i=0; i<xWidth; i++){
      for(int j=0; j<yHeight; j++){
        pushMatrix();
          translate(unitWidth/2+i*unitWidth, .65*unitHeight+j*unitHeight, totalDepth/2);
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
          translate(totalWidth/2, .65*unitHeight+j*unitHeight, unitWidth/2+i*unitWidth);
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