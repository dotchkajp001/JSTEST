package org.opengion.penguin.math.statistics;

import java.util.Arrays;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

/**
 * apache.commons.mathを利用したOLS重回帰計算のクラスです。
 * y = c0 + x1c1 + x2c2 + x3c3 ...の係数を求めます。
 * c0の切片を考慮するかどうかはnoInterceptで決めます。
 * 
 */
public class HybsMultiRegression {
	private double coe[];			// 各係数(xの種類＋１になる？）
	private double rsquare;		// 決定係数
	private boolean noIntercept; //切片を利用するかどうか
		
	/**
	 * コンストラクタ。
	 * 与えた二次元データを元に重回帰を計算します。
	 * xデータとして二次元配列を与えます。
	 * noInterceptで切片有り無しを選択します。
	 * @param in_x 説明変数
	 * @param in_y 目的変数
	 * @param noIntercept 切片利用有無(trueで利用しない)
	 */
	public HybsMultiRegression(final double[][] in_x, final double[] in_y, final boolean noIntercept){
		this.noIntercept = noIntercept;
		
		// ここで重回帰計算
		OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
		regression.setNoIntercept(noIntercept);
        regression.newSampleData(in_y, in_x);
		
		coe = regression.estimateRegressionParameters();
		rsquare = regression.calculateRSquared();
	}
	
	/**
	 * コンストラクタ。
	 * 係数配列を与えられるようにしておきます。
	 * （以前に計算したものを利用）
	 * @param in_c 係数配列
	 * @param noIntercept 切片利用有無(trueで利用しない)
	 * 
	 */
	public HybsMultiRegression( final double[] in_c, final boolean noIntercept){
		this.coe = in_c;
		this.noIntercept = noIntercept;
	}
	
	
	/**
	 * 係数の取得。
	 * @return 係数配列
	 */
	public double[] getParam(){
		return coe;
	}
	
	/**
	 * 決定係数の取得。
	 * @return 決定係数
	 */
	public double getRSquare(){
		return rsquare;
	}
	
	/**
	 * 計算( c0 + c1x1...)を行う。
	 * noInterceptによってc0の利用を決める。
	 * xの大きさが足りない場合は0を返す。
	 * 
	 * @param in_x 必要な大きさの変数配列
	 * @return 計算結果
	 */
	public double predict(final double[] in_x){
		double rtn = 0;
		int itr = noIntercept ? 0 : 1;
		if( in_x.length < coe.length-itr ){
			return 0;
		}
		
		for( int i=0; i < in_x.length; i++ ){
			rtn = rtn + in_x[i] * coe[i+itr];
		}
		if( !noIntercept ){ rtn = rtn + coe[0]; }
		
		return rtn;
	}

	/*** ここまでが本体 ***/
	/*** ここからテスト用mainメソッド ***/
	/**
	 * @param args *****************************************/
	public static void main(final String [] args) {
		// データはhttp://mjin.doshisha.ac.jp/R/14.htmlより
		double[] y = new double[] { 50, 60, 65, 65, 70, 75, 80, 85, 90, 95 };
		double[][] x = new double[10][];
		x[0] = new double[] { 165, 65 };
		x[1] = new double[] { 170, 68 };
		x[2] = new double[] { 172, 70 };
		x[3] = new double[] { 175, 65 };
		x[4] = new double[] { 170, 80 };
		x[5] = new double[] { 172, 85 };
		x[6] = new double[] { 183, 78 };
		x[7] = new double[] { 187, 79 };
		x[8] = new double[] { 180, 95 };
		x[9] = new double[] { 185, 97 };
		
		
		HybsMultiRegression mr = new HybsMultiRegression(x,y,true);
		
		System.out.println( mr.getRSquare() );
		System.out.println( Arrays.toString( mr.getParam()) );
		
		System.out.println( mr.predict( new double[] { 169,85 } ));
	}
}

