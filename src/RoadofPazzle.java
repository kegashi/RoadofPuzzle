import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;

//JFrame を継承
public class RoadofPazzle extends JFrame{
  public static void main(String args[]){
      RoadofPazzle frame = new RoadofPazzle("RoadofPazzle");//引数はWindow Title
      frame.setVisible(true);
  }

  //constructor. フレームの設定関係を行う
  //初期設定だけしたら後はMainPanelに投げる
  RoadofPazzle(String title){
      setTitle(title);
      setSize(800,600);
      setLocationRelativeTo(null);//初期画面表示位置を中央に
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//CLOSEでプログラム終了

      Container CP = getContentPane();//getContentPane()はJFrameクラスに定義されている
      //CP.setLayout(null);//レイアウトマネージャを停止

      //上部の背景色を橙に設定する
      JPanel panel = new JPanel();
      panel.setBackground(Color.ORANGE);
      CP.add(panel, BorderLayout.NORTH);

      //Mainパネルの作成、フレームへのセット
      MainPanel MP = new MainPanel();
      CP.add(MP);
      //CP.remove(MP);//フレームを外す
      addKeyListener(MP);//KeyListenerをフレームにセット
      //CP.removeKeyListener(MP);//KeyListenerを外す
  }
}
