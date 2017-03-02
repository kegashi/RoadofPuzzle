import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable, KeyListener{
	Thread t;
	final Image frame = getToolkit().getImage("frame.jpg"); //背景画像
	final Image clearBoard = getToolkit().getImage("clearboard.png"); //クリア時のボード
	ArrayList<Block> blocks = new ArrayList<Block>();
	int empty = 0;
	boolean stageClear = false;
	boolean resultview = false;
	int stage = 0;
	int move_num = 0;
	
  MainPanel(){
      //スレッドを1つ立てる
      t = new Thread(this);//Thread instance
      t.start();//Thread Start

  }

  //Runnableによるrun() method
  public void run(){

	  //最初のステージのステージ情報を取得する
	  StageInfo stageinfo = new StageInfo();
	  int stageInfo[] = stageinfo.getStageInfo(0);
	  //オブジェクト作成
	  for(int i = 0; i < 36; i++){
		  blocks.add(new Block());
		  blocks.get(i).addStatus(stageInfo[i], i);
		  if(stageInfo[i] == 9){
			  empty = i;
		  }
	  }
      //無限ループでThreadが終了しないようにする
      while(true){
          //矩形の大きさを変更する
          try{
              t.sleep(100);//10s毎に実施
          }catch(InterruptedException e){}

          repaint();
      }
  }

  //JComponentによるpaintComponent method
  //JPanel は JComponent を継承している
  public void paintComponent(Graphics g){
      //赤い矩形を描画する
	  g.drawImage(frame, 20, 0, 600, 600, this); //フレームを描画する
	  for(int i = 0; i< blocks.size(); i++){
		  Block b = blocks.get(i);
		  //System.out.println(b.img + ";X座標" + b.getPositionX()*100 + ";Y座標" + b.getPositionY()*100);
		  if(b.img != null){
			  g.drawImage(b.img,102 + b.getPositionX()*73, 84 + b.getPositionY()*73, 72, 72, this);
		  }
	  }
	  int stage_view = stage + 1;
	  g.drawString("↑: K", 630, 100);
	  g.drawString("↓: J", 630, 120);
	  g.drawString("←: H", 630, 140);
	  g.drawString("→: L", 630, 160);
	  g.drawString("(vimのキーバインドで移動)",630,180);
	  g.drawString("一直線の道を作ろう！",630,200);
	  g.drawString("ステージ: (" + stage_view + "/3)",630,240);
	  g.drawString("移動回数: " + move_num,630,260);
	  if(stageClear){
		  g.drawImage(clearBoard,170,200,300,200,this);
		  g.drawString("ステージ" + stage_view + "クリア", 250, 300);
		  g.drawString("スペースキーで次へ", 250, 320);
	  }
	  if(resultview){
		  g.drawImage(clearBoard,100,150,450,300,this);
		  g.drawString("結果発表",300,250);
		  g.drawString("移動回数: " + move_num,300,280);
		  String rank = "S";
		  if(move_num > 250){
			  rank = "A";
		  }else if(move_num > 400){
			  rank = "B";
		  }else if(move_num > 600){
			  rank = "C";
		  }
		  g.drawString("成績: " + rank,300,300);
	  }
	  
      //g.setColor(Color.red);
	  //g.fillRect(x, y, 20, 20);//引数は(X座標,Y座標,Width,height);
  }

  //ボタン入力が行われた時に呼ばれる. vectorは方向を示す
  public void blockExchanger(int vector){
	  //System.out.println("blockExchanger");
	  Block block = blocks.get(empty);
	  switch(vector){
	  	case 0:
	  		//System.out.println("case0:empty=" + empty);
	  		if(block.getPositionY() != 0 && blocks.get(empty - 6).getMove()){
	  			Block block2 = blocks.get(empty - 6);
	  			doExchange(empty,empty-6);
	  			empty-=6;
	  		}
	  		break;
	  	case 1:
	  		//System.out.println("case1:empty=" + empty);
	  		if(block.getPositionY() != 5 && blocks.get(empty + 6).getMove()){
	  			Block block2 = blocks.get(empty + 6);
	  			doExchange(empty,empty+6);
	  			empty+=6;
	  		}
	  		break;
	  	case 2:
	  		//System.out.println("case2:empty=" + empty);
	  		if(block.getPositionX() != 0 && blocks.get(empty - 1).getMove()){
	  			Block block2 = blocks.get(empty - 1);
	  			doExchange(empty,empty-1);
	  			empty--;
	  		}
	  		break;
	  	case 3:
	  		//System.out.println("case3:empty=" + empty);
	  		if(block.getPositionX() != 5 && blocks.get(empty + 1).getMove()){
	  			Block block2 = blocks.get(empty + 1);
	  			doExchange(empty,empty+1);
	  			empty++;
	  		}
	  		break;
	  }
	  if(isClear()){
		  stageClear = true;
	  }
  }
  
  //2つのブロックのポジションを交換する
  public void doExchange(int block_num, int block2_num){
	  Block tmp = new Block();
	  tmp = blocks.get(block_num);
	  blocks.set(block_num, blocks.get(block2_num));
	  blocks.set(block2_num, tmp);
	  
	  //実装ミスにより、positionも移動しないといけなくなった。
	  blocks.get(block_num).position(block_num);
	  blocks.get(block2_num).position(block2_num);
	  move_num++;
  }
  
  //スタートからゴールまで一直線に繋がっているかを評価する
  public boolean isClear(){
	  int num = 0; 				//今いるブロック番号
	  String vector = "right"; 	//そのブロックの出口
	  while(num != 35){
		  //現在のブロックが出た方向の逆をvector_oppに入れる
		  String vector_opp = opposite(vector);
		  
		  //次のブロックがその方向から侵入可能か調べる
		  int nextBlockNum = nextNum(num, vector);
		  if(nextBlockNum == 99){
			  System.out.println("isClear:" + num + ",(nextBlockNum==99)");
			  return false;
		  }
		  Block block = blocks.get(nextBlockNum);
		  if(block.isDirection(vector_opp)){
			  num = nextBlockNum;
			  vector = block.nextRoute(vector_opp);
		  }else{
			  System.out.println("isClear:" + num + ",(isDirection==false)");
			  return false;
		  }
	  }
	  System.out.println("isClear: " + num);
	  return true;
  }
  
  //(done) 反対の言葉を返す above → under
  public String opposite(String str){
	  switch(str){
	  	case "above":
	  		return "under";
	  	case "under":
	  		return "above";
	  	case "left":
	  		return "right";
	  }
	  return "left";
  }
  
  //(done) 次のブロックの数字を返す 6, under → 12 (6の下は+6で12) また、壁にぶつかったら99を返す
  //冗長的、余裕合ったら数値計算関数と壁衝突判定関数で分けるべき
  public int nextNum(int num, String str){
	  System.out.println("nextNum, num= " + num + ",str= " + str);
	  switch(str){
	  	case "above":
	  		if(num > 6){
	  			return num - 6;
	  		}
	  		break;
	  	case "under":
	  		if(num < 29){
	  			return num + 6;
	  		}
	  		break;
	  	case "left":
	  		if(num%6 != 0){
	  			return num - 1;
	  		}
	  		break;
	  	case "right":
	  		if(num%6 != 5){
	  			return num + 1;
	  		}
	  		break;
	  }
	  return 99;
  }
  
  public void goNextStage(){
	  System.out.println("goNextStage");
	  if(stageClear){
		  if(stage<2){
			  stage++;
			  changeNextStageBlock();
			  stageClear = false;
		  }else{
			  System.out.println("全クリしました！");
			  resultview = true;
			  stageClear = false;
		  }
	  }
  }
  
  public void changeNextStageBlock(){
	  System.out.println("changeNextStageBlock");
	  //ステージのステージ情報を取得する
	  StageInfo stageinfo = new StageInfo();
	  int stageInfo[] = stageinfo.getStageInfo(stage);
	  
	  for(int i = 0; i < 36; i++){
		  blocks.set(i, new Block());
		  blocks.get(i).addStatus(stageInfo[i], i);
		  if(blocks.get(i).isEmpty()){
			  empty = i;
		  }
	  }
  }
  
  /**********************
      KeyEventに関するもの
  **********************/
  //Keyが押された場合
  public void keyPressed(KeyEvent e){
	  if(e.getKeyCode() == KeyEvent.VK_SPACE){
    	  System.out.println("START");
    	  goNextStage();  
	  }else if(stageClear){
		 return;
	  }
      switch(e.getKeyCode()){
      		/*
          case KeyEvent.VK_SPACE:
        	  System.out.println("START");
        	  goNextStage();
        	  break;
        	*/
          case KeyEvent.VK_H:
        	  System.out.println("←");
        	  blockExchanger(2);
        	  break;
          case KeyEvent.VK_J:
        	  System.out.println("↓");
        	  blockExchanger(1);
        	  break;
          case KeyEvent.VK_K:
        	  System.out.println("↑");
        	  blockExchanger(0);
        	  break;
          case KeyEvent.VK_L:
              System.out.println("→");
              blockExchanger(3);
              break;
      }
  }

  //Keyが離された場合
  public void keyReleased(KeyEvent e){}

  //Keyが押された場合
  public void keyTyped(KeyEvent e){}
}