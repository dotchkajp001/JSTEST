package org.opengion.penguin.math.statistics;

/**
 * 独自実装の二次回帰計算クラスです。
 * f(x) = c1x^2 + c2x + c3
 * の曲線を求めます。
 */
public class HybsSquadraticRegression implements HybsSingleRegression{
	private double c1;		// ２次の係数
	private double c2;		// １次の係数
	private double c3;		// ０次の係数
	private double rsquare;		// 決定係数 今のところ求めていない
		
	/**
	 * コンストラクタ。
	 * 与えた二次元データを元に二次回帰を計算します。
	 * @param data xとyの組み合わせの配列
	 */
	public HybsSquadraticRegression(final double[][] data){
		//二次回帰曲線を求めるが、これはapacheにはなさそうなので自前で計算する。
		train(data);
	}
	
	/**
	 * コンストラクタ。
	 * 係数を与えます。
	 * （以前に計算したものを利用）
	 * @param c1 ２次の係数
	 * @param c2 １次の係数
	 * @param c3 ０次の係数
	 * 
	 */
	public HybsSquadraticRegression( final double c1, final double c2, final double c3 ){
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
	}
	
	/**
	 * コンストラクタ
	 * このコンストラクタを利用した場合はtrainを実施して学習するか、setCoefficientで係数をセットする。
	 */
	public HybsSquadraticRegression(){
		//何もしない
	}
	
	/**
	 * 係数計算
	 * 
	 * 
	 *	c3Σ＋c2Σx＋c1Σx^2＝Σy
	 *	c3Σx＋c2Σ(x^2)＋c1Σx^3＝Σ(xy)
	 *	c3Σ(x^2)＋c2Σ(x^3)＋c1Σ(x^4)＝Σ(ｘ^2*y)
	 *	この三元連立方程式を解くことになる。
	 *
	 * @param data x,yの配列
	 */
	public void train( final double[][] data ){
		// xの二乗等の総和用
		int data_n=data.length;;
		double data_x=0;
		double data_y=0;
		double x2=0;
		double sumx2=0;
		double sumx=0;
		double sumxy=0;
		double sumy=0;
		double sumx3=0;
		double sumx2y=0;
		double sumx4=0;
		
		// まずは計算に使うための和を計算
		for( int i=0; i < data_n; i++ ){
			data_x = data[i][0];
			data_y = data[i][1];
			x2 = data_x*data_x;
			
			sumx	+= data_x;
			sumx2	+= x2;
			sumxy	+= data_x * data_y;
			sumy	+= data_y;
			sumx3	+= x2 * data_x;
			sumx2y	+= x2 * data_y;
			sumx4	+= x2 * x2;
		}
		
		// ガウス・ジョルダン法で係数計算
		double diffx2 = sumx2 - sumx * sumx / data_n;
		double diffxy = sumxy - sumx * sumy / data_n;
		double diffx3 = sumx3 - sumx2 * sumx /data_n;
		double diffx2y = sumx2y - sumx2 * sumy /data_n;
		double diffx4 = sumx4 - sumx2 * sumx2 /data_n;
		double diffd = diffx2 * diffx4 - diffx3 * diffx3;
		c1 = ( diffx2y * diffx2 - diffxy * diffx3 ) / diffd;
		c2 = ( diffxy * diffx4 - diffx2y * diffx3 ) / diffd;
		c3 = sumy/data_n - c2*sumx/ data_n - c1*sumx2/data_n;
		
	}
	
	/**
	 * このクラスでは未使用。
	 * 
	 * @param opt オプション
	 */
	public void setOption(final double[] opt){
		// 特にオプションなし
	}
	
	/**
	 * 係数c1の取得。
	 * @return 係数c1
	 */
	public double getC1(){
		return c1;
	}
	
	/**
	 * 係数c2の取得。
	 * @return 係数c2
	 */
	public double getC2(){
		return c2;
	}
	
	/**
	 * 係数c3取得。
	 * @return 係数c3
	 */
	public double getC3(){
		return c3;
	}
	
	/**
	 * c1,c2,c3の順にセットした配列を返します。
	 * @return 係数の配列
	 */
	public double[] getCoefficient(){
		double[] rtn = {c1,c2,c3};
		return rtn;
	}
	
	/**
	 * c1,c2,c3の順に配列の内容をセットします。
	 * 
	 * @param in_c 係数配列
	 */
	public void setCoefficient(final double[] in_c){
		c1 = in_c[0];
		c2 = in_c[1];
		c3 = in_c[2];
	}
	
	/**
	 * c1*x^2 + c2*x + c3を計算
	 * @param in_x 与えるx
	 * @return 計算結果
	 */
	public double predict(final double in_x){
		return c1 * in_x * in_x + c2 * in_x + c3;
	}

	/*** ここまでが本体 ***/
	/*** ここからテスト用mainメソッド ***/
	/**
	 * @param args *****************************************/
	public static void main(final String [] args) {
		double[][] data = {{1, 2.3}, {2, 5.1}, {3, 9.1}, {4, 16.2}}; 
		
		
		HybsSquadraticRegression sr = new HybsSquadraticRegression(data);
		System.out.println(sr.getC1());
		System.out.println(sr.getC2());
		System.out.println(sr.getC3());
		
		System.out.println(sr.predict( 5 ));
	}
}

