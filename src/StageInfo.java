
//0→start, 1→above_under, 2→above_left, 3→above_right,
//4→under_left, 5→under_right, 6→left_right,
//7→no, 8→goal, 9→ブロックなし, 10→動かないブロック

public class StageInfo {
	public final int stageInfo[][] = {
			{
				//ステージ1
				0,4,7,10,10,10,
				7,1,7,10,10,10,
				7,1,7,10,10,10,
				1,7,7,10,10,10,
				7,1,6,7,6,7,
				7,7,3,6,9,8
			},{
				//ステージ2
				0,3,4,7,4,1,
				7,5,6,7,7,2,
				10,4,7,10,1,7,
				7,10,10,10,2,7,
				7,3,7,7,7,3,
				7,5,7,7,9,8
			},{
				//0→start, 1→above_under, 2→above_left, 3→above_right,
				//4→under_left, 5→under_right, 6→left_right,
				//7→no, 8→goal, 9→ブロックなし, 10→動かないブロック
				//ステージ3
				0,7,6,6,6,6,
				7,7,10,10,10,4,
				7,10,6,6,2,1,
				7,5,5,10,10,7,
				7,2,10,10,6,7,
				1,3,6,6,9,8
			},{
				//0→start, 1→above_under, 2→above_left, 3→above_right,
				//4→under_left, 5→under_right, 6→left_right,
				//7→no, 8→goal, 9→ブロックなし, 10→動かないブロック
				//ステージ3
				0,7,6,6,6,6,
				7,7,10,10,10,4,
				7,10,6,6,2,1,
				7,5,5,10,10,7,
				7,2,10,10,6,7,
				1,3,6,6,9,8
			}
	};
	
	public int[] getStageInfo(int num){
		return stageInfo[num];
	}
}