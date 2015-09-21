class BuildingHelper{
  
  float unitWidth = 30;
  float unitHeight = 40;
  
  Building[][] generateCity(int numBlocks){
    
    randomSeed(0);
    
    Building[][] city = new Building[numBlocks][numBlocks];
    int bldWidth, bldDepth, strWidth, strDepth;
    float xPos = 0;
    float yPos = 0;
    //city[0][0] = new Building(1,1,1,10,10);
    for(int i=0; i<numBlocks; i++){
      bldWidth = 2+int(random(3));
      
      for(int j=0; j<numBlocks; j++){
        
        city[i][j] = new Building(4,1+int(random(7)),4,8*unitWidth*i, 8*unitWidth*j);
      }
    }
    
    return city;
  } 
  
}