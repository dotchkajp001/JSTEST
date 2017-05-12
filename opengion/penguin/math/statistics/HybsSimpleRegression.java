package org.opengion.penguin.math.statistics;

import org.apache.commons.math3.stat.regression.SimpleRegression;

/**
 * apache.commons.mathを利用した線形単回帰計算のクラスです。
 * f(x)=ax+bの形で線形回帰を行います。
 */
public class HybsSimpleRegression implements HybsSingleRegression {
	private double slope;		// 傾き
	private double intercept;	// 切片
	private double rsquare;		// 決定係数
		
	/**
	 * コンストラクタ。
	 * 与えた二次元データを元に回帰直線を計算します。
	 * {x,y}の配列でデータを与えます。
	 * @param data xとyの組み合わせの配列
	 */
	public HybsSimpleRegression(final double[][] data){
		// ここで単回帰計算
		train(data);
	}
	
	/**
	 * コンストラクタ。
	 * 計算後の傾き、切片を与えられるようにしておきます。
	 * （以前に計算したものを利用）
	 * @param slope 
	 * @param intercept 
	 * 
	 */
	public HybsSimpleRegression( final double slope, final double intercept ){
		this.slope = slope;
		this.intercept = intercept;
	}
	
	/**
	 * コンストラクタ
	 * このコンストラクタを利用した場合はtrainを実施して学習するか、setCoefficientで係数をセットする。
	 */
	public HybsSimpleRegression(){
		//何もしない
	}
	
	/**
	 * dataを与えて回帰直線を求める。
	 * 
	 * @param data x,yの配列
	 */
	public void train(final double[][] data){
		SimpleRegression regression = new SimpleRegression();
		regression.addData(data);
		slope = regression.getSlope();
		intercept = regression.getIntercept();
		rsquare = regression.getRSquare();
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
	 * 傾きの取得。
	 * @return 傾き
	 */
	public double getSlope(){
		return slope;
	}
	
	/**
	 * 切片の取得。
	 * @return 切片
	 */
	public double getIntercept(){
		return intercept;
	}
	
	/**
	 * 決定係数の取得。
	 * @return 決定係数
	 */
	public double getRSquare(){
		return rsquare;
	}
	
	/**
	 * 傾き、切片、決定係数の順にセットした配列を返します。
	 * @return 係数の配列
	 */
	public double[] getCoefficient(){
		double[] rtn = {slope,intercept,rsquare};
		return rtn;
	}
	
	/**
	 * 傾き、切片の順に配列の内容をセットします。
	 * 3番目に決定係数が入っていても無視します。
	 * 
	 * @param c 係数配列
	 */
	public void setCoefficient(final double[] c){
		slope = c[0];
		intercept = c[1];
	}
	
	/**
	 * a + bxを計算。
	 * @param x 変数X
	 * @return 計算結果
	 */
	public double predict(final double x){
		return intercept + slope * x;
	}

	/*** ここまでが本体 ***/
	/*** ここからテスト用mainメソッド ***/
	/**
	 * @param args *****************************************/
	public static void main(final String [] args) {
		double[][] data = {{1, 2.3}, {2, 3.4}, {3, 6.1}, {4, 8.2}}; 
		
		
		HybsSimpleRegression sr = new HybsSimpleRegression(data);
		System.out.println(sr.getIntercept());
		System.out.println(sr.getSlope());
		System.out.println(sr.getRSquare());
		
		System.out.println(sr.predict( 5 ));
	}
}

