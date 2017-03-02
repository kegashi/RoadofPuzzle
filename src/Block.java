import java.awt.Image;
import javax.swing.ImageIcon;

public class Block {
	private boolean direction[] = {
		false,	//上 
		false,  //下
		false,  //左
		false	//右
	};
	private boolean movable = true;
	private int position = 0;
	private boolean empty = false;
	
	Image img = null;
	
	//(done) 初期化後にステージ情報を元に書き加える ブロックの種類(num)と場所(i)
	public void addStatus(int num, int i){
		ImageIcon icon = null;
		position = i;
		switch(num){
			case 0:
				direction[3] = true;
				movable = false;
				icon = new ImageIcon("block_right.jpg");
				img = icon.getImage();
				break;
			case 1:
				direction[0] = true;
				direction[1] = true;
				icon = new ImageIcon("block_above_under.jpg");
				img = icon.getImage();
				break;
			case 2:
				direction[0] = true;
				direction[2] = true;
				icon = new ImageIcon("block_above_left.jpg");
				img = icon.getImage();
				break;
			case 3:
				direction[0] = true;
				direction[3] = true;
				icon = new ImageIcon("block_above_right.jpg");
				img = icon.getImage();
				break;
			case 4:
				direction[1] = true;
				direction[2] = true;
				icon = new ImageIcon("block_under_left.jpg");
				img = icon.getImage();
				break;
			case 5:
				direction[1] = true;
				direction[3] = true;
				icon = new ImageIcon("block_under_right.jpg");
				img = icon.getImage();
				break;
			case 6:
				direction[2] = true;
				direction[3] = true;
				icon = new ImageIcon("block_left_right.jpg");
				img = icon.getImage();
				break;
			case 7:
				icon = new ImageIcon("block_no.jpg");
				img = icon.getImage();
				break;
			case 8:
				direction[2] = true;
				movable = false;
				icon = new ImageIcon("block_left.jpg");
				img = icon.getImage();
				break;
			case 9:
				empty = true;
				break;
			case 10:
				//動かない岩石ブロックを追加する予定
				movable = false;
				icon = new ImageIcon("no_move.jpg");
				img = icon.getImage();
				break;
		}
	}
	
	//(done) ポジション番号を返す
	public int getPosition(){
		return position;
	}
	
	//(done) ポジションのX座標を返す
	public int getPositionX(){
		int x = position % 6;
		return x;
	}
	
	//(done) 移動可能かどうかを返す
	public boolean getMove(){
		return movable;
	}
	
	//(done) ポジションのY座標を返す
	public int getPositionY(){
		int y = position / 6;
		return y;
	}

	//(done) ポジションを変更する
	public void position(int num){
		position = num;
	} 
	
	//(done) イメージ画像を返す
	public Image getImage(){
		return img;
	}
	
	//(done) その方向が開いているかどうかを返す 例:"above" → true
	public boolean isDirection(String str){
		switch(str){
			case "above":
				return direction[0];
			case "under":
				return direction[1];
			case "left":
				return direction[2];
		}
		return direction[3];
	}
	
	//(done) 空の箱かどうかを返す
	public boolean isEmpty(){
		return empty;
	}
	
	//(done) 入った場所を入力すると次に移動する方向を返す "above" → "under"
	//また、行き先が一つもない場合、"goal"を返す
	public String nextRoute(String str){
		switch(str){
			case "above":
				if(direction[1]){
					return "under";
				}else if(direction[2]){
					return "left";
				}else if(direction[3]){
					return "right";
				}
				break;
			case "under":
				if(direction[0]){
					return "above";
				}else if(direction[2]){
					return "left";
				}else if(direction[3]){
					return "right";
				}
				break;
			case "left":
				if(direction[3]){
					return "right";
				}else if(direction[0]){
					return "above";
				}else if(direction[1]){
					return "under";
				}
				break;
			case "right":
				if(direction[2]){
					return "left";
				}else if(direction[0]){
					return "above";
				}else if(direction[1]){
					return "under";
				}
		}
		return "goal";
	}
}
